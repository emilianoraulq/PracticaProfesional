/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import Modelo.Actividad;
import Modelo.Conexion;
import Modelo.ConsultasDesplegable;
import Modelo.ConsultasPerfiles;
import Modelo.ConsultasUsuario;
import Modelo.Perfil;
import Modelo.Permiso;
import Modelo.Usuario;
import Vista.Desplegable;
import Vista.FormPerfiles;
import Vista.MenuInicial;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JOptionPane;

/**
 *
 * @author erqui
 */

    
 public class ControladorPerfiles implements ActionListener, KeyListener, MouseListener {
    
    private FormPerfiles form1;
    private ConsultasPerfiles modelo;
    private Perfil perfil;
    private Actividad actividad;
    private Permiso permiso;
    private Usuario usuario;
    private ConsultasUsuario modeloUsuario;
    
    
    public ControladorPerfiles(FormPerfiles form1, ConsultasPerfiles modelo, Perfil perfil, Actividad actividad, Permiso permiso, Usuario usuario, ConsultasUsuario modeloUsuario) {
        
        this.form1=form1;
        this.modelo=modelo;
        this.perfil=perfil;
        this.actividad=actividad;
        this.permiso=permiso;
        this.usuario=usuario;
        this.modeloUsuario=modeloUsuario;
        
        //form1.jTableUsuarios.setModel(modelo.llenarTabla());
        
        form1.jPanel1.setVisible(false);
       // form1.jPanel2.setVisible(false);
        form1.jPanel3.setVisible(false);
        form1.btnSalirPerfiles.addActionListener(this);
        form1.btnNuevoPerfil.addActionListener(this);
        form1.btnModificarPerfil.addActionListener(this);
        form1.btnEliminarPerfil.addActionListener(this);
        form1.btnGuardarPerfil.addActionListener(this);
        form1.btnCancelarPerfil.addActionListener(this);
       // form1.btnNuevaActividad.addActionListener(this);
       // form1.btnModificarActividad.addActionListener(this);
      //  form1.btnEliminarActividad.addActionListener(this);
      //  form1.btnGuardarActividad.addActionListener(this);
      //  form1.btnCancelarActividad.addActionListener(this);
        form1.btnNuevoPermiso.addActionListener(this);
        form1.btnModificarPermiso.addActionListener(this);
        form1.btnEliminarPermiso.addActionListener(this);
        form1.btnGuardarPermiso.addActionListener(this);
        form1.txtIdPerfilPermiso.addKeyListener(this);
        form1.txtIdActividadPermiso.addKeyListener(this);
        form1.btnCancelarPermiso.addActionListener(this);
        
        
        
       
        
        
        
        
        
    }
    
    
    
    public void iniciar(){
    form1.setTitle("Empresa: "+Conexion.getNombreBase()+" - Usuario: " + usuario.getNombre() + " " + usuario.getApellido()+"- Menú de perfiles" );
    form1.setLocationRelativeTo(null);
    form1.setVisible(true);
    form1.jTablePerfiles.setModel(modelo.llenarTablaPerfiles());
   // form1.jTableActividades.setModel(modelo.llenarTablaActividades());
    form1.jTablePermisos.setModel(modelo.llenarTablaPermisos());
    form1.txtIdPerfil.setEnabled(false);
  //  form1.txtIdActividad.setEnabled(false);
    form1.txtIdPermiso.setEnabled(false);
    
    }
    
    
    
    
    
    

    @Override
    public void actionPerformed(ActionEvent e) {
        
        
        
 //---------------------------------------------------BOTONES PERFILES----------------------------------------//
        
         if (e.getSource() == form1.btnSalirPerfiles) {
         form1.dispose();
         MenuInicial menu = new MenuInicial();
         //Usuario usuario = new Usuario();
         //ConsultasUsuario modeloUsuario=new ConsultasUsuario();
         ControladorMenuInicial controlador = new ControladorMenuInicial(menu,usuario,modeloUsuario);
         controlador.iniciar();
        }
         
         if (e.getSource()==form1.btnCancelarPerfil){
             form1.jPanel1.setVisible(false);
         }
         
          if (e.getSource()==form1.btnCancelarPermiso){
             form1.jPanel3.setVisible(false);
         }
         
         
         
         
         
         if (e.getSource() == form1.btnGuardarPerfil) {
                   
            if("".equals(form1.txtDescripcionPerfil.getText())) {
            JOptionPane.showMessageDialog(null, "Por favor complete el campo descripcion");
            }
            else {
            perfil.setDescripcion(form1.txtDescripcionPerfil.getText());
           
     
            
                if (form1.btnNuevoPerfil.isEnabled()) {
                    
                    if (modelo.buscarDescripcionPerfil(perfil)) {
                    JOptionPane.showMessageDialog(null, "El nombre de perfil ya existe");
                    form1.txtDescripcionPerfil.requestFocus();
                    }
                    else
                    {    
                    if (modelo.insertarPerfil(perfil)) {
                    JOptionPane.showMessageDialog(null, "Registro insertado correctamente");
                    limpiarCajas();
                    form1.jTablePerfiles.setModel(modelo.llenarTablaPerfiles());
                    form1.btnGuardarPerfil.setEnabled(true);
                    form1.btnModificarPerfil.setEnabled(true);
                    form1.btnEliminarPerfil.setEnabled(true);
                    form1.jPanel1.setVisible(false);
                     }
                    else {
                    JOptionPane.showMessageDialog(null, "Error al insertar el registro");
                    limpiarCajas();
                     }
                    }
                }
                
                if (form1.btnModificarPerfil.isEnabled()) {
                    
                   
                    
                    perfil.setId(Integer.parseInt(form1.txtIdPerfil.getText()));
                    perfil.setDescripcion(form1.txtDescripcionPerfil.getText());
                  
                    
                    int id = modelo.buscarIdPerfil(perfil);
                    
                    if ((id == perfil.getId()) || (id == 0)) {
                        
                        if (modelo.modificarPerfil(perfil)) {
                        JOptionPane.showMessageDialog(null, "Registro modificado correctamente");
                        limpiarCajas();
                        form1.jTablePerfiles.setModel(modelo.llenarTablaPerfiles());
                        form1.btnNuevoPerfil.setEnabled(true);
                        form1.btnModificarPerfil.setEnabled(true);
                        form1.btnEliminarPerfil.setEnabled(true);
                        form1.jPanel1.setVisible(false);
                    
                         }
                        else {
                        JOptionPane.showMessageDialog(null, "No se puede modificar el perfil, hay permisos asociados al perfil");
                        limpiarCajas();
                        }
                    }
                    else {
                    JOptionPane.showMessageDialog(null, "El perfil ya existe");
                    form1.txtDescripcionPerfil.requestFocus();
                    }
                    
                
                    
                } //boton modificar 
                
                } 
          

        }
         
         
         
        if (e.getSource() == form1.btnNuevoPerfil) {
        limpiarCajas();
        form1.jPanel1.setVisible(true);
        form1.btnModificarPerfil.setEnabled(false);
        form1.btnEliminarPerfil.setEnabled(false);
        form1.txtDescripcionPerfil.requestFocus();
        
        }
        
        
         if (e.getSource() == form1.btnModificarPerfil) {
        int fila = form1.jTablePerfiles.getSelectedRow();
        
        if (fila == -1) {
                 JOptionPane.showMessageDialog(form1, "No hay ningun registro seleccionado");
        }
        else {
        form1.jPanel1.setVisible(true);
        String descripcion = form1.jTablePerfiles.getValueAt(fila, 1).toString();
        String id = form1.jTablePerfiles.getValueAt(fila, 0).toString();
        form1.txtIdPerfil.setText(id);
        form1.txtDescripcionPerfil.setText(descripcion);
        form1.btnNuevoPerfil.setEnabled(false);
        form1.btnEliminarPerfil.setEnabled(false);
        form1.txtDescripcionPerfil.requestFocus();
        }

        } 
         
         
         
         
          if (e.getSource() == form1.btnEliminarPerfil) {
             int fila = form1.jTablePerfiles.getSelectedRow();

             if (fila == -1) {
                 JOptionPane.showMessageDialog(form1, "No hay ningun registro seleccionado");
             } else {
                 
                 perfil.setDescripcion(form1.jTablePerfiles.getValueAt(fila, 1).toString());
                 perfil.setId((int) form1.jTablePerfiles.getValueAt(fila, 0));
                 
                 if ((!perfil.getDescripcion().equals("administrador"))&&(!perfil.getDescripcion().equals("auditor"))) {
                 
                 //veo si tiene permisos asociados
                 if (modelo.eliminarPerfil(perfil)) {
                     //sino tiene perfiles asociados chequeo que no haya ningun usuario con ese perfil
                     if(modelo.eliminarPerfil2(perfil)) {
                      form1.jTablePerfiles.setModel(modelo.llenarTablaPerfiles());
                     }
                    
                 }
             }else{
                     JOptionPane.showMessageDialog(form1, "No se puede eliminar un perfil predeterminado de la aplicación");
                 }

             }

        }
          
          
          
          
          
          
     //---------------------------------------------------BOTONES ACTIVIDADES----------------------------------------//
     
     
     
   
     
     
     
     
     
     
     
     
     
        
        
      //---------------------------------------------------BOTONES PERMISOS----------------------------------------//   
        
        if (e.getSource() == form1.btnNuevoPermiso) {
        
        //form1.botonBuscar.setEnabled(false);
        form1.btnModificarPermiso.setEnabled(false);
        form1.btnEliminarPermiso.setEnabled(false);
        
        limpiarCajas();
        
        form1.jPanel3.setVisible(true);
        //form1.jPanel3.setVisible(false);
        form1.txtIdPerfilPermiso.requestFocus();

        
        }
        
        
        if (e.getSource() == form1.btnModificarPermiso) {
         int fila = form1.jTablePermisos.getSelectedRow();
        
        if (fila == -1) {
                 JOptionPane.showMessageDialog(form1, "No hay ningun registro seleccionado");
        }
        else {
        form1.jPanel3.setVisible(true);
        //form1.jPanel3.setVisible(false);
        
        String id = form1.jTablePermisos.getValueAt(fila, 0).toString();
        String id_perfil_permiso = form1.jTablePermisos.getValueAt(fila, 1).toString();
        String id_actividad = form1.jTablePermisos.getValueAt(fila, 3).toString();
    
        
        form1.txtIdPermiso.setText(id);
        form1.txtIdPerfilPermiso.setText(id_perfil_permiso);
        form1.txtIdActividadPermiso.setText(id_actividad);
       
     
        
        form1.btnNuevoPermiso.setEnabled(false);
        form1.btnEliminarPermiso.setEnabled(false);
        form1.txtIdPerfilPermiso.requestFocus();
        
        }
        }
        
        
        
        if (e.getSource() == form1.btnEliminarPermiso) {
          int fila = form1.jTablePermisos.getSelectedRow();

             if (fila == -1) {
                 JOptionPane.showMessageDialog(form1, "No hay ningun registro seleccionado");
             } else {
                 permiso.setId((int) form1.jTablePermisos.getValueAt(fila, 0));
                 if (modelo.eliminarPermiso(permiso)) {
                     form1.jTablePermisos.setModel(modelo.llenarTablaPermisos());
             /*        try {
                        if (modelo.insertarEnAuditoriaClientes(cliente, "Eliminacion",usuario.getId())) {
                             
                         }
                     } catch (ParseException ex) {
                         Logger.getLogger(ControladorCliente.class.getName()).log(Level.SEVERE, null, ex);
                     } catch (UnknownHostException ex) {
                         Logger.getLogger(ControladorCliente.class.getName()).log(Level.SEVERE, null, ex);
                     }*/
                 }

             }
           
        }
        
        
        
        
        
        if (e.getSource() == form1.btnGuardarPermiso) {
            
                 
            
           
            if(("".equals(form1.txtIdPerfilPermiso.getText()))||("".equals(form1.txtIdActividadPermiso.getText()))) {
            JOptionPane.showMessageDialog(null, "Por favor complete todos los campos");
            }
            else {
            permiso.setId_perfil_permisos(Integer.parseInt(form1.txtIdPerfilPermiso.getText()));
            permiso.setId_actividad(Integer.parseInt(form1.txtIdActividadPermiso.getText()));
            
     
            
                if (form1.btnNuevoPermiso.isEnabled()) {
                    
                    Perfil perfilAux = new Perfil();
                    perfilAux.setId(Integer.parseInt(form1.txtIdPerfilPermiso.getText()));
                    String descripcionPerfilAux = modelo.buscarPerfilPermiso(perfilAux);
                    
                    Actividad actividadAux = new Actividad();
                    actividadAux.setId(Integer.parseInt(form1.txtIdActividadPermiso.getText()));
                    String descripcionActividadAux = modelo.buscarActividadPermiso(actividadAux);
                    
                    
                    
                    if (("Perfil inexistente".equals(descripcionPerfilAux))||("Actividad inexistente".equals(descripcionActividadAux))) {
                    JOptionPane.showMessageDialog(null, "Seleccione un perfil y una actividad existentes");
                    form1.txtIdPerfilPermiso.requestFocus();
                    }
                    else
                        if (modelo.existePermisoId(perfilAux.getId(),actividadAux.getId())){
                          JOptionPane.showMessageDialog(null, "El permiso ya existe");  
                        }
                    else
                    {    
                    if (modelo.insertarPermiso(permiso)) {
                    JOptionPane.showMessageDialog(null, "Registro insertado correctamente");
                    limpiarCajas();
                    form1.jTablePermisos.setModel(modelo.llenarTablaPermisos());
                    form1.btnGuardarPermiso.setEnabled(true);
                    form1.btnModificarPermiso.setEnabled(true);
                    form1.btnEliminarPermiso.setEnabled(true);
                    form1.jPanel3.setVisible(false);
                     }
                    else {
                        
                        
                    JOptionPane.showMessageDialog(null, "Error al insertar el registro");
                    limpiarCajas();
                     }
                    }
                }
                
                if (form1.btnModificarPermiso.isEnabled()) {
                    
                    Perfil perfilAux = new Perfil();
                    perfilAux.setId(Integer.parseInt(form1.txtIdPerfilPermiso.getText()));
                    String descripcionPerfilAux = modelo.buscarPerfilPermiso(perfilAux);
                    
                    Actividad actividadAux = new Actividad();
                    actividadAux.setId(Integer.parseInt(form1.txtIdActividadPermiso.getText()));
                    String descripcionActividadAux = modelo.buscarActividadPermiso(actividadAux);
                    
                   
                    
                    permiso.setId(Integer.parseInt(form1.txtIdPermiso.getText()));
                    permiso.setId_perfil_permisos(Integer.parseInt(form1.txtIdPerfilPermiso.getText()));
                    permiso.setId_actividad(Integer.parseInt(form1.txtIdActividadPermiso.getText()));
                  
                    
                    //int id = modelo.buscarIdUsuario(usuarioAux);
                    
                    if (!modelo.existePermisoId(perfilAux.getId(),actividadAux.getId())) {
                        
                        if (modelo.modificarPermiso(permiso)) {
                        JOptionPane.showMessageDialog(null, "Registro modificado correctamente");
                        limpiarCajas();
                        form1.jTablePermisos.setModel(modelo.llenarTablaPermisos());
                        form1.btnNuevoPermiso.setEnabled(true);
                        form1.btnModificarPermiso.setEnabled(true);
                        form1.btnEliminarPermiso.setEnabled(true);
                        form1.jPanel3.setVisible(false);
                    
                         }
                        else {
                        JOptionPane.showMessageDialog(null, "No se puede modificar el permiso");
                        limpiarCajas();
                        }
                    }
                    else {
                    JOptionPane.showMessageDialog(null, "El permiso ya existe");
                    form1.txtIdPerfilPermiso.requestFocus();
                    }
                    
                
                    
                } //boton modificar 
                
                } 
          

        }
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
    }

    @Override
    public void keyTyped(KeyEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void keyPressed(KeyEvent e) {
       // throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    
    //permisos
    if (e.getSource()==form1.txtIdPerfilPermiso){
       
        
        if (e.getKeyCode() == KeyEvent.VK_F1) {
            //form1.txtIdPerfilPermiso.removeFocusListener(this);
            Desplegable desplegable = new Desplegable(form1,true);
            ConsultasDesplegable modelo = new ConsultasDesplegable();
            
            ControladorDesplegable controlador = new ControladorDesplegable(desplegable,modelo,3);

            controlador.iniciar();
            //form1.btnGuardarPermiso.requestFocus();
        }
    }
    
     if (e.getSource()==form1.txtIdActividadPermiso){
       
        
        if (e.getKeyCode() == KeyEvent.VK_F1) {
            //form1.txtIdPerfilPermiso.removeFocusListener(this);
            Desplegable desplegable = new Desplegable(form1,true);
            ConsultasDesplegable modelo = new ConsultasDesplegable();
            
            ControladorDesplegable controlador = new ControladorDesplegable(desplegable,modelo,4);

            controlador.iniciar();
            form1.btnGuardarPermiso.requestFocus();
        }
    }
    
    }
    

    @Override
    public void keyReleased(KeyEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mousePressed(MouseEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mouseExited(MouseEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    
    
    
    
    
     public void limpiarCajas(){
    
    form1.txtDescripcionPerfil.setText("");
    form1.txtIdPerfil.setText("");
    
  
    
    }
    
    
    
    
    
    
    
    
}
    

