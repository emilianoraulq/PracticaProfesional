/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

import Vista.formLoading;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.JOptionPane;

/**
 *
 * @author erqui
 */
public class ConsultasNuevaEmpresa extends Conexion{
    PreparedStatement ps;
    ResultSet rs;
    
    
    
    
    public boolean crearBD (String nombre){
        
        

        
        
        
        
       Conexion con=new Conexion();
       
        //JOptionPane.showMessageDialog(null, Conexion.getNombreBase());
        //con.setBaseUrl("nueva");
        //con.setNombreBase("nueva");
        //JOptionPane.showMessageDialog(null, Conexion.getNombreBase());
        //JOptionPane.showMessageDialog(null, Conexion.getURL());
        Connection conexion;
        conexion=con.getConnection();
       
   
        try{
        
        ps = conexion.prepareStatement("CREATE DATABASE "+nombre);
        
        
    
            
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
    
    
    public boolean crearTablaActividades (String base){
       Conexion con=new Conexion();
      
        con.setBaseUrl(base);
        con.setNombreBase(base);
        Connection conexion;
        conexion=con.getConnection();

       
        
        try{
            
           
        
        ps = conexion.prepareStatement("CREATE TABLE `actividades` (`id` int(11) NOT NULL AUTO_INCREMENT,`descripcion` varchar(45) NOT NULL,PRIMARY KEY (`id`)) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;");
        
        
    
            
            int resultado = ps.executeUpdate();//ejecuto la insercion 
            
          
        
        if (resultado == 0) {
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
    
    
    public boolean crearTablaAuditoriaUsuarios (String base){
       
        Conexion con=new Conexion();
        con.setBaseUrl(base);
        con.setNombreBase(base);
        Connection conexion;
        conexion=con.getConnection();
  
       
        try{
            
           
        
        ps = conexion.prepareStatement("CREATE TABLE `auditoria_usuarios` ("
                + "`id_usuario` int(11) DEFAULT NULL,"
                + "`nombre` varchar(45) DEFAULT NULL,"
                + "`apellido` varchar(45) DEFAULT NULL,"
                + "`nick` varchar(45) DEFAULT NULL,"
                + "`dni` varchar(45) DEFAULT NULL,"
                + "`contraseña` varchar(45) DEFAULT NULL,"
                + "`id_perfil` int(11) DEFAULT NULL,"
                + "`fecha` timestamp NULL DEFAULT NULL,"
                + "`ip` varchar(45) DEFAULT NULL,"
                + "`accion` varchar(45) DEFAULT NULL"
                + ") ENGINE=InnoDB DEFAULT CHARSET=latin1;");
        
            
        int resultado = ps.executeUpdate();//ejecuto la insercion 
        
        if (resultado == 0) {
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
    
    
    
    public boolean crearTablaPerfiles (String base){
       
        Conexion con=new Conexion();
        con.setBaseUrl(base);
        con.setNombreBase(base);
        Connection conexion;
        conexion=con.getConnection();
  
       
        try{
            
           
        
        ps = conexion.prepareStatement("CREATE TABLE `perfiles` ("
                + "`id` int(11) NOT NULL AUTO_INCREMENT,"
                + "`descripcion` varchar(45) DEFAULT NULL,"
                + "PRIMARY KEY (`id`)"
                + ") ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;");
        
            
        int resultado = ps.executeUpdate();//ejecuto la insercion 
        
        if (resultado == 0) {
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
    
    
    public boolean crearTablaProvincias (String base){
       
        Conexion con=new Conexion();
        con.setBaseUrl(base);
        con.setNombreBase(base);
        Connection conexion;
        conexion=con.getConnection();
  
       
        try{
            
            
        
        ps = conexion.prepareStatement("CREATE TABLE `provincias` ("
                + "`id` int(11) NOT NULL AUTO_INCREMENT,"
                + "`provincia` varchar(30) DEFAULT NULL,"
                + "PRIMARY KEY (`id`)"
                + ") ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;");
        
            
        int resultado = ps.executeUpdate();//ejecuto la insercion 
        
        if (resultado == 0) {
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
    
    
    public boolean crearTablaAuditoriaClientes (String base){
       
        Conexion con=new Conexion();
        con.setBaseUrl(base);
        con.setNombreBase(base);
        Connection conexion;
        conexion=con.getConnection();
  
       
        try{
            
            
        
        ps = conexion.prepareStatement("CREATE TABLE `auditoria_clientes` ("
                + "`id_cliente` int(11) DEFAULT NULL,"
                + "`nombre` varchar(45) DEFAULT NULL,"
                + "`apellido` varchar(45) DEFAULT NULL,"
                + "`dni` varchar(45) DEFAULT NULL,"
                + "`domicilio` varchar(45) DEFAULT NULL,"
                + "`nroProvincia` int(11) DEFAULT NULL,"
                + "`fecha` timestamp NULL DEFAULT NULL,"
                + "`ip` varchar(45) DEFAULT NULL,"
                + "`accion` varchar(45) DEFAULT NULL,"
                + "`usuario_id` int(11) DEFAULT NULL,"
                + "KEY `usuario_idx` (`usuario_id`),"
                + "CONSTRAINT `usuario_id` FOREIGN KEY (`usuario_id`) REFERENCES `usuarios` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION"
                + ") ENGINE=InnoDB DEFAULT CHARSET=latin1;");
        
            
        int resultado = ps.executeUpdate();//ejecuto la insercion 
        
        if (resultado == 0) {
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
    
    
    public boolean crearTablaUsuarios (String base){
       
        Conexion con=new Conexion();
        con.setBaseUrl(base);
        con.setNombreBase(base);
        Connection conexion;
        conexion=con.getConnection();
  
       
        try{
            
            
        
        ps = conexion.prepareStatement("CREATE TABLE `usuarios` ("
                + "`id` int(11) NOT NULL AUTO_INCREMENT,"
                + "`nombre` varchar(45) DEFAULT NULL,"
                + "`apellido` varchar(45) DEFAULT NULL,"
                + "`nick` varchar(30) DEFAULT NULL,"
                + "`dni` int(11) DEFAULT NULL,"
                + "`contraseña` varchar(45) DEFAULT NULL,"
                + "`id_perfil` int(11) DEFAULT NULL,"
                + "PRIMARY KEY (`id`),"
                + "KEY `id_perfil_idx` (`id_perfil`),"
                + "CONSTRAINT `id_perfil` FOREIGN KEY (`id_perfil`) REFERENCES `perfiles` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION"
                + ") ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;");
        
            
        int resultado = ps.executeUpdate();//ejecuto la insercion 
        
        if (resultado == 0) {
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
    
    
    
    public boolean crearTablaAuditoriaProvincias (String base){
       
        Conexion con=new Conexion();
        con.setBaseUrl(base);
        con.setNombreBase(base);
        Connection conexion;
        conexion=con.getConnection();
  
       
        try{
            
            
        
        ps = conexion.prepareStatement("CREATE TABLE `auditoria_provincias` ("
                + "`id_provincia` int(11) DEFAULT NULL,"
                + "`nombre` varchar(45) DEFAULT NULL,"
                + "`fecha` timestamp NULL DEFAULT NULL,"
                + "`ip` varchar(45) DEFAULT NULL,"
                + "`accion` varchar(15) DEFAULT NULL,"
                + "`usuario` int(11) DEFAULT NULL,"
                + "KEY `usuario_idx` (`usuario`),"
                + "CONSTRAINT `usuario` FOREIGN KEY (`usuario`) REFERENCES `usuarios` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION"
                + ") ENGINE=InnoDB DEFAULT CHARSET=latin1;");
        
            
        int resultado = ps.executeUpdate();//ejecuto la insercion 
        
        if (resultado == 0) {
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
    
    
    
    public boolean crearTablaClientes (String base){
       
        Conexion con=new Conexion();
        con.setBaseUrl(base);
        con.setNombreBase(base);
        Connection conexion;
        conexion=con.getConnection();
  
       
        try{
            
            
        
        ps = conexion.prepareStatement("CREATE TABLE `clientes` ("
                + "`idClientes` int(11) NOT NULL AUTO_INCREMENT,"
                + "`Nombre` varchar(30) DEFAULT NULL,"
                + "`Apellido` varchar(30) DEFAULT NULL,"
                + "`DNI` varchar(20) DEFAULT NULL,"
                + "`Domicilio` varchar(30) DEFAULT NULL,"
                + "`nroProvincia` int(11) DEFAULT NULL,"
                + "PRIMARY KEY (`idClientes`),"
                + "KEY `nroProvincia_idx` (`nroProvincia`),"
                + "CONSTRAINT `nroProvincia` FOREIGN KEY (`nroProvincia`) REFERENCES `provincias` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION"
                + ") ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;");
        
            
        int resultado = ps.executeUpdate();//ejecuto la insercion 
        
        if (resultado == 0) {
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
    
    
    
    public boolean crearTablaPermisos (String base){
       
        Conexion con=new Conexion();
        con.setBaseUrl(base);
        con.setNombreBase(base);
        Connection conexion;
        conexion=con.getConnection();
        
        
  
       
        try{
            
            
        
        ps = conexion.prepareStatement("CREATE TABLE `permisos` ("
                + "`id` int(11) NOT NULL AUTO_INCREMENT,"
                + "`id_perfil_permisos` int(11) NOT NULL,"
                + "`id_actividad` int(11) NOT NULL,"
                + "PRIMARY KEY (`id`),"
                + "KEY `id_perfil_permisos_idx` (`id_perfil_permisos`),"
                + "KEY `id_actividad_idx` (`id_actividad`),"
                + "CONSTRAINT `id_actividad` FOREIGN KEY (`id_actividad`) REFERENCES `actividades` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,"
                + "CONSTRAINT `id_perfil_permisos` FOREIGN KEY (`id_perfil_permisos`) REFERENCES `perfiles` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION"
                + ") ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;");
        
            
        int resultado = ps.executeUpdate();//ejecuto la insercion 
        
        
        
        if (resultado == 0) {
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
    
    public boolean crearTablaDatosEmpresa (String base){
       
        Conexion con=new Conexion();
        con.setBaseUrl(base);
        con.setNombreBase(base);
        Connection conexion;
        conexion=con.getConnection();
  
       
        try{
            
            
        
        ps = conexion.prepareStatement("CREATE TABLE `datos_empresa` ("
                + "`id` int(11) NOT NULL AUTO_INCREMENT,"
                + "`nombre` varchar(50) DEFAULT NULL,"
                + "`razon_social` varchar(50) DEFAULT NULL,"
                + "`cuit` varchar(30) DEFAULT NULL,"
                + "`contraseña` varchar(50) DEFAULT NULL,"
                + "PRIMARY KEY (`id`)"
                + ") ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;");
        
            
        int resultado = ps.executeUpdate();//ejecuto la insercion 
        
        if (resultado == 0) {
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
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
     //me pasan el nombre de una bd y retorno true si existe. Falso en caso contrario.
    public boolean existeEmpresa (String nombre){
       
        Connection conexion = getConnection();
       
        try{
        
        ps = conexion.prepareStatement("SELECT SCHEMA_NAME FROM INFORMATION_SCHEMA.SCHEMATA WHERE (SCHEMA_NAME = '"+nombre+"')");
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
    
    
    
    
    public boolean eliminarEmpresa (String nombre){
       Conexion con=new Conexion();
       
        //JOptionPane.showMessageDialog(null, Conexion.getNombreBase());
        //con.setBaseUrl("nueva");
        //con.setNombreBase("nueva");
        //JOptionPane.showMessageDialog(null, Conexion.getNombreBase());
        //JOptionPane.showMessageDialog(null, Conexion.getURL());
        Connection conexion;
        conexion=con.getConnection();
       
   
        try{
        
        ps = conexion.prepareStatement("DROP DATABASE "+nombre);
        
        
    
            
            int resultado = ps.executeUpdate();//ejecuto la insercion 
            
            //JOptionPane.showMessageDialog(null, resultado);
        
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
    
    
    
    
    
    
    public boolean cargarTablas (String base){
        
        PreparedStatement ps1, ps2, ps3, ps4;
        int resultado1, resultado2, resultado3;
        
        //Conexion con=new Conexion();
        Conexion.setBaseUrl(base);
        Conexion.setNombreBase(base);
        Connection conexion;
        conexion=getConnection();
        
        try{
            
            ps1=conexion.prepareStatement("insert into perfiles (descripcion) values ('administrador'),('auditor')");
            ps2=conexion.prepareStatement("insert into actividades (descripcion) values ('altaClientes'),('modificarClientes'),('eliminarClientes'),('altaProvincias'),('modificarProvincias'),('eliminarProvincias')");
            ps3=conexion.prepareStatement("insert into usuarios (nombre,apellido,nick,dni,contraseña,id_perfil) values ('Usuario','Administrador','admin',0,'admin',1)");
            
            
            resultado1=ps1.executeUpdate();
            resultado2=ps2.executeUpdate();
            resultado3=ps3.executeUpdate();
            
            if ((resultado1>0)&&(resultado2>0)&&(resultado3>0)){
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
    
    
    
    public boolean establecerConexion (String nombre){
        
        

        
        
        
        
       Conexion con=new Conexion();
       
        //JOptionPane.showMessageDialog(null, Conexion.getNombreBase());
        //con.setBaseUrl("nueva");
        //con.setNombreBase("nueva");
        
        Connection conexion;
        conexion=con.getConnection();
       
   
        try{
        
        ps = conexion.prepareStatement("use "+nombre);
        
        
        
        
    
            
            int resultado = ps.executeUpdate();//ejecuto la insercion 
            
            JOptionPane.showMessageDialog(null,resultado);
            
            JOptionPane.showMessageDialog(null,"MENSAJE DE LA CONSULTA"+ Conexion.getNombreBase());
        JOptionPane.showMessageDialog(null,"MENSAJE DE LA CONSULTA"+ Conexion.getURL());
            
          
        
        if (resultado == 0) {
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
    
    
    
    
    
    public boolean insertarDatosEmpresa (String nombre, String razon_social, String cuit, String contraseña, String base){
         Conexion.setBaseUrl(base);
        Conexion.setNombreBase(base);
        Connection conexion;
        conexion=getConnection();
        
        try{
            
            ps=conexion.prepareStatement("insert into datos_empresa (nombre,razon_social,cuit,contraseña) values (?,?,?,?)");
            ps.setString(1, nombre);
            ps.setString(2, razon_social);
            ps.setString(3, cuit);
            ps.setString(4, contraseña);
           
            
            
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
    
    
    
    
}
