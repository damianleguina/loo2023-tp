package com.loo.tp.ui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import com.loo.tp.ControllerFactory;
import com.loo.tp.controllers.SessionController;
import com.loo.tp.controllers.UserController;

public class LoginFrame extends AppFrame {
    private SessionController sessionController;
    private UserController userController;

    private JTextField userNameTextField;
    private JPasswordField passwordField;
    private JButton loginButton;

    public LoginFrame() {
        super();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        var source = e.getSource();
        if (source == loginButton) {
            this.handleLogin();
        } else {
            this.handleCancel();
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
    protected void setDefaultSize() {
        this.setPreferredSize(new Dimension(300, 110));
    }

    @Override
    protected void onFailure() {
        new MenuFrame();
        this.close();
    }

    @Override
    protected void render() {
        this.setLocationRelativeTo(null);
        this.renderFormPanel();
        this.renderActionPanel();
    }

    private void renderFormPanel() {
        var panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.add(Box.createRigidArea(new Dimension(10, 5)));

        var userFieldPanel = new JPanel();
        userFieldPanel.setLayout(new GridLayout(0, 2));
        this.userNameTextField = new JTextField();
        userFieldPanel.add(new JLabel("Usuario: "));
        userFieldPanel.add(this.userNameTextField);
        panel.add(userFieldPanel);

        var passwordFieldPanel = new JPanel();
        passwordFieldPanel.setLayout(new GridLayout(0, 2));
        this.passwordField = new JPasswordField();
        passwordFieldPanel.add(new JLabel("Contraseña: "));
        passwordFieldPanel.add(this.passwordField);
        panel.add(passwordFieldPanel);

        this.add(panel,BorderLayout.NORTH);
    }

    private void renderActionPanel() {
        JPanel panel = new JPanel();
        this.loginButton = this.createButton("Ingresar", panel);
        this.add(panel,BorderLayout.SOUTH);
    }

    private void handleLogin() {
        var userName = userNameTextField.getText();
        if (userName == null || userName.equals("")) {
            this.showErrorDialog("El campo 'Usuario' tiene un valor inválido");
            return;
        }
        var password = new String(passwordField.getPassword());
        if (password == null || password.equals("")) {
            this.showErrorDialog("El campo 'Contraseña' tiene un valor inválido");
            return;
        }

        var response = userController.getUser(userNameTextField.getText(), new String(passwordField.getPassword()));
        if (!response.getValue0()) {
            this.showErrorDialog("El usuario o la contraseña es incorrecta");
            return;
        }

        new MenuFrame();
        this.dispose();
    }

    private void handleCancel() {
        this.dispose();
    }
}