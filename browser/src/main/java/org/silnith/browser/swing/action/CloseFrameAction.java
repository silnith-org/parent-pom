package org.silnith.browser.swing.action;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.JFrame;


public class CloseFrameAction extends AbstractAction {
    
    private final JFrame frame;

    public CloseFrameAction(JFrame frame) {
        super();
        this.frame = frame;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        frame.dispose();
    }

}
