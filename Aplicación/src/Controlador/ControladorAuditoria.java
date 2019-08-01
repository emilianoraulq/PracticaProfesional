
package Controlador;

import Modelo.Cliente;
import Modelo.Conexion;
import Modelo.ConsultasAuditoria;
import Modelo.ConsultasUsuario;
import Modelo.Provincia;
import Modelo.Usuario;
import Vista.FormAuditoria;
import Vista.MenuInicial;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import javax.swing.JOptionPane;

import javax.swing.table.DefaultTableModel;


public class ControladorAuditoria implements ActionListener, KeyListener {
    private FormAuditoria form1;
    private ConsultasAuditoria modelo;
    private ConsultasUsuario modeloUsuario;
    private Usuario usuario;
    private Provincia provincia;
    private Cliente cliente;
    
    
    public ControladorAuditoria(FormAuditoria form1, ConsultasAuditoria modelo, ConsultasUsuario modeloUsuario, Usuario usuario,Provincia provincia, Cliente cliente){
    this.form1 = form1;
    this.modelo = modelo;
    this.usuario = usuario;
    this.provincia = provincia;
    this.cliente = cliente;
    this.modeloUsuario = modeloUsuario;
    
    form1.botonListar.addActionListener(this);
    form1.campoBusqueda.addKeyListener(this);
    form1.campoFiltro.addKeyListener(this);
    form1.campoFiltro.addActionListener(this);
    form1.botonFiltrar.addActionListener(this);
    form1.botonExportarPDF.addActionListener(this);
    form1.botonExportarExcel.addActionListener(this);
    form1.botonSalir.addActionListener(this);
    form1.comboTablas.addActionListener(this);
    
    }
    
    
    public void iniciar(){
    form1.setTitle("Empresa: "+Conexion.getNombreBase()+" - Usuario: " + usuario.getNombre() + " " + usuario.getApellido()+"- Menú de auditoría" );
    form1.setLocationRelativeTo(null);
    form1.setVisible(true);
    form1.campoBusqueda.requestFocus();
    
    //la fecha desde y hasta puestas por defecto: 
    //desde: el primero del mes en curso
    //hasta: el dia actual
    int mes = Calendar.MONTH+2;

    Calendar c1 = new GregorianCalendar(2019,mes,25);
    Calendar c2 = new GregorianCalendar();
    form1.fechaHasta.setCalendar(c2);
    form1.fechaDesde.setCalendar(c1);
    
    }
    
    public void limpiarCajas(){
    form1.campoBusqueda.setText("");
    form1.campoFiltro.setText("");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        
        if (e.getSource() == form1.comboTablas) {
          
            if (form1.comboTablas.getSelectedIndex() == 2) {
            form1.panelFiltro.setVisible(false);
            }
            if (form1.comboTablas.getSelectedIndex() == 1) {
            form1.panelFiltro.setVisible(true);
            }
            if (form1.comboTablas.getSelectedIndex() == 0) {
            form1.panelFiltro.setVisible(true);
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
        
        if (e.getSource() == form1.botonListar) {
            //LISTO LAS PROVINCIAS
            if (form1.comboTablas.getSelectedIndex() == 0) {
            
             if (!"".equals(form1.campoBusqueda.getText())) {
             provincia.setId(Integer.parseInt(form1.campoBusqueda.getText()));
             }    
            
            Date date1 = form1.fechaDesde.getDate();
            Date date2 = form1.fechaHasta.getDate();
            
            long d1 = date1.getTime();
            long d2 = date2.getTime();
            
            java.sql.Date fechaDesde = new java.sql.Date(d1);
            java.sql.Date fechaHasta = new java.sql.Date(d2);

            DefaultTableModel modeloTabla = new DefaultTableModel();
            
            if (!"".equals(form1.campoFiltro.getText())) {
             usuario.setId(Integer.parseInt(form1.campoFiltro.getText()));
            }
           
            modeloTabla= modelo.consultarProvincia(provincia, usuario,fechaDesde,fechaHasta);
            form1.tablaMovimientos.setModel(modeloTabla);
            //vuelvo a poner el ID en cero para que si borra el campo busqueda y le da click a listar le salgan todas
            provincia.setId(0);
            }
            
            //LISTO LOS CLIENTES
             if (form1.comboTablas.getSelectedIndex() == 1) {

            if (!"".equals(form1.campoBusqueda.getText())) {
            cliente.setId(Integer.parseInt(form1.campoBusqueda.getText()));
            }    
           
            Date date1 = form1.fechaDesde.getDate();
            Date date2 = form1.fechaHasta.getDate();
            
            long d1 = date1.getTime();
            long d2 = date2.getTime();
            
            java.sql.Date fechaDesde = new java.sql.Date(d1);
            java.sql.Date fechaHasta = new java.sql.Date(d2);

            DefaultTableModel modeloTabla = new DefaultTableModel();
            
            if (!"".equals(form1.campoFiltro.getText())) {
            usuario.setId(Integer.parseInt(form1.campoFiltro.getText()));
            }
           
            modeloTabla= modelo.consultarCliente(cliente, usuario,fechaDesde,fechaHasta);
            form1.tablaMovimientos.setModel(modeloTabla);
             //vuelvo a poner el ID en cero para que si borra el campo busqueda y le da click a listar le salgan todos
            cliente.setId(0);
            }
             
             //LISTO LOS USUARIOS
             if (form1.comboTablas.getSelectedIndex() == 2) {
                 Usuario usuarioAux = new Usuario();
            
             if (!"".equals(form1.campoBusqueda.getText())) {
             usuarioAux.setId(Integer.parseInt(form1.campoBusqueda.getText()));
             }    
            
            Date date1 = form1.fechaDesde.getDate();
            Date date2 = form1.fechaHasta.getDate();
            
            long d1 = date1.getTime();
            long d2 = date2.getTime();
            
            java.sql.Date fechaDesde = new java.sql.Date(d1);
            java.sql.Date fechaHasta = new java.sql.Date(d2);

            DefaultTableModel modeloTabla = new DefaultTableModel();
            
           // if (!"".equals(form1.campoFiltro.getText())) {
           //  usuario.setId(Integer.parseInt(form1.campoFiltro.getText()));
           // }
           
            modeloTabla= modelo.consultarUsuario(usuarioAux,fechaDesde,fechaHasta);
            form1.tablaMovimientos.setModel(modeloTabla);
            //vuelvo a poner el ID en cero para que si borra el campo busqueda y le da click a listar le salgan todas
            usuarioAux.setId(0);
            } 
             
             
            
        }
        
        if (e.getSource() == form1.botonFiltrar) {
            
                if ("".equals(form1.campoFiltro.getText())) {
                JOptionPane.showMessageDialog(null, "Por favor ingrese ID Usuario");
                 form1.campoFiltro.requestFocus();
                }
                else{
                    int idUsuario = Integer.parseInt(form1.campoFiltro.getText());
                    usuario.setId(idUsuario);

                    //filtro una provincia
                    if (form1.comboTablas.getSelectedIndex() == 0) {

                        if (!"".equals(form1.campoBusqueda.getText())) {
                            int idProvincia = Integer.parseInt(form1.campoBusqueda.getText());
                            provincia.setId(idProvincia);
                        }

                        Date date1 = form1.fechaDesde.getDate();
                        Date date2 = form1.fechaHasta.getDate();

                        long d1 = date1.getTime();
                        long d2 = date2.getTime();

                        java.sql.Date fechaDesde = new java.sql.Date(d1);
                        java.sql.Date fechaHasta = new java.sql.Date(d2);

                        DefaultTableModel modeloTabla = new DefaultTableModel();

                        modeloTabla = modelo.filtrarProvincia(provincia, usuario, fechaDesde, fechaHasta);
                        form1.tablaMovimientos.setModel(modeloTabla);
                    }

                    //filtro un cliente
                    if (form1.comboTablas.getSelectedIndex() == 1) {
                        
                        if (!"".equals(form1.campoBusqueda.getText())) {
                        int idCliente = Integer.parseInt(form1.campoBusqueda.getText());
                        cliente.setId(idCliente);
                         }

                        Date date1 = form1.fechaDesde.getDate();
                        Date date2 = form1.fechaHasta.getDate();

                        long d1 = date1.getTime();
                        long d2 = date2.getTime();

                        java.sql.Date fechaDesde = new java.sql.Date(d1);
                        java.sql.Date fechaHasta = new java.sql.Date(d2);

                        DefaultTableModel modeloTabla = new DefaultTableModel();

                        modeloTabla = modelo.filtrarCliente(cliente, usuario, fechaDesde, fechaHasta);
                        form1.tablaMovimientos.setModel(modeloTabla);
            }
                }
        
    }
        
        
        if (e.getSource() == form1.botonExportarPDF) {
            //hay 3 posibilidades: que haya completado el campo de id y el de filtro, que haya completado solo el de id y que no haya completado ninguno de los dos
            //PROVINCIAS
            if (form1.comboTablas.getSelectedIndex() == 0) {
                
                if (!"".equals(form1.campoBusqueda.getText()) && (!"".equals(form1.campoFiltro.getText()))) {
                    Date date1 = form1.fechaDesde.getDate();
                    Date date2 = form1.fechaHasta.getDate();

                    long d1 = date1.getTime();
                    long d2 = date2.getTime();

                    //java.sql.Date fechaDesde = new java.sql.Date(d1);
                    // java.sql.Date fechaHasta = new java.sql.Date(d2);
                    java.sql.Timestamp fechaDesde = new java.sql.Timestamp(d1);
                    java.sql.Timestamp fechaHasta = new java.sql.Timestamp(d2);

                    provincia.setId(Integer.parseInt(form1.campoBusqueda.getText()));
                    usuario.setId(Integer.parseInt(form1.campoFiltro.getText()));

                    modelo.ReporteAuditoriaProvinciasPorProvinciaYUsuario(provincia, usuario, fechaDesde, fechaHasta);
                }
                
                if (!"".equals(form1.campoBusqueda.getText()) && ("".equals(form1.campoFiltro.getText()))) {
                    Date date1 = form1.fechaDesde.getDate();
                    Date date2 = form1.fechaHasta.getDate();

                    long d1 = date1.getTime();
                    long d2 = date2.getTime();

                    //java.sql.Date fechaDesde = new java.sql.Date(d1);
                    // java.sql.Date fechaHasta = new java.sql.Date(d2);
                    java.sql.Timestamp fechaDesde = new java.sql.Timestamp(d1);
                    java.sql.Timestamp fechaHasta = new java.sql.Timestamp(d2);

                    provincia.setId(Integer.parseInt(form1.campoBusqueda.getText()));
                    usuario.setId(-1);

                    modelo.ReporteAuditoriaProvinciasPorProvinciaYUsuario(provincia,usuario, fechaDesde, fechaHasta);
                }
                
                if ("".equals(form1.campoBusqueda.getText()) && ("".equals(form1.campoFiltro.getText()))) {
                    Date date1 = form1.fechaDesde.getDate();
                    Date date2 = form1.fechaHasta.getDate();

                    long d1 = date1.getTime();
                    long d2 = date2.getTime();

                    //java.sql.Date fechaDesde = new java.sql.Date(d1);
                    // java.sql.Date fechaHasta = new java.sql.Date(d2);
                    java.sql.Timestamp fechaDesde = new java.sql.Timestamp(d1);
                    java.sql.Timestamp fechaHasta = new java.sql.Timestamp(d2);
                    provincia.setId(-1);
                    usuario.setId(-1);

                    modelo.ReporteAuditoriaProvinciasPorProvinciaYUsuario(provincia,usuario,fechaDesde, fechaHasta);
                }
                
                 if ("".equals(form1.campoBusqueda.getText()) && (!"".equals(form1.campoFiltro.getText()))) {
                    Date date1 = form1.fechaDesde.getDate();
                    Date date2 = form1.fechaHasta.getDate();

                    long d1 = date1.getTime();
                    long d2 = date2.getTime();

                    //java.sql.Date fechaDesde = new java.sql.Date(d1);
                    // java.sql.Date fechaHasta = new java.sql.Date(d2);
                    java.sql.Timestamp fechaDesde = new java.sql.Timestamp(d1);
                    java.sql.Timestamp fechaHasta = new java.sql.Timestamp(d2);

                    provincia.setId(-1);
                    usuario.setId(Integer.parseInt(form1.campoFiltro.getText()));

                    modelo.ReporteAuditoriaProvinciasPorProvinciaYUsuario(provincia,usuario, fechaDesde, fechaHasta);
                }
                
            
            } //cierre de las opciones de PROVINCIA
            
            if (form1.comboTablas.getSelectedIndex() == 1) {
                
                if ("".equals(form1.campoBusqueda.getText()) && ("".equals(form1.campoFiltro.getText()))) {
                    Date date1 = form1.fechaDesde.getDate();
                    Date date2 = form1.fechaHasta.getDate();

                    long d1 = date1.getTime();
                    long d2 = date2.getTime();

                    java.sql.Timestamp fechaDesde = new java.sql.Timestamp(d1);
                    java.sql.Timestamp fechaHasta = new java.sql.Timestamp(d2);
                    
                    usuario.setId(-1);
                    cliente.setId(-1);

                    modelo.ReporteAuditoriaClientesPorClienteYUsuario(cliente,usuario,fechaDesde, fechaHasta);
                }
                
                if (!"".equals(form1.campoBusqueda.getText()) && ("".equals(form1.campoFiltro.getText()))) {
                    Date date1 = form1.fechaDesde.getDate();
                    Date date2 = form1.fechaHasta.getDate();

                    long d1 = date1.getTime();
                    long d2 = date2.getTime();

                    java.sql.Timestamp fechaDesde = new java.sql.Timestamp(d1);
                    java.sql.Timestamp fechaHasta = new java.sql.Timestamp(d2);

                    cliente.setId(Integer.parseInt(form1.campoBusqueda.getText()));
                    usuario.setId(-1);

                    modelo.ReporteAuditoriaClientesPorClienteYUsuario(cliente, usuario,fechaDesde, fechaHasta);
                }
                
                if (!"".equals(form1.campoBusqueda.getText()) && (!"".equals(form1.campoFiltro.getText()))) {
                    Date date1 = form1.fechaDesde.getDate();
                    Date date2 = form1.fechaHasta.getDate();

                    long d1 = date1.getTime();
                    long d2 = date2.getTime();


                    java.sql.Timestamp fechaDesde = new java.sql.Timestamp(d1);
                    java.sql.Timestamp fechaHasta = new java.sql.Timestamp(d2);

                    cliente.setId(Integer.parseInt(form1.campoBusqueda.getText()));
                    usuario.setId(Integer.parseInt(form1.campoFiltro.getText()));

                    modelo.ReporteAuditoriaClientesPorClienteYUsuario(cliente, usuario, fechaDesde, fechaHasta);
                }
                
                if ("".equals(form1.campoBusqueda.getText()) && (!"".equals(form1.campoFiltro.getText()))) {
                    Date date1 = form1.fechaDesde.getDate();
                    Date date2 = form1.fechaHasta.getDate();

                    long d1 = date1.getTime();
                    long d2 = date2.getTime();

                    java.sql.Timestamp fechaDesde = new java.sql.Timestamp(d1);
                    java.sql.Timestamp fechaHasta = new java.sql.Timestamp(d2);

                    cliente.setId(-1);
                    usuario.setId(Integer.parseInt(form1.campoFiltro.getText()));

                    modelo.ReporteAuditoriaClientesPorClienteYUsuario(cliente, usuario,fechaDesde, fechaHasta);
                }
            
            } //cierre de las opciones de CLIENTE
            
               if (form1.comboTablas.getSelectedIndex() == 2) {
                
                if ("".equals(form1.campoBusqueda.getText())) {
                    Date date1 = form1.fechaDesde.getDate();
                    Date date2 = form1.fechaHasta.getDate();

                    long d1 = date1.getTime();
                    long d2 = date2.getTime();

                    java.sql.Timestamp fechaDesde = new java.sql.Timestamp(d1);
                    java.sql.Timestamp fechaHasta = new java.sql.Timestamp(d2);
                    
                    usuario.setId(-1);

                    modelo.ReporteAuditoriaUsuarios(usuario,fechaDesde, fechaHasta);
                }
                else {
                    Date date1 = form1.fechaDesde.getDate();
                    Date date2 = form1.fechaHasta.getDate();

                    long d1 = date1.getTime();
                    long d2 = date2.getTime();

                    java.sql.Timestamp fechaDesde = new java.sql.Timestamp(d1);
                    java.sql.Timestamp fechaHasta = new java.sql.Timestamp(d2);
                    
                    usuario.setId(Integer.parseInt(form1.campoBusqueda.getText()));

                    modelo.ReporteAuditoriaUsuarios(usuario,fechaDesde, fechaHasta);
                }
                
                
                
               
            
            } //cierre de las opciones de CLIENTE
            
            
            
          // limpiarCajas();
             usuario.setId(-1);
             provincia.setId(-1);
             cliente.setId(-1);
        }
        
        if (e.getSource() == form1.botonExportarExcel) {
            //EXPORTO UN EXCEL DE PROVINCIAS
            if (form1.comboTablas.getSelectedIndex() == 0) {
                Date date1 = form1.fechaDesde.getDate();
                Date date2 = form1.fechaHasta.getDate();

                long d1 = date1.getTime();
                long d2 = date2.getTime();

                java.sql.Timestamp fechaDesde = new java.sql.Timestamp(d1);
                java.sql.Timestamp fechaHasta = new java.sql.Timestamp(d2);

                if (!"".equals(form1.campoBusqueda.getText())) {
                provincia.setId(Integer.parseInt(form1.campoBusqueda.getText()));
                }
                else {
                provincia.setId(0);
                }
                
                if (!"".equals(form1.campoFiltro.getText())) {
                usuario.setId(Integer.parseInt(form1.campoFiltro.getText()));
                }
                else {
                 usuario.setId(0);
                }

                modelo.crearExcelProvincias(provincia, usuario, fechaDesde, fechaHasta);
               
               
            }
            //EXPORTO UN EXCEL DE CLIENTES
             if (form1.comboTablas.getSelectedIndex() == 1) {
                Date date1 = form1.fechaDesde.getDate();
                Date date2 = form1.fechaHasta.getDate();

                long d1 = date1.getTime();
                long d2 = date2.getTime();

                java.sql.Timestamp fechaDesde = new java.sql.Timestamp(d1);
                java.sql.Timestamp fechaHasta = new java.sql.Timestamp(d2);

                if (!"".equals(form1.campoBusqueda.getText())) {
                cliente.setId(Integer.parseInt(form1.campoBusqueda.getText()));
                }
                else {
                cliente.setId(0);
                }
                
                if (!"".equals(form1.campoFiltro.getText())) {
                usuario.setId(Integer.parseInt(form1.campoFiltro.getText()));
                }
                 else {
                 usuario.setId(0);
                }

                modelo.crearExcelClientes(cliente, usuario, fechaDesde, fechaHasta);
             
            }
             //CREO UN EXCEL DE USUARIOS
             if (form1.comboTablas.getSelectedIndex() == 2) {
                 Usuario usuarioAux = new Usuario();
                 usuarioAux.setId(-1);
                Date date1 = form1.fechaDesde.getDate();
                Date date2 = form1.fechaHasta.getDate();

                long d1 = date1.getTime();
                long d2 = date2.getTime();

                java.sql.Timestamp fechaDesde = new java.sql.Timestamp(d1);
                java.sql.Timestamp fechaHasta = new java.sql.Timestamp(d2);

                if (!"".equals(form1.campoBusqueda.getText())) {
                usuarioAux.setId(Integer.parseInt(form1.campoBusqueda.getText()));
                }

                modelo.crearExcelUsuarios(usuarioAux, fechaDesde, fechaHasta);
            }
             
             usuario.setId(0);
             provincia.setId(0);
             cliente.setId(0);
        }
        
        
        
    }

    @Override
    public void keyTyped(KeyEvent e) {
        
      if (e.getSource() == form1.campoBusqueda) {
      char c = e.getKeyChar();
        
        if (((c < '0') || (c > '9')) && (c!= KeyEvent.VK_BACK_SPACE)) {
             e.consume();
            }
      } 
      
      if (e.getSource() == form1.campoFiltro) {
      char c = e.getKeyChar();
        
        if (((c < '0') || (c > '9')) && (c!= KeyEvent.VK_BACK_SPACE)) {
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
    
    
    
    
}
