
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

import com.mysql.jdbc.Connection;
import java.sql.DriverManager;
import javax.swing.JOptionPane;

/**
 *
 * @author Schefer
 */
public class Conexion {
    //public static final String URL = "jdbc:mysql://localhost:3306/practico1?autoreReconnet=true&useSSL=false";
    
    public static String nombreBase;
    public static String URL;
    public static String usuario;
    public static String contraseña; 
    
    public Conexion(){
        //this.nombreBase="ppstp1";
        this.URL="jdbc:mysql://localhost:3306/" + nombreBase + "?autoreReconnet=true&useSSL=false";
        this.usuario="root";
        this.contraseña="cejasc27";
    }

    public static String getNombreBase() {
        return nombreBase;
    }
    
    public static void setBaseUrl(String nombre) {
        URL = "jdbc:mysql://localhost:3306/" + nombre + "?autoreReconnet=true&useSSL=false";;
    }

    public static void setNombreBase(String nombre) {
        Conexion.nombreBase = nombre;
    }
    
   
    public static String getURL() {
        return URL;
    }

    public static String getUsuario() {
        return usuario;
    }

    public static  String getContraseña() {
        return contraseña;
    }
    
    
    
    public Connection getConnection(){
        Connection conexion = null;
        
        try{
        Class.forName("com.mysql.jdbc.Driver");
        conexion = (Connection) DriverManager.getConnection(URL,usuario,contraseña);
        //JOptionPane.showMessageDialog(null, URL);
            
        } catch(Exception ex){
        System.err.println("Error" + ex);
        }
        return conexion;
        
    }
    
    
    
    
    
}