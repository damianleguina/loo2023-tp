package com.loo.tp.ui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;

import com.loo.tp.ControllerFactory;
import com.loo.tp.controllers.PrintController;
import com.loo.tp.controllers.SessionController;
import com.loo.tp.entities.Print;
import com.loo.tp.enums.PrintQuality;
import com.loo.tp.enums.PrintStatus;
import com.loo.tp.ui.utils.builder.ActionPanelBuilder;
import com.loo.tp.ui.utils.builder.FormPanelBuilder;

public class AddPrintFrame extends AppFrame {
    private SessionController sessionController;
    private PrintController printController;

    private JButton goBackButton;
    private JButton addPrintButton;

    private JSpinner quantitySpinner;
    private JComboBox<String> qualityComboBox;

    // region Event Handlers
    @Override
    public void actionPerformed(ActionEvent arg0) {
        var source = arg0.getSource();
        if (source == goBackButton) {
            this.handleBackButtonClicked();
        } else if (source == addPrintButton) {
            this.handleAddPrintButton();
        }
    }
    // endregion Event Handlers

    // region Protected Methods
    @Override
    protected void init() {
        sessionController = ControllerFactory.getSessionController();
        printController = ControllerFactory.getPrintController();
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

        var user = sessionController.getCurrentUser();
        var textField = formBuilder.addTextField("Usuario:");
        textField.setText(user.getName());
        textField.setEnabled(false);

        quantitySpinner = formBuilder.addSpinner("Cantidad:");
        var spinnerModel = new SpinnerNumberModel(1, 1, 10, 1);
        quantitySpinner.setModel(spinnerModel);
        // Stolen from https://stackoverflow.com/questions/2902101/how-to-set-jspinner-as-non-editable
        ((JSpinner.DefaultEditor) quantitySpinner.getEditor()).getTextField().setEditable(false);

        qualityComboBox = formBuilder.addComboBox("Calidad: ", new String[] {
                PrintQuality.COLOR.toString(),
                PrintQuality.BLACK_AND_WHITE.toString()
        });

        this.add(formBuilder.build(), BorderLayout.CENTER);
    }

    private void renderSouthPanel() {
        var actionPanelBuilder = new ActionPanelBuilder(this);

        goBackButton = actionPanelBuilder.createButton("Volver");
        addPrintButton = actionPanelBuilder.createButton("Agregar trabajo");

        this.add(actionPanelBuilder.build(), BorderLayout.SOUTH);
    }

    // region Action Handlers
    private void handleBackButtonClicked() {
        new MenuFrame();
        this.close();
    }

    private void handleAddPrintButton() {
        int quantity;
        try {
            quantity = (Integer) quantitySpinner.getValue();
        } catch (Exception e) {
            quantity = 0;
        }
        if (quantity < 1 || quantity > 10) {
            this.showErrorDialog("Cantidad de copias inválida");
            return;
        }
        var quality = PrintQuality.findByValue((String) qualityComboBox.getSelectedItem());
        Print print = new Print(
                1,
                sessionController.getCurrentUserId(),
                quality,
                quantity,
                null,
                null,
                null,
                null);

        var response = printController.addPrint(print);

        if (!response.getValue0()) {
            this.showErrorDialog(response.getValue2());
            return;
        }

        this.quantitySpinner.setValue(1);
        this.qualityComboBox.setSelectedIndex(0);
        this.showSuccessDialog("Trabajo guardado correctamente.");
    }
    // endregion Action Handlers
    // endregion Private Methods
}
