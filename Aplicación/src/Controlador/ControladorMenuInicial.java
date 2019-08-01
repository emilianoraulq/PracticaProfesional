
package Controlador;

import Modelo.ConsultasProvincia;
import Modelo.Provincia;
import Vista.FormProvincias;
import Vista.MenuInicial;
import Controlador.ControladorProvincia;
import Modelo.Actividad;
import Modelo.Cliente;
import Modelo.Conexion;
import Modelo.ConsultasAuditoria;
import Modelo.ConsultasBackup;
import Modelo.ConsultasCliente;
import Modelo.ConsultasDesplegable;
import Modelo.ConsultasPerfiles;
import Modelo.ConsultasUsuario;
import Modelo.Perfil;
import Modelo.Permiso;
import Modelo.Usuario;
import Vista.Desplegable;
import Vista.FormAuditoria;
import Vista.FormBackup;
import Vista.FormClientes;
import Vista.FormPerfiles;
import Vista.FormSeguridadUsuario;
import Vista.FormUsuarios;
import Vista.LoginForm;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;

public class ControladorMenuInicial implements ActionListener {
    private MenuInicial form1; 
    private Usuario usuario;
    private ConsultasUsuario modeloUsuario;
    
    
    
    public ControladorMenuInicial(MenuInicial form1, Usuario usuario, ConsultasUsuario modeloUsuario) {
        this.form1 = form1;
        this.usuario=usuario;
        this.modeloUsuario=modeloUsuario;
        
        form1.jMenuItem2.addActionListener(this);
        form1.jMenuItem1.addActionListener(this);
        form1.jMenuItem3.addActionListener(this);
        form1.jMenuItem4.addActionListener(this);
        form1.jMenuItem5.addActionListener(this);
        form1.jMenuItem6.addActionListener(this);
        form1.jMenuItem7.addActionListener(this);
        form1.jMenuItem8.addActionListener(this);
        form1.jMenuItem9.addActionListener(this);
        form1.jMenuItem10.addActionListener(this);
        
    }

    ControladorMenuInicial() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    public void iniciar(){
        
    String perfil="";
 
    perfil=modeloUsuario.ObtenerPerfil(usuario);


    
    if(perfil.equals("administrador")){
        
        
    form1.setTitle("Empresa: "+Conexion.getNombreBase()+" - Usuario: " + usuario.getNombre() + " " + usuario.getApellido()+"- Menú principal" );
    form1.setLocationRelativeTo(null);   
    //form1.jMenu4.setVisible(false);
    }
    else if (perfil.equals("auditor")) {
     form1.setTitle("Empresa: "+Conexion.getNombreBase()+" - Usuario: " + usuario.getNombre() + " " + usuario.getApellido()+"- Menú principal" );
    form1.setLocationRelativeTo(null);
    form1.jMenu1.setVisible(false);
    form1.jMenu2.setVisible(false);
    form1.jMenu3.setVisible(false);
    form1.jMenu5.setVisible(false);
    }
            
    
    else{
        
        
    form1.setTitle("Empresa: "+Conexion.getNombreBase()+" - Usuario: " + usuario.getNombre() + " " + usuario.getApellido()+"- Menú principal" );
    form1.setLocationRelativeTo(null);
    form1.jMenu3.setVisible(false);
    form1.jMenu4.setVisible(false);
     form1.jMenu6.setVisible(false);
    
    
     perfil=modeloUsuario.ObtenerPerfil(usuario);
         
         
         
         if ((!modeloUsuario.existePermiso(perfil,"altaClientes"))&&(!modeloUsuario.existePermiso(perfil,"modificarClientes"))&&(!modeloUsuario.existePermiso(perfil,"eliminarClientes"))){
             
             
             form1.jMenu1.setVisible(false);
             
         }
         
          if ((!modeloUsuario.existePermiso(perfil,"altaProvincias"))&&(!modeloUsuario.existePermiso(perfil,"modificarProvincias"))&&(!modeloUsuario.existePermiso(perfil,"eliminarProvincias"))){
             
             
             form1.jMenu2.setVisible(false);
             
         }
    
    
    
        
    }
    
    form1.setVisible(true);
       
    
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        
        if (e.getSource() == form1.jMenuItem2) {
       
            FormProvincias formProvincias = new FormProvincias();
            
            Provincia provincia = new Provincia();
            ConsultasProvincia modelo = new ConsultasProvincia();
            ControladorProvincia controlador = new ControladorProvincia(formProvincias,modelo,provincia,usuario,modeloUsuario);
            
            
            controlador.iniciar();
             
            this.form1.dispose();
        
        }
        
         if (e.getSource() == form1.jMenuItem1) {
        
            FormClientes formClientes = new FormClientes();
            
            Cliente cliente = new Cliente();
            ConsultasCliente modelo = new ConsultasCliente();
            ControladorCliente controlador = new ControladorCliente(formClientes,modelo,cliente,usuario,modeloUsuario);
            
             controlador.iniciar();
             
             this.form1.dispose();
 
        
        }
         
         if (e.getSource() == form1.jMenuItem3){
             
             FormUsuarios formUsuarios=new FormUsuarios();
             //Usuario usuario=new Usuario();
             //ConsultasUsuario modelo = new ConsultasUsuario();
             ControladorUsuario controlador = new ControladorUsuario (formUsuarios,modeloUsuario,usuario);
             
             controlador.iniciar();
             
             this.form1.dispose();
             
         }
         
         if (e.getSource() == form1.jMenuItem4){
          FormAuditoria formAuditoria = new FormAuditoria();
          ConsultasAuditoria modelo = new ConsultasAuditoria();
          //Usuario usuario = new Usuario();
          Provincia provincia = new Provincia();
          Cliente cliente = new Cliente();
          
          ControladorAuditoria controlador = new ControladorAuditoria(formAuditoria,modelo,modeloUsuario,usuario,provincia,cliente);
          controlador.iniciar();
          
          this.form1.dispose();
         }
         
         if (e.getSource() == form1.jMenuItem5){
          FormPerfiles formPerfiles = new FormPerfiles();
          ConsultasPerfiles modelo = new ConsultasPerfiles();
          //Usuario usuario = new Usuario();
          Perfil perfil = new Perfil();
          Actividad actividad = new Actividad();
          Permiso permiso =new Permiso();
          ControladorPerfiles controlador = new ControladorPerfiles(formPerfiles,modelo,perfil,actividad,permiso,usuario,modeloUsuario);
           controlador.iniciar();
          
          this.form1.dispose();
         }
         
         
         if (e.getSource() == form1.jMenuItem6){
             FormSeguridadUsuario formSeguridad = new FormSeguridadUsuario();
             ControladorSeguridadUsuario controlador = new ControladorSeguridadUsuario(formSeguridad,modeloUsuario,usuario);
             
             controlador.iniciar();
             
             this.form1.dispose();
             
             
         }
         
          if (e.getSource() == form1.jMenuItem7){
             FormBackup formbackup = new FormBackup(form1,true);
             ConsultasBackup modeloBackup = new ConsultasBackup();
             ControladorBackup controlador = new ControladorBackup(formbackup,usuario,1,modeloBackup);
             
             controlador.iniciar();
             
             //this.form1.dispose();
            
             
             
         }
          
          if (e.getSource() == form1.jMenuItem8){
             FormBackup formbackup = new FormBackup(form1,true);
             ConsultasBackup modeloBackup = new ConsultasBackup();
             ControladorBackup controlador = new ControladorBackup(formbackup,usuario,2,modeloBackup);
             
             controlador.iniciar();
             
             //this.form1.dispose();
            
             
             
         }
          
          if (e.getSource() == form1.jMenuItem9){
              
              JOptionPane.showMessageDialog(null, "Versión 1.2.8");
          }
          
          if (e.getSource() == form1.jMenuItem10){
              
              form1.dispose();
              Usuario usuario= new Usuario();
              ConsultasUsuario modelo =new ConsultasUsuario();
              LoginForm menu=new LoginForm();
              ControladorLogin controlador = new ControladorLogin(menu,usuario,modelo);
              controlador.iniciar();
          }
          
         
        
        
    }
    
    
}
