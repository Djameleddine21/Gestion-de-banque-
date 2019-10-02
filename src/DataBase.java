import jdk.nashorn.internal.runtime.ECMAException;

import javax.swing.*;
import java.sql.*;
import java.util.ArrayList;

public class DataBase {

    private static int idMax;
    private String user;
    private String password;
    private String url;
    private Connection connection =null;
    private Statement statement =null;

    public boolean connexion(String user, String password, String bdd, JFrame fenetre){
        this.user = user ;
        this.password = password ;
        this.url = "jdbc:mysql://localhost:3306/"+bdd+"?zeroDateTimeBehavior=CONVERT_TO_NULL&serverTimezone=UTC";
       try{
           Class.forName("com.mysql.cj.jdbc.Driver");
           connection = DriverManager.getConnection(url, user, password);
            JOptionPane.showMessageDialog(fenetre,"Connexion avec Succes","Message",JOptionPane.INFORMATION_MESSAGE);
            return true;
       }
       catch (Exception e){
            JOptionPane.showMessageDialog(fenetre,"Impossible de connecté a ce serveur","Erreur",JOptionPane.ERROR_MESSAGE);
            return false;
       }
       finally {
           try {
               connection.close();
           }
           catch (Exception e){}
       }

    }
    public ArrayList<Compte> chargerComptes() throws Exception {
        Class.forName("com.mysql.cj.jdbc.Driver");
        connection = DriverManager.getConnection(url, user, password);
        statement = connection.createStatement();
        ArrayList<Compte> comptes = new ArrayList<>();
        ResultSet resultSet = statement.executeQuery("select * from comptes");
        while (resultSet.next()) {
            comptes.add(new Compte(resultSet.getString(1), resultSet.getString(2), resultSet.getInt(3),resultSet.getInt(4)));
        }
        resultSet.close();
        statement.close();
        connection.close();
        return comptes;
    }

    public Administrateur chargerAdministrateur() throws Exception {
        Class.forName("com.mysql.cj.jdbc.Driver");
        connection = DriverManager.getConnection(url, user, password);
        statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("select * from admin");
        Administrateur admin =null;
        while (resultSet.next()) {
            admin = new Administrateur(resultSet.getString(1), resultSet.getString(2));
        }
        resultSet.close();
        statement.close();
        connection.close();
        return admin;
    }

    public ArrayList<Client> chargerClients(ArrayList<Compte> comptes) throws Exception{
        Class.forName("com.mysql.cj.jdbc.Driver");
        connection = DriverManager.getConnection(url, user, password);
        statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("select * from clients");
        ArrayList<Client> clients= new ArrayList<Client>();
        while (resultSet.next()){
            for (Compte c : comptes){
                if (c.getId()==resultSet.getInt(1)) clients.add(new Client(c.getUsername(),c.getPassword(),resultSet.getInt(1),resultSet.getDouble(2),resultSet.getDouble(3),resultSet.getDouble(4)));
            }
        }
        resultSet.close();
        statement.close();
        connection.close();
        return clients;
    }

    public void addAmin(Administrateur admin)throws NullPointerException,Exception{
        Class.forName("com.mysql.cj.jdbc.Driver");
        connection = DriverManager.getConnection(url, user, password);
        statement = connection.createStatement();
        String cmd = "insert into admin value('"+admin.getUser()+"','"+admin.getPassword()+"',"+1+")" ;
        statement.executeUpdate(cmd);
        statement.close();
        connection.close();
    }

    public void addCompte(Compte c)throws Exception{
        Class.forName("com.mysql.cj.jdbc.Driver");
        connection = DriverManager.getConnection(url, user, password);
        statement = connection.createStatement();
        String cmd = "insert into comptes value('"+c.getUsername()+"','"+c.getPassword()+"',"+0+","+0+")" ;
        statement.executeUpdate(cmd);
        statement.close();
        connection.close();
    }

    public ArrayList<Compte> demandesCompteClient()throws Exception{
        Class.forName("com.mysql.cj.jdbc.Driver");
        connection = DriverManager.getConnection(url, user, password);
        statement = connection.createStatement();
        ResultSet res = statement.executeQuery("select * from comptes where demande=1");
        ArrayList<Compte> comptes= new ArrayList<Compte>();
        while (res.next()){
           comptes.add(new Compte(res.getString(1),res.getString(2) ,res.getInt(3),res.getInt(4)));
        }
        res.close();
        statement.close();
        connection.close();
        return comptes;

    }

    public void demandeClient(Compte c)throws Exception {
        Class.forName("com.mysql.cj.jdbc.Driver");
        connection = DriverManager.getConnection(url,user,password);
        statement = connection.createStatement();
        String cmd = "update comptes set demande=1 where user='"+c.getUsername()+"'" ;
        statement.executeUpdate(cmd);
        statement.close();
        connection.close();
    }

    public Compte getCompte(String user)throws Exception{
        Class.forName("com.mysql.cj.jdbc.Driver");
        connection = DriverManager.getConnection(url,this.user,password);
        statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("select * from comptes where user='"+user+"'");
        if (resultSet.next()){
            return new Compte(resultSet.getString(1),resultSet.getString(2),resultSet.getInt(3),resultSet.getInt(4));
        }
        resultSet.close();
        statement.close();
        connection.close();
        return null;
    }

    public Client getClient(Compte compte)throws Exception{
        Class.forName("com.mysql.cj.jdbc.Driver");
        connection = DriverManager.getConnection(url,user,password);
        statement = connection.createStatement();
        ResultSet res = statement.executeQuery("select * from clients where num="+compte.getId());
        Client c = null;
        if (res.next()) {
            c= new Client(compte.getUsername(),compte.getPassword(),res.getInt(1),res.getDouble(2),res.getDouble(3),res.getDouble(4));
        }
        res.close();
        statement.close();
        connection.close();
        return c;
    }

    public void addVersement(int id , double value)throws Exception{
        Class.forName("com.mysql.cj.jdbc.Driver");
        connection = DriverManager.getConnection(url,user,password);
        statement = connection.createStatement();
        statement.executeUpdate("update clients set retrait="+value+" where num="+id);
        statement.close();
        connection.close();
    }

    public void addRetrait(int id , double value)throws Exception{
        Class.forName("com.mysql.cj.jdbc.Driver");
        connection = DriverManager.getConnection(url,user,password);
        statement = connection.createStatement();
        statement.executeUpdate("update clients set versement="+value+" where num="+id);
        statement.close();
        connection.close();
    }

    public int getIdMax()throws Exception{
        Class.forName("com.mysql.cj.jdbc.Driver");
        connection = DriverManager.getConnection(url,user,password);
        statement = connection.createStatement();
        int id=1;
        ResultSet res = statement.executeQuery("select idmax from admin");
        if (res.next()){
            id = res.getInt(1);
        }
        res.close();
        statement.close();
        connection.close();
        return id;
    }

    public void setIdMax(int id)throws Exception{
        Class.forName("com.mysql.cj.jdbc.Driver");
        connection = DriverManager.getConnection(url,user,password);
        statement = connection.createStatement();
        statement.executeUpdate("update admin set idmax="+id);
        statement.close();
        connection.close();
    }

    public void addclient(DataBase bdd,Compte c)throws Exception{
        Class.forName("com.mysql.cj.jdbc.Driver");
        connection = DriverManager.getConnection(url,this.user,password);
        statement = connection.createStatement();
        statement.executeUpdate("insert into clients value("+(DataBase.idMax)+","+0+","+0+","+0+")");
        statement.close();
        connection.close();
        DataBase.idMax++;
        bdd.setIdMax(DataBase.idMax);
        setdemande(c);
    }

    public void setdemande(Compte c)throws Exception{
        Class.forName("com.mysql.cj.jdbc.Driver");
        connection = DriverManager.getConnection(url,this.user,password);
        statement = connection.createStatement();
        statement.executeUpdate("update comptes set demande=0 , id="+(DataBase.idMax-1)+" where user='"+c.getUsername()+"'");
        statement.close();
        connection.close();
    }

    public ArrayList<Client> demandesversement()throws Exception{
        Class.forName("com.mysql.cj.jdbc.Driver");
        connection = DriverManager.getConnection(url,this.user,password);
        statement = connection.createStatement();
        ResultSet res = statement.executeQuery("select * from clients inner join comptes on num=id");
        ArrayList<Client> clients = new ArrayList<Client>();
        while (res.next()){
            if (res.getInt(3)!=0){
                clients.add(new Client(res.getString(5),res.getString(6),res.getInt(7),res.getInt(2),res.getInt(3),res.getInt(4)));
            }
        }
        return clients;
    }

    public ArrayList<Client> demandesretrait()throws Exception{
        Class.forName("com.mysql.cj.jdbc.Driver");
        connection = DriverManager.getConnection(url,this.user,password);
        statement = connection.createStatement();
        ResultSet res = statement.executeQuery("select * from clients inner join comptes on num=id");
        ArrayList<Client> clients = new ArrayList<Client>();
        while (res.next()){
            if (res.getInt(4)!=0){
                clients.add(new Client(res.getString(5),res.getString(6),res.getInt(7),res.getInt(2),res.getInt(3),res.getInt(4)));
            }
        }
        return clients;
    }

    public void retrait(Client client)throws Exception{
        Class.forName("com.mysql.cj.jdbc.Driver");
        connection = DriverManager.getConnection(url,this.user,password);
        statement = connection.createStatement();
        double credit = client.getCredit();
        credit = credit - client.getRetrait();
        client.setCredit(credit);
        client.setRetrait(0.0);
        statement.executeUpdate("update clients set retrait=0,credit="+credit+" where num="+client.getId());
        statement.close();
        connection.close();
    }

    public void versement(Client client)throws Exception{
        Class.forName("com.mysql.cj.jdbc.Driver");
        connection = DriverManager.getConnection(url,this.user,password);
        statement = connection.createStatement();
        double credit = client.getCredit();
        credit = credit + client.getVersement();
        client.setCredit(credit);
        client.setVersement(0.0);
        statement.executeUpdate("update clients set versement=0,credit="+credit+" where num="+client.getId());
        statement.close();
        connection.close();
    }

}
