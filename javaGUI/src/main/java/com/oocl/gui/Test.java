package com.oocl.gui;

import java.awt.*;
import javax.swing.*;
import javax.swing.text.html.HTMLDocument;
import javax.swing.text.html.parser.Element;

public class Test extends JFrame {
    JMenuBar jb;
    JTextArea ja;
    JScrollPane jsp;
    public void setImage() {
        HTMLDocument doc = new HTMLDocument();

        jb = new JMenuBar();
        this.setJMenuBar(jb);
        JLabel label = new JLabel();
        ja = new JTextArea();
        jsp = new JScrollPane(ja);
        this.setSize(600, 400);
        this.setLayout(new BorderLayout());
        this.add(jsp);
        this.setVisible(true);
    }

    public static void main(String[] args) {
        Test a = new Test();
        a.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        a.setImage();
    }
}
