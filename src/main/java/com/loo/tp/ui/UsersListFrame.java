package com.loo.tp.ui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;

import javax.swing.JButton;
import javax.swing.event.ListSelectionEvent;
import javax.swing.table.DefaultTableModel;

import com.loo.tp.ControllerFactory;
import com.loo.tp.controllers.UserController;
import com.loo.tp.entities.User;
import com.loo.tp.ui.utils.builder.ActionPanelBuilder;
import com.loo.tp.ui.utils.table.TableFrame;
import com.loo.tp.ui.utils.table.TableWrapper;

public class UsersListFrame extends AppFrame implements TableFrame {
    private UserController userController;

    private Object[][] data;
    private TableWrapper table;
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
        int[] indexes = this.table.getSelectedIndexes();

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

    public DefaultTableModel getTableModel() {
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
        this.table = new TableWrapper(this);
        this.add(this.table.getTablePane(), BorderLayout.CENTER);
    }

    private void renderSouthPanel() {
        var actionPanelBuilder = new ActionPanelBuilder(this);

        this.goBackButton = actionPanelBuilder.createButton("Volver");

        this.changeUserStatusButton = actionPanelBuilder.createButton("");
        this.changeUserStatusButton.setVisible(false);

        this.viewUserButton = actionPanelBuilder.createButton("Administrar usuario");
        this.viewUserButton.setVisible(false);

        this.deactivateUsersButton = actionPanelBuilder.createButton("Desactivar usuarios");
        this.deactivateUsersButton.setVisible(false);

        this.activateUsersButton = actionPanelBuilder.createButton("Activar usuarios");
        this.activateUsersButton.setVisible(false);

        this.add(actionPanelBuilder.build(), BorderLayout.SOUTH);
    }

    private User getSelectedUser() {
        int index = this.table.getSelectedIndexes()[0];
        return (User) data[index][4];
    }

    private User[] getSelectedUsers() {
        int[] indexes = this.table.getSelectedIndexes();
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
        this.table.refreshModel();
    }

    private void handleViewUserButtonClicked() {
        var user = this.getSelectedUser();
        new UserInfoFrame(user.getId());
        this.close();
        this.table.refreshModel();
    }

    private void handleDeactivateUsersButtonClicked() {
        var users = this.getSelectedUsersIds();
        var result = this.userController.changeStatus(users, false);
        if (!result.getValue0()) {
            this.showErrorDialog(result.getValue2());
            return;
        }
        this.table.refreshModel();
    }

    private void handleActivateUsersButtonClicked() {
        var users = this.getSelectedUsersIds();
        var result = this.userController.changeStatus(users, true);
        if (!result.getValue0()) {
            this.showErrorDialog(result.getValue2());
            return;
        }
        this.table.refreshModel();
    }
    // endregion Action Handlers
    // endregion Private Methods
}
