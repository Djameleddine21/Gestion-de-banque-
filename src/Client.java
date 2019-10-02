public class Client extends Compte {

    private double credit ;
    private int id ;
    private double versement ;
    private double retrait ;

    public Client(String username ,String password,int id,double credit,double versement,double retrait){
        super(username ,password,id,0);
        this.id = id ;
        this.credit = credit;
        this.versement = versement ;
        this.retrait = retrait ;
    }

    public double getCredit() {
        return credit;
    }

    public void setCredit(double credit) {
        this.credit = credit;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getVersement() { return versement; }

    public void setVersement(double versement) { this.versement = versement; }

    public double getRetrait() {
        return retrait;
    }

    public void setRetrait(double retrait) {
        this.retrait = retrait;
    }

    public void versement(double x){
        credit +=x ;
    }

    public void retrait(double x){
        if (x > credit){
            System.out.println("Cette operation est impossible, vous avez uniquement : "+credit);
        }
        else credit = credit-x;
    }

}
