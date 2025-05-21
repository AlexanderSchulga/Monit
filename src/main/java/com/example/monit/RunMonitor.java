package com.example.monit;

public class RunMonitor {

    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                OpenForm h = new OpenForm();
                h.openFrame();
            }

        });
    }

}

