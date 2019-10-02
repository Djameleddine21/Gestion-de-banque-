
public class Compte {

    private String username ;
    private String password ;
    private int id ;
    private int demande ;

    public Compte(String username ,String password , int id,int demande){
        this.username = username ;
        this.password = password ;
        this.id = id;
        this.demande = demande ;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getDemande() { return demande; }

    public void setDemande(int demande) { this.demande = demande; }

    @Override
    public boolean equals (Object o){
          if( ((Compte)o).getUsername().equals(this.username)) return true;
          return false;
    }

}
