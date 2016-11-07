package com.tivit.inventariodmt.dataconsistency.utils;

import android.database.Cursor;
import android.os.Build;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.android.volley.toolbox.StringRequest;
import com.itextpdf.text.List;
import com.tivit.inventariodmt.dataconsistency.provider.EquipamentoContract;
import com.tivit.inventariodmt.dto.EquipamentoDTO;

/**
 * Created by kaique.rocha on 25/10/2016.
 */

public class Utilidades {

    // Indices para las COLUNAs indicadas en la proyección
    public static final int COLUNA_ID = 0;
    public static final int COLUNA_ID_REMOTA = 1;
    public static final int COLUNA_SERIE = 2;
    public static final int COLUNA_PATRIMONIO = 3;
    public static final int COLUNA_RFID = 4;
    public static final int COLUNA_TPEQUIPAMENTO = 5;
    public static final int COLUNA_STATUS = 6;
    public static final int COLUNA_DEPARTAMENTO = 7;
    public static final int COLUNA_LOCALIDADE = 8;
    public static final int COLUNA_DATA = 9;
    public static final int COLUNA_ESTADO = 10;

    /**
     * Determina si la aplicación corre en versiones superiores o iguales
     * a Android LOLLIPOP
     *
     * @return booleano de confirmación
     */
    public static boolean materialDesign() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP;
    }

    /**
     * Copia los datos de un gasto almacenados en un cursor hacia un
     * JSONObject
     *
     * @param c cursor
     * @return objeto jason
     */
    public static JSONObject deCursorAJSONObject(Cursor c) {
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

        inv_fs_ic_numero_serie = c.getString(COLUNA_SERIE);
        inv_fs_ic_Patrimonio = c.getString(COLUNA_PATRIMONIO);
        inv_fs_ic_RFID = c.getString(COLUNA_RFID);
        tipo_equipamento_id = c.getString(COLUNA_TPEQUIPAMENTO);
        status_id = c.getString(COLUNA_STATUS);
        departamento_id = c.getString(COLUNA_DEPARTAMENTO);
        localidade_id = c.getString(COLUNA_LOCALIDADE);
        data_criacao = c.getString(COLUNA_DATA);
        estado = c.getString(COLUNA_ESTADO);

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


}
