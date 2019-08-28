
package Modelo;

import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
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
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;


public class ConsultasAuditoria extends Conexion{
     PreparedStatement ps;
     ResultSet rs;
     

    public DefaultTableModel consultarProvincia(Provincia provincia, Usuario usuario,Date desde, Date hasta) {
        
        DefaultTableModel modeloTabla = new DefaultTableModel();
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        //String like = "where nombre like '" +provincia.getNombre()+ "%' ";
        
        //si el campo ID viene vacio me trae todo por defecto
        int idProvincia = provincia.getId();
        String where;
        
        if(idProvincia ==0) {
        where = "";
        }
        else {
        where = " where id_provincia = ? ";
        }
        
        //busco entre dos fechas
        String between = " and date(fecha) between '" + desde + "' and '" + hasta + "'";
        String order = " order by fecha desc";

        
        Connection conexion = getConnection();
       
        try{

        ps = conexion.prepareStatement("select id_provincia,auditoria_provincias.nombre,fecha,ip,accion,usuario,nick from auditoria_provincias inner join usuarios on usuarios.id = auditoria_provincias.usuario " + where + between + order);
        if (idProvincia !=0) {
        ps.setInt(1, provincia.getId());
        }
       
        rs = ps.executeQuery();
        
        modeloTabla.addColumn("Id_Prov");
        modeloTabla.addColumn("Provincia");
        modeloTabla.addColumn("Fecha");
        modeloTabla.addColumn("IP");
        modeloTabla.addColumn("Accion");
        modeloTabla.addColumn("Id_usuario");
        modeloTabla.addColumn("Nick");
        

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
    
    public DefaultTableModel consultarCliente(Cliente cliente, Usuario usuario,Date desde, Date hasta) {
        
        DefaultTableModel modeloTabla = new DefaultTableModel();
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        //String like = "where nombre like '" +provincia.getNombre()+ "%' ";
        
        //si el campo ID viene vacio me trae todo por defecto
        int idCliente = cliente.getId();
        String where;
       
        if(idCliente ==0) {
        where = "";
        }
        else {
        where = " where id_cliente = ? ";
        }
        
        //busco entre dos fechas
        String between = " and date(fecha) between '" + desde + "' and '" + hasta + "'";
        String order = " order by fecha desc";
        
        Connection conexion = getConnection();
       
        try{

        ps = conexion.prepareStatement("select id_cliente,auditoria_clientes.nombre,auditoria_clientes.apellido,auditoria_clientes.dni,domicilio,nroProvincia,fecha,ip,accion,usuario_id,nick from auditoria_clientes inner join usuarios on usuarios.id = auditoria_clientes.usuario_id " + where + between + order);

        //si el usuario indica un ID lo buscamos, sino por defecto, es decir idcliente = 0, trae todos
        if (idCliente !=0) {
        ps.setInt(1, cliente.getId());
        }
       
        rs = ps.executeQuery();
        
        modeloTabla.addColumn("Id_Cliente");
        modeloTabla.addColumn("Nombre");
        modeloTabla.addColumn("Apellido");
        modeloTabla.addColumn("DNI");
        modeloTabla.addColumn("Domicilio");
        modeloTabla.addColumn("ID Provincia");
        modeloTabla.addColumn("Fecha");
        modeloTabla.addColumn("IP");
        modeloTabla.addColumn("Accion");
        modeloTabla.addColumn("Id_usuario");
        modeloTabla.addColumn("Nick");
        

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

    public DefaultTableModel consultarUsuario(Usuario usuario, Date desde, Date hasta) {
        
        DefaultTableModel modeloTabla = new DefaultTableModel();
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        //String like = "where nombre like '" +provincia.getNombre()+ "%' ";
        
        //si el campo ID viene vacio me trae todo por defecto
        int idUsuario = usuario.getId();
        String where;
        String between;
        
        if(idUsuario == 0) {
        where = " where ";
        between = " date(fecha) between '" + desde + "' and '" + hasta + "'";
        }
        else {
        where = " where id_usuario = ? ";
        between = " and date(fecha) between '" + desde + "' and '" + hasta + "'";
        }
        
        //busco entre dos fechas
        //String between = " and date(fecha) between '" + desde + "' and '" + hasta + "'";
        String order = " order by fecha desc";

        
        Connection conexion = getConnection();
       
        try{
       // ps = conexion.prepareStatement("select auditoria_clientes.usuario_id " + where + between + order);

        ps = conexion.prepareStatement("select * from auditoria_usuarios " + where + between + order);
        if (idUsuario !=0) {
        ps.setInt(1, usuario.getId());
        }
       
        rs = ps.executeQuery();
        
        modeloTabla.addColumn("ID Usuario");
        modeloTabla.addColumn("Nombre");
        modeloTabla.addColumn("Apellido");
        modeloTabla.addColumn("Nick");
        modeloTabla.addColumn("DNI");
        modeloTabla.addColumn("Contraseña");
        modeloTabla.addColumn("ID Perfil");
        modeloTabla.addColumn("Fecha");
        modeloTabla.addColumn("IP");
        modeloTabla.addColumn("Accion");
        
        

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
    
    public DefaultTableModel filtrarProvincia(Provincia provincia, Usuario usuario,Date desde, Date hasta) {
        
        DefaultTableModel modeloTabla = new DefaultTableModel();
        PreparedStatement ps = null;
        ResultSet rs = null;
 
        String and2 = "";
        
        String and = " and usuarios.id= ? ";
        if(provincia.getId() != 0) {
         and2 = " and id_provincia = ? ";
        }
       
        
        
        //busco entre dos fechas
        String where = "where date(fecha) between '" + desde + "' and '" + hasta + "'";
        String order = " order by fecha desc";

        
        Connection conexion = getConnection();
       
        try{

        ps = conexion.prepareStatement("select id_provincia,auditoria_provincias.nombre,fecha,ip,accion,usuario,nick from auditoria_provincias inner join usuarios on usuarios.id = auditoria_provincias.usuario " + where + and + and2 + order);
       // System.out.println("select id_provincia,nombre,fecha,ip,accion,usuario,nick from auditoria_provincias inner join usuarios on usuarios.id_usuario = auditoria_provincias.usuario " + where + and);
                                                                                                              
        ps.setInt(1, usuario.getId());
        if (provincia.getId() !=0) {
        ps.setInt(2, provincia.getId());
        }
  
       
        rs = ps.executeQuery();
        
        modeloTabla.addColumn("Id_Prov");
        modeloTabla.addColumn("Provincia");
        modeloTabla.addColumn("Fecha");
        modeloTabla.addColumn("IP");
        modeloTabla.addColumn("Accion");
        modeloTabla.addColumn("Id_usuario");
        modeloTabla.addColumn("Nick");
        

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
    
    public DefaultTableModel filtrarCliente(Cliente cliente, Usuario usuario,Date desde, Date hasta) {
        
        DefaultTableModel modeloTabla = new DefaultTableModel();
        PreparedStatement ps = null;
        ResultSet rs = null;
 
        String and2 = "";
        String and = " and usuarios.id = ? ";
        if (cliente.getId() !=0) {
        and2 = " and id_cliente = ? ";
        }
        
     
        
        //busco entre dos fechas
        String where = "where date(fecha) between '" + desde + "' and '" + hasta + "'";
        String order = " order by fecha desc";

        
        Connection conexion = getConnection();
       
        try{

        ps = conexion.prepareStatement("select id_cliente,auditoria_clientes.nombre,auditoria_clientes.apellido,auditoria_clientes.dni,domicilio,nroProvincia,fecha,ip,accion,usuario_id,nick from auditoria_clientes inner join usuarios on usuarios.id = auditoria_clientes.usuario_id " + where + and + and2 + order);
       // System.out.println("select id_provincia,nombre,fecha,ip,accion,usuario,nick from auditoria_provincias inner join usuarios on usuarios.id_usuario = auditoria_provincias.usuario " + where + and);
                                                                                                              
        ps.setInt(1, usuario.getId());
        if (cliente.getId() !=0) {
        ps.setInt(2, cliente.getId());
        }
        
       
        rs = ps.executeQuery();
        
        modeloTabla.addColumn("Id_Cliente");
        modeloTabla.addColumn("Nombre");
        modeloTabla.addColumn("Apellido");
        modeloTabla.addColumn("DNI");
        modeloTabla.addColumn("Domicilio");
        modeloTabla.addColumn("Id_Provincia");
        modeloTabla.addColumn("Fecha");
        modeloTabla.addColumn("IP");
        modeloTabla.addColumn("Accion");
        modeloTabla.addColumn("Id_usuario");
        modeloTabla.addColumn("Nick");
        

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
        
    public void ReporteAuditoriaProvinciasPorProvinciaYUsuario(Provincia provincia, Usuario usuario, Timestamp desde, Timestamp hasta){
      
        Connection conexion = getConnection();
        
        try{
  
        JasperReport reporte = (JasperReport) JRLoader.loadObject(getClass().getResource("/Reportes/auditoriaProvincias.jasper"));
        
        Map parametro = new HashMap();
        parametro.put("fecha_desde", desde);
        parametro.put("fecha_hasta", hasta);
        parametro.put("id_provincia", provincia.getId());
        parametro.put("id_usuario", usuario.getId());
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
    
   
    
    public void ReporteAuditoriaClientesPorClienteYUsuario(Cliente cliente, Usuario usuario, Timestamp desde, Timestamp hasta){
      
        Connection conexion = getConnection();
        
        try{
  
        JasperReport reporte = (JasperReport) JRLoader.loadObject(getClass().getResource("/Reportes/auditoriaClientes.jasper"));
        
        Map parametro = new HashMap();
        parametro.put("fecha_desde", desde);
        parametro.put("fecha_hasta", hasta);
        parametro.put("id_cliente", cliente.getId());
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
    
        public void ReporteAuditoriaUsuarios(Usuario usuario, Timestamp desde, Timestamp hasta){
      
        Connection conexion = getConnection();
        
        try{
  
        JasperReport reporte = (JasperReport) JRLoader.loadObject(getClass().getResource("/Reportes/auditoriaUsuarios.jasper"));
        
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
          
    public void crearExcelProvincias (Provincia provincia, Usuario usuario, Timestamp desde,  Timestamp hasta) {
        Workbook libro = new XSSFWorkbook();
    
        Sheet hoja = (Sheet) libro.createSheet("Auditoria de Provincias");
        
        //creo un arreglo con el nombre de las columnas del Excel
        String cabeceras [] = new String [] {"Id Provincia","Nombre","Fecha","IP", "Accion","Id Usuario","Nick"};
        
        Row filaCabeceras = hoja.createRow(0);
        //asigno cada nombre de columna con la celda correspondiente
        for (int i=0; i < cabeceras.length; i++){
        Cell celda = filaCabeceras.createCell(i);
        celda.setCellValue(cabeceras[i]);
        }
        
        //busco entre dos fechas
        String where = "where date(fecha) between '" + desde + "' and '" + hasta + "'";
        String and = "";
        String and2 = "";
        String order = "order by fecha desc";
        
        if (provincia.getId() != 0) {
        and = " and id_provincia = ? ";
        }
            if (usuario.getId() != 0) {
            and2 = " and usuarios.id = ? ";
            }
        
        //System.out.println(where);
      //  System.out.println(and);
       // System.out.println(and2);
   

        
        //consulto la base de datos
        Connection conexion = getConnection();
        
        try{
        ps = conexion.prepareStatement("select id_provincia,auditoria_provincias.nombre,fecha,ip,accion,usuario,nick from auditoria_provincias inner join usuarios on usuarios.id = auditoria_provincias.usuario " + where + and + and2 + order);
        //ps = conexion.prepareStatement("select idClientes,Nombre,Apellido,DNI,Domicilio,nroProvincia,provincia from clientes inner join provincias on clientes.nroProvincia = provincias.id where idClientes between ? and ?");
        
            if (provincia.getId() != 0) {
                ps.setInt(1, provincia.getId());
                if (usuario.getId() != 0) {
                    ps.setInt(2, usuario.getId());
                }
            }
            
            if (provincia.getId() == 0) {
                if (usuario.getId() != 0) {
                    ps.setInt(1, usuario.getId());
                }
            }
            
        
       
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
        FileOutputStream archivo = new FileOutputStream("AuditoriaProvincias.xlsx");
        libro.write(archivo);
        archivo.close();
        
        } catch (Exception ex) {
        System.out.println("Error" + ex);
        }
        
        //abre el reporte automaticamente
        String file = new String("C:\\Users\\Schefer\\Desktop\\pruebagit\\PracticaProfesional\\Aplicación\\AuditoriaProvincias.xlsx"); 
        
        try{ 
        //definiendo la ruta en la propiedad file
        Runtime.getRuntime().exec("cmd /c start "+file);
     
        }catch(IOException e){
        e.printStackTrace();
        } 
        
    
    } 
    
       public void crearExcelClientes (Cliente cliente, Usuario usuario, Timestamp desde,  Timestamp hasta) {
        Workbook libro = new XSSFWorkbook();
    
        Sheet hoja = (Sheet) libro.createSheet("Auditoria de Clientes");
        
        //creo un arreglo con el nombre de las columnas del Excel
        String cabeceras [] = new String [] {"Id Cliente","Nombre","Apellido","dni","domicilio","Id Provincia","Fecha","IP", "Accion","Id Usuario","Nick"};
        
        Row filaCabeceras = hoja.createRow(0);
        //asigno cada nombre de columna con la celda correspondiente
        for (int i=0; i < cabeceras.length; i++){
        Cell celda = filaCabeceras.createCell(i);
        celda.setCellValue(cabeceras[i]);
        }
        
 
        
        //busco entre dos fechas
        String where = "where date(fecha) between '" + desde + "' and '" + hasta + "'";
        String and = "";
        String and2 = "";
        String order = "order by fecha desc";
        
        if (cliente.getId() != 0) {
        and = " and id_cliente = ? ";
        }
            if (usuario.getId() != 0) {
            and2 = " and usuarios.id = ? ";
            }
      
        //System.out.println(where);
      //  System.out.println(and);
       // System.out.println(and2);
   

        
        //consulto la base de datos
        Connection conexion = getConnection();
        
        try{
        ps = conexion.prepareStatement("select id_cliente,auditoria_clientes.nombre,auditoria_clientes.apellido,auditoria_clientes.dni,auditoria_clientes.domicilio,nroProvincia,fecha,ip,accion,usuario_id,nick from auditoria_clientes inner join usuarios on usuarios.id= auditoria_clientes.usuario_id " + where + and + and2 + order);
        //ps = conexion.prepareStatement("select idClientes,Nombre,Apellido,DNI,Domicilio,nroProvincia,provincia from clientes inner join provincias on clientes.nroProvincia = provincias.id where idClientes between ? and ?");
        
            if (cliente.getId() != 0) {
                ps.setInt(1, cliente.getId());
                if (usuario.getId() != 0) {
                    ps.setInt(2, usuario.getId());
                }
            }
 
            if (cliente.getId() == 0) {
                if (usuario.getId() != 0) {
                    ps.setInt(1, usuario.getId());
                }
            }
            
           
        
       
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

                if (i == 1 || i ==2 || i == 3 || i == 4 || i == 6 || i == 7 || i == 8 || i == 10) {
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
        FileOutputStream archivo = new FileOutputStream("AuditoriaClientes.xlsx");
        libro.write(archivo);
        archivo.close();
        
        } catch (Exception ex) {
        System.out.println("Error" + ex);
        }
        
        //abre el reporte automaticamente
        String file = new String("C:\\Users\\Schefer\\Desktop\\pruebagit\\PracticaProfesional\\Aplicación\\AuditoriaClientes.xlsx"); 
       
        try{ 
        //definiendo la ruta en la propiedad file
        Runtime.getRuntime().exec("cmd /c start "+file);
     
        }catch(IOException e){
        e.printStackTrace();
        } 
        
    
    }
       
        public void crearExcelUsuarios (Usuario usuario, Timestamp desde,  Timestamp hasta) {
        Workbook libro = new XSSFWorkbook();
    
        Sheet hoja = (Sheet) libro.createSheet("Auditoria de Usuarios");
        
        //creo un arreglo con el nombre de las columnas del Excel
        String cabeceras [] = new String [] {"Id Usuario","Nombre", "Apellido", "Nick", "DNI" , "Id Perfil" , "Fecha","IP", "Accion"};
        
        Row filaCabeceras = hoja.createRow(0);
        //asigno cada nombre de columna con la celda correspondiente
        for (int i=0; i < cabeceras.length; i++){
        Cell celda = filaCabeceras.createCell(i);
        celda.setCellValue(cabeceras[i]);
        }
        
        //busco entre dos fechas
        String where = "where date(fecha) between '" + desde + "' and '" + hasta + "'";
        String and = "";
        String order = "order by fecha desc";
        
        if (usuario.getId() != -1) {
        and = " and id_usuario = ? ";
            
        }
        //System.out.println(where);
      //  System.out.println(and);
       // System.out.println(and2);
   

        
        //consulto la base de datos
        Connection conexion = getConnection();
        
        try{
        ps = conexion.prepareStatement("select * from auditoria_usuarios " + where + and  + order);
        //ps = conexion.prepareStatement("select idClientes,Nombre,Apellido,DNI,Domicilio,nroProvincia,provincia from clientes inner join provincias on clientes.nroProvincia = provincias.id where idClientes between ? and ?");
        
            if (usuario.getId() != -1) {
                ps.setInt(1, usuario.getId());
                
            }
        
       
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

                if (i == 1 || i ==2 || i == 3 || i == 4 || i == 5 || i ==7 || i==8 || i==9) {
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
        FileOutputStream archivo = new FileOutputStream("AuditoriaUsuarios.xlsx");
        libro.write(archivo);
        archivo.close();
        
        } catch (Exception ex) {
        System.out.println("Error" + ex);
        }
        
        //abre el reporte automaticamente
        String file = new String("C:\\Users\\Schefer\\Desktop\\pruebagit\\PracticaProfesional\\Aplicación\\AuditoriaUsuarios.xlsx"); 
        
        try{ 
        //definiendo la ruta en la propiedad file
        Runtime.getRuntime().exec("cmd /c start "+file);
     
        }catch(IOException e){
        e.printStackTrace();
        } 
        
    
    }    
       
       
}
