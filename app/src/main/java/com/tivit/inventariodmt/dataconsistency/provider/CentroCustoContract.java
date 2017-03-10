package com.tivit.inventariodmt.dataconsistency.provider;

import android.content.UriMatcher;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by kaique.rocha on 09/03/2017.
 */

public class CentroCustoContract {
    public final static String AUTHORITY = "com.tivit.inventariodmt";
    public static final String CENTRO_CUSTO = "inv_FS_Centro_Custo";
    public final static String SINGLE_MIME = "vnd.android.cursor.item/vnd." + AUTHORITY + CENTRO_CUSTO;
    public final static String MULTIPLE_MIME = "vnd.android.cursor.dir/vnd." + AUTHORITY + CENTRO_CUSTO;
    public final static Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/" + CENTRO_CUSTO);
    public static final UriMatcher uriMatcher;

    public static final int ALLROWS = 1;

    public static final int SINGLE_ROW = 2;

    static {
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(AUTHORITY, CENTRO_CUSTO, ALLROWS);
        uriMatcher.addURI(AUTHORITY, CENTRO_CUSTO + "/#", SINGLE_ROW);
    }
    public static class Colunas implements BaseColumns {
        public final static String _ID = "inv_FS_CC_Id_Centro_Custo";
        public final static String NOME = "inv_FS_CC_Nome_Centro_Custo";
        public final static String DESCRICAO = "inv_FS_CC_Descricao";
        public final static String ID_DEPARTAMENTO = "inv_fs_CC_Id_Departamento";
    }
}

