package com.loo.tp.ui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.javatuples.Pair;

import com.loo.tp.ControllerFactory;
import com.loo.tp.controllers.PrintController;
import com.loo.tp.entities.Print;
import com.loo.tp.utils.InstantUtils;

public class PrintInfoFrame extends ContextualFrame<Object[]> {
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
        Pair<Boolean, Print> result = printController.getById((long) this.context[0]);
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
        this.setPreferredSize(new Dimension(300, 200));
        this.renderNorthPanel();
        this.renderSouthPanel();
    }

    private void renderNorthPanel() {
        var panel = new JPanel();
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
            new UserInfoFrame((long)this.context[2]);
        } else {
            new MenuFrame();
        }
        this.close();
    }

}
