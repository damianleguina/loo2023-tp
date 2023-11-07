package com.loo.tp.ui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
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
import com.loo.tp.controllers.UserController;
import com.loo.tp.entities.Print;
import com.loo.tp.entities.User;
import com.loo.tp.utils.InstantUtils;

public class UserInfoFrame extends ContextualFrame<Long> implements ListSelectionListener {
    private UserController userController;
    private PrintController printController;

    private User user;

    private JLabel nameLabel;
    private JLabel idLabel;
    private JLabel statusLabel;
    private JLabel isAdminLabel;
    private JTable table;
    private JButton goBackButton;
    private JButton changeStatusButton;
    private JButton viewPrintButton;
    private Object[][] data;
    private int selectedRow;

    public UserInfoFrame(long userId) {
        super(userId);
    }

    @Override
    public void actionPerformed(ActionEvent arg0) {
        var source = arg0.getSource();
        if (source == goBackButton) {
            this.handleBackButtonClicked();
        } else if (source == changeStatusButton) {
            this.handleChangeStatusButtonClicked();
        } else if (source == viewPrintButton) {
            this.handleViewPrintButtonClicked();
        }
    }

    @Override
    public void valueChanged(ListSelectionEvent arg0) {
        this.selectedRow = table.getSelectedRow();
        if (this.selectedRow == -1) {
            this.viewPrintButton.setVisible(false);
            return;
        }

        this.viewPrintButton.setVisible(true);
    }

    @Override
    protected void init() {
        this.userController = ControllerFactory.getUserController();
        this.printController = ControllerFactory.getPrintController();
        Pair<Boolean, User> result = userController.getById(this.context);
        this.user = result.getValue1();
    }

    @Override
    protected boolean shouldRender() {
        return user != null;
    }

    @Override
    protected void onFailure() {
        new MenuFrame();
        this.close();
    }

    @Override
    protected void render() {
        this.setPreferredSize(new Dimension(800, 400));
        this.renderNorthPanel();
        this.renderCenterPanel();
        this.renderSouthPanel();
    }

    private void renderNorthPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        this.nameLabel = new JLabel("Nombre: " + user.getName());
        panel.add(nameLabel);
        this.idLabel = new JLabel("Id: " + Long.toString(user.getId()));
        panel.add(idLabel);
        this.statusLabel = new JLabel("Estado: " + (user.isActive() ? "Activo" : "Inactivo"));
        panel.add(statusLabel);
        this.isAdminLabel = new JLabel("¿Administrador?: " + (user.isAdmin() ? "Si" : "No"));
        panel.add(isAdminLabel);
        this.add(panel, BorderLayout.NORTH);
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
        JPanel panel = new JPanel();
        // panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        this.goBackButton = this.createButton("Volver", panel);
        this.changeStatusButton = this.createButton("Cambiar estado", panel);
        this.viewPrintButton = this.createButton("Ver trabajo", panel);
        this.viewPrintButton.setVisible(false);
        this.add(panel, BorderLayout.SOUTH);
    }

    private DefaultTableModel getTableModel() {
        // User[] users = userController.getUsers().getValue1();
        Pair<Boolean, Print[]> result = printController.get(this.context);
        var prints = result.getValue1();
        String[] columnNames = { "Id", "Cantidad", "Calidad", "Estado", "Fecha inicio", "Fecha fin",
                "Fecha entrega" };
        data = new Object[prints.length][];
        for (int i = 0; i < prints.length; i++) {
            Print print = prints[i];
            data[i] = new Object[] {
                    print.getId(),
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

    private void handleBackButtonClicked() {
        new UsersListFrame();
        this.close();
    }

    private void handleChangeStatusButtonClicked() {
        this.user.setActive(!this.user.isActive());
        statusLabel.setText("Estado: " + (user.isActive() ? "Activo" : "Inactivo"));
    }

    private void handleViewPrintButtonClicked() {
        var print = getSelectedPrint();
        new PrintInfoFrame(new Object[] { print.getId(), this.getClass().getSimpleName(), user.getId() });
        this.close();
    }

    private Print getSelectedPrint() {
        return (Print)data[selectedRow][7];
    }
}
