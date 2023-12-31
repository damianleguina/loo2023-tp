package com.loo.tp.ui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import com.loo.tp.ControllerFactory;
import com.loo.tp.controllers.UserController;
import com.loo.tp.entities.User;
import com.loo.tp.ui.utils.builder.ActionPanelBuilder;
import com.loo.tp.ui.utils.builder.FormPanelBuilder;

public class AddUserFrame extends AppFrame {
    private UserController userController;

    private JTextField usernameTextField;
    private JPasswordField passwordField;
    private JPasswordField repeatPasswordField;
    private JCheckBox isAdminCheckBox;
    private JButton goBackButton;
    private JButton addUserButton;

    @Override
    public void actionPerformed(ActionEvent arg0) {
        var source = arg0.getSource();
        if (source == goBackButton) {
            this.handleBackButtonClicked();
        } else if (source == addUserButton) {
            this.handleAddUserButtonClicked();
        }
    }

    // region Protected Methods
    @Override
    protected void init() {
        this.userController = ControllerFactory.getUserController();
    }

    @Override
    protected void render() {
        this.renderCenterPanel();
        this.renderSouthPanel();
    }
    // endregion Protected Methods

    // region Private Methods
    private void renderCenterPanel() {
        var formBuilder = new FormPanelBuilder();

        usernameTextField = formBuilder.addTextField("Nombre del usuario:");
        passwordField = formBuilder.addPasswordField("Contraseña:");
        repeatPasswordField = formBuilder.addPasswordField("Repetir contraseña:");
        isAdminCheckBox = formBuilder.addCheckbox("¿Es administrador?   ");

        this.add(formBuilder.build(), BorderLayout.CENTER);
    }

    private void renderSouthPanel() {
        var actionPanelBuilder = new ActionPanelBuilder(this);

        goBackButton = actionPanelBuilder.createButton("Volver");
        addUserButton = actionPanelBuilder.createButton("Agregar usuario");

        this.add(actionPanelBuilder.build(), BorderLayout.SOUTH);
    }

    // region Action Handlers
    private void handleBackButtonClicked() {
        new MenuFrame();
        this.close();
    }

    private void handleAddUserButtonClicked() {
        var username = usernameTextField.getText();
        if (username == null || username.equals("")) {
            this.showErrorDialog("El campo 'Nombre del usuario' tiene un valor inválido.");
            return;
        }
        var password = new String(passwordField.getPassword());
        if (password == null || password.equals("")) {
            this.showErrorDialog("El campo 'Contraseña' tiene un valor inválido.");
            return;
        }
        var repeatPassword = new String(repeatPasswordField.getPassword());
        if (repeatPassword == null || repeatPassword.equals("")) {
            this.showErrorDialog("El campo 'Repetir contraseña' tiene un valor inválido.");
            return;
        }
        if (!repeatPassword.equals(password)) {
            this.showErrorDialog("Las contraseñas no son iguales.");
            return;
        }
        var isAdmin = isAdminCheckBox.isSelected();
        var user = new User(1, username, password, isAdmin);

        var response = this.userController.addUser(user);

        if (!response.getValue0()) {
            this.showErrorDialog(response.getValue2());
            return;
        }

        usernameTextField.setText("");
        ;
        passwordField.setText("");
        repeatPasswordField.setText("");
        isAdminCheckBox.setSelected(false);
        this.showSuccessDialog("Usuario agregado correctamente.");
    }
    // endregion Action Handlers
    // endregion Private Methods
}
