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
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Schefer
 */
public class ConsultasAsientos extends Conexion {
     PreparedStatement ps;
     ResultSet rs;
     
     
     
     //DEVUELVE LA CANTIDAD DE ASIENTOS QUE HAY, SE USA PARA CALCULAR EL SIG NRO DE ASIENTO
     public int cantidadAsientos(){
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        Connection conexion = getConnection(); 
        int cantidad = 0;

        
        try {
    
            ps = conexion.prepareStatement("select count(idasiento) from asientos " );
            rs = ps.executeQuery(); //obtengo los resultados de la consulta
            
            if (rs.next()) {
            cantidad = rs.getInt(1);
               
            }

            
        
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
     
     
     
          //DEVUELVE LA CANTIDAD DE RENGLONES QUE HAY, SE USA PARA CALCULAR EL SIG NRO DE RENGLON
     public int cantidadRenglonesdelAsiento(int asiento){
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        Connection conexion = getConnection(); 
        int cantidad = 0;

        
        try {
    
            ps = conexion.prepareStatement("select count(idrenglon) from renglones where nroasiento = ? " );
            ps.setInt(1, asiento);
            rs = ps.executeQuery(); //obtengo los resultados de la consulta
            
            if (rs.next()) {
            cantidad = rs.getInt(1);
               
            }

            
        
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
     
     
     public boolean generarAsiento (Asiento asiento){
       Connection conexion = getConnection();
        
        try{
        
        ps = conexion.prepareStatement("insert into asientos (idasiento,fechacontable,tipoasiento,okcarga,registrado,inflacion) values (?,?,?,?,?,?)");
            ps.setInt(1, asiento.getIdasiento());
            ps.setDate(2, asiento.getFechacontable());
            ps.setInt(3, asiento.getTipoasiento());
            ps.setInt(4, asiento.getOkcarga());
            ps.setInt(5, asiento.getRegistrado());
            ps.setInt(6, asiento.getInflacion());
            
            int resultado = ps.executeUpdate();//ejecuto la insercion 
        
        if (resultado > 0) {
            return true;
        }
        else{
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
     
     
     
    public boolean ingresarRenglon (Renglon renglon){
       Connection conexion = getConnection();
        
        try{
        
        ps = conexion.prepareStatement("insert into renglones (idrenglon,nroasiento,idcuenta,fechavencimiento,fechaoperacion,comprobante,leyenda,debehaber,importe,sucursal,seccion) values (?,?,?,?,?,?,?,?,?,?,?)");
            ps.setInt(1, renglon.getIdRenglon());
            ps.setInt(2, renglon.getNroAsiento());
            ps.setInt(3, renglon.getNroCuenta());
            ps.setDate(4, renglon.getFechaVencimiento());
            ps.setDate(5, renglon.getFechaOperacion());
            ps.setString(6, renglon.getComprobante());
            ps.setString(7, renglon.getLeyenda());
            ps.setInt(8, renglon.getDebeHaber());
            ps.setDouble(9, renglon.getImporte());
            ps.setInt(10, renglon.getSucursal());
            ps.setInt(11, renglon.getSeccion());
            
            int resultado = ps.executeUpdate();//ejecuto la insercion 
        
        if (resultado > 0) {
            return true;
        }
        else{
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
          
          
    public DefaultTableModel llenarTablaRenglones(int asiento) {
    DefaultTableModel modeloTabla = new DefaultTableModel();
        
        
        PreparedStatement ps = null;
        ResultSet rs = null;
        Connection conexion = getConnection();
        
        try {
    
            ps = conexion.prepareStatement("select idrenglon,idcuenta,descripcion,comprobante,debehaber,importe from renglones inner join plandecuentas on renglones.idcuenta = plandecuentas.nrocuenta where nroasiento = ?");
            ps.setInt(1, asiento);
            rs = ps.executeQuery(); //obtengo los resultados de la consulta
            
            modeloTabla.addColumn("Nro Renglon");
            modeloTabla.addColumn("Nro Cuenta");
            modeloTabla.addColumn("Nombre Cta");
            modeloTabla.addColumn("Comprobante");
            modeloTabla.addColumn("D/H");
            modeloTabla.addColumn("Debe");
            modeloTabla.addColumn("Haber");
            
            ResultSetMetaData rsmd = rs.getMetaData();
            int cantColumnas = rsmd.getColumnCount();//son 7 columnas

            while (rs.next()){
            Object fila [] = new Object[cantColumnas]; // guardo un registro
            
            for (int i = 0;i < 5; i++) {
                fila[i] = rs.getObject(i+1);
                System.out.println(rs.getObject(5));
                if (rs.getObject(5) == "0") {
                fila[5] = rs.getObject(6);    
                }
                else {
                fila[6] = rs.getObject(6);    
                }
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
    
    
    
    public double getTotalDebe(int asiento){
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        Connection conexion = getConnection(); 
        int cantidad = 0;

        
        try {
    
            ps = conexion.prepareStatement("select sum(importe) from renglones where debehaber = 0 and nroasiento = ? " );
            ps.setInt(1, asiento);
            rs = ps.executeQuery(); //obtengo los resultados de la consulta
            
            if (rs.next()) {
            cantidad = rs.getInt(1);
               
            }

            
        
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
    
    
        public double getTotalHaber(int asiento){
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        Connection conexion = getConnection(); 
        int cantidad = 0;

        
        try {
    
            ps = conexion.prepareStatement("select sum(importe) from renglones where debehaber = 1 and nroasiento = ? " );
            ps.setInt(1, asiento);
            rs = ps.executeQuery(); //obtengo los resultados de la consulta
            
            if (rs.next()) {
            cantidad = rs.getInt(1);
               
            }

            
        
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
          
     
     
     
     
}
