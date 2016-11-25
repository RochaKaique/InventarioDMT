package com.tivit.inventariodmt.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.tivit.inventariodmt.dataconsistency.utils.Criptografia;

/**
 * Created by alex.almeida on 03/08/2016.
 */
public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String BANCO_DADOS = "inventariator.db";
    private static int VERSAO = 1;

    public DatabaseHelper(Context context,
                          String name,
                          SQLiteDatabase.CursorFactory factory,
                          int version) {
        super(context, name, factory, version);
    }

    public DatabaseHelper(Context context) {
        super(context, BANCO_DADOS, null, VERSAO);
    }


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
//        sqLiteDatabase.execSQL("CREATE TABLE inv_FS_Status(_id INTEGER PRIMARY KEY AUTOINCREMENT, inv_FS_St_Nome_Status TEXT, inv_FS_St_Descricao TEXT);");
//        sqLiteDatabase.execSQL("CREATE TABLE inv_FS_Fabricante(_id INTEGER PRIMARY KEY AUTOINCREMENT, inv_FS_Fab_Nome_Fabricante TEXT, inv_FS_Fab_Descricao TEXT);");
//        sqLiteDatabase.execSQL("CREATE TABLE inv_FS_Sub_Status(_id INTEGER PRIMARY KEY AUTOINCREMENT, inv_FS_Subst_Nome_Sub_Status TEXT, inv_FS_Subst_Descricao TEXT, status_id INTEGER," +
//                " FOREIGN KEY(status_id) REFERENCES inv_FS_Status(_id));");
//        sqLiteDatabase.execSQL("CREATE TABLE inv_FS_Fornecedor(_id INTEGER PRIMARY KEY AUTOINCREMENT, inv_FS_For_Nome_Fornecedor, inv_FS_For_Descricao TEXT, inv_FS_For_Contato TEXT, inv_FS_For_CNPJ TEXT, inv_FS_For_Email TEXT, inv_FS_For_Telefone TEXT);");
//        sqLiteDatabase.execSQL("CREATE TABLE inv_FS_Modelo(_id INTEGER PRIMARY KEY AUTOINCREMENT, inv_FS_Mod_Nome_Modelo TEXT, inv_FS_Mod_Descricao TEXT, Inv_FS_Mod_Ano INTEGER, fabricante_id INTEGER," +
//                " FOREIGN KEY(fabricante_id) REFERENCES inv_FS_Fabricante(_id));");
//        sqLiteDatabase.execSQL("CREATE TABLE inv_FS_Tipo_Equipamento(_id INTEGER PRIMARY KEY AUTOINCREMENT, inv_FS_TP_Nome_Equipamento TEXT, inv_FS_TP_Kit_Instalacao INTEGER, inv_FS_TP_Descricao TEXT);");
//        sqLiteDatabase.execSQL("CREATE TABLE inv_FS_Tipo_Aquisicao(_id INTEGER PRIMARY KEY AUTOINCREMENT, inv_FS_TA_Nome_Aquisicao TEXT, inv_FS_TA_Descricao TEXT);");
//        sqLiteDatabase.execSQL("CREATE TABLE inv_FS_Lote_Compra(_id INTEGER PRIMARY KEY AUTOINCREMENT, inv_FS_Lt_Nome_Lote TEXT, inv_FS_Lt_Ano_Lote INTEGER, inv_FS_Lt_Sequencia_Lote INTEGER);");
//            sqLiteDatabase.execSQL("CREATE TABLE inv_FS_Estado(_id INTEGER PRIMARY KEY AUTOINCREMENT, inv_FS_Est_Nome TEXT);");
//        sqLiteDatabase.execSQL("CREATE TABLE inv_FS_Cidade(_id INTEGER PRIMARY KEY AUTOINCREMENT, inv_FS_Cid_Nome TEXT, estado_id INTEGER, FOREIGN KEY (estado_id) REFERENCES inv_FS_Estado(_id));");
//        sqLiteDatabase.execSQL("CREATE TABLE inv_FS_Localidade(_id INTEGER PRIMARY KEY AUTOINCREMENT, inv_FS_Loc_Endereco TEXT, inv_FS_Loc_Descricao TEXT, cidade_id INTEGER, FOREIGN KEY(cidade_id) REFERENCES inv_FS_Cidade(_id));");
//        sqLiteDatabase.execSQL("CREATE TABLE inv_FS_Organizacao(_id INTEGER PRIMARY KEY AUTOINCREMENT, inv_FS_Org_Nome_Organizacao TEXT, inv_FS_Org_CNPJ TEXT, inv_FS_Org_Descricao TEXT);");
//        sqLiteDatabase.execSQL("CREATE TABLE inv_FS_Departamento(_id INTEGER PRIMARY KEY AUTOINCREMENT, inv_FS_Dep_Nome_Departamento TEXT, inv_FS_Dep_Descricao TEXT, organizacao_id INTEGER," +
//                " FOREIGN KEY(organizacao_id) REFERENCES inv_FS_Organizacao(_id));");
//        sqLiteDatabase.execSQL("CREATE TABLE inv_FS_Centro_Custo(_id INTEGER PRIMARY KEY AUTOINCREMENT, inv_FS_CC_Nome_Centro_Custo TEXT, inv_FS_CC_Descricao TEXT, departamento_id INTEGER," +
//                " FOREIGN KEY (departamento_id) REFERENCES inv_FS_Departamento(_id));");
//        sqLiteDatabase.execSQL("CREATE TABLE inv_Perfil_Usuario(_id INTEGER PRIMARY KEY AUTOINCREMENT, inv_PU_Nome_Perfil_Usuario TEXT, inv_PU_Descricao TEXT);");
//        sqLiteDatabase.execSQL("CREATE TABLE inv_FS_Usuario_Final(_id INTEGER PRIMARY KEY AUTOINCREMENT, inv_FS_usf_Nome TEXT, inv_FS_usf_Id_Corporativo TEXT, inv_FS_usf_Status INTEGER, inv_FS_usf_Cargo TEXT," +
//                " inv_FS_usf_Ramal TEXT, inv_FS_usf_Email TEXT, inv_FS_usf_Celular TEXT, inv_FS_usf_Login TEXT, inv_FS_usf_Data_admissao DATE, inv_FS_usf_CPF TEXT, inv_FS_usf_RG TEXT," +
//                " inv_FS_usf_Data_Nascimento DATE, inv_FS_usf_Nome_Gestor TEXT, inv_FS_usf_Ramal_Gestor TEXT, inv_FS_usf_Observacao TEXT, organizacao_id INTEGER, departamento_id INTEGER, localidade_id INTEGER," +
//                " centro_custo_id INTEGER, FOREIGN KEY(organizacao_id) REFERENCES inv_FS_Organizacao(_id), FOREIGN KEY(departamento_id) REFERENCES inv_FS_Departamento(_id)," +
//                " FOREIGN KEY(localidade_id) REFERENCES inv_FS_Localidade(_id), FOREIGN KEY(centro_custo_id) REFERENCES inv_FS_Centro_Custo(_id));");
//        sqLiteDatabase.execSQL("CREATE TABLE inv_FS_Kit_Instalacao(_id INTEGER PRIMARY KEY AUTOINCREMENT, inv_FS_KI_Id_Nome_Kit_Instalacao TEXT, inv_FS_KI_Id_Descricao TEXT, tipo_equipamento_id INTEGER, FOREIGN KEY(tipo_equipamento_id) REFERENCES inv_FS_Tipo_Equipamento(_id));");
//        sqLiteDatabase.execSQL("CREATE TABLE inv_FS_Termo_Responsabilidade(_id INTEGER PRIMARY KEY AUTOINCREMENT, inv_FS_TR_DataHora_Emissao DATE, inv_FS_TR_DataHora_Assinatura DATE, inv_FS_TR_Validade_Termo INTEGER, inv_FS_TR_Termo_Assinado INTEGER, inv_FS_TR_Codigo_Confirmação TEXT," +
//                "  usuario_id INTEGER, FOREIGN KEY(usuario_id) REFERENCES inv_FS_Usuario_Final(_id));");
//        sqLiteDatabase.execSQL("CREATE TABLE Inv_FS_Item_config(_id INTEGER PRIMARY KEY AUTOINCREMENT, inv_fs_ic_numero_serie TEXT, inv_fs_ic_Nome_IC TEXT, inv_fs_ic_RFID TEXT, inv_fs_ic_Nota_fiscal TEXT," +
//                " inv_fs_ic_Patrimonio TEXT, inv_fs_ic_Data_compra DATE, inv_fs_ic_Data_Fim_Garantia DATE, inv_fs_ic_contrato TEXT, inv_fs_ic_Data_Fim_Leasing DATE, inv_fs_ic_codigo_anterior TEXT, inv_fs_ic_mac_adress TEXT," +
//                " inv_fs_ic_IP TEXT, inv_fs_ic_Data_Criacao DATE, inv_fs_ic_Data_Ultima_Atualizacao DATE, inv_fs_ic_codigo_descarte TEXT, inv_fs_ic_Rede_Conectada INTEGER, inv_fs_ic_Ult_Incidente TEXT," +
//                " inv_fs_ic_Incidente_Externo TEXT, status_id INTEGER, substatus_id INTEGER, fabricante_id INTEGER, modelo_id INTEGER, fornecedor_id INTEGER, tipo_equipamento_id INTEGER," +
//                " tipo_aquisicao_id INTEGER, localidade_id INTEGER, termo_id INTEGER, organizacao_id INTEGER, usuario_id INTEGER, lote_compra_id INTEGER, usuario_criacao_id INTEGER, departamento_id INTEGER," +
//                " usuario_ult_atualizacao_id INTEGER, kit_instalacao_id INTEGER, estado INTEGER NOT NULL DEFAULT 0, idRemoto TEXT, insert_pending INTEGER NOT NULL DEFAULT 0, FOREIGN KEY(status_id) REFERENCES inv_FS_Status(_id), FOREIGN KEY(substatus_id) REFERENCES inv_FS_Sub_Status(_id)," +
//                " FOREIGN KEY(fabricante_id) REFERENCES inv_FS_Fabricante(_id), FOREIGN KEY(modelo_id) REFERENCES inv_FS_Modelo(_id), FOREIGN KEY(fornecedor_id) REFERENCES inv_FS_Fornecedor(_id)," +
//                " FOREIGN KEY(tipo_equipamento_id) REFERENCES inv_FS_Tipo_Equipamento(_id), FOREIGN KEY(tipo_aquisicao_id) REFERENCES inv_FS_Tipo_Aquisicao(_id), FOREIGN KEY(localidade_id) REFERENCES inv_FS_Localidade(_id)," +
//                " FOREIGN KEY(organizacao_id) REFERENCES inv_FS_Organizacao(_id), FOREIGN KEY(usuario_id) REFERENCES inv_FS_Usuario_Final(_id), FOREIGN KEY(termo_id) REFERENCES inv_FS_Termo_Responsabilidade(_id)," +
//                " FOREIGN KEY (departamento_id) REFERENCES inv_FS_Departamento(_id));");
//
//        //VERIFICAÇÃO DE EQUIPAMENTOS QUE PASSARAM PELA VERIFICAÇÃO DO RFID EM GRUPO.
//        sqLiteDatabase.execSQL("CREATE TABLE verificacao(_id INTEGER PRIMARY KEY AUTOINCREMENT, rfid TEXT, data_verificacao DATE);");
//
//        //INSERÇÕES DE TESTE NO BANCO
//        sqLiteDatabase.execSQL("INSERT INTO inv_FS_Tipo_Equipamento (inv_FS_TP_Nome_Equipamento) VALUES ('Desktop');");
//        sqLiteDatabase.execSQL("INSERT INTO inv_FS_Tipo_Equipamento (inv_FS_TP_Nome_Equipamento) VALUES ('Notebook');");
//
//        sqLiteDatabase.execSQL("INSERT INTO inv_FS_Departamento (inv_FS_Dep_Nome_Departamento) VALUES ('TI');");
//        sqLiteDatabase.execSQL("INSERT INTO inv_FS_Departamento (inv_FS_Dep_Nome_Departamento) VALUES ('Fiscal');");
//
//        sqLiteDatabase.execSQL("INSERT INTO inv_FS_Status (inv_FS_St_Nome_Status) VALUES ('Instalado');");
//        sqLiteDatabase.execSQL("INSERT INTO inv_FS_Status (inv_FS_St_Nome_Status) VALUES ('Manutenção');");
//
//
//        sqLiteDatabase.execSQL("INSERT INTO inv_FS_Estado(inv_FS_Est_Nome) VALUES ('SP')");
//
//        sqLiteDatabase.execSQL("INSERT INTO inv_FS_Cidade(inv_FS_Cid_Nome, estado_id) VALUES ('São Paulo', 1)");
//
//        sqLiteDatabase.execSQL("INSERT INTO inv_FS_Localidade(inv_FS_Loc_Descricao, cidade_id) VALUES ('CENESP', 1)");
//        sqLiteDatabase.execSQL("INSERT INTO inv_FS_Localidade(inv_FS_Loc_Descricao, cidade_id) VALUES ('Ipiranga', 1)");
//        sqLiteDatabase.execSQL("INSERT INTO inv_FS_Localidade(inv_FS_Loc_Descricao, cidade_id) VALUES ('Transamerica', 1)");
//
//        /*sqLiteDatabase.execSQL("INSERT INTO inv_FS_Estado (inv_FS_Est_Nome) VALUES ('São Paulo');");
//        sqLiteDatabase.execSQL("INSERT INTO inv_FS_Estado (inv_FS_Est_Nome) VALUES ('Rio de Janeiro');");
//
//        sqLiteDatabase.execSQL("INSERT INTO inv_FS_Cidade (inv_FS_Cid_Nome, estado_id) VALUES ('São Paulo', 1);");
//        sqLiteDatabase.execSQL("INSERT INTO inv_FS_Cidade (inv_FS_Cid_Nome, estado_id) VALUES ('São Bernardo', 1);");
//        sqLiteDatabase.execSQL("INSERT INTO inv_FS_Cidade (inv_FS_Cid_Nome, estado_id) VALUES ('Rio de Janeiro', 2);");
//        sqLiteDatabase.execSQL("INSERT INTO inv_FS_Cidade (inv_FS_Cid_Nome, estado_id) VALUES ('Volta Redonda', 2);");
//
//        sqLiteDatabase.execSQL("INSERT INTO inv_FS_Localidade (inv_FS_Loc_Endereco TEXT, cidade_id) VALUES ('Rua Um', 1);");
//        sqLiteDatabase.execSQL("INSERT INTO inv_FS_Localidade (inv_FS_Loc_Endereco TEXT, cidade_id) VALUES ('Rua Dois', 2);");
//        sqLiteDatabase.execSQL("INSERT INTO inv_FS_Localidade (inv_FS_Loc_Endereco TEXT, cidade_id) VALUES ('Rua Três', 3);");
//        sqLiteDatabase.execSQL("INSERT INTO inv_FS_Localidade (inv_FS_Loc_Endereco TEXT, cidade_id) VALUES ('Rua Quatro', 4);");
//
//        sqLiteDatabase.execSQL("INSERT INTO inv_FS_Fabricante (inv_FS_Fab_Nome_Fabricante) VALUES ('DELL');");
//        sqLiteDatabase.execSQL("INSERT INTO inv_FS_Fabricante (inv_FS_Fab_Nome_Fabricante) VALUES ('HP');");
//
//        sqLiteDatabase.execSQL("INSERT INTO inv_FS_Modelo (inv_FS_Mod_Nome_Modelo, fabricante_id) VALUES ('Vostro', 1);");
//        sqLiteDatabase.execSQL("INSERT INTO inv_FS_Modelo (inv_FS_Mod_Nome_Modelo, fabricante_id) VALUES ('Optiplex', 1);");
//        sqLiteDatabase.execSQL("INSERT INTO inv_FS_Modelo (inv_FS_Mod_Nome_Modelo, fabricante_id) VALUES ('Pavillion', 2);");
//        sqLiteDatabase.execSQL("INSERT INTO inv_FS_Modelo (inv_FS_Mod_Nome_Modelo, fabricante_id) VALUES ('Complex', 2);");*/


        sqLiteDatabase.execSQL("CREATE TABLE inv_FS_Status( " +
                "inv_FS_St_id_Status INTEGER PRIMARY KEY," +
                "inv_FS_St_Nome_Status TEXT," +
                "inv_FS_St_Descricao TEXT);");

        sqLiteDatabase.execSQL("CREATE TABLE inv_FS_Fabricante(" +
                "inv_FS_Fab_Id_Fabricante INTEGER PRIMARY KEY, " +
                "inv_FS_Fab_Nome_Fabricante TEXT, " +
                "inv_FS_Fab_Descricao TEXT);");
        
        sqLiteDatabase.execSQL("CREATE TABLE inv_FS_Sub_Status(" +
                "inv_FS_Subst_id_Sub_Status INTEGER PRIMARY KEY, " +
                "inv_FS_Subst_Nome_Sub_Status TEXT, " +
                "inv_FS_Subst_Descricao TEXT, " +
                "inv_fs_ic_Id_Status INTEGER, " +
                "FOREIGN KEY(inv_fs_ic_Id_Status) REFERENCES inv_FS_Status(inv_FS_St_id_Status));");

        sqLiteDatabase.execSQL("CREATE TABLE inv_FS_Fornecedor(" +
                "inv_FS_For_Id_Fornecedor INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "inv_FS_For_Nome_Fornecedor, " +
                "inv_FS_For_Descricao TEXT, " +
                "inv_FS_For_Contato TEXT, " +
                "inv_FS_For_CNPJ TEXT, " +
                "inv_FS_For_Email TEXT, " +
                "inv_FS_For_Telefone TEXT);");

        sqLiteDatabase.execSQL("CREATE TABLE inv_FS_Modelo(" +
                "inv_FS_Mod_Id_Modelo INTEGER PRIMARY KEY, " +
                "inv_FS_Mod_Nome_Modelo TEXT, " +
                "inv_FS_Mod_Descricao TEXT, " +
                "Inv_FS_Mod_Ano INTEGER, " +
                "inv_FS_Mod_Id_Fabricante INTEGER, " +
                "FOREIGN KEY(inv_FS_Mod_Id_Fabricante) REFERENCES inv_FS_Fabricante(inv_FS_Fab_Id_Fabricante));");

        sqLiteDatabase.execSQL("CREATE TABLE inv_FS_Tipo_Equipamento(" +
                "inv_FS_TP_Id_Tipo_Equipamento INTEGER PRIMARY KEY, " +
                "inv_FS_TP_Nome_Equipamento TEXT, " +
                "inv_FS_TP_Kit_Instalacao INTEGER, " +
                "inv_FS_TP_Descricao TEXT);");

        sqLiteDatabase.execSQL("CREATE TABLE inv_FS_Tipo_Aquisicao(" +
                "inv_FS_TA_ID_Tipo_Aquisicao INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "inv_FS_TA_Nome_Aquisicao TEXT, " +
                "inv_FS_TA_Descricao TEXT);");

        sqLiteDatabase.execSQL("CREATE TABLE inv_FS_Lote_Compra(" +
                "inv_FS_Lt_Id_Lote INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "inv_FS_Lt_Nome_Lote TEXT, " +
                "inv_FS_Lt_Ano_Lote INTEGER, " +
                "inv_FS_Lt_Sequencia_Lote INTEGER);");

        sqLiteDatabase.execSQL("CREATE TABLE inv_FS_Localidade(" +
                "inv_FS_Loc_Id_Localidade INTEGER PRIMARY KEY, " +
                "inv_FS_Loc_Endereco TEXT," +
                "inv_FS_Loc_Descricao TEXT," +
                "inv_FS_Loc_cep TEXT," +
                "inv_FS_Loc_cidade TEXT," +
                "inv_FS_Loc_estado TEXT," +
                "inv_fs_Loc_EstadoSinc INTEGER," +
                "inv_FS_Loc_nome_localidade TEXT);");

        sqLiteDatabase.execSQL("CREATE TABLE inv_FS_Organizacao(" +
                "inv_FS_Org_Id_Organizacao INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "inv_FS_Org_Nome_Organizacao TEXT, " +
                "inv_FS_Org_CNPJ TEXT, " +
                "inv_FS_Org_Descricao TEXT);");

        sqLiteDatabase.execSQL("CREATE TABLE inv_FS_Departamento(" +
                "inv_FS_Dep_Id_Departamento INTEGER PRIMARY KEY, " +
                "inv_FS_Dep_Nome_Departamento TEXT, " +
                "inv_FS_Dep_Descricao TEXT, " +
                "inv_FS_Dep_Id_Organizacao INTEGER, " +
                "FOREIGN KEY(inv_FS_Dep_Id_Organizacao) REFERENCES inv_FS_Organizacao(inv_FS_Org_Id_Organizacao));");

        sqLiteDatabase.execSQL("CREATE TABLE inv_FS_Centro_Custo(" +
                "inv_FS_CC_Id_Centro_Custo INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "inv_FS_CC_Nome_Centro_Custo TEXT, " +
                "inv_FS_CC_Descricao TEXT, " +
                "inv_fs_ic_Id_Departamento INTEGER, " +
                "FOREIGN KEY (inv_fs_ic_Id_Departamento) REFERENCES inv_FS_Departamento(inv_FS_Dep_Id_Departamento));");

        sqLiteDatabase.execSQL("CREATE TABLE inv_Perfil_Usuario(" +
                "inv_PU_Id_Perfil_Usuario INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "inv_PU_Nome_Perfil_Usuario TEXT, " +
                "inv_PU_Descricao TEXT);");

        sqLiteDatabase.execSQL("CREATE TABLE inv_FS_Usuario_Final(" +
                "inv_FS_usf_id_usuario INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "inv_FS_usf_Nome TEXT, " +
                "inv_FS_usf_Id_Corporativo TEXT, " +
                "inv_FS_usf_Status INTEGER, " +
                "inv_FS_usf_Cargo TEXT, " +
                "inv_FS_usf_Ramal TEXT, " +
                "inv_FS_usf_Email TEXT, " +
                "inv_FS_usf_Celular TEXT, " +
                "inv_FS_usf_Login TEXT, " +
                "inv_FS_usf_Data_admissao DATE, " +
                "inv_FS_usf_CPF TEXT, " +
                "inv_FS_usf_RG TEXT, " +
                "inv_FS_usf_Data_Nascimento DATE, " +
                "inv_FS_usf_Nome_Gestor TEXT, " +
                "inv_FS_usf_Ramal_Gestor TEXT, " +
                "inv_FS_usf_Observacao TEXT, " +
                "inv_fs_ic_Id_Organizacao INTEGER, " +
                "inv_fs_ic_Id_Departamento INTEGER, " +
                "inv_fs_ic_Id_Localidade INTEGER, " +
                "inv_FS_usf_Id_Centro_Custo INTEGER, " +
                "FOREIGN KEY(inv_fs_ic_Id_Organizacao) REFERENCES inv_FS_Organizacao(inv_FS_Org_Id_Organizacao), " +
                "FOREIGN KEY(inv_fs_ic_Id_Departamento) REFERENCES inv_FS_Departamento(inv_FS_Dep_Id_Departamento), " +
                "FOREIGN KEY(inv_fs_ic_Id_Localidade) REFERENCES inv_FS_Localidade(inv_FS_Loc_Id_Localidade), " +
                "FOREIGN KEY(inv_FS_usf_Id_Centro_Custo) REFERENCES inv_FS_Centro_Custo(inv_FS_CC_Id_Centro_Custo));");

        sqLiteDatabase.execSQL("CREATE TABLE inv_FS_Kit_Instalacao(" +
                "inv_FS_KI_Id_Kit_Instalacao INTEGER PRIMARY KEY AUTOINCREMENT," +
                "inv_FS_KI_Id_Nome_Kit_Instalacao TEXT," +
                "inv_FS_KI_Id_Tipo_Equipamento_1 INTEGER," +
                "inv_FS_KI_Id_Tipo_Equipamento_2 INTEGER," +
                "inv_FS_KI_Id_Tipo_Equipamento_3 INTEGER," +
                "inv_FS_KI_Id_Tipo_Equipamento_4 INTEGER," +
                "inv_FS_KI_Id_Tipo_Equipamento_5 INTEGER," +
                "inv_FS_KI_Id_Tipo_Equipamento_6 INTEGER," +
                "inv_FS_KI_Id_Tipo_Equipamento_7 INTEGER," +
                "inv_FS_KI_Id_Tipo_Equipamento_8 INTEGER," +
                "inv_FS_KI_Id_Tipo_Equipamento_9 INTEGER," +
                "inv_FS_KI_Id_Tipo_Equipamento_10 INTEGER," +
                "inv_FS_KI_Id_Descricao TEXT," +
                "FOREIGN KEY(inv_FS_KI_Id_Tipo_Equipamento_1) REFERENCES Inv_FS_Item_config(inv_fs_ic_Id_IC)," +
                "FOREIGN KEY(inv_FS_KI_Id_Tipo_Equipamento_2) REFERENCES Inv_FS_Item_config(inv_fs_ic_Id_IC)," +
                "FOREIGN KEY(inv_FS_KI_Id_Tipo_Equipamento_3) REFERENCES Inv_FS_Item_config(inv_fs_ic_Id_IC)," +
                "FOREIGN KEY(inv_FS_KI_Id_Tipo_Equipamento_4) REFERENCES Inv_FS_Item_config(inv_fs_ic_Id_IC)," +
                "FOREIGN KEY(inv_FS_KI_Id_Tipo_Equipamento_5) REFERENCES Inv_FS_Item_config(inv_fs_ic_Id_IC)," +
                "FOREIGN KEY(inv_FS_KI_Id_Tipo_Equipamento_6) REFERENCES Inv_FS_Item_config(inv_fs_ic_Id_IC)," +
                "FOREIGN KEY(inv_FS_KI_Id_Tipo_Equipamento_7) REFERENCES Inv_FS_Item_config(inv_fs_ic_Id_IC)," +
                "FOREIGN KEY(inv_FS_KI_Id_Tipo_Equipamento_8) REFERENCES Inv_FS_Item_config(inv_fs_ic_Id_IC)," +
                "FOREIGN KEY(inv_FS_KI_Id_Tipo_Equipamento_9) REFERENCES Inv_FS_Item_config(inv_fs_ic_Id_IC)," +
                "FOREIGN KEY(inv_FS_KI_Id_Tipo_Equipamento_10) REFERENCES Inv_FS_Item_config(inv_fs_ic_Id_IC));");

        sqLiteDatabase.execSQL("CREATE TABLE inv_FS_Termo_Responsabilidade(" +
                "inv_FS_TR_ID_Termo INTEGER PRIMARY KEY AUTOINCREMENT," +
                "Inv_FS_TR_Id_IC INTEGER," +
                "inv_FS_TR_DataHora_Emissao DATE, " +
                "inv_FS_TR_DataHora_Assinatura DATE, " +
                "inv_FS_TR_Validade_Termo INTEGER, " +
                "inv_FS_TR_Termo_Assinado INTEGER, " +
                "inv_FS_TR_Codigo_Confirmação TEXT, " +
                "inv_fs_ic_Id_Usuario_Final INTEGER, " +
                "FOREIGN KEY(Inv_FS_TR_Id_IC) REFERENCES Inv_FS_Item_config(inv_fs_ic_Id_IC));" +
                "FOREIGN KEY(inv_fs_ic_Id_Usuario_Final) REFERENCES inv_FS_Usuario_Final(inv_FS_usf_id_usuario));");

        sqLiteDatabase.execSQL("CREATE TABLE Inv_FS_Item_config(" +
                "inv_fs_ic_Id_IC INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "inv_fs_ic_numero_serie TEXT, " +
                "inv_fs_ic_Nome_IC TEXT, " +
                "inv_fs_ic_RFID TEXT, " +
                "inv_fs_ic_Nota_fiscal TEXT, " +
                "inv_fs_ic_Patrimonio TEXT, " +
                "inv_fs_ic_Data_compra DATE, " +
                "inv_fs_ic_Data_Fim_Garantia DATE, " +
                "inv_fs_ic_contrato TEXT, " +
                "inv_fs_ic_Data_Fim_Leasing DATE, " +
                "inv_fs_ic_codigo_anterior TEXT, " +
                "inv_fs_ic_mac_adress TEXT, " +
                "inv_fs_ic_IP TEXT, " +
                "inv_fs_ic_Data_Criacao DATE, " +
                "inv_fs_ic_Data_Ultima_Atualizacao DATE, " +
                "inv_fs_ic_codigo_descarte TEXT, " +
                "inv_fs_ic_Rede_Conectada INTEGER, " +
                "inv_fs_ic_Ult_Incidente TEXT, " +
                "inv_fs_ic_Incidente_Externo TEXT, " +
                "inv_fs_ic_Id_Status INTEGER, " +
                "inv_fs_ic_Id_Substatus INTEGER, " +
                "inv_fs_ic_Id_Fabricante INTEGER, " +
                "inv_fs_ic_Id_Modelo INTEGER, " +
                "inv_fs_ic_Id_Fornecedor INTEGER, " +
                "inv_fs_ic_Id_Tipo_Equipamento INTEGER, " +
                "inv_fs_ic_Id_Tipo_Aquisiçao INTEGER, " +
                "inv_fs_ic_Id_Localidade INTEGER, " +
                "inv_fs_ic_Id_Termo INTEGER, " +
                "inv_fs_ic_Id_Organizacao INTEGER, " +
                "inv_fs_ic_Id_Usuario_Final INTEGER, " +
                "inv_fs_ic_Id_Lote_Compra INTEGER, " +
                "inv_fs_ic_Id_Usuario_Criacao INTEGER, " +
                "inv_fs_ic_Id_Departamento INTEGER, " +
                "inv_fs_ic_Id_Usuario_Ult_Atualizacao INTEGER, " +
                "inv_fs_ic_EstadoSinc INTEGER NOT NULL DEFAULT 0, " +
                "inv_fs_ic_idRemoto TEXT, " +
                "inv_fs_ic_insert_pending INTEGER NOT NULL DEFAULT 0, " +
                "FOREIGN KEY(inv_fs_ic_Id_Status) REFERENCES inv_FS_Status(inv_FS_St_id_Status), " +
                "FOREIGN KEY(inv_fs_ic_Id_Substatus) REFERENCES inv_FS_Sub_Status(inv_FS_St_id_Status), " +
                "FOREIGN KEY(inv_fs_ic_Id_Fabricante) REFERENCES inv_FS_Fabricante(inv_FS_Fab_Id_Fabricante), " +
                "FOREIGN KEY(inv_fs_ic_Id_Modelo) REFERENCES inv_FS_Modelo(inv_FS_Mod_Id_Modelo), " +
                "FOREIGN KEY(inv_fs_ic_Id_Fornecedor) REFERENCES inv_FS_Fornecedor(inv_FS_For_Id_Fornecedor), " +
                "FOREIGN KEY(inv_fs_ic_Id_Tipo_Equipamento) REFERENCES inv_FS_Tipo_Equipamento(inv_FS_For_Id_Fornecedor), " +
                "FOREIGN KEY(inv_fs_ic_Id_Tipo_Aquisiçao) REFERENCES inv_FS_Tipo_Aquisicao(inv_FS_TA_ID_Tipo_Aquisicao), " +
                "FOREIGN KEY(inv_fs_ic_Id_Localidade) REFERENCES inv_FS_Localidade(inv_FS_Loc_Id_Localidade), " +
                "FOREIGN KEY(inv_fs_ic_Id_Organizacao) REFERENCES inv_FS_Organizacao(inv_FS_Org_Id_Organizacao), " +
                "FOREIGN KEY(inv_fs_ic_Id_Usuario_Final) REFERENCES inv_FS_Usuario_Final(inv_FS_usf_id_usuario), " +
                "FOREIGN KEY(inv_fs_ic_Id_Termo) REFERENCES inv_FS_Termo_Responsabilidade(inv_FS_TR_ID_Termo), " +
                "FOREIGN KEY(inv_fs_ic_Id_Departamento) REFERENCES inv_FS_Departamento(inv_FS_Dep_Id_Departamento));");

        sqLiteDatabase.execSQL("CREATE TABLE inv_Usuario(" +
                "inv_us_id_Usuario INTEGER PRIMARY KEY," +
                "inv_us_Nome TEXT," +
                "inv_us_Login TEXT," +
                "inv_us_Senha TEXT," +
                "inv_us_Perfil INTEGER," +
                "inv_us_Email TEXT," +
                "inv_us_Ativo INTEGER," +
                "FOREIGN KEY(inv_us_Perfil) REFERENCES inv_Perfil_Usuario(inv_PU_Id_Perfil_Usuario));");

        sqLiteDatabase.execSQL("CREATE TABLE verificacao(" +
                "_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "rfid TEXT, " +
                "data_verificacao DATE);");



      //INSERÇÕES DE TESTE NO BANCO
//        sqLiteDatabase.execSQL("INSERT INTO inv_FS_Tipo_Equipamento (inv_FS_TP_Id_Tipo_Equipamento, inv_FS_TP_Nome_Equipamento) VALUES (1, 'Desktop');");
//        sqLiteDatabase.execSQL("INSERT INTO inv_FS_Tipo_Equipamento (inv_FS_TP_Id_Tipo_Equipamento, inv_FS_TP_Nome_Equipamento) VALUES (2, 'Notebook');");
//        sqLiteDatabase.execSQL("INSERT INTO inv_Perfil_Usuario (inv_PU_Nome_Perfil_Usuario) VALUES ('Padrao')");
//        try {
//            sqLiteDatabase.execSQL("INSERT INTO inv_Usuario (inv_us_Login, inv_us_Senha, inv_us_Perfil) VALUES ('marcos', '"+ Criptografia.encrypt("1234")+"', 1)");
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        sqLiteDatabase.execSQL("INSERT INTO inv_FS_Departamento (inv_FS_Dep_Id_Departamento, inv_FS_Dep_Nome_Departamento) VALUES (1, 'TI');");
//        sqLiteDatabase.execSQL("INSERT INTO inv_FS_Departamento (inv_FS_Dep_Id_Departamento, inv_FS_Dep_Nome_Departamento) VALUES (2, 'Fiscal');");
//
//        sqLiteDatabase.execSQL("INSERT INTO inv_FS_Fabricante (inv_FS_Fab_Id_Fabricante, inv_FS_Fab_Nome_Fabricante) VALUES (1, 'Dell')");
//        sqLiteDatabase.execSQL("INSERT INTO inv_FS_Fabricante (inv_FS_Fab_Id_Fabricante, inv_FS_Fab_Nome_Fabricante) VALUES (2, 'HP')");
//
//        sqLiteDatabase.execSQL("INSERT INTO inv_FS_Status (inv_FS_St_id_Status, inv_FS_St_Nome_Status) VALUES (1, 'Instalado');");
//        sqLiteDatabase.execSQL("INSERT INTO inv_FS_Status (inv_FS_St_id_Status, inv_FS_St_Nome_Status) VALUES (2, 'Manutenção');");
//
//        sqLiteDatabase.execSQL("INSERT INTO inv_FS_Modelo (inv_FS_Mod_Id_Modelo, inv_FS_Mod_Nome_Modelo, inv_FS_Mod_Id_Fabricante, Inv_FS_Mod_Ano) " +
//                "VALUES (1, 'Pavillion', 2, 2015)");
//        sqLiteDatabase.execSQL("INSERT INTO inv_FS_Modelo (inv_FS_Mod_Id_Modelo, inv_FS_Mod_Nome_Modelo, inv_FS_Mod_Id_Fabricante, Inv_FS_Mod_Ano) " +
//                "VALUES (2, 'Vostro', 1, 2015);");
//        sqLiteDatabase.execSQL("INSERT INTO inv_FS_Modelo (inv_FS_Mod_Id_Modelo, inv_FS_Mod_Nome_Modelo, inv_FS_Mod_Id_Fabricante, Inv_FS_Mod_Ano) " +
//                "VALUES (3, 'Optiplex', 1, 2015);");
//
//
//        sqLiteDatabase.execSQL("INSERT INTO inv_FS_Localidade(inv_FS_Loc_Id_Localidade, inv_FS_Loc_Descricao, inv_FS_Loc_cidade) VALUES (1, 'CENESP', 'São Paulo')");
//        sqLiteDatabase.execSQL("INSERT INTO inv_FS_Localidade(inv_FS_Loc_Id_Localidade, inv_FS_Loc_Descricao, inv_FS_Loc_cidade) VALUES (2, 'Ipiranga', 'São Paulo')");
//        sqLiteDatabase.execSQL("INSERT INTO inv_FS_Localidade(inv_FS_Loc_Id_Localidade, inv_FS_Loc_Descricao, inv_FS_Loc_cidade) VALUES (3, 'Transamerica', 'São Paulo')");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {

    }
}
