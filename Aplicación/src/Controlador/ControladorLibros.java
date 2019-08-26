/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import Modelo.Conexion;
import Modelo.ConsultasLibros;
import Modelo.ConsultasUsuario;
import Modelo.Usuario;
import Vista.FormLibroMayor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 *
 * @author erqui
 */
public class ControladorLibros implements ActionListener, KeyListener {
    
    
     private FormLibroMayor form1;
    private ConsultasLibros modelo;
    private ConsultasUsuario modeloUsuario;
    private Usuario usuario;
    
    
    
    public ControladorLibros(FormLibroMayor form1, ConsultasLibros modelo, ConsultasUsuario modeloUsuario, Usuario usuario){
    this.form1 = form1;
    this.modelo = modelo;
    this.usuario = usuario;
    this.modeloUsuario = modeloUsuario;
    
    form1.btnListarLibroMayor.addActionListener(this);
    
    
    }
    
    
    
    public void iniciar(){
    form1.setTitle("Empresa: "+Conexion.getNombreBase()+" - Usuario: " + usuario.getNombre() + " " + usuario.getApellido()+"- Menú de auditoría" );
    form1.setLocationRelativeTo(null);
    form1.setVisible(true);
    
    //la fecha desde y hasta puestas por defecto: 
    //desde: el primero del mes en curso
    //hasta: el dia actual
    int mes = Calendar.MONTH+2;

    Calendar c1 = new GregorianCalendar(2019,mes,25);
    Calendar c2 = new GregorianCalendar();
    form1.dateChooserHasta.setCalendar(c2);
    form1.dateChooserDesde.setCalendar(c1);
    
    }
    
    
    
    
    
    
    
    
    
    

    @Override
    public void actionPerformed(ActionEvent e) {
        
        if (e.getSource() == form1.btnListarLibroMayor){
            
            
            
            Date date1 = form1.dateChooserDesde.getDate();
            Date date2 = form1.dateChooserHasta.getDate();

                    long d1 = date1.getTime();
                    long d2 = date2.getTime();

                    //java.sql.Date fechaDesde = new java.sql.Date(d1);
                    // java.sql.Date fechaHasta = new java.sql.Date(d2);
                    java.sql.Timestamp fechaDesde = new java.sql.Timestamp(d1);
                    java.sql.Timestamp fechaHasta = new java.sql.Timestamp(d2);

            
             

                    modelo.LibroMayor(usuario, fechaDesde, fechaHasta);
       
           
            
            
        }
            
        
       
    }

    @Override
    public void keyTyped(KeyEvent e) {
       // throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void keyPressed(KeyEvent e) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void keyReleased(KeyEvent e) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
    

