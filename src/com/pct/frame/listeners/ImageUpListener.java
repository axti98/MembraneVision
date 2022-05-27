package com.pct.frame.listeners;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ImageUpListener implements ActionListener {

    private final JList<String> workingList;

    public ImageUpListener(JList<String> AList){
        this.workingList = AList;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(workingList.isSelectionEmpty()) return;
        DefaultListModel<String> model = (DefaultListModel<String>) workingList.getModel();

        int currentIndex = workingList.getSelectedIndex();
        if(currentIndex == 0) return;

        String selectedEntry = model.getElementAt(currentIndex);
        model.remove(currentIndex);
        model.insertElementAt(selectedEntry, currentIndex-1);
        workingList.setSelectedIndex(currentIndex-1);
    }
}
