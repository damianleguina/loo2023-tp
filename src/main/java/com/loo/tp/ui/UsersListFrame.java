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
    private JButton goBackButton;
    private JButton changeUserStatusButton;
    private JButton viewUserButton;
    private JButton deactivateUsersButton;
    private JButton activateUsersButton;

    public UsersListFrame() {
        super();
    }

    // region Protected Methods
    @Override
    public void actionPerformed(ActionEvent arg0) {
        var source = arg0.getSource();
        if (source == goBackButton) {
            this.handleBackButtonClicked();
        } else if (source == changeUserStatusButton) {
            this.handleChangeUserStatusButtonClicked();
        } else if (source == viewUserButton) {
            this.handleViewUserButtonClicked();
        } else if (source == deactivateUsersButton) {
            this.handleDeactivateUsersButtonClicked();
        } else if (source == activateUsersButton) {
            this.handleActivateUsersButtonClicked();
        }
    }

    @Override
    public void valueChanged(ListSelectionEvent arg0) {
        int[] indexes = this.table.getSelectionModel().getSelectedIndices();

        this.changeUserStatusButton.setVisible(false);
        this.viewUserButton.setVisible(false);
        this.deactivateUsersButton.setVisible(false);
        this.activateUsersButton.setVisible(false);

        if (indexes.length == 1) {
            var user = this.getSelectedUser();
            this.changeUserStatusButton.setText(user.isActive() ? "Desactivar usuario" : "Activar usuario");
            this.changeUserStatusButton.setVisible(true);
            this.viewUserButton.setVisible(true);
        }

        if (indexes.length > 1) {
            this.deactivateUsersButton.setVisible(true);
            this.activateUsersButton.setVisible(true);
        }
    }
    // endregion Event Handlers

    // region Protected Methods
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
        renderCenterPanel();
        renderSouthPanel();
    }
    // endregion Protected Methods

    // region Private Methods
    private void renderCenterPanel() {
        var model = getTableModel();
        table = new JTable(model);
        table.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        table.setDefaultEditor(Object.class, null);
        var listSelectionModel = table.getSelectionModel();
        listSelectionModel.addListSelectionListener(this);
        this.add(new JScrollPane(table), BorderLayout.CENTER);
    }

    private void renderSouthPanel() {
        var panel = new JPanel();
        goBackButton = createButton("Volver", panel);

        changeUserStatusButton = this.createButton("", panel);
        changeUserStatusButton.setVisible(false);

        viewUserButton = this.createButton("Administrar usuario", panel);
        viewUserButton.setVisible(false);

        deactivateUsersButton = this.createButton("Desactivar usuarios", panel);
        deactivateUsersButton.setVisible(false);

        activateUsersButton = this.createButton("Activar usuarios", panel);
        activateUsersButton.setVisible(false);

        this.add(panel, BorderLayout.SOUTH);
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

    private void refreshModel() {
        var model = getTableModel();
        this.table.setModel(model);
        model.fireTableDataChanged();
    }

    private User getSelectedUser() {
        int index = this.table.getSelectionModel().getSelectedIndices()[0];
        return (User) data[index][4];
    }

    private User[] getSelectedUsers() {
        int[] indexes = this.table.getSelectionModel().getSelectedIndices();
        User[] users = new User[indexes.length];
        for (int i = 0; i < indexes.length; i++) {
            users[i] = (User) data[indexes[i]][4];
        }
        return users;
    }

    private long[] getSelectedUsersIds() {
        var users = this.getSelectedUsers();
        var ids = new long[users.length];
        for (int i = 0; i < users.length; i++) {
            ids[i] = users[i].getId();
        }
        return ids;
    }

    // region Action Handlers
    private void handleBackButtonClicked() {
        new MenuFrame();
        this.close();
    }

    private void handleChangeUserStatusButtonClicked() {
        var user = this.getSelectedUser();
        var result = userController.changeStatus(user.getId(), !user.isActive());
        if (!result.getValue0()) {
            this.showErrorDialog(result.getValue2());
            return;
        }
        this.refreshModel();
    }

    private void handleViewUserButtonClicked() {
        var user = this.getSelectedUser();
        new UserInfoFrame(user.getId());
        this.close();
        this.refreshModel();
    }

    private void handleDeactivateUsersButtonClicked() {
        var users = this.getSelectedUsersIds();
        var result = this.userController.changeStatus(users, false);
        if (!result.getValue0()) {
            this.showErrorDialog(result.getValue2());
            return;
        }
        this.refreshModel();
    }

    private void handleActivateUsersButtonClicked() {
        var users = this.getSelectedUsersIds();
        var result = this.userController.changeStatus(users, true);
        if (!result.getValue0()) {
            this.showErrorDialog(result.getValue2());
            return;
        }
        this.refreshModel();
    }
    // endregion Action Handlers
    // endregion Private Methods
}
