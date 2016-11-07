package com.tivit.inventariodmt.dto;

/**
 * Created by Kaique on 20/10/2016.
 */

public class FabricanteDTO {
    private int _id;
    private String inv_FS_Fab_Nome_Fabricante;
    private String inv_FS_Fab_Descricao;

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String getInv_FS_Fab_Nome_Fabricante() {
        return inv_FS_Fab_Nome_Fabricante;
    }

    public void setInv_FS_Fab_Nome_Fabricante(String inv_FS_Fab_Nome_Fabricante) {
        this.inv_FS_Fab_Nome_Fabricante = inv_FS_Fab_Nome_Fabricante;
    }

    public String getInv_FS_Fab_Descricao() {
        return inv_FS_Fab_Descricao;
    }

    public void setInv_FS_Fab_Descricao(String inv_FS_Fab_Descricao) {
        this.inv_FS_Fab_Descricao = inv_FS_Fab_Descricao;
    }

    @Override
    public String toString() {
        return inv_FS_Fab_Nome_Fabricante;
    }
}
