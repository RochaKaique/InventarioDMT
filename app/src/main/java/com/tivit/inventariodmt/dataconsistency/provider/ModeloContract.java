package com.tivit.inventariodmt.dataconsistency.provider;

import android.content.UriMatcher;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by kaique.rocha on 17/11/2016.
 */

public class ModeloContract {

    public final static String AUTHORITY = "com.tivit.inventariodmt";
    public static final String MODELO = "inv_FS_Modelo";
    public final static String SINGLE_MIME = "vnd.android.cursor.item/vnd." + AUTHORITY + MODELO;
    public final static String MULTIPLE_MIME = "vnd.android.cursor.dir/vnd." + AUTHORITY + MODELO;
    public final static Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/" + MODELO);
    public static final UriMatcher uriMatcher;

    public static final int ALLROWS = 1;

    public static final int SINGLE_ROW = 2;

    static {
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(AUTHORITY, MODELO, ALLROWS);
        uriMatcher.addURI(AUTHORITY, MODELO + "/#", SINGLE_ROW);
    }

    public static class Colunas implements BaseColumns {
        public final static String _ID = "inv_FS_Mod_Id_Modelo";
        public final static String NOME = "inv_FS_Mod_Nome_Modelo";
        public final static String DESCRICAO = "inv_FS_Mod_Descricao";
        public final static String ANO = "Inv_FS_Mod_Ano";
        public final static String FABRICANTE = "inv_FS_Mod_Id_Fabricante";
    }
}
