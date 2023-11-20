package com.loo.tp.ui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;

import javax.swing.JButton;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import com.loo.tp.ControllerFactory;
import com.loo.tp.controllers.SessionController;
import com.loo.tp.controllers.UserController;
import com.loo.tp.ui.utils.builder.ActionPanelBuilder;
import com.loo.tp.ui.utils.builder.FormPanelBuilder;

public class LoginFrame extends AppFrame {
    private SessionController sessionController;
    private UserController userController;

    private JTextField usernameTextField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JButton exitButton;

    public LoginFrame() {
        super();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        var source = e.getSource();
        if (source == loginButton) {
            this.handleLoginButtonClicked();
        } else if (source == exitButton) {
            this.handleExitButtonClicked();
        }
    }

    @Override
    protected void init() {
        this.sessionController = ControllerFactory.getSessionController();
        this.userController = ControllerFactory.getUserController();
    }

    @Override
    protected boolean shouldRender() {
        return !sessionController.isLoggedIn();
    }

    @Override
    protected void onFailure() {
        new MenuFrame();
        this.close();
    }

    @Override
    protected void render() {
        this.renderNorthPanel();
        this.renderSouthPanel();
    }

    private void renderNorthPanel() {
        var formBuilder = new FormPanelBuilder();

        usernameTextField = formBuilder.addTextField("Usuario:");
        passwordField = formBuilder.addPasswordField("Contraseña:");

        this.add(formBuilder.build(), BorderLayout.CENTER);
    }

    private void renderSouthPanel() {
        var actionPanelBuilder = new ActionPanelBuilder(this);
        this.exitButton = actionPanelBuilder.createButton("Salir");
        this.loginButton = actionPanelBuilder.createButton("Ingresar");
        this.add(actionPanelBuilder.build(), BorderLayout.SOUTH);
    }

    private void handleLoginButtonClicked() {
        var username = usernameTextField.getText();
        if (username == null || username.equals("")) {
            this.showErrorDialog("El campo 'Usuario' tiene un valor inválido.");
            return;
        }
        var password = new String(passwordField.getPassword());
        if (password == null || password.equals("")) {
            this.showErrorDialog("El campo 'Contraseña' tiene un valor inválido.");
            return;
        }

        var response = userController.getUser(usernameTextField.getText(), new String(passwordField.getPassword()));
        if (!response.getValue0()) {
            this.showErrorDialog(response.getValue2());
            return;
        }

        new MenuFrame();
        this.dispose();
    }

    private void handleExitButtonClicked() {
        this.dispose();
    }
}