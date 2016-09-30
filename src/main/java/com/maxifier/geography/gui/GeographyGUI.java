package com.maxifier.geography.gui;/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */


import com.google.inject.Guice;
import com.google.inject.Injector;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

import com.maxifier.geography.GeographyModule;
import com.maxifier.geography.gui.GridRenderer;
import com.maxifier.geography.interpolation.model.Grid;
import com.maxifier.geography.interpolation.GridInterpolator;
import com.maxifier.geography.interpolation.model.Point;
import com.maxifier.geography.interpolation.model.Polygon;
import com.maxifier.geography.util.GridCSVHelper;

public class GeographyGUI extends javax.swing.JFrame {

    public static final String STOP = "Stop";
    public static final String START = "Start";
    private ExecutorService executorService = Executors.newSingleThreadExecutor();
    private Lock lock = new ReentrantLock();
    private Future runningTask;

    private final GridCSVHelper gridCSVHelper;

    private final GridRenderer gridRenderer;

    private final GridInterpolator gridInterpolator;

    public GeographyGUI() {
        Injector injector = Guice.createInjector(new GeographyModule());
        this.gridCSVHelper = injector.getInstance(GridCSVHelper.class);
        this.gridRenderer = injector.getInstance(GridRenderer.class);
        this.gridInterpolator = injector.getInstance(GridInterpolator.class);
        initComponents();
        setResizable(false);
        NonNegativeIntegerInputVerifier nonNegativeIntegerInputVerifier = new NonNegativeIntegerInputVerifier();
        gridStepXTextField.setInputVerifier(nonNegativeIntegerInputVerifier);
        gridStepYTextField.setInputVerifier(nonNegativeIntegerInputVerifier);
        neighborCountTextField.setInputVerifier(nonNegativeIntegerInputVerifier);
        searchRadiousTextField.setInputVerifier(nonNegativeIntegerInputVerifier);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        probeDataLabel = new javax.swing.JLabel();
        probeDataTextField = new javax.swing.JTextField();
        probeDataButton = new javax.swing.JButton();
        boundariesLabel = new javax.swing.JLabel();
        boundariesTextField = new javax.swing.JTextField();
        boundariesButton = new javax.swing.JButton();
        gridStepXTextField = new javax.swing.JTextField();
        launchButton = new javax.swing.JButton();
        gridStepLabel = new javax.swing.JLabel();
        neighborCountLabel = new javax.swing.JLabel();
        neighborCountTextField = new javax.swing.JTextField();
        searchRadiusLabel = new javax.swing.JLabel();
        searchRadiousTextField = new javax.swing.JTextField();
        gridStepYLabel = new javax.swing.JLabel();
        gridStepXLabel = new javax.swing.JLabel();
        gridStepYTextField = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setLocationByPlatform(true);

        probeDataLabel.setText("Probe Data");

        probeDataTextField.setEnabled(false);
        probeDataTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                probeDataTextFieldActionPerformed(evt);
            }
        });

        probeDataButton.setText("Choose");
        probeDataButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                probeDataButtonActionPerformed(evt);
            }
        });

        boundariesLabel.setText("Boundaries");

        boundariesTextField.setEnabled(false);
        boundariesTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                boundariesTextFieldActionPerformed(evt);
            }
        });

        boundariesButton.setText("Choose");
        boundariesButton.setActionCommand("");
        boundariesButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                boundariesButtonActionPerformed(evt);
            }
        });

        gridStepXTextField.setHorizontalAlignment(javax.swing.JTextField.TRAILING);
        gridStepXTextField.setText("1");
        gridStepXTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                gridStepXTextFieldActionPerformed(evt);
            }
        });

        launchButton.setText("Start");
        launchButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                launchButtonActionPerformed(evt);
            }
        });

        gridStepLabel.setText("Grid Step");

        neighborCountLabel.setText("Neighbor Count");

        neighborCountTextField.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        neighborCountTextField.setText("3");

        searchRadiusLabel.setText("Search Radius");

        searchRadiousTextField.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        searchRadiousTextField.setText("5");

        gridStepYLabel.setText("y");

        gridStepXLabel.setText("x");

        gridStepYTextField.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        gridStepYTextField.setText("1");
        gridStepYTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                gridStepYTextFieldActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(neighborCountLabel)
                            .addComponent(gridStepLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(19, 19, 19)
                        .addComponent(gridStepXLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 6, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(neighborCountTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(gridStepXTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(gridStepYLabel)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(gridStepYTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(searchRadiusLabel)
                                .addGap(18, 18, 18)
                                .addComponent(searchRadiousTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 15, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(probeDataLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(boundariesLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(boundariesTextField, javax.swing.GroupLayout.DEFAULT_SIZE, 235, Short.MAX_VALUE)
                            .addComponent(probeDataTextField, javax.swing.GroupLayout.DEFAULT_SIZE, 235, Short.MAX_VALUE))))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(probeDataButton, javax.swing.GroupLayout.DEFAULT_SIZE, 81, Short.MAX_VALUE)
                    .addComponent(boundariesButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(launchButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(probeDataLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(probeDataButton, javax.swing.GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE)
                    .addComponent(probeDataTextField, javax.swing.GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(boundariesButton, javax.swing.GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE)
                    .addComponent(boundariesTextField, javax.swing.GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE)
                    .addComponent(boundariesLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(launchButton, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(neighborCountLabel)
                    .addComponent(neighborCountTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(searchRadiusLabel)
                    .addComponent(searchRadiousTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(gridStepLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(gridStepXTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(gridStepYLabel)
                    .addComponent(gridStepXLabel)
                    .addComponent(gridStepYTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(27, 27, 27))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void gridStepXTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_gridStepXTextFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_gridStepXTextFieldActionPerformed

    private void probeDataTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_probeDataTextFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_probeDataTextFieldActionPerformed

    private void probeDataButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_probeDataButtonActionPerformed
        JFileChooser fileChooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter("CSV_Files", "csv");
        fileChooser.addChoosableFileFilter(filter);
        fileChooser.setFileFilter(filter);
        int ret = fileChooser.showDialog(null, "Open");
        if (ret == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            probeDataTextField.setText(file.getAbsolutePath());
        }
    }//GEN-LAST:event_probeDataButtonActionPerformed

    private void boundariesTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_boundariesTextFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_boundariesTextFieldActionPerformed

    private void boundariesButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_boundariesButtonActionPerformed
        JFileChooser fileChooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter("CSV_Files", "csv");
        fileChooser.addChoosableFileFilter(filter);
        fileChooser.setFileFilter(filter);
        int ret = fileChooser.showDialog(null, "Open");
        if (ret == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            boundariesTextField.setText(file.getAbsolutePath());
        }
    }//GEN-LAST:event_boundariesButtonActionPerformed

    private void launchButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_launchButtonActionPerformed
        if (lock.tryLock()) {
            try {
                if (START.equals(launchButton.getText())) {
                    launchButton.setText(STOP);
                    runningTask = executorService.submit((Runnable) () -> {
                        try {
                            Map<Point, Double> probeData = gridCSVHelper.read3DGrid(probeDataTextField.getText());
                            System.out.println(probeData);

                            List<Point> boundaries = gridCSVHelper.read2DGrid(boundariesTextField.getText());
                            System.out.println(boundaries);

                            Polygon polygon = new Polygon(boundaries);
                            int xStep = Integer.parseInt(gridStepXTextField.getText());
                            int yStep = Integer.parseInt(gridStepYTextField.getText());
                            int neighborCount = Integer.parseInt(neighborCountTextField.getText());
                            int searchRadius = Integer.parseInt(searchRadiousTextField.getText());
                            Grid interpolatedGrid = gridInterpolator.interpolate(
                                    new Grid(probeData, polygon, xStep, yStep),
                                    neighborCount,
                                    searchRadius
                            );
                            gridRenderer.render(interpolatedGrid);
                        } catch (IllegalArgumentException e) {
                            JOptionPane.showMessageDialog(
                                    this,
                                    e.getMessage(),
                                    "Error",
                                    JOptionPane.ERROR_MESSAGE
                            );
                        } catch (InterruptedException e) {
                            JOptionPane.showMessageDialog(
                                    this,
                                    "Grid interpolation has been interrupted",
                                    "Info",
                                    JOptionPane.INFORMATION_MESSAGE
                            );
                        } catch (IOException e) {
                            JOptionPane.showMessageDialog(
                                    this,
                                    "Error has occurred while trying to read csv file",
                                    "Error",
                                    JOptionPane.ERROR_MESSAGE
                            );
                        } catch (Exception e) {
                            e.printStackTrace();
                            JOptionPane.showMessageDialog(
                                    this,
                                    e.getMessage(),
                                    "Error",
                                    JOptionPane.ERROR_MESSAGE
                            );
                        } finally {
                            launchButton.setText(START);
                        }
                    });
                } else {
                    runningTask.cancel(true);
                    launchButton.setText(START);
                }
            } finally {
                lock.unlock();
            }
        }
    }//GEN-LAST:event_launchButtonActionPerformed

    private void gridStepYTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_gridStepYTextFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_gridStepYTextFieldActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(GeographyGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(GeographyGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(GeographyGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(GeographyGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new GeographyGUI().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton boundariesButton;
    private javax.swing.JLabel boundariesLabel;
    private javax.swing.JTextField boundariesTextField;
    private javax.swing.JLabel gridStepLabel;
    private javax.swing.JLabel gridStepXLabel;
    private javax.swing.JTextField gridStepXTextField;
    private javax.swing.JLabel gridStepYLabel;
    private javax.swing.JTextField gridStepYTextField;
    private javax.swing.JButton launchButton;
    private javax.swing.JLabel neighborCountLabel;
    private javax.swing.JTextField neighborCountTextField;
    private javax.swing.JButton probeDataButton;
    private javax.swing.JLabel probeDataLabel;
    private javax.swing.JTextField probeDataTextField;
    private javax.swing.JTextField searchRadiousTextField;
    private javax.swing.JLabel searchRadiusLabel;
    // End of variables declaration//GEN-END:variables

    static class NonNegativeIntegerInputVerifier extends InputVerifier {
        @Override
        public boolean verify(JComponent input) {
            String text = ((JTextField) input).getText();
            try {
                int value = Integer.parseInt(text);
                return value >= 0;
            } catch (NumberFormatException e) {
                return false;
            }
        }

        @Override
        public boolean shouldYieldFocus(JComponent input) {
            boolean valid = verify(input);
            if (!valid) {
                JOptionPane.showMessageDialog(null, "Field should contain non negative integer value");
            }
            return valid;
        }
    }
}
