

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Bets extends JFrame{
    private Player p;
    private JPanel panel;
    private JCheckBox skins, stableford, greenies, superskin, allin;
    private JButton button, remove;
    private JLabel moneyDue;
    private int monies;
    private boolean fng;
    
    public Bets(Player p, boolean fng){
        this.fng = fng;
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation(dim.width/2-this.getSize().width/2, dim.height/2-this.getSize().height/2);
        this.p = p;
        this.monies = 0;
        setLayout(new BorderLayout());
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        buildPanel();
        add(panel);
        pack();
        setVisible(true);
    }
    
    private void buildPanel(){
        panel = new JPanel(new GridLayout(9,1));
        
        JLabel label = new JLabel(p.getName());
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setVerticalAlignment(SwingConstants.CENTER);
        panel.add(label);
        
        JLabel quota = new JLabel("Target: "+p.getQuota());
        quota.setHorizontalAlignment(SwingConstants.CENTER);
        quota.setVerticalAlignment(SwingConstants.CENTER);
        panel.add(quota);
        
        ItemListener item = new ItemListener(){
            @Override
            public void itemStateChanged(ItemEvent e){
                JCheckBox bet = (JCheckBox)e.getSource();
                if(e.getStateChange() == ItemEvent.SELECTED){
                    if(bet.getName().equalsIgnoreCase("all")){
                        skins.setSelected(true);
                        stableford.setSelected(true);
                        greenies.setSelected(true);
                        superskin.setSelected(true);
                    }
                    if(bet.getName().equalsIgnoreCase("skins")){
                        monies += 10;
                    }
                    if(bet.getName().equalsIgnoreCase("stableford")){
                        monies += 10;
                    }
                    if(bet.getName().equalsIgnoreCase("greenies")){
                        monies += 10;
                    }
                    if(bet.getName().equalsIgnoreCase("superskin")){
                        monies += (p.getSuperSkin()+5);
                    }
                    moneyDue.setText("Money Due: $"+monies);
                }else if(e.getStateChange() == ItemEvent.DESELECTED){
                    if(bet.getName().equalsIgnoreCase("skins")){
                        monies -= 10;
                        if(allin.isSelected()) allin.setSelected(false);
                    }
                    if(bet.getName().equalsIgnoreCase("stableford")){
                        monies -= 10;
                        if(allin.isSelected()) allin.setSelected(false);
                    }
                    if(bet.getName().equalsIgnoreCase("greenies")){
                        monies -= 10;
                        if(allin.isSelected()) allin.setSelected(false);
                    }
                    if(bet.getName().equalsIgnoreCase("superskin")){
                        monies -= (p.getSuperSkin()+5);
                        if(allin.isSelected()) allin.setSelected(false);
                    }
                    moneyDue.setText("Money Due: $"+monies);
                }
            }
        };
        
        allin = new JCheckBox("All In");
        allin.setName("all");
        allin.addItemListener(item);
        panel.add(allin);
        
        skins = new JCheckBox("Skins ($10)");
        skins.setName("skins");
        skins.setSelected(p.isInSkins());
        skins.addItemListener(item);
        panel.add(skins);
        
        stableford = new JCheckBox("Stableford ($10)");
        stableford.setName("stableford");
        if(fng){
            stableford.setEnabled(false);
        }
        stableford.setSelected(p.isInStableford());
        stableford.addItemListener(item);
        panel.add(stableford);
        
        greenies = new JCheckBox("Greenies ($10)");
        greenies.setName("greenies");
        greenies.setSelected(p.isInGreenies());
        greenies.addItemListener(item);
        panel.add(greenies);
        
        superskin = new JCheckBox("Super Skin ($"+(p.getSuperSkin()+5)+")");
        superskin.setName("superskin");
        superskin.setSelected(p.isInSuperSkin());
        superskin.addItemListener(item);
        panel.add(superskin);
        
        moneyDue = new JLabel("Money Due: "+monies);
        moneyDue.setHorizontalAlignment(SwingConstants.CENTER);
        moneyDue.setVerticalAlignment(SwingConstants.CENTER);
        panel.add(moneyDue);
        
        button = new JButton("Done");
        button.addActionListener(new ButtonListener());
        panel.add(button);
        
    }
    public Player getPlayer(){
        return p;
    }
    
    private class ButtonListener implements ActionListener{
        public void actionPerformed(ActionEvent e){
            p.setInSkins(skins.isSelected());
            p.setInStableford(stableford.isSelected());
            p.setInGreenies(greenies.isSelected());
            p.setInSuperSkin(superskin.isSelected());
            dispose();
        }
    }
}