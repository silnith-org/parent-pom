package org.silnith.browser;

import java.lang.reflect.InvocationTargetException;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import org.silnith.browser.swing.action.factory.ActionFactory;
import org.silnith.browser.swing.factory.FrameFactory;
import org.silnith.browser.swing.factory.MenuFactory;

public class EntryPoint {

    public static void main(String[] args) throws InvocationTargetException, InterruptedException {
        final ActionFactory actionFactory = new ActionFactory();
        final MenuFactory menuFactory = new MenuFactory();
        menuFactory.setActionFactory(actionFactory);
        final FrameFactory frameFactory = new FrameFactory();
        menuFactory.setFrameFactory(frameFactory);
        frameFactory.setMenuFactory(menuFactory);
        final JFrame jFrame = frameFactory.getFrame();
        SwingUtilities.invokeAndWait(() -> {
            jFrame.pack();
            jFrame.setVisible(true);
        });
    }

}
