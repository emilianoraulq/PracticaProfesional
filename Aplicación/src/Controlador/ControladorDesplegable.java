
package Controlador;

import Modelo.Cliente;
import Modelo.ConsultasDesplegable;
import Vista.Desplegable;
import Vista.FormClientes;
import static Vista.FormClientes.campoProvincia;
import Vista.FormPerfiles;
import Vista.FormUsuarios;
import Vista.LoginForm;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;


public class ControladorDesplegable implements ActionListener,MouseListener,KeyListener {
     private Desplegable form1;
     private ConsultasDesplegable modelo;
     private int tabla;
     
     public ControladorDesplegable(Desplegable form1, ConsultasDesplegable modelo, int tabla) {
        this.form1 = form1;
        this.modelo = modelo;
        this.tabla = tabla;
        
        //aca iria el control para ver que tabla llenar
        if (tabla == 1) {
        form1.tablaDesplegable.setModel(modelo.llenarTablaProvincias()); //lleno tabla al iniciar
        form1.etiquetaBusqueda.setText("Provincia:");
        }
        
        if (tabla == 2){
           form1.tablaDesplegable.setModel(modelo.llenarTablaPerfiles()); //lleno tabla con perfiles al iniciar
            form1.etiquetaBusqueda.setText("Perfil:");
        }
        
         if (tabla == 3){
           form1.tablaDesplegable.setModel(modelo.llenarTablaPerfiles()); //lleno tabla con perfiles al iniciar
            form1.etiquetaBusqueda.setText("Perfil:");
        }
         
         if (tabla == 4){
           form1.tablaDesplegable.setModel(modelo.llenarTablaActividades()); //lleno tabla con perfiles al iniciar
            form1.etiquetaBusqueda.setText("Actividad:");
        }
         
         if (tabla==5){
             form1.tablaDesplegable.setModel(modelo.llenarTablaEmpresas()); //lleno tabla con perfiles al iniciar
            form1.etiquetaBusqueda.setText("Empresas:");
             
         }
         
         
       
        
        form1.botonBuscar.addActionListener(this);
        form1.botonConfirmar.addActionListener(this);
        form1.tablaDesplegable.addMouseListener(this);
        form1.tablaDesplegable.addKeyListener(this);
  
        
    }
     
     public void iniciar(){
    form1.setTitle("Por favor seleccione opcion");
    form1.setLocationRelativeTo(null);
    form1.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == form1.botonBuscar) {
            
            if (tabla == 1) {
             DefaultTableModel modeloTabla = new DefaultTableModel();
             modeloTabla = modelo.buscarProvincias(form1.jTextField1.getText());
             form1.tablaDesplegable.setModel(modeloTabla);
            }
            
            if (tabla == 2) {
             DefaultTableModel modeloTabla = new DefaultTableModel();
             modeloTabla = modelo.buscarPerfiles(form1.jTextField1.getText());
             form1.tablaDesplegable.setModel(modeloTabla);
            }
            
            if (tabla == 3){
             DefaultTableModel modeloTabla = new DefaultTableModel();
             modeloTabla = modelo.buscarPerfiles(form1.jTextField1.getText());
             form1.tablaDesplegable.setModel(modeloTabla);
            }
            
            if (tabla == 4){
             DefaultTableModel modeloTabla = new DefaultTableModel();
             modeloTabla = modelo.buscarActividades(form1.jTextField1.getText());
             form1.tablaDesplegable.setModel(modeloTabla);
            }
            
            if (tabla == 5){
             DefaultTableModel modeloTabla = new DefaultTableModel();
             modeloTabla = modelo.buscarEmpresas(form1.jTextField1.getText());
             form1.tablaDesplegable.setModel(modeloTabla);
            }
        
        }
        
        if (e.getSource() == form1.botonConfirmar) {
        
            int fila = form1.tablaDesplegable.getSelectedRow();
         
          if (fila ==-1) {
        JOptionPane.showMessageDialog(null, "No se selecciono ningun registro");
          }
         
          else {
              if (tabla == 1) {
              FormClientes.campoProvincia.setText(form1.tablaDesplegable.getValueAt(fila, 0).toString());
              FormClientes.campoNombreProvincia.setText(form1.tablaDesplegable.getValueAt(fila, 1).toString());
              }
              
              if (tabla == 2) {
              FormUsuarios.txtIdPerfilUsuario.setText(form1.tablaDesplegable.getValueAt(fila, 0).toString());
              FormUsuarios.txtNombrePerfilUsuario.setText(form1.tablaDesplegable.getValueAt(fila, 1).toString());
              }
              
              if (tabla == 3) {
              FormPerfiles.txtIdPerfilPermiso.setText(form1.tablaDesplegable.getValueAt(fila, 0).toString());
              //FormUsuarios.txtNombrePerfilUsuario.setText(form1.tablaDesplegable.getValueAt(fila, 1).toString());
              }
              
              if (tabla == 4) {
              FormPerfiles.txtIdActividadPermiso.setText(form1.tablaDesplegable.getValueAt(fila, 0).toString());
              //FormUsuarios.txtNombrePerfilUsuario.setText(form1.tablaDesplegable.getValueAt(fila, 1).toString());
              }
              
              if (tabla == 5) {
              LoginForm.txtEmpresa.setText(form1.tablaDesplegable.getValueAt(fila, 0).toString());
              //FormUsuarios.txtNombrePerfilUsuario.setText(form1.tablaDesplegable.getValueAt(fila, 1).toString());
              }
             
              this.form1.dispose();
              
               // if ("Provincias".equals(jLabel2.getText())) {
              //  Cliente.campoProvincia.setText(provincia);;
              //  }
              
              }
          
  
            
        }
     
    }

    

    @Override
    public void mousePressed(MouseEvent e) {
        
        int fila = form1.tablaDesplegable.getSelectedRow();
           if (e.getSource() == form1.tablaDesplegable) {
               
           if (e.getClickCount() == 2) {
 
           
           if (fila == -1) {
                JOptionPane.showMessageDialog(null, "No hay ningun registro seleccionado");
            } 
            else {
                if (tabla == 1) {
                FormClientes.campoProvincia.setText(form1.tablaDesplegable.getValueAt(fila, 0).toString());
                FormClientes.campoNombreProvincia.setText(form1.tablaDesplegable.getValueAt(fila, 1).toString());
                }
                
                if (tabla == 2) {
              FormUsuarios.txtIdPerfilUsuario.setText(form1.tablaDesplegable.getValueAt(fila, 0).toString());
              FormUsuarios.txtNombrePerfilUsuario.setText(form1.tablaDesplegable.getValueAt(fila, 1).toString());
                }
                 
                
                if (tabla == 3) {
              FormPerfiles.txtIdPerfilPermiso.setText(form1.tablaDesplegable.getValueAt(fila, 0).toString());
              //FormUsuarios.txtNombrePerfilUsuario.setText(form1.tablaDesplegable.getValueAt(fila, 1).toString());
                }
                
                if (tabla == 4) {
              FormPerfiles.txtIdActividadPermiso.setText(form1.tablaDesplegable.getValueAt(fila, 0).toString());
              //FormUsuarios.txtNombrePerfilUsuario.setText(form1.tablaDesplegable.getValueAt(fila, 1).toString());
                }
                
                if (tabla == 5) {
              LoginForm.txtEmpresa.setText(form1.tablaDesplegable.getValueAt(fila, 0).toString());
              //FormUsuarios.txtNombrePerfilUsuario.setText(form1.tablaDesplegable.getValueAt(fila, 1).toString());
                }
               
           }
            form1.dispose();
        }
           
     
      
    
        }     

        
    }

    @Override
    public void mouseClicked(MouseEvent e) {
   
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
    public void keyTyped(KeyEvent e) {
     
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getSource() == form1.tablaDesplegable) {
            
           if (e.getKeyCode() == KeyEvent.VK_ENTER) {
               
           int fila = form1.tablaDesplegable.getSelectedRow();
           
           if (fila == -1) {
                JOptionPane.showMessageDialog(null, "No hay ningun registro seleccionado");
            } 
            else {
                 if (tabla == 1) {
                 FormClientes.campoProvincia.setText(form1.tablaDesplegable.getValueAt(fila, 0).toString());
                 FormClientes.campoNombreProvincia.setText(form1.tablaDesplegable.getValueAt(fila, 1).toString());
                 }
                 
                 if (tabla == 2) {
                 FormUsuarios.txtIdPerfilUsuario.setText(form1.tablaDesplegable.getValueAt(fila, 0).toString());
                 FormUsuarios.txtNombrePerfilUsuario.setText(form1.tablaDesplegable.getValueAt(fila, 1).toString());
                 }
                
               
           }
            form1.dispose();
           
           }
        
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    
     
}
