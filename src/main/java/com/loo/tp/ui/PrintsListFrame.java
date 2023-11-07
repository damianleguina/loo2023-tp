package com.loo.tp.ui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

import org.javatuples.Pair;

import com.loo.tp.ControllerFactory;
import com.loo.tp.controllers.PrintController;
import com.loo.tp.entities.Print;
import com.loo.tp.utils.InstantUtils;

public class PrintsListFrame extends AppFrame implements ListSelectionListener {
    private PrintController printController;

    private JTable table;
    private JButton goBackButton;
    private JButton viewPrintButton;

    private Object[][] data;
    private int selectedRow;

    public PrintsListFrame() {
        super();
    }

    @Override
    public void actionPerformed(ActionEvent arg0) {
        var source = arg0.getSource();
        if (source == this.goBackButton) {
            this.handleGoBackButtonClicked();
        } else if (source == this.viewPrintButton) {
            this.handleViewPrintButtonClicked();
        }
    }

    @Override
    public void valueChanged(ListSelectionEvent arg0) {
        selectedRow = table.getSelectedRow();

        if (selectedRow == -1) {
            this.viewPrintButton.setVisible(false);
            return;
        }

        this.viewPrintButton.setVisible(true);
    }

    @Override
    protected void init() {
        // userController = ControllerFactory.getUserController();
        printController = ControllerFactory.getPrintController();
    }

    @Override
    protected void onFailure() {
        new MenuFrame();
        this.close();
    }

    @Override
    protected void render() {
        this.setPreferredSize(new Dimension(800, 400));
        this.renderCenterPanel();
        this.renderSouthPanel();
    }

    private void renderCenterPanel() {
        var model = getTableModel();
        table = new JTable(model);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.setDefaultEditor(Object.class, null);
        var listSelectionModel = table.getSelectionModel();
        listSelectionModel.addListSelectionListener(this);
        this.add(new JScrollPane(table), BorderLayout.CENTER);
    }

    private void renderSouthPanel() {
        var panel = new JPanel();
        this.goBackButton = this.createButton("Volver", panel);
        this.viewPrintButton = this.createButton("Ver trabajo", panel);
        this.viewPrintButton.setVisible(false);
        this.add(panel, BorderLayout.SOUTH);
    }

    private void handleGoBackButtonClicked() {
        new MenuFrame();
        this.close();
    }

    private void handleViewPrintButtonClicked() {
        var selectedPrint = this.getSelectedPrint();
        new PrintInfoFrame(new Object[] { selectedPrint.getId(), this.getClass().getSimpleName() });
        this.close();
    }

    private DefaultTableModel getTableModel() {
        // User[] users = userController.getUsers().getValue1();
        Pair<Boolean, Print[]> result = printController.get();
        var prints = result.getValue1();
        String[] columnNames = { "Id", "Usuario", "Cantidad", "Calidad", "Estado", "Fecha inicio", "Fecha fin",
                "Fecha entrega" };
        data = new Object[prints.length][];
        for (int i = 0; i < prints.length; i++) {
            Print print = prints[i];
            data[i] = new Object[] {
                    print.getId(),
                    print.getUser().getName(),
                    print.getQuantity(),
                    print.getQuality().toString(),
                    print.getStatus().toString(),
                    InstantUtils.format(print.getStartDate()),
                    InstantUtils.format(print.getEndDate()),
                    InstantUtils.format(print.getDeliveryDate()),
                    print
            };
        }

        return new DefaultTableModel(data, columnNames);
    }


    private Print getSelectedPrint() {
        return (Print)data[selectedRow][8];
    }
}
