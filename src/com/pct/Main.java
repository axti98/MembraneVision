package com.pct;

import com.pct.frame.PCTFrame;

import javax.swing.*;

public class Main {

    public static final PCTFrame frame = new PCTFrame();
    public static void main(String[] args){
        SwingUtilities.invokeLater(() -> frame.setVisible(true));
    }
}
