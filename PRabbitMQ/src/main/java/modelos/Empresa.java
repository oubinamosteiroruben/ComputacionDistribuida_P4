/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelos;

import java.util.ArrayList;
import java.util.List;

public class Empresa {
    private String nombre;
    private double valor;



    public Empresa(String nombre, double valor){
        this.nombre = nombre;
        this.valor = valor;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public String toString(){
        return this.nombre + "&" + this.valor;
    }

    public static Empresa getClass(String empresaString){
        String[] txt = empresaString.split("\\&");
        if(txt.length == 2){
            return new Empresa(txt[0],Double.parseDouble(txt[1]));
        }
        return null;
    }

    public static List<Empresa> getListEmpresas(String empresaList){
        List<Empresa> empresas = new ArrayList<>();
        Empresa e;
        String[] txt = empresaList.split("/");
        for(String empresaTxt: txt){
            e = getClass(empresaTxt);
            if(e != null) empresas.add(e);
        }
        return empresas;
    }

    public static String getListEmpresasToString(List<Empresa> list){
        String txt = "";
        for(Empresa e: list){
            txt += e.toString() + "/";
        }
        return txt;
    }
}
