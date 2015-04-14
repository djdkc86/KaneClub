
import javax.swing.*;
import java.awt.*;

public class Scorecard extends JPanel{
    
    JPanel a, c;
    private int width;
    
    public Scorecard(){
        width = (int)Toolkit.getDefaultToolkit().getScreenSize().getWidth();
        width = width/31;
        setLayout(new GridLayout(2,1));
        holes();
        pars();
        
        add(a);
        add(c);
    }
    
 
    private void holes(){
        a = new JPanel(null);
        for(int i =0;i<25;i++){
            JLabel label = new JLabel();
            if(i==0){
                label.setText("Hole");
                label.setBounds(0,0, width*4, 20);
            }
            if(i>=1 && i<=9){
                label.setText(""+i);
            }
            if(i==10){
                label.setText("Out");
            }
            if(i>=11 && i<=19){
                label.setText(""+(i-1));
            }
            if(i==20){
                label.setText("In");
            }
            if(i==21){
                label.setText("Total");
            }
            if(i==22){
                label.setText("Points");
            }
            if(i==23){
                label.setText("+/-");
            }
            if(i==24){
                label.setText("GREENIES");
                label.setBounds(width*27, 0, width*4, 20);
            }
            if(i>0 && i < 24){
                label.setBounds(width*(3+i), 0 , width, 20);
            }
            label.setHorizontalAlignment(SwingConstants.CENTER);
            label.setVerticalAlignment(SwingConstants.CENTER);
            a.add(label);
        }
    }
    
    private void pars(){
        
        c = new JPanel(null);
        for(int i = 0; i< 28; i++){
            JLabel label = new JLabel("");
            if(i == 0){
                label.setText("Pars");
            }
            if(i == 10 || i == 20){
                label.setText(""+36);
            }
            if(i == 21){
                label.setText(""+72);
            }
            if(i == 1 || i == 3 || i == 11 || i == 15){
                label.setText(""+5);
            }
            if(i == 4 || i == 7 || i == 14 || i == 19){
                label.setText(""+3);
            }
            if(i == 2 || i == 5 || i == 6 || i == 8 || i == 9 || i ==12 || i == 13 || i == 16 || i == 17 || i == 18){
                label.setText(""+4);
            }
            if(i == 24) label.setText(""+4);
            if(i == 25) label.setText(""+7);
            if(i == 26) label.setText(""+13);
            if(i == 27) label.setText(""+18);
            if(i==0){
                label.setBounds(0,0, width*4, 20);
            }else{
                label.setBounds(width*(3+i), 0 , width, 20);
            }
            label.setHorizontalAlignment(SwingConstants.CENTER);
            label.setVerticalAlignment(SwingConstants.CENTER);
            c.add(label);
        }
    }

}