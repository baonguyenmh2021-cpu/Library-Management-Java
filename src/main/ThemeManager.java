package main;

import java.awt.Window;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import com.formdev.flatlaf.*;
import com.formdev.flatlaf.intellijthemes.*;
import com.formdev.flatlaf.intellijthemes.materialthemeuilite.*;

public class ThemeManager {

    public static void applyTheme(String theme) {
        try {
            switch (theme) {
                case "Flat Light":
                    UIManager.setLookAndFeel(new FlatLightLaf());
                    break;
                case "Flat Dark":
                    UIManager.setLookAndFeel(new FlatDarkLaf());
                    break;
                case "IntelliJ Light":
                    UIManager.setLookAndFeel(new FlatIntelliJLaf());
                    break;
                case "Nord":
                    UIManager.setLookAndFeel(new FlatNordIJTheme());
                    break;
                case "Carbon":
                    UIManager.setLookAndFeel(new FlatCarbonIJTheme());
                    break;
                case "Material Ocean":
                    UIManager.setLookAndFeel(new FlatMaterialOceanicIJTheme());
                    break;
                case "Material Palenight":
                    UIManager.setLookAndFeel(new FlatMaterialPalenightIJTheme());
                    break;
                case "GitHub Green":
                    UIManager.setLookAndFeel(new FlatGitHubIJTheme());
                    break;
                case "GitHub Dark Orange":
                    UIManager.setLookAndFeel(new FlatGitHubDarkIJTheme());
                    break;
                case "Dracula":
                    UIManager.setLookAndFeel(new com.formdev.flatlaf.intellijthemes.FlatDraculaIJTheme());
                    break;
                case "Material Darker":
                    UIManager.setLookAndFeel(new FlatMaterialDarkerIJTheme());
                    break;
                case "Solarized Light":
                    UIManager.setLookAndFeel(new com.formdev.flatlaf.intellijthemes.FlatSolarizedLightIJTheme());
                    break;
            }

            // 🌍 UPDATE TOÀN BỘ FORM ĐANG MỞ
            for (Window w : Window.getWindows()) {
                SwingUtilities.updateComponentTreeUI(w);
                w.pack();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
