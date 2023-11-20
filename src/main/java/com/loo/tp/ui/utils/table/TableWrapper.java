package com.loo.tp.ui.utils.table;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;

public class TableWrapper {
    JTable table;
    TableFrame frame;

    public TableWrapper(TableFrame frame) {
        this.frame = frame;
    }

    public JTable getTable() {
        return this.table;
    }

    public JScrollPane getTablePane() {
        var model = frame.getTableModel();
        table = new JTable(model);
        table.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        table.setDefaultEditor(Object.class, null);
        var listSelectionModel = table.getSelectionModel();
        listSelectionModel.addListSelectionListener(this.frame);
        return new JScrollPane(table);
    }

    public void refreshModel() {
        var model = frame.getTableModel();
        this.table.setModel(model);
        model.fireTableDataChanged();
    }

    public int[] getSelectedIndexes() {
        return this.table.getSelectionModel().getSelectedIndices();
    }
}
