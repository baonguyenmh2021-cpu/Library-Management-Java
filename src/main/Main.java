package main;

import com.formdev.flatlaf.FlatLightLaf;
import javax.swing.UIManager;

import view.LoginForm;

public class Main {
    public static void main(String[] args) {
        try { 
           UIManager.setLookAndFeel(new FlatLightLaf());
        } catch (Exception e) {
            e.printStackTrace();
        }
        javax.swing.SwingUtilities.invokeLater(() -> {
            new LoginForm().setVisible(true);
        });
    }
}
