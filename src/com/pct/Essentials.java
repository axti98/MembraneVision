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
}
