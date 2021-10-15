package org.silnith.browser.swing.factory;

import java.awt.event.KeyEvent;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JTabbedPane;

import org.silnith.browser.swing.action.factory.ActionFactory;

public class MenuFactory {
    
    private FrameFactory frameFactory;
    
    private ActionFactory actionFactory;
    
    
    
    public FrameFactory getFrameFactory() {
        return frameFactory;
    }


    
    public void setFrameFactory(FrameFactory frameFactory) {
        this.frameFactory = frameFactory;
    }


    public ActionFactory getActionFactory() {
        return actionFactory;
    }

    
    public void setActionFactory(ActionFactory actionFactory) {
        this.actionFactory = actionFactory;
    }

    public JMenuBar getMenuBar(final JFrame jFrame, final JTabbedPane jTabbedPane) {
        final JMenuBar jMenuBar = new JMenuBar();
        jMenuBar.add(getApplicationMenu(frameFactory));
        jMenuBar.add(getFrameMenu(jFrame));
        jMenuBar.add(getTabMenu(jFrame, jTabbedPane));
        return jMenuBar;
    }
    
    public JMenu getApplicationMenu(final FrameFactory frameFactory) {
        final JMenu jMenu = new JMenu("Application");
        jMenu.setMnemonic(KeyEvent.VK_A);
        jMenu.add(actionFactory.getNewFrameAction(frameFactory));
        return jMenu;
    }
    
    public JMenu getFrameMenu(final JFrame jFrame) {
        final JMenu jMenu = new JMenu("Frame");
        jMenu.setMnemonic(KeyEvent.VK_A);
        jMenu.add(actionFactory.getCloseFrameAction(jFrame));
        return jMenu;
    }
    
    public JMenu getTabMenu(final JFrame jFrame, final JTabbedPane jTabbedPane) {
        final JMenu jMenu = new JMenu("Tab");
        jMenu.setMnemonic(KeyEvent.VK_T);
        jMenu.add(actionFactory.getNewTabAction(jTabbedPane));
        return jMenu;
    }

}
