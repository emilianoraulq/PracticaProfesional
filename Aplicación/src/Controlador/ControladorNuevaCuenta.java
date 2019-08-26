/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import Modelo.Conexion;
import Modelo.ConsultasPlanCuentas;
import Modelo.Cuenta;
import Modelo.Usuario;
import Vista.FormPlanCuentas;
import Vista.NuevaCuenta;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JOptionPane;

/**
 *
 * @author Schefer
 */
public class ControladorNuevaCuenta implements ActionListener, KeyListener, MouseListener {
    private NuevaCuenta form1;
    private ConsultasPlanCuentas modelo;
    private Usuario usuario;
    private Cuenta cuenta;
    private int opcion;
    
    public ControladorNuevaCuenta(NuevaCuenta form1,ConsultasPlanCuentas modelo,Usuario usuario, Cuenta cuenta, int opcion){
    this.form1 = form1;
    this.modelo = modelo;
    this.usuario = usuario;
    this.cuenta = cuenta;
    this.opcion = opcion;
    
    form1.tablaCuentas.setModel(modelo.llenarTablaConTitulos());
    form1.botonAceptar.addActionListener(this);
    form1.botonVolver.addActionListener(this);
    form1.tablaCuentas.addMouseListener(this);
    
    form1.campoCodigo.addKeyListener(this);
    
        if (opcion == 1) {
            form1.jPanel2.setVisible(false);
            form1.etiquetaLeyenda.setText("Haga doble click sobre la cuenta que quiere volver a activar");
            form1.etiquetaTitulo.setText("Recuperar cuenta");
            form1.tablaCuentas.setModel(modelo.llenarTablaConCuentasInactivas());
        }
        

    
    }
    
     public void iniciar(){
    form1.setTitle("Empresa: "+Conexion.getNombreBase()+" - Usuario: " + usuario.getNombre() + " " + usuario.getApellido()+"- Plan de Cuentas" );
    form1.setLocationRelativeTo(null);
    form1.setVisible(true);
 
    
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        
        if (e.getSource() == form1.botonAceptar) { 
            
            //controlo que no haya dejado vacio los campos
            if ("".equals(form1.campoCodigo.getText()) || ("".equals(form1.campoDescripcion.getText()))) {
                JOptionPane.showMessageDialog(null,"Por favor complete todos los campos");
            }
            else{
                //una vez que los campos estan completos controlo que no haya ingresado un codigo de balance ya existente
                if(modelo.buscarCodigoBalance(form1.campoCodigo.getText())) {
                    JOptionPane.showMessageDialog(null,"Por favor ingrese codigo de balance no existente");  
                    form1.campoCodigo.requestFocus();
                }
                else{               
               //establezco los campos de la cuenta o titulo para insertar en la BBDD
                    cuenta.setCodigo(form1.campoCodigo.getText());
            
                    if (form1.comboCuentaTitulo.getSelectedIndex() == 0) {
                        cuenta.setDescripcion(form1.campoDescripcion.getText().toUpperCase());
                        cuenta.setTipo(0);
                
                     }
                    
                    if (form1.comboCuentaTitulo.getSelectedIndex() == 1) {
                        cuenta.setDescripcion(form1.campoDescripcion.getText());
                        cuenta.setTipo(1);
                
                    }
                    
                    cuenta.setNivel(Integer.parseInt(form1.campoNivel.getText()));
                    cuenta.setInflacion(0);
                    cuenta.setActivo(1);
            
           
            //la inserto en la BBDD
                if(modelo.crearCuenta(cuenta)) {
                JOptionPane.showMessageDialog(null, "Cuenta creada correctamente");
                
            //vuelvo al menu de cuentas
                FormPlanCuentas formplancuentas = new FormPlanCuentas();
                ConsultasPlanCuentas modelo = new ConsultasPlanCuentas();
                ControladorPlanCuentas controlador = new ControladorPlanCuentas(formplancuentas,modelo,usuario);
                
                this.form1.dispose();
                controlador.iniciar();
             
                
           }
           else{
                JOptionPane.showMessageDialog(null, "Error al crear la cuenta");   
           }     
                    
                }
                
        
            }
     
        }
        
        
        if (e.getSource() == form1.botonVolver) {
             FormPlanCuentas formplancuentas = new FormPlanCuentas();
             ConsultasPlanCuentas modelo = new ConsultasPlanCuentas();
             ControladorPlanCuentas controlador = new ControladorPlanCuentas(formplancuentas,modelo,usuario);
             this.form1.dispose();
             controlador.iniciar();
             
             
        }
        
        
        
        
        
    }

    @Override
    public void keyTyped(KeyEvent e) {
      
        //el campo codigo de balance solo permite numeros, guiones y puntos
      if (e.getSource() == form1.campoCodigo) {
      char c = e.getKeyChar();
        
        if (((c < '0') || (c > '9')) && (c!= KeyEvent.VK_BACK_SPACE) && (c!=KeyEvent.VK_ENTER) && (c!=KeyEvent.VK_PERIOD) && (c!=KeyEvent.VK_MINUS)) { 
            form1.campoCodigo.requestFocus();
             e.consume();
            
            
            }
      }
        
    }

    @Override
    public void keyPressed(KeyEvent e) {
        
    }

    @Override
    public void keyReleased(KeyEvent e) {
       
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }
    

    @Override
    public void mousePressed(MouseEvent e) {
        
        if (e.getSource() == form1.tablaCuentas) {
               
           if (e.getClickCount() == 2) {
               
               if (opcion == 1) {
                   //activo de nuevo la cuenta desactivada
                   int fila = form1.tablaCuentas.getSelectedRow();
                   int nroCuenta = (int) form1.tablaCuentas.getValueAt(fila, 3);
                   
                   if (modelo.activarCuenta(nroCuenta)) {
                       
                        JOptionPane.showMessageDialog(null, "Cuenta reactivada");
                        FormPlanCuentas formplancuentas = new FormPlanCuentas();
                        ConsultasPlanCuentas modelo = new ConsultasPlanCuentas();
                        ControladorPlanCuentas controlador = new ControladorPlanCuentas(formplancuentas,modelo,usuario);
                        this.form1.dispose();
                        controlador.iniciar();
                       
                   }
                   else{
                       JOptionPane.showMessageDialog(null, "La cuenta no se puede reactivar");
                   }
                   
                   
                   
                   
               }
           
           }
        
        }
        
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (e.getSource() == form1.tablaCuentas) {
            //obtengo la fila que selecciono el usuario
            int fila = form1.tablaCuentas.getSelectedRow();
            String codigo = "";
            
            //veo en que nivel del plan de cuentas esta 
            int nivel = modelo.nivelDeLaCuenta(form1.tablaCuentas.getValueAt(fila, 0).toString());
            form1.campoNivel.setText(String.valueOf(nivel+1));
            
            //calculo la cantidad de nodos que tiene el nivel inmediato posterior, o sea +1, del codigo seleccionado
            //SACAR EL COMENTARIO DE CANTIDAD NODOS
            int cantidadNodos = modelo.cantidadElementosDelNivel(nivel+1,form1.tablaCuentas.getValueAt(fila, 0).toString());
            
            //asigno el codigo de balance al campo de texto. Si el nivel es 0 el codigo del nivel siguiente no lleva cero. Por ej. 1.1, 1.2, etc
            if (nivel == 0) {
            codigo = form1.tablaCuentas.getValueAt(fila, 0).toString() + "." + String.valueOf(cantidadNodos+1);
            }
            else {
                if (cantidadNodos <= 8) { //agrego el cero
                codigo = form1.tablaCuentas.getValueAt(fila, 0).toString() + "." + "0" + String.valueOf(cantidadNodos+1);   
                }
                else {
                codigo = form1.tablaCuentas.getValueAt(fila, 0).toString() + "." + String.valueOf(cantidadNodos+1);    
                }
            
            }
            
            //asigno el codigo al campo de texto 
            form1.campoCodigo.setText(codigo);
            
            
        }
        
        
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {
        
    }
    
}
