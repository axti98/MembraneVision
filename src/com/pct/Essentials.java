package com.pct;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public final class Essentials {

    private Essentials(){}

    public static ImageIcon createCroppedImage(String APath, int AWidth, int AHeight)
    {
        BufferedImage selectedImg;

        try {
            selectedImg = ImageIO.read(new File(APath));
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        Image croppedImage = selectedImg.getScaledInstance(
                AWidth,
                AHeight,
                Image.SCALE_SMOOTH
        );

        return new ImageIcon(croppedImage);
    }

    public static File commandExecutionFile(String APath)
    {
        File file = new File(APath);
        if(!file.exists()) {
            String msg = "Converter tool was not found. Please ensure that the file lies in the same folder as " +
                    "the executable.";
            JOptionPane.showMessageDialog(Main.frame, msg, "", JOptionPane.ERROR_MESSAGE);
            throw new IllegalArgumentException(msg);
        }

        return file;
    }
}
