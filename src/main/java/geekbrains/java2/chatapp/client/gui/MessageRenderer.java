package geekbrains.java2.chatapp.client.gui;

import geekbrains.java2.chatapp.client.ChatGuiClient;
import geekbrains.java2.chatapp.dto.Message;

import javax.swing.*;
import java.awt.*;
import java.text.SimpleDateFormat;

public class MessageRenderer  implements ListCellRenderer<Message> {
    private static SimpleDateFormat dateFormat = new SimpleDateFormat("MM.dd HH:mm");
    private String myUsername;
    private static final Color myMessagesColor = new Color(173,216,230);
    private static final Color privateMessagesColor = new Color(255,255,237);
    private final ChatGuiClient client;
    public MessageRenderer(ChatGuiClient client, String myUsername) {
        this.client = client;
        this.myUsername = myUsername;
    }

    @Override
    public Component getListCellRendererComponent(JList<? extends Message> list, Message value, int index, boolean isSelected, boolean cellHasFocus) {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new BorderLayout());
        String fromUser= client.getUsernameById( value.getFromUser());
        if(fromUser.equals(myUsername)){
            String myMessageTitle ;
            if(value.getTarget() == 0)
                myMessageTitle = "Всем";
            else
                myMessageTitle = client.getUsernameById(value.getTarget());
            leftPanel.add(new JLabel(myMessageTitle),BorderLayout.CENTER);
        }
        else
            leftPanel.add(new JLabel(fromUser),BorderLayout.CENTER);
        if (value.isPrivate())
            leftPanel.setBackground(privateMessagesColor);
        leftPanel.add(new JLabel(dateFormat.format(value.getSendTime())), BorderLayout.SOUTH);
        if (value.isPrivate())
            leftPanel.add(new JLabel("(private)"),BorderLayout.NORTH);
        panel.add(leftPanel,BorderLayout.WEST);
        JTextArea messageTextLabel = new JTextArea(value.getText());
        messageTextLabel.setLineWrap(true);
        messageTextLabel.setWrapStyleWord(true);
        messageTextLabel.setEditable(false);
        messageTextLabel.setBorder(BorderFactory.createEmptyBorder(0,20,0,0));
        if(fromUser.equals(myUsername))
            messageTextLabel.setBackground(myMessagesColor);
        panel.add(messageTextLabel, BorderLayout.CENTER);

        return panel;
    }
}
