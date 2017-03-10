package com.tivit.inventariodmt.dataconsistency.provider;

import android.content.UriMatcher;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by kaique.rocha on 08/03/2017.
 */

public class UsuarioFinalContract {

    public final static String AUTHORITY = "com.tivit.inventariodmt";
    public static final String USUARIO_FINAL = "inv_FS_Usuario_Final";
    public final static String SINGLE_MIME = "vnd.android.cursor.item/vnd." + AUTHORITY + USUARIO_FINAL;
    public final static String MULTIPLE_MIME = "vnd.android.cursor.dir/vnd." + AUTHORITY + USUARIO_FINAL;
    public final static Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/" + USUARIO_FINAL);
    public static final UriMatcher uriMatcher;

    public static final int ALLROWS = 1;

    public static final int SINGLE_ROW = 2;

    static {
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(AUTHORITY, USUARIO_FINAL, ALLROWS);
        uriMatcher.addURI(AUTHORITY, USUARIO_FINAL + "/#", SINGLE_ROW);
    }

    public static class Colunas implements BaseColumns {
        public final static String _ID = "inv_FS_usf_id_usuario";
        public final static String NOME = "inv_FS_usf_Nome";
        public final static String ID_CORPORATIVO = "inv_FS_usf_Id_Corporativo";
        public final static String STATUS = "inv_FS_usf_Status";
        public final static String CARGO = "inv_FS_usf_Cargo";
        public final static String RAMAL = "inv_FS_usf_Ramal";
        public final static String EMAIL = "inv_FS_usf_Email";
        public final static String CELULAR = "inv_FS_usf_Celular";
        public final static String LOGIN = "inv_FS_usf_Login";
        public final static String DATA_ADMISSAO = "inv_FS_usf_Data_admissao";
        public final static String CPF = "inv_FS_usf_CPF";
        public final static String RG = "inv_FS_usf_RG";
        public final static String DATA_NASCIMENTO = "inv_FS_usf_Data_Nascimento";
        public final static String GESTOR = "inv_FS_usf_Nome_Gestor";
        public final static String RAMAL_GESTOR = "inv_FS_usf_Ramal_Gestor";
        public final static String OBSERVACAO = "inv_FS_usf_Observacao";
        public final static String ID_ORGANIZACAO = "inv_fs_usf_Id_Organizacao";
        public final static String ID_DEPARTAMENTO = "inv_fs_usf_Id_Departamento";
        public final static String ID_LOCALIDADE = "inv_fs_usf_Id_Localidade";
        public final static String ID_CENTRO_CUSTO = "inv_FS_usf_Id_Centro_Custo";
    }
}
