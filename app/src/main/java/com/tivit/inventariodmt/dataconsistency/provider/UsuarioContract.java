package com.tivit.inventariodmt.dataconsistency.provider;

import android.content.UriMatcher;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by kaique.rocha on 22/11/2016.
 */

public class UsuarioContract {

    public final static String AUTHORITY = "com.tivit.inventariodmt";
    public static final String USUARIO = "inv_Usuario";
    public final static String SINGLE_MIME = "vnd.android.cursor.item/vnd." + AUTHORITY + USUARIO;
    public final static String MULTIPLE_MIME = "vnd.android.cursor.dir/vnd." + AUTHORITY + USUARIO;
    public final static Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/" + USUARIO);
    public static final UriMatcher uriMatcher;

    public static final int ALLROWS = 1;

    public static final int SINGLE_ROW = 2;

    static {
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(AUTHORITY, USUARIO, ALLROWS);
        uriMatcher.addURI(AUTHORITY, USUARIO + "/#", SINGLE_ROW);
    }

    public static class Colunas implements BaseColumns {
        public final static String _ID = "inv_us_id_Usuario";
        public final static String NOME = "inv_us_Nome";
        public final static String LOGIN = "inv_us_Login";
        public final static String SENHA = "inv_us_Senha";
        public final static String PERFIL = "inv_us_Perfil";
        public final static String EMAIL = "inv_us_Email";
        public final static String ATIVO = "inv_us_Ativo";
    }
}


