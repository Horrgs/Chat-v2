package org.horrgs.chat.client.windows;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Horrgs on 1/11/2016.
 */
public class MainMenu {
    public JComponent getCreateAccountWindow() {
        JPanel jPanel = new JPanel();
        GridBagConstraints gbc = new GridBagConstraints();
        JTextField email, username;
        JPasswordField[] passwords = new JPasswordField[2];
        JButton createAccount, goBack;
        jPanel.setLayout(new GridBagLayout());
        gbc.gridx = 0;
        gbc.gridy = 0;
        jPanel.add(addExampleField("Email: "), gbc);
        gbc.gridy = 1;
        jPanel.add(addExampleField("Username: "), gbc);
        gbc.gridy = 2;
        jPanel.add(addExampleField("Password: "), gbc);
        gbc.gridy = 3;
        jPanel.add(addExampleField("Confirm Password: "), gbc);
        gbc.gridx = 1;
        gbc.gridy = 0;

        email = new JTextField("", 15);
        jPanel.add(email, gbc);

        gbc.gridy = 1;

        username = new JTextField("", 15);
        jPanel.add(username, gbc);

        for(int x = 0; x < passwords.length; x++) {
            gbc.gridy += 1;
            passwords[x] = new JPasswordField("", 15);
            jPanel.add(passwords[x], gbc);
        }
        gbc.gridy +=1;
        gbc.gridx = 0;
        createAccount = new JButton("Create Account");
        //TODO: prompt license & add action listener.
        createAccount.addActionListener();
        jPanel.add(createAccount, gbc);

        gbc.gridx = 1;
        goBack = new JButton("Go Back");
        //TODO: Prompt that you're removing entered info & add action listener.
        createAccount.addActionListener();
        jPanel.add(goBack, gbc);
        return jPanel;
    }

    public JComponent getLoginWindow() {
        JTextField emailOrUsername;
        JPasswordField password;
        JButton login, goBack;

    }

    public JTextField addExampleField(String text) {
        JTextField exampleField = new JTextField(text);
        exampleField.setBackground(new Color(255, 255, 255));
        exampleField.setBorder(BorderFactory.createLineBorder(new Color(255, 255, 255)));
        exampleField.setEditable(false);
        return exampleField;
    }
}
