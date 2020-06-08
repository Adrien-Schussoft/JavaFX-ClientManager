package org.adrien.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ClientDAO extends Connexion {

    private Connection conn;

    public ClientDAO(){

    }

    /**
     * Create a new client in database.
     * @param cli
     */
    public void insert(Client cli) {

        try {
            conn = Connexion.getConnexion();
            String query = "INSERT INTO client (cli_nom, cli_prenom, cli_ville) VALUES (?, ?, ?)";
            PreparedStatement stm = conn.prepareStatement(query);
            stm.setString(1, cli.getNom());
            stm.setString(2, cli.getPrenom());
            stm.setString(3, cli.getVille());
            stm.execute();
            stm.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * update an existing client in database.
     * @param cli
     */
    public void update(Client cli) {

        try{
            conn = Connexion.getConnexion();
            String query = "UPDATE client SET cli_nom = (?),cli_prenom = (?),cli_ville = (?) WHERE cli_id = (?)";
            PreparedStatement pmt = conn.prepareStatement(query);
            pmt.setString(1, cli.getNom());
            pmt.setString(2, cli.getPrenom());
            pmt.setString(3, cli.getVille());
            pmt.setInt(4,cli.getId());
            pmt.executeUpdate();
            pmt.close();
            conn.close();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    /**
     * delete an existing client in database.
     * @param cli
     */
    public void delete(Client cli) {

        try {
            conn = Connexion.getConnexion();
            String query = "DELETE FROM client WHERE cli_id = (?)";
            PreparedStatement pmt = conn.prepareStatement(query);
            pmt.setInt(1,cli.getId());
            pmt.execute();
            pmt.close();
            conn.close();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    /**
     * Search an existing client by id in database.
     * @param id
     * @return client
     */
    public Client findById(int id) {
        conn = Connexion.getConnexion();
        ResultSet rs = null;
        Client client = new Client();

        try {
            String query = "SELECT * FROM CLIENT WHERE cli_id = (?)";
            PreparedStatement stm = conn.prepareStatement(query);

            stm.setInt(1, id);
            rs = stm.executeQuery();
            if (rs.next()) {
                client.setId(rs.getInt("cli_id"));
                client.setNom(rs.getString("cli_nom"));
                client.setPrenom(rs.getString("cli_prenom"));
                client.setVille(rs.getString("cli_ville"));
            }
            else{
                client.setId(0);
                client.setNom("");
                client.setPrenom("");
                client.setVille("");
            }
            rs.close();
            stm.close();
            conn.close();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return client;
    }

    /**
     * Return a list of  all clients in database.
     * @return resultat
     */
    public ArrayList list() {
        conn = Connexion.getConnexion();
        ResultSet rs = null;
        Client client;
        ArrayList<Client> resultat = new ArrayList<>();
        String query = "SELECT * FROM client";
        try {
            PreparedStatement pmt = conn.prepareStatement(query);
            rs = pmt.executeQuery();
            while (rs.next()) {
                client = new Client(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4));
                resultat.add(client);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return resultat;
    }
}
