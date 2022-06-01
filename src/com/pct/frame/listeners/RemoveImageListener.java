package com.pct.frame.listeners;

import com.pct.Main;
import com.pct.Const;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RemoveImageListener implements ActionListener {

    private final JList<String> workingList;

    public RemoveImageListener(JList<String> AList){
        this.workingList = AList;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        int currentIndex = workingList.getSelectedIndex();
        if(currentIndex != Const.NO_INDEX_SELECTED)
        {
            DefaultListModel<String> model = (DefaultListModel<String>) workingList.getModel();
            model.remove(currentIndex);
            if(model.getSize() == 0){
                Main.frame.getImgUpButton().setEnabled(false);
                Main.frame.getImgDownButton().setEnabled(false);
            }
        }
    }
}
