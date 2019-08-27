/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import Modelo.Asiento;
import Modelo.Conexion;
import Modelo.ConsultasAsientos;
import Modelo.ConsultasDesplegable;
import Modelo.ConsultasUsuario;
import Modelo.Renglon;
import Modelo.Usuario;
import Vista.Desplegable;
import Vista.FormAsientos;
import Vista.FormPlanCuentas;
import Vista.MenuInicial;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Schefer
 */
public class ControladorAsientos implements ActionListener, KeyListener, FocusListener {
    
    private FormAsientos form1;
    private ConsultasAsientos modelo;
    private Usuario usuario; 
    
    
    public ControladorAsientos(FormAsientos form1,ConsultasAsientos modelo,Usuario usuario){
    this.form1 = form1;
    this.modelo = modelo;
    this.usuario = usuario; 
    
    form1.botonNuevoAsiento.addActionListener(this);
    form1.botonRetomarAsiento.addActionListener(this);
    form1.botonListar.addActionListener(this);
    form1.botonIniciarCarga.addActionListener(this);
    //form1.botonNuevoRenglon.addActionListener(this);
    form1.botonModificarRenglon.addActionListener(this);
    form1.botonEliminarRenglon.addActionListener(this);
    form1.botonOK.addActionListener(this);
    form1.botonVolverAlInicio.addActionListener(this);
    form1.botonGrabar.addActionListener(this);
    form1.botonVolverAtras.addActionListener(this);
    form1.botonCancelar.addActionListener(this);
    form1.botonSalir.addActionListener(this);
    
    form1.campoNroCuenta.addKeyListener(this);
    form1.campoSeccion.addKeyListener(this);
    form1.campoSucursal.addKeyListener(this);
    form1.campoImporte.addKeyListener(this);
    form1.campoRetomarAsiento.addKeyListener(this);
    
    form1.campoNroCuenta.addFocusListener(this);
    form1.campoSeccion.addFocusListener(this);
    form1.campoSucursal.addFocusListener(this);
    form1.fechaContable.addFocusListener(this);
    
    
    
    
    
    }
    
     public void iniciar(){
        form1.setTitle("Empresa: "+Conexion.getNombreBase()+" - Usuario: " + usuario.getNombre() + " " + usuario.getApellido()+"- Menu de Asientos" );
        form1.setLocationRelativeTo(null);
        form1.setVisible(true);

        form1.panelRenglones.setVisible(false);
        form1.fechaContable.setEnabled(false);
        form1.campoAsiento.setEnabled(false);
        form1.checkInflacion.setEnabled(false);
        form1.opcionApertura.setEnabled(false);
        form1.opcionCierre.setEnabled(false);
        form1.opcionNormal.setEnabled(false);
        form1.botonIniciarCarga.setEnabled(false);
        form1.botonVolverAtras.setEnabled(false);
        form1.panelTipoAsiento.setVisible(false);

        Calendar c2 = new GregorianCalendar();
        form1.fechaOperacion.setCalendar(c2);
        form1.fechaVencimiento.setCalendar(c2);
        
        //para no permitir que escriban en el campo de las fechas
        ((JTextField) form1.fechaContable.getDateEditor()).setEditable(false);
        ((JTextField) form1.fechaOperacion.getDateEditor()).setEditable(false);
        ((JTextField) form1.fechaVencimiento.getDateEditor()).setEditable(false);
         
    }
     
     public void limpiarCajasRenglon(){
         form1.campoNroCuenta.setText("");
         form1.campoImporte.setText("");
         form1.campoNombreCuenta.setText("");
         
     }
     
     public void limpiarTodasCajasRenglon(){
         form1.campoNroCuenta.setText("");
         form1.campoSucursal.setText("");
         form1.campoSeccion.setText("");
         form1.campoComprobante.setText("");
         form1.campoLeyenda.setText("");
         form1.campoImporte.setText("");
         form1.campoNombreCuenta.setText("");
         
     }
    
    
    
    
    @Override
    public void actionPerformed(ActionEvent e) {
        
        if (e.getSource() == form1.botonNuevoAsiento) {
            form1.botonRetomarAsiento.setEnabled(false);
            int numAsiento = modelo.cantidadAsientos()+1;
            form1.campoAsiento.setText(String.valueOf(numAsiento));
            
            Calendar c2 = new GregorianCalendar();
            form1.fechaContable.setCalendar(c2);
             
            form1.fechaContable.setEnabled(true);
            form1.checkInflacion.setEnabled(true);
            form1.opcionApertura.setEnabled(true);
            form1.opcionCierre.setEnabled(true);
            form1.opcionNormal.setEnabled(true);
            form1.botonIniciarCarga.setEnabled(true);
            form1.botonVolverAtras.setEnabled(true);
            
            limpiarTodasCajasRenglon();
            form1.panelRenglones.setVisible(false);
            form1.panelTipoAsiento.setVisible(false);
           
        }
        
        if (e.getSource() == form1.botonRetomarAsiento) {
            //controlo que el campo no este vacio
           if ("".equals(form1.campoRetomarAsiento.getText())) {
              JOptionPane.showMessageDialog(null, "Por favor indique el nro de asiento a retomar");   
           }
           else{
               //controlo que la cuenta exista
               if (modelo.buscarAsiento(Integer.parseInt(form1.campoRetomarAsiento.getText()))) {
                   
                    int idAsiento = Integer.parseInt(form1.campoRetomarAsiento.getText());
                    Asiento asiento =new Asiento();
                    asiento = modelo.getAsiento(idAsiento); 
                    
                    //controlo que no sea un asiento ya registrado, es decir pasado al mayor
                    if (asiento.getRegistrado() == 1) {
                        JOptionPane.showMessageDialog(null, "El asiento ya se paso al mayor, no se puede retomar"); 
                        form1.panelRenglones.setVisible(false);
                    }
                    else{
                        //TRAIGO TODOS LOS CAMPOS DEL ASIENTO
                       form1.botonNuevoAsiento.setEnabled(false);

                       form1.fechaContable.setEnabled(true);
                       form1.checkInflacion.setEnabled(true);
                       form1.panelTipoAsiento.setVisible(true);
                       form1.opcionApertura.setEnabled(true);
                       form1.opcionCierre.setEnabled(true);
                       form1.opcionNormal.setEnabled(true);
                       //form1.botonIniciarCarga.setEnabled(true);

                       form1.fechaContable.setDate(asiento.getFechacontable());
                       form1.campoAsiento.setText(String.valueOf(asiento.getIdasiento()));
                       if (asiento.getInflacion() == 1) {
                           form1.checkInflacion.setSelected(true);
                       }
                       if (asiento.getTipoasiento() == 1) {
                           form1.opcionApertura.setSelected(true);
                       }
                       if (asiento.getTipoasiento() == 5) {
                           form1.opcionNormal.setSelected(true);
                       }
                       if (asiento.getTipoasiento() == 9) {
                           form1.opcionCierre.setSelected(true);
                       }
                    
                    
                        //TRAIGO TODOS LOS RENGLONES DEL ASIENTO
                        form1.panelRenglones.setVisible(true);
                        int numRenglon = modelo.cantidadRenglonesdelAsiento(Integer.parseInt(form1.campoAsiento.getText()));
                        form1.campoNroRenglon.setText(String.valueOf(numRenglon+1));

                        DefaultTableModel modeloTabla = modelo.llenarTablaRenglones(idAsiento);
                        form1.tablaRenglones.setModel(modeloTabla);

                        //MUESTRO EL DEBE, HABER Y DIFERENCIA
                        String totalDebe = modelo.getTotalDebe(idAsiento);  

                        form1.campoTotalDebe.setText(totalDebe);

                        String totalHaber = modelo.getTotalHaber(idAsiento);

                        form1.campoTotalHaber.setText(totalHaber);  

                        BigDecimal numero1 = new BigDecimal(form1.campoTotalDebe.getText());
                        BigDecimal numero2 = new BigDecimal(form1.campoTotalHaber.getText());
                        BigDecimal diferencia = numero1.subtract(numero2);
                        form1.campoDiferencia.setText(String.valueOf(diferencia));

                        }
                    
                    
               }
               else{
                  JOptionPane.showMessageDialog(null, "El nro de asiento no existe");  
                  form1.panelRenglones.setVisible(false);
               }

               
               
           } //cierre else inicial
            
        }
        
        
        
        
        
        
        if (e.getSource() == form1.botonIniciarCarga) {
                //CONTROLO LA FECHA CONTABLE CON LA FECHA DE INICIO Y FIN DE EJERCICIO
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                Date fechaCont = null;
                Date fechaInicio = null;
                Date fechaFin = null;
                
                String cadena = sdf.format(form1.fechaContable.getDate());
                try {
                    fechaCont = sdf.parse(cadena);
                } catch (ParseException ex) {
                    Logger.getLogger(ControladorAsientos.class.getName()).log(Level.SEVERE, null, ex);
                }
 
                Date inicio = modelo.getFechaInicioEjercicio();
                String fechainicio = sdf.format(inicio);
                try {
                    fechaInicio = sdf.parse(fechainicio);

                     } catch (ParseException ex) {
                    Logger.getLogger(ControladorAsientos.class.getName()).log(Level.SEVERE, null, ex);
                }
                
                Date fin = modelo.getFechaFinEjercicio();
                String fechafin = sdf.format(fin);
                try {
                    fechaFin = sdf.parse(fechafin);

                     } catch (ParseException ex) {
                    Logger.getLogger(ControladorAsientos.class.getName()).log(Level.SEVERE, null, ex);
                }
                
                //SI LA FECHA CONTABLE ES MENOR A LA FECHA DE INICIO INDICA ERROR
                if ((fechaCont.compareTo(fechaInicio) < 0) || (fechaCont.compareTo(fechaFin) > 0)) {
                    JOptionPane.showMessageDialog(null, "Por favor chequee la fecha, considere el ejercicio contable");
                }
                else{//PUEDO CARGAR UN REGISTRO. Y SI LAS FECHAS SON IGUALES PERMITO ELEGIR EL TIPO DE ASIENTO
                    if ((fechaCont.compareTo(fechaInicio) == 0) || (fechaCont.compareTo(fechaFin) == 0)) {
                        //System.out.println("coinciden las fechas");
                        form1.panelTipoAsiento.setVisible(true);
                        form1.opcionNormal.setSelected(true);
                    }
                   
                form1.botonIniciarCarga.setEnabled(false);
                form1.botonVolverAtras.setEnabled(false);
                //creo un asiento
                Asiento asiento = new Asiento();

                //tomo el id de asiento
                asiento.setIdasiento(Integer.parseInt(form1.campoAsiento.getText()));

                //tomo la fecha
                Date date1 = form1.fechaContable.getDate();
                long d1 = date1.getTime();
                java.sql.Date fechaContable = new java.sql.Date(d1);
            
                asiento.setFechacontable(fechaContable);

                //tomo el tipo de asiento por defecto es el normal, sino le habilito el panel para que elija el tipo de asiento
                asiento.setTipoasiento(5);
                
                //cuando esta todo bien cargado 
                asiento.setOkcarga(0);

                //registrado va de entrada en FALSE
                asiento.setRegistrado(0);

                //tomo si es ajustable o no por inflacion
                if (form1.checkInflacion.isSelected()) {
                asiento.setInflacion(1);
                }
                else{
                asiento.setInflacion(0);    
                }
                //cargo el asiento en la BBDD y muestro el panel de los renglones
                if (modelo.generarAsiento(asiento)) {
                    form1.panelRenglones.setVisible(true);
                    int numRenglon = modelo.cantidadRenglonesdelAsiento(Integer.parseInt(form1.campoAsiento.getText()));
                    form1.campoNroRenglon.setText(String.valueOf(numRenglon+1));
                    DefaultTableModel modelotabla = new DefaultTableModel();
                    form1.tablaRenglones.setModel(modelotabla);
                }  
              
                
                }

            
         
            
        }
        
        
        
        if (e.getSource() == form1.botonModificarRenglon) {
            int fila = form1.tablaRenglones.getSelectedRow();
            
            if (fila == -1) {
              JOptionPane.showMessageDialog(null, "Seleccione el renglon a modificar");  
            }
            else{
                form1.botonCancelar.setEnabled(true);
                //form1.botonNuevoRenglon.setEnabled(false);
                form1.botonEliminarRenglon.setEnabled(false);
                
                int idRenglon = Integer.parseInt(form1.tablaRenglones.getValueAt(fila, 0).toString());
                int nroAsiento = Integer.parseInt(form1.campoAsiento.getText());
                Renglon renglon = new Renglon();  
                renglon = modelo.getRenglon(nroAsiento, idRenglon);
                
                form1.campoNroRenglon.setText(String.valueOf(renglon.getIdRenglon()));
                form1.campoNroCuenta.setText(String.valueOf(renglon.getNroCuenta()));
                form1.fechaVencimiento.setDate(renglon.getFechaVencimiento());
                form1.fechaOperacion.setDate(renglon.getFechaOperacion());
                form1.campoSucursal.setText(String.valueOf(renglon.getSucursal()));
                form1.campoSeccion.setText(String.valueOf(renglon.getSeccion()));
                form1.campoComprobante.setText(renglon.getComprobante());
                form1.campoLeyenda.setText(renglon.getLeyenda());
              
                if(renglon.getDebeHaber() == 0) {
                   form1.opcionDebe.setSelected(true);
                   form1.opcionHaber.setSelected(false);
                }
                if(renglon.getDebeHaber() == 1) {
                   form1.opcionHaber.setSelected(true);
                   form1.opcionDebe.setSelected(false);
                }
                
                  BigDecimal importe = new BigDecimal(renglon.getImporte());
                  form1.campoImporte.setText(String.valueOf(renglon.getImporte()));
                  form1.campoNombreCuenta.setText(modelo.getCuentaPlanCuentas(renglon.getNroCuenta()));
                
                        
                     
                        
                        
            }
        }
        
        
        
        
        
        
        
        if (e.getSource() == form1.botonEliminarRenglon) {
            
         int fila = form1.tablaRenglones.getSelectedRow();
            
         if (fila == -1) {
            JOptionPane.showMessageDialog(null, "Seleccione el renglon a eliminar");  
         }
         else{
            int idRenglon = Integer.parseInt(form1.tablaRenglones.getValueAt(fila, 0).toString());
            int nroAsiento = Integer.parseInt(form1.campoAsiento.getText());
            
            //le pregunto si realmente quiere eliminar el renglon
            int chequeo = JOptionPane.showConfirmDialog(null, "Por favor confirme si quiere eliminar el renglon.");
            if (chequeo == JOptionPane.YES_OPTION) {
                if (modelo.eliminarRenglon(nroAsiento, idRenglon)) {
                 //JOptionPane.showMessageDialog(null, "Renglon eliminado con exito"); 
                 
                   DefaultTableModel modeloTabla = modelo.llenarTablaRenglones(Integer.parseInt(form1.campoAsiento.getText()));
                    form1.tablaRenglones.setModel(modeloTabla);

                    String totalDebe = modelo.getTotalDebe(Integer.parseInt(form1.campoAsiento.getText()));  

                    form1.campoTotalDebe.setText(totalDebe);

                    String totalHaber = modelo.getTotalHaber(Integer.parseInt(form1.campoAsiento.getText()));
     
                    form1.campoTotalHaber.setText(totalHaber);  

                    BigDecimal numero1 = new BigDecimal(form1.campoTotalDebe.getText());
                    BigDecimal numero2 = new BigDecimal(form1.campoTotalHaber.getText());
                    BigDecimal diferencia = numero1.subtract(numero2);
                    form1.campoDiferencia.setText(String.valueOf(diferencia));

                    //limpio los textfield
                    limpiarTodasCajasRenglon();

                    //sumo 1 al nro de renglones
                     int numRenglon = modelo.cantidadRenglonesdelAsiento(Integer.parseInt(form1.campoAsiento.getText()));
                     form1.campoNroRenglon.setText(String.valueOf(numRenglon+1));
                 
                 
                }   
            }
           
            
            
         }
            
            
        }
        
        
        
        if (e.getSource() == form1.botonOK) {
            
            
            if ("".equals(form1.campoNroCuenta.getText()) || "".equals(form1.campoSucursal.getText()) || "".equals(form1.campoSeccion.getText()) || "".equals(form1.campoComprobante.getText()) || "".equals(form1.campoImporte.getText()) || "".equals(form1.campoLeyenda.getText())) {
             JOptionPane.showMessageDialog(null, "Por favor complete todos los campos");
            }
            else{
                Renglon renglon = new Renglon();
       
                renglon.setIdRenglon(Integer.parseInt(form1.campoNroRenglon.getText()));
                renglon.setNroAsiento(Integer.parseInt(form1.campoAsiento.getText()));
                renglon.setNroCuenta(Integer.parseInt(form1.campoNroCuenta.getText()));

                Date date1 = form1.fechaVencimiento.getDate();
                long d1 = date1.getTime();
                java.sql.Date fechaVencimiento = new java.sql.Date(d1);

                Date date2 = form1.fechaVencimiento.getDate();
                long d2 = date2.getTime();
                java.sql.Date fechaOperacion= new java.sql.Date(d2);

                renglon.setFechaVencimiento(fechaVencimiento);
                renglon.setFechaOperacion(fechaOperacion);
                renglon.setComprobante(form1.campoComprobante.getText());
                renglon.setLeyenda(form1.campoLeyenda.getText());
                
                if (form1.opcionDebe.isSelected()) {
                    renglon.setDebeHaber(0);
                }
                if (form1.opcionHaber.isSelected()) {
                    renglon.setDebeHaber(1);
                }
                renglon.setImporte(Double.parseDouble(form1.campoImporte.getText()));
                renglon.setSeccion(Integer.parseInt(form1.campoSeccion.getText()));
                renglon.setSucursal(Integer.parseInt(form1.campoSucursal.getText()));
                
                //SI EL BOTON NUEVO ESTA DESACTIVADO ESTOY MODIFICANDO UN RENGLON SINO LO ESTOY INSERTANDO
                
                if (!form1.botonEliminarRenglon.isEnabled()) {
                    //actualizo el renglon en la bbdd, lo muestro en la tabla y actualizo el campo DEBE, HABER y DIFERENCIA
                    if (modelo.actualizarRenglon(renglon)) {
                        DefaultTableModel modeloTabla = modelo.llenarTablaRenglones(Integer.parseInt(form1.campoAsiento.getText()));
                        form1.tablaRenglones.setModel(modeloTabla);

                        String totalDebe = modelo.getTotalDebe(Integer.parseInt(form1.campoAsiento.getText()));  

                        form1.campoTotalDebe.setText(totalDebe);

                        String totalHaber = modelo.getTotalHaber(Integer.parseInt(form1.campoAsiento.getText()));

                        form1.campoTotalHaber.setText(totalHaber);  

                        BigDecimal numero1 = new BigDecimal(form1.campoTotalDebe.getText());
                        BigDecimal numero2 = new BigDecimal(form1.campoTotalHaber.getText());
                        BigDecimal diferencia = numero1.subtract(numero2);
                        form1.campoDiferencia.setText(String.valueOf(diferencia));

                        //limpio los textfield
                        limpiarCajasRenglon();

                        //sumo 1 al nro de renglones
                         //form1.botonNuevoRenglon.setEnabled(true);
                         form1.botonEliminarRenglon.setEnabled(true);
                         form1.botonCancelar.setEnabled(true);
                         limpiarTodasCajasRenglon();
                         int numRenglon = modelo.cantidadRenglonesdelAsiento(Integer.parseInt(form1.campoAsiento.getText()));
                         form1.campoNroRenglon.setText(String.valueOf(numRenglon+1));
                         
                    }
                    
                    
                    
                }
                else{     
                 //guardo el renglon en la bbdd, lo muestro en la tabla y actualizo el campo DEBE, HABER y DIFERENCIA
            
                    if (modelo.ingresarRenglon(renglon)) {
                        DefaultTableModel modeloTabla = modelo.llenarTablaRenglones(Integer.parseInt(form1.campoAsiento.getText()));
                        form1.tablaRenglones.setModel(modeloTabla);

                        String totalDebe = modelo.getTotalDebe(Integer.parseInt(form1.campoAsiento.getText()));  

                        form1.campoTotalDebe.setText(totalDebe);

                        String totalHaber = modelo.getTotalHaber(Integer.parseInt(form1.campoAsiento.getText()));

                        form1.campoTotalHaber.setText(totalHaber);  

                        BigDecimal numero1 = new BigDecimal(form1.campoTotalDebe.getText());
                        BigDecimal numero2 = new BigDecimal(form1.campoTotalHaber.getText());
                        BigDecimal diferencia = numero1.subtract(numero2);
                        form1.campoDiferencia.setText(String.valueOf(diferencia));

                        //limpio los textfield
                        limpiarCajasRenglon();

                        //sumo 1 al nro de renglones
                         int numRenglon = modelo.cantidadRenglonesdelAsiento(Integer.parseInt(form1.campoAsiento.getText()));
                         form1.campoNroRenglon.setText(String.valueOf(numRenglon+1));
                
                
                
                    }   
                    
                }

 
            
            } //cierre control campos vacios
           

            
        } //cierre boton ok
        
        
        
        
        
        if (e.getSource() == form1.botonGrabar) {
            if ("0.00".equals(form1.campoDiferencia.getText())) {
                //le pongo okcarga en true al asiento
                Asiento asiento = new Asiento();
                
                //tomo el id de asiento
                asiento.setIdasiento(Integer.parseInt(form1.campoAsiento.getText()));
            
                //tomo la fecha
                Date date1 = form1.fechaContable.getDate();
                long d1 = date1.getTime();
                java.sql.Date fechaContable = new java.sql.Date(d1);

                asiento.setFechacontable(fechaContable);

                //tomo el tipo de asiento

                if (form1.opcionApertura.isSelected()) {
                asiento.setTipoasiento(1);
                }
                if (form1.opcionNormal.isSelected()) {
                asiento.setTipoasiento(5);    
                }
                if(form1.opcionCierre.isSelected()) {
                asiento.setTipoasiento(9);    
                }

                //cuando esta todo bien cargado 
                asiento.setOkcarga(1);

                //registrado va de entrada en FALSE
                asiento.setRegistrado(0);

                //tomo si es ajustable o no por inflacion
                if (form1.checkInflacion.isSelected()) {
                asiento.setInflacion(1);
                }
                else{
                asiento.setInflacion(0);    
                }

                
                
                if (modelo.actualizarAsiento(asiento)) {
                  JOptionPane.showMessageDialog(null, "Registro cargado ok.");
                  limpiarTodasCajasRenglon();
                  form1.panelRenglones.setVisible(false);
                  form1.panelTipoAsiento.setVisible(false);
                  form1.botonNuevoAsiento.setEnabled(true);
                  form1.botonRetomarAsiento.setEnabled(true);
                  form1.fechaContable.setEnabled(false);
                  form1.checkInflacion.setEnabled(false);
                  form1.opcionApertura.setEnabled(false);
                  form1.opcionCierre.setEnabled(false);
                  form1.opcionNormal.setEnabled(false);
                  form1.botonIniciarCarga.setEnabled(false);
                  form1.botonVolverAtras.setEnabled(false);
                  
                }
                
                
            }
            else{
                JOptionPane.showMessageDialog(null, "Hay diferencia de saldos. Por favor corregir.");
            }
        }
        
        
        
        
        
        
        
        if (e.getSource() == form1.botonVolverAlInicio) {
             form1.panelRenglones.setVisible(false);
             form1.panelTipoAsiento.setVisible(false);
             form1.botonNuevoAsiento.setEnabled(true);
             form1.botonRetomarAsiento.setEnabled(true);
             
             form1.fechaContable.setEnabled(false);
             form1.checkInflacion.setEnabled(false);
             form1.opcionApertura.setEnabled(false);
             form1.opcionCierre.setEnabled(false);
             form1.opcionNormal.setEnabled(false);
             form1.botonIniciarCarga.setEnabled(false);
             form1.botonVolverAtras.setEnabled(false);
             
        
        }
        
        
        
        
        
        
        
        if (e.getSource() == form1.botonVolverAtras) {
              form1.panelRenglones.setVisible(false);
              form1.panelTipoAsiento.setVisible(false);
                form1.fechaContable.setEnabled(false);
                form1.campoAsiento.setEnabled(false);
                form1.checkInflacion.setEnabled(false);
                form1.opcionApertura.setEnabled(false);
                form1.opcionCierre.setEnabled(false);
                form1.opcionNormal.setEnabled(false);
                form1.botonIniciarCarga.setEnabled(false);
                form1.botonVolverAtras.setEnabled(false);

              Calendar c2 = new GregorianCalendar();
              form1.fechaOperacion.setCalendar(c2);
              form1.fechaVencimiento.setCalendar(c2);
              form1.fechaContable.setCalendar(c2);
             form1.botonNuevoAsiento.setEnabled(true);
             form1.botonRetomarAsiento.setEnabled(true);
        }
        
        
        if (e.getSource() == form1.botonCancelar) {
        //   form1.botonNuevoRenglon.setEnabled(true);
           form1.botonEliminarRenglon.setEnabled(true);
           form1.botonCancelar.setEnabled(true);
           limpiarTodasCajasRenglon();
           int numRenglon = modelo.cantidadRenglonesdelAsiento(Integer.parseInt(form1.campoAsiento.getText()));
           form1.campoNroRenglon.setText(String.valueOf(numRenglon+1));
        }
        
        if (e.getSource() == form1.botonSalir) {
             MenuInicial menu = new MenuInicial();
            ConsultasUsuario modeloUsuario = new ConsultasUsuario();
            ControladorMenuInicial controlador = new ControladorMenuInicial(menu,usuario,modeloUsuario);
            controlador.iniciar();
            form1.dispose();
        }
            
        
        
        
        
        
        
        
    }

    @Override
    public void keyTyped(KeyEvent e) {
     
     if (e.getSource() == form1.campoNroCuenta) {
       char c = e.getKeyChar();
        
        if (((c < '0') || (c > '9')) && (c!= KeyEvent.VK_BACK_SPACE)) {
             e.consume();
            }
        
      }   
        
        
     if (e.getSource() == form1.campoSucursal) {
       char c = e.getKeyChar();
        
        if (((c < '0') || (c > '9')) && (c!= KeyEvent.VK_BACK_SPACE)) {
             e.consume();
            }
        
      }
     
     
      if (e.getSource() == form1.campoSeccion){
       char c = e.getKeyChar();
        
        if (((c < '0') || (c > '9')) && (c!= KeyEvent.VK_BACK_SPACE)) {
             e.consume();
            }
        
      }
      
      if (e.getSource() == form1.campoRetomarAsiento){
       char c = e.getKeyChar();
        
        if (((c < '0') || (c > '9')) && (c!= KeyEvent.VK_BACK_SPACE)) {
             e.consume();
            }
        
      } 
      
       if (e.getSource() == form1.campoDesde){
       char c = e.getKeyChar();
        
        if (((c < '0') || (c > '9')) && (c!= KeyEvent.VK_BACK_SPACE)) {
             e.consume();
            }
        
      } 
       
       if (e.getSource() == form1.campoHasta){
       char c = e.getKeyChar();
        
        if (((c < '0') || (c > '9')) && (c!= KeyEvent.VK_BACK_SPACE)) {
             e.consume();
            }
        
      }  
      
      
      
       if (e.getSource() == form1.campoImporte){
       char c = e.getKeyChar();
        
        if (((c < '0') || (c > '9')) && (c!= KeyEvent.VK_BACK_SPACE) && (c!=KeyEvent.VK_PERIOD) && (c!=KeyEvent.VK_ENTER)) {
             e.consume();
            }
        
      } 
     
     
     
    }
    
   
    
    
    
    
    
    
    

    @Override
    public void keyPressed(KeyEvent e) {
        
        if (e.getSource() == form1.campoNroCuenta) {
            char c = e.getKeyChar();
            
            if (e.getKeyCode() == KeyEvent.VK_F1) {
                Desplegable desplegable = new Desplegable(form1,true);
                ConsultasDesplegable modelo = new ConsultasDesplegable();

                ControladorDesplegable controlador = new ControladorDesplegable(desplegable,modelo,6);

                controlador.iniciar();    
            }
            
        }
        
        if (e.getSource() == form1.campoSeccion) {
            char c = e.getKeyChar();
            
            if (e.getKeyCode() == KeyEvent.VK_F1) {
                Desplegable desplegable = new Desplegable(form1,true);
                ConsultasDesplegable modelo = new ConsultasDesplegable();

                ControladorDesplegable controlador = new ControladorDesplegable(desplegable,modelo,7);

                controlador.iniciar();    
            }
            
        }
        
        if (e.getSource() == form1.campoSucursal) {
            char c = e.getKeyChar();
            
            if (e.getKeyCode() == KeyEvent.VK_F1) {
                Desplegable desplegable = new Desplegable(form1,true);
                ConsultasDesplegable modelo = new ConsultasDesplegable();

                ControladorDesplegable controlador = new ControladorDesplegable(desplegable,modelo,8);

                controlador.iniciar();    
            }
            
        }
        
        
        
        
      
    }

    @Override
    public void keyReleased(KeyEvent e) {
       
    }

    @Override
    public void focusGained(FocusEvent e) {
        
    }

    @Override
    public void focusLost(FocusEvent e) {
        
        if (e.getSource() == form1.campoNroCuenta) {
            
            if ("".equals(form1.campoNroCuenta.getText())) {
              Desplegable desplegable = new Desplegable(form1,true);
              ConsultasDesplegable modelo = new ConsultasDesplegable();

              ControladorDesplegable controlador = new ControladorDesplegable(desplegable,modelo,6);

              controlador.iniciar();      
            }
            else{
                if (modelo.buscarCuenta(Integer.parseInt(form1.campoNroCuenta.getText()))) {
                //cargo el nombre en el campo nombre cuenta
                String nombreCuenta = modelo.buscarNombreCuenta(Integer.parseInt(form1.campoNroCuenta.getText()));
                form1.campoNombreCuenta.setText(nombreCuenta);
                }
                else{
                 Desplegable desplegable = new Desplegable(form1,true);
                 ConsultasDesplegable modelo = new ConsultasDesplegable();

                 ControladorDesplegable controlador = new ControladorDesplegable(desplegable,modelo,6);

                 controlador.iniciar();    
                }
            }
                  
            
        }
        
        
        
  

       
    }
    
}
