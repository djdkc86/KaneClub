
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

public class addPlayer extends JFrame{
    
    JTextField player, number;
    Player p;
    Bets b;
    
    public addPlayer(){
        
        JPanel panel = new JPanel(new FlowLayout());
        
        JLabel label = new JLabel("Name: ");
        player = new JTextField(40);
        
        panel.add(label);
        panel.add(player);
        
        JPanel quotaPanel = new JPanel(new FlowLayout());
        
        JLabel quota = new JLabel("Quota: ");
        number = new JTextField(3);
        
        quotaPanel.add(quota);
        quotaPanel.add(number);
        
        JPanel butPan = new JPanel(new FlowLayout());
        
        JButton button = new JButton("Add");
        button.addActionListener(new ButtonListener());
        
        butPan.add(button);
        
        setLayout(new GridLayout(3,1));
        add(panel);
        add(quotaPanel);
        add(butPan);
        
        pack();
        setVisible(true);
    }
    
    private class ButtonListener implements ActionListener{
        public void actionPerformed(ActionEvent e){
            p = new Player(player.getText(), Integer.parseInt(number.getText()), false);
            ModifyXml mod = new ModifyXml(p.getName(), p.getQuota());
            dispose();
            //b = new Bets(p, true);
        }
    }
    
    public Player getPlayer(){
        //p = b.getPlayer();
        return p;
    }
}