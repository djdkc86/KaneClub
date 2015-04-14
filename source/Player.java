
public class Player implements Comparable<Player>{
    private String name;
    private int quota;
    private int superSkin;
    private int skinMoney, greenieMoney, stablefordMoney;
    private final int POTS = 10;
    private int weeksBehind;
    private boolean inGreenies, inSkins, inStableford, inSuperSkin, upToDate;
    
    public Player(){
        this.name = "";
        this.quota = 36;
        this.superSkin = 0;
        this.weeksBehind = 0;
        this.inGreenies = false;
        this.inSkins = false;
        this.inStableford = false;
        this.inSuperSkin = false;
        this.upToDate = false;
    }
    
    public Player(String name){
        this.name = name;
        this.quota = 36;
        this.superSkin = 0;
        this.weeksBehind = 0;
        this.inGreenies = false;
        this.inSkins = false;
        this.inStableford = false;
        this.inSuperSkin = false;
        this.upToDate = false;
    }
    
    public Player(String name, int quota, boolean upToDate){
        this.name = name;
        this.quota = quota;
        this.superSkin = 0;
        this.weeksBehind = 0;
        this.inGreenies = false;
        this.inSkins = false;
        this.inStableford = false;
        this.inSuperSkin = false;
        this.upToDate = upToDate;
    }
    
    public String getName(){
        return name;
    }
    
    public void setName(String name){
        this.name = name;
    }
    
    public void setWeeksBehind(int weeksBehind){
        this.weeksBehind = weeksBehind;
        this.superSkin = 5 * weeksBehind;
    }
    
    public int getWeeksBehind(){
        return weeksBehind;
    }
    
    public int getQuota(){
        return quota;
    }
    
    public void setQuota(int quota){
        this.quota = quota;
    }
    
    public int getSuperSkin(){
        return superSkin;
    }
    
    public void setSuperSkin(int superSkin){
        this.superSkin = superSkin;
    }
    
    public boolean isInGreenies(){
        return inGreenies;
    }
    
    public void setInGreenies(boolean inGreenies){
        this.inGreenies = inGreenies;
    }
    
    public boolean isInStableford(){
        return inStableford;
    }
    
    public void setInStableford(boolean inStableford){
        this.inStableford = inStableford;
    }
    
    public boolean isInSkins(){
        return inSkins;
    }
    
    public void setInSkins(boolean inSkins){
        this.inSkins = inSkins;

    }
    
    public boolean isInSuperSkin(){
        return inSuperSkin;
    }
    
    public void setUpToDate(boolean upToDate){
        this.upToDate = upToDate;
    }
    
    public void setInSuperSkin(boolean inSuperSkin){
        this.inSuperSkin = inSuperSkin;
        if(inSuperSkin) this.superSkin += 5;
    }
    
    public int getPot(boolean inGame){
        if(inGame) return POTS;
        else return 0;
    }
    
    public int compareTo(Player p){
        return name.compareTo(p.name);
    }
}