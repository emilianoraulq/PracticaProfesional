
package Controlador;


import Modelo.Conexion;
import static Modelo.Conexion.contraseña;
import Modelo.ConsultasBackup;
import Modelo.ConsultasUsuario;
import Modelo.Usuario;
import Vista.FormBackup;
import static Vista.LoginForm.txtEmpresa;
import Vista.MenuInicial;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;


/**
 *
 * @author Schefer
 */
public class ControladorBackup implements ActionListener {
    private FormBackup form1; 
    private Usuario usuario;
    private int opcion;
    private ConsultasBackup modelo;
    
    public ControladorBackup (FormBackup form1, Usuario usuario, int opcion, ConsultasBackup modelo) {
    this.form1 = form1;
    this.usuario = usuario;
    this.opcion = opcion;
    //OPCION 1 ES EXPORTAR
    //OPTION 2 ES IMPORTAR
    this.modelo = modelo;
    
    form1.botonAccion.addActionListener(this);
    form1.botonSeleccionar.addActionListener(this);
    form1.btnCancelar.addActionListener(this);
    
     
    if (opcion == 1) {
    form1.etiquetaMenu.setText("Menu de Respaldo de Informacion");
    form1.botonAccion.setText("Exportar BD");
    form1.botonSeleccionar.setText("Seleccionar carpeta");
    form1.campoContraseña.setVisible(false);
    form1.etiquetaContraseña.setVisible(false);
    }
    
    if (opcion == 2) {
    form1.etiquetaMenu.setText("Menu de Restauración de Informacion");
    form1.botonAccion.setText("Importar BD");
    form1.botonSeleccionar.setText("Seleccionar archivo");
    }
    
    if (opcion==3){
        form1.etiquetaMenu.setText("Menu de Restauración de Informacion");
        form1.botonAccion.setText("Importar BD");
        form1.botonSeleccionar.setText("Seleccionar archivo");
    }
    

    
    }
    
    public void iniciar(){
        
     if (opcion==3)   {
    form1.setTitle("Menu de Backup ");  
    form1.setLocationRelativeTo(null);
    form1.setVisible(true);
   
    
    form1.campoRuta.requestFocus();
   
    
    form1.campoRuta.requestFocus();
    
     }
     else{
         form1.setTitle("Empresa: "+Conexion.getNombreBase()+" - Usuario: " + usuario.getNombre() + " " + usuario.getApellido()+"- Menú de backup" );
    form1.setLocationRelativeTo(null);
    form1.setVisible(true);
   
    
    form1.campoRuta.requestFocus();
    
 
         
     }
    
    
    }

    @Override
    public void actionPerformed(ActionEvent e) {
       
        if (e.getSource() == form1.botonSeleccionar) {
            
            if (opcion == 1) {
                JFileChooser fc = new JFileChooser();
               fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

               int op = fc.showSaveDialog(null);

               if (op == JFileChooser.APPROVE_OPTION) {
               String ruta = fc.getSelectedFile().getPath();
               form1.campoRuta.setText(ruta);
               }
            }
            
            if (opcion == 2) {
                JFileChooser fc = new JFileChooser();
               FileNameExtensionFilter filtro = new FileNameExtensionFilter("SQL","sql"); //esto es opcional, pero esta bueno para que solo muestre los archivos SQL
               fc.setFileFilter(filtro);
               
               int op = fc.showOpenDialog(null);

               if (op == JFileChooser.APPROVE_OPTION) {
               String ruta = fc.getSelectedFile().getPath();
               form1.campoRuta.setText(ruta);
               }
            }
            
            if (opcion==3){
                
                JFileChooser fc = new JFileChooser();
               FileNameExtensionFilter filtro = new FileNameExtensionFilter("SQL","sql"); //esto es opcional, pero esta bueno para que solo muestre los archivos SQL
               fc.setFileFilter(filtro);
               
               int op = fc.showOpenDialog(null);

               if (op == JFileChooser.APPROVE_OPTION) {
               String ruta = fc.getSelectedFile().getPath();
               form1.campoRuta.setText(ruta);
               }
                
            }
           
        
        } //FIN BOTON SELECCIONAR
        
        if (e.getSource() == form1.botonAccion) {
            
            if (opcion == 1) {
        
            String ruta = form1.campoRuta.getText();
            
            //String empresa = "empresa1"; 
            Calendar date = new GregorianCalendar();
            int año = date.get(Calendar.YEAR);
            int mes = date.get(Calendar.MONTH)+1;
            int dia = date.get(Calendar.DAY_OF_MONTH);
            
               
            int hora = date.get(Calendar.HOUR_OF_DAY);
            int minutos = date.get(Calendar.MINUTE);
            int segundos = date.get(Calendar.SECOND);
            //System.out.println(hora);
            //System.out.println(minutos);
            //System.out.println(segundos);

            String fecha = String.valueOf(dia) + "-" + String.valueOf(mes) + "-" + String.valueOf(año) + "-" + String.valueOf(hora) + "-" + String.valueOf(minutos) + "-" + String.valueOf(segundos);
           
        
            String nombre = "\\backup" + "-" + fecha + "-" + Conexion.getNombreBase()+".sql";
            String backup = "";
            
                if (!"".equals(ruta)) {
                    
                    try {
                    backup = "C:\\Program Files\\MySQL\\MySQL Server 8.0\\bin\\mysqldump --opt -u"+Conexion.getUsuario()+" -p"+Conexion.getContraseña()+" -B "+Conexion.getNombreBase()+" -r "+ruta+nombre;
                   
                    Runtime rt = Runtime.getRuntime();
                    rt.exec(backup);
                    JOptionPane.showMessageDialog(null, "Exportado exitosamente");
                    
                    
                    
                    //esto es para volver al formulario ppal
                    form1.dispose();
                    
                     //
                    
                    
                    } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, ex.getMessage());
                    }
                    

                }
            
            }
            
            if (opcion == 2) {
                
                String ruta = form1.campoRuta.getText();
                String backup = "";
            
                if (!"".equals(ruta)) {
                    
                    String cadena = modelo.conseguirNombreScript(ruta);
 
                    //comparo que la cadena que me paso sea igual que el nombre de la bbdd

                    if (cadena.equals(Conexion.getNombreBase())) {
                    //si son iguales ahora comparo la contraseña que indicó el usuario con la contraseña de la tabla datos-empresa
                    //primero obtengo la contraseña del campo password
                        String contraseña = "";
                        for (int j=0; j < form1.campoContraseña.getPassword().length ;j++) {
                        contraseña += form1.campoContraseña.getPassword()[j];
                        
                        }
                        
                        System.out.println(contraseña);
                     //ahora si comparo la contraseña con la de la tabla datos_empresa
                        if (modelo.controlarContraseña(contraseña)) {
                        //una vez controlado el script y la contraseña, si se puede importar el script
                            try {
                            backup = "cmd /c mysql -u"+Conexion.getUsuario()+" -p"+Conexion.getContraseña()+" "+Conexion.getNombreBase()+" < "+ruta;
                            Runtime rt = Runtime.getRuntime();
                            rt.exec(backup);
                            JOptionPane.showMessageDialog(null, "Importado exitosamente");
                            
                            
                            //esto es para volver al formulario ppal
                            form1.dispose();
                           
                            //
                            
                            
                            
                            
                            } catch (Exception ex) {
                            JOptionPane.showMessageDialog(null, ex.getMessage());
                            }
                        
                        }
                        else {
                        JOptionPane.showMessageDialog(null, "Por favor ingrese correctamente la contraseña de su empresa");
                        form1.campoContraseña.requestFocus();
                        }
       
                    }
                    else {
                      JOptionPane.showMessageDialog(null, "Archivo backup incorrecto");
                      form1.campoRuta.requestFocus();
                    }
 
                        
                }
                else{
                    JOptionPane.showMessageDialog(null, "Por favor ingrese algun backup");
                    form1.campoRuta.requestFocus();
                }
            
            }
            
            if (opcion==3){
                
                String ruta = form1.campoRuta.getText();
                String backup = "";
                //JOptionPane.showMessageDialog(null, txtEmpresa.getText());
            
                if (!"".equals(ruta)) {
                    
                    String cadena = modelo.conseguirNombreScript(ruta);
 
                    //comparo que la cadena que me paso sea igual que el nombre de la bbdd
                        
                    if (cadena.equals(txtEmpresa.getText())) {//aca se modificó Conexion.getNombreBase()
                        
                    //si son iguales lo comparo con el nombre de la base de datos
                    //CHEQUEAR QUE RECIBA BIEN EL NOMBRE DE LA BASE DE DATOS
                        
                            try {
                            backup = "cmd /c mysql -u"+Conexion.getUsuario()+" -p"+Conexion.getContraseña()+" "+Conexion.getNombreBase()+" < "+ruta;
                            Runtime rt = Runtime.getRuntime();
                            rt.exec(backup);
                            JOptionPane.showMessageDialog(null, "Importado exitosamente");
                            
                            //esto es para volver al formulario ppal
                            form1.dispose();
                           
                            //
                            
                            } catch (Exception ex) {
                            JOptionPane.showMessageDialog(null, ex.getMessage());
                            }

       
                    }
                    else {
                      JOptionPane.showMessageDialog(null, "Archivo backup incorrecto");
                      form1.campoRuta.requestFocus();
                    }
 
                        
                }
                else{
                    JOptionPane.showMessageDialog(null, "Por favor ingrese algun backup");
                    form1.campoRuta.requestFocus();
                }
                
            }
            
        } //FIN BOTON ACCION (EXPORTAR O IMPORTAR)
        
        if (e.getSource()==form1.btnCancelar){
            
          
                form1.dispose();
               
                         
            
            
        }
        
    }
}
