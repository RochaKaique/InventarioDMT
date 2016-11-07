package com.tivit.inventariodmt.dto;

/**
 * Created by Kaique on 20/10/2016.
 */

public class DepartamentoDTO {
    private int _id;
    private String inv_FS_Dep_Nome_Departamento;
    private String inv_FS_Dep_Descricao;
    private int organizacao_id;

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String getInv_FS_Dep_Nome_Departamento() {
        return inv_FS_Dep_Nome_Departamento;
    }

    public void setInv_FS_Dep_Nome_Departamento(String inv_FS_Dep_Nome_Departamento) {
        this.inv_FS_Dep_Nome_Departamento = inv_FS_Dep_Nome_Departamento;
    }

    public String getInv_FS_Dep_Descricao() {
        return inv_FS_Dep_Descricao;
    }

    public void setInv_FS_Dep_Descricao(String inv_FS_Dep_Descricao) {
        this.inv_FS_Dep_Descricao = inv_FS_Dep_Descricao;
    }

    public int getOrganizacao_id() {
        return organizacao_id;
    }

    public void setOrganizacao_id(int organizacao_id) {
        this.organizacao_id = organizacao_id;
    }


    @Override
    public String toString()
    {
        return inv_FS_Dep_Nome_Departamento;
    }
}
