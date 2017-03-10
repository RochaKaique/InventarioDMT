package com.tivit.inventariodmt.dataconsistency.provider;

import android.content.UriMatcher;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by kaique.rocha on 09/03/2017.
 */

public class OrganizacaoContract {

    public final static String AUTHORITY = "com.tivit.inventariodmt";
    public static final String ORGANIZACAO = "inv_FS_Organizacao";
    public final static String SINGLE_MIME = "vnd.android.cursor.item/vnd." + AUTHORITY + ORGANIZACAO;
    public final static String MULTIPLE_MIME = "vnd.android.cursor.dir/vnd." + AUTHORITY + ORGANIZACAO;
    public final static Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/" + ORGANIZACAO);
    public static final UriMatcher uriMatcher;

    public static final int ALLROWS = 1;

    public static final int SINGLE_ROW = 2;

    static {
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(AUTHORITY, ORGANIZACAO, ALLROWS);
        uriMatcher.addURI(AUTHORITY, ORGANIZACAO + "/#", SINGLE_ROW);
    }

    public static class Colunas implements BaseColumns {
        public final static String _ID = "inv_FS_Org_Id_Organizacao";
        public final static String NOME = "inv_FS_Org_Nome_Organizacao";
        public final static String CNPJ = "inv_FS_Org_CNPJ";
        public final static String DESCRICAO = "inv_FS_Org_Descricao";
    }
}
