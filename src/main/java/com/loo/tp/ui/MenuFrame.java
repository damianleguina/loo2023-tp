package com.loo.tp.ui;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;

import com.loo.tp.ControllerFactory;
import com.loo.tp.controllers.SessionController;
import com.loo.tp.entities.User;

public class MenuFrame extends AppFrame {
    private SessionController sessionController;

    private User currentUser;

    private JButton listUsersButton;
    private JButton listPrintsButton;
    private JButton addPrintButton;
    private JButton listMyPrintsButton;
    private JButton logoutButton;

    public MenuFrame() {
        super();
    }

    public void actionPerformed(ActionEvent arg0) {
        var source = arg0.getSource();
        if (source == listUsersButton) {
            this.handleListUsersButtonClicked();
        } else if (source == listPrintsButton) {
            this.handleListPrintsButtonClicked();
        } else if (source == logoutButton) {
            this.handleLogoutButton();
        }
        return;
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
        var container = this.getContentPane();
        this.setLayout(new BoxLayout(container, BoxLayout.X_AXIS));

        this.add(Box.createRigidArea(new Dimension(10, 40)));

        if (this.currentUser.isAdmin()) {
            this.listUsersButton = this.createButton("Usuarios", container);
            this.add(Box.createHorizontalStrut(10));
            this.listPrintsButton = this.createButton("Trabajos", container);
        } else {
            this.addPrintButton = this.createButton("Agregar trabajo", container);
            this.add(Box.createHorizontalStrut(10));
            this.listMyPrintsButton = this.createButton("Mis trabajos", container);
        }

        this.add(Box.createHorizontalStrut(10));
        this.logoutButton = this.createButton("Salir", container);

        this.add(Box.createRigidArea(new Dimension(10, 40)));
        this.pack();
    }

    private void handleListUsersButtonClicked() {
        new UsersListFrame();
        this.close();
    }

    private void handleListPrintsButtonClicked() {
        new PrintsListFrame();
        this.close();
    }

    private void handleLogoutButton() {
        this.sessionController.Logout();
        new LoginFrame();
        this.close();
    }

}
