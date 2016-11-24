package com.tivit.inventariodmt.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.tivit.inventariodmt.dataconsistency.utils.Criptografia;

/**
 * Created by kaique.rocha on 23/11/2016.
 */

public class UsuarioDAO {

    private DatabaseHelper helper;
    private SQLiteDatabase db;
    public UsuarioDAO(Context context) {
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

    public boolean autenticaUsuario(String usu, String senha){
        String senhaCrip = "";
        try {
            senhaCrip = Criptografia.encrypt(senha);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Cursor cursor = getDb().rawQuery("SELECT inv_us_id_Usuario, inv_us_Nome, inv_us_Login, inv_us_Senha, inv_us_Perfil, inv_us_Email, inv_us_Ativo FROM inv_Usuario WHERE inv_us_Login = '" + usu +"'",null);
        if(cursor.moveToNext())
        {
            String pass = cursor.getString(cursor.getColumnIndex("inv_us_Senha"));
            if(pass.equals(senhaCrip)) {
                return true;
            }
        }
        close();
        return false;
    }
}
