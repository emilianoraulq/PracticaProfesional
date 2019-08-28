
package Modelo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 *
 * @author Schefer
 */
public class ConsultasBackup extends Conexion {
     PreparedStatement ps;
     ResultSet rs;
     
     
     public boolean controlarContraseña(String contraseña){
     Connection conexion = getConnection();
     String contraseñaBBDD = "";
     
     try{
         
     ps = conexion.prepareStatement("select contraseña from datos_empresa");
     rs = ps.executeQuery();
     
     if (rs.next()) {
     contraseñaBBDD = rs.getString(1);
     System.out.println(contraseñaBBDD);
     }

     
     }catch(Exception ex){
     System.err.println("Error" + ex);
     }
     finally {
        
            try{
            conexion.close();
            } catch (Exception ex) {
             System.err.println("Error" + ex);
            }
        }
     
     if (contraseña.equals(contraseñaBBDD)) {
     return true;
     }
     else {
     return false;
     }
     
     }
     
     public String conseguirNombreScript(String nombre){
         
        //si la ruta no esta vacia controlo que el nombre del script sea igual que el nombre de la bbdd
        String cadenaInvertida = "";
        int i=nombre.length()-1;
        char caracter = '-';
        boolean encontre = false;
              
            while (encontre != true){
                cadenaInvertida += nombre.charAt(i);
                i = i-1;
                    if (nombre.charAt(i) == caracter){
                        encontre = true;
                        }
            }
                    
        //invierto la cadena
        StringBuilder builder=new StringBuilder(cadenaInvertida);
        String cad=builder.reverse().toString();
                    
        //a la cadena le saco el .sql
        String cadena = cad.substring(0, cad.length()-4);
        
        return cadena;
                    
     
     }
    
}

