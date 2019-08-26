/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;
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
public class ConsultasPlanCuentas extends Conexion {
    PreparedStatement ps;
    ResultSet rs;
    
    //trae todas las cuentas ACTIVAS
     public DefaultTableModel llenarTablaPlanCuentas(){
     DefaultTableModel modeloTabla = new DefaultTableModel();
     
     
     PreparedStatement ps = null;
     ResultSet rs = null;
     Connection conexion = getConnection();
     
          try {
    
            ps = conexion.prepareStatement("select codigo,descripcion,tipo,nrocuenta from plandecuentas where activo = 1 order by codigo asc");
            rs = ps.executeQuery(); //obtengo los resultados de la consulta
            
            modeloTabla.addColumn("Codigo");
            modeloTabla.addColumn("Descripcion");
            modeloTabla.addColumn("Tipo");
            modeloTabla.addColumn("Nro de Cuenta");
            
            ResultSetMetaData rsmd = rs.getMetaData();
            int cantColumnas = rsmd.getColumnCount();//son 4

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
     
     
     
     //return modeloTabla;
     } 
     
     
     //trae solamente los titulos
     public DefaultTableModel llenarTablaConTitulos(){
     DefaultTableModel modeloTabla = new DefaultTableModel();
     
     
     PreparedStatement ps = null;
     ResultSet rs = null;
     Connection conexion = getConnection();
     
          try {
    
            ps = conexion.prepareStatement("select codigo,descripcion,tipo,nrocuenta from plandecuentas where tipo = 0 order by codigo asc");
            rs = ps.executeQuery(); //obtengo los resultados de la consulta
            
            modeloTabla.addColumn("Codigo");
            modeloTabla.addColumn("Descripcion");
            modeloTabla.addColumn("Tipo");
            modeloTabla.addColumn("Nro de Cuenta");
            
            ResultSetMetaData rsmd = rs.getMetaData();
            int cantColumnas = rsmd.getColumnCount();//son 4

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
     
     
     
     //return modeloTabla;
     } 
     
     
     
     
     //DEVUELVE LA CANTIDAD DE ELEMENTOS QUE HAY EN EL NIVEL Y QUE EMPIECEN CON EL CODIGO PASADO POR PARAMETRO
     public int cantidadElementosDelNivel(int nivel, String cuenta){
        PreparedStatement ps = null;
        ResultSet rs = null;
        Connection conexion = getConnection(); 
        int cantidad = 0;
       // char inicial = cuenta.charAt(0);
        //System.out.println(inicial);
        
        String like = "and codigo like '" +cuenta+ "%' ";
        //System.out.println(like);
        
        
        try {
    
            ps = conexion.prepareStatement("select count(codigo) from plandecuentas where nivel = ? " + like);
            ps.setInt(1, nivel);
            rs = ps.executeQuery(); //obtengo los resultados de la consulta
            
            if (rs.next()) {
            cantidad = rs.getInt(1);
            //System.out.println(cantidad);     
            }

           
            //conexion.close();
            
        
        } catch (Exception ex) {
        System.err.print("Error" + ex);
        return cantidad;
        }
        finally{
            
        try{
            conexion.close();
            } catch (Exception ex) {
             System.err.println("Error" + ex);
            }
        }
         
      return cantidad;  
     }
     
     
     
    //DEVUELVE EL NIVEL EN EL QUE SE ENCUENTRA LA CUENTA QUE LE PASO COMO PARAMETRO 
    public int nivelDeLaCuenta(String codigoBalance){
        PreparedStatement ps = null;
        ResultSet rs = null;
        Connection conexion = getConnection(); 
        int nivel = 0;
        
        try {
    
            ps = conexion.prepareStatement("select nivel from plandecuentas where codigo = ?");
            ps.setString(1, codigoBalance);
            rs = ps.executeQuery(); //obtengo los resultados de la consulta
            
            if (rs.next()) {
            nivel = rs.getInt(1);
            //System.out.println(nivel);     
            }

           
            //conexion.close();
            
        
        } catch (Exception ex) {
        System.err.print("Error" + ex);
        return nivel;
        }
        finally{
            
        try{
            conexion.close();
            } catch (Exception ex) {
             System.err.println("Error" + ex);
            }
        }
         
      return nivel;  
     }
     
    
    public boolean crearCuenta (Cuenta cuenta){
       Connection conexion = getConnection();
        
        try{
        
        ps = conexion.prepareStatement("insert into plandecuentas (codigo,descripcion,tipo,nivel,inflacion,activo) values (?,?,?,?,?,?)");
            ps.setString(1, cuenta.getCodigo());
            ps.setString(2, cuenta.getDescripcion());
            ps.setInt(3, cuenta.getTipo());
            ps.setInt(4, cuenta.getNivel());
            ps.setInt(5, cuenta.getInflacion());
            ps.setInt(6, cuenta.getActivo());
            
        int resultado = ps.executeUpdate();//ejecuto la insercion 
        
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
    
    
    
    public boolean eliminarCuenta (int nroCuenta){
       Connection conexion = getConnection();
        
        try{
        
        ps = conexion.prepareStatement("delete from plandecuentas where nrocuenta = ?");
            ps.setInt(1, nroCuenta);
            
        int resultado = ps.executeUpdate();//ejecuto el borrado 
        
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
    
    
    
    public boolean modificarCuenta (Cuenta cuenta){
       Connection conexion = getConnection();
        
        try{
        
        ps = conexion.prepareStatement("update plandecuentas set codigo = ?, descripcion = ?, tipo = ?, nivel = ?, inflacion = ?, activo = ? where nrocuenta = ?");
            ps.setString(1, cuenta.getCodigo());
            ps.setString(2, cuenta.getDescripcion());
            ps.setInt(3, cuenta.getTipo());
            ps.setInt(4, cuenta.getNivel());
            ps.setInt(5, cuenta.getInflacion());
            ps.setInt(6, cuenta.getActivo());
            ps.setInt(7, cuenta.getNroCuenta());
            
        int resultado = ps.executeUpdate();//ejecuto la actualizacion 
        
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
    
    
    
    
    //se usa para borrados logicos
    public boolean desactivarCuenta (int nroCuenta){
       Connection conexion = getConnection();
        
        try{
        
        ps = conexion.prepareStatement("update plandecuentas set activo = 0 where nrocuenta = ?");
            ps.setInt(1, nroCuenta);
            
        int resultado = ps.executeUpdate();//ejecuto la actualizacion 
        
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
    
    
    
    //recupero un borrado logico
    public boolean activarCuenta (int nroCuenta){
       Connection conexion = getConnection();
        
        try{
        
        ps = conexion.prepareStatement("update plandecuentas set activo = 1 where nrocuenta = ?");
            ps.setInt(1, nroCuenta);
            
        int resultado = ps.executeUpdate();//ejecuto la actualizacion 
        
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
    
    
    
     public DefaultTableModel llenarTablaConCuentasInactivas(){
     DefaultTableModel modeloTabla = new DefaultTableModel();
     
     
     PreparedStatement ps = null;
     ResultSet rs = null;
     Connection conexion = getConnection();
     
          try {
    
            ps = conexion.prepareStatement("select codigo,descripcion,tipo,nrocuenta from plandecuentas where activo = 0 order by codigo asc");
            rs = ps.executeQuery(); //obtengo los resultados de la consulta
            
            modeloTabla.addColumn("Codigo");
            modeloTabla.addColumn("Descripcion");
            modeloTabla.addColumn("Tipo");
            modeloTabla.addColumn("Nro de Cuenta");
            
            ResultSetMetaData rsmd = rs.getMetaData();
            int cantColumnas = rsmd.getColumnCount();//son 4

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
     
     
     
     //return modeloTabla;
     } 
    
    
    public boolean buscarCodigoBalance(String cuenta) {
         //DEVUELVE TRUE SI EL CODIGO DE BALANCE YA ESTA INGRESADO EN LA BBDD
 
        Connection conexion = getConnection();
       
        try{
        
        ps = conexion.prepareStatement("select count(codigo) from plandecuentas where codigo = ?");
        ps.setString(1,cuenta);
        rs = ps.executeQuery();
        int cantCuenta;
        
    
       if (rs.next()) {
        cantCuenta = rs.getInt(1);
        }
        else {
        cantCuenta = 1;
        }
       
       if (cantCuenta !=0) {
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
    
    
    
        //se usa para buscar si un codigo de balance esta al MODIFICAR una cuenta. (si se usa el anterior y el usuario no cambia su codigo de balance no funciona)
         public int buscarCodigoBalanceAlModificar(String codigo) {
         //le paso un codigo de balance y me devuelve el id. Sino esta el codigo me devuelve 0
              Connection conexion = getConnection();
       
        try{
        
        ps = conexion.prepareStatement("select nrocuenta from plandecuentas where codigo= ?");
        ps.setString(1,codigo);
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
    
    
    public DefaultTableModel buscarCuentas(String cuenta) {

        DefaultTableModel modeloTabla = new DefaultTableModel();
        modeloTabla.addColumn("Codigo");
        modeloTabla.addColumn("Descripcion");
        modeloTabla.addColumn("Tipo");
        modeloTabla.addColumn("Nro de Cuenta");
        
        String like = "";
     
        if (!"".equals(cuenta)) {
        //like = "where descripcion like '" +cuenta+ "%' ";    
        like = "where descripcion like '%" +cuenta+ "%' "; 
        }

        Connection conexion = getConnection();
       
        try{
        
        ps = conexion.prepareStatement("select codigo,descripcion,tipo,nrocuenta from plandecuentas " + like + " order by codigo asc");
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
    
    
    public void ReportePlanDeCuentas(){
      
        Connection conexion = getConnection();
        
        try{
  
        JasperReport reporte = (JasperReport) JRLoader.loadObject(getClass().getResource("/Reportes/planCuentas.jasper"));
        
        Map parametro = new HashMap();
        
        parametro.put("nombreEmpresa", Conexion.getNombreBase());
        
        
        JasperPrint imprimirReporte = JasperFillManager.fillReport(reporte, parametro,conexion);
        //JasperPrint imprimirReporte = JasperFillManager.fillReport(reporte, null,conexion);
        
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
    
    //DEVUELVE LA CUENTA CON EL ID QUE LE PASAMOS POR PARAMETRO
    public Cuenta getCuenta(int idCuenta){
        
        
 
        Connection conexion = getConnection();
         Cuenta cuenta = new Cuenta();
       
        try{
        
        ps = conexion.prepareStatement("select * from plandecuentas where nrocuenta = ?");
        ps.setInt(1,idCuenta);
        rs = ps.executeQuery();
       
        
         while (rs.next()){
            Object fila [] = new Object[7]; // guardo un registro
            
            for (int i = 0;i < 7; i++) {
                fila[i] = rs.getObject(i+1);
               // System.out.println(rs.getObject(5));
                
            }
            
            
           cuenta.setCodigo((String) fila[0]);
           cuenta.setNroCuenta((int) fila[1]);
           cuenta.setDescripcion((String) fila[2]);
           cuenta.setTipo((int) fila[3]);
           cuenta.setNivel((int) fila[4]);
           cuenta.setInflacion((int) fila[5]);
           cuenta.setActivo((int) fila[6]);
            
            
         }
         
            
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
      return cuenta;  
    }
    
    
    
    
    
}
