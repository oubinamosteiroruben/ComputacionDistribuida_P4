/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package herramientas;

import java.util.ArrayList;
import modelos.Empresa;
import java.util.List;
import modelos.Alarma;

/**
 *
 * @author ruben
 */


// Forma mensaje: 

// MSG: {TIPO}${USUARIO}${OTRO}
public class Herramientas {
    
    
    public static String getTipo(String message){
        String [] l = message.split("\\$");
        System.out.println("Tipo: " + l[0]);
        return l[0];
    }
    
    public static List<Empresa> getListEmpresas(String message){
        String [] l = message.split("\\$");
        return Empresa.getListEmpresas(l[2]);
    }
    
    public static String generarMensaje(String tipo, String username){
        return new String(tipo + "$" + username + "$" + "$");
    }
    
    public static String generarMensaje(String tipo, String username, String empresas, String alarmas){
        return new String(tipo + "$" + username + "$" + empresas + "$" + alarmas);
    }
    
    public static String getCliente(String message){
        String [] l = message.split("\\$");
        System.out.println(l[1]);
        return l[1];
    }
    
    public static List<Alarma> getListAlarmas(String message){
        String [] l = message.split("\\$");
        if(l.length > 3){
            return Alarma.getListAlarmas(l[3]);
        }
        return new ArrayList<>();
    }
    
    public static String generarMensaje(String tipo, String username,  String alarmas){
        return new String(tipo + "$" + username + "$" + "$" + alarmas);
    }
    
    public static Alarma getAlarma(String message, Integer id){
        
        String [] l = message.split("\\$");
        if(l.length > 3){
            return Alarma.getAlarma(l[3],id);
        }
        return Alarma.getAlarma(message,id);
    }
    
    public static Alarma getAlarma(String message){
        
        String [] l = message.split("\\$");
        if(l.length > 3){
            return Alarma.getAlarma(l[3]);
        }
        return Alarma.getAlarma(message);
    }
    
    
}
