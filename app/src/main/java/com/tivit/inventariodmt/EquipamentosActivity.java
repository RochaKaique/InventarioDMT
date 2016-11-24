package com.tivit.inventariodmt;

import android.app.ListActivity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.widget.SimpleAdapter;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.tivit.inventariodmt.dao.DatabaseHelper;
import com.tivit.inventariodmt.dataconsistency.provider.EquipamentoContract;
import com.tivit.inventariodmt.dataconsistency.utils.Utilidades;

public class EquipamentosActivity extends ListActivity {

    private List<Map<String, Object>> equipamentos;
    private DatabaseHelper helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_equipamentos);
        //Utilidades.setTaskBarColored(this);
        String[] de = {"id", "serial", "patrimonio", "dataCriacao", "tipoEquipamento", "rfid"};
        int[] para = {R.id.id, R.id.serial, R.id.patrimonio, R.id.dataCriacao, R.id.tipoEquipamento, R.id.rfid};

        SimpleAdapter adapter = new SimpleAdapter(this, listarEquipamentos(), R.layout.activity_equipamentos, de, para);

        setListAdapter(adapter);
    }

    private List<Map<String, Object>> listarEquipamentos() {
        this.helper = new DatabaseHelper(this);
        equipamentos = new ArrayList<>();

        SQLiteDatabase db = helper.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT inv_fs_ic_Id_IC, inv_fs_ic_numero_serie, inv_fs_ic_Patrimonio, inv_fs_ic_Data_Criacao, inv_fs_ic_Id_Tipo_Equipamento, inv_fs_ic_RFID, inv_fs_ic_EstadoSinc, inv_fs_ic_insert_pending," +
                " inv_fs_ic_idRemoto FROM Inv_FS_Item_config", null);
        cursor.moveToFirst();

        for(int i = 0; i < cursor.getCount(); i++) {
            Map<String, Object> item = new HashMap<>();

            String id = cursor.getString(cursor.getColumnIndex(EquipamentoContract.Columnas._ID));
            String numeroSerie = cursor.getString(cursor.getColumnIndex(EquipamentoContract.Columnas.N_SERIE));
            String patrimonio = cursor.getString(cursor.getColumnIndex(EquipamentoContract.Columnas.PATRIMONIO));
            long dataCriacao = cursor.getLong(cursor.getColumnIndex(EquipamentoContract.Columnas.DATA));
            String tipoEquipamento = cursor.getString(cursor.getColumnIndex(EquipamentoContract.Columnas.TIPO));
            String rfid = cursor.getString(cursor.getColumnIndex(EquipamentoContract.Columnas.RFID));
            int estado = cursor.getInt(cursor.getColumnIndex(EquipamentoContract.Columnas.ESTADO));
            int pending = cursor.getInt(cursor.getColumnIndex(EquipamentoContract.Columnas.INSERT_PENDING));
            int remoto =  cursor.getInt(cursor.getColumnIndex(EquipamentoContract.Columnas.ID_REMOTA));

            AlertDialog.Builder msg = new AlertDialog.Builder(this);
            msg.setMessage("Série = "+numeroSerie+" Código = "+id+" Estado = " +estado+ " Pending = "+pending + "Remota =" + remoto);
            msg.show();

            item.put("id", id);
            item.put("serial", numeroSerie);
            item.put("patrimonio", patrimonio);


            DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

            //Date dataCriacaoDate = Date(dataCriacao);
            item.put("dataCriacao", dateFormat.format(dataCriacao));
            //item.put("tipoEquipamento", tipoEquipamento);
            item.put("rfid", rfid);

            if(tipoEquipamento == null) {
                tipoEquipamento = "1";
            }

            if(tipoEquipamento.equals("1")) {
                item.put("tipoEquipamento", R.drawable.ic_desktop_windows_black_24dp);
            }
            if (tipoEquipamento.equals("2")) {
                item.put("tipoEquipamento", R.drawable.ic_computer_black_24dp);
            }

            equipamentos.add(item);
            cursor.moveToNext();
        }
        if (!cursor.isClosed()) {
            cursor.close();
        }
        return equipamentos;
    }
}
