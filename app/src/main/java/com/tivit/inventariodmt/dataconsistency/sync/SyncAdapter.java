package com.tivit.inventariodmt.dataconsistency.sync;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.AbstractThreadedSyncAdapter;
import android.content.ContentProviderClient;
import android.content.ContentProviderOperation;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.OperationApplicationException;
import android.content.SyncResult;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.os.RemoteException;
import android.util.Log;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.gson.Gson;
import com.tivit.inventariodmt.DownloadActivity;
import com.tivit.inventariodmt.R;
import com.tivit.inventariodmt.dao.DatabaseHelper;
import com.tivit.inventariodmt.dataconsistency.provider.DepartamentoContract;
import com.tivit.inventariodmt.dataconsistency.provider.EquipamentoContract;
import com.tivit.inventariodmt.dataconsistency.provider.FabricanteContract;
import com.tivit.inventariodmt.dataconsistency.provider.LocalidadeContract;
import com.tivit.inventariodmt.dataconsistency.provider.ModeloContract;
import com.tivit.inventariodmt.dataconsistency.provider.StatusContract;
import com.tivit.inventariodmt.dataconsistency.provider.TipoEquipamentoContract;
import com.tivit.inventariodmt.dataconsistency.utils.Constantes;
import com.tivit.inventariodmt.dataconsistency.utils.Utilidades;
import com.tivit.inventariodmt.dataconsistency.web.VolleySingleton;
import com.tivit.inventariodmt.dto.DepartamentoDTO;
import com.tivit.inventariodmt.dto.EquipamentoDTO;
import com.tivit.inventariodmt.dto.FabricanteDTO;
import com.tivit.inventariodmt.dto.LocalidadeDTO;
import com.tivit.inventariodmt.dto.ModeloDTO;
import com.tivit.inventariodmt.dto.StatusDTO;
import com.tivit.inventariodmt.dto.TipoEquipamentoDTO;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 * Created by kaique.rocha on 27/10/2016.
 */

public class SyncAdapter extends AbstractThreadedSyncAdapter {

    private static final String TAG = SyncAdapter.class.getSimpleName();
    public static int tabelaASincronizar = -1;
    ContentResolver resolver;
    //LocalidadeProvider resolverL;
    private Gson gson = new Gson();

    private static final String[] PROJECTION = new String[]{
            EquipamentoContract.Columnas._ID,
            EquipamentoContract.Columnas.ID_REMOTA,
            EquipamentoContract.Columnas.N_SERIE,
            EquipamentoContract.Columnas.PATRIMONIO,
            EquipamentoContract.Columnas.RFID,
            EquipamentoContract.Columnas.TIPO,
            EquipamentoContract.Columnas.STATUS,
            EquipamentoContract.Columnas.DEPARTAMENTO,
            EquipamentoContract.Columnas.LOCALIDADE,
            EquipamentoContract.Columnas.DATA,
            EquipamentoContract.Columnas.ESTADO,
            EquipamentoContract.Columnas.FABRICANTE,
            EquipamentoContract.Columnas.MODELO,
    };

    private static final String[] LOCALIDADE_PROJECTION = new String[]{
            LocalidadeContract.Colunas._ID,
            LocalidadeContract.Colunas.CEP,
            LocalidadeContract.Colunas.CIDADE,
            LocalidadeContract.Colunas.DESCRICAO,
            LocalidadeContract.Colunas.ENDERECO,
            LocalidadeContract.Colunas.ESTADO,
            LocalidadeContract.Colunas.ESTADO_SINC,
            LocalidadeContract.Colunas.ID_REMOTA,
            LocalidadeContract.Colunas.INSERT_PENDING,
            LocalidadeContract.Colunas.NOME,
    };

    public static final int COLUNA_ID = 0;
    public static final int COLUNA_ID_REMOTA = 1;
    public static final int COLUNA_N_SERIE = 2;
    public static final int COLUNA_PATRIMONIO = 3;
    public static final int COLUNA_RFID = 4;
    public static final int COLUNA_TIPO = 5;
    public static final int COLUNA_STATUS = 6;
    public static final int COLUNA_DEPARTAMENTO = 7;
    public static final int COLUNA_LOCALIDADE = 8;
    public static final int COLUNA_DATA = 9;
    private DatabaseHelper helper;
    private SQLiteDatabase db;
    //public static final int COLUNA_ESTADO = 10;


    private SQLiteDatabase getDb() {
        helper = new DatabaseHelper(getContext());

        if (db == null) {
            db = helper.getReadableDatabase();
        }
        return db;
    }

    public SyncAdapter(Context context, boolean autoInitialize) {
        super(context, autoInitialize);
        resolver = context.getContentResolver();
    }

    public SyncAdapter(
            Context context,
            boolean autoInitialize,
            boolean allowParallelSyncs) {
        super(context, autoInitialize, allowParallelSyncs);
        resolver = context.getContentResolver();
    }
    public static void inicializarSyncAdapter(Context context) {
        obtenerCuentaASincronizar(context);
    }

    @Override
    public void onPerformSync(Account account,
                              Bundle extras,
                              String authority,
                              ContentProviderClient provider,
                              final SyncResult syncResult) {

        Log.i(TAG, "onPerformSync()...");

        boolean apenasSubida = extras.getBoolean(ContentResolver.SYNC_EXTRAS_UPLOAD, false);
        switch (tabelaASincronizar) {
            case 1:
                if (!apenasSubida) {
                    realizarSincronizacionLocal(syncResult);
                } else {
                    realizarSincronizacionRemota();
                }
            break;
            case 2:
                downloadLocaidades(syncResult);
                downloadFabricantes(syncResult);
                downloadStatus(syncResult);
                downloadDepartamentos(syncResult);
                downloadModelos(syncResult);
                downloadTpEquipamentos(syncResult);
                break;
        }
    }

    private void realizarSincronizacionLocal(final SyncResult syncResult) {
        Log.i(TAG, "Actualizando el cliente.");
        final boolean[] retorno = new boolean[1];
        try {
            JsonArrayRequest jr = new JsonArrayRequest(Request.Method.GET, Constantes.GET_URL + DownloadActivity.idLocalidade,
                    new Response.Listener<JSONArray>() {
                        @Override
                        public void onResponse(JSONArray response) {
                            procesarRespuestaGet(response, syncResult);

                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.e(TAG, error.toString());

                        }
                    }
            );
            jr.setRetryPolicy(new DefaultRetryPolicy(3000,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            VolleySingleton.getInstance(getContext()).addToRequestQueue(jr);
        }
        catch (Exception e)
        {
            Log.e(TAG, e.toString());

        }
    }
    /**
     * Procesa la respuesta del servidor al pedir que se retornen todos los gastos.
     *
     * @param response   Respuesta en formato Json
     * @param syncResult Registro de resultados de sincronización
     */
    private void procesarRespuestaGet(JSONArray response, SyncResult syncResult) {
        try {
            // Obtener atributo "estado"
            JSONObject rObject = (JSONObject) response.get(0);
            String estado = rObject.getString(Constantes.ESTADO);

            switch (estado) {
                case Constantes.SUCCESS: // EXITO
                    actualizarDatosLocales(response, syncResult);
                    break;
                case Constantes.FAILED: // FALLIDO
                    String mensaje = rObject.getString(Constantes.MENSAJE);
                    Log.i(TAG, mensaje);
                    break;
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    //SINCRONIZAÇÃO DE LOCALIDADE

    private void downloadLocaidades(final SyncResult syncResult) {
        Log.i(TAG, "Atualizando.");
        try {
            JsonArrayRequest jr = new JsonArrayRequest(Request.Method.GET, Constantes.GET_LOCALIDADE,
                    new Response.Listener<JSONArray>() {
                        @Override
                        public void onResponse(JSONArray response) {
                            atualizarLocalidade(response, syncResult);

                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.e(TAG, error.toString());

                        }
                    }
            );
            jr.setRetryPolicy(new DefaultRetryPolicy(6000,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            VolleySingleton.getInstance(getContext()).addToRequestQueue(jr);
        }
        catch (Exception e)
        {
            Log.e(TAG, e.toString());

        }
    }

    //SINCRONIZAÇÃO DE DEPARTAMENTO

    private void downloadDepartamentos(final SyncResult syncResult) {
        Log.i(TAG, "Atualizando.");
        try {
            JsonArrayRequest jr = new JsonArrayRequest(Request.Method.GET, Constantes.GET_DEPARTAMENTO,
                    new Response.Listener<JSONArray>() {
                        @Override
                        public void onResponse(JSONArray response) {
                            atualizarDepartamentos(response, syncResult);
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.e(TAG, error.toString());

                        }
                    }
            );
            jr.setRetryPolicy(new DefaultRetryPolicy(6000,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            VolleySingleton.getInstance(getContext()).addToRequestQueue(jr);
        }
        catch (Exception e)
        {
            Log.e(TAG, e.toString());

        }
    }

    //SINCRONIZAÇÃO DE FABRICANTE

    private void downloadFabricantes(final SyncResult syncResult) {
        Log.i(TAG, "Atualizando.");
        try {
            JsonArrayRequest jr = new JsonArrayRequest(Request.Method.GET, Constantes.GET_FABRICANTE,
                    new Response.Listener<JSONArray>() {
                        @Override
                        public void onResponse(JSONArray response) {
                            atualizarFabricantes(response, syncResult);
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.e(TAG, error.toString());

                        }
                    }
            );
            jr.setRetryPolicy(new DefaultRetryPolicy(6000,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            VolleySingleton.getInstance(getContext()).addToRequestQueue(jr);
        }
        catch (Exception e)
        {
            Log.e(TAG, e.toString());

        }
    }

    //SINCRONIZAÇÃO DE MODELOS

    private void downloadModelos(final SyncResult syncResult) {
        Log.i(TAG, "Atualizando.");
        try {
            JsonArrayRequest jr = new JsonArrayRequest(Request.Method.GET, Constantes.GET_MODELO,
                    new Response.Listener<JSONArray>() {
                        @Override
                        public void onResponse(JSONArray response) {
                            atualizarModelos(response, syncResult);
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.e(TAG, error.toString());

                        }
                    }
            );
            jr.setRetryPolicy(new DefaultRetryPolicy(6000,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            VolleySingleton.getInstance(getContext()).addToRequestQueue(jr);
        }
        catch (Exception e)
        {
            Log.e(TAG, e.toString());

        }
    }

    //SINCRONIZAÇÃO DE SATAUS

    private void downloadStatus(final SyncResult syncResult) {
        Log.i(TAG, "Atualizando.");
        try {
            JsonArrayRequest jr = new JsonArrayRequest(Request.Method.GET, Constantes.GET_STATUS,
                    new Response.Listener<JSONArray>() {
                        @Override
                        public void onResponse(JSONArray response) {
                            atualizarStatus(response, syncResult);
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.e(TAG, error.toString());

                        }
                    }
            );
            jr.setRetryPolicy(new DefaultRetryPolicy(6000,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            VolleySingleton.getInstance(getContext()).addToRequestQueue(jr);
        }
        catch (Exception e)
        {
            Log.e(TAG, e.toString());

        }
    }


    //SINCRONIZAÇÃO DE TIPOS  DE EQUILPAMENTO

    private void downloadTpEquipamentos(final SyncResult syncResult) {
        Log.i(TAG, "Atualizando.");
        try {
            JsonArrayRequest jr = new JsonArrayRequest(Request.Method.GET, Constantes.GET_TP_EQUIPAMENTO,
                    new Response.Listener<JSONArray>() {
                        @Override
                        public void onResponse(JSONArray response) {
                            atualizarTpEquipamentos(response, syncResult);
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.e(TAG, error.toString());

                        }
                    }
            );
            jr.setRetryPolicy(new DefaultRetryPolicy(6000,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            VolleySingleton.getInstance(getContext()).addToRequestQueue(jr);
        }
        catch (Exception e)
        {
            Log.e(TAG, e.toString());

        }
    }


//    private void procesarRespostaGetLocalidade(JSONArray response, SyncResult syncResult) {
//        try {
//            // Obtener atributo "estado"
//            JSONObject rObject = (JSONObject) response.get(0);
//            String estado = rObject.getString(Constantes.ESTADO_LOCALIDADE);
//            atualizarLocalidade(response, syncResult);
//
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//    }

    private void realizarSincronizacionRemota() {
        Log.i(TAG, "Atualizando o servidor...");

        iniciarActualizacion();

        Cursor c = obtenerRegistrosSucios();

        Log.i(TAG, "Foram encontrados " + c.getCount() + " registros a sincronizar.");

        if (c.getCount() > 0) {
            while (c.moveToNext()) {
                final int idLocal = c.getInt(COLUNA_ID);


                    JsonObjectRequest jr = new JsonObjectRequest(
                                Request.Method.POST,
                                Constantes.INSERT_URL,
                                Utilidades.deCursorAJSONObjectEquipamento(c),
                                new Response.Listener<JSONObject>() {
                                    @Override
                                    public void onResponse(JSONObject response) {
                                        procesarRespuestaInsert(response, idLocal);
                                        String where = "inv_fs_ic_EstadoSinc=?";
                                        String[] args = new String[] { "0" };
                                        resolver.delete(EquipamentoContract.CONTENT_URI,where,args);
                                        //getDb().execSQL("DELETE FROM sqlite_sequence WHERE name = 'Inv_FS_Item_config';");
                                    }
                                },
                                new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {
                                        Log.d(TAG, "Error Volley: " + error.getMessage());
                                    }
                                }

                        );
                jr.setRetryPolicy(new DefaultRetryPolicy(2000,
                       DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                        DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
                VolleySingleton.getInstance(getContext()).addToRequestQueue(jr);
            }

        } else {
            Log.i(TAG, "Não foi necessário sincronizar");
        }
        c.close();
    }

    private Cursor obtenerRegistrosSucios() {
        Uri uri = EquipamentoContract.CONTENT_URI;
        String selection = EquipamentoContract.Columnas.INSERT_PENDING + "=? AND "
                + EquipamentoContract.Columnas.ESTADO + "=?";
        String[] selectionArgs = new String[]{"1", EquipamentoContract.ESTADO_SYNC + ""};

        return resolver.query(uri, PROJECTION, selection, selectionArgs, null);
    }

    private void iniciarActualizacion() {
        Uri uri = EquipamentoContract.CONTENT_URI;
        String selection = EquipamentoContract.Columnas.INSERT_PENDING + "=? AND "
                + EquipamentoContract.Columnas.ESTADO + "=?";
        String[] selectionArgs = new String[]{"1", EquipamentoContract.ESTADO_OK + ""};

        ContentValues v = new ContentValues();
        v.put(EquipamentoContract.Columnas.ESTADO, EquipamentoContract.ESTADO_SYNC);

        int results = resolver.update(uri, v, selection, selectionArgs);
        Log.i(TAG, "Registros na fila de inserção:" + results);
    }

    private void finalizarActualizacion(String idRemota, int idLocal) {
        Uri uri = EquipamentoContract.CONTENT_URI;
        String selection = EquipamentoContract.Columnas._ID + "=?";
        String[] selectionArgs = new String[]{String.valueOf(idLocal)};

        ContentValues v = new ContentValues();
        v.put(EquipamentoContract.Columnas.INSERT_PENDING, "0");
        v.put(EquipamentoContract.Columnas.ESTADO, EquipamentoContract.ESTADO_OK);
        v.put(EquipamentoContract.Columnas.ID_REMOTA, idRemota);

        resolver.update(uri, v, selection, selectionArgs);
    }

    public void procesarRespuestaInsert(JSONObject response, int idLocal) {

        try {
            // Obtener estado
            String estado = response.getString(Constantes.ESTADO);
            // Obtener mensaje
            //String mensaje = response.getString(Constantes.MENSAJE);
            // Obtener identificador del nuevo registro creado en el servidor
            String idRemota = response.getString(Constantes.ID_GASTO);

            switch (estado) {
                case Constantes.SUCCESS:
                    Log.i(TAG, "Finalizando Sincronização");
                    finalizarActualizacion(idRemota, idLocal);
                    break;

                case Constantes.FAILED:
                    //Log.e(TAG, mensaje);
                    break;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

//    private void actualizarDatosLocales(JSONArray equipamentos, SyncResult syncResult) {
//
//
//
//        /*try {
//            // Obtener array "gastos"
//            equipamentos = response.getJSONArray(Constantes.EQUIPAMENTOS);
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }*/
//        // Parsear con Gson
//
//
//
//        //EquipamentoDTO[] res = gson.fromJson(equipamentos != null ? equipamentos.toString() : null, EquipamentoDTO[].class);
//        //List<EquipamentoDTO> data = Arrays.asList(res);
//
//        List<EquipamentoDTO> data = new ArrayList<>();
//        EquipamentoDTO eDto = new EquipamentoDTO();
//
//        for (int i = 0; i < equipamentos.length(); i++){
//            try {
//                JSONObject equipObj = (JSONObject) equipamentos.get(i);
//                eDto.set_id(equipObj.getInt("_id"));
//                eDto.setInv_fs_ic_numero_serie(equipObj.getString("inv_fs_ic_numero_serie"));
//                eDto.setInv_fs_ic_RFID(equipObj.getString("inv_fs_ic_RFID"));
//                eDto.setInv_fs_ic_Patrimonio(equipObj.getString("inv_fs_ic_Patrimonio"));
//                eDto.setStatus_id(equipObj.getInt("status_id"));
//                eDto.setTipo_equipamento_id(equipObj.getInt("tipo_equipamento_id"));
//                eDto.setLocalidade_id(equipObj.getInt("localidade_id"));
//                eDto.setDepartamento_id(equipObj.getInt("departamento_id"));
//                eDto.setInv_fs_ic_Data_Criacao(equipObj.getLong("inv_fs_ic_Data_Criacao"));
//
//                eDto.setEstado(equipObj.getInt("estado"));
//
//                data.add(eDto);
//
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
//        }
//
//        // Lista para recolección de operaciones pendientes
//        ArrayList<ContentProviderOperation> ops = new ArrayList<ContentProviderOperation>();
//
//        // Tabla hash para recibir las entradas entrantes
//        HashMap<String, EquipamentoDTO> expenseMap = new HashMap<String, EquipamentoDTO>();
//        for (EquipamentoDTO e : data) {
//            expenseMap.put(String.valueOf(e.get_id()), e);
//        }
//
//        // Consultar registros remotos actuales
//        Uri uri = EquipamentoContract.CONTENT_URI;
//        String select = EquipamentoContract.Columnas.ID_REMOTA + " IS NOT NULL";
//        Cursor c = resolver.query(uri, PROJECTION, select, null, null);
//        assert c != null;
//
//        Log.i(TAG, "Se encontraron " + c.getCount() + " registros locales.");
//
//        // Encontrar datos obsoletos
//        int id;
//        String serie;
//        String patrimonio;
//        String rfid;
//        int tipo;
//        int status;
//        int departamento;
//        int localidade;
//        long dataCriacao;
//        //int estado;
//        while (c.moveToNext()) {
//            syncResult.stats.numEntries++;
//
//            id = c.getInt(COLUNA_ID_REMOTA);
//            serie = c.getString(COLUNA_N_SERIE);
//            patrimonio = c.getString(COLUNA_PATRIMONIO);
//            tipo = c.getInt(COLUNA_TIPO);
//            status= c.getInt(COLUNA_STATUS);
//            departamento = c.getInt(COLUNA_DEPARTAMENTO);
//            localidade = c.getInt(COLUNA_LOCALIDADE);
//            dataCriacao = c.getLong(COLUNA_DATA);
//            //estado = c.getInt(COLUNA_ESTADO);
//
//            EquipamentoDTO match = expenseMap.get(id);
//
//            if (match != null) {
//                // Esta entrada existe, por lo que se remueve del mapeado
//                expenseMap.remove(id);
//
//                Uri existingUri = EquipamentoContract.CONTENT_URI.buildUpon()
//                        .appendPath(String.valueOf(id)).build();
//
//                // Comprobar si el gasto necesita ser actualizado
//                boolean b = String.valueOf(match.get_id()) != serie;
//                boolean b1 = match.getInv_fs_ic_numero_serie() != null && !match.getInv_fs_ic_numero_serie().equals(serie);
//                boolean b2 = match.getInv_fs_ic_Patrimonio() != null && !match.getInv_fs_ic_Patrimonio().equals(patrimonio);
//                boolean b3 = match.getTipo_equipamento_id() == tipo;
//                boolean b4 = match.getStatus_id() != status;
//                boolean b5 = match.getDepartamento_id() != departamento;
//                boolean b6 = match.getLocalidade_id() != localidade;
////                DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");ss
//                boolean b7 = match.getInv_fs_ic_Data_Criacao() != dataCriacao;
//                //boolean b8 = match.getEstado() != estado;
//
//                if (b || b1 || b2 || b3 || b4 || b5 || b6 || b7) {
//
//                    Log.i(TAG, "Programando actualización de: " + existingUri);
//
//                    ops.add(ContentProviderOperation.newUpdate(existingUri)
//                            .withValue(EquipamentoContract.Columnas.N_SERIE, match.inv_fs_ic_numero_serie)
//                            .withValue(EquipamentoContract.Columnas.PATRIMONIO, match.inv_fs_ic_Patrimonio)
//                            .withValue(EquipamentoContract.Columnas.RFID, match.inv_fs_ic_RFID)
//                            .withValue(EquipamentoContract.Columnas.TIPO, match.tipo_equipamento_id)
//                            .withValue(EquipamentoContract.Columnas.STATUS, match.status_id)
//                            .withValue(EquipamentoContract.Columnas.DEPARTAMENTO, match.departamento_id)
//                            .withValue(EquipamentoContract.Columnas.TIPO, match.localidade_id)
//                            .withValue(EquipamentoContract.Columnas.DATA, match.inv_fs_ic_Data_Criacao)
//                            .build());
//                    syncResult.stats.numUpdates++;
//                } else {
//                    Log.i(TAG, "No hay acciones para este registro: " + existingUri);
//                }
//            } else {
//                // Debido a que la entrada no existe, es removida de la base de datos
//                Uri deleteUri = EquipamentoContract.CONTENT_URI.buildUpon()
//                        .appendPath(String.valueOf(id)).build();
//                Log.i(TAG, "Programando eliminación de: " + deleteUri);
//                ops.add(ContentProviderOperation.newDelete(deleteUri).build());
//                syncResult.stats.numDeletes++;
//            }
//        }
//        c.close();
//
//        // Insertar items resultantes
//        for (EquipamentoDTO e : expenseMap.values()) {
//            Log.i(TAG, "Programando inserción de: " + e.get_id());
//            ops.add(ContentProviderOperation.newInsert(EquipamentoContract.CONTENT_URI)
//                    .withValue(EquipamentoContract.Columnas.N_SERIE, e.inv_fs_ic_numero_serie)
//                    .withValue(EquipamentoContract.Columnas.PATRIMONIO, e.inv_fs_ic_Patrimonio)
//                    .withValue(EquipamentoContract.Columnas.RFID, e.inv_fs_ic_RFID)
//                    .withValue(EquipamentoContract.Columnas.TIPO, e.tipo_equipamento_id)
//                    .withValue(EquipamentoContract.Columnas.STATUS, e.status_id)
//                    .withValue(EquipamentoContract.Columnas.DEPARTAMENTO, e.departamento_id)
//                    .withValue(EquipamentoContract.Columnas.TIPO, e.localidade_id)
//                    .withValue(EquipamentoContract.Columnas.DATA, e.inv_fs_ic_Data_Criacao)
//                    .build());
//            syncResult.stats.numInserts++;
//        }
//
//        if (syncResult.stats.numInserts > 0 ||
//                syncResult.stats.numUpdates > 0 ||
//                syncResult.stats.numDeletes > 0) {
//            Log.i(TAG, "Aplicando operaciones...");
//            try {
//                resolver.applyBatch(EquipamentoContract.AUTHORITY, ops);
//            } catch (RemoteException | OperationApplicationException e) {
//                e.printStackTrace();
//            }
//            resolver.notifyChange(
//                    EquipamentoContract.CONTENT_URI,
//                    null,
//                    false);
//            Log.i(TAG, "Sincronización finalizada.");
//
//        } else {
//            Log.i(TAG, "No se requiere sincronización");
//        }
//
//    }

    private void actualizarDatosLocales(JSONArray response, SyncResult syncResult) {

        //JSONArray equipamentos = null;

//        try {
//            // Obtener array "gastos"
//            //equipamentos = response.getJSONArray(Constantes.EQUIPAMENTOS);
//        } catch (JSONException ex) {
//            ex.printStackTrace();
//        }
        // Parsear con Gson
        EquipamentoDTO[] res = gson.fromJson(response != null ? response.toString() : null, EquipamentoDTO[].class);
        List<EquipamentoDTO> data = Arrays.asList(res);

        Gson gson = new Gson();
        Log.i(TAG, gson.toJson(data));


         //Lista para recolección de operaciones pendientes
        ArrayList<ContentProviderOperation> ops = new ArrayList<ContentProviderOperation>();

        // Tabla hash para recibir las entradas entrantes
        HashMap<String, EquipamentoDTO> expenseMap = new HashMap<String, EquipamentoDTO>();
        for (EquipamentoDTO e : data) {
            expenseMap.put(String.valueOf(e.getInv_fs_ic_numero_serie()), e);
        }

        // Consultar registros remotos actuales
        Uri uri = EquipamentoContract.CONTENT_URI;
        String select = EquipamentoContract.Columnas.ESTADO + " = 0";
        Cursor c = resolver.query(uri, PROJECTION, select, null, null);
        assert c != null;

        Log.i(TAG, "Foram encontrados " + c.getCount() + " registros locais.");

        // Encontrar datos obsoletos
        int id;
        String serie;
        String patrimonio;
        String rfid;
        int tipo;
        int status;
        int departamento;
        int localidade;
        long dataCriacao;
        //int estado;
        while (c.moveToNext()) {
            syncResult.stats.numEntries++;

            id = c.getInt(c.getColumnIndex(EquipamentoContract.Columnas._ID));
            serie = c.getString(c.getColumnIndex(EquipamentoContract.Columnas.N_SERIE));
            patrimonio = c.getString(c.getColumnIndex(EquipamentoContract.Columnas.PATRIMONIO));
            tipo = c.getInt(5);
            status= c.getInt(c.getColumnIndex(EquipamentoContract.Columnas.STATUS));
            departamento = c.getInt(c.getColumnIndex(EquipamentoContract.Columnas.DEPARTAMENTO));
            localidade = c.getInt(c.getColumnIndex(EquipamentoContract.Columnas.LOCALIDADE));
            dataCriacao = c.getLong(c.getColumnIndex(EquipamentoContract.Columnas.DATA));
            //estado = c.getInt(COLUNA_ESTADO);

            EquipamentoDTO match = expenseMap.get(serie);

            if (match != null) {
                // Esta entrada existe, por lo que se remueve del mapeado
                expenseMap.remove(serie);

                Uri existingUri = EquipamentoContract.CONTENT_URI.buildUpon()
                        .appendPath(String.valueOf(id)).build();

                // Comprobar si el gasto necesita ser actualizado
                //boolean b = String.valueOf(match.getInv_fs_ic_numero_serie()) != serie;
                boolean b1 = match.getInv_fs_ic_numero_serie() != null && !match.getInv_fs_ic_numero_serie().equals(serie);
                boolean b2 = match.getInv_fs_ic_Patrimonio() != null && !match.getInv_fs_ic_Patrimonio().equals(patrimonio);
                boolean b3 = match.getInv_fs_ic_Id_Tipo_Equipamento() == tipo;
                boolean b4 = match.getInv_fs_ic_Id_Status() != status;
                boolean b5 = match.getInv_fs_ic_Id_Departamento() != departamento;
                boolean b6 = match.getInv_fs_ic_Id_Localidade() != localidade;
//                DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");ss
                boolean b7 = match.getInv_fs_ic_Data_Criacao() != dataCriacao;
                //boolean b8 = match.getEstado() != estado;

                if (b1 || b2 || b3 || b4 || b5 || b6 || b7) {

                    Log.i(TAG, "Programando atualização de: " + existingUri);

                    ops.add(ContentProviderOperation.newUpdate(existingUri)
                            .withValue(EquipamentoContract.Columnas.N_SERIE, match.getInv_fs_ic_numero_serie())
                            .withValue(EquipamentoContract.Columnas.PATRIMONIO, match.getInv_fs_ic_Patrimonio())
                            .withValue(EquipamentoContract.Columnas.RFID, match.getInv_fs_ic_RFID())
                            .withValue(EquipamentoContract.Columnas.TIPO, match.getInv_fs_ic_Id_Tipo_Equipamento())
                            .withValue(EquipamentoContract.Columnas.STATUS, match.getInv_fs_ic_Id_Status())
                            .withValue(EquipamentoContract.Columnas.DEPARTAMENTO, match.getInv_fs_ic_Id_Departamento())
                            .withValue(EquipamentoContract.Columnas.DATA, match.getInv_fs_ic_Data_Criacao())
                            .build());
                    syncResult.stats.numUpdates++;
                } else {
                    Log.i(TAG, "Não há acões para estes registros: " + existingUri);
                }
            } else {
                // Debido a que la entrada no existe, es removida de la base de datos
                Uri deleteUri = EquipamentoContract.CONTENT_URI.buildUpon()
                        .appendPath(String.valueOf(id)).build();
                Log.i(TAG, "Programando eliminação de: " + deleteUri);
                ops.add(ContentProviderOperation.newDelete(deleteUri).build());
                syncResult.stats.numDeletes++;
            }
        }
        c.close();

        // Insertar items resultantes
        for (EquipamentoDTO e : expenseMap.values()) {
            Log.i(TAG, "Programando inserción de: " + e.getInv_fs_ic_Id_IC());
            ops.add(ContentProviderOperation.newInsert(EquipamentoContract.CONTENT_URI)
                    .withValue(EquipamentoContract.Columnas.N_SERIE, e.getInv_fs_ic_numero_serie())
                    .withValue(EquipamentoContract.Columnas.PATRIMONIO, e.getInv_fs_ic_Patrimonio())
                    .withValue(EquipamentoContract.Columnas.RFID, e.getInv_fs_ic_RFID())
                    .withValue(EquipamentoContract.Columnas.TIPO, e.getInv_fs_ic_Id_Tipo_Equipamento())
                    .withValue(EquipamentoContract.Columnas.STATUS, e.getInv_fs_ic_Id_Status())
                    .withValue(EquipamentoContract.Columnas.DEPARTAMENTO, e.getInv_fs_ic_Id_Departamento())
                    .withValue(EquipamentoContract.Columnas.DATA, e.getInv_fs_ic_Data_Criacao())
                    .build());
            syncResult.stats.numInserts++;
        }

        if (syncResult.stats.numInserts > 0 ||
                syncResult.stats.numUpdates > 0 ||
                syncResult.stats.numDeletes > 0) {
            Log.i(TAG, "Aplicando operaciones...");
            try {
                resolver.applyBatch(EquipamentoContract.AUTHORITY, ops);
            } catch (RemoteException | OperationApplicationException e) {
                e.printStackTrace();
            }
            resolver.notifyChange(
                    EquipamentoContract.CONTENT_URI,
                    null,
                    false);
            Log.i(TAG, "Sincronização finalizada.");

        } else {
            Log.i(TAG, "Não foi necessário sincronizar");
        }

    }

    private void atualizarLocalidade(JSONArray response, SyncResult syncResult) {

        LocalidadeDTO[] res = gson.fromJson(response != null ? response.toString() : null, LocalidadeDTO[].class);
        List<LocalidadeDTO> data = Arrays.asList(res);

        Gson gson = new Gson();
        Log.i(TAG, gson.toJson(data));

        HashMap<String, LocalidadeDTO> expenseMap = new HashMap<String, LocalidadeDTO>();
        for (LocalidadeDTO l : data) {
            expenseMap.put(String.valueOf(l.getInv_FS_Loc_Id_Localidade()), l);
        }


        for (LocalidadeDTO l : expenseMap.values()) {
            ContentValues values = new ContentValues();
            values.put(LocalidadeContract.Colunas._ID, l.getInv_FS_Loc_Id_Localidade());
            values.put(LocalidadeContract.Colunas.CEP, l.getInv_FS_Loc_cep());
            values.put(LocalidadeContract.Colunas.CIDADE, l.getInv_FS_Loc_cidade());
            values.put(LocalidadeContract.Colunas.DESCRICAO, l.getInv_FS_Loc_Descricao());
            values.put(LocalidadeContract.Colunas.ENDERECO, l.getInv_FS_Loc_Endereco());
            values.put(LocalidadeContract.Colunas.ESTADO, l.getInv_FS_Loc_estado());
            values.put(LocalidadeContract.Colunas.CIDADE, l.getInv_FS_Loc_cidade());
            values.put(LocalidadeContract.Colunas.NOME, l.getInv_FS_Loc_nome_localidade());
            syncResult.stats.numInserts++;
            getDb().insert(LocalidadeContract.LOCALIDADE,null,values);
        }

        if (syncResult.stats.numInserts > 0 || syncResult.stats.numUpdates > 0 || syncResult.stats.numDeletes > 0) {
            Log.i(TAG, "Aplicando operações...");

            resolver.notifyChange(LocalidadeContract.CONTENT_URI,null,false);
            Log.i(TAG, "Sincronização finalizada.");

        } else {
            Log.i(TAG, "Não foi necessário sincronizar");
        }

    }

    private void atualizarDepartamentos(JSONArray response, SyncResult syncResult) {

        DepartamentoDTO[] res = gson.fromJson(response != null ? response.toString() : null, DepartamentoDTO[].class);
        List<DepartamentoDTO> data = Arrays.asList(res);

        Gson gson = new Gson();
        Log.i(TAG, gson.toJson(data));

        HashMap<String, DepartamentoDTO> expenseMap = new HashMap<String, DepartamentoDTO>();
        for (DepartamentoDTO d : data) {
            expenseMap.put(String.valueOf(d.getInv_FS_Dep_Id_Departamento()), d);
        }


        for (DepartamentoDTO d : expenseMap.values()) {
            ContentValues values = new ContentValues();
            values.put(DepartamentoContract.Colunas._ID, d.getInv_FS_Dep_Id_Departamento());
            values.put(DepartamentoContract.Colunas.DESCRICAO, d.getInv_FS_Dep_Descricao());
            values.put(DepartamentoContract.Colunas.NOME, d.getInv_FS_Dep_Nome_Departamento());
            values.put(DepartamentoContract.Colunas.ORGANIZACAO, d.getInv_FS_Dep_Id_Organizacao());

            syncResult.stats.numInserts++;
            getDb().insert(DepartamentoContract.DEPARTAMENTO,null,values);
        }

        if (syncResult.stats.numInserts > 0 || syncResult.stats.numUpdates > 0 || syncResult.stats.numDeletes > 0) {
            Log.i(TAG, "Aplicando operações...");

            resolver.notifyChange(LocalidadeContract.CONTENT_URI,null,false);
            Log.i(TAG, "Sincronização finalizada.");

        } else {
            Log.i(TAG, "Não foi necessário sincronizar");
        }

    }

    private void atualizarFabricantes(JSONArray response, SyncResult syncResult) {

        FabricanteDTO[] res = gson.fromJson(response != null ? response.toString() : null, FabricanteDTO[].class);
        List<FabricanteDTO> data = Arrays.asList(res);

        Gson gson = new Gson();
        Log.i(TAG, gson.toJson(data));

        HashMap<String, FabricanteDTO> expenseMap = new HashMap<String, FabricanteDTO>();
        for (FabricanteDTO f : data) {
            expenseMap.put(String.valueOf(f.getInv_FS_Fab_Id_Fabricante()), f);
        }


        for (FabricanteDTO f : expenseMap.values()) {
            ContentValues values = new ContentValues();
            values.put(FabricanteContract.Colunas._ID, f.getInv_FS_Fab_Id_Fabricante());
            values.put(FabricanteContract.Colunas.NOME, f.getInv_FS_Fab_Nome_Fabricante());
            values.put(FabricanteContract.Colunas.DESCRICAO, f.getInv_FS_Fab_Descricao());

            syncResult.stats.numInserts++;
            getDb().insert(FabricanteContract.FABRICANTE,null,values);
        }

        if (syncResult.stats.numInserts > 0 || syncResult.stats.numUpdates > 0 || syncResult.stats.numDeletes > 0) {
            Log.i(TAG, "Aplicando operações...");

            //resolver.notifyChange(FabricanteContract.CONTENT_URI,null,false);
            Log.i(TAG, "Sincronização finalizada.");

        } else {
            Log.i(TAG, "Não foi necessário sincronizar");
        }

    }

    private void atualizarModelos(JSONArray response, SyncResult syncResult) {

        ModeloDTO[] res = gson.fromJson(response != null ? response.toString() : null, ModeloDTO[].class);
        List<ModeloDTO> data = Arrays.asList(res);

        Gson gson = new Gson();
        Log.i(TAG, gson.toJson(data));

        HashMap<String, ModeloDTO> expenseMap = new HashMap<String, ModeloDTO>();
        for (ModeloDTO m : data) {
            expenseMap.put(String.valueOf(m.getInv_FS_Mod_Id_Modelo()), m);
        }


        for (ModeloDTO m : expenseMap.values()) {
            ContentValues values = new ContentValues();
            values.put(ModeloContract.Colunas._ID, m.getInv_FS_Mod_Id_Modelo());
            values.put(ModeloContract.Colunas.NOME, m.getInv_FS_Mod_Nome_Modelo());
            values.put(ModeloContract.Colunas.DESCRICAO, m.getInv_FS_Mod_Descricao());
            values.put(ModeloContract.Colunas.ANO, m.getInv_FS_Mod_Ano());
            values.put(ModeloContract.Colunas.FABRICANTE, m.getInv_FS_Mod_Id_Fabricante());

            syncResult.stats.numInserts++;
            getDb().insert(ModeloContract.MODELO,null,values);
        }

        if (syncResult.stats.numInserts > 0 || syncResult.stats.numUpdates > 0 || syncResult.stats.numDeletes > 0) {
            Log.i(TAG, "Aplicando operações...");

            //resolver.notifyChange(FabricanteContract.CONTENT_URI,null,false);
            Log.i(TAG, "Sincronização finalizada.");

        } else {
            Log.i(TAG, "Não foi necessário sincronizar");
        }

    }

    private void atualizarStatus(JSONArray response, SyncResult syncResult) {

        StatusDTO[] res = gson.fromJson(response != null ? response.toString() : null, StatusDTO[].class);
        List<StatusDTO> data = Arrays.asList(res);

        Gson gson = new Gson();
        Log.i(TAG, gson.toJson(data));

        HashMap<String, StatusDTO> expenseMap = new HashMap<String, StatusDTO>();
        for (StatusDTO s : data) {
            expenseMap.put(String.valueOf(s.getInv_FS_St_id_Status()), s);
        }


        for (StatusDTO s : expenseMap.values()) {
            ContentValues values = new ContentValues();
            values.put(StatusContract.Colunas._ID, s.getInv_FS_St_id_Status());
            values.put(StatusContract.Colunas.NOME, s.getInv_FS_St_Nome_Status());
            values.put(StatusContract.Colunas.DESCRICAO, s.getInv_FS_St_Descricao());

            syncResult.stats.numInserts++;
            getDb().insert(StatusContract.STATUS,null,values);
        }

        if (syncResult.stats.numInserts > 0 || syncResult.stats.numUpdates > 0 || syncResult.stats.numDeletes > 0) {
            Log.i(TAG, "Aplicando operações...");

            //resolver.notifyChange(FabricanteContract.CONTENT_URI,null,false);
            Log.i(TAG, "Sincronização finalizada.");

        } else {
            Log.i(TAG, "Não foi necessário sincronizar");
        }

    }

    private void atualizarTpEquipamentos(JSONArray response, SyncResult syncResult) {

        TipoEquipamentoDTO[] res = gson.fromJson(response != null ? response.toString() : null, TipoEquipamentoDTO[].class);
        List<TipoEquipamentoDTO> data = Arrays.asList(res);

        Gson gson = new Gson();
        Log.i(TAG, gson.toJson(data));

        HashMap<String, TipoEquipamentoDTO> expenseMap = new HashMap<String, TipoEquipamentoDTO>();
        for (TipoEquipamentoDTO t : data) {
            expenseMap.put(String.valueOf(t.getInv_FS_TP_Id_Tipo_Equipamento()), t);
        }


        for (TipoEquipamentoDTO t : expenseMap.values()) {
            ContentValues values = new ContentValues();
            values.put(TipoEquipamentoContract.Colunas._ID, t.getInv_FS_TP_Id_Tipo_Equipamento());
            values.put(TipoEquipamentoContract.Colunas.NOME, t.getInv_FS_TP_Nome_Equipamento());
            values.put(TipoEquipamentoContract.Colunas.KIT_INSTALACAO, t.getInv_FS_TP_Kit_Instalacao());
            values.put(TipoEquipamentoContract.Colunas.DESCRICAO, t.getInv_FS_TP_Descricao());

            syncResult.stats.numInserts++;
            getDb().insert(TipoEquipamentoContract.TP_EQUIPAMENTO,null,values);
        }

        if (syncResult.stats.numInserts > 0 || syncResult.stats.numUpdates > 0 || syncResult.stats.numDeletes > 0) {
            Log.i(TAG, "Aplicando operações...");

            //resolver.notifyChange(FabricanteContract.CONTENT_URI,null,false);
            Log.i(TAG, "Sincronização finalizada.");

        } else {
            Log.i(TAG, "Não foi necessário sincronizar");
        }

    }


    public static void sincronizarAhora(Context context, boolean onlyUpload, int tabela) {
        Log.i(TAG, "Realizando pedido de sincronização manual.");
        Bundle bundle = new Bundle();
        bundle.putBoolean(ContentResolver.SYNC_EXTRAS_EXPEDITED, true);
        bundle.putBoolean(ContentResolver.SYNC_EXTRAS_MANUAL, true);
        if (onlyUpload)
            bundle.putBoolean(ContentResolver.SYNC_EXTRAS_UPLOAD, true);
        tabelaASincronizar = tabela;
        ContentResolver.requestSync(obtenerCuentaASincronizar(context),context.getString(R.string.provider_authority), bundle);
    }

    public static Account obtenerCuentaASincronizar(Context context) {
        // Obtener instancia del administrador de cuentas
        AccountManager accountManager =
                (AccountManager) context.getSystemService(Context.ACCOUNT_SERVICE);

        // Crear cuenta por defecto
        Account newAccount = new Account(
                context.getString(R.string.app_name), Constantes.ACCOUNT_TYPE);

        // Comprobar existencia de la cuenta
        if (null == accountManager.getPassword(newAccount)) {

            // Añadir la cuenta al account manager sin password y sin datos de usuario
            if (!accountManager.addAccountExplicitly(newAccount, "", null))
                return null;
        }
        Log.i(TAG, "Conta de usuário obtida.");
        return newAccount;
    }
}
