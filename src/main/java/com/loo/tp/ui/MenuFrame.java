package com.loo.tp.ui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;

import com.loo.tp.ControllerFactory;
import com.loo.tp.controllers.SessionController;
import com.loo.tp.entities.User;

public class MenuFrame extends AppFrame {
    private SessionController sessionController;

    private User currentUser;

    private JButton listUsersButton;
    private JButton addUserButton;
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
        } else if (source == addUserButton) {
            this.handleAddUserButton();
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
        var panel = new JPanel();
        panel.add(Box.createRigidArea(new Dimension(5, 5)));
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        if (this.currentUser.isAdmin()) {
            this.listUsersButton = this.createButton("Usuarios", panel);

            panel.add(Box.createRigidArea(new Dimension(0, 10)));
            this.addUserButton = this.createButton("Agregar Usuario", panel);

            panel.add(Box.createRigidArea(new Dimension(0, 10)));
            this.listPrintsButton = this.createButton("Trabajos", panel);
        } else {
            panel.add(Box.createRigidArea(new Dimension(0, 10)));
            this.addPrintButton = this.createButton("Agregar trabajo", panel);
            panel.add(Box.createRigidArea(new Dimension(0, 10)));
            this.listMyPrintsButton = this.createButton("Mis trabajos", panel);
        }
        panel.add(Box.createRigidArea(new Dimension(0, 10)));
        this.logoutButton = this.createButton("Salir", panel);
        this.add(panel, BorderLayout.CENTER);
    }

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

    private void handleLogoutButton() {
        this.sessionController.Logout();
        new LoginFrame();
        this.close();
    }

}
