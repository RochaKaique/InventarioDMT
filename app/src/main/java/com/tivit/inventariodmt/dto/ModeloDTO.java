package com.tivit.inventariodmt.dto;

/**
 * Created by kaique.rocha on 16/11/2016.
 */

public class ModeloDTO {

    private int inv_FS_Mod_Id_Modelo;
    private String inv_FS_Mod_Nome_Modelo;
    private String inv_FS_Mod_Descricao;
    private int Inv_FS_Mod_Ano;
    private int inv_FS_Mod_Id_Fabricante;

    public int getInv_FS_Mod_Id_Modelo() {
        return inv_FS_Mod_Id_Modelo;
    }

    public void setInv_FS_Mod_Id_Modelo(int inv_FS_Mod_Id_Modelo) {
        this.inv_FS_Mod_Id_Modelo = inv_FS_Mod_Id_Modelo;
    }

    public String getInv_FS_Mod_Nome_Modelo() {
        return inv_FS_Mod_Nome_Modelo;
    }

    public void setInv_FS_Mod_Nome_Modelo(String inv_FS_Mod_Nome_Modelo) {
        this.inv_FS_Mod_Nome_Modelo = inv_FS_Mod_Nome_Modelo;
    }

    public String getInv_FS_Mod_Descricao() {
        return inv_FS_Mod_Descricao;
    }

    public void setInv_FS_Mod_Descricao(String inv_FS_Mod_Descricao) {
        this.inv_FS_Mod_Descricao = inv_FS_Mod_Descricao;
    }

    public int getInv_FS_Mod_Ano() {
        return Inv_FS_Mod_Ano;
    }

    public void setInv_FS_Mod_Ano(int Inv_FS_Mod_Ano) {
        this.Inv_FS_Mod_Ano = Inv_FS_Mod_Ano;
    }

    public int getInv_FS_Mod_Id_Fabricante() {
        return inv_FS_Mod_Id_Fabricante;
    }

    public void setInv_FS_Mod_Id_Fabricante(int inv_FS_Mod_Id_Fabricante) {
        this.inv_FS_Mod_Id_Fabricante = inv_FS_Mod_Id_Fabricante;
    }

    @Override
    public String toString(){
        return getInv_FS_Mod_Nome_Modelo();
    }

}
