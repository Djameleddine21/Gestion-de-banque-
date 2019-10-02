import com.sun.scenario.effect.impl.sw.java.JSWBoxBlurPeer;

import javax.swing.*;
import java.awt.dnd.DropTarget;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class Interface extends JFrame{

    public static void authentification(JFrame fenetre, DataBase bdd)throws Exception{

        fenetre.setTitle("Accueil");
        fenetre.setSize(500,500);
        fenetre.setLocationRelativeTo(null);
        fenetre.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        fenetre.setLayout(null);
        // ajouter les bottons
        Administrateur admin = bdd.chargerAdministrateur();
        String s ;
        if (admin!=null) s= "Se connecter en tant que Gestionnaire";
        else s = "Créer un Compte de gestionnaire";
        JButton cnx_admin = new JButton(s);
        cnx_admin.setBounds(100,80,300,30);
        fenetre.getContentPane().add(cnx_admin);
        JButton cnx = new JButton("Se connecter en tant que Client");
        cnx.setBounds(100,200,300,30);
        fenetre.getContentPane().add(cnx);
        JButton ins = new JButton("S'inscrire");
        ins.setBounds(100,300,300,30);
        fenetre.getContentPane().add(ins);
        // gestion des evenements
        if(admin != null) connexionAdminEvent(fenetre,cnx_admin,bdd);
        else inscriptionAdmin(fenetre,cnx_admin,bdd);
        inscriptionEvent(fenetre,ins,bdd);
        connexionEvent(fenetre,cnx,bdd);
        fenetre.setVisible(true);
    }

    public static void inscriptionAdmin(JFrame fenetre,JButton cnx_admin,DataBase bdd){
        cnx_admin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JLabel labelNom = new JLabel("nom:");
                JLabel labelPassword = new JLabel("mot de passe :");
                JTextField nom = new JTextField();
                JPasswordField password = new JPasswordField();
                Object[] tab = new Object[]{labelNom, nom, labelPassword, password};
                int rep = JOptionPane.showOptionDialog(fenetre, tab, "Inscription", JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE, null, null, null);
                if (rep == 0) {
                    String mdp = String.valueOf(password.getPassword());
                    try {
                        bdd.addAmin(new Administrateur(nom.getText(),mdp));
                        JOptionPane.showMessageDialog(fenetre,"Inscription avec succes");
                    }
                    catch (Exception e1) {
                        e1.printStackTrace();
                    }
                }
            }
        });
    }

    public static void connexionAdminEvent(JFrame fenetre,JButton cnx_admin, DataBase bdd)throws Exception {
        Administrateur admin = bdd.chargerAdministrateur();
        cnx_admin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JLabel labelNom = new JLabel("nom:");
                JLabel labelPassword = new JLabel("mot de passe :");
                JTextField nom = new JTextField();
                JPasswordField password = new JPasswordField();
                Object[] tab = new Object[]{labelNom, nom, labelPassword, password};
                int rep = JOptionPane.showOptionDialog(fenetre, tab, "Conexion", JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE, null, null, null);
                if (rep == 0) {
                    String mdp = String.valueOf(password.getPassword());
                    if (!admin.equals(new Administrateur(nom.getText(),mdp))) {
                        JOptionPane.showMessageDialog(fenetre, "Ce Gestionnaire n'existe pas", "Erreur", JOptionPane.ERROR_MESSAGE);
                    }
                    else{
                        try {
                            pageAdmin(fenetre,bdd);
                        }
                        catch (Exception e1){
                            e1.printStackTrace();
                        }
                    }
                }
            }
        });
    }

    public static void connexionEvent(JFrame fenetre,JButton cnx,DataBase bdd)throws Exception{
        ArrayList<Compte> comptes = bdd.chargerComptes();
        cnx.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JLabel labelNom = new JLabel("nom:");
                JLabel labelPassword = new JLabel("mot de passe :");
                JTextField nom = new JTextField();
                JPasswordField password = new JPasswordField();
                Object[] tab = new Object[]{labelNom, nom, labelPassword, password};
                int rep = JOptionPane.showOptionDialog(fenetre, tab, "Conexion", JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE, null, null, null);
                if (rep == 0) {
                    String mdp = String.valueOf(password.getPassword());
                    if (! comptes.contains(new Compte(nom.getText(),mdp,0,0))) {
                        JOptionPane.showMessageDialog(fenetre, "Ce Client n'existe pas", "Erreur", JOptionPane.ERROR_MESSAGE);
                    }
                    else{
                        try {
                            Compte c =bdd.getCompte(nom.getText());
                            // Cas d'un Client
                            if (bdd.chargerClients(comptes).contains(bdd.getClient(c))){
                                pageClient(fenetre,bdd,bdd.getClient(c));
                            }
                            // Cas d'un compte
                            else {
                                pageCompte(fenetre,bdd,bdd.getCompte(nom.getText()));
                            }
                        }
                        catch (Exception e1){
                            e1.printStackTrace();
                        }
                    }
                }
            }
        });
    }

    public static void pageAdmin(JFrame fenetre ,DataBase bdd)throws Exception{
        fenetre.getContentPane().removeAll();
        fenetre.repaint();
        fenetre.setTitle("Page Gestionnaire");
        fenetre.setSize(500,600);
        fenetre.setLocationRelativeTo(null);
        JButton listeCompte = new JButton("Consulter la liste des comptes");
        listeCompte.setBounds(100,20,300,30);
        fenetre.getContentPane().add(listeCompte);
        JButton listeClient = new JButton("Consulter la liste des clients");
        listeClient.setBounds(100,120,300,30);
        fenetre.getContentPane().add(listeClient);
        JButton demandCompte = new JButton("Visualiser les demnades des comptes");
        demandCompte.setBounds(100,220,300,30);
        fenetre.getContentPane().add(demandCompte);
        JButton demandVersement = new JButton("Effectuer des versements");
        demandVersement.setBounds(100,320,300,30);
        fenetre.getContentPane().add(demandVersement);
        JButton demandRetrait = new JButton("Effectuer des retraits");
        demandRetrait.setBounds(100,420,300,30);
        fenetre.getContentPane().add(demandRetrait);
        // TO DO gestion des evenements
        ControllerAdmin.listCompt(fenetre,listeCompte,bdd);
        ControllerAdmin.listClient(fenetre,listeClient,bdd);
        ControllerAdmin.demandComptes(fenetre,demandCompte,bdd);
        ControllerAdmin.demandRetrait(fenetre,demandRetrait,bdd);
        ControllerAdmin.demandVersement(fenetre,demandVersement,bdd);
    }

    public static void pageClient(JFrame fenetre,DataBase bdd,Client client)throws Exception{
        fenetre.getContentPane().removeAll();
        fenetre.repaint();
        fenetre.setTitle("Page Client");
        fenetre.setLocationRelativeTo(null);
        JLabel label = new JLabel("Utilisitaeur : "+client.getUsername()+"      ID : "+client.getId()+"      Credit: "+client.getCredit());
        label.setBounds(100,50,450,30);
        fenetre.getContentPane().add(label);
        JButton versement = new JButton("Demander Versement ");
        versement.setBounds(100,200,300,30);
        fenetre.getContentPane().add(versement);
        JButton retrait = new JButton("Demander Retriat ");
        retrait.setBounds(100,300,300,30);
        fenetre.getContentPane().add(retrait);
        // gestion des evenements
        demandVersementEvent(fenetre,versement,bdd,client.getId());
        demandRetraitEvent(fenetre,retrait,bdd,client.getId());
    }

    public static void demandRetraitEvent(JFrame fenetre ,JButton btn,DataBase bdd , int id){
        btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JLabel label = new JLabel(" Entrez la valeur que vous vouliez:");
                JTextField value = new JTextField();
                Object[] tab = new Object[]{label, value};
                int rep = JOptionPane.showOptionDialog(fenetre, tab, "Message", JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE, null, null, null);
                if(rep==0){
                    try {
                        double valeur = Double.valueOf(value.getText());
                        bdd.addRetrait(id,valeur);
                        JOptionPane.showMessageDialog(fenetre,"Votre demande est enregestré","Message",JOptionPane.INFORMATION_MESSAGE);
                    }
                    catch (Exception e1){
                        e1.printStackTrace();
                    }
                }
            }
        });
    }

    public static void demandVersementEvent(JFrame fenetre ,JButton btn,DataBase bdd ,int id){
        btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JLabel label = new JLabel(" Entrez la valeur que vous vouliez:");
                JTextField value = new JTextField();
                Object[] tab = new Object[]{label, value};
                int rep = JOptionPane.showOptionDialog(fenetre, tab, "Message", JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE, null, null, null);
                if(rep==0){
                    try {
                        double valeur = Double.valueOf(value.getText());
                        bdd.addVersement(id,valeur);
                        JOptionPane.showMessageDialog(fenetre,"Votre demande est enregestré","Message",JOptionPane.INFORMATION_MESSAGE);
                    }
                    catch (Exception e1){
                        e1.printStackTrace();
                    }
                }
            }
        });
    }


    public static void inscriptionEvent(JFrame fenetre,JButton ins,DataBase bdd)throws Exception{
        ArrayList<Compte> comptes = bdd.chargerComptes();
        ins.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                JLabel labelNom = new JLabel("nom:");
                JLabel labelPassword = new JLabel("mot de passe :");
                JTextField nom = new JTextField();
                JPasswordField password = new JPasswordField();
                Object[] tab = new Object[]{labelNom, nom, labelPassword, password};
                int rep = JOptionPane.showOptionDialog(fenetre, tab, "Inscription", JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE, null, null, null);
                if (rep == 0) {
                    try
                    {
                        String mdp = String.valueOf(password.getPassword());
                        if (comptes.contains(new Compte(nom.getText(), mdp, 0,0))) {
                            JOptionPane.showMessageDialog(fenetre, "Ce comptes est deja exister", "Erreur", JOptionPane.ERROR_MESSAGE);
                        }
                        else{
                            bdd.addCompte(new Compte(nom.getText(),mdp,0,0));
                            pageCompte(fenetre,bdd,new Compte(nom.getText(),mdp,0,0));
                        }
                    }
                    catch (Exception e1){
                        e1.printStackTrace();
                    }
                }
            }
        });
    }

    public static void pageCompte(JFrame fenetre,DataBase bdd,Compte compte){
        fenetre.getContentPane().removeAll();
        fenetre.getContentPane().repaint();
        fenetre.setTitle("Page d'accueil");
        fenetre.setSize(500,300);
        JLabel label = new JLabel("Utilisateur : "+compte.getUsername());
        label.setBounds(100,20,100,30);
        fenetre.getContentPane().add(label);
        JButton demande = new JButton("Demander un compte client");
        demande.setBounds(100,100,300,30);
        fenetre.getContentPane().add(demande);
        fenetre.setLocationRelativeTo(null);
        fenetre.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        demande.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    bdd.demandeClient(compte);
                }
                catch (Exception e1){
                    e1.printStackTrace();
                }
            }
        });
    }


}
