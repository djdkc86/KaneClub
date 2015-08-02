
import javax.swing.*;
import static javax.swing.ScrollPaneConstants.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.ArrayList;
import java.util.*;
import java.text.*;

public class GroupScorecard extends JFrame{
    Date d;
    ArrayList<PlayerPanel> scores;
    ArrayList<Player> player;
    JPanel butPan, moneyPan, payout;
    JLabel s, st, g, ss;
    GridLayout layout = new GridLayout(1,0);
    SuperSkinTxt ssTxt = new SuperSkinTxt();
    private String txtName;
    private int skin, stabe, greenie, supers, gg, greenPay, howManyMadeMoney, gs, width, firstPlace, secondPlace, thirdPlace, firstMoney, secondMoney, thirdMoney;
    public boolean[] skinsFront, skinsBack;
    private boolean superWon;
    public GroupScorecard(ArrayList<Player> p, int skin, int stabe, int greenie, int supers){
        
        d = new Date();
        DateFormat df = new SimpleDateFormat("MMM dd yyyy");
        txtName = df.format(d);
        txtName.replaceAll("\\s+","");
        txtName = txtName + ".txt";
        
        width = (int) Toolkit.getDefaultToolkit().getScreenSize().getWidth();
        
        scores = new ArrayList<>(p.size());
        player = new ArrayList<>(p.size());
        
        this.skin = skin;
        this.stabe = stabe;
        this.greenie = greenie;
        this.supers = supers;
        this.howManyMadeMoney = 0;
        this.gg = 0;
        this.firstPlace = -200;
        this.secondPlace = -200;
        this.thirdPlace = -200;
        this.greenPay = 0;
        superWon = false;
        
        this.secondMoney = stabe * 3/10  - (stabe * 3 / 10)%5;
        if(stabe > 150){
            this.firstMoney = stabe * 6 / 10 - (stabe * 6 / 10)%5;
            this.thirdMoney = stabe / 10 - (stabe / 10) %5;
        }else{
            this.firstMoney = stabe * 7/10 - (stabe * 7 / 10)%5;
            this.thirdMoney = 0;
        }
        
        JPanel group = new JPanel(null);
        
        Scorecard panel = new Scorecard();
        
        panel.setBounds(0,0,width, 40);

        buildPanel();

        skinsFront = new boolean[9];
        skinsBack = new boolean[9];
        
        for(int i = 0; i<9;i++){
            skinsFront[i] = false;
            skinsBack[i] = false;
        }
        
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        
        group.add(panel);
        int height = (p.size()+2)*30 + 40;
        int holder = 40;
        int index = 0;
        for(Player playing : p){
            PlayerPanel dude = new PlayerPanel(playing);
            if(index % 2 == 0){
                dude.setBackground(Color.WHITE);
                dude.setWhite(true);
            }
            dude.setBounds(0, 40 + (index++)*30, width, 30);
            group.add(dude);
            scores.add(dude);
            player.add(playing);
        }
        
        butPan.setBounds(0, 40 + (index++)*30, width, 30);
        group.add(butPan);
        
        moneyPan.setBounds(0, 40 + (index++)*30, width, 30);
        group.add(moneyPan);
        group.setPreferredSize(new Dimension(width, height));
        
        JScrollPane jsp = new JScrollPane(group);
        jsp.setHorizontalScrollBarPolicy(HORIZONTAL_SCROLLBAR_NEVER);
        add(jsp, BorderLayout.CENTER);
        
        add(payout, BorderLayout.SOUTH);
        setSize(Toolkit.getDefaultToolkit().getScreenSize().width, Toolkit.getDefaultToolkit().getScreenSize().height);
        //pack();
        setVisible(true);
    }
    
    private void buildPanel(){
        butPan = new JPanel(new FlowLayout());
        //butPan.setBackground(Color.BLACK);
        JButton button = new JButton("Compute");
        button.addActionListener(new ComputeButtonListner());
        
        JButton fixIt = new JButton("Go Back");
        fixIt.addActionListener(new FixItListener());
        
        JButton finishRound = new JButton("Finish Round");
        finishRound.addActionListener(new FinishListener());
        
        butPan.add(button);
        butPan.add(fixIt);
        butPan.add(finishRound);
        
        moneyPan = new JPanel(new GridLayout(1,4));
        s = new JLabel("Skins: $"+skin);
        st = new JLabel("Stableford: $"+stabe+" ("+firstMoney+"-"+secondMoney+"-"+thirdMoney+")");
        g = new JLabel("Greenies: $"+greenie);
        ss = new JLabel("Super Skin: $"+supers);
        moneyPan.add(s);
        moneyPan.add(st);
        moneyPan.add(g);
        moneyPan.add(ss);
        //moneyPan.setBackground(Color.WHITE);
        
        payout = new JPanel(layout);
        JLabel label = new JLabel("Payout");
        payout.add(label);
        
    }
    
    private class FinishListener implements ActionListener{
        public void actionPerformed(ActionEvent e){
            
            try{
                if(superWon){
                    ssTxt.writeFile(0, 0);
                }else{
                    ssTxt.writeFile(ssTxt.getWeeks()+1, supers);
                }
                DatesTxt dt = new DatesTxt(false);
                CreatePastData cpd = new CreatePastData(txtName, scores, howManyMadeMoney, s.getText(), st.getText(), g.getText(), ss.getText());
            }catch(FileNotFoundException err){
                err.printStackTrace();
            }catch(IOException err){
                err.printStackTrace();
            }
            
            ModifyXml mod = new ModifyXml();
            
            for (int i = 0; i < scores.size(); i++){
                String name = player.get(i).getName();
                int quota = scores.get(i).getNewPoints();
                boolean superStuff = player.get(i).isInSuperSkin();
                
                mod = new ModifyXml(name, quota, superStuff);
            }
        }
        
    }
    
    private class FixItListener implements ActionListener{
        public void actionPerformed(ActionEvent e){
            try{
                dispose();
                new PickPlayers(player);
            }catch(FileNotFoundException err){
                err.printStackTrace();
            }
        }
    }
    
    private class ComputeButtonListner implements ActionListener{
        public void actionPerformed(ActionEvent e){
            gg = 0;
            gs = 0;
            howManyMadeMoney = 0;
            int hasSkins = 0;
            int index = 0;
            int hasGreen = 0;
            firstPlace = -200;
            secondPlace = -200;
            thirdPlace = -200;
            secondMoney = stabe * 3/10  - (stabe * 3 / 10)%5;
            if(stabe > 150){
                firstMoney = stabe * 6 / 10 - (stabe * 6 / 10)%5;
                thirdMoney = stabe / 10 - (stabe / 10) %5;
            }else{
                firstMoney = stabe * 7/10 - (stabe * 7 / 10)%5;
                thirdMoney = 0;
            }
            for(int i = 0; i < 9; i++){
                boolean goodFront = false;
                boolean goodBack = false;
                int lowScoreFront = 10;
                int lowScoreBack = 10;
                int superSkinScore = 3;
                int posF = -1;
                int posB = -1;
                int posS = -1;
                for(int j = 0; j< scores.size();j++){
                    if(i == 6){
                        if(player.get(j).isInSuperSkin()){
                            if(scores.get(j).front9[i] < superSkinScore){
                                superSkinScore = scores.get(j).front9[i];
                                superWon = true;
                                posS = j;
                            } else if (scores.get(j).front9[i] == superSkinScore){
                                superWon = false;
                            }
                        }
                    }
                    if(player.get(j).isInSkins()){
                        if(scores.get(j).front9[i] < lowScoreFront){
                            lowScoreFront = scores.get(j).front9[i];
                            goodFront = true;
                            posF = j;
                        } else if(scores.get(j).front9[i] == lowScoreFront){
                            goodFront = false;
                        }
                        if(scores.get(j).back9[i] < lowScoreBack){
                            lowScoreBack = scores.get(j).back9[i];
                            goodBack = true;
                            posB = j;
                        }else if(scores.get(j).back9[i] == lowScoreBack){
                            goodBack = false;
                        }
                    }
                }
                if(goodFront) scores.get(posF).frontSkins[i] = true;
                if(goodBack) scores.get(posB).backSkins[i] = true;
                if(superWon && posS != -1) scores.get(posS).setWasSuperWon(true);
                if(!goodFront){
                    for(PlayerPanel p : scores){
                        p.frontSkins[i] = false;
                    }
                }
                if(!goodBack){
                    for(PlayerPanel p : scores){
                        p.backSkins[i] = false;
                    }
                }
                if(!superWon){
                    for(PlayerPanel p: scores){
                        p.setWasSuperWon(false);
                    }
                }
            }
            
            for(PlayerPanel p: scores){
                p.update();
                int againstQ = p.getDiffer();
                if(againstQ > thirdPlace && againstQ != firstPlace && againstQ != secondPlace){
                    if(againstQ > firstPlace){
                        thirdPlace = secondPlace;
                        secondPlace = firstPlace;
                        firstPlace = againstQ;
                    }else if(againstQ > secondPlace){
                        thirdPlace = secondPlace;
                        secondPlace = againstQ;
                    }else{
                        thirdPlace = againstQ;
                    }
                }
            }
            
            int paying1 = 0;
            int paying2 = 0;
            int paying3 = 0;
            for(PlayerPanel p: scores){
                if(firstPlace == p.getDiffer()){
                    p.setFP(true);
                    paying1++;
                }
                else p.setFP(false);
                if(secondPlace == p.getDiffer()){
                    p.setSP(true);
                    paying2++;
                }
                else p.setSP(false);
                if(thirdPlace == p.getDiffer()){
                    p.setTP(true);
                    paying3++;
                }
                else p.setTP(false);
            }
            if(paying1 > 1){
                if(paying1 == 2){
                    firstMoney = firstMoney + secondMoney;
                    secondMoney = thirdMoney;
                    thirdMoney = 0;
                }else{
                    firstMoney = firstMoney + secondMoney + thirdMoney;
                    secondMoney = 0;
                    thirdMoney = 0;
                }
                firstMoney = firstMoney / paying1 - (firstMoney / paying1) % 5;
            }
            if(paying2 > 1){
                secondMoney = secondMoney + thirdMoney;
                thirdMoney = 0;
                secondMoney = secondMoney / paying2 - (secondMoney / paying2) % 5;
            }
            if(paying3 > 1 && thirdMoney !=0){
                thirdMoney = thirdMoney / paying3 - (thirdMoney / paying3) % 5;
            }
            st.setText("Stableford: $"+stabe+" ("+firstMoney+"-"+secondMoney+"-"+thirdMoney+")");
            for(PlayerPanel p: scores){
                if(p.getFP()) p.setStabeMoney(firstMoney);
                else if(p.getSP()) p.setStabeMoney(secondMoney);
                else if(p.getTP()) p.setStabeMoney(thirdMoney);
                //if(p.getInTheMoney()) howManyMadeMoney++;
                gg += p.getGreen();
                gs += p.getGoodSkins();
                if(p.hasGoodSkin()) hasSkins++;
                if(p.hasGoodGreenie()) hasGreen++;
                p.update();
            }
            for(PlayerPanel p: scores){
                if (p.getInTheMoney()) howManyMadeMoney++;
            }
            int greenMoney = 0;
            int skinMoney = 0;
            if(hasGreen == 1) greenMoney = greenie;
            else if(hasGreen > 1) greenMoney = greenie/gg - (greenie/gg)%5;
            if(hasSkins == 1) skinMoney = skin;
            else if (hasSkins > 1) skinMoney = skin/gs - (skin/gs)%5;
            
            payout.removeAll();
            if(howManyMadeMoney > 0){
            layout.setRows(howManyMadeMoney);
            layout.setColumns(0);
            if(howManyMadeMoney > 0){
                    for(PlayerPanel p: scores){
                        if(p.getInTheMoney()){
                                JLabel label = new JLabel(p.getName()+" $"+p.moneyMade(skinMoney, greenMoney, hasSkins, hasGreen, supers), SwingConstants.CENTER);
                                payout.add(label);
                        
                        }
                    }
                }
            }
            s.setText("Skins: $"+skinMoney);
            st.setText("Stableford: $"+firstMoney+"- $"+secondMoney+"- $"+thirdMoney);
            g.setText("Greenies: $"+greenMoney);
            validate();
            repaint();
            //this part for testing
            
        }
    }
}