package com.tivit.inventariodmt.dto;

/**
 * Created by Kaique on 20/10/2016.
 */

public class StatusDTO {
    private int _id;
    private String inv_FS_St_Nome_Status;
    private String inv_FS_St_Descricao;

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String getInv_FS_St_Nome_Status() {
        return inv_FS_St_Nome_Status;
    }

    public void setInv_FS_St_Nome_Status(String inv_FS_St_Nome_Status) {
        this.inv_FS_St_Nome_Status = inv_FS_St_Nome_Status;
    }

    public String getInv_FS_St_Descricao() {
        return inv_FS_St_Descricao;
    }

    public void setInv_FS_St_Descricao(String inv_FS_St_Descricao) {
        this.inv_FS_St_Descricao = inv_FS_St_Descricao;
    }

    @Override
    public String toString() {
        return inv_FS_St_Nome_Status;
    }
}
