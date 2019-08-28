/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package prueba1;


import Controlador.ControladorLogin;
import Controlador.ControladorMenuInicial;
import Modelo.ConsultasUsuario;
import Modelo.Usuario;
import Vista.LoginForm;
import Vista.MenuInicial;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

/**
 *
 * @author Schefer
 */
public class Prueba1 {
  

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
 
        try {
            UIManager.setLookAndFeel("com.jtattoo.plaf.texture.TextureLookAndFeel");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Prueba1.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            Logger.getLogger(Prueba1.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(Prueba1.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnsupportedLookAndFeelException ex) {
            Logger.getLogger(Prueba1.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        Usuario usuario= new Usuario();
        ConsultasUsuario modelo =new ConsultasUsuario();
        LoginForm menu=new LoginForm();
        ControladorLogin controlador=new ControladorLogin(menu,usuario,modelo);
        controlador.iniciar();
        
        
       // MenuInicial menu = new MenuInicial();
        //ControladorMenuInicial controlador = new ControladorMenuInicial(menu);
        //controlador.iniciar();
      
    }
    
}
