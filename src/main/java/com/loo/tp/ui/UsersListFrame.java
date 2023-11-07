package com.loo.tp.ui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

import com.loo.tp.ControllerFactory;
import com.loo.tp.controllers.UserController;
import com.loo.tp.entities.User;

public class UsersListFrame extends AppFrame implements ListSelectionListener {
    private UserController userController;

    private Object[][] data;
    private JTable table;
    private int selectedRow;
    private JButton goBackButton;
    private JButton changeUserStatusButton;
    private JButton viewUserButton;

    public UsersListFrame() {
        super();
    }

    @Override
    public void actionPerformed(ActionEvent arg0) {
        var source = arg0.getSource();
        if (source == goBackButton) {
            this.handleBackButtonClicked();
        } else if (source == changeUserStatusButton) {
            this.handleChangeUserStatusButtonClicked();
        } else if (source == viewUserButton) {
            this.handleViewUserButtonClicked();
        }
    }

    @Override
    public void valueChanged(ListSelectionEvent arg0) {
        selectedRow = table.getSelectedRow();

        if (selectedRow == -1) {
            this.changeUserStatusButton.setVisible(false);
            this.viewUserButton.setVisible(false);
            return;
        }

        User user = this.getSelectedUser();
        this.changeUserStatusButton.setText(user.isActive() ? "Desactivar usuario" : "Activar usuario");
        this.changeUserStatusButton.setVisible(true);
        this.viewUserButton.setVisible(true);
    }

    @Override
    protected void init() {
        userController = ControllerFactory.getUserController();
    }
    
    @Override
    protected void onFailure() {
        new MenuFrame();
        this.close();
    }

    @Override
    protected void render() {
        var model = getTableModel();
        table = new JTable(model);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.setDefaultEditor(Object.class, null);
        var listSelectionModel = table.getSelectionModel();
        listSelectionModel.addListSelectionListener(this);
        this.add(new JScrollPane(table), BorderLayout.CENTER);

        var panel = new JPanel();
        goBackButton = createButton("Volver",panel);
        changeUserStatusButton = this.createButton("", panel);
        changeUserStatusButton.setVisible(false);
        viewUserButton = this.createButton("Administrar usuario", panel);
        viewUserButton.setVisible(false);
        this.add(panel, BorderLayout.SOUTH);
    }

    private User getSelectedUser() {
        return (User) data[selectedRow][4];
    }

    private void handleBackButtonClicked() {
        new MenuFrame();
        this.close();
    }

    private void handleChangeUserStatusButtonClicked() {
        User user = this.getSelectedUser();
        userController.changeStatus(user.getId(), !user.isActive());
        var model = getTableModel();
        table.setModel(model);
        model.fireTableDataChanged();
    }

    private void handleViewUserButtonClicked() {
        User user = this.getSelectedUser();
        new UserInfoFrame(user.getId());
        this.close();
    }

    private DefaultTableModel getTableModel() {
        User[] users = userController.getUsers().getValue1();
        String[] columnNames = { "Id", "Usuario", "Estado", "Administrador" };
        data = new Object[users.length][];
        for (int i = 0; i < users.length; i++) {
            User user = users[i];
            data[i] = new Object[] {
                    user.getId(),
                    user.getName(),
                    user.isActive() ? "Activo" : "Inactivo",
                    user.isAdmin() ? "Si" : "No",
                    user
            };
        }

        return new DefaultTableModel(data, columnNames);
    }
}
