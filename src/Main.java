import javax.swing.*;
import java.util.ArrayList;

public class Main {
    // Important !!!
    /* il faut de garantir tout d'abord toutes les droits a l'utlisateur dans le serveur MySQL avec :
        grant all privileges on <nom de la base de données>.* to '<User name>'@'localhost';*/
    /* de plus il faut desactivé le mode 'safe usage mode' pour pouvoir changer les données,avec :
        SET SQL_SAFE_UPDATES = 0;
     */
    // Il faut de changer le nom de la connexion MySQL et le mot de passe selon votre Serveur

    public static ArrayList<Compte> demandes = new ArrayList<Compte>();
    public static void main(String[] args) {

        try {
            JFrame fenetre = new JFrame();
            JLabel labeluser = new JLabel(" Entrez le nom d'utilisateur du serveur MySQL:");
            JTextField user = new JTextField();
            JLabel labelpassword = new JLabel(" Entrez le mot de passe du serveur MySQL:");
            JTextField password = new JTextField();
            JLabel labeldatabase = new JLabel(" Entrez le nom  de la base de données :");
            JTextField database = new JTextField();
            Object[] tab = new Object[]{labeluser, user,labelpassword,password,labeldatabase,database};
            int rep = JOptionPane.showOptionDialog(fenetre, tab, "Connexion à la base de données", JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE, null, null, null);
            if(rep==0){
                DataBase bdd = new DataBase();
                boolean cnx = bdd.connexion(user.getText(),password.getText(),database.getText(),fenetre);
                if (cnx) Interface.authentification(fenetre,bdd);
                else System.exit(0);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
