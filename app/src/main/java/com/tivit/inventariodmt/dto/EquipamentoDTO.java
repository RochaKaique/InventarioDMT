package com.tivit.inventariodmt.dto;

import java.util.Date;

/**
 * Created by kaique.rocha on 19/10/2016.
 */

public class EquipamentoDTO {
    
    public int _id;
    public String inv_fs_ic_numero_serie;
    public String inv_fs_ic_Nome_IC;
    public String inv_fs_ic_RFID;
    public String inv_fs_ic_Nota_fiscal;
    public String inv_fs_ic_Patrimonio;
    public Date inv_fs_ic_Data_compra;
    public Date inv_fs_ic_Data_Fim_Garantia;
    public String inv_fs_ic_contrato;
    public Date inv_fs_ic_Data_Fim_Leasing;
    public String inv_fs_ic_codigo_anterior;
    public String inv_fs_ic_mac_adress;
    public String inv_fs_ic_IP;
    public long inv_fs_ic_Data_Criacao;
    public Date inv_fs_ic_Data_Ultima_Atualizacao;
    public String inv_fs_ic_codigo_descarte;
    public int inv_fs_ic_Rede_Conectada;
    public String inv_fs_ic_Ult_Incidente;
    public String inv_fs_ic_Incidente_Externo;
    
    public int status_id;
    public int substatus_id;
    public int fabricante_id;
    public int modelo_id;
    public int fornecedor_id;
    public int tipo_equipamento_id;
    public int tipo_aquisicao_id;
    public int localidade_id;
    public int termo_id;
    public int organizacao_id;
    public int usuario_id;
    public int lote_compra_id;
    public int usuario_criacao_id;
    public int departamento_id;
    public int usuario_ult_atualizacao_id;
    public int kit_instalacao_id;
    public int estado;

    public EquipamentoDTO(int _id, String inv_fs_ic_numero_serie, String inv_fs_ic_Nome_IC, String inv_fs_ic_RFID, String inv_fs_ic_Nota_fiscal, String inv_fs_ic_Patrimonio, Date inv_fs_ic_Data_compra, Date inv_fs_ic_Data_Fim_Garantia, String inv_fs_ic_contrato, Date inv_fs_ic_Data_Fim_Leasing, String inv_fs_ic_codigo_anterior, String inv_fs_ic_mac_adress, String inv_fs_ic_IP, long inv_fs_ic_Data_Criacao, Date inv_fs_ic_Data_Ultima_Atualizacao, String inv_fs_ic_codigo_descarte, int inv_fs_ic_Rede_Conectada, String inv_fs_ic_Ult_Incidente, String inv_fs_ic_Incidente_Externo, int status_id, int substatus_id, int fabricante_id, int modelo_id, int fornecedor_id, int tipo_equipamento_id, int tipo_aquisicao_id, int localidade_id, int termo_id, int organizacao_id, int usuario_id, int lote_compra_id, int usuario_criacao_id, int departamento_id, int usuario_ult_atualizacao_id, int kit_instalacao_id, int estado) {
        this._id = _id;
        this.inv_fs_ic_numero_serie = inv_fs_ic_numero_serie;
        this.inv_fs_ic_Nome_IC = inv_fs_ic_Nome_IC;
        this.inv_fs_ic_RFID = inv_fs_ic_RFID;
        this.inv_fs_ic_Nota_fiscal = inv_fs_ic_Nota_fiscal;
        this.inv_fs_ic_Patrimonio = inv_fs_ic_Patrimonio;
        this.inv_fs_ic_Data_compra = inv_fs_ic_Data_compra;
        this.inv_fs_ic_Data_Fim_Garantia = inv_fs_ic_Data_Fim_Garantia;
        this.inv_fs_ic_contrato = inv_fs_ic_contrato;
        this.inv_fs_ic_Data_Fim_Leasing = inv_fs_ic_Data_Fim_Leasing;
        this.inv_fs_ic_codigo_anterior = inv_fs_ic_codigo_anterior;
        this.inv_fs_ic_mac_adress = inv_fs_ic_mac_adress;
        this.inv_fs_ic_IP = inv_fs_ic_IP;
        this.inv_fs_ic_Data_Criacao = inv_fs_ic_Data_Criacao;
        this.inv_fs_ic_Data_Ultima_Atualizacao = inv_fs_ic_Data_Ultima_Atualizacao;
        this.inv_fs_ic_codigo_descarte = inv_fs_ic_codigo_descarte;
        this.inv_fs_ic_Rede_Conectada = inv_fs_ic_Rede_Conectada;
        this.inv_fs_ic_Ult_Incidente = inv_fs_ic_Ult_Incidente;
        this.inv_fs_ic_Incidente_Externo = inv_fs_ic_Incidente_Externo;
        this.status_id = status_id;
        this.substatus_id = substatus_id;
        this.fabricante_id = fabricante_id;
        this.modelo_id = modelo_id;
        this.fornecedor_id = fornecedor_id;
        this.tipo_equipamento_id = tipo_equipamento_id;
        this.tipo_aquisicao_id = tipo_aquisicao_id;
        this.localidade_id = localidade_id;
        this.termo_id = termo_id;
        this.organizacao_id = organizacao_id;
        this.usuario_id = usuario_id;
        this.lote_compra_id = lote_compra_id;
        this.usuario_criacao_id = usuario_criacao_id;
        this.departamento_id = departamento_id;
        this.usuario_ult_atualizacao_id = usuario_ult_atualizacao_id;
        this.kit_instalacao_id = kit_instalacao_id;
        this.estado = estado;
    }

    public EquipamentoDTO(){}

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }

    public String getInv_fs_ic_numero_serie() {
        return inv_fs_ic_numero_serie;
    }

    public void setInv_fs_ic_numero_serie(String inv_fs_ic_numero_serie) {
        this.inv_fs_ic_numero_serie = inv_fs_ic_numero_serie;
    }

    public String getInv_fs_ic_Nome_IC() {
        return inv_fs_ic_Nome_IC;
    }

    public void setInv_fs_ic_Nome_IC(String inv_fs_ic_Nome_IC) {
        this.inv_fs_ic_Nome_IC = inv_fs_ic_Nome_IC;
    }

    public String getInv_fs_ic_RFID() {
        return inv_fs_ic_RFID;
    }

    public void setInv_fs_ic_RFID(String inv_fs_ic_RFID) {
        this.inv_fs_ic_RFID = inv_fs_ic_RFID;
    }

    public String getInv_fs_ic_Nota_fiscal() {
        return inv_fs_ic_Nota_fiscal;
    }

    public void setInv_fs_ic_Nota_fiscal(String inv_fs_ic_Nota_fiscal) {
        this.inv_fs_ic_Nota_fiscal = inv_fs_ic_Nota_fiscal;
    }

    public String getInv_fs_ic_Patrimonio() {
        return inv_fs_ic_Patrimonio;
    }

    public void setInv_fs_ic_Patrimonio(String inv_fs_ic_Patrimonio) {
        this.inv_fs_ic_Patrimonio = inv_fs_ic_Patrimonio;
    }

    public Date getInv_fs_ic_Data_compra() {
        return inv_fs_ic_Data_compra;
    }

    public void setInv_fs_ic_Data_compra(Date inv_fs_ic_Data_compra) {
        this.inv_fs_ic_Data_compra = inv_fs_ic_Data_compra;
    }

    public Date getInv_fs_ic_Data_Fim_Garantia() {
        return inv_fs_ic_Data_Fim_Garantia;
    }

    public void setInv_fs_ic_Data_Fim_Garantia(Date inv_fs_ic_Data_Fim_Garantia) {
        this.inv_fs_ic_Data_Fim_Garantia = inv_fs_ic_Data_Fim_Garantia;
    }

    public String getInv_fs_ic_contrato() {
        return inv_fs_ic_contrato;
    }

    public void setInv_fs_ic_contrato(String inv_fs_ic_contrato) {
        this.inv_fs_ic_contrato = inv_fs_ic_contrato;
    }

    public Date getInv_fs_ic_Data_Fim_Leasing() {
        return inv_fs_ic_Data_Fim_Leasing;
    }

    public void setInv_fs_ic_Data_Fim_Leasing(Date inv_fs_ic_Data_Fim_Leasing) {
        this.inv_fs_ic_Data_Fim_Leasing = inv_fs_ic_Data_Fim_Leasing;
    }

    public String getInv_fs_ic_codigo_anterior() {
        return inv_fs_ic_codigo_anterior;
    }

    public void setInv_fs_ic_codigo_anterior(String inv_fs_ic_codigo_anterior) {
        this.inv_fs_ic_codigo_anterior = inv_fs_ic_codigo_anterior;
    }

    public String getInv_fs_ic_mac_adress() {
        return inv_fs_ic_mac_adress;
    }

    public void setInv_fs_ic_mac_adress(String inv_fs_ic_mac_adress) {
        this.inv_fs_ic_mac_adress = inv_fs_ic_mac_adress;
    }

    public String getInv_fs_ic_IP() {
        return inv_fs_ic_IP;
    }

    public void setInv_fs_ic_IP(String inv_fs_ic_IP) {
        this.inv_fs_ic_IP = inv_fs_ic_IP;
    }

    public long getInv_fs_ic_Data_Criacao() {
        return inv_fs_ic_Data_Criacao;
    }

    public void setInv_fs_ic_Data_Criacao(long inv_fs_ic_Data_Criacao) {
        this.inv_fs_ic_Data_Criacao = inv_fs_ic_Data_Criacao;
    }

    public Date getInv_fs_ic_Data_Ultima_Atualizacao() {
        return inv_fs_ic_Data_Ultima_Atualizacao;
    }

    public void setInv_fs_ic_Data_Ultima_Atualizacao(Date inv_fs_ic_Data_Ultima_Atualizacao) {
        this.inv_fs_ic_Data_Ultima_Atualizacao = inv_fs_ic_Data_Ultima_Atualizacao;
    }

    public String getInv_fs_ic_codigo_descarte() {
        return inv_fs_ic_codigo_descarte;
    }

    public void setInv_fs_ic_codigo_descarte(String inv_fs_ic_codigo_descarte) {
        this.inv_fs_ic_codigo_descarte = inv_fs_ic_codigo_descarte;
    }

    public int getInv_fs_ic_Rede_Conectada() {
        return inv_fs_ic_Rede_Conectada;
    }

    public void setInv_fs_ic_Rede_Conectada(int inv_fs_ic_Rede_Conectada) {
        this.inv_fs_ic_Rede_Conectada = inv_fs_ic_Rede_Conectada;
    }

    public String getInv_fs_ic_Ult_Incidente() {
        return inv_fs_ic_Ult_Incidente;
    }

    public void setInv_fs_ic_Ult_Incidente(String inv_fs_ic_Ult_Incidente) {
        this.inv_fs_ic_Ult_Incidente = inv_fs_ic_Ult_Incidente;
    }

    public String getInv_fs_ic_Incidente_Externo() {
        return inv_fs_ic_Incidente_Externo;
    }

    public void setInv_fs_ic_Incidente_Externo(String inv_fs_ic_Incidente_Externo) {
        this.inv_fs_ic_Incidente_Externo = inv_fs_ic_Incidente_Externo;
    }

    public int getStatus_id() {
        return status_id;
    }

    public void setStatus_id(int status_id) {
        this.status_id = status_id;
    }

    public int getSubstatus_id() {
        return substatus_id;
    }

    public void setSubstatus_id(int substatus_id) {
        this.substatus_id = substatus_id;
    }

    public int getFabricante_id() {
        return fabricante_id;
    }

    public void setFabricante_id(int fabricante_id) {
        this.fabricante_id = fabricante_id;
    }

    public int getModelo_id() {
        return modelo_id;
    }

    public void setModelo_id(int modelo_id) {
        this.modelo_id = modelo_id;
    }

    public int getFornecedor_id() {
        return fornecedor_id;
    }

    public void setFornecedor_id(int fornecedor_id) {
        this.fornecedor_id = fornecedor_id;
    }

    public int getTipo_equipamento_id() {
        return tipo_equipamento_id;
    }

    public void setTipo_equipamento_id(int tipo_equipamento_id) {
        this.tipo_equipamento_id = tipo_equipamento_id;
    }

    public int getTipo_aquisicao_id() {
        return tipo_aquisicao_id;
    }

    public void setTipo_aquisicao_id(int tipo_aquisicao_id) {
        this.tipo_aquisicao_id = tipo_aquisicao_id;
    }

    public int getLocalidade_id() {
        return localidade_id;
    }

    public void setLocalidade_id(int localidade_id) {
        this.localidade_id = localidade_id;
    }

    public int getTermo_id() {
        return termo_id;
    }

    public void setTermo_id(int termo_id) {
        this.termo_id = termo_id;
    }

    public int getOrganizacao_id() {
        return organizacao_id;
    }

    public void setOrganizacao_id(int organizacao_id) {
        this.organizacao_id = organizacao_id;
    }

    public int getUsuario_id() {
        return usuario_id;
    }

    public void setUsuario_id(int usuario_id) {
        this.usuario_id = usuario_id;
    }

    public int getLote_compra_id() {
        return lote_compra_id;
    }

    public void setLote_compra_id(int lote_compra_id) {
        this.lote_compra_id = lote_compra_id;
    }

    public int getUsuario_criacao_id() {
        return usuario_criacao_id;
    }

    public void setUsuario_criacao_id(int usuario_criacao_id) {
        this.usuario_criacao_id = usuario_criacao_id;
    }

    public int getDepartamento_id() {
        return departamento_id;
    }

    public void setDepartamento_id(int departamento_id) {
        this.departamento_id = departamento_id;
    }

    public int getUsuario_ult_atualizacao_id() {
        return usuario_ult_atualizacao_id;
    }

    public void setUsuario_ult_atualizacao_id(int usuario_ult_atualizacao_id) {
        this.usuario_ult_atualizacao_id = usuario_ult_atualizacao_id;
    }

    public int getKit_instalacao_id() {
        return kit_instalacao_id;
    }

    public void setKit_instalacao_id(int kit_instalacao_id) {
        this.kit_instalacao_id = kit_instalacao_id;
    }



    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }
}
