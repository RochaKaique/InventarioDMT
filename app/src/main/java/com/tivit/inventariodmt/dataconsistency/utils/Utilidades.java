package com.tivit.inventariodmt.dataconsistency.utils;

import android.content.Context;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.android.volley.toolbox.StringRequest;
import com.itextpdf.text.List;
import com.tivit.inventariodmt.dataconsistency.provider.EquipamentoContract;
import com.tivit.inventariodmt.dataconsistency.provider.LocalidadeContract;
import com.tivit.inventariodmt.dto.EquipamentoDTO;

/**
 * Created by kaique.rocha on 25/10/2016.
 */

public class Utilidades {


    public static boolean materialDesign() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP;
    }


    public static JSONObject deCursorAJSONObjectEquipamento(Cursor c) {
        JSONObject jObject = new JSONObject();
        String inv_fs_ic_numero_serie;
        String inv_fs_ic_Patrimonio;
        String inv_fs_ic_RFID;
        String tipo_equipamento_id;
        String status_id;
        String departamento_id;
        String localidade_id;
        String data_criacao;
        String estado;

        inv_fs_ic_numero_serie = c.getString(c.getColumnIndex(EquipamentoContract.Columnas.N_SERIE));
        inv_fs_ic_Patrimonio = c.getString(c.getColumnIndex(EquipamentoContract.Columnas.PATRIMONIO));
        inv_fs_ic_RFID = c.getString(c.getColumnIndex(EquipamentoContract.Columnas.RFID));
        tipo_equipamento_id = c.getString(c.getColumnIndex(EquipamentoContract.Columnas.TIPO));
        status_id = c.getString(c.getColumnIndex(EquipamentoContract.Columnas.STATUS));
        departamento_id = c.getString(c.getColumnIndex(EquipamentoContract.Columnas.DEPARTAMENTO));
        localidade_id = c.getString(c.getColumnIndex(EquipamentoContract.Columnas.LOCALIDADE));
        data_criacao = c.getString(c.getColumnIndex(EquipamentoContract.Columnas.DATA));
        estado = c.getString(c.getColumnIndex(EquipamentoContract.Columnas.ESTADO));

        try {
            jObject.put(EquipamentoContract.Columnas.N_SERIE,inv_fs_ic_numero_serie);
            jObject.put(EquipamentoContract.Columnas.PATRIMONIO, inv_fs_ic_Patrimonio);
            jObject.put(EquipamentoContract.Columnas.RFID, inv_fs_ic_RFID);
            jObject.put(EquipamentoContract.Columnas.TIPO, tipo_equipamento_id);
            jObject.put(EquipamentoContract.Columnas.STATUS, status_id);
            jObject.put(EquipamentoContract.Columnas.DEPARTAMENTO, departamento_id);
            jObject.put(EquipamentoContract.Columnas.LOCALIDADE, localidade_id);
            jObject.put(EquipamentoContract.Columnas.DATA, data_criacao);
            jObject.put(EquipamentoContract.Columnas.ESTADO, estado);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        Log.i("Cursor a JSONObject", String.valueOf(jObject));

        return jObject;
    }

    public static JSONObject deCursorAJSONObjectLocalidade(Cursor c)
    {
        JSONObject j = new JSONObject();
        String inv_FS_Loc_Id_Localidade;
        String inv_FS_Loc_Endereco;
        String inv_FS_Loc_Descricao;
        String inv_FS_Loc_cep;
        String inv_FS_Loc_cidade;
        String inv_FS_Loc_estado;
        String inv_FS_Loc_nome_localidade;

        inv_FS_Loc_Id_Localidade = c.getString(c.getColumnIndex(LocalidadeContract.Colunas._ID));
        inv_FS_Loc_Endereco = c.getString(c.getColumnIndex(LocalidadeContract.Colunas.ENDERECO));
        inv_FS_Loc_Descricao = c.getString(c.getColumnIndex(LocalidadeContract.Colunas.DESCRICAO));
        inv_FS_Loc_cep = c.getString(c.getColumnIndex(LocalidadeContract.Colunas.CEP));
        inv_FS_Loc_estado = c.getString(c.getColumnIndex(LocalidadeContract.Colunas.ESTADO));
        inv_FS_Loc_cidade = c.getString(c.getColumnIndex(LocalidadeContract.Colunas.CIDADE));
        inv_FS_Loc_nome_localidade = c.getString(c.getColumnIndex(LocalidadeContract.Colunas.NOME));

        try{

            j.put(LocalidadeContract.Colunas._ID, inv_FS_Loc_Id_Localidade);
            j.put(LocalidadeContract.Colunas.ENDERECO, inv_FS_Loc_Endereco);
            j.put(LocalidadeContract.Colunas.DESCRICAO, inv_FS_Loc_Descricao);
            j.put(LocalidadeContract.Colunas.CEP, inv_FS_Loc_cep);
            j.put(LocalidadeContract.Colunas.ESTADO, inv_FS_Loc_estado);
            j.put(LocalidadeContract.Colunas.CIDADE, inv_FS_Loc_cidade);
            j.put(LocalidadeContract.Colunas.NOME, inv_FS_Loc_nome_localidade);
        }
        catch (JSONException e){
            e.printStackTrace();
        }

        return j;
    }

    public static int networkState(Context cont){
        ConnectivityManager cm = (ConnectivityManager)cont.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting();

        if(isConnected)
        {
            boolean isWiFi = activeNetwork.getType() == ConnectivityManager.TYPE_WIFI;
            if (isWiFi){
                return 1;
            }
            return 2;
        }
        else{
            return -1;
        }
    }


    public static boolean isConnected(Context context){
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }


}
