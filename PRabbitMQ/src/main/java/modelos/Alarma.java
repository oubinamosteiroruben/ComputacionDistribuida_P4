/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelos;

import java.util.ArrayList;
import java.util.List;
import modelos.Empresa;

/**
 *
 * @author ruben
 */
public class Alarma {
    
    private Integer id;
    private Empresa empresa;
    private double valor;
    private int tipo;
    private boolean finalizada;
    
    public Alarma(Integer id, Empresa empresa, double valor, int tipo){
        this.id = id;
        this.empresa = empresa;
        this.valor = valor;
        this.tipo = tipo;
        this.finalizada = false;
    }
    
    public Alarma(Empresa empresa, double valor, int tipo){
        this.empresa = empresa;
        this.valor = valor;
        this.tipo = tipo;
        this.finalizada = false;
    }
    
    public void setId(Integer id){
        this.id = id;
    }
    
    public void setEmpresa(Empresa empresa){
        this.empresa = empresa;
    }
    
    public void setValor(double valor){
        this.valor = valor;
    }
    
    public void setTipo(int tipo){
        this.tipo = tipo;
    }
    
    public Integer getId(){
        return this.id;
    }
    
    public Empresa getEmpresa(){
        return this.empresa;
    }
    
    public double getValor(){
        return this.valor;
    }
    
    public int getTipo(){
        return this.tipo;
    }

    public boolean isFinalizada() {
        return finalizada;
    }

    public void setFinalizada(boolean finalizada) {
        this.finalizada = finalizada;
    }
    
    public String toString(){
        String s = "";
        if(this.id != null){
            s+=this.id;
        }
        return s + "&" + empresa.getNombre() + "&" + empresa.getValor() + "&" + valor + "&" + getTipo();
    }
    
    public static String getListAlarmasToString(List<Alarma> listAlarmas){
        String txt = "";
        for(Alarma a: listAlarmas){
            txt += a.toString() + "/";
        }
        return txt;
    }
    
    public static List<Alarma> getListAlarmas(String alarmaList){
        List<Alarma> alarmas = new ArrayList<>();
        Alarma a;
        String[] txt = alarmaList.split("/");
        for(String alarmaTxt: txt){
            a = getClass(alarmaTxt);
            if(a != null) alarmas.add(a);
        }
        return alarmas;
    }
    
    public static Alarma getClass(String alarmaString){
        String[] txt = alarmaString.split("&");
        if(txt.length == 5){
            return new Alarma(Integer.parseInt(txt[0]),new Empresa(txt[1],Double.parseDouble(txt[2])),Double.parseDouble(txt[3]),Integer.parseInt(txt[4]));
        }
        return null;
    }
    
    public static Alarma getClass(String alarmaString, Integer id){
        String[] txt = alarmaString.split("&");
        if(txt.length == 5){
            return new Alarma(id,new Empresa(txt[1],Double.parseDouble(txt[2])),Double.parseDouble(txt[3]),Integer.parseInt(txt[4]));
        }
        return null;
    }
    
    public static Alarma getAlarma(String alarmaTxt, Integer id){
        return getClass(alarmaTxt,id);
    }
    
    public static Alarma getAlarma(String alarmaTxt){
        return getClass(alarmaTxt);
    }
}
