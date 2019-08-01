/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;
import static javax.swing.WindowConstants.DISPOSE_ON_CLOSE;
import javax.swing.table.DefaultTableModel;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;

/**
 *
 * @author Schefer
 */
public class ConsultasProvincia extends Conexion {
    PreparedStatement ps;
    ResultSet rs;
    
    public boolean insertar(Provincia provincia) {
    
        Connection conexion = getConnection();
        
        try{
        
        ps = conexion.prepareStatement("insert into provincias (provincia) values (?)");
        ps.setString(1, provincia.getNombre());
        int resultado = ps.executeUpdate();
        
        if (resultado > 0) {
        return true;
        }
        else {
          return false;
        }
            
        } catch(Exception ex) {
        System.err.println("Error" + ex);
         return false;
        }
        finally {
        
            try{
            conexion.close();
            } catch (Exception ex) {
             System.err.println("Error" + ex);
            }
        }
 
        
    }
    
    public DefaultTableModel buscarProvincias(Provincia provincia, int indice) {

        DefaultTableModel modeloTabla = new DefaultTableModel();
        modeloTabla.addColumn("Codigo");
        modeloTabla.addColumn("Provincia");
        
        String like ="";
        if (indice == 0) {
         like = "where id ='" +provincia.getId()+ "'";
        }
        else if (indice == 1) {
         like = "where provincia like '" +provincia.getNombre()+ "%' ";
        }
        
        else if (indice == 2) {  
         like ="";
        }
        
        
        Connection conexion = getConnection();
       
        try{
        
        ps = conexion.prepareStatement("select * from provincias " + like);
        rs = ps.executeQuery();
        
          ResultSetMetaData rsmd = rs.getMetaData();
            int cantColumnas = rsmd.getColumnCount();//son 7
        
        while (rs.next()) {
        Object fila [] = new Object[cantColumnas]; // guardo un registro
            
            for (int i = 0;i < cantColumnas; i++) {
                fila[i] = rs.getObject(i+1);
            }
            
            modeloTabla.addRow(fila);
        }
        return modeloTabla;
        
    
            
        } catch(Exception ex) {
        System.err.println("Error" + ex);
         return modeloTabla;
        }
        finally {
        
            try{
            conexion.close();
            } catch (Exception ex) {
             System.err.println("Error" + ex);
            }
        }
        
        
        
        
    }
    
    public DefaultTableModel llenarTabla() {
    DefaultTableModel modeloTabla = new DefaultTableModel();
        
        
        PreparedStatement ps = null;
        ResultSet rs = null;
        Connection conexion = getConnection();
        
        try {
    
            ps = conexion.prepareStatement("select id,provincia from provincias");
            rs = ps.executeQuery(); //obtengo los resultados de la consulta
            
            modeloTabla.addColumn("Codigo");
            modeloTabla.addColumn("Provincia");
            
            ResultSetMetaData rsmd = rs.getMetaData();
            int cantColumnas = rsmd.getColumnCount();//son 7

            while (rs.next()){
            Object fila [] = new Object[cantColumnas]; // guardo un registro
            
            for (int i = 0;i < cantColumnas; i++) {
                fila[i] = rs.getObject(i+1);
            }
            
            modeloTabla.addRow(fila);
            
            }
            return modeloTabla;  
            //conexion.close();
            
        
        } catch (Exception ex) {
        System.err.print("Error" + ex);
        return modeloTabla;
        }
        finally{
            
        try{
            conexion.close();
            } catch (Exception ex) {
             System.err.println("Error" + ex);
            }
        }
        
        
     
    }

    public boolean eliminar(Provincia provincia) {

        Connection conexion = getConnection();
        
        try{
           //aca tiene que chequear que no haya clientes en esas provincias
        ps = conexion.prepareStatement("select count(idClientes) from clientes where nroProvincia = ?");
        ps.setInt(1,provincia.getId());
        rs = ps.executeQuery();
        int cantClientes;
        
        if (rs.next()) {
        cantClientes = rs.getInt(1);
        }
        else {
        cantClientes = 1;
        }
        
        if (cantClientes !=0) {
        JOptionPane.showMessageDialog(null, "No se puede eliminar la provincia, hay clientes asociados a la provincia");
        return false;
        }
        
        else
        {
            //le pregunto si realmente quiere eliminar la provincia
        int chequeo = JOptionPane.showConfirmDialog(null, "Esta seguro de eliminar la provincia? Por favor confirme.");
        if (chequeo == JOptionPane.YES_OPTION) {

        ps = conexion.prepareStatement("delete from provincias where id =?");
        ps.setInt(1, provincia.getId());
        int resultado = ps.executeUpdate();
        
        if (resultado > 0) {
            
            JOptionPane.showMessageDialog(null, "Provincia eliminada con exito");
            return true;
        }
        else {
        JOptionPane.showMessageDialog(null, "Error. La provincia no se pudo eliminar");
        return false;
        }
        
        }
        
        }

            
        } catch(Exception ex) {
        System.err.println("Error" + ex);
        return false;
        }
        finally {
        
            try{
            conexion.close();
            
            } catch (Exception ex) {
             System.err.println("Error" + ex);
             return false;
            }
        }
        
    return false;
    }
    
    public boolean modificar(Provincia provincia){
         Connection conexion = getConnection();
        
        try{
        
        ps = conexion.prepareStatement("select count(idClientes) from clientes where nroProvincia = ?");
        ps.setInt(1,provincia.getId());
        rs = ps.executeQuery();
        int cantClientes;
        
        if (rs.next()) {
        cantClientes = rs.getInt(1);
        }
        else {
        cantClientes = 1;
        }
        
        if (cantClientes !=0) {
        return false;
        }
        
        else{
     
        ps = conexion.prepareStatement("update provincias set provincia = ? where id = ?");
        ps.setString(1, provincia.getNombre());
        ps.setInt(2, provincia.getId());
        int resultado = ps.executeUpdate();
        
        if (resultado > 0) {
        return true;
        }
        else {
          return false;
        }
        
        }   
        
        } catch(Exception ex) {
        System.err.println("Error" + ex);
         return false;
        }
        finally {
        
            try{
            conexion.close();
            } catch (Exception ex) {
             System.err.println("Error" + ex);
            }
        }
    }
    
    public void llamarReporteProvincias(int desde, int hasta){
      
        Connection conexion = getConnection();
        try{
        
        
        //JasperReport reporte = null;
        //String ruta = "src\\Modelo\\reporteProvincias.jasper";
        //reporte = (JasperReport) JRLoader.loadObjectFromFile(ruta);
        
        JasperReport reporte = (JasperReport) JRLoader.loadObject(getClass().getResource("/Reportes/reporteProvincias.jasper"));
        
        Map parametro = new HashMap();
        parametro.put("id_provincia1", desde);
        parametro.put("id_provincia2", hasta);
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
    
    public boolean buscarNombreProvincia(Provincia provincia) {
         //DEVUELVE TRUE SI EL NOMBRE DE LA PROVINCIA YA ESTA INGRESADO EN LA BBDD
 
        Connection conexion = getConnection();
       
        try{
        
        ps = conexion.prepareStatement("select count(id) from provincias where provincia = ?");
        ps.setString(1,provincia.getNombre());
        rs = ps.executeQuery();
        int cantNombre;
        
    
       if (rs.next()) {
        cantNombre = rs.getInt(1);
        }
        else {
        cantNombre = 1;
        }
       
       if (cantNombre !=0) {
        return true;
        }
       else
        {
            return false;
        }
        
    
            
        } catch(Exception ex) {
        System.err.println("Error" + ex);
         return true;
        }
        finally {
        
            try{
            conexion.close();
            } catch (Exception ex) {
             System.err.println("Error" + ex);
            }
        }
     }
    
    public int buscarIdProvincia(Provincia provincia) {
        //me pasan el nombre de una provincia y me devuelve el id. Sino existe me devuelve un 0
        Connection conexion = getConnection();
       
        try{
        
        ps = conexion.prepareStatement("select id from provincias where provincia = ?");
        ps.setString(1,provincia.getNombre());
        rs = ps.executeQuery();
        int id;
        
    
       if (rs.next()) {
        id = rs.getInt(1);
        }
        else {
        id = 0;
        }
       
       return id;
    
            
        } catch(Exception ex) {
        System.err.println("Error" + ex);
         return 0;
        }
        finally {
        
            try{
            conexion.close();
            } catch (Exception ex) {
             System.err.println("Error" + ex);
            }
        }
     }
    
    
        public boolean insertarEnAuditoriaProvincias(Provincia provincia, String accion,Usuario usuario) throws ParseException, UnknownHostException {
         //establezco la fecha actual y la formateo para la bbdd
         Date date = new Date();
         
        DateFormat hourdateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

         String cadena = hourdateFormat.format(date);
         
         Date fecha = hourdateFormat.parse(cadena);
         
         long d = fecha.getTime();
         

         java.sql.Timestamp fecha2 = new java.sql.Timestamp(d);
         //System.out.println(d);
          //System.out.println(fecha);
         //System.out.println(fecha2);

         
         //establezco el IP de la pc
         String ip = InetAddress.getLocalHost().getHostAddress();
 
        Connection conexion = getConnection();
        
        try{
        
        ps = conexion.prepareStatement("insert into auditoria_provincias (id_provincia,nombre,fecha,ip,accion,usuario) values (?,?,?,?,?,?)");
        ps.setInt(1, provincia.getId());
        ps.setString(2, provincia.getNombre());
        ps.setTimestamp(3, fecha2);
        ps.setString(4, ip);
        ps.setString(5, accion);
        ps.setInt(6, usuario.getId());
        
        int resultado = ps.executeUpdate();
        
        if (resultado > 0) {
        return true;
        }
        else {
          return false;
        }
            
        } catch(Exception ex) {
        System.err.println("Error" + ex);
         return false;
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