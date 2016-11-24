package com.tivit.inventariodmt;

import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.itextpdf.text.DocumentException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import com.tivit.inventariodmt.dao.EquipamentoDAO;
import com.tivit.inventariodmt.dao.GeradorPdf;
import com.tivit.inventariodmt.dao.PreencheCombosDao;
import com.tivit.inventariodmt.dataconsistency.provider.EquipamentoContract;
import com.tivit.inventariodmt.dataconsistency.utils.Utilidades;
import com.tivit.inventariodmt.dto.LocalidadeDTO;

public class ContagemActivity extends AppCompatActivity {

    public static int ENABLE_BLUETOOTH = 1;
    public static int SELECT_PAIRED_DEVICE = 2;
    public static int SELECT_DISCOVERED_DEVICE = 3;

    private static String strRfid = "";
    private static int numLeituras = 0;
    private ProgressDialog progresso;
    private boolean verificaProgress;
    private Button acionarContagem;
    private Button finalizarContagem;
    private Button gerarRelatorio;
    PreencheCombosDao combos;
    private static TextView statusBluetooth;
    private static TextView quantidadeEncontrada;
    private static TextView quantidadeEquipamentosInicial;
    private static TextView labelQuantidadeEncontrada;
    private TextView valorLido;
    static Set<String> rfids;
    private BluetoothAdapter blueAdapter;
    private EquipamentoDAO equipamentoDAO;
    private List rfidValido;
    private List rfidInvalido;
    private Spinner localidade;


    private static final String[] PROJECTION = new String[]{
            EquipamentoContract.Columnas._ID,
            EquipamentoContract.Columnas.ID_REMOTA,
            EquipamentoContract.Columnas.N_SERIE,
            EquipamentoContract.Columnas.PATRIMONIO,
            EquipamentoContract.Columnas.RFID,
            EquipamentoContract.Columnas.TIPO,
            EquipamentoContract.Columnas.STATUS,
            EquipamentoContract.Columnas.DEPARTAMENTO,
            EquipamentoContract.Columnas.LOCALIDADE,
            EquipamentoContract.Columnas.DATA,
            EquipamentoContract.Columnas.ESTADO,
    };

    ConnectionThreadContagem connect;
    private static boolean leitura = false;

    private static final String ARQUIVO = "relatorioEquipamentos.pdf";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.combos = new PreencheCombosDao(this);
        super.onCreate(savedInstanceState);
        Utilidades.setTaskBarColored(this);
        setContentView(R.layout.activity_contagem);

        this.verificaProgress = false;
        this.equipamentoDAO = new EquipamentoDAO(this);
        this.rfids = new TreeSet<>();
        this.rfidValido = new ArrayList();
        this.rfidInvalido = new ArrayList();
        this.blueAdapter = BluetoothAdapter.getDefaultAdapter();

        acionarContagem = (Button) findViewById(R.id.btAcionarContagem);
        finalizarContagem = (Button) findViewById(R.id.btFinalizarContagem);
        gerarRelatorio = (Button) findViewById(R.id.btGerarRelatorio);
        statusBluetooth = (TextView) findViewById(R.id.tvStatusBluetooth);
        labelQuantidadeEncontrada = (TextView) findViewById(R.id.tvQuantidadeEncontrada);
        quantidadeEncontrada = (TextView) findViewById(R.id.tvValorLido);
        valorLido = (TextView) findViewById(R.id.tvValorLido);
        quantidadeEquipamentosInicial = (TextView) findViewById(R.id.tvTotalEquipamentosInicial);
        localidade = (Spinner) findViewById(R.id.spLocalidade);
        labelQuantidadeEncontrada.setVisibility(View.INVISIBLE);
        quantidadeEquipamentosInicial.setText(equipamentoDAO.contagemEquipamentos());
        quantidadeEncontrada.setVisibility(View.INVISIBLE);
        valorLido.setVisibility(View.INVISIBLE);
        gerarRelatorio.setVisibility(View.INVISIBLE);
        finalizarContagem.setVisibility(View.INVISIBLE);

        if (blueAdapter.isEnabled()) {
            statusBluetooth.setText(R.string.bluetooth_actived);
        }
        this.rfids = new TreeSet<>();

        populaCombos();
        LocalidadeDTO l = (LocalidadeDTO) localidade.getSelectedItem();
        Toast.makeText(this,l.toString(),Toast.LENGTH_LONG);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (verificaProgress == true) {
            this.progresso = ProgressDialog.show(this, "AGUARDE", "Processando...", false, true);
            if (progresso.isShowing()) {
                progresso.dismiss();
            }
        }

    }

    public void recebeRfids(String rfid) {
        //this.rfids = new TreeSet<>();
        rfids.add(rfid);
    }

    public void conectaBluetooth(View view) {

        if (blueAdapter.isEnabled()) {
            Intent intent = new Intent(this, DiscoveredDevices.class);
            startActivityForResult(intent, BluetoothActivity.SELECT_DISCOVERED_DEVICE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == ENABLE_BLUETOOTH) {
            if (resultCode == RESULT_OK) {
                statusBluetooth.setText("Bluetooth ativado.");
            } else {
                statusBluetooth.setText("Bluetooth não ativado.");
            }
        } else if (requestCode == SELECT_PAIRED_DEVICE || requestCode == SELECT_DISCOVERED_DEVICE) {
            if (resultCode == RESULT_OK) {
                statusBluetooth.setText("Você selecionou " + data.getStringExtra("btDevName") + "\n"
                        + data.getStringExtra("btDevAddress"));

                connect = new ConnectionThreadContagem(data.getStringExtra("btDevAddress"));
                connect.start();
            } else {
                statusBluetooth.setText("Nenhum dispositivo selecionado.");
            }
        }
    }

//    public static Handler handler = new Handler() {
//        @Override
//        public void handleMessage(Message msg) {
//            //if (leitura == true) {
//                Bundle bundle = msg.getData();
//                byte[] data = bundle.getByteArray("data");
//                String dataString = new String(data);
//
//                if (dataString.equals("---N"))
//                    statusBluetooth.setText("Ocorreu um erro durante a conexão.");
//                else if (dataString.equals("---S"))
//                    statusBluetooth.setText("Conectado.");
//                else {
//                    rfids.add(new String(data + "\n"));
//                    //recebeRfid.setText(new String(data));
//                    //MUDAR
//                    quantidadeEncontrada.setText("" + rfids.size());
//                }
//            //}
//        }
//    };

    public static Handler handler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            //recebeRfid.clearComposingText();
            Bundle bundle = msg.getData();
            byte[] data = bundle.getByteArray("data");
            String dataString = new String(data);

            if (dataString.equals("---N"))
                statusBluetooth.setText("Ocorreu um erro durante a conexão.");
            else if (dataString.equals("---S")) {
                statusBluetooth.setText("Conectado.");
                //isDeviceConnected = true;
            }
            else {
                String strData = new String(data);
                if(strRfid != strData)
                    strRfid = strRfid + strData;
//                recebeRfid.setText("");
                //recebeRfid.setText(strRfid.toUpperCase());
                if(strRfid != "" && strData.length() < 10)
                    numLeituras ++;
                if(numLeituras == 2) {
                    rfids.add(strRfid);
                    strRfid = "";
                    numLeituras = 0;

                }
            }
        }
    };

    public void acionaContagem(View view) {
        if (leitura == false) {
            enviarMensagem("L");
            acionarContagem.setText(R.string.pause_score);
            finalizarContagem.setVisibility(View.VISIBLE);
            leitura = true;
        }
        else if (leitura == true) {
            acionarContagem.setText(R.string.restart_score);
            leitura = false;
        }
    }

    public void enviarMensagem(String mensagem) {
        byte[] data = mensagem.getBytes();
        connect.write(data);
    }


    public void finalizarContagem(View view) {
        System.out.println("F I N A L I Z A R");
        leitura = false;
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.ending);
        builder.setMessage(R.string.confirm_end_score);
        builder.setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int id) {
                acionarContagem.setText(R.string.continue_reading);
            }
        });
        builder.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int id) {
                acionarContagem.setText(R.string.score_starting);
                verificarRfids();
                String idsValidos = "" +rfidInvalido.size();
                valorLido.setText(idsValidos);
                quantidadeEncontrada.setVisibility(View.VISIBLE);
                labelQuantidadeEncontrada.setVisibility(View.VISIBLE);
                valorLido.setVisibility(View.VISIBLE);
                gerarRelatorio.setVisibility(View.VISIBLE);
                int j = 0;
                while (j < rfidValido.size()) {
                    verificaProgress = true;
                    //equipamentoDAO.insertRfid("" + rfidValido.get(j));
                }
                rfidValido.clear();
                verificaProgress = false;
                finalizarContagem.setVisibility(View.INVISIBLE);
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public void verificarRfids() {
        Uri uri = EquipamentoContract.CONTENT_URI;

        Iterator i = rfids.iterator();
        //int loc = ((LocalidadeDTO) localidade.getSelectedItem()).getInv_FS_Loc_Id_Localidade();

        String selection = EquipamentoContract.Columnas.RFID + " = ?";
        String[] selectionArgs = new String[]{i.next().toString()};


        while (i.hasNext()) {
            Cursor c = getContentResolver().query(uri, PROJECTION, selection, selectionArgs, null);
            if (c.getCount() > 0) {
                rfidValido.add(i.next());
            } else {
                rfidInvalido.add(i.next());
            }
        }
    }

    public void gerarRelatorio(View view) throws DocumentException, IOException {
        GeradorPdf geraPdf = new GeradorPdf();
        geraPdf.criarPdf(ARQUIVO);
    }

    private void populaCombos()
    {
        ArrayAdapter<LocalidadeDTO> adLocalidade = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, combos.listarLocalidades());
        localidade = (Spinner) findViewById(R.id.spLocalidade);
        localidade.setAdapter(adLocalidade);
    }
}