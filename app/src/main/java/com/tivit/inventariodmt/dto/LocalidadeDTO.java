package com.tivit.inventariodmt.dto;

/**
 * Created by kaique.rocha on 19/10/2016.
 */

public class LocalidadeDTO {
    private int _id;
    private String inv_FS_Loc_Endereco;
    private String inv_FS_Loc_Descricao;
    private int cidade_id;

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
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

    public int getCidade_id() {
        return cidade_id;
    }

    public void setCidade_id(int cidade_id) {
        this.cidade_id = cidade_id;
    }

    @Override
    public String toString() {
        return getInv_FS_Loc_Descricao();
    }
}
