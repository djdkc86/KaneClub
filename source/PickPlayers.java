
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
import java.io.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

//adding players is a pain

public class PickPlayers extends JFrame{
    
    public List<Player> players;
    public ArrayList<Player> playing;
    private JPanel panel, buttons;
    private boolean isSet, goBack;
    private int skinMoney, greenieMoney, stabeMoney, superMoney, weeksBehind;
    addPlayer addP;
    Bets b;
    Player play;
    SuperSkinTxt ssTxt;
    
    public PickPlayers() throws FileNotFoundException{
        
        ssTxt = new SuperSkinTxt();
        this.weeksBehind = ssTxt.getWeeks();
        skinMoney = 0;
        greenieMoney = 0;
        stabeMoney = 0;
        this.superMoney = ssTxt.getAmount();
        this.goBack = false;
        makeSexy();
    }
    
    public PickPlayers(ArrayList<Player> playing) throws FileNotFoundException{
        
        ssTxt = new SuperSkinTxt();
        this.weeksBehind = ssTxt.getWeeks();
        this.playing = playing;
        skinMoney = 0;
        greenieMoney = 0;
        stabeMoney = 0;
        this.superMoney = ssTxt.getWeeks();
        this.goBack = true;
        makeSexy();
        
    }
    
    private void makeSexy(){
        generateList();
        
        //layoutstuff
        setLayout(new BorderLayout());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        buildPanel();
        
        add(panel, BorderLayout.CENTER);
        add(buttons, BorderLayout.SOUTH);
        pack();
        setVisible(true);
    }
    
    private void buildPanel(){
        panel = new JPanel(new GridLayout(players.size()/6, 6));
        
        for(Player p: players){
            JButton btn = new JButton(p.getName());
            btn.addActionListener(new PlayerButtonListener());
            panel.add(btn);
        }
        
        
        buttons = new JPanel(new FlowLayout());
        
        JButton ready = new JButton("Ready");
        ready.addActionListener(new ReadyButtonListener());
        
        JButton remove = new JButton("Remove");
        remove.addActionListener(new RemoveListener());
        
        JButton addPlayer = new JButton("Add a New Player");
        addPlayer.addActionListener(new AddPlayerListener());
        
        JButton refresh = new JButton("Refresh");
        refresh.addActionListener(new RefreshListener());
        
        buttons.add(ready);
        buttons.add(remove);
        buttons.add(addPlayer);
        buttons.add(refresh);
    }
    
    private void generateList(){
        XMLReader read = new XMLReader("players.xml");
        players = (ArrayList<Player>)read.playerList;
        Collections.sort(players);
        for(Player p: players){
            if(weeksBehind < p.getWeeksBehind()){
                p.setWeeksBehind(weeksBehind);
            }
        }
        if(!goBack) playing = new ArrayList<>(0);
    }
    
    public Player betsPopup(Player p, boolean fng){
        b = new Bets(p, fng);
        return b.getPlayer();
    }
    
    private class RefreshListener implements ActionListener{
        public void actionPerformed(ActionEvent e){
            play = addP.getPlayer();
            play.setWeeksBehind(weeksBehind);
            play.setSuperSkin(weeksBehind*5);
            players.add(play);
            JButton btn = new JButton(play.getName());
            btn.addActionListener(new PlayerButtonListener());
            panel.add(btn);
            validate();
            repaint();
        }
    }
    
    private class AddPlayerListener implements ActionListener{
        public void actionPerformed(ActionEvent e){
            addP = new addPlayer();
        }
    }
    
    private class PlayerButtonListener implements ActionListener{
        public void actionPerformed(ActionEvent e){
            isSet = false;
            int index = 0;
            if(playing.size() != 0){
            playingloop:
                for(Player p : playing){
                    if(p.getName() != null && p.getName().contains(e.getActionCommand())){
                        if(p.isInSuperSkin()){
                            p.setSuperSkin(p.getSuperSkin()-5);
                        }
                        playing.set(index, betsPopup(p, false));
                        isSet = true;
                        break playingloop;
                    }
                    index++;
                }
            }
            if(!isSet){
                for(Player p : players){
                    if(p.getName() != null && p.getName().contains(e.getActionCommand())){
                        playing.add(betsPopup(p, false));
                    }
                }
            }
        }
    }
    
    private class ReadyButtonListener implements ActionListener{
        public void actionPerformed(ActionEvent e){
            Collections.sort(playing);
            dispose();
            for(Player p : playing){
                skinMoney += p.getPot(p.isInSkins());
                stabeMoney += p.getPot(p.isInStableford());
                greenieMoney += p.getPot(p.isInGreenies());
                if(p.isInSuperSkin()) superMoney += p.getSuperSkin();
            }
    
            new GroupScorecard(playing, skinMoney, stabeMoney, greenieMoney, superMoney);
        }
    }
    
    private class RemoveListener implements ActionListener{
        public void actionPerformed(ActionEvent e){
            FixPlayers fix = new FixPlayers(playing);
            playing = fix.newList();
        }
    }
}