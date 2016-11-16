package com.tivit.inventariodmt.dataconsistency.utils;

/**
 * Created by kaique.rocha on 25/10/2016.
 */

public class Constantes {
    /**
     * Puerto que utilizas para la conexión.
     * Dejalo en blanco si no has configurado esta característica.
     */
    private static final String PORTA = ":8084";

    /**
     * Dirección IP de genymotion o AVD
     */
    private static final String IP = "http://192.168.137.1";

    /**
     * URLs del Web Service
     */
    public static final String GET_URL = IP + PORTA + "/InventarioDMT_Ws/webresources/Equipamento/listarPorLocalidade/";
    public static final String INSERT_URL = IP + PORTA + "/InventarioDMT_Ws/webresources/Equipamento/cadastrar_equipamento";

    public static final String GET_LOCALIDADE = IP + PORTA + "/InventarioDMT_Ws/webresources/localidade/listar";

    /**
     * Campos de las respuestas Json
     */
    public static final String ID_GASTO = "inv_fs_ic_Id_IC";
    public static final String ESTADO = "inv_fs_ic_EstadoSinc";
    public static final String EQUIPAMENTOS = "equipamentos";
    public static final String MENSAJE = "mensagem";

    public static final String ESTADO_LOCALIDADE = "inv_fs_Loc_EstadoSinc";


    public static final String SUCCESS = "1";
    public static final String FAILED = "2";

    /**
     * Tipo de cuenta para la sincronización
     */
    public static final String ACCOUNT_TYPE = "com.tivit.inventariodmt.account";
}