package org.silnith.browser.swing.factory;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JTabbedPane;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;


public class FrameFactory {

    private MenuFactory menuFactory;

    
    public MenuFactory getMenuFactory() {
        return menuFactory;
    }

    
    public void setMenuFactory(MenuFactory menuFactory) {
        this.menuFactory = menuFactory;
    }

    public JFrame getFrame() {
        final JTabbedPane jTabbedPane = new JTabbedPane();
        jTabbedPane.setTabPlacement(SwingConstants.TOP);
        jTabbedPane.setTabLayoutPolicy(JTabbedPane.WRAP_TAB_LAYOUT);

        final JFrame jFrame = new JFrame("Browser");
        jFrame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        jFrame.setJMenuBar(menuFactory.getMenuBar(jFrame, jTabbedPane));
        jFrame.getContentPane().add(jTabbedPane, BorderLayout.CENTER);
        return jFrame;
    }

}
