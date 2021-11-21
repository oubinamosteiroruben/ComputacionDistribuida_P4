package gui.modelos;

import modelos.Empresa;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.List;


public final class ModeloTablaEmpresas extends AbstractTableModel {

    private List<Empresa> empresas;

    // ------------------------------------------------------------------------
    // ----------------------------- Constructor ------------------------------
    public ModeloTablaEmpresas() {
        this.empresas = new ArrayList<>();
    }

    // ------------------------------------------------------------------------
    // -------------------------- Overrides Getters ---------------------------
    @Override
    public final int getRowCount() {
        return this.empresas.size();
    }

    @Override
    public final int getColumnCount() {
        return 2;
    }

    @Override
    public final Object getValueAt(int rowIndex, int columnIndex) {
        Object resultado=null;
        switch (columnIndex){
            case 0: resultado= this.empresas.get(rowIndex).getNombre(); break;
            case 1: resultado= this.empresas.get(rowIndex).getValor(); break;
        }
        return resultado;
    }

    @Override
    public final String getColumnName(int col) {
        String nombre = null;

        switch (col){
            case 0: nombre= "Empresa"; break;
            case 1: nombre= "Valor"; break;
        }
        return nombre;
    }

    @Override
    public final Class getColumnClass(int col) {
        Class clase = null;

        switch (col){
            case 0: clase = java.lang.String.class; break;
            case 1: clase = java.lang.Double.class; break;
        }
        return clase;
    }

    // ------------------------------------------------------------------------
    // ------------------------------ Funciones -------------------------------
    public final void setFilas(java.util.List<Empresa> empresas) {
        if (empresas != null ) {
            this.empresas = empresas;
            fireTableDataChanged();
        }
    }

    public final Empresa obtenerEmpresa(int i) {
        return this.empresas.get(i);
    }

    public final void actualizarTabla() {
        fireTableDataChanged();
    }

    public final void nuevaEmpresa(Empresa e) {
        this.empresas.add(e);
        fireTableRowsInserted(this.empresas.size() - 1, this.empresas.size() - 1);
    }
}