/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import Modelo.Conexion;
import Modelo.ConsultasNuevaEmpresa;
import Modelo.ConsultasUsuario;
import Modelo.Usuario;
import Vista.FormNuevaEmpresa;
import Vista.formLoading;
import java.awt.Dialog;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.logging.Handler;
import java.util.logging.Logger;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import static javax.swing.JOptionPane.NO_OPTION;

/**
 *
 * @author erqui
 */
public class ControladorNuevaEmpresa implements ActionListener, KeyListener {
    
    
    
    
    private FormNuevaEmpresa form1;
    private ConsultasNuevaEmpresa modeloEmpresa;
    
    
    public ControladorNuevaEmpresa(FormNuevaEmpresa form1, ConsultasNuevaEmpresa modeloEmpresa){
        this.form1=form1;
     
        this.modeloEmpresa=modeloEmpresa;
        
        form1.btnCrearEmpresa.addActionListener(this);
        form1.txtNombreEmpresa.addKeyListener(this);
        form1.btnCancelarEmpresa.addActionListener(this);
        form1.txtContraseñaEmpresa.addKeyListener(this);
      
        
    }
    
    
    public void iniciar(){
    form1.setTitle("Nueva empresa");
    form1.setLocationRelativeTo(null);
    form1.setVisible(true);
   
    
    
    
    
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        
   
    
        
        if (e.getSource()==form1.btnCrearEmpresa){
            
            
            
                    
            
                     String nombreBase=form1.txtNombreEmpresa.getText().replaceAll(" ", "");
                     System.out.println(nombreBase);
   
                    formLoading loading = new formLoading();
                    loading.setLocationRelativeTo(null);
                    loading.setVisible(true);
                    int chequeo = JOptionPane.showConfirmDialog(null, "¿Desea crear la empresa "+form1.txtNombreEmpresa.getText()+"?"+"\n"+"\n Sus credenciales iniciales para iniciar serán:"+"\n Usuario: admin"+"\n Contraseña: admin"+"\n");
                    if (chequeo == JOptionPane.YES_OPTION) {
        
                        
                
                
                if (
                    (modeloEmpresa.crearBD(nombreBase))&&
                    (modeloEmpresa.crearTablaDatosEmpresa(nombreBase))&&
                    (modeloEmpresa.crearTablaActividades(nombreBase))&&
                    (modeloEmpresa.crearTablaAuditoriaUsuarios(nombreBase))&&
                    (modeloEmpresa.crearTablaPerfiles(nombreBase))&&
                    (modeloEmpresa.crearTablaProvincias(nombreBase))&&
                    (modeloEmpresa.crearTablaUsuarios(nombreBase))&&
                    (modeloEmpresa.crearTablaAuditoriaClientes(nombreBase))&&
                    (modeloEmpresa.crearTablaAuditoriaProvincias(nombreBase))&&
                    (modeloEmpresa.crearTablaClientes(nombreBase))&&
                    (modeloEmpresa.crearTablaPermisos(nombreBase))&&
                    (modeloEmpresa.cargarTablas(nombreBase))&&
                    (modeloEmpresa.insertarDatosEmpresa(nombreBase,form1.txtRazonSocialEmpresa.getText(),form1.txtCuitEmpresa.getText(),form1.txtContraseñaEmpresa.getText(),nombreBase))    
                   )
                   
            {
                
              
                loading.dispose();
                JOptionPane.showMessageDialog(null, "Empresa creada exitosamente");
                form1.dispose();
                
                
            }
            
            else{
                JOptionPane.showMessageDialog(null, "Error al crear la empresa");
                modeloEmpresa.eliminarEmpresa(nombreBase);
                form1.dispose();
                
            }
                
                 
                        
                        
        }
                    
                    if(chequeo == JOptionPane.NO_OPTION){
                        
                        loading.dispose();
                    }
                    
                    if(chequeo == JOptionPane.CANCEL_OPTION){
                        
                        loading.dispose();
                    }
                      
                        
                      
                     
                      
                      
                      
         
                      
            
   
            
            
            
            
                        
            
            
            
            
              loading.dispose();
                    
            
          
            
            }
        
        
        if (e.getSource()==form1.btnCancelarEmpresa){
            
            form1.dispose();
        }
            
            
    }

    @Override
    public void keyTyped(KeyEvent e) {
        
        
        
        
         if (e.getSource() == form1.txtNombreEmpresa) {
       char c = e.getKeyChar();
        
        if (c=='-') {
             e.consume();
            }
        
        if (c==KeyEvent.VK_SPACE){
            
        }
        else
            if ((c >= '0') && (c <= '9')) {
             
            }
        else
               if ((c >= 'A') && (c <= 'z')) {
             
            }
        
               else 
                   if ((c == '`') || (c == '[')|| (c == ']')||(c == '^')){
                   
               }
               else{
                   e.consume();
               }
                      
        
        
      
        
        
        
      }
         
          
         
         
         
         
         
         
    }

    @Override
    public void keyPressed(KeyEvent e) {
        
        if (e.getSource() == form1.txtContraseñaEmpresa){
        
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            
            
            
            formLoading loading = new formLoading();
                    loading.setLocationRelativeTo(null);
                    loading.setVisible(true);
                    int chequeo = JOptionPane.showConfirmDialog(null, "¿Desea crear la empresa "+form1.txtNombreEmpresa.getText()+"?"+"\n"+"\n Sus credenciales iniciales para iniciar serán:"+"\n Usuario: admin"+"\n Contraseña: admin"+"\n");
                    if (chequeo == JOptionPane.YES_OPTION) {
        
                        
                
                
                if (
                    (modeloEmpresa.crearBD(form1.txtNombreEmpresa.getText()))&&
                    (modeloEmpresa.crearTablaDatosEmpresa(form1.txtNombreEmpresa.getText()))&&
                    (modeloEmpresa.crearTablaActividades(form1.txtNombreEmpresa.getText()))&&
                    (modeloEmpresa.crearTablaAuditoriaUsuarios(form1.txtNombreEmpresa.getText()))&&
                    (modeloEmpresa.crearTablaPerfiles(form1.txtNombreEmpresa.getText()))&&
                    (modeloEmpresa.crearTablaProvincias(form1.txtNombreEmpresa.getText()))&&
                    (modeloEmpresa.crearTablaUsuarios(form1.txtNombreEmpresa.getText()))&&
                    (modeloEmpresa.crearTablaAuditoriaClientes(form1.txtNombreEmpresa.getText()))&&
                    (modeloEmpresa.crearTablaAuditoriaProvincias(form1.txtNombreEmpresa.getText()))&&
                    (modeloEmpresa.crearTablaClientes(form1.txtNombreEmpresa.getText()))&&
                    (modeloEmpresa.crearTablaPermisos(form1.txtNombreEmpresa.getText()))&&
                    (modeloEmpresa.cargarTablas(form1.txtNombreEmpresa.getText()))&&
                    (modeloEmpresa.insertarDatosEmpresa(form1.txtNombreEmpresa.getText(),form1.txtRazonSocialEmpresa.getText(),form1.txtCuitEmpresa.getText(),form1.txtContraseñaEmpresa.getText(),form1.txtNombreEmpresa.getText()))    
                   )
                   
            {
                
              
                loading.dispose();
                JOptionPane.showMessageDialog(null, "Empresa creada exitosamente");
                form1.dispose();
                
                
            }
            
            else{
                JOptionPane.showMessageDialog(null, "Error al crear la empresa");
                modeloEmpresa.eliminarEmpresa(form1.txtNombreEmpresa.getText());
                loading.dispose();
                
            }
                
                 
                        
                        
        }
                    
                    if(chequeo == JOptionPane.NO_OPTION){
                        
                        loading.dispose();
                    }
                    
                    if(chequeo == JOptionPane.CANCEL_OPTION){
                        
                        loading.dispose();
                    }
                      
                        
                      
                     
                      
                      
                      
         
                      
            
   
            
            
            
            
                        
            
            
            
            
              loading.dispose();
            
            
            
        }
        
        
        }
        
    }

    @Override
    public void keyReleased(KeyEvent e) {
        
    }
    
    
    
    
    

        
       
        
        
        
        
        
    
    
    
    
    
    
    
    
    
    
    
    
}
