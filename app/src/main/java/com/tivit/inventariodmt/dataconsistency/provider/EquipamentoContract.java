package com.tivit.inventariodmt.dataconsistency.provider;

import android.content.UriMatcher;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by kaique.rocha on 25/10/2016.
 */

public class EquipamentoContract {

    public final static String AUTHORITY = "com.tivit.inventariodmt";
    public static final String EQUIPAMENTO = "Inv_FS_Item_config";
    public final static String SINGLE_MIME = "vnd.android.cursor.item/vnd." + AUTHORITY + EQUIPAMENTO;
    public final static String MULTIPLE_MIME = "vnd.android.cursor.dir/vnd." + AUTHORITY + EQUIPAMENTO;
    public final static Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/" + EQUIPAMENTO);
    public static final UriMatcher uriMatcher;



    public static final int ALLROWS = 1;

    public static final int SINGLE_ROW = 2;

    static {
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(AUTHORITY, EQUIPAMENTO, ALLROWS);
        uriMatcher.addURI(AUTHORITY, EQUIPAMENTO + "/#", SINGLE_ROW);
    }

    // Valores para la columna ESTADO
    public static final int ESTADO_OK = 0;
    public static final int ESTADO_SYNC = 1;

    public static class Columnas implements BaseColumns {

        public final static String N_SERIE =  "inv_fs_ic_numero_serie";
        public final static String PATRIMONIO = "inv_fs_ic_Patrimonio";
        public final static String RFID = "inv_fs_ic_RFID";
        public final static String TIPO = "tipo_equipamento_id";
        public final static String STATUS = "status_id";
        public final static String DEPARTAMENTO = "departamento_id";
        public final static String LOCALIDADE = "localidade_id";
        public static final String DATA = "inv_fs_ic_Data_Criacao";

        public static final String ESTADO = "estado";
        public static final String ID_REMOTA = "idRemoto";
        public final static String INSERT_PENDING = "insert_pending";

    }
}
