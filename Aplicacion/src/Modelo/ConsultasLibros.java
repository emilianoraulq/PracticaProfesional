/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

import com.mysql.jdbc.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;
import static javax.swing.WindowConstants.DISPOSE_ON_CLOSE;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;

/**
 *
 * @author erqui
 */
public class ConsultasLibros extends Conexion {
    
    PreparedStatement ps;
     ResultSet rs;
     
     
     public void LibroMayor(Usuario usuario, Timestamp desde, Timestamp hasta){
      
        Connection conexion = getConnection();
        
        try{
  
        JasperReport reporte = (JasperReport) JRLoader.loadObject(getClass().getResource("/Reportes/libroMayor.jasper"));
        
        Map parametro = new HashMap();
        parametro.put("fecha_desde", desde);
        parametro.put("fecha_hasta", hasta);
        parametro.put("id_usuario", usuario.getId());
        parametro.put("nombreEmpresa", Conexion.getNombreBase());
        
       
        
        JasperPrint imprimirReporte = JasperFillManager.fillReport(reporte, parametro,conexion);
        
        JasperViewer vistaReporte = new JasperViewer(imprimirReporte,false);
        vistaReporte.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        vistaReporte.setVisible(true);
    
            
        } catch(Exception ex) {
        System.err.println("Error" + ex);
         
        }
        finally {
        
           try{
           conexion.close();
           } catch (Exception ex) {
            System.err.println("Error" + ex);
           }
        }
     }
    
}
