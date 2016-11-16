package com.tivit.inventariodmt.dataconsistency.provider;

import android.content.UriMatcher;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by kaique.rocha on 10/11/2016.
 */

public class LocalidadeContract {

    public final static String AUTHORITY = "com.tivit.inventariodmt";
    public static final String LOCALIDADE = "inv_FS_Localidade";
    public final static String SINGLE_MIME = "vnd.android.cursor.item/vnd." + AUTHORITY + LOCALIDADE;
    public final static String MULTIPLE_MIME = "vnd.android.cursor.dir/vnd." + AUTHORITY + LOCALIDADE;
    public final static Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/" + LOCALIDADE);
    public static final UriMatcher uriMatcher;



    public static final int ALLROWS = 1;

    public static final int SINGLE_ROW = 2;

    static {
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(AUTHORITY, LOCALIDADE, ALLROWS);
        uriMatcher.addURI(AUTHORITY, LOCALIDADE + "/#", SINGLE_ROW);
    }

    // Valores para la columna ESTADO
    public static final int ESTADO_OK = 0;
    public static final int ESTADO_SYNC = 1;

    public static class Colunas implements BaseColumns {

        public final static String _ID = "inv_FS_Loc_Id_Localidade";
        public final static String ENDERECO = "inv_FS_Loc_Endereco";
        public final static String DESCRICAO = "inv_FS_Loc_Descricao";
        public final static String CEP = "inv_FS_Loc_cep";
        public final static String CIDADE = "inv_FS_Loc_cidade";
        public final static String ESTADO = "inv_FS_Loc_estado";
        public final static String NOME = "inv_FS_Loc_nome_localidade";

        public static final String ESTADO_SINC = "inv_fs_Loc_EstadoSinc";
        public static final String ID_REMOTA = "inv_fs_Loc_idRemoto";
        public final static String INSERT_PENDING = "inv_fs_Loc_insert_pending";


    }
}
