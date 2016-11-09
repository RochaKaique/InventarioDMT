package com.tivit.inventariodmt.dto;

/**
 * Created by Kaique on 20/10/2016.
 */

public class DepartamentoDTO {
    private int inv_FS_Dep_Id_Departamento;
    private String inv_FS_Dep_Nome_Departamento;
    private String inv_FS_Dep_Descricao;
    private int inv_fs_ic_Id_Organizacao;

    public int getInv_FS_Dep_Id_Departamento() {
        return inv_FS_Dep_Id_Departamento;
    }

    public void setInv_FS_Dep_Id_Departamento(int inv_FS_Dep_Id_Departamento) {
        this.inv_FS_Dep_Id_Departamento = inv_FS_Dep_Id_Departamento;
    }

    public int getInv_fs_ic_Id_Organizacao() {
        return inv_fs_ic_Id_Organizacao;
    }

    public void setInv_fs_ic_Id_Organizacao(int inv_fs_ic_Id_Organizacao) {
        this.inv_fs_ic_Id_Organizacao = inv_fs_ic_Id_Organizacao;
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



    @Override
    public String toString()
    {
        return inv_FS_Dep_Nome_Departamento;
    }
}
