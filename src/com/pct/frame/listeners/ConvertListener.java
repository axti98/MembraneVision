package com.pct.frame.listeners;

import com.pct.Const;
import com.pct.Essentials;

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
    private int expansionDegree;
    private int voxelSize;
    private boolean selectionMode;
    public ConvertListener(JList<String> AModel, int AExpansionDegree, int AVoxelSize, boolean ASelectionMode)
    {
        this.imageFileList = AModel;
        this.expansionDegree = AExpansionDegree;
        this.voxelSize = AVoxelSize;
        this.selectionMode = ASelectionMode;
    }

    private List<String> getCommandList()
    {
        String path = "." + File.separator + "tool";
        File file;

        List<String> result = new ArrayList<>();

        if(System.getProperty(Const.OS_NAME_PROPERTY).contains(Const.OS_NAME_WIN))
        {
            path += Const.EXC_EXTENSION_WIN;
            file = Essentials.commandExecutionFile(path);

            result.add("cmd.exe");
            result.add("/c");
            result.add("start");
            result.add(file.getAbsolutePath());
            result.add(Integer.toString(expansionDegree));
            result.add(Float.toString(voxelSize));

        }
        else if(System.getProperty(Const.OS_NAME_PROPERTY).contains(Const.OS_NAME_LINUX))
        {
            path += Const.EXC_EXTENSION_WIN;
            file = Essentials.commandExecutionFile(path);

            // TODO: Implement linux application
            result.add("sh");

        } else {
            throw new RuntimeException(Const.FALSE_OS);
        }

        return result;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        ListModel<String> fileListModel = imageFileList.getModel();
        List<String> command = this.getCommandList();
        ProcessBuilder builder = new ProcessBuilder();

        for(int i = 0; i < fileListModel.getSize(); i++)
        {
            command.add(fileListModel.getElementAt(i));
        }

        builder.command(command);

        try {
            Process process = builder.start();
            int exitCode = process.waitFor();
            System.out.println(exitCode);
        } catch (IOException | InterruptedException ex) {
            throw new RuntimeException(ex);
        }
    }
}
