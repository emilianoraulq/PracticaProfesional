/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import Modelo.Conexion;
import Modelo.ConsultasProvincia;
import Modelo.ConsultasUsuario;
import Modelo.Provincia;
import Modelo.Usuario;
import Vista.FormProvincias;
import Vista.MenuInicial;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.net.UnknownHostException;
import java.text.ParseException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Schefer
 */
public class ControladorProvincia implements ActionListener,MouseListener,KeyListener{
    private FormProvincias form1;
    private ConsultasProvincia modelo;
    private Provincia provincia;
    private Usuario usuario;
     private ConsultasUsuario modeloUsuario;

    public ControladorProvincia(FormProvincias form1, ConsultasProvincia modelo, Provincia provincia,Usuario usuario,ConsultasUsuario modeloUsuario) {
        this.form1 = form1;
        this.modelo = modelo;
        this.provincia = provincia;
         this.usuario=usuario;
        this.modeloUsuario=modeloUsuario;
 
        form1.tablaProvincias.setModel(modelo.llenarTabla()); //lleno tabla al iniciar
        form1.jPanel2.setVisible(false);
        form1.campoID.setEnabled(false);
        
        form1.botonAceptar.addActionListener(this);
        form1.botonBuscar.addActionListener(this);
        form1.botonNuevo.addActionListener(this);
        form1.botonSalir.addActionListener(this);
        form1.botonCancelar.addActionListener(this);
        form1.botonModificar.addActionListener(this);
        form1.botonEliminar.addActionListener(this);
        form1.botonListar.addActionListener(this);
        
        form1.campoDesde.addKeyListener(this);
        form1.campoHasta.addKeyListener(this);
        form1.campoNombre.addKeyListener(this);
                
        form1.tablaProvincias.addMouseListener(this);
        
        
    }
    
    public void iniciar(){
        
        
        
    String perfil="";
         
         perfil=modeloUsuario.ObtenerPerfil(usuario);
         
         if (!perfil.equals("administrador")){
         
         if (!modeloUsuario.existePermiso(perfil,"altaProvincias")){
             
             
             form1.botonNuevo.setVisible(false);
             
         }
         
         if (!modeloUsuario.existePermiso(perfil,"modificarProvincias")){
             
             form1.botonModificar.setVisible(false);
         }
         
         if (!modeloUsuario.existePermiso(perfil,"eliminarProvincias")){
             
             form1.botonEliminar.setVisible(false);
         }
         
         
        }    
        
        
    form1.setTitle("Empresa: "+Conexion.getNombreBase()+" - Usuario: " + usuario.getNombre() + " " + usuario.getApellido()+"- Menú de provincias" );
    form1.setLocationRelativeTo(null);
    form1.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        
        if (e.getSource() == form1.botonAceptar) {
            if("".equals(form1.campoNombre.getText())) {
            JOptionPane.showMessageDialog(null, "Por favor complete el campo Nombre");
            }
            else {
            provincia.setNombre(form1.campoNombre.getText());
            
                if (form1.botonNuevo.isEnabled()) {
                    
                    if (modelo.buscarNombreProvincia(provincia)) {
                    JOptionPane.showMessageDialog(null, "Por favor verifique nombre de Provincia existente");
                    form1.campoNombre.requestFocus();
                    }
                    else
                    {    
                    if (modelo.insertar(provincia)) {
                    JOptionPane.showMessageDialog(null, "Registro insertado correctamente");
                    //inserto en la tabla de auditorias
                    int id = modelo.buscarIdProvincia(provincia);
                    provincia.setId(id);
                    provincia.setNombre(form1.campoNombre.getText());
                        try {
                            if (modelo.insertarEnAuditoriaProvincias(provincia, "Nueva",usuario)) {
                                
                            }   } catch (ParseException ex) {
                            Logger.getLogger(ControladorProvincia.class.getName()).log(Level.SEVERE, null, ex);
                        } catch (UnknownHostException ex) {
                            Logger.getLogger(ControladorProvincia.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    
                    limpiarCajas();
                    form1.tablaProvincias.setModel(modelo.llenarTabla());
                    form1.botonNuevo.setEnabled(true);
                    form1.botonModificar.setEnabled(true);
                    form1.botonEliminar.setEnabled(true);
                    form1.jPanel2.setVisible(false);
                     }
                    else {
                    JOptionPane.showMessageDialog(null, "Error al insertar el registro");
                    limpiarCajas();
                     }
                    }
                }
                
                if (form1.botonModificar.isEnabled()) {
                    
                    provincia.setId(Integer.parseInt(form1.campoID.getText()));
                    
                    int id = modelo.buscarIdProvincia(provincia);
                    
                    if ((id == provincia.getId()) || (id == 0)) {
                        
                        if (modelo.modificar(provincia)) {
                        JOptionPane.showMessageDialog(null, "Registro modificado correctamente");
                         //inserto la modificacion en la tabla de auditoria

                        //provincia.setId(id);
                        //provincia.setNombre(form1.campoNombre.getText());
                        try {
                            if (modelo.insertarEnAuditoriaProvincias(provincia, "Modificacion",usuario)) {
                                
                            }   } catch (ParseException ex) {
                            Logger.getLogger(ControladorProvincia.class.getName()).log(Level.SEVERE, null, ex);
                        } catch (UnknownHostException ex) {
                            Logger.getLogger(ControladorProvincia.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        limpiarCajas();
                        form1.tablaProvincias.setModel(modelo.llenarTabla());
                        form1.botonNuevo.setEnabled(true);
                        form1.botonModificar.setEnabled(true);
                        form1.botonEliminar.setEnabled(true);
                        form1.jPanel2.setVisible(false);
                    
                         }
                        else {
                        JOptionPane.showMessageDialog(null, "No se puede modificar la provincia, hay clientes asociados a la provincia");
                        limpiarCajas();
                        }
                    }
                    else {
                    JOptionPane.showMessageDialog(null, "Por favor verifique Provincia existente");
                    form1.campoNombre.requestFocus();
                    }
                    
                
                    
                } //boton modificar
                
                } 
          

        } //fin boton aceptar
        
        if (e.getSource() == form1.botonBuscar) { 
            if (form1.jComboBox1.getSelectedIndex() == 0) {
                
                if ("".equals(form1.campoBusqueda.getText())) {
                DefaultTableModel modeloTabla = new DefaultTableModel();
                modeloTabla = modelo.buscarProvincias(provincia,2);
                form1.tablaProvincias.setModel(modeloTabla);
                }
                if (!"".equals(form1.campoBusqueda.getText())) { //ver sino pone ningun codigo deberian aparecer todos los codigos
                provincia.setId(Integer.parseInt(form1.campoBusqueda.getText())); 
                DefaultTableModel modeloTabla = new DefaultTableModel();
                modeloTabla = modelo.buscarProvincias(provincia,form1.jComboBox1.getSelectedIndex());
                form1.tablaProvincias.setModel(modeloTabla);
                }    
               
            }
            
            if (form1.jComboBox1.getSelectedIndex() == 1) {
            provincia.setNombre(form1.campoBusqueda.getText());
            DefaultTableModel modeloTabla = new DefaultTableModel();
            modeloTabla = modelo.buscarProvincias(provincia,form1.jComboBox1.getSelectedIndex());
        
            form1.tablaProvincias.setModel(modeloTabla);
            }
        
        
        }
        
        if (e.getSource() == form1.botonNuevo) {
        limpiarCajas();
        form1.jPanel2.setVisible(true);
        form1.botonModificar.setEnabled(false);
        form1.botonEliminar.setEnabled(false);
        form1.campoNombre.requestFocus();
        
        }
        
        if (e.getSource() == form1.botonSalir) {
        form1.dispose();
         MenuInicial menu = new MenuInicial();
         //Usuario usuario = new Usuario();
         //ConsultasUsuario modeloUsuario=new ConsultasUsuario();
         ControladorMenuInicial controlador = new ControladorMenuInicial(menu,usuario,modeloUsuario);
         controlador.iniciar();
        }
        
        if (e.getSource() == form1.botonCancelar) {
        form1.jPanel2.setVisible(false);
        form1.botonModificar.setEnabled(true);
        form1.botonEliminar.setEnabled(true);
        form1.botonNuevo.setEnabled(true);
        
        limpiarCajas();
        }
        
        if (e.getSource() == form1.botonEliminar) {
             int fila = form1.tablaProvincias.getSelectedRow();

             if (fila == -1) {
                 JOptionPane.showMessageDialog(form1, "No hay ningun registro seleccionado");
             } else {
                 provincia.setNombre(form1.tablaProvincias.getValueAt(fila, 1).toString());
                 provincia.setId((int) form1.tablaProvincias.getValueAt(fila, 0));
                 if (modelo.eliminar(provincia)) {
                     //inserto la eliminacion en la tabla de auditoria

                        try {
                            if (modelo.insertarEnAuditoriaProvincias(provincia, "Eliminacion",usuario)) {
                                
                            }   } catch (ParseException ex) {
                            Logger.getLogger(ControladorProvincia.class.getName()).log(Level.SEVERE, null, ex);
                        } catch (UnknownHostException ex) {
                            Logger.getLogger(ControladorProvincia.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        
                     form1.tablaProvincias.setModel(modelo.llenarTabla());
                 }

             }

        }
         
        if (e.getSource() == form1.botonModificar) {
        int fila = form1.tablaProvincias.getSelectedRow();
        
        if (fila == -1) {
                 JOptionPane.showMessageDialog(form1, "No hay ningun registro seleccionado");
        }
        else {
        form1.jPanel2.setVisible(true);
        String nombre = form1.tablaProvincias.getValueAt(fila, 1).toString();
        String id = form1.tablaProvincias.getValueAt(fila, 0).toString();
        form1.campoID.setText(id);
        form1.campoNombre.setText(nombre);
        form1.botonNuevo.setEnabled(false);
        form1.botonEliminar.setEnabled(false);
        form1.campoNombre.requestFocus();
        }

        } 
        
        if (e.getSource() == form1.botonListar) {
          int desde = 0;
            int hasta = 0;
            
          if ("".equals(form1.campoDesde.getText()) && ("".equals(form1.campoHasta.getText()))) {
          desde = 1;
          hasta = 1000;
          modelo.llamarReporteProvincias(desde,hasta);
          limpiarCajas();
          }
          
          else {
            if ("".equals(form1.campoDesde.getText())) {
            JOptionPane.showMessageDialog(null,"Por favor complete el campo DESDE");
            }
            
            else {
            if ("".equals(form1.campoHasta.getText())) {
            JOptionPane.showMessageDialog(null,"Por favor complete el campo HASTA");
            }
            else {
              desde = Integer.parseInt(form1.campoDesde.getText());
              hasta = Integer.parseInt(form1.campoHasta.getText());  
            modelo.llamarReporteProvincias(desde,hasta);
            limpiarCajas();
            }

          }
          }
          
        }
   
        
    }
    
    public void limpiarCajas(){
    form1.campoBusqueda.setText("");
    form1.campoID.setText("");
    form1.campoNombre.setText("");
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        
    }

    @Override
    public void mousePressed(MouseEvent e) {
       
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        
        int fila = form1.tablaProvincias.getSelectedRow();
           if (e.getSource() == form1.tablaProvincias) {
               
          
           if (fila == -1) {
                JOptionPane.showMessageDialog(null, "No hay ningun registro seleccionado");
            } 
            else {
                form1.campoID.setText(form1.tablaProvincias.getValueAt(fila, 0).toString());
                form1.campoNombre.setText(form1.tablaProvincias.getValueAt(fila, 1).toString());
               
           }
   
        }    
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        
    }

    @Override
    public void mouseExited(MouseEvent e) {
        
    }

    @Override
    public void keyTyped(KeyEvent e) {
        
      if (e.getSource() == form1.campoDesde) { 
        char c = e.getKeyChar();
        
        if (((c < '0') || (c > '9')) && (c!= KeyEvent.VK_BACK_SPACE)) {
             e.consume();
            JOptionPane.showMessageDialog(null, "Ingrese solo numeros, se lista por ID Provincia");
            }
    }
      
     if (e.getSource() == form1.campoHasta) { 
        char c = e.getKeyChar();
        
        if (((c < '0') || (c > '9')) && (c!= KeyEvent.VK_BACK_SPACE)) {
             e.consume();
            JOptionPane.showMessageDialog(null, "Ingrese solo numeros, se lista por ID Provincia");
            }
    }
     

      
      
      
    }

    @Override
    public void keyPressed(KeyEvent e) {
        
          if (e.getSource() == form1.campoNombre) {
     
         if (e.getKeyCode() == KeyEvent.VK_ENTER) {
             
            if("".equals(form1.campoNombre.getText())) {
            JOptionPane.showMessageDialog(null, "Por favor complete el campo Nombre");
            }
            else {
            provincia.setNombre(form1.campoNombre.getText());
            
                if (form1.botonNuevo.isEnabled()) {
                    
                    if (modelo.buscarNombreProvincia(provincia)) {
                    JOptionPane.showMessageDialog(null, "Por favor verifique nombre de Provincia existente");
                    form1.campoNombre.requestFocus();
                    }
                    else
                    {    
                    if (modelo.insertar(provincia)) {
                    JOptionPane.showMessageDialog(null, "Registro insertado correctamente");
                    //inserto en la tabla de auditorias
                    int id = modelo.buscarIdProvincia(provincia);
                    provincia.setId(id);
                    provincia.setNombre(form1.campoNombre.getText());
                        try {
                            if (modelo.insertarEnAuditoriaProvincias(provincia, "Nueva",usuario)) {
                                
                            }   } catch (ParseException ex) {
                            Logger.getLogger(ControladorProvincia.class.getName()).log(Level.SEVERE, null, ex);
                        } catch (UnknownHostException ex) {
                            Logger.getLogger(ControladorProvincia.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    limpiarCajas();
                    form1.tablaProvincias.setModel(modelo.llenarTabla());
                    form1.botonNuevo.setEnabled(true);
                    form1.botonModificar.setEnabled(true);
                    form1.botonEliminar.setEnabled(true);
                    form1.jPanel2.setVisible(false);
                     }
                    else {
                    JOptionPane.showMessageDialog(null, "Error al insertar el registro");
                    limpiarCajas();
                     }
                    }
                }
                
                if (form1.botonModificar.isEnabled()) {
                    
                    provincia.setId(Integer.parseInt(form1.campoID.getText()));
                    
                    int id = modelo.buscarIdProvincia(provincia);
                    
                    if ((id == provincia.getId()) || (id == 0)) {
                        
                        if (modelo.modificar(provincia)) {
                        JOptionPane.showMessageDialog(null, "Registro modificado correctamente");
                        //inserto la modificacion en la tabla de auditoria

                        //provincia.setId(id);
                        //provincia.setNombre(form1.campoNombre.getText());
                        try {
                            if (modelo.insertarEnAuditoriaProvincias(provincia, "Modificacion",usuario)) {
                                
                            }   } catch (ParseException ex) {
                            Logger.getLogger(ControladorProvincia.class.getName()).log(Level.SEVERE, null, ex);
                        } catch (UnknownHostException ex) {
                            Logger.getLogger(ControladorProvincia.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        limpiarCajas();
                        form1.tablaProvincias.setModel(modelo.llenarTabla());
                        form1.botonNuevo.setEnabled(true);
                        form1.botonModificar.setEnabled(true);
                        form1.botonEliminar.setEnabled(true);
                        form1.jPanel2.setVisible(false);
                    
                         }
                        else {
                        JOptionPane.showMessageDialog(null, "No se puede modificar la provincia, hay clientes asociados a la provincia");
                        limpiarCajas();
                        form1.jPanel2.setVisible(false);
                        }
                    }
                    else {
                    JOptionPane.showMessageDialog(null, "Por favor verifique Provincia existente");
                    form1.campoNombre.requestFocus();
                    }
                    
                
                    
                } //fin pulsacion ENTER
                
                } 
          

         }
     
     } 
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
    
    
    
}
