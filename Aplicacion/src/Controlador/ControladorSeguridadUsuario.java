/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import Modelo.CifrarContraseña;
import Modelo.Conexion;
import Modelo.ConsultasUsuario;
import Modelo.Usuario;
import Vista.FormSeguridadUsuario;
import Vista.MenuInicial;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.net.UnknownHostException;
import java.text.ParseException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author erqui
 */
public class ControladorSeguridadUsuario implements ActionListener, MouseListener, FocusListener, KeyListener {
    
    private FormSeguridadUsuario form1;
    private ConsultasUsuario modeloUsuario;
    private Usuario usuario;
    
    
    
    public ControladorSeguridadUsuario (FormSeguridadUsuario form1, ConsultasUsuario modeloUsuario, Usuario usuario){
        
        this.form1=form1;
        this.modeloUsuario=modeloUsuario;
        this.usuario=usuario;
        
        form1.btnGuardarCambios.addActionListener(this);
        form1.btnCancelarCambios.addActionListener(this);
        
    }
    
    
    public void iniciar(){
    form1.setTitle("Empresa: "+Conexion.getNombreBase()+" - Usuario: " + usuario.getNombre() + " " + usuario.getApellido()+"- Seguridad" );
    form1.setLocationRelativeTo(null);
    form1.setVisible(true);
    }
    
    
    
    

    @Override
    public void actionPerformed(ActionEvent e) {
        
        
        if (e.getSource()==form1.btnGuardarCambios){
            
            
        
            if(("".equals(form1.txtContraseñaAnterior.getText()))||("".equals(form1.txtContraseñaNueva.getText()))||("".equals(form1.txtRepetirContraseñaNueva.getText()))) {
            JOptionPane.showMessageDialog(null, "Por favor complete todos los campos");
            }
            else {
               //obtengo la contraseña
              String contraseña = "";
             for (int i=0; i < form1.txtContraseñaAnterior.getPassword().length ;i++) {
             contraseña += form1.txtContraseñaAnterior.getPassword()[i];
             }
             //la cifro (si no es la contraseña por defecto)
             String contraseñaCifrada="";
             if (!contraseña.equals("admin")){
                contraseñaCifrada = CifrarContraseña.md5(contraseña);
             }else{
                contraseñaCifrada="admin";}
             
                if (modeloUsuario.existeContraseña(usuario.getNick(),contraseñaCifrada)){
                    
                    
                    
                    if (form1.txtContraseñaNueva.getText().equals(form1.txtRepetirContraseñaNueva.getText())){
                        
                        
                            usuario.setNombre(usuario.getNombre());
                            usuario.setApellido(usuario.getApellido());
                            usuario.setNick(usuario.getNick());
                            usuario.setDni(usuario.getDni());
                            usuario.setId_perfil(usuario.getId_perfil());
                            
                            contraseña = "";
                            for (int i = 0; i <form1.txtContraseñaNueva.getPassword().length; i++) {
                            contraseña += form1.txtContraseñaNueva.getPassword()[i];
                            }
                            contraseñaCifrada = CifrarContraseña.md5(contraseña);
                            usuario.setContraseña(contraseñaCifrada);
                            
                            
                        if (modeloUsuario.modificar(usuario)) {
                        JOptionPane.showMessageDialog(null, "Contraseña modificada correctamente");
                        
                         try {
                            if (modeloUsuario.insertarEnAuditoriaUsuarios(usuario, "Modificacion")) {
                                
                            }   } catch (ParseException ex) {
                            Logger.getLogger(ControladorProvincia.class.getName()).log(Level.SEVERE, null, ex);
                        } catch (UnknownHostException ex) {
                            Logger.getLogger(ControladorProvincia.class.getName()).log(Level.SEVERE, null, ex);
                        }
                     
                        
                         form1.dispose();
                         MenuInicial menu = new MenuInicial();
         
                        ControladorMenuInicial controlador = new ControladorMenuInicial(menu,usuario,modeloUsuario);
                        controlador.iniciar();
                        
                        
                    
                         }
                        else {
                        JOptionPane.showMessageDialog(null, "Error al modificar la contraseña");
    
                        }
                        
                        
                    }
                    
                    else{
                        
                        JOptionPane.showMessageDialog(null, "Las nuevas contraseñas no coinciden");
                        
                    }
                    
                    
                    
                    
                }
                
                else{
                    JOptionPane.showMessageDialog(null, "Su contraseña actual es incorrecta");
                }
                
                
                
                
            
                }
                
                 
    }
        
        if (e.getSource()==form1.btnCancelarCambios){
            
            
                         form1.dispose();
                         MenuInicial menu = new MenuInicial();
         
                        ControladorMenuInicial controlador = new ControladorMenuInicial(menu,usuario,modeloUsuario);
                        controlador.iniciar();
            
            
            
            
        }
        
        
    

            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
        
        
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mousePressed(MouseEvent e) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mouseExited(MouseEvent e) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void focusGained(FocusEvent e) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void focusLost(FocusEvent e) {
       //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void keyTyped(KeyEvent e) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void keyPressed(KeyEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void keyReleased(KeyEvent e) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
