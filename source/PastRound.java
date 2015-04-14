
import javax.swing.*;
import static javax.swing.ScrollPaneConstants.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;
import java.text.*;

public class PastRound extends JFrame{
    
    ArrayList <PastPlayer> madeMoney;
    private String s, st, g, ss;
    JPanel butPan, moneyPan, group, payout;
    Scorecard scorecard;
    JScrollPane jsp;
    
    public PastRound(ArrayList<PastPlayer> player, String s, String st, String g, String ss, int moneyMakers, String date){
        
        setLayout(new BorderLayout());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(Toolkit.getDefaultToolkit().getScreenSize().width, Toolkit.getDefaultToolkit().getScreenSize().height);
        setTitle(date);
        madeMoney = new ArrayList<> (moneyMakers);
        this.s = s;
        this.st = st;
        this.g = g;
        this.ss = ss;
        int width = (int) Toolkit.getDefaultToolkit().getScreenSize().getWidth();
        int index = 0;
        int height = (player.size()+2)*30 + 40;
        
        group = new JPanel(null);
        
        scorecard = new Scorecard();
        scorecard.setBounds(0,0,width,40);
        
        group.add(scorecard);
        
        for(PastPlayer pp : player){
            PlayerPanel dude = new PlayerPanel(pp);
            if(index%2 == 0){
                dude.setBackground(Color.WHITE);
            }
            if(dude.getFP()) dude.setBackground(new Color(255, 215, 0));
            if(dude.getSP()) dude.setBackground(new Color(192, 192, 192));
            if(dude.getTP()) dude.setBackground(new Color(102, 93, 30));
            dude.setBounds(0, 40 + (index++)*30, width, 30);
            if(pp.getMoneyMaker()){
                madeMoney.add(pp);
            }
            group.add(dude);
        }
        
        butPan = new JPanel(new FlowLayout());
        JButton button = new JButton("Go Back");
        button.addActionListener(new ButtonListener());
        butPan.add(button);
        butPan.setBounds(0, 40 + (index++)*30, width, 30);
        group.add(butPan);
        
        moneyPan = new JPanel(new GridLayout(1,4));
        moneyPan.add(new JLabel(s));
        moneyPan.add(new JLabel(st));
        moneyPan.add(new JLabel(g));
        moneyPan.add(new JLabel(ss));
        moneyPan.setBounds(0, 40 + (index++)*30, width, 30);
        group.add(moneyPan);
        
        group.setPreferredSize(new Dimension(width, height));
        
        jsp = new JScrollPane(group);
        jsp.setHorizontalScrollBarPolicy(HORIZONTAL_SCROLLBAR_NEVER);
        
        payout = new JPanel(new GridLayout(moneyMakers, 0));
        for(PastPlayer pp: madeMoney){
            JLabel jl = new JLabel(pp.getName()+ " $"+pp.getMoneyMade(), SwingConstants.CENTER);
            payout.add(jl);
        }
        
        add(jsp, BorderLayout.CENTER);
        add(payout, BorderLayout.SOUTH);
        
        setVisible(true);
        
    }
    
    private class ButtonListener implements ActionListener{
        public void actionPerformed(ActionEvent e){
            dispose();
            new OpenScreen();
        }
    }
    
}