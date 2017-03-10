package com.tivit.inventariodmt.dao;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

import java.util.ArrayList;
import java.util.List;

import com.tivit.inventariodmt.dataconsistency.provider.EquipamentoContract;
import com.tivit.inventariodmt.dataconsistency.provider.LocalidadeContract;
import com.tivit.inventariodmt.dataconsistency.provider.UsuarioFinalContract;
import com.tivit.inventariodmt.dto.DepartamentoDTO;
import com.tivit.inventariodmt.dto.FabricanteDTO;
import com.tivit.inventariodmt.dto.LocalidadeDTO;
import com.tivit.inventariodmt.dto.ModeloDTO;
import com.tivit.inventariodmt.dto.StatusDTO;
import com.tivit.inventariodmt.dto.TipoEquipamentoDTO;
import com.tivit.inventariodmt.dto.UsuarioDTO;
import com.tivit.inventariodmt.dto.UsuarioFinalDTO;

/**
 * Created by alex.almeida on 15/08/2016.
 */
public class PreencheCombosDao {

    private DatabaseHelper helper;
    private List<String> localidades;
    private List<String> tipoEquipamentos;
    private List<String> fabricantes;
    private List<String> status;
    private List<String> modelos;
    private List<String> usuarios;
    private List<String> departamentos;
    private SQLiteDatabase db;
    ContentResolver resolver;

    private static final String[] LOCALIDADE_PROJECTION = new String[]{
            LocalidadeContract.Colunas._ID,
            LocalidadeContract.Colunas.CEP,
            LocalidadeContract.Colunas.CIDADE,
            LocalidadeContract.Colunas.DESCRICAO,
            LocalidadeContract.Colunas.ENDERECO,
            LocalidadeContract.Colunas.ESTADO,
            LocalidadeContract.Colunas.ESTADO_SINC,
            LocalidadeContract.Colunas.ID_REMOTA,
            LocalidadeContract.Colunas.INSERT_PENDING,
            LocalidadeContract.Colunas.NOME,
    };

    public PreencheCombosDao(Context context) {
        helper = new DatabaseHelper(context);
    }

    private SQLiteDatabase getDb() {
        if (db == null) {
            db = helper.getReadableDatabase();
        }
        return db;
    }

    public void close() {
        helper.close();
    }

    /*public List listarLocalidades() {
        this.localidades = new ArrayList<>();


        Cursor cursor = getDb().rawQuery("SELECT inv_FS_Loc_Descricao FROM inv_FS_Localidade", null);
        cursor.moveToFirst();

        for (int i = 0; i < cursor.getCount(); i++) {

            String descricao = cursor.getString(0);

            localidades.add(descricao);
            cursor.moveToNext();
        }
        cursor.close();
        return localidades;
    }*/

    public List<LocalidadeDTO> listarLocalidades(){
        Uri uri = LocalidadeContract.CONTENT_URI;
        List<LocalidadeDTO> lL = new ArrayList<>();
        String select = LocalidadeContract.Colunas._ID + " >=?";
        String[] sqlArgs = new String[]{"-1"};
        Cursor cursor = getDb().rawQuery("SELECT inv_FS_Loc_Id_Localidade, inv_FS_Loc_Endereco, Inv_FS_Loc_Descricao, inv_FS_Loc_cidade FROM inv_FS_Localidade", null);
        //Cursor cursor = resolver.query(uri,LOCALIDADE_PROJECTION,select,null,null);
        assert cursor != null;
        cursor.moveToFirst();
        if (cursor.getCount() > 0) {
            do {
                LocalidadeDTO l = new LocalidadeDTO();
                l.setInv_FS_Loc_Id_Localidade(cursor.getInt(0));
                l.setInv_FS_Loc_Endereco(cursor.getString(1));
                l.setInv_FS_Loc_Descricao(cursor.getString(2));

                lL.add(l);

            } while (cursor.moveToNext());
            cursor.close();
        }
        return lL;
    }

    public List<UsuarioFinalDTO> listarUsuariosFinais(){
        Uri uri = UsuarioFinalContract.CONTENT_URI;
        List<UsuarioFinalDTO> lUsf = new ArrayList<>();
//        Cursor cursor = getDb().rawQuery("SELECT inv_FS_usf_id_usuario, inv_FS_usf_CPF, inv_FS_usf_Cargo, inv_FS_usf_Celular, inv_FS_usf_Data_Nascimento," +
//                " inv_FS_usf_Data_admissao, inv_FS_usf_Email, inv_FS_usf_Id_Centro_Custo, inv_FS_usf_Id_Corporativo, inv_FS_usf_Login," +
//                " nv_FS_usf_Nome, inv_FS_usf_Nome_Gestor, inv_FS_usf_Observacao, inv_FS_usf_Status, inv_FS_usf_id_Departamento, inv_FS_usf_RG," +
//                " inv_FS_usf_id_Localidade, inv_FS_usf_id_Organizacao, inv_FS_usf_Ramal, inv_FS_usf_Ramal_Gestor FROM  inv_FS_Usuario_Final", null);
        Cursor cursor = getDb().rawQuery("SELECT * FROM inv_FS_Usuario_Final", null);
        cursor.moveToFirst();
        if(cursor.getCount() > 0){
            do{
                UsuarioFinalDTO usu = new UsuarioFinalDTO();
                usu.setInv_FS_usf_id_usuario(cursor.getInt(cursor.getColumnIndex("inv_FS_usf_id_usuario")));
                usu.setInv_FS_usf_Nome(cursor.getString(cursor.getColumnIndex("inv_FS_usf_Nome")));
                lUsf.add(usu);
            }while(cursor.moveToNext());
            cursor.close();
        }
        return lUsf;

    }

    /*public List<LocalidadeDTO> listarLocalidades() throws SQLException {
        List<LocalidadeDTO> lL = new ArrayList<>();
        ResultSet rs = null;
        rs = (ResultSet) getDb().rawQuery("SELECT * FROM inv_FS_Localidade", null);
        while (rs.next()){
            LocalidadeDTO l = new LocalidadeDTO();
            l.set_id(rs.getInt("_id"));
            l.setCidade_id(rs.getInt("cidade_id"));
            l.setInv_FS_Loc_Descricao(rs.getString("inv_FS_Loc_Descricao"));
            l.setInv_FS_Loc_Endereco(rs.getString("inv_FS_Loc_Endereco"));

            lL.add(l);
        }
        return lL;
    }*/

    public List<DepartamentoDTO> listarDepartamentos() {
        List<DepartamentoDTO> lD = new ArrayList<>();

        Cursor cursor = getDb().rawQuery("SELECT inv_FS_Dep_Id_Departamento, inv_FS_Dep_Nome_Departamento FROM inv_FS_Departamento", null);
        cursor.moveToFirst();
        if (cursor.getCount() > 0) {
            do {
                DepartamentoDTO d = new DepartamentoDTO();

                d.setInv_FS_Dep_Id_Departamento(cursor.getInt(0));
                d.setInv_FS_Dep_Nome_Departamento(cursor.getString(1));
                lD.add(d);

            } while (cursor.moveToNext());
        }
        cursor.close();
        return lD;
    }

    /*public List<String> listarTipoEquipamentos() {
        this.tipoEquipamentos = new ArrayList<>();

        Cursor cursor = getDb().rawQuery("SELECT inv_FS_TP_Nome_Equipamento FROM inv_FS_Tipo_Equipamento", null);
        cursor.moveToFirst();

        for(int i = 0; i < cursor.getCount(); i++) {
            String tipo = cursor.getString(0);
            tipoEquipamentos.add(tipo);
            cursor.moveToNext();
        }
        cursor.close();
        return tipoEquipamentos;
    }*/

    public List<TipoEquipamentoDTO> listarTipoEquipamentos(){
        List<TipoEquipamentoDTO> tipoEquipamentos = new ArrayList<>();
        Cursor cursor = getDb().rawQuery("SELECT * FROM inv_FS_Tipo_Equipamento", null);
        cursor.moveToFirst();
        if (cursor.getCount() > 0) {
            do {
                TipoEquipamentoDTO e = new TipoEquipamentoDTO();
                e.setInv_FS_TP_Id_Tipo_Equipamento(cursor.getInt(0));
                e.setInv_FS_TP_Nome_Equipamento(cursor.getString(1));
                e.setInv_FS_TP_Kit_Instalacao(cursor.getInt(2));
                e.setInv_FS_TP_Descricao(cursor.getString(3));

                tipoEquipamentos.add(e);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return tipoEquipamentos;
    }

    public List<StatusDTO> listarStatus() {
        List<StatusDTO> lS = new ArrayList<>();

        Cursor cursor = getDb().rawQuery("SELECT inv_FS_St_id_Status, inv_FS_St_Nome_Status FROM inv_FS_Status", null);
        cursor.moveToFirst();
        if (cursor.getCount() > 0) {
            do {
                StatusDTO stt = new StatusDTO();
                stt.setInv_FS_St_id_Status(cursor.getInt(0));
                stt.setInv_FS_St_Nome_Status(cursor.getString(1));
                stt.setInv_FS_St_Descricao("");

                lS.add(stt);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return lS;
    }

    public List<FabricanteDTO> listarFabricantes() {
        List<FabricanteDTO> lF = new ArrayList<>();

        Cursor cursor = getDb().rawQuery("SELECT inv_FS_Fab_Id_Fabricante, inv_FS_Fab_Nome_Fabricante FROM inv_FS_Fabricante", null);
        cursor.moveToFirst();
        if (cursor.getCount() > 0) {
            do {
                FabricanteDTO f = new FabricanteDTO();
                f.setInv_FS_Fab_Id_Fabricante(cursor.getInt(0));
                f.setInv_FS_Fab_Nome_Fabricante(cursor.getString(1));

                lF.add(f);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return lF;
    }

    public List<ModeloDTO> listarModelos(int codigoFabricante) {
        this.modelos = new ArrayList<>();
        List<ModeloDTO> mod = new ArrayList<>();
        Cursor cursor = getDb().rawQuery("SELECT inv_FS_Mod_Id_Modelo, inv_FS_Mod_Nome_Modelo FROM inv_FS_Modelo WHERE inv_FS_Mod_Id_Fabricante = " +codigoFabricante, null);
        cursor.moveToFirst();
        if (cursor.getCount() > 0) {
            do {
                ModeloDTO m = new ModeloDTO();
                m.setInv_FS_Mod_Id_Modelo(cursor.getInt(0));
                m.setInv_FS_Mod_Nome_Modelo(cursor.getString(1));

                mod.add(m);

            }while(cursor.moveToNext());
        }
        cursor.close();
        return mod;
    }

    public Cursor selcionaPorRfid(String rfid)
    {
        //rfid = rfid.toUpperCase().replace("\n","").replace("\r","");
        String[] selecArgs = new String[]{rfid};
        String sql = "SELECT ic.inv_fs_ic_RFID, ic.inv_fs_ic_numero_serie, tp.inv_FS_TP_Nome_Equipamento, fab.inv_FS_Fab_Nome_Fabricante, modd.inv_FS_Mod_Nome_Modelo, loc.inv_FS_Loc_Descricao FROM inv_fs_item_config ic " +
                "INNER JOIN inv_FS_Localidade loc ON loc.inv_FS_Loc_Id_Localidade = ic.inv_fs_ic_Id_Localidade " +
                "INNER JOIN inv_FS_Fabricante fab ON fab.inv_FS_Fab_Id_Fabricante = ic.inv_fs_ic_Id_Fabricante " +
                "INNER JOIN inv_FS_Modelo modd ON modd.inv_FS_Mod_Id_Modelo = ic.inv_fs_ic_Id_Modelo " +
                "INNER JOIN inv_FS_Tipo_Equipamento tp ON ic.inv_fs_ic_Id_Tipo_Equipamento = tp.inv_FS_TP_Id_Tipo_Equipamento " +
                "WHERE ic.inv_fs_ic_RFID == ?";

        Cursor c = getDb().rawQuery(sql,selecArgs);

        return c;
    }
}