package com.tivit.inventariodmt.dto;

/**
 * Created by kaique.rocha on 19/10/2016.
 */

public class LocalidadeDTO {
    private int inv_FS_Loc_Id_Localidade; 
    private String inv_FS_Loc_Endereco;
    private String inv_FS_Loc_Descricao;
    private String inv_FS_Loc_cep;
    private String inv_FS_Loc_cidade;
    private String inv_FS_Loc_estado;
    private String inv_FS_Loc_nome_localidade;

    public int getInv_FS_Loc_Id_Localidade() {
        return inv_FS_Loc_Id_Localidade;
    }

    public void setInv_FS_Loc_Id_Localidade(int inv_FS_Loc_Id_Localidade) {
        this.inv_FS_Loc_Id_Localidade = inv_FS_Loc_Id_Localidade;
    }

    public String getInv_FS_Loc_Endereco() {
        return inv_FS_Loc_Endereco;
    }

    public void setInv_FS_Loc_Endereco(String inv_FS_Loc_Endereco) {
        this.inv_FS_Loc_Endereco = inv_FS_Loc_Endereco;
    }

    public String getInv_FS_Loc_Descricao() {
        return inv_FS_Loc_Descricao;
    }

    public void setInv_FS_Loc_Descricao(String inv_FS_Loc_Descricao) {
        this.inv_FS_Loc_Descricao = inv_FS_Loc_Descricao;
    }

    public String getInv_FS_Loc_cep() {
        return inv_FS_Loc_cep;
    }

    public void setInv_FS_Loc_cep(String inv_FS_Loc_cep) {
        this.inv_FS_Loc_cep = inv_FS_Loc_cep;
    }

    public String getInv_FS_Loc_cidade() {
        return inv_FS_Loc_cidade;
    }

    public void setInv_FS_Loc_cidade(String inv_FS_Loc_cidade) {
        this.inv_FS_Loc_cidade = inv_FS_Loc_cidade;
    }

    public String getInv_FS_Loc_estado() {
        return inv_FS_Loc_estado;
    }

    public void setInv_FS_Loc_estado(String inv_FS_Loc_estado) {
        this.inv_FS_Loc_estado = inv_FS_Loc_estado;
    }

    public String getInv_FS_Loc_nome_localidade() {
        return inv_FS_Loc_nome_localidade;
    }

    public void setInv_FS_Loc_nome_localidade(String inv_FS_Loc_nome_localidade) {
        this.inv_FS_Loc_nome_localidade = inv_FS_Loc_nome_localidade;
    }

    @Override
    public String toString() {
        return getInv_FS_Loc_Descricao();
    }
}
