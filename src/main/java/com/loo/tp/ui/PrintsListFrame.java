package com.loo.tp.ui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;

import javax.swing.JButton;
import javax.swing.event.ListSelectionEvent;
import javax.swing.table.DefaultTableModel;

import com.loo.tp.ControllerFactory;
import com.loo.tp.controllers.PrintController;
import com.loo.tp.controllers.SessionController;
import com.loo.tp.entities.Print;
import com.loo.tp.enums.PrintStatus;
import com.loo.tp.ui.utils.builder.ActionPanelBuilder;
import com.loo.tp.ui.utils.table.TableFrame;
import com.loo.tp.ui.utils.table.TableWrapper;
import com.loo.tp.utils.InstantUtils;

public class PrintsListFrame extends AppFrame implements TableFrame {
    private SessionController sessionController;
    private PrintController printController;
    private boolean adminMode;

    private TableWrapper table;
    private JButton goBackButton;
    private JButton viewPrintButton;
    private JButton deletePrintsButton;

    private Object[][] data;

    public PrintsListFrame() {
        super();
    }

    // region Event Handlers
    @Override
    public void actionPerformed(ActionEvent arg0) {
        var source = arg0.getSource();
        if (source == this.goBackButton) {
            this.handleGoBackButtonClicked();
        } else if (source == this.viewPrintButton) {
            this.handleViewPrintButtonClicked();
        } else if (source == this.deletePrintsButton) {
            this.handleDeletePrintsButtonClicked();
        }
    }

    @Override
    public void valueChanged(ListSelectionEvent arg0) {
        int[] indexes = this.table.getSelectedIndexes();

        this.viewPrintButton.setVisible(false);
        this.deletePrintsButton.setVisible(false);

        if (indexes.length == 1) {
            this.viewPrintButton.setVisible(true);
        }

        if (indexes.length == 1 && !this.adminMode && this.getSelectedPrint().getStatus() == PrintStatus.PENDING) {
            this.deletePrintsButton.setVisible(true);
        }

        if (indexes.length > 1 && !this.adminMode) {
            this.deletePrintsButton.setVisible(true);
        }
    }

    @Override
    public DefaultTableModel getTableModel() {
        var result = adminMode ? printController.get() : printController.get(sessionController.getCurrentUserId());
        var prints = result.getValue1();
        String[] columnNames = { "Id", "Usuario", "Cantidad", "Calidad", "Estado", "Fecha inicio", "Fecha fin",
                "Fecha entrega" };
        data = new Object[prints.length][];
        for (int i = 0; i < prints.length; i++) {
            Print print = prints[i];
            data[i] = new Object[] {
                    print.getId(),
                    print.getUser().getName(),
                    print.getQuantity(),
                    print.getQuality().toString(),
                    print.getStatus().toString(),
                    InstantUtils.format(print.getStartDate()),
                    InstantUtils.format(print.getEndDate()),
                    InstantUtils.format(print.getDeliveryDate()),
                    print
            };
        }

        return new DefaultTableModel(data, columnNames);
    }
    // endregion Event Handlers

    // region Protected Methods
    @Override
    protected void init() {
        sessionController = ControllerFactory.getSessionController();
        printController = ControllerFactory.getPrintController();
        adminMode = sessionController.getCurrentUser().isAdmin();
    }

    @Override
    protected boolean shouldRender() {
        return sessionController.isLoggedIn();
    }

    @Override
    protected void onFailure() {
        new MenuFrame();
        this.close();
    }

    @Override
    protected void render() {
        this.renderCenterPanel();
        this.renderSouthPanel();
    }
    // endregion Protected Methods

    // region Private Methods
    private void renderCenterPanel() {
        this.table = new TableWrapper(this);
        this.add(this.table.getTablePane(), BorderLayout.CENTER);
    }

    private void renderSouthPanel() {
        var actionPanelBuilder = new ActionPanelBuilder(this);
        this.goBackButton = actionPanelBuilder.createButton("Volver");

        this.viewPrintButton = actionPanelBuilder.createButton("Ver trabajo");
        this.viewPrintButton.setVisible(false);

        this.deletePrintsButton = actionPanelBuilder.createButton("Borrar trabajos");
        this.deletePrintsButton.setVisible(false);

        this.add(actionPanelBuilder.build(), BorderLayout.SOUTH);
    }

    private Print getSelectedPrint() {
        int index = this.table.getSelectedIndexes()[0];
        return (Print) data[index][8];
    }

    private Print[] getSelectedPrints() {
        int[] indexes = this.table.getSelectedIndexes();
        Print[] prints = new Print[indexes.length];
        for (int i = 0; i < indexes.length; i++) {
            prints[i] = (Print) data[indexes[i]][8];
        }
        return prints;
    }

    private long[] getSelectedPrintsIds() {
        var prints = this.getSelectedPrints();
        var ids = new long[prints.length];
        for (int i = 0; i < prints.length; i++) {
            ids[i] = prints[i].getId();
        }
        return ids;
    }

    // region Action Handlers
    private void handleGoBackButtonClicked() {
        new MenuFrame();
        this.close();
    }

    private void handleViewPrintButtonClicked() {
        var selectedPrint = this.getSelectedPrint();
        new PrintInfoFrame(new Object[] { selectedPrint.getId(), this.getClass().getSimpleName() });
        this.close();
    }

    private void handleDeletePrintsButtonClicked() {
        var printsIds = this.getSelectedPrintsIds();
        var response = this.printController.deletePrints(printsIds);
        if (!response.getValue0()) {
            this.showErrorDialog(response.getValue2());
            return;
        }
        this.table.refreshModel();
        this.showSuccessDialog("Trabajos borrados exitosamente");
    }
    // endregion Action Handlers
    // endregion Private Methods
}
