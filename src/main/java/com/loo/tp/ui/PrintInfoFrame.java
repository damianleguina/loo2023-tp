package com.loo.tp.ui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.loo.tp.ControllerFactory;
import com.loo.tp.controllers.PrintController;
import com.loo.tp.entities.Print;
import com.loo.tp.enums.PrintStatus;
import com.loo.tp.utils.InstantUtils;

public class PrintInfoFrame extends ContextFrame<Object[]> {
    private PrintController printController;

    private Print print;

    private JButton goBackButton;

    public PrintInfoFrame(Object[] context) {
        super(context);
    }

    @Override
    public void actionPerformed(ActionEvent arg0) {
        var source = arg0.getSource();
        if (source == goBackButton) {
            this.handleGoBackButton();
        }
    }

    @Override
    protected void init() {
        this.printController = ControllerFactory.getPrintController();
        var result = printController.getById((long) this.context[0]);
        this.print = result.getValue1();
    }

    @Override
    protected boolean shouldRender() {
        return this.print != null;
    }

    @Override
    protected void onFailure() {
        new MenuFrame();
        this.close();
    }

    @Override
    protected void render() {
        this.renderNorthPanel();
        this.renderCenterPanel();
        this.renderSouthPanel();
    }

    private void renderNorthPanel() {
        var panel = new JPanel();
        panel.add(Box.createRigidArea(new Dimension(10, 5)));
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.add(new JLabel("Id: " + Long.toString(print.getId())));
        panel.add(new JLabel("Usuario: " + print.getUser().getName()));
        panel.add(new JLabel("Cantidad: " + Long.toString(print.getQuantity())));
        panel.add(new JLabel("Calidad: " + print.getQuality().toString()));
        panel.add(new JLabel("Estado: " + print.getStatus().toString()));
        panel.add(new JLabel("Fecha inicio: " + InstantUtils.format(print.getStartDate())));
        panel.add(new JLabel("Fecha fin: " + InstantUtils.format(print.getEndDate())));
        panel.add(new JLabel("Fecha de entrega: " + InstantUtils.format(print.getDeliveryDate())));
        this.add(panel, BorderLayout.NORTH);
    }

    private void renderCenterPanel() {
        var panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.add(Box.createRigidArea(new Dimension(10, 5)));

        var statusPanel = new JPanel();
        statusPanel.setLayout(new BoxLayout(statusPanel, BoxLayout.X_AXIS));
        statusPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        var dimension = new Dimension(300, 20);
        statusPanel.setMaximumSize(dimension);
        statusPanel.setMinimumSize(dimension);
        statusPanel.setPreferredSize(dimension);

        statusPanel.add(new JLabel("Cambiar estado"));
        var statusComboBox = new JComboBox<String>(new String[] {
            PrintStatus.PENDING.toString(),
            PrintStatus.RECEIVED.toString(),
            PrintStatus.IN_PROGRESS.toString(),
            PrintStatus.FINISHED.toString(),
        });
        statusPanel.add(statusComboBox);
        panel.add(statusPanel);
        this.add(panel, BorderLayout.CENTER);
    }

    private void renderSouthPanel() {
        var panel = new JPanel();
        this.goBackButton = this.createButton("Volver", panel);
        this.add(panel, BorderLayout.SOUTH);
    }

    private void handleGoBackButton() {
        if (this.context.length < 2) {
            new MenuFrame();
            this.close();
            return;
        }
        var referrer = (String) this.context[1];
        if (referrer.equals("PrintsListFrame")) {
            new PrintsListFrame();
        } else if (referrer.equals("UserInfoFrame")) {
            new UserInfoFrame((long) this.context[2]);
        } else {
            new MenuFrame();
        }
        this.close();
    }

}
