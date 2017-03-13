package com.tivit.inventariodmt;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.tivit.inventariodmt.dao.PreencheCombosDao;
import com.tivit.inventariodmt.dataconsistency.sync.SyncAdapter;
import com.tivit.inventariodmt.dataconsistency.utils.Utilidades;
import com.tivit.inventariodmt.dto.LocalidadeDTO;

public class DownloadActivity extends AppCompatActivity {
    PreencheCombosDao combos;
    private Spinner localidade;
    public static int resposta;
    public static String idLocalidade;
    public static TextView txtResultado;
    public static ProgressBar pgDownload;
    public static TextView tvProgress;
    public static boolean isSinc;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_download);
        Utilidades.setTaskBarColored(this);
        this.combos = new PreencheCombosDao(this);
        comboLocalidade();
        pgDownload = (ProgressBar) findViewById(R.id.pgDownload);
        tvProgress = (TextView) findViewById(R.id.tvProgress);
        txtResultado = (TextView) findViewById(R.id.tvResultado);

    }

    public void onClickDownload(View view)
    {
        txtResultado.setVisibility(View.INVISIBLE);
        pgDownload.setVisibility(View.VISIBLE);
        tvProgress.setVisibility(View.VISIBLE);
//        TextView txtResultado = (TextView) findViewById(R.id.tvResultado);
//
//        txtResultado.setText("Download Realizado");
//        txtResultado.setTextColor(Color.RED);

        idLocalidade = String.valueOf(((LocalidadeDTO) localidade.getSelectedItem()).getInv_FS_Loc_Id_Localidade());
        if(((LocalidadeDTO) localidade.getSelectedItem()).getInv_FS_Loc_Id_Localidade() > 0){
            SyncAdapter.sincronizarAhora(getApplicationContext(), false, 1);
        }
        else{
            Toast.makeText(this, "Selecione uma Locaidade", Toast.LENGTH_SHORT).show();
        }



    }

    public void comboLocalidade() {
        ArrayAdapter<LocalidadeDTO> adLocalidade = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, combos.listarLocalidades());
        localidade = (Spinner) findViewById(R.id.spLocalidade);
        localidade.setAdapter(adLocalidade);
    }
}
