
import java.awt.*;
import javax.swing.*;
import java.io.*;
import java.util.*;
import java.awt.event.*;

public class GetPastRoundInfo extends JFrame{
    JList list;
    public GetPastRoundInfo() throws FileNotFoundException, IOException{
        DatesTxt dates = new DatesTxt(true);
        int size = dates.getSize();
        String [] date = new String[size];
        for(int i = 0; i < size; i++){
            date[i] = dates.getDates(i);
        }
        list = new JList(date);
        
        JPanel butPan = new JPanel(new FlowLayout());
        JButton button = new JButton("Go");
        button.addActionListener(new GoPressedListener());
        butPan.add(button);
        
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation(dim.width/2-this.getSize().width/2, dim.height/2-this.getSize().height/2);
        
        setLayout(new BorderLayout());
        
        add(new JScrollPane(list), BorderLayout.CENTER);
        add(butPan, BorderLayout.SOUTH);
        pack();
        setVisible(true);
    }
    
    private class GoPressedListener implements ActionListener{
        public void actionPerformed(ActionEvent e){
            new ReadPastData(list.getModel().getElementAt(list.getSelectedIndex()).toString());
            dispose();
        }
    }
}