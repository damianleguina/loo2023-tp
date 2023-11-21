package com.loo.tp;

import com.loo.tp.ui.AddPrintFrame;
import com.loo.tp.ui.AddUserFrame;
import com.loo.tp.ui.LoginFrame;
import com.loo.tp.ui.MenuFrame;
import com.loo.tp.ui.PrintInfoFrame;
import com.loo.tp.ui.PrintsListFrame;
import com.loo.tp.ui.UserInfoFrame;
import com.loo.tp.ui.UsersListFrame;

public class App {
    public static void main(String[] args) {
        ControllerFactory.init();
        var userController = ControllerFactory.getUserController();
        // userController.getUser("Blinky", "qwerty");
        userController.getUser("Pinky", "dvorak");
        // new PrintsListFrame();
        new PrintsListFrame();
    }
}
