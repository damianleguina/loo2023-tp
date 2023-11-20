package com.loo.tp.ui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;

import javax.swing.JButton;

import com.loo.tp.ControllerFactory;
import com.loo.tp.controllers.SessionController;
import com.loo.tp.entities.User;
import com.loo.tp.ui.utils.builder.ActionPanelBuilder;

public class MenuFrame extends AppFrame {
    private SessionController sessionController;

    private User currentUser;

    private JButton listUsersButton;
    private JButton addUserButton;
    private JButton listPrintsButton;
    private JButton addPrintButton;
    private JButton logoutButton;

    public MenuFrame() {
        super();
    }

    public void actionPerformed(ActionEvent arg0) {
        var source = arg0.getSource();
        if (source == listUsersButton) {
            this.handleListUsersButtonClicked();
        } else if (source == addUserButton) {
            this.handleAddUserButton();
        } else if (source == listPrintsButton) {
            this.handleListPrintsButtonClicked();
        } else if (source == logoutButton) {
            this.handleLogoutButton();
        } else if (source == addPrintButton) {
            this.handleAddPrintButton();
        }
    }

    @Override
    protected void init() {
        this.sessionController = ControllerFactory.getSessionController();
        this.currentUser = sessionController.getCurrentUser();
    }

    @Override
    protected boolean shouldRender() {
        return this.sessionController.isLoggedIn();
    }

    @Override
    protected void onFailure() {
        new LoginFrame();
        this.close();
    }

    @Override
    protected void render() {
        var actionPanelBuilder = new ActionPanelBuilder(this);

        this.logoutButton = actionPanelBuilder.createButton("Salir");
        if (this.currentUser.isAdmin()) {
            this.listUsersButton = actionPanelBuilder.createButton("Usuarios");
            this.addUserButton = actionPanelBuilder.createButton("Agregar Usuario");
            this.listPrintsButton = actionPanelBuilder.createButton("Trabajos");
        } else {
            this.addPrintButton = actionPanelBuilder.createButton("Agregar trabajo");
            this.listPrintsButton = actionPanelBuilder.createButton("Mis trabajos");
        }
        this.add(actionPanelBuilder.build(), BorderLayout.SOUTH);
    }

    // region Action Handlers
    private void handleListUsersButtonClicked() {
        new UsersListFrame();
        this.close();
    }

    private void handleAddUserButton() {
        new AddUserFrame();
        this.close();
    }

    private void handleListPrintsButtonClicked() {
        new PrintsListFrame();
        this.close();
    }

    private void handleAddPrintButton() {
        new AddPrintFrame();
        this.close();
    }

    private void handleLogoutButton() {
        this.sessionController.Logout();
        new LoginFrame();
        this.close();
    }
    // endregion Action Handlers

}
