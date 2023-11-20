package com.loo.tp.ui.utils.table;

import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

public interface TableFrame extends ListSelectionListener {
    DefaultTableModel getTableModel();

}
