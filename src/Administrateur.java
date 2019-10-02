import javafx.scene.AmbientLight;

import java.util.ArrayList;

public class Administrateur {
    private String user ;
    private String password ;
    public static int idmax ;
    ArrayList <Compte> comptes ;
    ArrayList <Client> clients ;

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public ArrayList<Compte> getComptes() {
        return comptes;
    }

    public void setComptes(ArrayList<Compte> comptes) {
        this.comptes = comptes;
    }

    public ArrayList<Client> getClients() {
        return clients;
    }

    public void setClients(ArrayList<Client> clients) {
        this.clients = clients;
    }

    public Administrateur(String user , String password){
        this.user = user ;
        this.password = password ;
        comptes = new ArrayList<Compte>();
        clients = new ArrayList<Client>();
    }

    public void consulterComptes(ArrayList<Compte> comptes){
        System.out.println("La lsite des comptes : ");
        for (Compte c : comptes){
            System.out.println("\t->" + c.getUsername());
        }
    }

    public void consulterClients(ArrayList<Client> clients){
        System.out.println("La liste des clients : ");
        for (Client c : clients){
            System.out.println("-> le nom : "+c.getUsername());
            System.out.println("\t-> id : "+c.getId());
            System.out.println("\t-> credit : "+c.getCredit());
        }
    }

    public void ajouterClient(Compte compte){
        Client client = new Client(compte.getUsername(),compte.getPassword(),Administrateur.idmax,0,0,0);
        if ( ! clients.contains(client)){
            clients.add(client);
            Administrateur.idmax++;
        }
    }

    public Compte recherche(int id){
        for(Compte c : comptes){
           if(c.getId()==id) return c ;
        }
        return null;
    }

    @Override
    public boolean equals (Object o){
        if( ((Administrateur)o).getUser().equals(this.user) && ((Administrateur)o).getPassword().equals(this.password)){
            return true;
        }
        return false;
    }

}