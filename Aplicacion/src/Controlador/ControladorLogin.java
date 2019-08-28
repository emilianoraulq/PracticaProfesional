/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import Modelo.CifrarContraseña;
import Modelo.Conexion;
import Modelo.ConsultasBackup;
import Modelo.ConsultasDesplegable;
import Modelo.ConsultasNuevaEmpresa;
import Modelo.ConsultasUsuario;
import Modelo.Usuario;
import Vista.Desplegable;
import Vista.FormBackup;
import Vista.FormNuevaEmpresa;
import Vista.LoginForm;
import Vista.MenuInicial;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JOptionPane;
import static javax.swing.JOptionPane.WARNING_MESSAGE;

/**
 *
 * @author erqui
 */
public class ControladorLogin implements ActionListener, KeyListener {
    
    private LoginForm form1;
    private Usuario usuario;
    private ConsultasUsuario modeloUsuario;
    
    //private ConsultasNuevaEmpresa modeloEmpresa;
    
    
    public ControladorLogin (LoginForm form1, Usuario usuario, ConsultasUsuario modeloUsuario){
        this.form1=form1;
        this.usuario=usuario;
        this.modeloUsuario=modeloUsuario;
        
        form1.btnIniciarSesion.addActionListener(this);
        form1.txtContraseñaLogin.addKeyListener(this);
        form1.btnSalir.addActionListener(this);
        
        form1.btnCrearEmpresa.addActionListener(this);
        form1.txtEmpresa.addKeyListener(this);
        form1.btnRecuperarEmpresa.addActionListener(this);
        
    }
    
    
    public void iniciar(){
    form1.setTitle("Inicio de sesion");
    form1.setLocationRelativeTo(null);
    form1.setVisible(true);
     Conexion.setBaseUrl("world");
     Conexion.setNombreBase("world");
    }
    

    @Override
    public void actionPerformed(ActionEvent e) {
        
        if (e.getSource()==form1.btnIniciarSesion){
            
            
        //Conexion con=new Conexion();
        
        ConsultasNuevaEmpresa modeloEmpresaAux = new ConsultasNuevaEmpresa();
        /*
        if(modeloEmpresaAux.establecerConexion(form1.txtEmpresa.getText())){
            JOptionPane.showMessageDialog(null, "Bienvenido "+Conexion.getNombreBase()+""+Conexion.getURL());
        }*/
        
        
       
        //JOptionPane.showMessageDialog(null, Conexion.getNombreBase());
        Conexion.setBaseUrl(form1.txtEmpresa.getText());
        Conexion.setNombreBase(form1.txtEmpresa.getText());
        //JOptionPane.showMessageDialog(null, Conexion.getNombreBase());
       // JOptionPane.showMessageDialog(null, Conexion.getURL());
        
        
            
        if (modeloEmpresaAux.existeEmpresa(form1.txtEmpresa.getText())){
            
        
        
            
             usuario.setNick(form1.txtUsuarioLogin.getText());
             //recupero la contraseña del campo password
             String contraseña = "";
             for (int i=0; i < form1.txtContraseñaLogin.getPassword().length ;i++) {
             contraseña += form1.txtContraseñaLogin.getPassword()[i];
             }
             //la cifro
             String contraseñaCifrada = CifrarContraseña.md5(contraseña);
             //ahora si la puedo comprobar
             
             if (contraseña.equals("admin")){
                 contraseñaCifrada="admin";
             }
            
            if ((modeloUsuario.BuscarCargarUsuario(usuario))&&((usuario.getContraseña().equals(contraseñaCifrada)))){
        
         
                    
                    JOptionPane.showMessageDialog(null, "Bienvenido "+usuario.getNombre());
                    
                    MenuInicial menu = new MenuInicial();
                    ControladorMenuInicial controlador = new ControladorMenuInicial(menu,usuario,modeloUsuario);
                    controlador.iniciar();
                    form1.dispose();
                    
                
                
                
               
                
            }
            
            else{
                 JOptionPane.showMessageDialog(null, "Usuario o contraseña incorrectos. Intente nuevamente.");
                 System.out.println(usuario.getNick());
                 System.out.println(usuario.getContraseña());
            }
            
        }
        else{
           JOptionPane.showMessageDialog(null, "La empresa no existe. Puede recuperarla reiniciando la aplicación y pulsando el botón 'Recuperar empresa'"); 
        }
            
        }
        
        
        if (e.getSource()==form1.btnSalir){
            
            form1.dispose();
           
            
        }
        
        if (e.getSource()==form1.btnCrearEmpresa){
            
            FormNuevaEmpresa menu = new FormNuevaEmpresa();
            ConsultasNuevaEmpresa modeloEmpresa = new ConsultasNuevaEmpresa();
            ControladorNuevaEmpresa controlador = new ControladorNuevaEmpresa(menu,modeloEmpresa);
            controlador.iniciar();
            
            
        }
        
        
         if (e.getSource()==form1.btnRecuperarEmpresa){
             
             if(!form1.txtEmpresa.getText().equals("")){
            
             ConsultasNuevaEmpresa consulta = new ConsultasNuevaEmpresa();
             
             if (consulta.existeEmpresa(form1.txtEmpresa.getText())){
                 
                 JOptionPane.showMessageDialog(null, "No se puede recuperar una empresa existente","",WARNING_MESSAGE); 
                 
             }else{
             
                 
                 int chequeo = JOptionPane.showConfirmDialog(null, "¿Desea recuperar la empresa "+form1.txtEmpresa.getText()+"?");
                 if (chequeo == JOptionPane.YES_OPTION){
                     FormBackup formbackup = new FormBackup(form1,true);
                     ConsultasBackup modeloBackup = new ConsultasBackup();
                     ControladorBackup controlador = new ControladorBackup(formbackup,usuario,3,modeloBackup);
                     formbackup.etiquetaContraseña.setVisible(false);
                     formbackup.campoContraseña.setVisible(false);
             
                    controlador.iniciar();
                 }
                 
         }
         }
             
             else{
                 JOptionPane.showMessageDialog(null, "Complete el campo empresa");
             }
            
            
        }
        
        
        
        
    }

    @Override
    public void keyTyped(KeyEvent e) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getSource() == form1.txtContraseñaLogin){
        
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            
            //Conexion con=new Conexion();
        
        ConsultasNuevaEmpresa modeloEmpresaAux = new ConsultasNuevaEmpresa();
        /*
        if(modeloEmpresaAux.establecerConexion(form1.txtEmpresa.getText())){
            JOptionPane.showMessageDialog(null, "Bienvenido "+Conexion.getNombreBase()+""+Conexion.getURL());
        }*/
        
        
       
        //JOptionPane.showMessageDialog(null, Conexion.getNombreBase());
        Conexion.setBaseUrl(form1.txtEmpresa.getText());
        Conexion.setNombreBase(form1.txtEmpresa.getText());
        //JOptionPane.showMessageDialog(null, Conexion.getNombreBase());
        //JOptionPane.showMessageDialog(null, Conexion.getURL());
        
        
            
        if (modeloEmpresaAux.existeEmpresa(form1.txtEmpresa.getText())){
            
        
        
            
             usuario.setNick(form1.txtUsuarioLogin.getText());
             //recupero la contraseña del campo password
             String contraseña = "";
             for (int i=0; i < form1.txtContraseñaLogin.getPassword().length ;i++) {
             contraseña += form1.txtContraseñaLogin.getPassword()[i];
             }
             //la cifro
             String contraseñaCifrada = CifrarContraseña.md5(contraseña);
             //ahora si la puedo comprobar
             
             if (contraseña.equals("admin")){
                 contraseñaCifrada="admin";
             }
            
            if ((modeloUsuario.BuscarCargarUsuario(usuario))&&((usuario.getContraseña().equals(contraseñaCifrada)))){
        
         
                    
                    JOptionPane.showMessageDialog(null, "Bienvenido "+usuario.getNombre());
                    
                    MenuInicial menu = new MenuInicial();
                    ControladorMenuInicial controlador = new ControladorMenuInicial(menu,usuario,modeloUsuario);
                    controlador.iniciar();
                    form1.dispose();
                    
                
                
                
               
                
            }
            
            else{
                 JOptionPane.showMessageDialog(null, "Usuario o contraseña incorrectos. Intente nuevamente.");
                 System.out.println(usuario.getNick());
                 System.out.println(usuario.getContraseña());
            }
            
        }
        else{
           JOptionPane.showMessageDialog(null, "La empresa no existe. Puede recuperarla reiniciando la aplicación y pulsando el botón 'Recuperar empresa'"); 
        }
           }
        
    }
        
        
        
        
        
        
        
        
        if (e.getSource() == form1.txtEmpresa) {
          char c = e.getKeyChar();
            
        if (e.getKeyCode() == KeyEvent.VK_F1) {
            //form1.txtEmpresa.removeFocusListener(this);
            Desplegable desplegable = new Desplegable(form1,true);
            ConsultasDesplegable modelo = new ConsultasDesplegable();
            
            ControladorDesplegable controlador = new ControladorDesplegable(desplegable,modelo,5);

            controlador.iniciar();
            //form1.btnIniciarSesion.requestFocus();}
            
        }
        
        
        
        
        
        
        
        
        
        
        
        
    }
        
    }

    @Override
    public void keyReleased(KeyEvent e) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    
    
}
