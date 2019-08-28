/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

import com.mysql.jdbc.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author erqui
 */
public class ConsultasPerfiles extends Conexion {
    
    PreparedStatement ps;
    ResultSet rs;
    
    
    
    
    
    
//----------------------------------------------CONSULTAS PERFILES--------------------------------------------------//
    
        public boolean insertarPerfil (Perfil perfil){
        Connection conexion= getConnection();
        
        try{
            
            ps=conexion.prepareStatement("insert into perfiles (descripcion) values (?)");
            ps.setString(1, perfil.getDescripcion());
           
            
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
       
       
       
       
       
       
       public boolean modificarPerfil(Perfil perfil){
         Connection conexion = getConnection();
        
        try{
        
        ps = conexion.prepareStatement("select count(id) from permisos where id_perfil_permisos = ?");
        ps.setInt(1,perfil.getId());
        rs = ps.executeQuery();
        int cantPermisos;
        
        if (rs.next()) {
        cantPermisos = rs.getInt(1);
        }
        else {
        cantPermisos = 1;
        }
        
        if (cantPermisos !=0) {
        return false;
        }
        
        else{
     
        ps = conexion.prepareStatement("update perfiles set descripcion = ? where id = ?");
        ps.setString(1, perfil.getDescripcion());
        ps.setInt(2, perfil.getId());
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
       
       
       public boolean eliminarPerfil(Perfil perfil) {

        Connection conexion = getConnection();
        
        try{
           //aca tiene que chequear que no haya permisos en ese perfil
        ps = conexion.prepareStatement("select count(id) from permisos where id_perfil_permisos = ?");
        ps.setInt(1,perfil.getId());
        rs = ps.executeQuery();
        int cantPermisos;
        
        if (rs.next()) {
        cantPermisos = rs.getInt(1);
        }
        else {
        cantPermisos = 1;
        }
        
        if (cantPermisos !=0) {
        JOptionPane.showMessageDialog(null, "No se puede eliminar el perfil, hay permisos asociados al perfil");
        return false;
        }
        
        else
        {
            return true;
            /*
            //le pregunto si realmente quiere eliminar el perfil
        int chequeo = JOptionPane.showConfirmDialog(null, "Esta seguro de eliminar el perfil?");
        if (chequeo == JOptionPane.YES_OPTION) {

        ps = conexion.prepareStatement("delete from perfiles where id =?");
        ps.setInt(1, perfil.getId());
        int resultado = ps.executeUpdate();
        
        if (resultado > 0) {
            
            JOptionPane.showMessageDialog(null, "Perfil eliminado con exito");
            return true;
        }
        else {
        JOptionPane.showMessageDialog(null, "Error. El perfil no se pudo eliminar");
        return false;
        }
        
        }
        */
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
       
 
    }
       
        public boolean eliminarPerfil2(Perfil perfil) {

        Connection conexion = getConnection();
        
        try{
           //aca tiene que chequear que no haya usuarios con ese perfil
        ps = conexion.prepareStatement("select count(id) from usuarios where id_perfil = ?");
        ps.setInt(1,perfil.getId());
        rs = ps.executeQuery();
        int cantUsuarios;
        
        if (rs.next()) {
        cantUsuarios = rs.getInt(1);
        }
        else {
        cantUsuarios = 1;
        }
        
        if (cantUsuarios !=0) {
        JOptionPane.showMessageDialog(null, "No se puede eliminar el perfil, hay usuarios asociados al perfil");
        return false;
        }
        
        else
        {
            //le pregunto si realmente quiere eliminar el perfil
        int chequeo = JOptionPane.showConfirmDialog(null, "Esta seguro de eliminar el perfil?");
        if (chequeo == JOptionPane.YES_OPTION) {

        ps = conexion.prepareStatement("delete from perfiles where id =?");
        ps.setInt(1, perfil.getId());
        int resultado = ps.executeUpdate();
        
        if (resultado > 0) {
            
            JOptionPane.showMessageDialog(null, "Perfil eliminado con exito");
            return true;
        }
        else {
        JOptionPane.showMessageDialog(null, "Error. El perfil no se pudo eliminar");
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
            return true;
            } catch (Exception ex) {
             System.err.println("Error" + ex);
             return false;
            }
        }
        
 
    }
       
       
       
       
       
       
        public boolean buscarDescripcionPerfil(Perfil perfil) {
         //DEVUELVE TRUE SI EL NOMBRE DE LA PROVINCIA YA ESTA INGRESADO EN LA BBDD
 
        Connection conexion = getConnection();
       
        try{
        
        ps = conexion.prepareStatement("select count(id) from perfiles where descripcion = ?");
        ps.setString(1,perfil.getDescripcion());
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
        
        
        
        
        public int buscarIdPerfil(Perfil perfil) {
        //me pasan el nombre de una provincia y me devuelve el id. Sino existe me devuelve un 0
        Connection conexion = getConnection();
       
        try{
        
        ps = conexion.prepareStatement("select id from perfiles where descripcion = ?");
        ps.setString(1,perfil.getDescripcion());
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
       
       
       
       
       //----------------------------------------------CONSULTAS ACTIVIDADES--------------------------------------------------//
       
       
        public boolean insertarActividad (Actividad actividad){
        Connection conexion= getConnection();
        
        try{
            
            ps=conexion.prepareStatement("insert into actividades (descripcion) values (?)");
            ps.setString(1, actividad.getDescripcion());
           
            
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
       
       
       
       
       
       
       public boolean modificarActividad(Actividad actividad){
         Connection conexion = getConnection();
        
        try{
        
        ps = conexion.prepareStatement("select count(id) from permisos where id_actividad = ?");
        ps.setInt(1,actividad.getId());
        rs = ps.executeQuery();
        int cantPermisos;
        
        if (rs.next()) {
        cantPermisos = rs.getInt(1);
        }
        else {
        cantPermisos = 1;
        }
        
        if (cantPermisos !=0) {
        return false;
        }
        
        else{
     
        ps = conexion.prepareStatement("update actividades set descripcion = ? where id = ?");
        ps.setString(1, actividad.getDescripcion());
        ps.setInt(2, actividad.getId());
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
       
       
       public boolean eliminarActividad(Actividad actividad) {

        Connection conexion = getConnection();
        
        try{
           //aca tiene que chequear que no haya clientes en esas provincias
        ps = conexion.prepareStatement("select count(id) from permisos where id_actividad = ?");
        ps.setInt(1,actividad.getId());
        rs = ps.executeQuery();
        int cantPermisos;
        
        if (rs.next()) {
        cantPermisos = rs.getInt(1);
        }
        else {
        cantPermisos = 1;
        }
        
        if (cantPermisos !=0) {
        JOptionPane.showMessageDialog(null, "No se puede eliminar la actividad, hay permisos asociados a la actividad");
        return false;
        }
        
        else
        {
            //le pregunto si realmente quiere eliminar el perfil
        int chequeo = JOptionPane.showConfirmDialog(null, "Esta seguro de eliminar la actividad?");
        if (chequeo == JOptionPane.YES_OPTION) {

        ps = conexion.prepareStatement("delete from actividades where id =?");
        ps.setInt(1, actividad.getId());
        int resultado = ps.executeUpdate();
        
        if (resultado > 0) {
            
            JOptionPane.showMessageDialog(null, "Actividad eliminada con exito");
            return true;
        }
        else {
        JOptionPane.showMessageDialog(null, "Error. La actividad no se pudo eliminar");
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
            return true;
            } catch (Exception ex) {
             System.err.println("Error" + ex);
             return false;
            }
        }
        
 
    }
       
       
        public boolean buscarDescripcionActividad(Actividad actividad) {
         //DEVUELVE TRUE SI EL NOMBRE DE LA PROVINCIA YA ESTA INGRESADO EN LA BBDD
 
        Connection conexion = getConnection();
       
        try{
        
        ps = conexion.prepareStatement("select count(id) from actividades where descripcion = ?");
        ps.setString(1,actividad.getDescripcion());
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
        
        
        public int buscarIdActividad(Actividad actividad) {
        //me pasan el nombre de una provincia y me devuelve el id. Sino existe me devuelve un 0
        Connection conexion = getConnection();
       
        try{
        
        ps = conexion.prepareStatement("select id from actividades where descripcion = ?");
        ps.setString(1,actividad.getDescripcion());
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
       
       
       
       
       
       //----------------------------------------------CONSULTAS PERMISOS--------------------------------------------------//
        
        
        
        
    public DefaultTableModel llenarTablaPermisos() {
    DefaultTableModel modeloTabla = new DefaultTableModel();
        
        
        PreparedStatement ps = null;
        ResultSet rs = null;
        Connection conexion = getConnection();
        
        try {
    
            ps = conexion.prepareStatement("select permisos.id,permisos.id_perfil_permisos,perfiles.descripcion,permisos.id_actividad,actividades.descripcion from permisos inner join perfiles on (permisos.id_perfil_permisos = perfiles.id) inner join actividades on (permisos.id_actividad=actividades.id)");
            rs = ps.executeQuery(); //obtengo los resultados de la consulta
            
            modeloTabla.addColumn("ID");
            modeloTabla.addColumn("ID Perfil");
            modeloTabla.addColumn("Descripción Perfil");
            modeloTabla.addColumn("ID Actividad");
            modeloTabla.addColumn("Descripcion Actividad");
           
            
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
    
    
    
    
    
    
    
    public boolean eliminarPermiso(Permiso permiso){
             Connection conexion = getConnection();
             
        int chequeo = JOptionPane.showConfirmDialog(null, "Esta seguro de eliminar el permiso?");
        if (chequeo == JOptionPane.YES_OPTION) {
        
        try{

        ps = conexion.prepareStatement("delete from permisos where id =?");
        ps.setInt(1, permiso.getId());
        int resultado = ps.executeUpdate();
        
        if (resultado > 0) {
            
            JOptionPane.showMessageDialog(null, "Permiso eliminado con exito");
            return true;
        }
        else {
        JOptionPane.showMessageDialog(null, "Error. El permiso no se pudo eliminar");
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
        return true;
     }
    
    
    
    public boolean insertarPermiso (Permiso permiso){
       Connection conexion = getConnection();
        
        try{
        
        ps = conexion.prepareStatement("insert into permisos (id_perfil_permisos,id_actividad) values (?,?)");
            ps.setInt(1, permiso.getId_perfil_permisos());
            ps.setInt(2, permiso.getId_actividad());
            
            
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
    
    
    
    
    public String buscarPerfilPermiso(Perfil perfil) {
 
        Connection conexion = getConnection();
       
        try{
        
        ps = conexion.prepareStatement("select descripcion from perfiles where id = ?");
        ps.setInt(1,perfil.getId());
        rs = ps.executeQuery();
        String resultado;
        
    
       if (rs.next()) {
        resultado = rs.getString(1);
        }
        else {
        resultado = "Perfil inexistente";
        }
       
       return resultado;
    
            
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
    
    
    
    public String buscarActividadPermiso(Actividad actividad) {
 
        Connection conexion = getConnection();
       
        try{
        
        ps = conexion.prepareStatement("select descripcion from actividades where id = ?");
        ps.setInt(1,actividad.getId());
        rs = ps.executeQuery();
        String resultado;
        
    
       if (rs.next()) {
        resultado = rs.getString(1);
        }
        else {
        resultado = "Actividad inexistente";
        }
       
       return resultado;
    
            
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
    
    
    
    
    public boolean existePermisoId(int perfil, int actividad) {
        //me pasan el nombre de una provincia y me devuelve el id. Sino existe me devuelve un 0
        Connection conexion = getConnection();
       
        try{
        
        ps = conexion.prepareStatement("SELECT * FROM permisos JOIN actividades ON (id_actividad=actividades.id) JOIN perfiles ON (id_perfil_permisos=perfiles.id) WHERE (perfiles.id='"+perfil+"') and (actividades.id='"+actividad+"')");
        //ps.setString(1,usuario.getNombre());
        rs = ps.executeQuery();
        //int id;
        
    
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
    
    
    
    public boolean modificarPermiso(Permiso permiso){
         Connection conexion = getConnection();
        
        try{
        
        ps = conexion.prepareStatement("update permisos set id_perfil_permisos = ?,id_actividad = ? where id = ?");
        ps.setInt(1, permiso.getId_perfil_permisos());
        ps.setInt(2, permiso.getId_actividad());
        ps.setInt(3, permiso.getId());
        
        
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
