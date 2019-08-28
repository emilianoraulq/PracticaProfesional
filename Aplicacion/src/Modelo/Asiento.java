/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

import java.sql.Date;

/**
 *
 * @author Schefer
 */
public class Asiento {
    private int idasiento;
    private Date fechacontable;
    private int tipoasiento;
    private int okcarga;
    private int registrado;
    private int inflacion;

    public int getIdasiento() {
        return idasiento;
    }

    public void setIdasiento(int idasiento) {
        this.idasiento = idasiento;
    }

    public Date getFechacontable() {
        return fechacontable;
    }

    public void setFechacontable(Date fechacontable) {
        this.fechacontable = fechacontable;
    }

    public int getTipoasiento() {
        return tipoasiento;
    }

    public void setTipoasiento(int tipoasiento) {
        this.tipoasiento = tipoasiento;
    }

    public int getOkcarga() {
        return okcarga;
    }

    public void setOkcarga(int okcarga) {
        this.okcarga = okcarga;
    }

    public int getRegistrado() {
        return registrado;
    }

    public void setRegistrado(int registrado) {
        this.registrado = registrado;
    }

    public int getInflacion() {
        return inflacion;
    }

    public void setInflacion(int inflacion) {
        this.inflacion = inflacion;
    }
    
}
