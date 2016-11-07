package com.tivit.inventariodmt.dataconsistency.provider;

import android.content.ContentProvider;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.text.TextUtils;

import com.tivit.inventariodmt.dao.DatabaseHelper;

/**
 * Created by kaique.rocha on 25/10/2016.
 */

public class EquipamentoProvider extends ContentProvider {


    //NOME DO BANCO DE DADOS LOCAL
    private static final String DATABASE_NAME_NEW = "inventariator.db";

    //VERSÃO DA BASE DE DADOS LOCAL

    private static final int DATABASE_VERSION_NEW = 1;

    private ContentResolver resolver;

    private DatabaseHelper databaseHelper;

    @Override
    public boolean onCreate() {
        // Inicializando gestor BD
        databaseHelper = new DatabaseHelper(getContext(), DATABASE_NAME_NEW, null, DATABASE_VERSION_NEW);
        resolver = getContext().getContentResolver();
        return true;
    }

    public Cursor query(
            Uri uri,
            String[] projection,
            String selection,
            String[] selectionArgs,
            String sortOrder) {

        // Obtener base de datos
        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        // Comparar Uri
        int match = EquipamentoContract.uriMatcher.match(uri);

        Cursor c;

        switch (match) {
            case EquipamentoContract.ALLROWS:
                // Consultando todos los registros
                c = db.query(EquipamentoContract.EQUIPAMENTO, projection,
                        selection, selectionArgs,
                        null, null, sortOrder);
                c.setNotificationUri(
                        resolver,
                        EquipamentoContract.CONTENT_URI);
                break;
            case EquipamentoContract.SINGLE_ROW:
                // Consultando un solo registro basado en el Id del Uri
                long idEquipamento = ContentUris.parseId(uri);
                c = db.query(EquipamentoContract.EQUIPAMENTO, projection,
                        EquipamentoContract.Columnas._ID + " = " + idEquipamento,
                        selectionArgs, null, null, sortOrder);
                c.setNotificationUri(
                        resolver,
                        EquipamentoContract.CONTENT_URI);
                break;
            default:
                throw new IllegalArgumentException("URI não suportada: " + uri);
        }
        return c;
    }

    @Override
    public String getType(Uri uri) {
        switch (EquipamentoContract.uriMatcher.match(uri)) {
            case EquipamentoContract.ALLROWS:
                return EquipamentoContract.MULTIPLE_MIME;
            case EquipamentoContract.SINGLE_ROW:
                return EquipamentoContract.SINGLE_MIME;
            default:
                throw new IllegalArgumentException("Equipamento desconhecido: " + uri);
        }
    }

    public Uri insert(Uri uri, ContentValues values) {
        // Validar la uri
        if (EquipamentoContract.uriMatcher.match(uri) != EquipamentoContract.ALLROWS) {
            throw new IllegalArgumentException("URI desconocida : " + uri);
        }
        ContentValues contentValues;
        if (values != null) {
            contentValues = new ContentValues(values);
        } else {
            contentValues = new ContentValues();
        }

        // Inserción de nueva fila
        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        long rowId = db.insert(EquipamentoContract.EQUIPAMENTO, null, contentValues);
        if (rowId > 0) {
            Uri uri_gasto = ContentUris.withAppendedId(
                    EquipamentoContract.CONTENT_URI, rowId);
            resolver.notifyChange(uri_gasto, null, false);
            return uri_gasto;
        }
        throw new SQLException("Erro ao inserir: " + uri);
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {

        SQLiteDatabase db = databaseHelper.getWritableDatabase();

        int match = EquipamentoContract.uriMatcher.match(uri);
        int affected;

        switch (match) {
            case EquipamentoContract.ALLROWS:
                affected = db.delete(EquipamentoContract.EQUIPAMENTO, selection, selectionArgs);
                break;
            case EquipamentoContract.SINGLE_ROW:
                long idGasto = ContentUris.parseId(uri);
                affected = db.delete(EquipamentoContract.EQUIPAMENTO,
                        EquipamentoContract.Columnas.ID_REMOTA + "=" + idGasto + (!TextUtils.isEmpty(selection) ? " AND (" + selection + ')' : ""),
                        selectionArgs);
                // Notificar cambio asociado a la uri
                resolver.
                        notifyChange(uri, null, false);
                break;
            default:
                throw new IllegalArgumentException("Equipamento desconhecido: " + uri);
        }
        return affected;
    }

    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {

        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        int affected;
        switch (EquipamentoContract.uriMatcher.match(uri)) {
            case EquipamentoContract.ALLROWS:
                affected = db.update(EquipamentoContract.EQUIPAMENTO, values,
                        selection, selectionArgs);
                break;
            case EquipamentoContract.SINGLE_ROW:
                String idGasto = uri.getPathSegments().get(1);
                affected = db.update(EquipamentoContract.EQUIPAMENTO, values,
                        EquipamentoContract.Columnas.ID_REMOTA + "=" + idGasto
                                + (!TextUtils.isEmpty(selection) ?
                                " AND (" + selection + ')' : ""),
                        selectionArgs);
                break;
            default:
                throw new IllegalArgumentException("URI desconhecida: " + uri);
        }
        resolver.notifyChange(uri, null, false);
        return affected;
    }

}
