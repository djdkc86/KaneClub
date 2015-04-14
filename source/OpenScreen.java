
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class OpenScreen extends JFrame{
    
    private JPanel panel1, panel2, panel3;
    private JButton newButton, oldButton;
    private JLabel welcome, intro;
    private Date d;
    
    public OpenScreen(){
        
        //set the layout
        setLayout(new BorderLayout());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation(dim.width/2-(300/2),dim.height/2 - (100/2));
        setSize(300, 100);
        
        
        buildPanel();
        add(panel1, BorderLayout.NORTH);
        add(panel2, BorderLayout.CENTER);
        add(panel3, BorderLayout.SOUTH);
        
        setVisible(true);
    }
    
    private void buildPanel(){
        d = new Date();
        DateFormat df = new SimpleDateFormat("MMM dd yyyy");
        
        panel1 = new JPanel();
        welcome = new JLabel("Welcome Commish");
        panel1.add(welcome);
        
        panel2 = new JPanel();
        intro = new JLabel("This is for Kane Club on "+df.format(d));
        panel2.add(intro);
        
        panel3 = new JPanel(new GridLayout(1,2));
        newButton = new JButton("New Round");
        newButton.addActionListener(new NewRoundListener());
        oldButton = new JButton("Past Rounds");
        oldButton.addActionListener(new OldRoundListener());
        panel3.add(newButton);
        panel3.add(oldButton);
    }
    
    //add listeners for the buttons
    private class NewRoundListener implements ActionListener{
        public void actionPerformed(ActionEvent e){
            try{
                new PickPlayers();
                dispose();
            }catch(java.io.FileNotFoundException err){
                err.printStackTrace();
            }
        }
    }
    
    private class OldRoundListener implements ActionListener{
        public void actionPerformed(ActionEvent e){
            try{
                new GetPastRoundInfo();
                dispose();
            }catch(java.io.FileNotFoundException err){
                err.printStackTrace();
            }catch(java.io.IOException err){
                err.printStackTrace();
            }
        }
    }
}