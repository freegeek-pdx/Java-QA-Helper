/*
 *
 * MIT License
 *
 * Copyright (c) 2019 Free Geek
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"),
 * to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense,
 * and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY,
 * WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 *
 */
package GUI;

import Utilities.*;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import javax.swing.JFrame;
import javax.swing.SwingWorker;

/**
 * @author Pico Mitchell (of Free Geek)
 */
public class QAScreenTestWindow extends javax.swing.JFrame {

    private int clickCount = 0;
    private long lastChange = new Date().getTime();
    private boolean disableTimeLimit = false;

    /**
     * Creates new form QAScreenTestWindow
     *
     * @param shouldDisableTimeLimit
     */
    public QAScreenTestWindow(boolean shouldDisableTimeLimit) {
        initComponents();
        finishSetup();

        disableTimeLimit = shouldDisableTimeLimit;
    }

    private void finishSetup() {
        getContentPane().setBackground(Color.RED);
        getRootPane().setDefaultButton(btnChangeColor);

        GraphicsDevice defaultScreenDevice = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();

        if (defaultScreenDevice.isFullScreenSupported()) {
            defaultScreenDevice.setFullScreenWindow(this);
        } else {
            setExtendedState(JFrame.MAXIMIZED_BOTH);
        }

        (new SwingWorker<Void, Void>() {
            @Override
            protected Void doInBackground() throws Exception {
                TimeUnit.SECONDS.sleep(1); // Wait a second to make sure the window is fully setup before bringing to front and hiding cursor.

                return null;
            }

            @Override
            protected void done() {
                if (isVisible()) {
                    setVisible(true);
                    toFront();
                    requestFocus();

                    getContentPane().setCursor(Toolkit.getDefaultToolkit().createCustomCursor(new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB), new Point(0, 0), "blank cursor"));
                }
            }
        }).execute();

    }

    /**
     * This method is called from within the constructor to initialize the form. WARNING: Do NOT modify this code. The content of this method is always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        btnChangeColor = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("QA Helper  —  Solid Colors Screen Test");
        setAlwaysOnTop(true);
        setIconImages(new TwemojiImage("AppIcon", this).toImageIconsForFrame());
        setLocationByPlatform(true);
        setName("solidColorScreenTestFrame"); // NOI18N
        setUndecorated(true);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowIconified(java.awt.event.WindowEvent evt) {
                formWindowIconified(evt);
            }
        });

        btnChangeColor.setBorder(null);
        btnChangeColor.setBorderPainted(false);
        btnChangeColor.setContentAreaFilled(false);
        btnChangeColor.setFocusPainted(false);
        btnChangeColor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnChangeColorActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(btnChangeColor, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(btnChangeColor, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnChangeColorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnChangeColorActionPerformed
        if (disableTimeLimit || new Date().getTime() - lastChange >= 1000) {
            // Force people to stay on each color for at least 1 second
            btnChangeColor.setText("");

            switch (clickCount) {
                case 0:
                    getContentPane().setBackground(Color.GREEN);
                    break;
                case 1:
                    getContentPane().setBackground(Color.BLUE);
                    break;
                case 2:
                    getContentPane().setBackground(Color.BLACK);
                    break;
                case 3:
                    getContentPane().setBackground(Color.WHITE);
                    break;
                default:
                    setTitle(getTitle() + "  —  FINISHED");
                    getContentPane().setCursor(Cursor.getDefaultCursor());

                    dispose();
                    break;
            }

            lastChange = new Date().getTime();
            clickCount++;
        } else {
            if (btnChangeColor.getText().isEmpty()) {
                lastChange = new Date().getTime(); // Reset time when someone clicks too early. This will stop people from just rapid clicking and give time to read the message.
            }
            btnChangeColor.setText(btnChangeColor.getText().isEmpty() ? "<html><b style='font-size: larger; color: " + ((clickCount == 1 || clickCount == 4) ? "black" : "white") + "'><i>Please examine each solid color screen for more than 1 second.</i></b></html>" : "");
        }
    }//GEN-LAST:event_btnChangeColorActionPerformed

    private void formWindowIconified(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowIconified
        setExtendedState(JFrame.MAXIMIZED_BOTH);
    }//GEN-LAST:event_formWindowIconified

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnChangeColor;
    // End of variables declaration//GEN-END:variables
}
