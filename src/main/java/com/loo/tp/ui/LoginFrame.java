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
    private JPasswordField passwordTextField;
    private JButton loginButton;
    private JButton cancelButton;

    public LoginFrame() {
        super();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        var source = e.getSource();
        if (source == loginButton) {
            this.handleLogin();
        } else if (source == cancelButton) {
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
    protected void onFailure() {
        new MenuFrame();
        this.close();
    }

    @Override
    protected void render() {
        this.setPreferredSize(new Dimension(300, 110));
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
        this.passwordTextField = new JPasswordField();
        passwordFieldPanel.add(new JLabel("Contraseña: "));
        passwordFieldPanel.add(this.passwordTextField);
        panel.add(passwordFieldPanel);

        this.add(panel,BorderLayout.NORTH);
    }

    private void renderActionPanel() {
        JPanel panel = new JPanel();
        this.cancelButton = this.createButton("Salir", panel);
        this.loginButton = this.createButton("Ingresar", panel);
        this.add(panel,BorderLayout.SOUTH);
    }

    private void handleLogin() {
        var userName = userNameTextField.getText();
        if (userName == null || userName.equals("")) {
            JOptionPane.showMessageDialog(null, "El campo 'Usuario' tiene un valor inválido", "Usuario inválido",
                    JOptionPane.ERROR_MESSAGE, null);
            return;
        }
        var password = passwordTextField.getText();
        if (password == null || password.equals("")) {
            JOptionPane.showMessageDialog(null, "El campo 'Contraseña' tiene un valor inválido", "Contraseña inválida",
                    JOptionPane.ERROR_MESSAGE, null);
            return;
        }

        var response = userController.getUser(userNameTextField.getText(), passwordTextField.getText());
        if (!response.getValue0()) {
            JOptionPane.showMessageDialog(null, "El usuario o la contraseña es incorrecta", "Error",
                    JOptionPane.ERROR_MESSAGE, null);
            return;
        }

        new MenuFrame();
        this.dispose();
    }

    private void handleCancel() {
        this.dispose();
    }
}