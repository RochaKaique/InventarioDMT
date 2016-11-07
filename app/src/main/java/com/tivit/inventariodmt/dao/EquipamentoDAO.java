package com.tivit.inventariodmt.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.Date;

/**
 * Created by alex.almeida on 01/09/2016.
 */
public class EquipamentoDAO {

    private DatabaseHelper helper;
    private SQLiteDatabase db;

    public EquipamentoDAO(Context context) {
        helper = new DatabaseHelper(context);
    }

    private SQLiteDatabase getDb() {
        if (db == null) {
            db = helper.getReadableDatabase();
        }
        return db;
    }

    private void close() {
        helper.close();
    }

    public String contagemEquipamentos() {
        Cursor cursor = getDb().rawQuery("SELECT COUNT(inv_fs_ic_RFID) FROM Inv_FS_Item_config", null);
        String resposta = "" +cursor.getCount();
        close();
        return resposta;
    }

    public boolean findByRfid(String rfid) {
        Cursor cursor = getDb().rawQuery("SELECT * FROM Inv_FS_Item_config WHERE inv_fs_ic_RFID = " +rfid+ ";", null);
        if(cursor.getCount() < 0) {
            return false;
        }
        return true;
    }

    public boolean findByRfidInLocale(String rfid, int localidade) {
        Cursor cursor = getDb().rawQuery("SELECT * FROM Inv_FS_Item_config WHERE inv_fs_ic_RFID = " +rfid+ " and localidade_id = "+localidade+";", null);
        if(cursor.getCount() < 0) {
            return false;
        }
        return true;
    }

    public void insertRfid(String rfid) {
        Date dataAgora = new Date(System.currentTimeMillis());

        SQLiteDatabase db = helper.getWritableDatabase();
        ContentValues value = new ContentValues();
        value.put("rfid", rfid);
        value.put("data_verificacao", dataAgora.getTime());

        long resultado = db.insert("verificacao", null, value);
    }
}
