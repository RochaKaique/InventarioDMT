package com.tivit.inventariodmt.dataconsistency.provider;

import android.content.UriMatcher;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by kaique.rocha on 17/11/2016.
 */

public class StatusContract {
    public final static String AUTHORITY = "com.tivit.inventariodmt";
    public static final String STATUS = "inv_FS_Status";
    public final static String SINGLE_MIME = "vnd.android.cursor.item/vnd." + AUTHORITY + STATUS;
    public final static String MULTIPLE_MIME = "vnd.android.cursor.dir/vnd." + AUTHORITY + STATUS;
    public final static Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/" + STATUS);
    public static final UriMatcher uriMatcher;

    public static final int ALLROWS = 1;

    public static final int SINGLE_ROW = 2;

    static {
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(AUTHORITY, STATUS, ALLROWS);
        uriMatcher.addURI(AUTHORITY, STATUS + "/#", SINGLE_ROW);
    }

    public static class Colunas implements BaseColumns {
        public final static String _ID = "inv_FS_St_id_Status";
        public final static String NOME = "inv_FS_St_Nome_Status";
        public final static String DESCRICAO = "inv_FS_St_Descricao";
    }
}
