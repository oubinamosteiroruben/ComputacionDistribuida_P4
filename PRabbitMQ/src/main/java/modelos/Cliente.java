/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelos;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ruben
 */
public class Cliente {
    
    
    private List<Alarma> alarmas;
    private String nombre;
    
    
    public Cliente(String nombre){
        this.nombre = nombre;
        this.alarmas = new ArrayList<>();
    }
    
    public Cliente(String nombre, List<Alarma> listAlarmas){
        this.nombre = nombre;
        this.alarmas = listAlarmas;
    }

    public List<Alarma> getAlarmas() {
        return alarmas;
    }

    public void setAlarmas(List<Alarma> listAlarmas) {
        this.alarmas = listAlarmas;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
    public void nuevaAlarma(Alarma a){
        List<Alarma> newList = new ArrayList<>();
        newList.add(a);
        for(Alarma aAux: this.alarmas){
            newList.add(aAux);
        }
        this.alarmas = newList;
    }
    
}
