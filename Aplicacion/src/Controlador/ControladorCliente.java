/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import Modelo.Cliente;
import Modelo.Conexion;
import Modelo.ConsultasCliente;
import Modelo.ConsultasDesplegable;
import Modelo.ConsultasNuevaEmpresa;
import Modelo.ConsultasUsuario;
import Modelo.Usuario;
import Vista.Desplegable;
import Vista.FormClientes;
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
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Schefer
 */
public class ControladorCliente implements ActionListener,KeyListener, MouseListener,FocusListener{
     private FormClientes form1;
     private ConsultasCliente modelo;
     private Cliente cliente;
     private Usuario usuario;
     private ConsultasUsuario modeloUsuario;
     
     public ControladorCliente(FormClientes form1, ConsultasCliente modelo, Cliente provincia,Usuario usuario,ConsultasUsuario modeloUsuario) {
        this.form1 = form1;
        this.modelo = modelo;
        this.cliente = provincia;
        this.usuario=usuario;
        this.modeloUsuario=modeloUsuario;
        
 
        form1.tablaClientes.setModel(modelo.llenarTablaClientes()); //lleno tabla al iniciar
        form1.jPanel2.setVisible(false);
        form1.campoID.setEnabled(false);
        form1.campoNombreProvincia.setEnabled(false);
        form1.campoBusqueda.requestFocus();
        
        
        form1.botonAceptar.addActionListener(this);
        form1.botonBuscar.addActionListener(this);
        form1.botonNuevo.addActionListener(this);
        form1.botonSalir.addActionListener(this);
        form1.botonCancelar.addActionListener(this);
        form1.botonModificar.addActionListener(this);
        form1.botonEliminar.addActionListener(this);
        form1.botonListar.addActionListener(this);
        form1.botonExportar.addActionListener(this);
        
        form1.campoProvincia.addKeyListener(this);
        form1.campoDNI.addKeyListener(this);
        form1.campoDesde.addKeyListener(this);
        form1.campoHasta.addKeyListener(this);
        form1.campoNombre.addKeyListener(this);
        form1.campoApellido.addKeyListener(this);
        
        form1.tablaClientes.addMouseListener(this);
        
        form1.campoProvincia.addFocusListener(this);
        

        
        
    }

     public void iniciar(){
         String perfil="";
         
         perfil=modeloUsuario.ObtenerPerfil(usuario);
         
         if (!perfil.equals("administrador")){
         
         if (!modeloUsuario.existePermiso(perfil,"altaClientes")){
             
             
             form1.botonNuevo.setVisible(false);
             
         }
         
         if (!modeloUsuario.existePermiso(perfil,"modificarClientes")){
             
             form1.botonModificar.setVisible(false);
         }
         
         if (!modeloUsuario.existePermiso(perfil,"eliminarClientes")){
             
             form1.botonEliminar.setVisible(false);
         }
         
         
        }
         //System.out.println(usuario.getId());
     form1.setTitle("Empresa: "+Conexion.getNombreBase()+" - Usuario: " + usuario.getNombre() + " " + usuario.getApellido()+"- Menú de clientes" );
     form1.setLocationRelativeTo(null);    
    form1.setVisible(true);
    //prueba
     //ConsultasNuevaEmpresa modeloEmpresaAux = new ConsultasNuevaEmpresa();
        
      // boolean resultado=(modeloEmpresaAux.establecerConexion("nueva"));
            //JOptionPane.showMessageDialog(null, "Bienvenido "+Conexion.getNombreBase()+""+Conexion.getURL());
        
    //JOptionPane.showMessageDialog(null,Conexion.getNombreBase());
    }
     
     public void limpiarCajas(){
    form1.campoBusqueda.setText("");
    form1.campoID.setText("");
    form1.campoNombre.setText("");
    form1.campoApellido.setText("");
    form1.campoDNI.setText("");
    form1.campoDomicilio.setText("");
    form1.campoProvincia.setText("");
    form1.campoDesde.setText("");
    form1.campoHasta.setText("");
    form1.campoNombreProvincia.setText("");
    }
     
     
    
     
     
    @Override
    public void actionPerformed(ActionEvent e) {
        
        if (e.getSource() == form1.botonExportar) {
        
            int desde = 0;
            int hasta = 0;
            
          if ("".equals(form1.campoDesde.getText()) && ("".equals(form1.campoHasta.getText()))) {
          desde = 1;
          hasta = 1000;
          modelo.crearExcel(desde,hasta);
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
            modelo.crearExcel(desde,hasta);
            limpiarCajas();
            }

          }
          }
        
        }
        
     
        if (e.getSource() == form1.botonBuscar) {
          if (form1.jComboBox1.getSelectedIndex() == 0) {
                
                if ("".equals(form1.campoBusqueda.getText())) {
                DefaultTableModel modeloTabla = new DefaultTableModel();
                modeloTabla = modelo.buscarClientes(cliente,3);
                form1.tablaClientes.setModel(modeloTabla);
                }
                if (!"".equals(form1.campoBusqueda.getText())) { //ver sino pone ningun codigo deberian aparecer todos los codigos
                cliente.setId(Integer.parseInt(form1.campoBusqueda.getText())); 
                DefaultTableModel modeloTabla = new DefaultTableModel();
                modeloTabla = modelo.buscarClientes(cliente,form1.jComboBox1.getSelectedIndex());
                form1.tablaClientes.setModel(modeloTabla);
                }    
               
            }
            
            if (form1.jComboBox1.getSelectedIndex() == 1)  {
            cliente.setNombre(form1.campoBusqueda.getText());
            DefaultTableModel modeloTabla = new DefaultTableModel();
            modeloTabla = modelo.buscarClientes(cliente,form1.jComboBox1.getSelectedIndex());
            form1.tablaClientes.setModel(modeloTabla);
       
            }
            
            if (form1.jComboBox1.getSelectedIndex() == 2)  {
            cliente.setApellido(form1.campoBusqueda.getText());
            DefaultTableModel modeloTabla = new DefaultTableModel();
            modeloTabla = modelo.buscarClientes(cliente,form1.jComboBox1.getSelectedIndex());
            form1.tablaClientes.setModel(modeloTabla);
       
            }
        }
        
        
        if (e.getSource() == form1.botonNuevo) {
        
        form1.botonBuscar.setEnabled(false);
        form1.botonModificar.setEnabled(false);
        form1.botonEliminar.setEnabled(false);
        
        limpiarCajas();
        
        form1.jPanel2.setVisible(true);
        form1.jPanel3.setVisible(false);
        form1.campoNombre.requestFocus();

        
        }
        
        if (e.getSource() == form1.botonModificar) {
         int fila = form1.tablaClientes.getSelectedRow();
        
        if (fila == -1) {
                 JOptionPane.showMessageDialog(form1, "No hay ningun registro seleccionado");
        }
        else {
        form1.jPanel2.setVisible(true);
        form1.jPanel3.setVisible(false);
        
        String id = form1.tablaClientes.getValueAt(fila, 0).toString();
        String nombre = form1.tablaClientes.getValueAt(fila, 1).toString();
        String apellido = form1.tablaClientes.getValueAt(fila, 2).toString();
        String dni= form1.tablaClientes.getValueAt(fila, 3).toString();
        String domicilio= form1.tablaClientes.getValueAt(fila, 4).toString();
        String idProvincia = form1.tablaClientes.getValueAt(fila, 5).toString();
        String nombreProvincia = form1.tablaClientes.getValueAt(fila,6).toString();
        
        form1.campoID.setText(id);
        form1.campoNombre.setText(nombre);
        form1.campoApellido.setText(apellido);
        form1.campoDNI.setText(dni);
        form1.campoDomicilio.setText(domicilio);
        form1.campoProvincia.setText(idProvincia);
        form1.campoNombreProvincia.setText(nombreProvincia);
     
        
        form1.botonNuevo.setEnabled(false);
        form1.botonEliminar.setEnabled(false);
        form1.campoNombre.requestFocus();
        
        }
        }
        
        
        if (e.getSource() == form1.botonEliminar) {
          int fila = form1.tablaClientes.getSelectedRow();

             if (fila == -1) {
                 JOptionPane.showMessageDialog(form1, "No hay ningun registro seleccionado");
             } else {
                 Cliente clienteAux = new Cliente();
                 cliente.setId((int) form1.tablaClientes.getValueAt(fila, 0));
                 cliente.setNombre(form1.tablaClientes.getValueAt(fila, 1).toString());
                 cliente.setApellido(form1.tablaClientes.getValueAt(fila, 2).toString());
                 cliente.setDni(form1.tablaClientes.getValueAt(fila, 3).toString());
                 cliente.setDomicilio(form1.tablaClientes.getValueAt(fila, 4).toString());
                 cliente.setIdProvincia((int) form1.tablaClientes.getValueAt(fila, 5));
                 clienteAux = cliente;
                 
                 if (modelo.eliminarCliente(cliente)) {
                    
                     
                      try {
                         if (modelo.insertarEnAuditoriaClientes(clienteAux, "Eliminacion",usuario)) {
                             
                         }
                     } catch (ParseException ex) {
                         Logger.getLogger(ControladorCliente.class.getName()).log(Level.SEVERE, null, ex);
                     } catch (UnknownHostException ex) {
                         Logger.getLogger(ControladorCliente.class.getName()).log(Level.SEVERE, null, ex);
                     }
                       form1.tablaClientes.setModel(modelo.llenarTablaClientes());
                 }

             }
           
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
            form1.jPanel3.setVisible(true);
            form1.botonNuevo.setEnabled(true);
            form1.botonEliminar.setEnabled(true);
            form1.botonModificar.setEnabled(true);
        }
        
        if (e.getSource() == form1.botonListar) {
            int desde = 0;
            int hasta = 0;
            
          if ("".equals(form1.campoDesde.getText()) && ("".equals(form1.campoHasta.getText()))) {
          desde = 1;
          hasta = 1000;
          modelo.llamarReporteClientes(desde,hasta);
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
            modelo.llamarReporteClientes(desde,hasta);
            limpiarCajas();
            }

          }
          }
          
       
        }
        
        
        
        if (e.getSource() == form1.botonAceptar) {
           //CHEQUEO QUE LOS CAMPOS ESTEN COMPLETOS
           if (("".equals(form1.campoNombre.getText()) ) || ("".equals(form1.campoApellido.getText())) || ("".equals(form1.campoDNI.getText())) || ("".equals(form1.campoDomicilio.getText())) ) {
        JOptionPane.showMessageDialog(null, "Por favor complete TODOS los campos");
           }
           //CHEQUEO QUE EL CAMPO PROVINCIA NO ESTE VACIO
           else if ("".equals(form1.campoProvincia.getText())) {
                
                JOptionPane.showMessageDialog(null, "Por favor complete el campo Provincia");
                Desplegable desplegable = new Desplegable(form1,true);
                ConsultasDesplegable modelo = new ConsultasDesplegable();
            
                ControladorDesplegable controlador = new ControladorDesplegable(desplegable,modelo,1);

                controlador.iniciar();
            
                }

            
           else 
        {
            
            cliente.setNombre(form1.campoNombre.getText());
            cliente.setApellido(form1.campoApellido.getText());
            cliente.setDomicilio(form1.campoDomicilio.getText());
            cliente.setDni(form1.campoDNI.getText());
            cliente.setIdProvincia(Integer.parseInt(form1.campoProvincia.getText()));
     

            //CREO UN CLIENTE
            if (form1.botonNuevo.isEnabled()) {
                
                  //CHEQUEO QUE EL DNI QUE ME INGRESARON NO ESTE YA INGRESADO
                if (modelo.buscarDniAlInsertar(cliente)) {
                JOptionPane.showMessageDialog(null, "Por favor verifique DNI existente");
                form1.campoDNI.requestFocus();
                }
                else {
                //DNI CORRECTO  
                     if (modelo.crearCliente(cliente)) {
                    JOptionPane.showMessageDialog(null, "Registro insertado correctamente");
                     //inserto en la tabla de auditorias de cliente
                    int id = modelo.buscarIdCliente(cliente);
                    cliente.setId(id);
                    try {
                        //System.out.println(usuario.getId());
                             if (modelo.insertarEnAuditoriaClientes(cliente, "Nuevo",usuario)) {
                                 
                             }    } catch (ParseException ex) {
                             Logger.getLogger(ControladorCliente.class.getName()).log(Level.SEVERE, null, ex);
                         } catch (UnknownHostException ex) {
                             Logger.getLogger(ControladorCliente.class.getName()).log(Level.SEVERE, null, ex);
                         }
                    
                    limpiarCajas();
                    form1.tablaClientes.setModel(modelo.llenarTablaClientes());
                    form1.botonNuevo.setEnabled(true);
                    form1.botonBuscar.setEnabled(true);
                    form1.botonModificar.setEnabled(true);
                    form1.botonEliminar.setEnabled(true);
                    form1.jPanel2.setVisible(false);
                    form1.jPanel3.setVisible(true);
                     }
                     
                    else {
                    JOptionPane.showMessageDialog(null, "Error al insertar el registro");
                    Desplegable desplegable = new Desplegable(form1,true);
                    ConsultasDesplegable modelo = new ConsultasDesplegable();
            
                    ControladorDesplegable controlador = new ControladorDesplegable(desplegable,modelo,1);

                    controlador.iniciar();
                    //limpiarCajas();
                     }
                }
            }
            
            //MODIFICO UN CLIENTE
            else if (form1.botonModificar.isEnabled()) {
                
            cliente.setId(Integer.parseInt(form1.campoID.getText()));
            
            int id = modelo.buscarDniAlModificar(Integer.parseInt(cliente.getDni()));
            //esto devuelve 0 si el dni no existe o el mismo dni si el cliente modifica otro campo que no sea el DNI
            
                if ((id == cliente.getId()) || (id == 0)) {
             //lo puedo modificar totalmente
                    if (modelo.modificarCliente(cliente)) {
                        JOptionPane.showMessageDialog(null, "Registro modificado correctamente");
                         try {
                            if (modelo.insertarEnAuditoriaClientes(cliente, "Modificacion", usuario)) {
                                
                            }
                        } catch (ParseException ex) {
                            Logger.getLogger(ControladorCliente.class.getName()).log(Level.SEVERE, null, ex);
                        } catch (UnknownHostException ex) {
                            Logger.getLogger(ControladorCliente.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        limpiarCajas();
                        form1.tablaClientes.setModel(modelo.llenarTablaClientes());
                        form1.botonNuevo.setEnabled(true);
                        form1.botonBuscar.setEnabled(true);
                        form1.botonModificar.setEnabled(true);
                        form1.botonEliminar.setEnabled(true);
                        form1.jPanel2.setVisible(false);
                        form1.jPanel3.setVisible(true);
                    }
                    else {
                        //no se puede modificar
                        JOptionPane.showMessageDialog(null, "Error al modificar el registro");
                        Desplegable desplegable = new Desplegable(form1,true);
                        ConsultasDesplegable modelo = new ConsultasDesplegable();
            
                        ControladorDesplegable controlador = new ControladorDesplegable(desplegable,modelo,1);

                        controlador.iniciar();
                    }
             
                }
                
                else {
                    //no se puede modificar porque lo tiene otra persona el dni
                    JOptionPane.showMessageDialog(null, "Por favor verifique DNI ya existente");
                    form1.campoDNI.requestFocus();
                }
              
            
            } //boton modificar
        
          
        
        }
           //le vuelvo a poner el focus listener al campo, ya que si presiona F1 se lo saca
        form1.campoProvincia.addFocusListener(this);
        } //fin boton aceptar
       
   
    }

    @Override
    public void keyTyped(KeyEvent e) {
        
        if (e.getSource() == form1.campoNombre) {
            
        char c = e.getKeyChar();
        
        if ((c >= '0') && (c <= '9')) {
             e.consume();
            }
        
        if (form1.campoNombre.getText().length() ==21) {
         e.consume();  
        }
        
        }
        
         if (e.getSource() == form1.campoApellido) {
             
         char c = e.getKeyChar();
        
         if ((c >= '0') && (c <= '9')) {
             e.consume();
            }
            
        if (form1.campoApellido.getText().length() ==21) {
         e.consume();  
        }
        
        }
        
        
        //NO PERMITO CARACTERES NI QUE SUPERE LOS OCHO DIGITOS EN EL CAMPO DNI
      if (e.getSource() == form1.campoDNI) {
       char c = e.getKeyChar();
        
        if (((c < '0') || (c > '9')) && (c!= KeyEvent.VK_BACK_SPACE)) {
             e.consume();
            }
        if (form1.campoDNI.getText().length() ==9) {
         e.consume();   
        
        }
        
      } 
      
      if (e.getSource() == form1.campoProvincia) {
      char c = e.getKeyChar();
        
        if (((c < '0') || (c > '9')) && (c!= KeyEvent.VK_BACK_SPACE) && (c!=KeyEvent.VK_ENTER)) { 
            form1.campoProvincia.requestFocus();
             e.consume();
            
            
            }
      }
      
      if (e.getSource() == form1.campoDesde) {
      char c = e.getKeyChar();
        
        if (((c < '0') || (c > '9')) && (c!= KeyEvent.VK_BACK_SPACE)) {
             e.consume();
            }
      }
      
      if (e.getSource() == form1.campoHasta) {
      char c = e.getKeyChar();
        
        if (((c < '0') || (c > '9')) && (c!= KeyEvent.VK_BACK_SPACE)) {
             e.consume();
            }
      }
      
      
    }

    @Override
    public void keyPressed(KeyEvent e) {
        
      if (e.getSource() == form1.campoProvincia) {
          char c = e.getKeyChar();
            
        if (e.getKeyCode() == KeyEvent.VK_F1) {
            form1.campoProvincia.removeFocusListener(this);
            Desplegable desplegable = new Desplegable(form1,true);
            ConsultasDesplegable modelo = new ConsultasDesplegable();
            
            ControladorDesplegable controlador = new ControladorDesplegable(desplegable,modelo,1);

            controlador.iniciar();
            form1.botonAceptar.requestFocus();
        }
        
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            form1.botonAceptar.requestFocus();
       /*     
            //ESTA VACIO
            if ("".equals(form1.campoProvincia.getText())) {
            JOptionPane.showMessageDialog(null, "Por favor complete la provincia");
            Desplegable desplegable = new Desplegable(form1,true);
            ConsultasDesplegable modelo = new ConsultasDesplegable();
            
            ControladorDesplegable controlador = new ControladorDesplegable(desplegable,modelo,1);

            controlador.iniciar();
            }
            //NO ESTA VACIO
            else {
            cliente.setIdProvincia(Integer.parseInt(form1.campoProvincia.getText()));
            String provincia = modelo.buscarProvincia(cliente);
            
            if ("Provincia inexistente".equals(provincia)) {
            JOptionPane.showMessageDialog(null, "Por favor ingrese un codigo correcto de provincia");
            Desplegable desplegable = new Desplegable(form1,true);
            ConsultasDesplegable modelo = new ConsultasDesplegable();
            
            ControladorDesplegable controlador = new ControladorDesplegable(desplegable,modelo,1);

            controlador.iniciar();
            }
            //INGRESO UN CODIGO CORRECTO
            else {
             form1.campoNombreProvincia.setText(provincia);
            }
           
            }
           
            form1.botonAceptar.requestFocus();
      */  }
 
        }
    
    }

    @Override
    public void keyReleased(KeyEvent e) {
        
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        
    }

    @Override
    public void mousePressed(MouseEvent e) {
    int fila = form1.tablaClientes.getSelectedRow();
           if (e.getSource() == form1.tablaClientes) {
               
          
           if (fila == -1) {
                JOptionPane.showMessageDialog(null, "No hay ningun registro seleccionado");
            } 
            else {
                form1.campoID.setText(form1.tablaClientes.getValueAt(fila, 0).toString());
                form1.campoNombre.setText(form1.tablaClientes.getValueAt(fila, 1).toString());
                form1.campoApellido.setText(form1.tablaClientes.getValueAt(fila, 2).toString());
                form1.campoDNI.setText(form1.tablaClientes.getValueAt(fila, 3).toString());
                form1.campoDomicilio.setText(form1.tablaClientes.getValueAt(fila, 4).toString());
                form1.campoProvincia.setText(form1.tablaClientes.getValueAt(fila, 5).toString());
               
           }
   
        }    
    }

    @Override
    public void mouseReleased(MouseEvent e) {
       
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        
    }

    @Override
    public void mouseExited(MouseEvent e) {
       
    }

    @Override
    public void focusGained(FocusEvent e) {
     
    }

    @Override
    public void focusLost(FocusEvent e) {

       
      if (e.getSource() == form1.campoProvincia) {
          //SI DEJA EL CAMPO VACIO
          if ("".equals(form1.campoProvincia.getText()) ) {
              JOptionPane.showMessageDialog(null, "Por favor complete el codigo de provincia");
            Desplegable desplegable = new Desplegable(form1,true);
            ConsultasDesplegable modelo = new ConsultasDesplegable();
            
            ControladorDesplegable controlador = new ControladorDesplegable(desplegable,modelo,1);

            controlador.iniciar();
            form1.botonAceptar.requestFocus();
          }
          else {
              //SI LO COMPLETA
              Cliente cliente = new Cliente();
              cliente.setIdProvincia(Integer.parseInt(form1.campoProvincia.getText()));
              String provincia = modelo.buscarProvincia(cliente);
              //SI LO COMPLETA MAL
              if ("Provincia inexistente".equals(provincia)) {
                  JOptionPane.showMessageDialog(null, "Por favor ingrese un codigo de provincia correcto");
                  Desplegable desplegable = new Desplegable(form1, true);
                  ConsultasDesplegable modelo = new ConsultasDesplegable();

                  ControladorDesplegable controlador = new ControladorDesplegable(desplegable, modelo,1);

                  controlador.iniciar();
                  form1.botonAceptar.requestFocus();
              }
              //SI LO COMPLETA BIEN
              else {
              form1.campoNombreProvincia.setText(provincia);
              form1.botonAceptar.requestFocus();
              }
              
          }
      
      }
   
      
    }
     
     
     
}
