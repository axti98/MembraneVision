package com.pct.frame.listeners;

import com.pct.Essentials;
import com.pct.Const;

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
    private float distance = 0;
    private boolean selectionMode;
    public ConvertListener(JList<String> AModel, int AExpansionDegree, int ADistance, boolean ASelectionMode)
    {
        this.imageFileList = AModel;
        this.expansionDegree = AExpansionDegree;
        this.distance = ADistance;
        this.selectionMode =ASelectionMode;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        ListModel<String> fileListModel = imageFileList.getModel();
        String path = "." + File.separator + "tool";
        List<String> command = new ArrayList<>();

        File file;
        ProcessBuilder builder = new ProcessBuilder();
        if(System.getProperty(Const.OS_NAME_PROPERTY).contains(Const.OS_NAME_WIN))
        {
            path += Const.EXC_EXTENSION_WIN;
            file = Essentials.commandExecutionFile(path);

            command.add("cmd.exe");
            command.add("/c");
            command.add("start");
            command.add(file.getAbsolutePath());
            command.add(Integer.toString(expansionDegree));
            command.add(Float.toString(distance));

            for(int i = 0; i < fileListModel.getSize(); i++)
            {
                command.add(fileListModel.getElementAt(i));
            }

            builder.command(command);
        }
        else if(System.getProperty(Const.OS_NAME_PROPERTY).contains(Const.OS_NAME_LINUX))
        {
            path += Const.EXC_EXTENSION_WIN;
            file = Essentials.commandExecutionFile(path);
        } else {
            throw new RuntimeException(Const.FALSE_OS);
        }

        try {
            Process process = builder.start();
            int exitCode = process.waitFor();
            System.out.println(exitCode);
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        } catch (InterruptedException ex) {
            throw new RuntimeException(ex);
        }
    }
}
