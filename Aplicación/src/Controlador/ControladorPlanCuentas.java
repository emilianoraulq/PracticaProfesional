/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import Modelo.Conexion;
import Modelo.ConsultasPlanCuentas;
import Modelo.ConsultasUsuario;
import Modelo.Cuenta;
import Modelo.Usuario;
import Vista.FormPlanCuentas;
import Vista.MenuInicial;
import Vista.NuevaCuenta;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Schefer
 */
public class ControladorPlanCuentas implements ActionListener, KeyListener{
    private FormPlanCuentas form1;
    private ConsultasPlanCuentas modelo;
    private Usuario usuario;
    
    public ControladorPlanCuentas(FormPlanCuentas form1,ConsultasPlanCuentas modelo,Usuario usuario){
    this.form1 = form1;
    this.modelo = modelo;
    this.usuario = usuario;
    
    form1.tablaCuentas.setModel(modelo.llenarTablaPlanCuentas());
    form1.botonNueva.addActionListener(this);
    form1.botonModificar.addActionListener(this);
    form1.botonEliminar.addActionListener(this);
    form1.botonDesactivarCuenta.addActionListener(this);
    form1.botonSalir.addActionListener(this);
    form1.botonBuscar.addActionListener(this);
    form1.botonListar.addActionListener(this);
    form1.botonCuentasInactivas.addActionListener(this);
    form1.botonVolver.addActionListener(this);
    form1.botonConfirmar.addActionListener(this);
    
    form1.panelModificarCuenta.setVisible(false);
    }
    
     public void iniciar(){
    form1.setTitle("Empresa: "+Conexion.getNombreBase()+" - Usuario: " + usuario.getNombre() + " " + usuario.getApellido()+"- Plan de Cuentas" );
    form1.setLocationRelativeTo(null);
    form1.setVisible(true);
    
    }
    
    
    

    @Override
    public void actionPerformed(ActionEvent e) {
        
        
        if(e.getSource() == form1.botonNueva) {
            NuevaCuenta form2 = new NuevaCuenta(form1,true);
            //ConsultasDesplegable modelo = new ConsultasDesplegable();
            
            Cuenta cuenta = new Cuenta();
            ControladorNuevaCuenta controlador = new ControladorNuevaCuenta(form2,modelo,usuario,cuenta,0);
            
            this.form1.dispose();
            controlador.iniciar();
            
            
        }
        
        
        
        
        if (e.getSource() == form1.botonSalir) {
            MenuInicial menu = new MenuInicial();
            ConsultasUsuario modeloUsuario = new ConsultasUsuario();
            ControladorMenuInicial controlador = new ControladorMenuInicial(menu,usuario,modeloUsuario);
            controlador.iniciar();
            form1.dispose();
        }
        
        
        
        
        
        if (e.getSource() == form1.botonBuscar) {
            DefaultTableModel modeloTabla = new DefaultTableModel();
            modeloTabla = modelo.buscarCuentas(form1.campoBuscar.getText());
            form1.tablaCuentas.setModel(modeloTabla);
        }
        
        
        
        
        
        if (e.getSource() == form1.botonListar) {
            modelo.ReportePlanDeCuentas();
        } 
        
        
        
        
        
        if (e.getSource() == form1.botonEliminar) {
            //ELIMINA FISICAMENTE LA CUENTA. FALTA CONTROLAR QUE NO TENGA SALDO
           int fila = form1.tablaCuentas.getSelectedRow();
           
           if (fila == -1) {
             JOptionPane.showMessageDialog(form1, "No hay ninguna cuenta seleccionada");  
           }
           else{
             //busco en el nivel siguiente si la cuenta sino tiene hijos
             String codigo = form1.tablaCuentas.getValueAt(fila, 0).toString();
             int nivel = modelo.nivelDeLaCuenta(codigo);
             int cantElementos = modelo.cantidadElementosDelNivel(nivel+1, codigo);
             
             if (cantElementos > 0) {
                JOptionPane.showMessageDialog(form1, "La cuenta no se puede borrar, tiene cuentas asociadas"); 
             }
             else{
               int nroCuenta = (int) form1.tablaCuentas.getValueAt(fila, 3);
               
               int chequeo = JOptionPane.showConfirmDialog(null, "Esta seguro de eliminar la cuenta? Por favor confirme ya que no se podra recuperar.");
        
                    if (chequeo == JOptionPane.YES_OPTION) {
                         if (modelo.eliminarCuenta(nroCuenta)) {
                            JOptionPane.showMessageDialog(null, "Cuenta eliminada con exito"); 
                            form1.tablaCuentas.setModel(modelo.llenarTablaPlanCuentas());
                         }
                         else{
                            JOptionPane.showMessageDialog(null, "Error. La cuenta no se puede eliminar");    
                         }    
                    }
         
               
             }
             
             
           }
       
        
        }
        
        
        
        
        
        if (e.getSource() == form1.botonDesactivarCuenta) {
            
            int fila = form1.tablaCuentas.getSelectedRow();
           
           if (fila == -1) {
             JOptionPane.showMessageDialog(form1, "No hay ninguna cuenta seleccionada");  
           }
           else{
           //busco en el nivel siguiente si la cuenta sino tiene hijos
             String codigo = form1.tablaCuentas.getValueAt(fila, 0).toString();
             int nivel = modelo.nivelDeLaCuenta(codigo);
             int cantElementos = modelo.cantidadElementosDelNivel(nivel+1, codigo);
             
             if (cantElementos > 0) {
                JOptionPane.showMessageDialog(form1, "La cuenta no se puede desactivar, tiene cuentas asociadas"); 
             }
             else{
               int nroCuenta = (int) form1.tablaCuentas.getValueAt(fila, 3);
               if (modelo.desactivarCuenta(nroCuenta)) {
               JOptionPane.showMessageDialog(null, "Cuenta desactivada exitosamente. Puede volver a activarla en el menu de cuentas inactivas");   
                form1.tablaCuentas.setModel(modelo.llenarTablaPlanCuentas());
               }
               else{
               JOptionPane.showMessageDialog(null, "Error. La cuenta no se puede desactivar");    
               }
               
             }
             
             
           }
            
        }
        
        
        
        
        
        if (e.getSource() == form1.botonCuentasInactivas) {
             NuevaCuenta form2 = new NuevaCuenta(form1,true);
            
            Cuenta cuenta = new Cuenta();
            ControladorNuevaCuenta controlador = new ControladorNuevaCuenta(form2,modelo,usuario,cuenta,1);
            
            this.form1.dispose();
            controlador.iniciar();
            
        }
        
        
        
        if (e.getSource() == form1.botonModificar) {
        //traigo los campos al panel
            int fila = form1.tablaCuentas.getSelectedRow();
            
            if (fila == -1) {
                JOptionPane.showMessageDialog(form1, "No hay ninguna cuenta seleccionada");  
            }
            else{
                form1.panelModificarCuenta.setVisible(true);
                
                Cuenta cuenta = new Cuenta();
                cuenta = modelo.getCuenta(Integer.parseInt(form1.tablaCuentas.getValueAt(fila, 3).toString()));
                
                form1.campoCodigo.setText(cuenta.getCodigo());
                form1.campoDescripcion.setText(cuenta.getDescripcion());
                form1.campoNivel.setText(String.valueOf(cuenta.getNivel()));
                form1.campoNroCuenta.setText(String.valueOf(cuenta.getNroCuenta()));
                
                if (cuenta.getInflacion() == 1) {
                    form1.checkAjustable.setSelected(true);
                }
                if (cuenta.getInflacion() == 0) {
                    form1.checkAjustable.setSelected(false);
                }
                
                if (cuenta.getTipo() == 1) {
                    form1.comboTituloCuenta.setSelectedIndex(1);
                }
                
                if (cuenta.getTipo() == 0) {
                    form1.comboTituloCuenta.setSelectedIndex(0);
                }
                
                
               /* 
                form1.campoCodigo.setText(form1.tablaCuentas.getValueAt(fila, 0).toString());
                form1.campoDescripcion.setText(form1.tablaCuentas.getValueAt(fila, 1).toString());
                
                int tipo = (int) form1.tablaCuentas.getValueAt(fila, 2);
                
                if (tipo == 0) {
                  form1.comboTituloCuenta.setSelectedIndex(0);
                }
                else{
                  form1.comboTituloCuenta.setSelectedIndex(1);  
                }
                 
                form1.campoNroCuenta.setText(form1.tablaCuentas.getValueAt(fila, 3).toString());
                */
                
            }
             
            
        }
        
        
        
        
        
        if (e.getSource() == form1.botonVolver) {
           form1.panelModificarCuenta.setVisible(false); 
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
