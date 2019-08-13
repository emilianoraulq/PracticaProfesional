/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import Modelo.Asiento;
import Modelo.Conexion;
import Modelo.ConsultasAsientos;
import Modelo.Renglon;
import Modelo.Usuario;
import Vista.FormAsientos;
import Vista.FormPlanCuentas;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Schefer
 */
public class ControladorAsientos implements ActionListener, KeyListener {
    
    private FormAsientos form1;
    private ConsultasAsientos modelo;
    private Usuario usuario; 
    
    
    public ControladorAsientos(FormAsientos form1,ConsultasAsientos modelo,Usuario usuario){
    this.form1 = form1;
    this.modelo = modelo;
    this.usuario = usuario; 
    
    form1.botonCargarAsiento.addActionListener(this);
    form1.botonRetomarAsiento.addActionListener(this);
    form1.botonListar.addActionListener(this);
    form1.botonIniciarCarga.addActionListener(this);
    form1.botonNuevoRenglon.addActionListener(this);
    form1.botonModificarRenglon.addActionListener(this);
    form1.botonEliminarRenglon.addActionListener(this);
    form1.botonOK.addActionListener(this);
    form1.botonCancelar.addActionListener(this);
    form1.botonGrabar.addActionListener(this);
    
    
    
    }
    
     public void iniciar(){
    form1.setTitle("Empresa: "+Conexion.getNombreBase()+" - Usuario: " + usuario.getNombre() + " " + usuario.getApellido()+"- Menu de Asientos" );
    form1.setLocationRelativeTo(null);
    form1.setVisible(true);
    form1.panelRenglones.setVisible(false);
  
    }
    
    
    
    
    @Override
    public void actionPerformed(ActionEvent e) {
        
        if (e.getSource() == form1.botonCargarAsiento) {
            int numAsiento = modelo.cantidadAsientos()+1;
            form1.campoAsiento.setText(String.valueOf(numAsiento));
            
             Calendar c2 = new GregorianCalendar();
             form1.fechaContable.setCalendar(c2);
        }
        
        
        
        
        
        
        if (e.getSource() == form1.botonIniciarCarga) {
            //creo un asiento
            Asiento asiento = new Asiento();
            
            //tomo el id de asiento
            asiento.setIdasiento(Integer.parseInt(form1.campoAsiento.getText()));
            
            //tomo la fecha
            Date date1 = form1.fechaContable.getDate();
            long d1 = date1.getTime();
            java.sql.Date fechaContable = new java.sql.Date(d1);
            
            asiento.setFechacontable(fechaContable);
            
            //tomo el tipo de asiento
            
            if (form1.opcionApertura.isSelected()) {
            asiento.setTipoasiento(1);
            }
            if (form1.opcionNormal.isSelected()) {
            asiento.setTipoasiento(5);    
            }
            if(form1.opcionCierre.isSelected()) {
            asiento.setTipoasiento(9);    
            }
            
            //cuando esta todo bien cargado 
            asiento.setOkcarga(0);
            
            //registrado va de entrada en FALSE
            asiento.setRegistrado(0);
            
            //tomo si es ajustable o no por inflacion
            if (form1.checkInflacion.isSelected()) {
            asiento.setInflacion(1);
            }
            else{
            asiento.setInflacion(0);    
            }
            //cargo el asiento en la BBDD y muestro el panel de los renglones
            if (modelo.generarAsiento(asiento)) {
                form1.panelRenglones.setVisible(true);
                int numRenglon = modelo.cantidadRenglonesdelAsiento(Integer.parseInt(form1.campoAsiento.getText()));
                form1.campoNroRenglon.setText(String.valueOf(numRenglon));
            }
            
        }
        
        
        
        
        
        if (e.getSource() == form1.botonOK) {
            Renglon renglon = new Renglon();
            
            renglon.setIdRenglon(Integer.parseInt(form1.campoNroRenglon.getText()));
            renglon.setNroAsiento(Integer.parseInt(form1.campoAsiento.getText()));
            renglon.setNroCuenta(Integer.parseInt(form1.campoNroCuenta.getText()));
            renglon.setFechaVencimiento((java.sql.Date) form1.fechaVencimiento.getDate());
            renglon.setFechaOperacion((java.sql.Date) form1.fechaOperacion.getDate());
            renglon.setComprobante(form1.campoComprobante.getText());
            renglon.setLeyenda(form1.campoLeyenda.getText());
            if (form1.opcionDebe.isSelected()) {
                renglon.setDebeHaber(0);
            }
            if (form1.opcionHaber.isSelected()) {
                renglon.setDebeHaber(1);
            }
            renglon.setImporte(Double.parseDouble(form1.campoImporte.getText()));
            renglon.setSeccion(Integer.parseInt(form1.campoSeccion.getText()));
            renglon.setSucursal(Integer.parseInt(form1.campoSucursal.getText()));
            
            //guardo el renglon en la bbdd, lo muestro en la tabla y actualizo el campo DEBE y HABER
            
            if (modelo.ingresarRenglon(renglon)) {
                DefaultTableModel modeloTabla = modelo.llenarTablaRenglones(Integer.parseInt(form1.campoAsiento.getText()));
                double totalDebe = modelo.getTotalDebe(Integer.parseInt(form1.campoAsiento.getText()));
                double totalHaber = modelo.getTotalHaber(Integer.parseInt(form1.campoAsiento.getText()));
                form1.campoTotalDebe.setText(String.valueOf(totalDebe));
                form1.campoTotalHaber.setText(String.valueOf(totalHaber));
                
                
                
                
            }
            
            
            
            
            
        }
        
        
        
    }

    @Override
    public void keyTyped(KeyEvent e) {
     
    }

    @Override
    public void keyPressed(KeyEvent e) {
      
    }

    @Override
    public void keyReleased(KeyEvent e) {
       
    }
    
}
