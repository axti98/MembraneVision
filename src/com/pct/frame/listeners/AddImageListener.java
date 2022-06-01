package com.pct.frame.listeners;

import com.pct.Main;
import com.pct.Const;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.Collection;

public class AddImageListener implements ActionListener {

    private final DefaultListModel<String> model;
    private final JFileChooser fileChooser;
    public AddImageListener(ListModel<String> AModel){
        this.model = (DefaultListModel<String>) AModel;
        fileChooser = new JFileChooser();
        fileChooser.setDialogTitle(Const.FILE_CHOOSER_TITLE);
        fileChooser.setMultiSelectionEnabled(true);
        FileFilter ff = new FileNameExtensionFilter("Image files",
                Const.EXT_TIF,
                Const.EXT_TIFF,
                Const.EXT_JPG,
                Const.EXT_PNG
        );
        fileChooser.setFileFilter(ff);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        int option = fileChooser.showOpenDialog(Main.frame);

        if(option == JFileChooser.CANCEL_OPTION) return;

        if(Main.frame.getImageFileList().getModel().getSize() == 0) {
            Main.frame.getImgUpButton().setEnabled(true);
            Main.frame.getImgDownButton().setEnabled(true);
        }

        Collection<String> files = new ArrayList<>();
        for(File f : fileChooser.getSelectedFiles()){
            files.add(f.getAbsolutePath());
        }

        model.addAll(files);
    }
}
