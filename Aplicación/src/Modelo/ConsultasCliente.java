/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

import java.io.FileOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JOptionPane;
import static javax.swing.WindowConstants.DISPOSE_ON_CLOSE;
import javax.swing.table.DefaultTableModel;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;



/**
 *
 * @author Schefer
 */
public class ConsultasCliente extends Conexion{
    PreparedStatement ps;
    ResultSet rs;
    
    public void crearExcel (int id1, int id2) {
        Workbook libro = new XSSFWorkbook();
    
        Sheet hoja = (Sheet) libro.createSheet("Reporte Clientes");
        
        //creo un arreglo con el nombre de las columnas del Excel
        String cabeceras [] = new String [] {"Id","Nombre","Apellido","DNI", "Domicilio","IdProvincia","Provincia"};
        
        Row filaCabeceras = hoja.createRow(0);
        //asigno cada nombre de columna con la celda correspondiente
        for (int i=0; i < cabeceras.length; i++){
        Cell celda = filaCabeceras.createCell(i);
        celda.setCellValue(cabeceras[i]);
        }
        
        //consulto la base de datos
        Connection conexion = getConnection();
        
        try{
        
        ps = conexion.prepareStatement("select idClientes,Nombre,Apellido,DNI,Domicilio,nroProvincia,provincia from clientes inner join provincias on clientes.nroProvincia = provincias.id where idClientes between ? and ?");
        ps.setInt(1,id1);
        ps.setInt(2,id2);
        rs = ps.executeQuery();
        
        //consigo el numero de columnas
        int nroColumnas = rs.getMetaData().getColumnCount();
        //comienza en la fila 1, la 0 tiene las cabeceras
        int nroFila = 1;
        
        while (rs.next()) {
        Row filaDatos = hoja.createRow(nroFila);
        
        //pasamos celda por celda al excel
            for (int i = 0; i < nroColumnas; i++){
            Cell celda = filaDatos.createCell(i);

                if (i == 1 || i ==2 || i == 3 || i == 4 || i == 6) {
                celda.setCellValue(rs.getString(i+1));
                }
                else {
                celda.setCellValue(rs.getInt(i+1));
                }
                //ajusto las columnas del Excel
                hoja.autoSizeColumn(i);
            }
        
        nroFila++;
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
        
        try {
        FileOutputStream archivo = new FileOutputStream("ReporteClientes.xlsx");
        libro.write(archivo);
        archivo.close();
        
        } catch (Exception ex) {
        System.out.println("Error" + ex);
        }
        
        //abre el reporte automaticamente
        String file = new String("C:\\Users\\erqui\\Desktop\\TRABAJOHECHO\\proyecto\\nueva_prueba\\PracticaProfesional\\Prueba1\\ReporteClientes.xlsx"); 
        
        try{ 
        //definiendo la ruta en la propiedad file
        Runtime.getRuntime().exec("cmd /c start "+file);
     
        }catch(IOException e){
        e.printStackTrace();
        } 
        
    
    }

    
    public DefaultTableModel llenarTablaClientes() {
    DefaultTableModel modeloTabla = new DefaultTableModel();
        
        
        PreparedStatement ps = null;
        ResultSet rs = null;
        Connection conexion = getConnection();
        
        try {
    
            ps = conexion.prepareStatement("select idClientes,Nombre,Apellido,DNI,Domicilio,nroProvincia,provincia from clientes inner join provincias on clientes.nroProvincia = provincias.id");
            rs = ps.executeQuery(); //obtengo los resultados de la consulta
            
            modeloTabla.addColumn("ID");
            modeloTabla.addColumn("Nombre");
            modeloTabla.addColumn("Apellido");
            modeloTabla.addColumn("DNI");
            modeloTabla.addColumn("Domicilio");
            modeloTabla.addColumn("ID Prov");
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
    
     public DefaultTableModel buscarClientes(Cliente cliente, int indice) {
           DefaultTableModel modeloTabla = new DefaultTableModel();
            modeloTabla.addColumn("ID");
            modeloTabla.addColumn("Nombre");
            modeloTabla.addColumn("Apellido");
            modeloTabla.addColumn("DNI");
            modeloTabla.addColumn("Domicilio");
            modeloTabla.addColumn("ID Prov");
            modeloTabla.addColumn("Provincia");
        
        String like ="";
        if (indice == 0) {
         like = "where idClientes ='" +cliente.getId()+ "'";
        }
        else if (indice == 1) {
         like = "where nombre like '" +cliente.getNombre()+ "%' ";
        }
        else if (indice == 2) {
        like = "where apellido like '" +cliente.getApellido()+ "%' ";
        }
        
        else if (indice == 3) {  
         like ="";
        }
        
        
        Connection conexion = getConnection();
       
        try{
        
        ps = conexion.prepareStatement("select idClientes,Nombre,Apellido,DNI,Domicilio,nroProvincia,provincia from clientes inner join provincias on clientes.nroProvincia = provincias.id " + like);
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
     
     public boolean eliminarCliente(Cliente cliente){
             Connection conexion = getConnection();
             
        int chequeo = JOptionPane.showConfirmDialog(null, "Esta seguro de eliminar el cliente? Por favor confirme.");
        
        if (chequeo == JOptionPane.YES_OPTION) {
        
        try{

        ps = conexion.prepareStatement("delete from clientes where idClientes =?");
        ps.setInt(1, cliente.getId());
        int resultado = ps.executeUpdate();
        
        if (resultado > 0) {
            
            JOptionPane.showMessageDialog(null, "Cliente eliminado con exito");
            return true;
        }
        else {
        JOptionPane.showMessageDialog(null, "Error. El cliente no se pudo eliminar");
        return false;
        }

            
        } catch(Exception ex) {
        System.err.println("Error" + ex);
        return false;
        }
        finally {
        
            try{
            conexion.close();
           // return true;
            } catch (Exception ex) {
             System.err.println("Error" + ex);
             return false;
            }
        }
        }
        return false;
     }
     
     public boolean crearCliente (Cliente cliente){
       Connection conexion = getConnection();
        
        try{
        
        ps = conexion.prepareStatement("insert into clientes (Nombre,Apellido,DNI,Domicilio,nroProvincia) values (?,?,?,?,?)");
            ps.setString(1, cliente.getNombre());
            ps.setString(2, cliente.getApellido());
            ps.setString(3, cliente.getDni());
            ps.setString(4, cliente.getDomicilio());
            ps.setInt(5, cliente.getIdProvincia());
            
            int resultado = ps.executeUpdate();//ejecuto la insercion 
        
        if (resultado > 0) {
        return true;
        }
        else {
           // JOptionPane.showInputDialog(null,"Por favor verifique DNI");
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
     
     public boolean modificarCliente(Cliente cliente){
              Connection conexion = getConnection();
        
        try{
        
        ps = conexion.prepareStatement("update clientes set Nombre = ?, Apellido = ?, DNI = ?, Domicilio = ?,nroProvincia = ? where idClientes = ?");
        ps.setString(1, cliente.getNombre());
        ps.setString(2, cliente.getApellido());
        ps.setString(3, cliente.getDni());
        ps.setString(4, cliente.getDomicilio());
        ps.setInt(5, cliente.getIdProvincia());
        ps.setInt(6, cliente.getId());
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
     
     public boolean buscarDniAlInsertar(Cliente cliente) {
         //DEVUELVE TRUE SI EL DNI YA ESTA INGRESADO EN LA BBDD
 
        Connection conexion = getConnection();
       
        try{
        
        ps = conexion.prepareStatement("select count(idClientes) from clientes where DNI = ?");
        ps.setInt(1,Integer.parseInt(cliente.getDni()));
        rs = ps.executeQuery();
        int cantDni;
        
    
       if (rs.next()) {
        cantDni = rs.getInt(1);
        }
        else {
        cantDni = 1;
        }
       
       if (cantDni !=0) {
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
     
     public int buscarDniAlModificar(int dni) {
         //le paso un dni y me devuelve el id. Sino esta el dni me devuelve 0
              Connection conexion = getConnection();
       
        try{
        
        ps = conexion.prepareStatement("select idClientes from clientes where dni= ?");
        ps.setInt(1,dni);
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
     
     public boolean buscarIdProvincia(Cliente cliente) {
 
        Connection conexion = getConnection();
       
        try{
        
        ps = conexion.prepareStatement("select count(id) from provincias where id = ?");
        ps.setInt(1,cliente.getIdProvincia());
        rs = ps.executeQuery();
        int cantId;
        
    
       if (rs.next()) {
        cantId = rs.getInt(1);
        }
        else {
        cantId = 1;
        }
       
       if (cantId !=0) {
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
     
     public void llamarReporteClientes(int desde, int hasta){
      
        Connection conexion = getConnection();
        try{
        

        //JasperReport reporte2 = null;
        //String ruta = "src\\Modelo\\reporteClientes.jasper";
        //reporte2 = (JasperReport) JRLoader.loadObjectFromFile(ruta);
        
        JasperReport reporte = (JasperReport) JRLoader.loadObject(getClass().getResource("/Reportes/reporteClientes.jasper"));
        
        
        Map parametro = new HashMap();
        parametro.put("id_cliente1", desde);
        parametro.put("id_cliente2", hasta);
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
     
     public String buscarProvincia(Cliente cliente) {
 
        Connection conexion = getConnection();
       
        try{
        
        ps = conexion.prepareStatement("select provincia from provincias where id = ?");
        ps.setInt(1,cliente.getIdProvincia());
        rs = ps.executeQuery();
        String provincia;
        
    
       if (rs.next()) {
        provincia = rs.getString(1);
        }
        else {
        provincia = "Provincia inexistente";
        }
       
       return provincia;
    
            
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
     
     
           public boolean insertarEnAuditoriaClientes(Cliente cliente, String accion,Usuario usuario) throws ParseException, UnknownHostException {
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
        
        ps = conexion.prepareStatement("insert into auditoria_clientes(id_cliente,nombre,apellido,dni,domicilio,nroProvincia,fecha,ip,accion,usuario_id) values (?,?,?,?,?,?,?,?,?,?)");
        ps.setInt(1, cliente.getId());
        ps.setString(2, cliente.getNombre());
        ps.setString(3, cliente.getApellido());
        ps.setString(4, cliente.getDni());
        ps.setString(5, cliente.getDomicilio());
        ps.setInt(6, cliente.getIdProvincia());
        ps.setTimestamp(7, fecha2);
        ps.setString(8, ip);
        ps.setString(9, accion);
        ps.setInt(10, usuario.getId());
        

        
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
           
        public int buscarIdCliente(Cliente cliente) {
        //me pasan el nombre de un cliente y me devuelve el id. Sino existe me devuelve un 0
        Connection conexion = getConnection();
       
        try{
        
        ps = conexion.prepareStatement("select idClientes from clientes where nombre = ? and apellido = ? and DNI = ? and Domicilio = ? and nroProvincia = ?");

        ps.setString(1,cliente.getNombre());
        ps.setString(2,cliente.getApellido());
        ps.setString(3,cliente.getDni());
        ps.setString(4,cliente.getDomicilio());
        ps.setInt(5,cliente.getIdProvincia());
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
     
}
