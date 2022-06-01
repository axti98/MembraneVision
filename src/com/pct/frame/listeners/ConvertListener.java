package com.pct.frame.listeners;

import com.pct.Essentials;
import com.pct.frame.Const;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ConvertListener implements ActionListener
{
    private final JList<String> imageFileList;
    private int expansionDegree = 1;
    private boolean selectionMode;
    public ConvertListener(JList<String> AModel, int AExpansionDegree, boolean ASelectionMode)
    {
        this.imageFileList = AModel;
        this.expansionDegree = AExpansionDegree;
        this.selectionMode =ASelectionMode;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        StringBuilder arguments = new StringBuilder(" " + expansionDegree + "");
        ListModel<String> fileListModel = imageFileList.getModel();
        String path = "." + File.separator + "cmd_tool";
        List<String> command = new ArrayList<>();

        if (imageFileList.isSelectionEmpty() && fileListModel.getSize() != 0 && selectionMode) {
            for (int i = 0; i < fileListModel.getSize(); i++)
                arguments.append(" ").append(fileListModel.getElementAt(i));
        }

        if (!imageFileList.isSelectionEmpty() && selectionMode) {
            for(String str : imageFileList.getSelectedValuesList())
                arguments.append(" ").append(str);
        }

        File file;
        if(System.getProperty(Const.OS_NAME_PROPERTY).contains(Const.OS_NAME_WIN))
        {
            path += Const.EXC_EXTENSION_WIN;
            file = Essentials.commandExecutionFile(path);

            command.add("cmd.exe");
            command.add("/c start " + file.getAbsolutePath() + arguments);
        }
        else if(System.getProperty(Const.OS_NAME_PROPERTY).contains(Const.OS_NAME_LINUX))
        {
            path += Const.EXC_EXTENSION_WIN;
            file = Essentials.commandExecutionFile(path);

            command.add(file.getAbsolutePath() + arguments);
        } else {
            throw new RuntimeException(Const.FALSE_OS);
        }

        try {
            Process process = new ProcessBuilder(command).start();
            while(process.isAlive());
            System.out.println("Process ended with exit value " + process.exitValue());
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }
}
