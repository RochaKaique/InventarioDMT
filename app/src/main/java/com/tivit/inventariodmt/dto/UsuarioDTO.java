package com.tivit.inventariodmt.dto;

import java.util.Date;

/**
 * Created by Kaique on 20/10/2016.
 */

public class UsuarioDTO {
    private int inv_us_id_Usuario;
    private String inv_us_Nome;
    private String inv_us_Login;
    private String inv_us_Senha;
    private int inv_us_Perfil;
    private String inv_us_Email;
    private int inv_us_Ativo;

    public int getInv_us_id_Usuario() {
        return inv_us_id_Usuario;
    }

    public void setInv_us_id_Usuario(int inv_us_id_Usuario) {
        this.inv_us_id_Usuario = inv_us_id_Usuario;
    }

    public String getInv_us_Nome() {
        return inv_us_Nome;
    }

    public void setInv_us_Nome(String inv_us_Nome) {
        this.inv_us_Nome = inv_us_Nome;
    }

    public String getInv_us_Login() {
        return inv_us_Login;
    }

    public void setInv_us_Login(String inv_us_Login) {
        this.inv_us_Login = inv_us_Login;
    }

    public String getInv_us_Senha() {
        return inv_us_Senha;
    }

    public void setInv_us_Senha(String inv_us_Senha) {
        this.inv_us_Senha = inv_us_Senha;
    }

    public int getInv_us_Perfil() {
        return inv_us_Perfil;
    }

    public void setInv_us_Perfil(int inv_us_Perfil) {
        this.inv_us_Perfil = inv_us_Perfil;
    }

    public String getInv_us_Email() {
        return inv_us_Email;
    }

    public void setInv_us_Email(String inv_us_Email) {
        this.inv_us_Email = inv_us_Email;
    }

    public int getInv_us_Ativo() {
        return inv_us_Ativo;
    }

    public void setInv_us_Ativo(int inv_us_Ativo) {
        this.inv_us_Ativo = inv_us_Ativo;
    }

}