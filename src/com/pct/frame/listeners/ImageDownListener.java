package com.pct.frame.listeners;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ImageDownListener implements ActionListener {

    private JList<String> workingList;

    public ImageDownListener(JList<String> AList){
        this.workingList = AList;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(workingList.isSelectionEmpty())
            return;
        DefaultListModel<String> model = (DefaultListModel<String>) workingList.getModel();

        int currentIndex = workingList.getSelectedIndex();
        if(currentIndex == model.getSize()-1) return;

        String selectedEntry = model.getElementAt(currentIndex);
        model.remove(currentIndex);
        model.insertElementAt(selectedEntry, currentIndex+1);
        workingList.setSelectedIndex(currentIndex+1);
    }
}
