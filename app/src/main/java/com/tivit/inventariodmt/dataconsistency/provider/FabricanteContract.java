package com.tivit.inventariodmt.dataconsistency.provider;

import android.content.UriMatcher;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by kaique.rocha on 16/11/2016.
 */

public class FabricanteContract {

    public final static String AUTHORITY = "com.tivit.inventariodmt";
    public static final String FABRICANTE = "inv_FS_Fabricante";
    public final static String SINGLE_MIME = "vnd.android.cursor.item/vnd." + AUTHORITY + FABRICANTE;
    public final static String MULTIPLE_MIME = "vnd.android.cursor.dir/vnd." + AUTHORITY + FABRICANTE;
    public final static Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/" + FABRICANTE);
    public static final UriMatcher uriMatcher;

    public static final int ALLROWS = 1;

    public static final int SINGLE_ROW = 2;

    static {
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(AUTHORITY, FABRICANTE, ALLROWS);
        uriMatcher.addURI(AUTHORITY, FABRICANTE + "/#", SINGLE_ROW);
    }

    public static class Colunas implements BaseColumns {

        public final static String _ID = "inv_FS_Fab_Id_Fabricante";
        public final static String NOME = "inv_FS_Fab_Nome_Fabricante";
        public final static String DESCRICAO = "inv_FS_Fab_Descricao";


    }
}
