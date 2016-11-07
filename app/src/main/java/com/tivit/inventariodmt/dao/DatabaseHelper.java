package com.tivit.inventariodmt.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

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
        sqLiteDatabase.execSQL("CREATE TABLE inv_FS_Status(_id INTEGER PRIMARY KEY AUTOINCREMENT, inv_FS_St_Nome_Status TEXT, inv_FS_St_Descricao TEXT);");
        sqLiteDatabase.execSQL("CREATE TABLE inv_FS_Fabricante(_id INTEGER PRIMARY KEY AUTOINCREMENT, inv_FS_Fab_Nome_Fabricante TEXT, inv_FS_Fab_Descricao TEXT);");
        sqLiteDatabase.execSQL("CREATE TABLE inv_FS_Sub_Status(_id INTEGER PRIMARY KEY AUTOINCREMENT, inv_FS_Subst_Nome_Sub_Status TEXT, inv_FS_Subst_Descricao TEXT, status_id INTEGER," +
                " FOREIGN KEY(status_id) REFERENCES inv_FS_Status(_id));");
        sqLiteDatabase.execSQL("CREATE TABLE inv_FS_Fornecedor(_id INTEGER PRIMARY KEY AUTOINCREMENT, inv_FS_For_Nome_Fornecedor, inv_FS_For_Descricao TEXT, inv_FS_For_Contato TEXT, inv_FS_For_CNPJ TEXT, inv_FS_For_Email TEXT, inv_FS_For_Telefone TEXT);");
        sqLiteDatabase.execSQL("CREATE TABLE inv_FS_Modelo(_id INTEGER PRIMARY KEY AUTOINCREMENT, inv_FS_Mod_Nome_Modelo TEXT, inv_FS_Mod_Descricao TEXT, Inv_FS_Mod_Ano INTEGER, fabricante_id INTEGER," +
                " FOREIGN KEY(fabricante_id) REFERENCES inv_FS_Fabricante(_id));");
        sqLiteDatabase.execSQL("CREATE TABLE inv_FS_Tipo_Equipamento(_id INTEGER PRIMARY KEY AUTOINCREMENT, inv_FS_TP_Nome_Equipamento TEXT, inv_FS_TP_Kit_Instalacao INTEGER, inv_FS_TP_Descricao TEXT);");
        sqLiteDatabase.execSQL("CREATE TABLE inv_FS_Tipo_Aquisicao(_id INTEGER PRIMARY KEY AUTOINCREMENT, inv_FS_TA_Nome_Aquisicao TEXT, inv_FS_TA_Descricao TEXT);");
        sqLiteDatabase.execSQL("CREATE TABLE inv_FS_Lote_Compra(_id INTEGER PRIMARY KEY AUTOINCREMENT, inv_FS_Lt_Nome_Lote TEXT, inv_FS_Lt_Ano_Lote INTEGER, inv_FS_Lt_Sequencia_Lote INTEGER);");
            sqLiteDatabase.execSQL("CREATE TABLE inv_FS_Estado(_id INTEGER PRIMARY KEY AUTOINCREMENT, inv_FS_Est_Nome TEXT);");
        sqLiteDatabase.execSQL("CREATE TABLE inv_FS_Cidade(_id INTEGER PRIMARY KEY AUTOINCREMENT, inv_FS_Cid_Nome TEXT, estado_id INTEGER, FOREIGN KEY (estado_id) REFERENCES inv_FS_Estado(_id));");
        sqLiteDatabase.execSQL("CREATE TABLE inv_FS_Localidade(_id INTEGER PRIMARY KEY AUTOINCREMENT, inv_FS_Loc_Endereco TEXT, inv_FS_Loc_Descricao TEXT, cidade_id INTEGER, FOREIGN KEY(cidade_id) REFERENCES inv_FS_Cidade(_id));");
        sqLiteDatabase.execSQL("CREATE TABLE inv_FS_Organizacao(_id INTEGER PRIMARY KEY AUTOINCREMENT, inv_FS_Org_Nome_Organizacao TEXT, inv_FS_Org_CNPJ TEXT, inv_FS_Org_Descricao TEXT);");
        sqLiteDatabase.execSQL("CREATE TABLE inv_FS_Departamento(_id INTEGER PRIMARY KEY AUTOINCREMENT, inv_FS_Dep_Nome_Departamento TEXT, inv_FS_Dep_Descricao TEXT, organizacao_id INTEGER," +
                " FOREIGN KEY(organizacao_id) REFERENCES inv_FS_Organizacao(_id));");
        sqLiteDatabase.execSQL("CREATE TABLE inv_FS_Centro_Custo(_id INTEGER PRIMARY KEY AUTOINCREMENT, inv_FS_CC_Nome_Centro_Custo TEXT, inv_FS_CC_Descricao TEXT, departamento_id INTEGER," +
                " FOREIGN KEY (departamento_id) REFERENCES inv_FS_Departamento(_id));");
        sqLiteDatabase.execSQL("CREATE TABLE inv_Perfil_Usuario(_id INTEGER PRIMARY KEY AUTOINCREMENT, inv_PU_Nome_Perfil_Usuario TEXT, inv_PU_Descricao TEXT);");
        sqLiteDatabase.execSQL("CREATE TABLE inv_FS_Usuario_Final(_id INTEGER PRIMARY KEY AUTOINCREMENT, inv_FS_usf_Nome TEXT, inv_FS_usf_Id_Corporativo TEXT, inv_FS_usf_Status INTEGER, inv_FS_usf_Cargo TEXT," +
                " inv_FS_usf_Ramal TEXT, inv_FS_usf_Email TEXT, inv_FS_usf_Celular TEXT, inv_FS_usf_Login TEXT, inv_FS_usf_Data_admissao DATE, inv_FS_usf_CPF TEXT, inv_FS_usf_RG TEXT," +
                " inv_FS_usf_Data_Nascimento DATE, inv_FS_usf_Nome_Gestor TEXT, inv_FS_usf_Ramal_Gestor TEXT, inv_FS_usf_Observacao TEXT, organizacao_id INTEGER, departamento_id INTEGER, localidade_id INTEGER," +
                " centro_custo_id INTEGER, FOREIGN KEY(organizacao_id) REFERENCES inv_FS_Organizacao(_id), FOREIGN KEY(departamento_id) REFERENCES inv_FS_Departamento(_id)," +
                " FOREIGN KEY(localidade_id) REFERENCES inv_FS_Localidade(_id), FOREIGN KEY(centro_custo_id) REFERENCES inv_FS_Centro_Custo(_id));");
        sqLiteDatabase.execSQL("CREATE TABLE inv_FS_Kit_Instalacao(_id INTEGER PRIMARY KEY AUTOINCREMENT, inv_FS_KI_Id_Nome_Kit_Instalacao TEXT, inv_FS_KI_Id_Descricao TEXT, tipo_equipamento_id INTEGER, FOREIGN KEY(tipo_equipamento_id) REFERENCES inv_FS_Tipo_Equipamento(_id));");
        sqLiteDatabase.execSQL("CREATE TABLE inv_FS_Termo_Responsabilidade(_id INTEGER PRIMARY KEY AUTOINCREMENT, inv_FS_TR_DataHora_Emissao DATE, inv_FS_TR_DataHora_Assinatura DATE, inv_FS_TR_Validade_Termo INTEGER, inv_FS_TR_Termo_Assinado INTEGER, inv_FS_TR_Codigo_Confirmação TEXT," +
                "  usuario_id INTEGER, FOREIGN KEY(usuario_id) REFERENCES inv_FS_Usuario_Final(_id));");
        sqLiteDatabase.execSQL("CREATE TABLE Inv_FS_Item_config(_id INTEGER PRIMARY KEY AUTOINCREMENT, inv_fs_ic_numero_serie TEXT, inv_fs_ic_Nome_IC TEXT, inv_fs_ic_RFID TEXT, inv_fs_ic_Nota_fiscal TEXT," +
                " inv_fs_ic_Patrimonio TEXT, inv_fs_ic_Data_compra DATE, inv_fs_ic_Data_Fim_Garantia DATE, inv_fs_ic_contrato TEXT, inv_fs_ic_Data_Fim_Leasing DATE, inv_fs_ic_codigo_anterior TEXT, inv_fs_ic_mac_adress TEXT," +
                " inv_fs_ic_IP TEXT, inv_fs_ic_Data_Criacao DATE, inv_fs_ic_Data_Ultima_Atualizacao DATE, inv_fs_ic_codigo_descarte TEXT, inv_fs_ic_Rede_Conectada INTEGER, inv_fs_ic_Ult_Incidente TEXT," +
                " inv_fs_ic_Incidente_Externo TEXT, status_id INTEGER, substatus_id INTEGER, fabricante_id INTEGER, modelo_id INTEGER, fornecedor_id INTEGER, tipo_equipamento_id INTEGER," +
                " tipo_aquisicao_id INTEGER, localidade_id INTEGER, termo_id INTEGER, organizacao_id INTEGER, usuario_id INTEGER, lote_compra_id INTEGER, usuario_criacao_id INTEGER, departamento_id INTEGER," +
                " usuario_ult_atualizacao_id INTEGER, kit_instalacao_id INTEGER, estado INTEGER NOT NULL DEFAULT 0, idRemoto TEXT, insert_pending INTEGER NOT NULL DEFAULT 0, FOREIGN KEY(status_id) REFERENCES inv_FS_Status(_id), FOREIGN KEY(substatus_id) REFERENCES inv_FS_Sub_Status(_id)," +
                " FOREIGN KEY(fabricante_id) REFERENCES inv_FS_Fabricante(_id), FOREIGN KEY(modelo_id) REFERENCES inv_FS_Modelo(_id), FOREIGN KEY(fornecedor_id) REFERENCES inv_FS_Fornecedor(_id)," +
                " FOREIGN KEY(tipo_equipamento_id) REFERENCES inv_FS_Tipo_Equipamento(_id), FOREIGN KEY(tipo_aquisicao_id) REFERENCES inv_FS_Tipo_Aquisicao(_id), FOREIGN KEY(localidade_id) REFERENCES inv_FS_Localidade(_id)," +
                " FOREIGN KEY(organizacao_id) REFERENCES inv_FS_Organizacao(_id), FOREIGN KEY(usuario_id) REFERENCES inv_FS_Usuario_Final(_id), FOREIGN KEY(termo_id) REFERENCES inv_FS_Termo_Responsabilidade(_id)," +
                " FOREIGN KEY (departamento_id) REFERENCES inv_FS_Departamento(_id));");

        //VERIFICAÇÃO DE EQUIPAMENTOS QUE PASSARAM PELA VERIFICAÇÃO DO RFID EM GRUPO.
        sqLiteDatabase.execSQL("CREATE TABLE verificacao(_id INTEGER PRIMARY KEY AUTOINCREMENT, rfid TEXT, data_verificacao DATE);");

        //INSERÇÕES DE TESTE NO BANCO
        sqLiteDatabase.execSQL("INSERT INTO inv_FS_Tipo_Equipamento (inv_FS_TP_Nome_Equipamento) VALUES ('Desktop');");
        sqLiteDatabase.execSQL("INSERT INTO inv_FS_Tipo_Equipamento (inv_FS_TP_Nome_Equipamento) VALUES ('Notebook');");

        sqLiteDatabase.execSQL("INSERT INTO inv_FS_Departamento (inv_FS_Dep_Nome_Departamento) VALUES ('TI');");
        sqLiteDatabase.execSQL("INSERT INTO inv_FS_Departamento (inv_FS_Dep_Nome_Departamento) VALUES ('Fiscal');");

        sqLiteDatabase.execSQL("INSERT INTO inv_FS_Status (inv_FS_St_Nome_Status) VALUES ('Instalado');");
        sqLiteDatabase.execSQL("INSERT INTO inv_FS_Status (inv_FS_St_Nome_Status) VALUES ('Manutenção');");


        sqLiteDatabase.execSQL("INSERT INTO inv_FS_Estado(inv_FS_Est_Nome) VALUES ('SP')");

        sqLiteDatabase.execSQL("INSERT INTO inv_FS_Cidade(inv_FS_Cid_Nome, estado_id) VALUES ('São Paulo', 1)");

        sqLiteDatabase.execSQL("INSERT INTO inv_FS_Localidade(inv_FS_Loc_Descricao, cidade_id) VALUES ('CENESP', 1)");
        sqLiteDatabase.execSQL("INSERT INTO inv_FS_Localidade(inv_FS_Loc_Descricao, cidade_id) VALUES ('Ipiranga', 1)");
        sqLiteDatabase.execSQL("INSERT INTO inv_FS_Localidade(inv_FS_Loc_Descricao, cidade_id) VALUES ('Transamerica', 1)");

        /*sqLiteDatabase.execSQL("INSERT INTO inv_FS_Estado (inv_FS_Est_Nome) VALUES ('São Paulo');");
        sqLiteDatabase.execSQL("INSERT INTO inv_FS_Estado (inv_FS_Est_Nome) VALUES ('Rio de Janeiro');");

        sqLiteDatabase.execSQL("INSERT INTO inv_FS_Cidade (inv_FS_Cid_Nome, estado_id) VALUES ('São Paulo', 1);");
        sqLiteDatabase.execSQL("INSERT INTO inv_FS_Cidade (inv_FS_Cid_Nome, estado_id) VALUES ('São Bernardo', 1);");
        sqLiteDatabase.execSQL("INSERT INTO inv_FS_Cidade (inv_FS_Cid_Nome, estado_id) VALUES ('Rio de Janeiro', 2);");
        sqLiteDatabase.execSQL("INSERT INTO inv_FS_Cidade (inv_FS_Cid_Nome, estado_id) VALUES ('Volta Redonda', 2);");

        sqLiteDatabase.execSQL("INSERT INTO inv_FS_Localidade (inv_FS_Loc_Endereco TEXT, cidade_id) VALUES ('Rua Um', 1);");
        sqLiteDatabase.execSQL("INSERT INTO inv_FS_Localidade (inv_FS_Loc_Endereco TEXT, cidade_id) VALUES ('Rua Dois', 2);");
        sqLiteDatabase.execSQL("INSERT INTO inv_FS_Localidade (inv_FS_Loc_Endereco TEXT, cidade_id) VALUES ('Rua Três', 3);");
        sqLiteDatabase.execSQL("INSERT INTO inv_FS_Localidade (inv_FS_Loc_Endereco TEXT, cidade_id) VALUES ('Rua Quatro', 4);");

        sqLiteDatabase.execSQL("INSERT INTO inv_FS_Fabricante (inv_FS_Fab_Nome_Fabricante) VALUES ('DELL');");
        sqLiteDatabase.execSQL("INSERT INTO inv_FS_Fabricante (inv_FS_Fab_Nome_Fabricante) VALUES ('HP');");

        sqLiteDatabase.execSQL("INSERT INTO inv_FS_Modelo (inv_FS_Mod_Nome_Modelo, fabricante_id) VALUES ('Vostro', 1);");
        sqLiteDatabase.execSQL("INSERT INTO inv_FS_Modelo (inv_FS_Mod_Nome_Modelo, fabricante_id) VALUES ('Optiplex', 1);");
        sqLiteDatabase.execSQL("INSERT INTO inv_FS_Modelo (inv_FS_Mod_Nome_Modelo, fabricante_id) VALUES ('Pavillion', 2);");
        sqLiteDatabase.execSQL("INSERT INTO inv_FS_Modelo (inv_FS_Mod_Nome_Modelo, fabricante_id) VALUES ('Complex', 2);");*/
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {

    }
}
