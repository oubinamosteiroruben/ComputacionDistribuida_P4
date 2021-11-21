package gui.modelos;

import modelos.Alarma;
import definiciones.Definiciones;
import modelos.Empresa;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.List;


public final class ModeloTablaAlarmas extends AbstractTableModel {

    private List<Alarma> alarmas;

    // ------------------------------------------------------------------------
    // ----------------------------- Constructor ------------------------------
    public ModeloTablaAlarmas() {
        this.alarmas = new ArrayList<>();
    }

    // ------------------------------------------------------------------------
    // -------------------------- Overrides Getters ---------------------------
    @Override
    public final int getRowCount() {
        return this.alarmas.size();
    }

    @Override
    public final int getColumnCount() {
        return 5;
    }

    @Override
    public final Object getValueAt(int rowIndex, int columnIndex) {
        Object resultado=null;
        
        double valorAlarma;
        double valorActual;

        switch (columnIndex){
            case 0: resultado = this.alarmas.get(rowIndex).getId(); break;
            case 1: resultado= this.alarmas.get(rowIndex).getEmpresa().getNombre(); break;
            case 2: resultado= this.alarmas.get(rowIndex).getValor(); break;
            case 3: switch(alarmas.get(rowIndex).getTipo()){
                        case Definiciones.SUBIDA:
                            resultado = "↑";
                            break;
                        case Definiciones.BAJADA:
                            resultado = "↓";
                            break;
                        default:
                            resultado = "";
                    }
            break;
            case 4: resultado = "";
                    if(this.alarmas.get(rowIndex).isFinalizada()){
                        resultado = Definiciones.ALARMA_TXT;
                    }
                    /*valorAlarma = this.alarmas.get(rowIndex).getValor();
                    valorActual = this.alarmas.get(rowIndex).getEmpresa().getValor();
                    switch(this.alarmas.get(rowIndex).getTipo()){
                        case Definiciones.SUBIDA:
                            if(valorAlarma <= valorActual){
                                resultado = Definiciones.ALARMA_TXT;
                            }
                            break;
                        case Definiciones.BAJADA:
                            if(valorAlarma >= valorActual){
                                resultado = Definiciones.ALARMA_TXT;
                            }
                            break;
                    }*/
                break;
        }
        return resultado;
    }

    @Override
    public final String getColumnName(int col) {
        String nombre = null;

        switch (col){
            case 0: nombre = "ID"; break;
            case 1: nombre= "Empresa"; break;
            case 2: nombre= "Valor Objectivo"; break;
            case 3: nombre= "Subida/Bajada"; break;
            case 4: nombre= "";
        }
        return nombre;
    }

    @Override
    public final Class getColumnClass(int col) {
        Class clase = null;

        switch (col){
            case 0: clase = java.lang.Integer.class; break;
            case 1: clase = java.lang.String.class; break;
            case 2: clase = java.lang.Double.class; break;
            case 3: clase = java.lang.String.class; break;
            case 4: clase = java.lang.String.class; break;
        }
        return clase;
    }

    // ------------------------------------------------------------------------
    // ------------------------------ Funciones -------------------------------
    public final void setFilas(java.util.List<Alarma> alarmas) {
        if (alarmas != null ) {
            this.alarmas = alarmas;
            fireTableDataChanged();
        }
    }

    public final Alarma obtenerAlarma(int i) {
        return this.alarmas.get(i);
    }

    public final void actualizarTabla() {
        fireTableDataChanged();
    }

    public final void nuevaAlarma(Alarma a) {
        a.setId(this.alarmas.size());
        this.alarmas.add(a);
        fireTableRowsInserted(this.alarmas.size() - 1, this.alarmas.size() - 1);
    }
}