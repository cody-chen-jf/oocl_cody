package com.oocl.chat_room.ui;

import com.oocl.chat_room.action.impl.ClientAction;
import com.oocl.chat_room.pojo.User;
import com.oocl.chat_room.protocol.DataPackage;
import com.oocl.chat_room.util.DateUtil;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.net.Socket;
import java.util.Date;

public class ChatFrame extends JFrame implements ActionListener, Runnable {
    private ClientAction clientAction;
    private User user = null;
    private boolean flag;
    private String connector;

    private JTextArea chatTa;
    private JTextField sendTf;
    private JButton sendBtn;
    private JButton shakeBtn;
    private JList<String> friendsJl;
    private JScrollPane scorllPanel;
   
    private DefaultListModel<String> model;

    public User getUser() {
        return user;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    public JTextArea getChatTa() {
        return chatTa;
    }

    public DefaultListModel<String> getModel() {
        return model;
    }

    public JList<String> getFriendsJl() {
        return friendsJl;
    }

    public ChatFrame(User user) {
        try {
            this.user = user;
            this.clientAction = new ClientAction(new Socket("127.0.0.1", 8888), this);
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.setSize(600, 500);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        init();
        addEvent();
        this.setVisible(true);
    }

    private void init() {
        this.setLayout(new BorderLayout());
        JPanel panLeft = new JPanel(new BorderLayout());
        JPanel panRight = new JPanel(new BorderLayout());
        JPanel panLeftBottom = new JPanel(new BorderLayout());
        JPanel panBtnGroup = new JPanel(new BorderLayout());
        chatTa = new JTextArea(10, 20);
        panRight.setPreferredSize(new Dimension(100, 0));
       

        sendTf = new JTextField();
        sendBtn = new JButton("send");
        shakeBtn = new JButton("shake");
        sendBtn.setSize(25, 25);
        shakeBtn.setSize(25, 25);
        sendTf.setFont(new Font("宋体", Font.BOLD, 18));
        sendTf.setForeground(Color.red);
        chatTa.setFont(new Font("宋体", Font.BOLD, 18));
        chatTa.setForeground(Color.blue);


        panLeft.add(new JScrollPane(chatTa), BorderLayout.CENTER);
        panLeft.add(panLeftBottom, BorderLayout.SOUTH);
        panRight.setBorder(new LineBorder(Color.red));


        panBtnGroup.add(shakeBtn, BorderLayout.NORTH);
        panBtnGroup.add(sendBtn, BorderLayout.SOUTH);
        panLeftBottom.add(sendTf, BorderLayout.CENTER);
        panLeftBottom.add(panBtnGroup, BorderLayout.EAST);

        getRootPane().setDefaultButton(sendBtn);
        panLeft.requestFocus();

        model = new DefaultListModel<>();
        friendsJl = new JList<String>(model);
        friendsJl.setFont(new Font("宋体", Font.BOLD, 22));
        
        model.addElement("ALL");
        scorllPanel = new JScrollPane(friendsJl);
        panRight.add(scorllPanel, BorderLayout.CENTER);

        this.add(panLeft, BorderLayout.CENTER);
        this.add(panRight, BorderLayout.EAST);
    }

    private void addEvent() {
        sendBtn.addActionListener(this);

        friendsJl.addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent e) {
                Object obj = ((JList) e.getSource()).getSelectedValue();
                if (obj != null) {
                    setTitle(user.getName() +  " 与 " + obj.toString() + " 正在对话");
                    connector = obj.toString();
                }
            }
        });

        // 设置默认选中 ALL
        friendsJl.setSelectedIndex(0);

        shakeBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clientAction.sendDataPackage(new DataPackage(user.getName(), connector, DataPackage.MessageType.SHAKE, null));
            }
        });
    }

    @Override
    protected void processWindowEvent(WindowEvent e) {
        if (e.getID() == WindowEvent.WINDOW_CLOSING) {
            clientAction.sendDataPackage(new DataPackage(user.getName(), "ALL", DataPackage.MessageType.LOGOUT, "LOGOUT"));
        }
        super.processWindowEvent(e);
    }

    public void actionPerformed(ActionEvent e) {
        sendMsg();
    }

    private void sendMsg() {
        long time = new Date().getTime();
        String content = user.getName() + " 对 " + connector + " 说 " + "( " + DateUtil.formatDate(time) + " )" + "\n\r  " + sendTf.getText();
        chatTa.append(content + "\n");
        if (!content.equals("")) {
            clientAction.sendDataPackage(new DataPackage(user.getName(), connector, DataPackage.MessageType.MESSAGE, content, time));
        }
        sendTf.setText("");
    }

    public void shakeWindow() {
        for (int i = 0; i < 3; i++) {
            setLocation(getLocation().x, getLocation().y);
            try {
                Thread.sleep(100);
            } catch (InterruptedException e1) {
                e1.printStackTrace();
            }
            setLocation(getLocation().x + 10, getLocation().y - 10);
            try {
                Thread.sleep(100);
            } catch (InterruptedException e1) {
                e1.printStackTrace();
            }
            setLocation(getLocation().x - 10, getLocation().y + 10);
        }
    }

    @Override
    public void run() {
        flag = true;
        clientAction.sendDataPackage(new DataPackage(user.getName(), "ALL", DataPackage.MessageType.LOGIN, "LOGIN"));
        while (flag) {
            DataPackage dataPackage = clientAction.receiveDataPackage();
            clientAction.handleDataPackage(dataPackage);
        }
    }
}