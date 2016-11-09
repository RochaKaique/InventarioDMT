package com.tivit.inventariodmt;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.tivit.inventariodmt.dao.PreencheCombosDao;
import com.tivit.inventariodmt.dataconsistency.sync.SyncAdapter;
import com.tivit.inventariodmt.dto.LocalidadeDTO;

public class DownloadActivity extends AppCompatActivity {
    PreencheCombosDao combos;
    private Spinner localidade;
    public static String idLocalidade;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_download);
        this.combos = new PreencheCombosDao(this);
        comboLocalidade();

    }

    public void onClickDownload(View view)
    {
//        TextView txtResultado = (TextView) findViewById(R.id.tvResultado);
//
//        txtResultado.setText("Download Realizado");
//        txtResultado.setTextColor(Color.RED);
        try{
            idLocalidade = String.valueOf(((LocalidadeDTO) localidade.getSelectedItem()).getInv_FS_Loc_Id_Localidade());
            SyncAdapter.sincronizarAhora(getApplicationContext(), false);
            TextView txtResultado = (TextView) findViewById(R.id.tvResultado);
            txtResultado.setText("Download Realizado");
            txtResultado.setTextColor(Color.GREEN);
        }catch(Exception ex){
            Log.e("Download", "Erro ao Baixar o dados da localidade");
        }
    }

    public void comboLocalidade() {
        ArrayAdapter<LocalidadeDTO> adLocalidade = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, combos.listarLocalidades());
        localidade = (Spinner) findViewById(R.id.spLocalidade);
        localidade.setAdapter(adLocalidade);
    }
}
