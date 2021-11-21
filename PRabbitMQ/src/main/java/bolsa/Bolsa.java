/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bolsa;

import modelos.Empresa;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ruben
 */
public class Bolsa {
    public static List<Empresa> getDatos(){
        List<Empresa> empresas = new ArrayList<>();
        final String url =
                "https://www.bolsamadrid.es/esp/aspx/Mercados/Precios.aspx?indice=ESI100000000&punto=indice";

        try {
            final Document document = Jsoup.connect(url).get();
            //System.out.println(document.outerHtml());
            for (Element row : document.select(
                    "table.TblPort tr")) {
                if (row.select("td:nth-of-type(1)").text().equals("")) {
                    continue;
                }
                else {
                    final String name =
                            row.select("td:nth-of-type(1)").text();
                    final String value =
                            row.select("td:nth-of-type(2)").text();
                    empresas.add(new Empresa(name,getValue(value)));
                }
            }
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }

        return empresas;
    }


    private static Double getValue(String txt){
        String txtAux = "";
        String [] l = txt.split("\\.");
        for(String s: l){
            txtAux+=s;
        }
        l = txtAux.split(",");
        txtAux = l[0] + "." + l[1];
        return Double.parseDouble(txtAux);
    }
}