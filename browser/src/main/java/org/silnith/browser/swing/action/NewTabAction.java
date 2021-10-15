package org.silnith.browser.swing.action;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.JTabbedPane;


public class NewTabAction extends AbstractAction {
    
    private final JTabbedPane tabbedPane;

    public NewTabAction(JTabbedPane tabbedPane) {
        super();
        this.tabbedPane = tabbedPane;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        tabbedPane.addTab("Tab", null);
    }

}
