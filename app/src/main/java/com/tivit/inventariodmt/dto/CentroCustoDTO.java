package com.tivit.inventariodmt.dto;

/**
 * Created by kaique.rocha on 09/03/2017.
 */

public class CentroCustoDTO {
    private int inv_FS_CC_Id_Centro_Custo;
    private String inv_FS_CC_Nome_Centro_Custo;
    private String inv_FS_CC_Descricao;
    private int inv_fs_CC_Id_Departamento;

    public int getInv_FS_CC_Id_Centro_Custo() {
        return inv_FS_CC_Id_Centro_Custo;
    }

    public void setInv_FS_CC_Id_Centro_Custo(int inv_FS_CC_Id_Centro_Custo) {
        this.inv_FS_CC_Id_Centro_Custo = inv_FS_CC_Id_Centro_Custo;
    }

    public String getInv_FS_CC_Nome_Centro_Custo() {
        return inv_FS_CC_Nome_Centro_Custo;
    }

    public void setInv_FS_CC_Nome_Centro_Custo(String inv_FS_CC_Nome_Centro_Custo) {
        this.inv_FS_CC_Nome_Centro_Custo = inv_FS_CC_Nome_Centro_Custo;
    }

    public String getInv_FS_CC_Descricao() {
        return inv_FS_CC_Descricao;
    }

    public void setInv_FS_CC_Descricao(String inv_FS_CC_Descricao) {
        this.inv_FS_CC_Descricao = inv_FS_CC_Descricao;
    }

    public int getInv_fs_CC_Id_Departamento() {
        return inv_fs_CC_Id_Departamento;
    }

    public void setInv_fs_CC_Id_Departamento(int inv_fs_CC_Id_Departamento) {
        this.inv_fs_CC_Id_Departamento = inv_fs_CC_Id_Departamento;
    }
}
