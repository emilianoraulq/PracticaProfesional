/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

/**
 *
 * @author erqui
 */
public class Permiso {
    
    private int id;
    private int id_perfil_permisos;
    private int id_actividad;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId_perfil_permisos() {
        return id_perfil_permisos;
    }

    public void setId_perfil_permisos(int id_perfil_permisos) {
        this.id_perfil_permisos = id_perfil_permisos;
    }

    public int getId_actividad() {
        return id_actividad;
    }

    public void setId_actividad(int id_actividad) {
        this.id_actividad = id_actividad;
    }
    
    
    
}
