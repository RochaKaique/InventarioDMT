package com.tivit.inventariodmt.dataconsistency.provider;

import android.content.UriMatcher;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by kaique.rocha on 16/11/2016.
 */

public class DepartamentoContract {
    public final static String AUTHORITY = "com.tivit.inventariodmt";
    public static final String DEPARTAMENTO = "inv_FS_Departamento";
    public final static String SINGLE_MIME = "vnd.android.cursor.item/vnd." + AUTHORITY + DEPARTAMENTO;
    public final static String MULTIPLE_MIME = "vnd.android.cursor.dir/vnd." + AUTHORITY + DEPARTAMENTO;
    public final static Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/" + DEPARTAMENTO);
    public static final UriMatcher uriMatcher;

    public static final int ALLROWS = 1;

    public static final int SINGLE_ROW = 2;

    static {
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(AUTHORITY, DEPARTAMENTO, ALLROWS);
        uriMatcher.addURI(AUTHORITY, DEPARTAMENTO + "/#", SINGLE_ROW);
    }

    public static class Colunas implements BaseColumns {
        public final static String _ID = "inv_FS_Dep_Id_Departamento";
        public final static String NOME = "inv_FS_Dep_Nome_Departamento";
        public final static String DESCRICAO = "inv_FS_Dep_Descricao";
        public final static String ORGANIZACAO = "inv_FS_Dep_Id_Organizacao";

    }

}
