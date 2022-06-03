package com.pct.frame.listeners;

import com.pct.Const;
import com.pct.Main;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class RemoveImageListener implements ActionListener {

    private final JList<String> workingList;

    public RemoveImageListener(JList<String> AList){
        this.workingList = AList;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        List<String> selectedValuesList = workingList.getSelectedValuesList();
        if(!selectedValuesList.isEmpty())
        {
            DefaultListModel<String> model = (DefaultListModel<String>) workingList.getModel();
            for(String el : selectedValuesList)
            {
                model.removeElement(el);
            }

            if(model.getSize() == Const.EMPTY_LIST){
                Main.frame.getImgUpButton().setEnabled(false);
                Main.frame.getImgDownButton().setEnabled(false);
                Main.frame.getImgRemoveButton().setEnabled(false);
                Main.frame.getConvertButton().setEnabled(false);
            }
        }
    }
}
