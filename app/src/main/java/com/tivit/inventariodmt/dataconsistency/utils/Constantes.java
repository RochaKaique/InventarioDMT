package com.tivit.inventariodmt.dataconsistency.utils;

import com.tivit.inventariodmt.DownloadActivity;
import com.tivit.inventariodmt.MenuActivity;

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
    private static final String IP = "http://192.168.49.213";

    /**
     * URLs del Web Service
     */

    public static final String GET_URL = IP + PORTA + "/InventarioDMT_Ws/webresources/equipamento/listarPorLocalidade/";
    public static final String INSERT_URL = IP + PORTA + "/InventarioDMT_Ws/webresources/equipamento/cadastrar_equipamento/"+ MenuActivity.login;

    public static final String GET_LOCALIDADE = IP + PORTA + "/InventarioDMT_Ws/webresources/localidade/listar/"+ MenuActivity.login + "/";
    public static final String GET_DEPARTAMENTO = IP + PORTA +"/InventarioDMT_Ws/webresources/departamento/listar/"+ MenuActivity.login + "/";
    public static final String GET_FABRICANTE = IP + PORTA +"/InventarioDMT_Ws/webresources/fabricante/listar/"+ MenuActivity.login + "/";
    public static final String GET_MODELO = IP + PORTA +"/InventarioDMT_Ws/webresources/modelo/listar/"+ MenuActivity.login + "/";
    public static final String GET_STATUS = IP + PORTA +"/InventarioDMT_Ws/webresources/status/listar/"+ MenuActivity.login + "/";
    public static final String GET_TP_EQUIPAMENTO = IP + PORTA +"/InventarioDMT_Ws/webresources/tipoequipamento/listar/"+ MenuActivity.login + "/";
    public static final String GET_USUARIO = IP + PORTA +"/InventarioDMT_Ws/webresources/usuario/listar/"+ MenuActivity.login + "/";

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