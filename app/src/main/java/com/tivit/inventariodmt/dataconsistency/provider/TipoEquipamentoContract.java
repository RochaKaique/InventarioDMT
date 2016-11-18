package com.tivit.inventariodmt.dataconsistency.provider;

import android.content.UriMatcher;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by kaique.rocha on 17/11/2016.
 */

public class TipoEquipamentoContract {

    public final static String AUTHORITY = "com.tivit.inventariodmt";
    public static final String TP_EQUIPAMENTO = "inv_FS_Tipo_Equipamento";
    public final static String SINGLE_MIME = "vnd.android.cursor.item/vnd." + AUTHORITY + TP_EQUIPAMENTO;
    public final static String MULTIPLE_MIME = "vnd.android.cursor.dir/vnd." + AUTHORITY + TP_EQUIPAMENTO;
    public final static Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/" + TP_EQUIPAMENTO);
    public static final UriMatcher uriMatcher;

    public static final int ALLROWS = 1;

    public static final int SINGLE_ROW = 2;

    static {
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(AUTHORITY, TP_EQUIPAMENTO, ALLROWS);
        uriMatcher.addURI(AUTHORITY, TP_EQUIPAMENTO + "/#", SINGLE_ROW);
    }

    public static class Colunas implements BaseColumns {
        public final static String _ID = "inv_FS_TP_Id_Tipo_Equipamento";
        public final static String NOME = "inv_FS_TP_Nome_Equipamento";
        public final static String KIT_INSTALACAO = "inv_FS_TP_Kit_Instalacao";
        public final static String DESCRICAO = "inv_FS_TP_Kit_Instalacao";
    }

}
