import sun.plugin2.message.Message;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class ControllerAdmin {

    public static void demandVersement(JFrame fenetre,JButton btn,DataBase bdd){
        btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                    ArrayList<Client> list = bdd.demandesversement();
                    if ( ! list.isEmpty()) {
                        Object[] tab = new Object[list.size() + 1];
                        int i = 1;
                        tab[0] = new JLabel("Cliquer pour accepter la demande(un seul clique)");

                        for (Client client : list) {
                            tab[i] = new JButton(client.getUsername());
                            acceptVersement(fenetre,(JButton)tab[i],bdd,client);
                            i++;
                        }
                        JOptionPane.showOptionDialog(fenetre, tab, "Liste des demandes de versement", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, null, null);
                    }
                    else JOptionPane.showMessageDialog(fenetre, "la liste des demandes est vide","Liste des demandes de versement",JOptionPane.WARNING_MESSAGE);
                }
                catch (Exception e1){
                    e1.printStackTrace();
                }
            }
        });
    }

    public static void acceptVersement(JFrame fenetre,JButton btn,DataBase bdd ,Client client){
        btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                    bdd.versement(client);
                }
                catch (Exception e1){
                    e1.printStackTrace();
                }
            }
        });
    }

    public static void demandRetrait(JFrame fenetre,JButton btn,DataBase bdd){
        btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                    ArrayList<Client> list = bdd.demandesretrait();
                    if ( ! list.isEmpty()) {
                        Object[] tab = new Object[list.size() + 1];
                        int i = 1;
                        tab[0] = new JLabel("Cliquer pour accepter la demande(un seul clique)");

                        for (Client client : list) {
                            tab[i] = new JButton(client.getUsername());
                            acceptRetarit(fenetre,(JButton)tab[i],bdd,client);
                            i++;
                        }
                        JOptionPane.showOptionDialog(fenetre, tab, "Liste des demandes de retraits", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, null, null);
                    }
                    else JOptionPane.showMessageDialog(fenetre, "la liste des demandes est vide","Liste des demandes",JOptionPane.WARNING_MESSAGE);
                }
                catch (Exception e1){
                    e1.printStackTrace();
                }
            }
        });
    }

    public static void acceptRetarit(JFrame fenetre,JButton btn,DataBase bdd ,Client client){
        btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                    if (client.getCredit() < client.getRetrait()){
                        JOptionPane.showMessageDialog(fenetre,"Impossible d'effectuer cette opération (crédit < retrait)","Erreur",JOptionPane.ERROR_MESSAGE);
                    }
                    else {
                        bdd.retrait(client);
                    }
                }
                catch (Exception e1){
                    e1.printStackTrace();
                }
            }
        });
    }

    public static void demandComptes(JFrame fenetre,JButton btn,DataBase bdd)throws Exception{
        btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    ArrayList<Compte> list =  bdd.demandesCompteClient();
                    if( ! list.isEmpty()){
                        Object[] tab = new Object[list.size()+1];
                        int i=1;
                        tab[0]=new JLabel("Cliquer pour accepter la demande(un seul clique)");
                        for (Compte compte : list){
                            tab[i]= new JButton(compte.getUsername());
                            accepterdemande(fenetre,(JButton)tab[i],bdd,list.get(i-1));
                            i++;
                        }
                        JOptionPane.showOptionDialog(fenetre, tab, "Liste des comptes", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, null, null);
                    }
                    else {
                        JOptionPane.showMessageDialog(fenetre,"la liste des demandes est vide !","Message",JOptionPane.WARNING_MESSAGE);
                    }
                }
                catch (Exception e1){
                    e1.printStackTrace();
                }
            }
        });
    }

    public static void accepterdemande(JFrame fenetre , JButton btn,DataBase bdd,Compte compte){
        btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    bdd.addclient(bdd,compte);
                }
                catch (Exception e1){
                    e1.printStackTrace();
                }
            }
        });
    }

    public static void listCompt(JFrame fenetre,JButton btn,DataBase bdd)throws Exception{
        btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    ArrayList<Compte> list =  bdd.chargerComptes();
                    if(list!=null){
                        Object[] tab = new Object[list.size()];
                        int i=0;
                        for (Compte compte : list){
                            tab[i]= new JLabel(compte.getUsername());
                            i++;
                        }
                        int rep = JOptionPane.showOptionDialog(fenetre, tab, "Liste des comptes", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, null, null);
                    }
                    else {
                        JOptionPane.showMessageDialog(fenetre,"la liste des comptes est vide !","Message",JOptionPane.WARNING_MESSAGE);
                    }
                }
                catch (Exception e1){
                    e1.printStackTrace();
                }
            }
        });
    }

    public static void listClient(JFrame fenetre,JButton btn,DataBase bdd)throws Exception{
        btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    ArrayList<Client> list =  bdd.chargerClients(bdd.chargerComptes());
                    if(list!=null){
                        Object[] tab = new Object[list.size()];
                        int i=0;
                        for (Compte compte : list){
                            tab[i]= new JLabel(compte.getUsername());
                            i++;
                        }
                        int rep = JOptionPane.showOptionDialog(fenetre, tab, "Liste des comptes", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, null, null);
                    }
                    else {
                        JOptionPane.showMessageDialog(fenetre,"la liste des comptes est vide !","Message",JOptionPane.WARNING_MESSAGE);
                    }
                }
                catch (Exception e1){
                    e1.printStackTrace();
                }
            }
        });
    }

}
