package com.tivit.inventariodmt.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import com.tivit.inventariodmt.dto.DepartamentoDTO;
import com.tivit.inventariodmt.dto.FabricanteDTO;
import com.tivit.inventariodmt.dto.LocalidadeDTO;
import com.tivit.inventariodmt.dto.StatusDTO;
import com.tivit.inventariodmt.dto.TipoEquipamentoDTO;
import com.tivit.inventariodmt.dto.UsuarioDTO;

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
        List<LocalidadeDTO> lL = new ArrayList<>();
        Cursor cursor = getDb().rawQuery("SELECT inv_FS_Loc_Id_Localidade, inv_FS_Loc_Endereco, Inv_FS_Loc_Descricao, inv_FS_Loc_cidade FROM inv_FS_Localidade", null);
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
                f.setInv_FS_Fab_Nome_Fabricante(cursor.getString(0));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return lF   ;
    }

    public List<String> listarModelos(int codigoFabricante) {
        this.modelos = new ArrayList<>();

        Cursor cursor = getDb().rawQuery("SELECT inv_FS_Mod_Nome_Modelo FROM inv_FS_Modelo WHERE _id = " +codigoFabricante, null);
        cursor.moveToFirst();
        if (cursor.getCount() > 0) {
            while (cursor.moveToFirst()) {
                String modelo = cursor.getString(0);
                modelos.add(modelo);
            }
        }
        cursor.close();
        return modelos;
    }

    public List<UsuarioDTO> listarUsuarios() {
        List<UsuarioDTO> lU = new ArrayList<>();

        Cursor cursor = getDb().rawQuery("SELECT inv_FS_usf_id_usuario, inv_FS_usf_Nome FROM inv_FS_Usuario_Final", null);
        cursor.moveToFirst();
        if (cursor.getCount() > 0) {
            do {
                UsuarioDTO u = new UsuarioDTO();
                u.setInv_fs_ic_Id_Organizacao(cursor.getInt(0));
                u.setInv_FS_usf_Nome(cursor.getString(2));

            } while (cursor.moveToNext());
        }
        cursor.close();
        return lU;
    }
}