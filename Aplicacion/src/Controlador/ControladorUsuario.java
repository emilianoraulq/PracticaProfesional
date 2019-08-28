/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import Modelo.CifrarContraseña;
import Modelo.Conexion;
import Modelo.ConsultasDesplegable;
import Modelo.ConsultasUsuario;
import Modelo.Usuario;
import Vista.Desplegable;
import Vista.FormUsuarios;
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
import static javax.swing.JOptionPane.WARNING_MESSAGE;

/**
 *
 * @author erqui
 */
public class ControladorUsuario implements ActionListener, KeyListener, MouseListener, FocusListener {
    
    private FormUsuarios form1;
    private ConsultasUsuario modelo;
    private Usuario usuario;
    
    
    public ControladorUsuario(FormUsuarios form1, ConsultasUsuario modelo, Usuario usuario) {
        
        this.form1=form1;
        this.modelo=modelo;
        this.usuario=usuario;
        
        //form1.jTableUsuarios.setModel(modelo.llenarTabla());
        
        form1.jPanel1.setVisible(false);
        
        form1.btnCancelarUsuario.addActionListener(this);
        form1.btnNuevoUsuario.addActionListener(this);
        form1.btnGuardarUsuario.addActionListener(this);
        form1.btnModificarUsuario.addActionListener(this);
        form1.btnEliminarUsuario.addActionListener(this);
        form1.btnSalirUsuarios.addActionListener(this);
        form1.botonResetearContraseña.addActionListener(this);
        
        form1.jTableUsuarios.addMouseListener(this);
        
        form1.txtIdPerfilUsuario.addKeyListener(this);
        form1.txtDniUsuario.addKeyListener(this);
        
        
        form1.txtIdPerfilUsuario.addFocusListener(this);
        
                
        
        
        
        
        
    }
    
    
    
    
    public void iniciar(){
    form1.setTitle("Empresa: "+Conexion.getNombreBase()+" - Usuario: " + usuario.getNombre() + " " + usuario.getApellido()+"- Menú de usuarios" );
    form1.setLocationRelativeTo(null);
    form1.setVisible(true);
    form1.jTableUsuarios.setModel(modelo.llenarTablaUsuarios());
    form1.txtIdUsuario.setEnabled(false);

    }
    
    
    
    
    

    @Override
    public void actionPerformed(ActionEvent e) {
        //eventos botones
        
        if (e.getSource() == form1.botonResetearContraseña) {
        
        int chequeo = JOptionPane.showConfirmDialog(null, "Esta seguro de resetear la contraseña del usuario? Por favor confirme.");
        if (chequeo == JOptionPane.YES_OPTION) {
        form1.txtContraseñaUsuario.setEnabled(true);
        form1.txtRepetirContraseñaUsuario.setEnabled(true);
        
        form1.txtContraseñaUsuario.setText("");
        form1.txtRepetirContraseñaUsuario.setText("");
        form1.botonResetearContraseña.setVisible(false);
        }
            
        }
        
        
        if (e.getSource() == form1.btnSalirUsuarios) {
         form1.dispose();
         MenuInicial menu = new MenuInicial();
         //Usuario usuario = new Usuario();
         //ConsultasUsuario modeloUsuario=new ConsultasUsuario();
         ControladorMenuInicial controlador = new ControladorMenuInicial(menu,usuario,modelo);
         controlador.iniciar();
        }
        
        
        
        
        
        
        
        
        if (e.getSource() == form1.btnGuardarUsuario) {
                    Usuario usuarioAux;
                    usuarioAux = new Usuario();
            if(("".equals(form1.txtNombreUsuario.getText()))||("".equals(form1.txtApellidoUsuario.getText()))||("".equals(form1.txtNickUsuario.getText()))||("".equals(form1.txtDniUsuario.getText()))||("".equals(form1.txtIdPerfilUsuario.getText()))||("".equals(form1.txtContraseñaUsuario.getText()))||("".equals(form1.txtRepetirContraseñaUsuario.getText()))) {
            JOptionPane.showMessageDialog(null, "Por favor complete todos los campos");
            }
            else {
            usuarioAux.setNombre(form1.txtNombreUsuario.getText());
            usuarioAux.setApellido(form1.txtApellidoUsuario.getText());
            usuarioAux.setNick(form1.txtNickUsuario.getText());
            usuarioAux.setDni(form1.txtDniUsuario.getText());
            usuarioAux.setId_perfil(Integer.parseInt(form1.txtIdPerfilUsuario.getText()));
            //prueba cifrar contraseña
            String contraseña = "";
            for (int i=0; i < form1.txtContraseñaUsuario.getPassword().length ;i++) {
             contraseña += form1.txtContraseñaUsuario.getPassword()[i];
             }
            usuarioAux.setContraseña(contraseña);

            
            //fin de prueba
            //usuarioAux.setContraseña(form1.txtContraseñaUsuario.getText());
     
            
                if (form1.btnNuevoUsuario.isEnabled()) {
                   
                    
                    if (modelo.buscarNickUsuario(usuarioAux)) {
                    JOptionPane.showMessageDialog(null, "El nombre de usuario ya existe");
                    form1.txtNombreUsuario.requestFocus();
                    }
                    else
                        if (!form1.txtContraseñaUsuario.getText().equals(form1.txtRepetirContraseñaUsuario.getText())){
                            JOptionPane.showMessageDialog(null, "Las contraseñas no coinciden");
                            
                        }
                   else {   
                        String contraseñaCifrada = CifrarContraseña.md5(contraseña);
                        usuarioAux.setContraseña(contraseñaCifrada);
                    if (modelo.insertar(usuarioAux)) {
                    JOptionPane.showMessageDialog(null, "Registro insertado correctamente");
                    //inserto en la tabla de auditorias
                    int id = modelo.buscarIdUsuario2(usuarioAux);
                    usuarioAux.setId(id);
                        try {
                            if (modelo.insertarEnAuditoriaUsuarios(usuarioAux, "Nuevo")) {
                                
                            }   } catch (ParseException ex) {
                            Logger.getLogger(ControladorProvincia.class.getName()).log(Level.SEVERE, null, ex);
                        } catch (UnknownHostException ex) {
                            Logger.getLogger(ControladorProvincia.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    
                    
                    limpiarCajas();
                    form1.jTableUsuarios.setModel(modelo.llenarTablaUsuarios());
                    form1.btnGuardarUsuario.setEnabled(true);
                    form1.btnModificarUsuario.setEnabled(true);
                    form1.btnEliminarUsuario.setEnabled(true);
                    form1.jPanel1.setVisible(false);
               
                    
                     }
                    else {
                        
                        
                    JOptionPane.showMessageDialog(null, "Error al insertar el registro");
                    limpiarCajas();
                     }
                    }
                }
                
                if (form1.btnModificarUsuario.isEnabled()) {
            
                   
                    
                    usuarioAux.setId(Integer.parseInt(form1.txtIdUsuario.getText()));
                    usuarioAux.setNombre(form1.txtNombreUsuario.getText());
                    usuarioAux.setApellido(form1.txtApellidoUsuario.getText());
                    usuarioAux.setNick(form1.txtNickUsuario.getText());
                    usuarioAux.setDni(form1.txtDniUsuario.getText());
                    usuarioAux.setId_perfil(Integer.parseInt(form1.txtIdPerfilUsuario.getText()));
                    if(form1.botonResetearContraseña.isVisible()) {
                    usuarioAux.setContraseña(contraseña);
                    }
                    else {
                    String contraseñaCifrada = CifrarContraseña.md5(contraseña);
                    usuarioAux.setContraseña(contraseñaCifrada);
                    }
                    
                    
                    int id = modelo.buscarIdUsuario(usuarioAux);
                    
                    if ((id == usuarioAux.getId()) || (id == 0)) {
                        
                        if (!form1.txtContraseñaUsuario.getText().equals(form1.txtRepetirContraseñaUsuario.getText())){
                            
                            JOptionPane.showMessageDialog(null, "Las contraseñas no coinciden");
                        }
                        
                        else if (modelo.modificar(usuarioAux)) {
                        JOptionPane.showMessageDialog(null, "Registro modificado correctamente.");
                        if (usuario.getId()==usuarioAux.getId()){
                            usuario=usuarioAux;
                            //JOptionPane.showMessageDialog(null, "Inicie sesión nuevamente para aplicar los cambios", "Advertencia",WARNING_MESSAGE);
                        }
                        
                
                        
                         try {
                            if (modelo.insertarEnAuditoriaUsuarios(usuarioAux, "Modificacion")) {
                                
                            }   } catch (ParseException ex) {
                            Logger.getLogger(ControladorProvincia.class.getName()).log(Level.SEVERE, null, ex);
                        } catch (UnknownHostException ex) {
                            Logger.getLogger(ControladorProvincia.class.getName()).log(Level.SEVERE, null, ex);
                        }
                         
                        limpiarCajas();
                        form1.jTableUsuarios.setModel(modelo.llenarTablaUsuarios());
                        form1.btnNuevoUsuario.setEnabled(true);
                        form1.btnModificarUsuario.setEnabled(true);
                        form1.btnEliminarUsuario.setEnabled(true);
                        form1.jPanel1.setVisible(false);
                    
                         }
                        else {
                        JOptionPane.showMessageDialog(null, "No se puede modificar el usuario");
                        limpiarCajas();
                        }
                    }
                    else {
                    JOptionPane.showMessageDialog(null, "El nick ya existe");
                    form1.txtNombreUsuario.requestFocus();
                    }
                    
                
                    
                } //boton modificar 
                
                } 
          

        }
        
        
        if (e.getSource() == form1.btnNuevoUsuario) {
        limpiarCajas();
        form1.jPanel1.setVisible(true);
        form1.btnModificarUsuario.setEnabled(false);
        form1.btnEliminarUsuario.setEnabled(false);
        form1.txtNombreUsuario.requestFocus();
        form1.txtContraseñaUsuario.setEnabled(true);
        form1.txtRepetirContraseñaUsuario.setEnabled(true);
        form1.botonResetearContraseña.setVisible(false);
        
        }
        
        
        
        
        if (e.getSource() == form1.btnModificarUsuario) {
        int fila = form1.jTableUsuarios.getSelectedRow();
        
        if (fila == -1) {
                 JOptionPane.showMessageDialog(form1, "No hay ningun registro seleccionado");
        }
        else {
        form1.jPanel1.setVisible(true);
        
        String id = form1.jTableUsuarios.getValueAt(fila, 0).toString();
        String nombre = form1.jTableUsuarios.getValueAt(fila, 1).toString();
        String apellido = form1.jTableUsuarios.getValueAt(fila, 2).toString();
        String nick = form1.jTableUsuarios.getValueAt(fila, 3).toString();
        String dni = form1.jTableUsuarios.getValueAt(fila, 4).toString();
        String contraseña = form1.jTableUsuarios.getValueAt(fila, 5).toString();
        String perfil_id= form1.jTableUsuarios.getValueAt(fila, 6).toString();
        String perfil_descripcion = form1.jTableUsuarios.getValueAt(fila, 7).toString();
       
        form1.txtIdUsuario.setText(id);
        form1.txtNombreUsuario.setText(nombre);
        form1.txtApellidoUsuario.setText(apellido);
        form1.txtNickUsuario.setText(nick);
        form1.txtDniUsuario.setText(dni);
        FormUsuarios.txtIdPerfilUsuario.setText(perfil_id);
        FormUsuarios.txtNombrePerfilUsuario.setText(perfil_descripcion);
        form1.txtContraseñaUsuario.setText(contraseña);
        form1.txtRepetirContraseñaUsuario.setText(contraseña);
        
        form1.btnNuevoUsuario.setEnabled(false);
        form1.btnEliminarUsuario.setEnabled(false);
        form1.txtContraseñaUsuario.setEnabled(false);
        form1.txtRepetirContraseñaUsuario.setEnabled(false);
        form1.botonResetearContraseña.setVisible(true);
        form1.txtNombreUsuario.requestFocus();
        }

        } 
        
        
        
        if (e.getSource() == form1.btnCancelarUsuario) {
        form1.jPanel1.setVisible(false);
        form1.btnModificarUsuario.setEnabled(true);
        form1.btnEliminarUsuario.setEnabled(true);
        form1.btnNuevoUsuario.setEnabled(true);
        
        limpiarCajas();
        }
        
        
        
        
        
        
        if (e.getSource() == form1.btnEliminarUsuario) {
                    Usuario usuarioAux;
                    usuarioAux = new Usuario();
             int fila = form1.jTableUsuarios.getSelectedRow();

             if (fila == -1) {
                 JOptionPane.showMessageDialog(form1, "No hay ningun registro seleccionado");
             } else {
                 usuarioAux.setId((int) form1.jTableUsuarios.getValueAt(fila, 0));
                 usuarioAux.setNombre(form1.jTableUsuarios.getValueAt(fila, 1).toString());
                 usuarioAux.setApellido(form1.jTableUsuarios.getValueAt(fila, 2).toString());
                 usuarioAux.setNick(form1.jTableUsuarios.getValueAt(fila, 3).toString());
                 usuarioAux.setDni(form1.jTableUsuarios.getValueAt(fila, 4).toString());
                 usuarioAux.setContraseña(form1.jTableUsuarios.getValueAt(fila, 5).toString());
                 usuarioAux.setId_perfil(Integer.parseInt(form1.jTableUsuarios.getValueAt(fila, 6).toString()));
                 
                 if (modelo.eliminar(usuarioAux)) {
                     form1.jTableUsuarios.setModel(modelo.llenarTablaUsuarios());
                     
                     try {
                            if (modelo.insertarEnAuditoriaUsuarios(usuarioAux, "Eliminacion")) {
                                
                            }   } catch (ParseException ex) {
                            Logger.getLogger(ControladorProvincia.class.getName()).log(Level.SEVERE, null, ex);
                        } catch (UnknownHostException ex) {
                            Logger.getLogger(ControladorProvincia.class.getName()).log(Level.SEVERE, null, ex);
                        }
                     
                      
                 }

             }

        }
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
    }

    @Override
    public void keyTyped(KeyEvent e) {
        
        if (e.getSource() == form1.txtDniUsuario) {
       char c = e.getKeyChar();
        
        if (((c < '0') || (c > '9')) && (c!= KeyEvent.VK_BACK_SPACE)) {
             e.consume();
            }
        if (form1.txtDniUsuario.getText().length() ==9) {
         e.consume();   
        
        }
        
      } 
        
        if (e.getSource() == form1.txtIdPerfilUsuario) {
       char c = e.getKeyChar();
        
        if (((c < '0') || (c > '9')) && (c!= KeyEvent.VK_BACK_SPACE)) {
             e.consume();
            }
        if (form1.txtIdPerfilUsuario.getText().length() ==9) {
         e.consume();   
        
        }
        
      } 
        
    }

    @Override
    public void keyPressed(KeyEvent e) {
        
        
        
        if (e.getKeyCode() == KeyEvent.VK_F1) {
            form1.txtIdPerfilUsuario.removeFocusListener(this);
            Desplegable desplegable = new Desplegable(form1,true);
            ConsultasDesplegable modelo = new ConsultasDesplegable();
            
            ControladorDesplegable controlador = new ControladorDesplegable(desplegable,modelo,2);

            controlador.iniciar();
            form1.btnGuardarUsuario.requestFocus();
        }
        
        
        
    }

    @Override
    public void keyReleased(KeyEvent e) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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
    
    
    
    public void limpiarCajas(){
    //form1.campoBusqueda.setText("");
    //form1.campoID.setText("");
    form1.txtNombreUsuario.setText("");
    form1.txtApellidoUsuario.setText("");
    form1.txtNickUsuario.setText("");
    form1.txtDniUsuario.setText("");
    form1.txtIdUsuario.setText("");
    form1.txtIdPerfilUsuario.setText("");
    form1.txtContraseñaUsuario.setText("");
    form1.txtRepetirContraseñaUsuario.setText("");
    form1.txtNombrePerfilUsuario.setText("");
    
    }

    @Override
    public void focusGained(FocusEvent e) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void focusLost(FocusEvent e) {
        
        if (e.getSource() == form1.txtIdPerfilUsuario) {
          //SI DEJA EL CAMPO VACIO
          if ("".equals(form1.txtIdPerfilUsuario.getText()) ) {
              JOptionPane.showMessageDialog(null, "Por favor complete el codigo de perfil");
            Desplegable desplegable = new Desplegable(form1,true);
            ConsultasDesplegable modelo = new ConsultasDesplegable();
            
            ControladorDesplegable controlador = new ControladorDesplegable(desplegable,modelo,2);

            controlador.iniciar();
            form1.btnGuardarUsuario.requestFocus();
          }
          else {
              //SI LO COMPLETA
              Usuario usuario2 = new Usuario();
              usuario2.setId_perfil(Integer.parseInt(form1.txtIdPerfilUsuario.getText()));
              String perfil = modelo.buscarPerfil(usuario2);
              //SI LO COMPLETA MAL
              if ("Perfil inexistente".equals(perfil)) {
                  JOptionPane.showMessageDialog(null, "Por favor ingrese un codigo de perfil correcto");
                  Desplegable desplegable = new Desplegable(form1, true);
                  ConsultasDesplegable modelo = new ConsultasDesplegable();

                  ControladorDesplegable controlador = new ControladorDesplegable(desplegable, modelo,2);

                  controlador.iniciar();
                  form1.btnGuardarUsuario.requestFocus();
              }
              //SI LO COMPLETA BIEN
              else {
              form1.txtNombrePerfilUsuario.setText(perfil);
              form1.btnGuardarUsuario.requestFocus();
              }
              
          }
      
      }
        
    }
    
    
}
