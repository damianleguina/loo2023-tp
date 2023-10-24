package com.loo.tp;

import com.loo.tp.controllers.UserController;
import com.loo.tp.ui.LoginFrame;

public class App 
{
    public static void main( String[] args )
    {
        UserController userController = ControllerFactory.getUserController();
        LoginFrame loginFrame = new LoginFrame();
        loginFrame.setVisible(true);
    }
}
