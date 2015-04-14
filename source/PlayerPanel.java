
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class PlayerPanel extends JPanel{

    Player player;
    Color defaultColor;
    private JTextField[] front, back;
    private JLabel in, out, total, points, vsQuota;
    private JLabel name;
    private int quota, inScore, outScore, totalScore, pulled, plusminus, goodSkins, stabeMoney, gre, ski, sss;
    private int goodGreenies;
    private int width;
    public int[] front9, back9, toPar;
    public boolean[] frontSkins, backSkins, goodG;
    private boolean inTheMoney, fp, hasSkins, sp, tp, hasGreenie, superWon, isWhite;
    private final int[] FRONTPAR = {5,4,5,3,4,4,3,4,4};
    private final int[] BACKPAR = {5,4,4,3,5,4,4,4,3};
    
    //constructor that sets up the whole thing
    public PlayerPanel(Player p){
        
        defaultColor = getBackground();
        
        player = p;
        
        FocusAdapter update = new FocusAdapter(){
            @Override
            public void focusLost(FocusEvent e){
                for(int i = 0; i< 9; i++){
                    if(front[i] != null || !(front[i].equals(""))){
                        front9[i] = Integer.parseInt(front[i].getText().trim());
                        toPar[i] = front9[i] - FRONTPAR[i];
                    }
                    if(back[i] != null || !(back[i].equals(""))){
                        back9[i] = Integer.parseInt(back[i].getText().trim());
                        toPar[i+9] = back9[i] - BACKPAR[i];
                    }
                }
                update();
            }
        };
        
        this.quota = p.getQuota();
        this.inScore = 0;
        this.outScore = 0;
        this.totalScore = 0;
        this.pulled = 0;
        this.plusminus = 0 - quota;
        this.stabeMoney = 0;
        hasGreenie = false;
        goodSkins = 0;
        goodGreenies = 0;
        inTheMoney = false;
        superWon = false;
        isWhite = false;
        fp = false;
        sp = false;
        tp = false;
        hasSkins = false;
        
        front = new JTextField[9];
        front9 = new int[9];
        frontSkins = new boolean[9];
        
        back = new JTextField[9];
        back9 = new int[9];
        backSkins = new boolean[9];
        
        toPar = new int[18];
        
        setLayout(null);
        
        width = (int)Toolkit.getDefaultToolkit().getScreenSize().getWidth();
        width = width/31;
        
        String n = p.getName();
        name = new JLabel(n, SwingConstants.CENTER);
        name.setBounds(0,0, width*4, 30);
        add(name);
        
        
        for(int i = 0; i < 9; i++){
            front9[i] = 0;
            frontSkins[i] = false;
            front[i] = new JTextField("0", SwingConstants.CENTER);
            front[i].setBounds((width*(4+i)),0,width, 30);
            add(front[i]);
            front[i].addFocusListener(update);
        }
        
        out = new JLabel(Integer.toString(outScore), SwingConstants.CENTER);
        out.setBounds((width*13),0,width,30);
        add(out);
        
        for(int i = 0;i<9;i++){
            back9[i] = 0;
            backSkins[i] = false;
            back[i] = new JTextField("0", SwingConstants.CENTER);
            back[i].setBounds((width*(14+i)), 0, width, 30);
            add(back[i]);
            back[i].addFocusListener(update);
        }
        
        in = new JLabel(Integer.toString(inScore), SwingConstants.CENTER);
        in.setBounds(width*23, 0 , width, 30);
        add(in);
        
        total = new JLabel(Integer.toString(totalScore), SwingConstants.CENTER);
        total.setBounds(width*24, 0, width, 30);
        add(total);
        
        points = new JLabel();
        points.setBounds(width*25, 0, width, 30);
        points.setVerticalAlignment(SwingConstants.CENTER);
        points.setHorizontalAlignment(SwingConstants.CENTER);
        add(points);
        
        vsQuota = new JLabel();
        vsQuota.setBounds(width*26, 0, width, 30);
        vsQuota.setVerticalAlignment(SwingConstants.CENTER);
        vsQuota.setHorizontalAlignment(SwingConstants.CENTER);
        add(vsQuota);
        
        goodG = new boolean[4];
        for(int i = 0; i<4;i++){
            ItemListener item = new ItemListener(){
                @Override
                public void itemStateChanged(ItemEvent e){
                    int st = e.getStateChange();
                    int change = -1;
                    JCheckBox  jbox = (JCheckBox)e.getSource();
                    if (st == ItemEvent.SELECTED){
                        goodGreenies++;
                        change = Integer.parseInt(jbox.getName().trim());
                        goodG[change] = true;
                    }else{
                        goodGreenies--;
                        change = Integer.parseInt(jbox.getName().trim());
                        goodG[change] = false;
                    }
                }
            };
            JCheckBox box = new JCheckBox();
            box.setName(Integer.toString(i));
            goodG[i] = false;
            if(!p.isInGreenies()){
                box.setEnabled(false);
            }
            box.addItemListener(item);
            box.setVerticalAlignment(SwingConstants.CENTER);
            box.setHorizontalAlignment(SwingConstants.CENTER);
            box.setBounds(width*(27+i), 0, width, 30);
            add(box);
        }

    }
    
    public PlayerPanel(PastPlayer pastplayer){
        
        setLayout(null);
        
        width = (int)Toolkit.getDefaultToolkit().getScreenSize().getWidth();
        width = width/31;
        
        front9 = new int[9];
        back9 = new int[9];
        front = new JTextField[9];
        back = new JTextField[9];
        
        name = new JLabel(pastplayer.getName(), SwingConstants.CENTER);
        name.setBounds(0,0, width*4, 30);
        add(name);
        
        for(int i = 0; i < 9; i++){
            front9[i] = pastplayer.getFront9(i);
            front[i] = new JTextField(Integer.toString(pastplayer.getFront9(i)), SwingConstants.CENTER);
            front[i].setBounds((width*(4+i)),0,width, 30);
            front[i].setEditable(false);
            add(front[i]);
        }
        
        out = new JLabel(Integer.toString(pastplayer.getOutScore()), SwingConstants.CENTER);
        out.setBounds((width*13),0,width,30);
        add(out);
        
        for(int i = 0; i < 9; i++){
            back9[i] = pastplayer.getBack9(i);
            back[i] = new JTextField(Integer.toString(pastplayer.getBack9(i)), SwingConstants.CENTER);
            back[i].setBounds((width*(14+i)),0,width, 30);
            back[i].setEditable(false);
            add(back[i]);
        }

        in = new JLabel(Integer.toString(pastplayer.getInScore()), SwingConstants.CENTER);
        in.setBounds(width*23, 0 , width, 30);
        add(in);
        
        total = new JLabel(Integer.toString(pastplayer.getTotalScore()), SwingConstants.CENTER);
        total.setBounds(width*24, 0, width, 30);
        add(total);
        
        points = new JLabel(Integer.toString(pastplayer.getPulled()), SwingConstants.CENTER);
        points.setBounds(width*25, 0, width, 30);
        add(points);
        
        vsQuota = new JLabel(Integer.toString(pastplayer.getPlusminus()), SwingConstants.CENTER);
        vsQuota.setBounds(width*26, 0, width, 30);
        add(vsQuota);

        for(int i = 0; i<4;i++){
            
            JCheckBox box = new JCheckBox();
            box.setEnabled(false);
            box.setSelected(pastplayer.getGreenies(i));
        
            box.setVerticalAlignment(SwingConstants.CENTER);
            box.setHorizontalAlignment(SwingConstants.CENTER);
            box.setBounds(width*(27+i), 0, width, 30);
            add(box);
        }
        for(int i = 0; i < 18; i++){
            if(i < 9){
                if(pastplayer.getSkins(i)){
                    front[i].setBackground(Color.RED);
                }
            }else{
                if(pastplayer.getSkins(i)){
                    back[i-9].setBackground(Color.RED);
                }
            }
        }
        this.fp = pastplayer.getPlace(0);
        this.sp = pastplayer.getPlace(1);
        this.tp = pastplayer.getPlace(2);
        if(pastplayer.getSuperWon()){
            front[6].setBackground(Color.BLUE);
        }

    }
    
    public void setWhite(boolean isWhite){
        this.isWhite = isWhite;
    }
    
    //update on the fly... have to figure out the skins
    public void update(){
        Color good = Color.RED;
        Color noGood = Color.WHITE;
        Color goodSuper = Color.BLUE;
        
        goodSkins = 0;
        
        outScore = 0;
        inScore = 0;
        pulled = 0;
        for(int n : front9){
            outScore += n;
        }
        for(int n : back9){
            inScore += n;
        }
        
        if(player.isInSkins()){
            for(int i = 0; i < 9; i++){
                if(frontSkins[i]){
                    front[i].setBackground(good);
                }
                if(backSkins[i]){
                    back[i].setBackground(good);
                }
                if(!frontSkins[i]){
                    front[i].setBackground(noGood);
                }
                if(!backSkins[i]){
                    back[i].setBackground(noGood);
                }
            }
        }
        
        setGoodSkins();
        
        if((fp || sp || tp) && player.isInStableford()){
            if(fp) setBackground(new Color(255, 215, 0));
            if(sp) setBackground(new Color(192, 192, 192));
            if(tp) setBackground(new Color(102, 93, 30));
        }else{
            if(!isWhite) setBackground(defaultColor);
        }
        
        if(superWon){
            front[6].setBackground(goodSuper);
        }
        else if(!superWon && !frontSkins[6]){
            front[6].setBackground(noGood);
        }
        else if(!superWon && frontSkins[6]){
            front[6].setBackground(good);
        }
        
        if(stabeMoney > 0){
            setInTheMoney(true);
        }
        else{
            setInTheMoney(false);
        }
        
        differ();
        pulled = pointsPulled();
        if(player.isInStableford()){
            plusminus = pulled - quota;
        }
        totalScore = outScore + inScore;
        
        out.setText(""+outScore);
        in.setText(""+inScore);
        total.setText(""+totalScore);
        points.setText(""+pulled);
        vsQuota.setText(""+plusminus);
    }
    
    private void setGoodSkins(){
        for(int i = 0; i < 9; i++){
            if(frontSkins[i]) goodSkins++;
            if(backSkins[i]) goodSkins++;
        }
        if(goodSkins > 0) hasSkins = true;
        else hasSkins = false;
    }
    
    private void setInTheMoney(boolean isGood){
        inTheMoney = isGood;
        if(goodSkins > 0) inTheMoney = true;
        if(superWon) inTheMoney = true;
        if(goodGreenies > 0){
            inTheMoney = true;
            hasGreenie = true;
        }
    }
    
    public boolean getInTheMoney(){
        return inTheMoney;
    }
    
    public void differ(){
        for(int i = 0; i< 9;i++){
            if(front9[i] == 1){
                toPar[i] = -3;
            }
            if(back9[i] == 1){
                toPar[i+9] = -3;
            }
        }
    }
    
    public int pointsPulled(){
        int sum = 0;
        for(int n : toPar){
            if(n > 2) sum += -1;
            if(n == 2) sum = sum;
            if(n == 1) sum += 1;
            if(n == 0) sum += 2;
            if(n == -1) sum += 4;
            if(n == -2) sum += 8;
            if(n == -3) sum += 10;
        }
        return sum;
    }
    
    public int getDiffer(){
        return plusminus;
    }
    
    public int getGreen(){
        return goodGreenies;
    }
    
    public int getGoodSkins(){
        return goodSkins;
    }
    
    public int getNewPoints(){
        int returnValue = 0;
        if(plusminus > 13) returnValue = 9;
        else if(plusminus > 9 ) returnValue = 7;
        else if(plusminus > 5) returnValue = 5;
        else if(plusminus > 2) returnValue = 3;
        else if(plusminus > -3) returnValue = returnValue;
        else if(plusminus > -7) returnValue = -1;
        else if(plusminus > -12) returnValue = -2;
        else returnValue = -3;
        
        if(player.isInStableford()){
            return quota + returnValue;
        }else{
            return quota;
        }
    }
    
    public int moneyMade(int gs, int gg, int money, int greens, int superSkin){
        sss = superSkin;
        gre = 0;
        ski = 0;
        if(inTheMoney){
            if(money == 1 && hasSkins){
                ski = gs;
            }else if (goodSkins != 0){
                ski = gs * goodSkins;
            }
            if(greens == 1 && hasGreenie){
                gre = gg;
            }else if (goodGreenies != 0){
                gre = gg * goodGreenies;
            }
        }
        if(superWon) return gre + ski + stabeMoney + superSkin;
        else return gre + ski + stabeMoney;
    }
    
    public int moneyMade(){
        if(inTheMoney){
            if(superWon){
                return gre + ski + stabeMoney + sss;
            }else{
                return gre + ski + stabeMoney;
            }
        }else{
            return 0;
        }
    }
    
    public void setStabeMoney(int stabeMoney){
        this.stabeMoney = stabeMoney;
    }
    
    public String getName(){
        return player.getName();
    }
    
    public void setFP(boolean fp){
        this.fp = fp;
    }
    
    public void setSP(boolean sp){
        this.sp = sp;
    }
    
    public void setTP(boolean tp){
        this.tp = tp;
    }
    
    public boolean getFP(){
        return fp;
    }
    
    public boolean getSP(){
        return sp;
    }
    
    public boolean getTP(){
        return tp;
    }
    
    public boolean hasGoodSkin(){
        return hasSkins;
    }
    
    public boolean hasGoodGreenie(){
        return hasGreenie;
    }
    
    public Player getPlayer(){
        return player;
    }
    
    public void setWasSuperWon(boolean won){
        this.superWon = won;
    }
    
    public boolean wasSuperWon(){
        return superWon;
    }
    
    public boolean[] getGoodG(){
        return goodG;
    }
    
    public int getInScore(){
        return inScore;
    }
    
    public int getOutScore(){
        return outScore;
    }
    
    public int getTotalScore(){
        return totalScore;
    }
    
    public int getPulled(){
        return pulled;
    }
    
    public int getPlusMinus(){
        return plusminus;
    }
    
}