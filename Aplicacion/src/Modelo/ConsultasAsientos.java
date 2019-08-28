/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.text.DecimalFormat;
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
    
    
    
        public Asiento getAsiento(int nroasiento){
        PreparedStatement ps = null;
        ResultSet rs = null;
        Asiento asiento = new Asiento();
        
        Connection conexion = getConnection(); 

        try {
    
            ps = conexion.prepareStatement("select * from asientos where idasiento = ?" );
            ps.setInt(1, nroasiento);
            rs = ps.executeQuery(); //obtengo los resultados de la consulta
            
            ResultSetMetaData rsmd = rs.getMetaData();
            int cantColumnas = rsmd.getColumnCount();//son 6 columnas
            
            
            while (rs.next()){
            Object fila [] = new Object[cantColumnas]; // guardo un registro
            
            for (int i = 0;i < cantColumnas; i++) {
                fila[i] = rs.getObject(i+1);
               // System.out.println(rs.getObject(5));
                
            }
    
            asiento.setIdasiento((int) fila[0]);
            asiento.setFechacontable((Date) fila[1]);
            asiento.setTipoasiento((int) fila[2]);
            asiento.setOkcarga((int) fila[3]);
            asiento.setRegistrado((int) fila[4]);
            asiento.setInflacion((int) fila[5]);
 
            }
            
        
        } catch (Exception ex) {
        System.err.print("Error" + ex);
        return asiento;
        }
        finally{
            
        try{
            conexion.close();
            } catch (Exception ex) {
             System.err.println("Error" + ex);
            }
        }
      //formato.format(cantidad);  
     
      
      return asiento;  
    
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
            int cantColumnas = rsmd.getColumnCount();//son 6 columnas
            //System.out.println(cantColumnas);

            while (rs.next()){
            Object fila [] = new Object[cantColumnas+1]; // guardo un registro
            
            for (int i = 0;i < cantColumnas; i++) {
                fila[i] = rs.getObject(i+1);
               // System.out.println(rs.getObject(5));
                
           
            }
            
            
            
             if(rs.getInt(5) == 0) {
                   fila[5]=rs.getString(6);  
                   fila[6]=""; 
                }
                
                if(rs.getInt(5) == 1) {
                   fila[5]="";
                   fila[6]=rs.getString(6);  
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
    
    
    
    public String getTotalDebe(int asiento){
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        Connection conexion = getConnection(); 
        
        //DecimalFormat formato = new DecimalFormat("0.00");
        String cantidad = "";
        //formato.format(cantidad);
        
        try {
    
            ps = conexion.prepareStatement("select sum(importe) from renglones where debehaber = 0 and nroasiento = ? " );
            ps.setInt(1, asiento);
            rs = ps.executeQuery(); //obtengo los resultados de la consulta
            
            if (rs.next()) {
            cantidad = rs.getString(1);
               
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
        if (cantidad == null) {
          cantidad = "0.00";   
         // System.out.println("entro en el IF");
        }
        //System.out.println(cantidad);
      return cantidad;  
     }  
    
    
       public String getTotalHaber(int asiento){
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        Connection conexion = getConnection(); 

        String cantidad = "";
  
        try {
    
            ps = conexion.prepareStatement("select sum(importe) from renglones where debehaber = 1 and nroasiento = ? " );
            ps.setInt(1, asiento);
            rs = ps.executeQuery(); //obtengo los resultados de la consulta
            
            if (rs.next()) {
            cantidad = rs.getString(1);
               
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
        
        if (cantidad == null) {
          cantidad = "0.00";   
       
        }
       // System.out.println(cantidad);
      return cantidad;  
     }  

    
    
    public boolean actualizarAsiento(Asiento asiento){
        Connection conexion = getConnection();
        
                
        try{
        
        ps = conexion.prepareStatement("update asientos set fechacontable = ?, tipoasiento = ?, inflacion = ?, okcarga = ? where idasiento = ?");
        ps.setDate(1, asiento.getFechacontable());
        ps.setInt(2, asiento.getTipoasiento());
        ps.setInt(3, asiento.getInflacion());
        ps.setInt(4, asiento.getOkcarga());
        ps.setInt(5, asiento.getIdasiento());
        
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
    
    
        public boolean actualizarRenglon(Renglon renglon){
        Connection conexion = getConnection();
        
                
        try{
        
        ps = conexion.prepareStatement("update renglones set idcuenta = ?, fechavencimiento = ?, fechaoperacion = ?, comprobante = ?, leyenda = ?, debehaber = ?, importe = ?, sucursal = ?, seccion = ? where idrenglon = ? and nroasiento = ?");
        ps.setInt(1, renglon.getNroCuenta());
        ps.setDate(2, renglon.getFechaVencimiento());
        ps.setDate(3, renglon.getFechaOperacion());
        ps.setString(4, renglon.getComprobante());
        ps.setString(5, renglon.getLeyenda());
        ps.setInt(6, renglon.getDebeHaber());
        ps.setDouble(7, renglon.getImporte());
        ps.setInt(8, renglon.getSucursal());
        ps.setInt(9, renglon.getSeccion());
        ps.setInt(10, renglon.getIdRenglon());
        ps.setInt(11, renglon.getNroAsiento());
        
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
          
   
    
    
    
        public Renglon getRenglon(int nroasiento, int idrenglon){
        PreparedStatement ps = null;
        ResultSet rs = null;
        Renglon renglon = new Renglon();
        
        Connection conexion = getConnection(); 

        try {
    
            ps = conexion.prepareStatement("select * from renglones where nroasiento = ? and idrenglon = ? " );
            ps.setInt(1, nroasiento);
            ps.setInt(2, idrenglon);
            rs = ps.executeQuery(); //obtengo los resultados de la consulta
            
            ResultSetMetaData rsmd = rs.getMetaData();
            int cantColumnas = rsmd.getColumnCount();//son 6 columnas
            
            
            while (rs.next()){
            Object fila [] = new Object[cantColumnas]; // guardo un registro
            
            for (int i = 0;i < cantColumnas; i++) {
                fila[i] = rs.getObject(i+1);
               // System.out.println(rs.getObject(5));
                
            }
    
            renglon.setIdRenglon((int) fila[0]);
            renglon.setNroCuenta((int) fila[2]);
            renglon.setFechaVencimiento((Date) fila[3]);
            renglon.setFechaOperacion((Date) fila[4]);
            renglon.setComprobante((String) fila[5]);
            renglon.setLeyenda((String) fila[6]);
            renglon.setDebeHaber((int) fila[7]);
            renglon.setSucursal((int) fila[9]);
            renglon.setSeccion((int) fila[10]);
            
           // renglon.setImporte((double) fila[8]);
            BigDecimal importe;
                importe = new BigDecimal((fila[8].toString()));
           
           renglon.setImporte(Double.parseDouble(importe.toString()));
           
          // System.out.println("el importe es" + importe);
            
            }
            
        
        } catch (Exception ex) {
        System.err.print("Error" + ex);
        return renglon;
        }
        finally{
            
        try{
            conexion.close();
            } catch (Exception ex) {
             System.err.println("Error" + ex);
            }
        }
      //formato.format(cantidad);  
     
      
      return renglon;  
    
     }
        
        
        
        public String getCuentaPlanCuentas(int idcuenta){
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        Connection conexion = getConnection(); 

        String cuenta = "";
  
        try {
    
            ps = conexion.prepareStatement("select descripcion from plandecuentas where nrocuenta = ?" );
            ps.setInt(1, idcuenta);
            rs = ps.executeQuery(); //obtengo los resultados de la consulta
            
            if (rs.next()) {
            cuenta = rs.getString(1);
               
            }

            
        
        } catch (Exception ex) {
        System.err.print("Error" + ex);
        return cuenta;
        }
        finally{
            
        try{
            conexion.close();
            } catch (Exception ex) {
             System.err.println("Error" + ex);
            }
        }
        

      return cuenta;  
     }
        
        
       public boolean eliminarRenglon (int nroAsiento,int idRenglon){
       Connection conexion = getConnection();
        
        try{
        
        ps = conexion.prepareStatement("delete from renglones where idrenglon = ? and nroasiento = ? ");
        ps.setInt(1, idRenglon);
        ps.setInt(2, nroAsiento);
            
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
     
   
      public boolean buscarCuenta(int idcuenta) {
 
        Connection conexion = getConnection();
       
        try{
        
        ps = conexion.prepareStatement("select * from plandecuentas where activo = 1 and tipo = 1 and nrocuenta = ?");
        ps.setInt(1,idcuenta);
        rs = ps.executeQuery();

            if (rs.next()) {
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
      
        public String buscarNombreCuenta(int idcuenta) {
 
        Connection conexion = getConnection();
       
        try{
        
        ps = conexion.prepareStatement("select descripcion from plandecuentas where nrocuenta = ?");
        ps.setInt(1,idcuenta);
        rs = ps.executeQuery();
        String nombre="";

            if (rs.next()) {
                nombre = rs.getString(1);
                return nombre;
            }
            else {
                return "";
            }
  
            
        } catch(Exception ex) {
        System.err.println("Error" + ex);
         return "";
        }
        finally {
        
            try{
            conexion.close();
            } catch (Exception ex) {
             System.err.println("Error" + ex);
            }
        }
     }
        
        
        
        
       public boolean buscarSucursal(int idsucursal) {
 
        Connection conexion = getConnection();
       
        try{
        
        ps = conexion.prepareStatement("select * from sucursales where idsucursal = ?");
        ps.setInt(1,idsucursal);
        rs = ps.executeQuery();

            if (rs.next()) {
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
       
       
        public boolean buscarSeccion(int idseccion) {
 
        Connection conexion = getConnection();
       
        try{
        
        ps = conexion.prepareStatement("select * from secciones where idseccion = ?");
        ps.setInt(1,idseccion);
        rs = ps.executeQuery();

            if (rs.next()) {
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
        
        public Date getFechaInicioEjercicio() {
 
        Connection conexion = getConnection();
        Date fecha;
       
        try{
        
        ps = conexion.prepareStatement("select inicio_ejercicio from datos_empresa");
        rs = ps.executeQuery();

            if (rs.next()) {
                fecha = rs.getDate(1);  
                return fecha;
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
        
        return null;
     } 
        
        
        
        public Date getFechaFinEjercicio() {
 
        Connection conexion = getConnection();
        Date fecha;
       
        try{
        
        ps = conexion.prepareStatement("select fin_ejercicio from datos_empresa");
        rs = ps.executeQuery();

            if (rs.next()) {
                fecha = rs.getDate(1);  
                return fecha;
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
        
        return null;
     }    
        
        
        
        
        
        
        
       public boolean buscarAsiento(int idasiento) {
 
        Connection conexion = getConnection();
       
        try{
        
        ps = conexion.prepareStatement("select * from asientos where idasiento = ?");
        ps.setInt(1,idasiento);
        rs = ps.executeQuery();

            if (rs.next()) {
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
       
       
       
      public void listadoAsientos(int desde, int hasta){
      
        Connection conexion = getConnection();
        try{
         
        JasperReport reporte = (JasperReport) JRLoader.loadObject(getClass().getResource("/Reportes/listadoRenglones.jasper"));
        
        
        Map parametro = new HashMap();
        parametro.put("idasiento1", desde);
        parametro.put("idasiento2", hasta);
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
      
      
      
      
     public boolean confirmarAsiento(Asiento asiento){
        Connection conexion = getConnection();
        
                
        try{
        
        ps = conexion.prepareStatement("update asientos set fechacontable = ?, tipoasiento = ?, inflacion = ?, okcarga = ?, registrado = 1 where idasiento = ?");
        ps.setDate(1, asiento.getFechacontable());
        ps.setInt(2, asiento.getTipoasiento());
        ps.setInt(3, asiento.getInflacion());
        ps.setInt(4, asiento.getOkcarga());
        ps.setInt(5, asiento.getIdasiento());
        
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
