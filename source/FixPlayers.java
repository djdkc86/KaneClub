
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.util.ArrayList;

public class FixPlayers extends JFrame{
    private ArrayList<Player> fixedList;
    JList list;
    JPanel buttonPanel;
    public FixPlayers(ArrayList<Player> players){
        this.fixedList = players;
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        makeList();
        makeButton();
        
        setLayout(new BorderLayout());
        
        add(new JScrollPane(list), BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
        
        setLocation(dim.width/2-this.getSize().width/2, dim.height/2-this.getSize().height/2);
        pack();
        setVisible(true);
    }
    
    private void makeList(){
        int index = 0;
        int size = fixedList.size();
        String [] names = new String[size];
        for(Player p: fixedList){
            names[index++] = p.getName();
        }
        list = new JList(names);
    }
    
    private void makeButton(){
        buttonPanel = new JPanel(new FlowLayout());
        JButton button = new JButton("Remove");
        button.addActionListener(new ButtonPressed());
        buttonPanel.add(button);
    }
    
    private class ButtonPressed implements ActionListener{
        public void actionPerformed(ActionEvent e){
            int selected = list.getSelectedIndex();
            fixedList.remove(selected);
            dispose();
        }
    }
    
    public ArrayList<Player> newList(){
        return fixedList;
    }
}