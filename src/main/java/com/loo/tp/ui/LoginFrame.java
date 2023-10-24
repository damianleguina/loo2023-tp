package com.loo.tp.ui;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.loo.tp.ControllerFactory;
import com.loo.tp.controllers.UserController;

public class LoginFrame extends JFrame implements ActionListener {
    private UserController userController;

    private JTextField userNameTextField;
    private JTextField passwordTextField;
    private JButton loginButton;
    private JButton cancelButton;

    public LoginFrame() {
        this.userController = ControllerFactory.getUserController();
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setLayout(new GridLayout(0, 1));
        this.setSize(300, 140);
        this.add(createFormPanel());
        this.add(createActionPanel());
    }

    private JPanel createFormPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(0, 2));
        this.userNameTextField = new JTextField();
        panel.add(new JLabel("Usuario: "));
        panel.add(this.userNameTextField);

        this.passwordTextField = new JTextField();
        panel.add(new JLabel("Contraseña: "));
        panel.add(this.passwordTextField);
        return panel;
    }

    private JPanel createActionPanel() {
        JPanel panel = new JPanel();

        this.cancelButton = new JButton("Salir");
        this.cancelButton.addActionListener(this);
        panel.add(this.cancelButton);

        this.loginButton = new JButton("Ingresar");
        this.loginButton.addActionListener(this);
        panel.add(this.loginButton);
        return panel;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == loginButton) {
            this.handleLogin();
        } else {
            this.handleCancel();
        }
    }

    private void handleLogin() {
        var userName = userNameTextField.getText();
        if (userName == null || userName.equals("")) {
            JOptionPane.showMessageDialog(null, "El campo 'Usuario' tiene un valor inválido", "Usuario inválido", JOptionPane.ERROR_MESSAGE, null);
            return;
        }
        var password = passwordTextField.getText();
        if (password == null || password.equals("")) {
            JOptionPane.showMessageDialog(null, "El campo 'Contraseña' tiene un valor inválido", "Contraseña inválida", JOptionPane.ERROR_MESSAGE, null);
            return;
        }

        var response = userController.getUser(userNameTextField.getText(), passwordTextField.getText());
        if (!response.getValue0()) {
            JOptionPane.showMessageDialog(null, "El usuario o la contraseña es incorrecta", "Error", JOptionPane.ERROR_MESSAGE, null);
            return;
        }

        System.out.println(response.getValue1());
    }

    private void handleCancel() {
        this.dispose();
    }
}