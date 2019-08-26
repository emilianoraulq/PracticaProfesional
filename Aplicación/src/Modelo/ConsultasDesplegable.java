
package Modelo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import javax.swing.table.DefaultTableModel;


public class ConsultasDesplegable extends Conexion {
    PreparedStatement ps;
    ResultSet rs;  
    
    public DefaultTableModel llenarTablaProvincias() {
     DefaultTableModel modeloTabla = new DefaultTableModel();
        
        
        PreparedStatement ps = null;
        ResultSet rs = null;
        Connection conexion = getConnection();
        
        try {
    
            ps = conexion.prepareStatement("select id,provincia from provincias");
            rs = ps.executeQuery(); //obtengo los resultados de la consulta
            
            modeloTabla.addColumn("Codigo");
            modeloTabla.addColumn("Nombre");
            
              ResultSetMetaData rsmd = rs.getMetaData();
            int cantColumnas = rsmd.getColumnCount();//son 2

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
    
        public DefaultTableModel buscarProvincias(String provincia) {

        DefaultTableModel modeloTabla = new DefaultTableModel();
        modeloTabla.addColumn("Codigo");
        modeloTabla.addColumn("Nombre");
 
        String like = "where provincia like '" +provincia+ "%' ";
   
        
        Connection conexion = getConnection();
       
        try{
        
        ps = conexion.prepareStatement("select * from provincias " + like);
        rs = ps.executeQuery();
        
          ResultSetMetaData rsmd = rs.getMetaData();
            int cantColumnas = rsmd.getColumnCount();
        
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
        
        
        
        
        
        
  public DefaultTableModel llenarTablaPerfiles() {
             
        DefaultTableModel modeloTabla = new DefaultTableModel();
        
        
        PreparedStatement ps = null;
        ResultSet rs = null;
        Connection conexion = getConnection();
        
        try {
    
            ps = conexion.prepareStatement("select * from perfiles");
            rs = ps.executeQuery(); //obtengo los resultados de la consulta
            
            modeloTabla.addColumn("ID");
            modeloTabla.addColumn("Descripcion");
         
            
            
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
  
  
  
  
  
  
  public DefaultTableModel buscarPerfiles(String perfil) {

        DefaultTableModel modeloTabla = new DefaultTableModel();
        modeloTabla.addColumn("ID");
        modeloTabla.addColumn("Descripcion");
 
        String like = "where descripcion like '" +perfil+ "%' ";
   
        
        Connection conexion = getConnection();
       
        try{
        
        ps = conexion.prepareStatement("select * from perfiles " + like);
        rs = ps.executeQuery();
        
          ResultSetMetaData rsmd = rs.getMetaData();
            int cantColumnas = rsmd.getColumnCount();
        
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
        
  
  
  
  
  
  
  
  public DefaultTableModel llenarTablaActividades() {
             
        DefaultTableModel modeloTabla = new DefaultTableModel();
        
        
        PreparedStatement ps = null;
        ResultSet rs = null;
        Connection conexion = getConnection();
        
        try {
    
            ps = conexion.prepareStatement("select * from actividades");
            rs = ps.executeQuery(); //obtengo los resultados de la consulta
            
            modeloTabla.addColumn("ID");
            modeloTabla.addColumn("Descripcion");
         
            
            
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
  
  
  
  
  
  
  
  public DefaultTableModel buscarActividades(String actividad) {

        DefaultTableModel modeloTabla = new DefaultTableModel();
        modeloTabla.addColumn("ID");
        modeloTabla.addColumn("Descripcion");
 
        String like = "where descripcion like '" +actividad+ "%' ";
   
        
        Connection conexion = getConnection();
       
        try{
        
        ps = conexion.prepareStatement("select * from actividades " + like);
        rs = ps.executeQuery();
        
          ResultSetMetaData rsmd = rs.getMetaData();
            int cantColumnas = rsmd.getColumnCount();
        
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
  
  
  
  public DefaultTableModel llenarTablaEmpresas() {
             
        DefaultTableModel modeloTabla = new DefaultTableModel();
        
        
       Conexion con=new Conexion();
        con.setBaseUrl("practico1");
        con.setNombreBase("practico1");
        Connection conexion;
        conexion=con.getConnection();
        
        try {
    
            ps = conexion.prepareStatement("SELECT SCHEMA_NAME FROM INFORMATION_SCHEMA.SCHEMATA WHERE ((SCHEMA_NAME<>'sakila')and(SCHEMA_NAME<>'world')and(SCHEMA_NAME<>'sys')and(SCHEMA_NAME<>'performance_schema')and(SCHEMA_NAME<>'information_schema')and(SCHEMA_NAME<>'mysql'))");
            rs = ps.executeQuery(); //obtengo los resultados de la consulta
            
            modeloTabla.addColumn("Empresas");
            
         
            
            
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
  
  
  
  
  
  public DefaultTableModel buscarEmpresas(String empresa) {

        DefaultTableModel modeloTabla = new DefaultTableModel();
        modeloTabla.addColumn("Empresa");
    
 
        String like = "where SCHEMA_NAME like '" +empresa+ "%' ";
   
        
        Connection conexion = getConnection();
       
        try{
        
        ps = conexion.prepareStatement("SELECT SCHEMA_NAME FROM INFORMATION_SCHEMA.SCHEMATA " + like);
        rs = ps.executeQuery();
        
          ResultSetMetaData rsmd = rs.getMetaData();
            int cantColumnas = rsmd.getColumnCount();
        
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
  
  
  
      public DefaultTableModel llenarTablaPlanDeCuentas() {
     DefaultTableModel modeloTabla = new DefaultTableModel();
        
        
        PreparedStatement ps = null;
        ResultSet rs = null;
        Connection conexion = getConnection();
        
        try {
    
            ps = conexion.prepareStatement("select codigo,nrocuenta,descripcion from plandecuentas where activo = 1 order by codigo asc");
            rs = ps.executeQuery(); //obtengo los resultados de la consulta
            
            modeloTabla.addColumn("Codigo");
            modeloTabla.addColumn("Nro Cuenta");
            modeloTabla.addColumn("Descripcion");
            
              ResultSetMetaData rsmd = rs.getMetaData();
            int cantColumnas = rsmd.getColumnCount();//son 3

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
      
      
      
      public DefaultTableModel buscarCuenta(String cuenta) {

        DefaultTableModel modeloTabla = new DefaultTableModel();
        modeloTabla.addColumn("Codigo");
        modeloTabla.addColumn("Nro Cuenta");
        modeloTabla.addColumn("Descripcion");
    
 
        String like = "where descripcion like '" +cuenta+ "%' ";
   
        
        Connection conexion = getConnection();
       
        try{
        
        ps = conexion.prepareStatement("select codigo,nrocuenta,descripcion from plandecuentas " + like);
        rs = ps.executeQuery();
        
          ResultSetMetaData rsmd = rs.getMetaData();
            int cantColumnas = rsmd.getColumnCount();
        
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
      
      
     public Cuenta getCuenta(int idcuenta) {
 
        Connection conexion = getConnection();
        Cuenta cuenta = new Cuenta();
       
        try{
        
        ps = conexion.prepareStatement("select * from plandecuentas where nrocuenta = ?");
        ps.setInt(1,idcuenta);
        rs = ps.executeQuery();
       
            ResultSetMetaData rsmd = rs.getMetaData();
            int cantColumnas = rsmd.getColumnCount();

           while (rs.next()){
            Object fila [] = new Object[cantColumnas]; // guardo un registro
            
            for (int i = 0;i < cantColumnas; i++) {
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
           
             
           

          return cuenta;  
  
            
        } catch(Exception ex) {
        System.err.println("Error" + ex);
         return cuenta;
        }
        finally {
        
            try{
            conexion.close();
            } catch (Exception ex) {
             System.err.println("Error" + ex);
            }
        }
     }  
      
      
      
      
      
      
      
            public DefaultTableModel llenarTablaSecciones() {
     DefaultTableModel modeloTabla = new DefaultTableModel();
        
        
        PreparedStatement ps = null;
        ResultSet rs = null;
        Connection conexion = getConnection();
        
        try {
    
            ps = conexion.prepareStatement("select idseccion,nombreseccion from secciones");
            rs = ps.executeQuery(); //obtengo los resultados de la consulta
            
            modeloTabla.addColumn("Id");
            modeloTabla.addColumn("Nombre");
            
              ResultSetMetaData rsmd = rs.getMetaData();
            int cantColumnas = rsmd.getColumnCount();//son 2

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
            
    
          public DefaultTableModel llenarTablaSucursales() {
     DefaultTableModel modeloTabla = new DefaultTableModel();
        
        
        PreparedStatement ps = null;
        ResultSet rs = null;
        Connection conexion = getConnection();
        
        try {
    
            ps = conexion.prepareStatement("select idsucursal,nombresucursal from sucursales");
            rs = ps.executeQuery(); //obtengo los resultados de la consulta
            
            modeloTabla.addColumn("Id");
            modeloTabla.addColumn("Nombre");
            
              ResultSetMetaData rsmd = rs.getMetaData();
            int cantColumnas = rsmd.getColumnCount();//son 2

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
  
          
          
        public DefaultTableModel buscarSeccion(String seccion) {

        DefaultTableModel modeloTabla = new DefaultTableModel();
        modeloTabla.addColumn("Id");
        modeloTabla.addColumn("Nombre");
    
        String like = "where nombreseccion like '" +seccion+ "%' ";
   
        
        Connection conexion = getConnection();
       
        try{
        
        ps = conexion.prepareStatement("select idseccion,nombreseccion from secciones " + like);
        rs = ps.executeQuery();
        
          ResultSetMetaData rsmd = rs.getMetaData();
            int cantColumnas = rsmd.getColumnCount();
        
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
        
        
        
        
        public DefaultTableModel buscarSucursal(String sucursal) {

        DefaultTableModel modeloTabla = new DefaultTableModel();
        modeloTabla.addColumn("Id");
        modeloTabla.addColumn("Nombre");
    
        String like = "where nombresucursal like '" +sucursal+ "%' ";
   
        
        Connection conexion = getConnection();
       
        try{
        
        ps = conexion.prepareStatement("select idsucursal,nombresucursal from sucursales " + like);
        rs = ps.executeQuery();
        
          ResultSetMetaData rsmd = rs.getMetaData();
            int cantColumnas = rsmd.getColumnCount();
        
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
       
               
               
               
      
      
      
      
        
        
        
        
}
