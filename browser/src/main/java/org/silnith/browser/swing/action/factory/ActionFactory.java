package org.silnith.browser.swing.action.factory;

import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

import javax.swing.Action;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JTabbedPane;
import javax.swing.KeyStroke;

import org.silnith.browser.swing.action.CloseFrameAction;
import org.silnith.browser.swing.action.NewFrameAction;
import org.silnith.browser.swing.action.NewTabAction;
import org.silnith.browser.swing.factory.FrameFactory;

public class ActionFactory {
    
    public NewFrameAction getNewFrameAction(final FrameFactory frameFactory) {
        final Icon largeIcon = null;
        final Icon smallIcon = new ImageIcon();
        
        final NewFrameAction newFrameAction = new NewFrameAction();
        newFrameAction.setFrameFactory(frameFactory);
        newFrameAction.putValue(Action.SHORT_DESCRIPTION, "Opens a new frame.");
//        Action.ACTION_COMMAND_KEY;
        newFrameAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_N);
        newFrameAction.putValue(Action.NAME, "New Frame");
//        newFrameAction.putValue(Action.DISPLAYED_MNEMONIC_INDEX_KEY, -1);
//        newFrameAction.putValue(Action.LARGE_ICON_KEY, largeIcon);
//        newFrameAction.putValue(Action.SMALL_ICON, smallIcon);
        newFrameAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_N, 0 | InputEvent.META_DOWN_MASK));
        
        return newFrameAction;
    }
    
    public CloseFrameAction getCloseFrameAction(final JFrame jFrame) {
        final CloseFrameAction closeFrameAction = new CloseFrameAction(jFrame);
        closeFrameAction.putValue(Action.SHORT_DESCRIPTION, "Closes the frame.");
//        Action.ACTION_COMMAND_KEY;
        closeFrameAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_C);
        closeFrameAction.putValue(Action.NAME, "Close Frame");
//        closeFrameAction.putValue(Action.DISPLAYED_MNEMONIC_INDEX_KEY, -1);
//        closeFrameAction.putValue(Action.LARGE_ICON_KEY, largeIcon);
//        closeFrameAction.putValue(Action.SMALL_ICON, smallIcon);
//        closeFrameAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_C, 0 | InputEvent.META_DOWN_MASK));
        
        return closeFrameAction;
    }
    
    public NewTabAction getNewTabAction(final JTabbedPane jTabbedPane) {
        final NewTabAction newTabAction = new NewTabAction(jTabbedPane);
        newTabAction.putValue(Action.SHORT_DESCRIPTION, "Opens a new tab.");
//        Action.ACTION_COMMAND_KEY;
        newTabAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_T);
        newTabAction.putValue(Action.NAME, "New Tab");
//        newTabAction.putValue(Action.DISPLAYED_MNEMONIC_INDEX_KEY, -1);
//        newTabAction.putValue(Action.LARGE_ICON_KEY, largeIcon);
//        newTabAction.putValue(Action.SMALL_ICON, smallIcon);
        newTabAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_T, 0 | InputEvent.META_DOWN_MASK));
        
        return newTabAction;
    }

}
