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

        public final static String _ID = "inv_fs_ic_Id_IC";
        public final static String N_SERIE =  "inv_fs_ic_numero_serie";
        public final static String PATRIMONIO = "inv_fs_ic_Patrimonio";
        public final static String RFID = "inv_fs_ic_RFID";
        public final static String TIPO = "inv_fs_ic_Id_Tipo_Equipamento";
        public final static String STATUS = "inv_fs_ic_Id_Status";
        public final static String DEPARTAMENTO = "inv_fs_ic_Id_Departamento";
        public final static String LOCALIDADE = "inv_fs_ic_Id_Localidade";
        public static final String DATA = "inv_fs_ic_Data_Criacao";
        public static final String FABRICANTE = "inv_fs_ic_Id_Fabricante";
        public static final String MODELO = "inv_fs_ic_Id_Modelo";
        public static final String USUARIO_FINAL = "inv_fs_ic_Id_Usuario_Final";
        public static final String ESTADO = "inv_fs_ic_EstadoSinc";
        public static final String ID_REMOTA = "inv_fs_ic_idRemoto";
        public final static String INSERT_PENDING = "inv_fs_ic_insert_pending";
        public static final String USUARIO_CADASTRO = "inv_fs_ic_Id_Usuario_Criacao";

    }
}
