package com.tivit.inventariodmt.dto;

/**
 * Created by kaique.rocha on 19/10/2016.
 */

public class TipoEquipamentoDTO {
    private int inv_FS_TP_Id_Tipo_Equipamento;
    private String inv_FS_TP_Nome_Equipamento;
    private int inv_FS_TP_Kit_Instalacao;
    private String inv_FS_TP_Descricao;

    public int getInv_FS_TP_Id_Tipo_Equipamento() {
        return inv_FS_TP_Id_Tipo_Equipamento;
    }

    public void setInv_FS_TP_Id_Tipo_Equipamento(int inv_FS_TP_Id_Tipo_Equipamento) {
        this.inv_FS_TP_Id_Tipo_Equipamento = inv_FS_TP_Id_Tipo_Equipamento;
    }

    public String getInv_FS_TP_Nome_Equipamento() {
        return inv_FS_TP_Nome_Equipamento;
    }

    public void setInv_FS_TP_Nome_Equipamento(String inv_FS_TP_Nome_Equipamento) {
        this.inv_FS_TP_Nome_Equipamento = inv_FS_TP_Nome_Equipamento;
    }

    public int getInv_FS_TP_Kit_Instalacao() {
        return inv_FS_TP_Kit_Instalacao;
    }

    public void setInv_FS_TP_Kit_Instalacao(int inv_FS_TP_Kit_Instalacao) {
        this.inv_FS_TP_Kit_Instalacao = inv_FS_TP_Kit_Instalacao;
    }

    public String getInv_FS_TP_Descricao() {
        return inv_FS_TP_Descricao;
    }

    public void setInv_FS_TP_Descricao(String inv_FS_TP_Descricao) {
        this.inv_FS_TP_Descricao = inv_FS_TP_Descricao;
    }

    @Override
    public String toString() {
        return inv_FS_TP_Nome_Equipamento;
    }
}
