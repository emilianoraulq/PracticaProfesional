/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

import com.mysql.jdbc.Connection;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author erqui
 */
public class ConsultasUsuario extends Conexion {
    
    PreparedStatement ps;
    ResultSet rs;
    
    
    
    public boolean insertar (Usuario usuario){
        Connection conexion= getConnection();
        
        try{
            
            ps=conexion.prepareStatement("insert into usuarios (nombre,apellido,nick,dni,id_perfil,contraseña) values (?,?,?,?,?,?)");
            ps.setString(1, usuario.getNombre());
            ps.setString(2, usuario.getApellido());
            ps.setString(3, usuario.getNick());
            ps.setString(4, usuario.getDni());
            ps.setInt(5, usuario.getId_perfil());
            ps.setString(6, usuario.getContraseña());
            
            int resultado =ps.executeUpdate();
            
            if (resultado>0){
                return true;
            }
            else{
                return false;
            }
            
            
        }
        catch(Exception ex){
            System.err.println("Error"+ex);
            return false;
            
        }
        
        finally{
            try{
                conexion.close();
                
            }
            catch(Exception ex){
                System.err.println("Error"+ex);
                
            }
        }
        
        
    }
    
    
    
     public boolean buscarNickUsuario(Usuario usuario) {
         //DEVUELVE TRUE SI EL NOMBRE DE LA PROVINCIA YA ESTA INGRESADO EN LA BBDD
 
        Connection conexion = getConnection();
       
        try{
        
        ps = conexion.prepareStatement("select count(id) from usuarios where nick = ?");
        ps.setString(1,usuario.getNick());
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

    public DefaultTableModel llenarTablaUsuarios() {
    DefaultTableModel modeloTabla = new DefaultTableModel();
        
        
        PreparedStatement ps = null;
        ResultSet rs = null;
        Connection conexion = getConnection();
        
        try {
    
            ps = conexion.prepareStatement("select usuarios.id,nombre,apellido,nick,dni,contraseña,perfiles.id,perfiles.descripcion from usuarios inner join perfiles on usuarios.id_perfil = perfiles.id");
            rs = ps.executeQuery(); //obtengo los resultados de la consulta
            
            modeloTabla.addColumn("ID");
            modeloTabla.addColumn("Nombre");
            modeloTabla.addColumn("Apellido");
            modeloTabla.addColumn("Nick");
            modeloTabla.addColumn("DNI");
            modeloTabla.addColumn("Contraseña");
            modeloTabla.addColumn("ID Perfil");
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
    

    public int buscarIdUsuario(Usuario usuario) {
        //me pasan el nombre de una provincia y me devuelve el id. Sino existe me devuelve un 0
        Connection conexion = getConnection();
       
        try{
        
        ps = conexion.prepareStatement("select id from usuarios where nombre = ?");
        ps.setString(1,usuario.getNombre());
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
    

    public boolean modificar(Usuario usuario){
         Connection conexion = getConnection();
        
        try{
        
        ps = conexion.prepareStatement("update usuarios set nombre = ?,apellido = ?,nick = ?,dni = ?,id_perfil = ?,contraseña = ? where id = ?");
        ps.setString(1, usuario.getNombre());
        ps.setString(2, usuario.getApellido());
        ps.setString(3, usuario.getNick());
        ps.setString(4, usuario.getDni());
        ps.setInt(5, usuario.getId_perfil());
        ps.setString(6, usuario.getContraseña());
        ps.setInt(7, usuario.getId());
        
        
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
 
    
     public boolean eliminar(Usuario usuario) {

        Connection conexion = getConnection();
        
        try{
         
       
        
            //le pregunto si realmente quiere eliminar la provincia
        int chequeo = JOptionPane.showConfirmDialog(null, "Está seguro que desea eliminar al usuario seleccionado?");
        if (chequeo == JOptionPane.YES_OPTION) {

        ps = conexion.prepareStatement("delete from usuarios where id =?");
        ps.setInt(1, usuario.getId());
        int resultado = ps.executeUpdate();
        
        if (resultado > 0) {
            
            JOptionPane.showMessageDialog(null, "Usuario eliminado con exito");
            return true;
        }
        else {
        JOptionPane.showMessageDialog(null, "Error. El usuario no se pudo eliminar");
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
             return false;
            }
        }
        
        return false;
    }

     
     public boolean existePermiso(String perfil, String actividad) {
        //me pasan el nombre de una provincia y me devuelve el id. Sino existe me devuelve un 0
        Connection conexion = getConnection();
       
        try{
        
        ps = conexion.prepareStatement("SELECT * FROM permisos JOIN actividades ON (id_actividad=actividades.id) JOIN perfiles ON (id_perfil_permisos=perfiles.id) WHERE (perfiles.descripcion='"+perfil+"') and (actividades.descripcion='"+actividad+"')");
        //ps.setString(1,usuario.getNombre());
        rs = ps.executeQuery();
        int id;
        
    
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
     
  
     
     public boolean BuscarCargarUsuario(Usuario usuario) {

        //PreparedStatement ps = null;
        //ResultSet rs = null;
        Connection conexion = getConnection();
        
        
        //prueba
        //Conexion con =new Conexion();
       //con.setBaseUrl("nueva");
        //Connection conexion=con.getConnection();
        
        
        
        
        
        //JOptionPane.showMessageDialog(null, "Datos conexion en consultasUsuario "+Conexion.getURL());

        String sql = "SELECT * FROM usuarios WHERE nick=?";

        try {

            ps = conexion.prepareStatement(sql);
            ps.setString(1, usuario.getNick());
            rs = ps.executeQuery();

            if (rs.next()) {
          
                usuario.setId(rs.getInt("id"));
                usuario.setNombre(rs.getString("nombre"));
                usuario.setApellido(rs.getString("apellido"));
                usuario.setNick(rs.getString("nick"));
                usuario.setDni(rs.getString("dni"));
                usuario.setId_perfil(rs.getInt("id_perfil"));
                usuario.setContraseña(rs.getString("contraseña"));
                return true;
            }
            else{
                 
            return false;}
            
        } catch (SQLException e) {

            System.err.println(e);
            return false;

        } finally {
            try {
                conexion.close();

            } catch (SQLException e) {
                System.err.println(e);

            }
        }

    }
    
     public String ObtenerPerfil(Usuario usuario) {

        PreparedStatement ps = null;
        ResultSet rs = null;
        Connection conexion = getConnection();

        String sql = "SELECT * FROM usuarios JOIN perfiles ON (perfiles.id=usuarios.id_perfil) WHERE nick=?";
        String cadena="";

        try {

            ps = conexion.prepareStatement(sql);
            ps.setString(1, usuario.getNick());
            rs = ps.executeQuery();

            if (rs.next()) {
          
                cadena=(rs.getString("descripcion"));
                
                
                return cadena;
            }
            else{
                 
            return cadena;}
            
        } catch (SQLException e) {

            System.err.println(e);
            return cadena;

        } finally {
            try {
                conexion.close();

            } catch (SQLException e) {
                System.err.println(e);

            }
        }

    }
     
 
     public String buscarPerfil(Usuario usuario) {
 
        Connection conexion = getConnection();
       
        try{
        
        ps = conexion.prepareStatement("select descripcion from perfiles where id = ?");
        ps.setInt(1,usuario.getId_perfil());
        rs = ps.executeQuery();
        String perfil;
        
    
       if (rs.next()) {
        perfil = rs.getString(1);
        }
        else {
        perfil = "Perfil inexistente";
        }
       
       return perfil;
    
            
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
 
     
      public boolean existeContraseña(String nick, String contraseña) {
        //busca coincidencias entre nick y contraseña
        Connection conexion = getConnection();
       
        try{
        
        ps = conexion.prepareStatement("SELECT * FROM usuarios WHERE (nick='"+nick+"') and (contraseña='"+contraseña+"')");
  
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
      
      
      
    public boolean insertarEnAuditoriaUsuarios(Usuario usuario, String accion) throws ParseException, UnknownHostException {
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
 
        java.sql.Connection conexion = getConnection();
        
        try{
        
        ps = conexion.prepareStatement("insert into auditoria_usuarios (id_usuario,nombre,apellido,nick,dni,contraseña,id_perfil,fecha,ip,accion) values (?,?,?,?,?,?,?,?,?,?)");
        ps.setInt(1, usuario.getId());
        ps.setString(2, usuario.getNombre());
        ps.setString(3, usuario.getApellido());
        ps.setString(4, usuario.getNick());
        ps.setString(5, usuario.getDni());
        ps.setString(6, usuario.getContraseña());
        ps.setInt(7, usuario.getId_perfil());
        ps.setTimestamp(8, fecha2);
        ps.setString(9, ip);
        ps.setString(10, accion);
        
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
    
    public int buscarIdUsuario2(Usuario usuario) {
        //me pasan el nombre de una provincia y me devuelve el id. Sino existe me devuelve un 0
        java.sql.Connection conexion = getConnection();
       
        try{
        
        ps = conexion.prepareStatement("select id from usuarios where nick = ?");
        ps.setString(1,usuario.getNick());
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
