package com.tivit.inventariodmt;

import android.database.Cursor;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.tivit.inventariodmt.dataconsistency.provider.EquipamentoContract;
import com.tivit.inventariodmt.dataconsistency.provider.LocalidadeContract;
import com.tivit.inventariodmt.dataconsistency.sync.SyncAdapter;

public class TestActivity extends AppCompatActivity {

    private static final String[] LOCALIDADE_PROJECTION = new String[]{
            LocalidadeContract.Colunas._ID,
            LocalidadeContract.Colunas.CEP,
            LocalidadeContract.Colunas.CIDADE,
            LocalidadeContract.Colunas.DESCRICAO,
            LocalidadeContract.Colunas.ENDERECO,
            LocalidadeContract.Colunas.ESTADO,
            LocalidadeContract.Colunas.ESTADO_SINC,
            //LocalidadeContract.Colunas.ID_REMOTA,
            //LocalidadeContract.Colunas.INSERT_PENDING,
            LocalidadeContract.Colunas.NOME,
    };



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        downloadTeste();
    }

    private void downloadTeste() {
        SyncAdapter.sincronizarAhora(getApplicationContext(), false, 2);
//        Uri uri = LocalidadeContract.CONTENT_URI;
//        Cursor cursor = getContentResolver().query(uri,LOCALIDADE_PROJECTION, null, null, null);
//        cursor.moveToFirst();

    }

}
