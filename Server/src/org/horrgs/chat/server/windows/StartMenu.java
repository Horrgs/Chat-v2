package org.horrgs.chat.server.windows;

import oracle.jrockit.jfr.JFR;
import org.horrgs.chat.server.ConnectionHandle;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Horrgs on 11/22/2015.
 */
public class StartMenu extends JFrame {
    public JButton start,end;
    public StartMenu() {
        setSize(400, 400);
        setTitle("Server");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLayout(new GridBagLayout());
        GridBagConstraints gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        start = new JButton("Start");
        start.addActionListener(new WindowListener());
        add(start, gridBagConstraints);

        gridBagConstraints.gridx = 1;
        end = new JButton("End");
        add(end, gridBagConstraints);

        setVisible(true);
    }

    public class WindowListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent ev) {
            ConnectionHandle connectionHandle = new ConnectionHandle();
            if(ev.getSource() == start) {
                connectionHandle.start();
            } else {
                connectionHandle.end("Closed by host request.");
            }
        }
    }
}