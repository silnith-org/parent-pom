package org.silnith.browser.swing.action;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.JFrame;

import org.silnith.browser.swing.factory.FrameFactory;


public class NewFrameAction extends AbstractAction {
    
    private FrameFactory frameFactory;

    
    public NewFrameAction() {
        super();
    }


    public FrameFactory getFrameFactory() {
        return frameFactory;
    }

    
    public void setFrameFactory(FrameFactory frameFactory) {
        this.frameFactory = frameFactory;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        final JFrame frame = frameFactory.getFrame();
        frame.pack();
        frame.setVisible(true);
    }

}
