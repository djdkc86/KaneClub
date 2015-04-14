
public class PastPlayer{
    private String name;
    private int [] back9, front9;
    private int outScore, inScore, totalScore, pulled, plusminus, moneyMade;
    private boolean [] greenies, skins, place;
    private boolean superWon, moneyMaker;
    
    public PastPlayer(String name, int [] front, int [] back, int outScore, int inScore, int totalScore, int pulled, int plusminus, int moneyMade, boolean [] greenie, boolean superWon, boolean moneyMaker, boolean [] skin, boolean [] places){
        this.name = name;
        front9 = new int[9];
        back9 = new int[9];
        for(int i = 0; i <9;i++){
            front9[i] = front[i];
            back9[i] = back[i];
        }
        this.outScore = outScore;
        this.inScore = inScore;
        this.totalScore = totalScore;
        this.pulled = pulled;
        this.plusminus = plusminus;
        this.moneyMade = moneyMade;
        greenies = new boolean[4];
        for(int i = 0; i<4;i++){
            greenies[i] = greenie[i];
        }
        skins = new boolean[18];
        for(int i =0; i< 18; i++){
            skins[i] = skin[i];
        }
        place = new boolean[3];
        for(int i = 0; i< 3;i++){
            place[i] = places[i];
        }
        this.superWon = superWon;
        this.moneyMaker = moneyMaker;
    }
    
    public int getFront9(int i){
        return front9[i];
    }
    
    public int getBack9(int i){
        return back9[i];
    }
    
    public int getOutScore(){
        return outScore;
    }
    
    public int getInScore(){
        return inScore;
    }
    
    public int getTotalScore(){
        return totalScore;
    }
    
    public int getPulled(){
        return pulled;
    }
    
    public int getPlusminus(){
        return plusminus;
    }
    
    public int getMoneyMade(){
        return moneyMade;
    }
    
    public boolean getMoneyMaker(){
        return moneyMaker;
    }
    
    public boolean getSuperWon(){
        return superWon;
    }
    
    public boolean getGreenies(int i){
        return greenies[i];
    }
    
    public boolean getSkins(int i){
        return skins[i];
    }
    
    public boolean getPlace(int i){
        return place[i];
    }
    
    public String getName(){
        return name;
    }
    
}