package com.tivit.inventariodmt.dataconsistency.utils;

/**
 * Created by kaique.rocha on 25/10/2016.
 */

public class Constantes {
    /**
     * Puerto que utilizas para la conexión.
     * Dejalo en blanco si no has configurado esta característica.
     */
    private static final String PUERTO_HOST = ":8084";

    /**
     * Dirección IP de genymotion o AVD
     */
    private static final String IP = "http://192.168.49.213";

    /**
     * URLs del Web Service
     */
    public static final String GET_URL = IP + PUERTO_HOST + "/InventarioDMT_Ws/webresources/Equipamento/listar";
    public static final String INSERT_URL = IP + PUERTO_HOST + "/InventarioDMT_Ws/webresources/Equipamento/cadastrar_equipamento";

    /**
     * Campos de las respuestas Json
     */
    public static final String ID_GASTO = "_id";
    public static final String ESTADO = "estado";
    public static final String EQUIPAMENTOS = "equipamentos";
    public static final String MENSAJE = "mensagem";

    /**
     * Códigos del campo {@link ESTADO}
     */
    public static final String SUCCESS = "1";
    public static final String FAILED = "2";

    /**
     * Tipo de cuenta para la sincronización
     */
    public static final String ACCOUNT_TYPE = "com.tivit.inventariodmt.account";
}