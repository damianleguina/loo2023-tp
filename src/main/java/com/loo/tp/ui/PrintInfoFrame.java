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
import com.loo.tp.controllers.SessionController;
import com.loo.tp.entities.Print;
import com.loo.tp.enums.PrintStatus;
import com.loo.tp.ui.utils.builder.ActionPanelBuilder;
import com.loo.tp.utils.InstantUtils;

public class PrintInfoFrame extends ContextFrame<Object[]> {
    private PrintController printController;
    private SessionController sessionController;
    private boolean adminMode;

    private Print print;

    private JLabel statusLabel;
    private JLabel startDateLabel;
    private JLabel endDateLabel;
    private JLabel deliveryDateLabel;
    private JButton goBackButton;
    private JButton changeStatusButton;

    public PrintInfoFrame(Object[] context) {
        super(context);
    }

    // region Event Handlers
    @Override
    public void actionPerformed(ActionEvent arg0) {
        var source = arg0.getSource();
        if (source == goBackButton) {
            this.handleGoBackButton();
        } else if (source == changeStatusButton) {
            this.handleChangeStatusButtonClicked();
        }
    }
    // endregion Event Handlers

    // region Override Methods
    @Override
    protected void init() {
        this.printController = ControllerFactory.getPrintController();
        var result = printController.getById((long) this.context[0]);
        this.print = result.getValue1();
        sessionController = ControllerFactory.getSessionController();
        adminMode = sessionController.getCurrentUser().isAdmin();
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
        this.renderSouthPanel();
        this.refreshFrame();
    }
    // endregion Override Methods

    // region Private Methods
    private void renderNorthPanel() {
        var panel = new JPanel();
        panel.add(Box.createRigidArea(new Dimension(10, 5)));
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.add(new JLabel("Id: " + Long.toString(print.getId())));
        panel.add(new JLabel("Usuario: " + print.getUser().getName()));
        panel.add(new JLabel("Cantidad: " + Long.toString(print.getQuantity())));
        panel.add(new JLabel("Calidad: " + print.getQuality().toString()));
        statusLabel = new JLabel();
        panel.add(statusLabel);
        startDateLabel = new JLabel();
        panel.add(startDateLabel);
        endDateLabel = new JLabel();
        panel.add(endDateLabel);
        deliveryDateLabel = new JLabel();
        panel.add(deliveryDateLabel);
        this.add(panel, BorderLayout.NORTH);
    }

    private void renderSouthPanel() {
        var actionPanelBuilder = new ActionPanelBuilder(this);

        this.goBackButton = actionPanelBuilder.createButton("Volver");

        this.changeStatusButton = actionPanelBuilder.createButton("");

        this.add(actionPanelBuilder.build(), BorderLayout.SOUTH);
    }

    private void refreshFrame() {
        statusLabel.setText("Estado: " + print.getStatus().toString());
        startDateLabel.setText("Fecha inicio: " + InstantUtils.format(print.getStartDate()));
        endDateLabel.setText("Fecha fin: " + InstantUtils.format(print.getEndDate()));
        deliveryDateLabel.setText("Fecha de entrega: " + InstantUtils.format(print.getDeliveryDate()));

        PrintStatus nextStatus = PrintStatus.getNexStatus(this.print.getStatus());
        String changeStatusButtonLabel = String.format("Cambiar estado a '%s'",
                nextStatus != null ? nextStatus.toString() : "");

        changeStatusButton.setText(changeStatusButtonLabel);
        if (!this.adminMode || print.getStatus() == PrintStatus.DELIVERED) {
            this.changeStatusButton.setVisible(false);
        }
    }

    // region Action Handlers
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

    private void handleChangeStatusButtonClicked() {
        var response = this.printController.advanceStatus(this.print.getId());
        if (!response.getValue0()) {
            this.showErrorDialog(response.getValue2());
            return;
        }
        this.print = response.getValue1();
        this.refreshFrame();
        this.showSuccessDialog("Estado actualizado correctamente.");
    }
    // endregion Action Handlers
    // region Private Methods
}
