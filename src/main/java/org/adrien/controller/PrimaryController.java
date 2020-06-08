package org.adrien.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import org.adrien.model.Client;
import org.adrien.model.ClientDAO;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

import static org.adrien.controller.RegExpMatching.isValidInput;

public class PrimaryController implements Initializable {

    @FXML
    private TableView<Client> lst_clients;
    @FXML
    private TableColumn<Client, String> col_prenom;
    @FXML
    private TableColumn<Client, String> col_nom;
    @FXML
    private Button btn_sauver;
    @FXML
    private Button btn_annuler;
    @FXML
    private Button btn_supprimer;
    @FXML
    private Button btn_update;
    @FXML
    private Button btn_ok;
    @FXML
    private TextField text_prenom;
    @FXML
    private TextField text_nom;
    @FXML
    private TextField text_ville;
    @FXML
    private VBox vbox_form;
    @FXML
    private Label label_error;
    @FXML
    private Label label_details;
    @FXML
    private BorderPane borderPane;

    ObservableList<Client> model = FXCollections.observableArrayList();

    /**
     * Initialize TableView and data before the launch.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ClientDAO clientDAO = new ClientDAO();
        ArrayList client = clientDAO.list();
        for (int i =0;i<client.size();i++){
            model.add((Client) client.get(i));
        }
        lst_clients.setEditable(false);
        // Jonction du tableau avec les données
        col_prenom.setCellValueFactory(new PropertyValueFactory<>("prenom"));
        col_nom.setCellValueFactory(new PropertyValueFactory<>("nom"));
        // Indique au TableView le model à observer pour trouver les données
        lst_clients.setItems(model);
        getDataModelSelected();
        vbox_form.setVisible(false);
        label_error.setVisible(false);
    }

    /**
     * Save event to save data.
     * @param event
     * @throws SQLException
     */
    @FXML
    private void save(ActionEvent event) throws SQLException {
        if(!vbox_form.isVisible()){
            clearInputs();
            setVboxVisible();
        }else{
            if(isValidInput(text_nom.getText()) && isValidInput(text_prenom.getText()) && isValidInput(text_ville.getText())){
                label_error.setVisible(false);
                Client client = new Client();
                ClientDAO repo = new ClientDAO();
                client.setPrenom(text_prenom.getText());
                client.setNom(text_nom.getText());
                client.setVille(text_ville.getText());
                repo.insert(client);
                model.add(client);
                model.clear();
                ArrayList clientList = repo.list();
                model.addAll(clientList);
            }
            else{
                label_error.setText("Saisie incorrecte");
                label_error.setVisible(true);
                System.out.println("Saisie invalide");
            }
        }
    }

    /**
     * update event to update data.
     * @param event
     * @throws SQLException
     */
    @FXML
    private void update(ActionEvent event) throws SQLException {
        if(!vbox_form.isVisible()){
            setVboxVisible();
        }else {
            if(isValidInput(text_nom.getText()) && isValidInput(text_prenom.getText()) && isValidInput(text_ville.getText())) {
                label_error.setVisible(false);
                ClientDAO repo = new ClientDAO();
                Client client = new Client(lst_clients.getSelectionModel().getSelectedIndex(), text_nom.getText(), text_prenom.getText(), text_ville.getText());
                client.setId(lst_clients.getSelectionModel().getSelectedItem().getId());
                client.setPrenom(text_prenom.getText());
                client.setNom(text_nom.getText());
                client.setVille(text_ville.getText());
                repo.update(client);
                model.clear();
                ArrayList clientList = repo.list();
                model.addAll(clientList);
            }else{
                label_error.setText("Saisie incorrecte");
                label_error.setVisible(true);
                System.out.println("Saisie invalide");
            }
        }
    }

    /**
     * delete event to delete data.
     * @param event
     * @throws SQLException
     */
    @FXML
    private void delete(ActionEvent event) throws SQLException {
        if(!vbox_form.isVisible()){
            setVboxVisible();
        }else {
            ClientDAO repo = new ClientDAO();
            Client client = new Client(lst_clients.getSelectionModel().getSelectedIndex());
            client.setId(lst_clients.getSelectionModel().getSelectedItem().getId());
            System.out.println(client.getId());
            repo.delete(client);
            model.clear();
            ArrayList clientList = repo.list();
            model.addAll(clientList);
            clearInputs();
        }
    }

    /**
     * Cancel the user's input.
     * @param event
     */
    @FXML
    private void annuler(ActionEvent event) {
    clearInputs();
    setVboxUnvisible();
    }

    private void clearInputs(){
        text_prenom.clear();
        text_nom.clear();
        text_ville.clear();
    }

    /**
     * Get the information of the selected row in the textFields areas.
     */
    @FXML
    private void getDataModelSelected(){
         lst_clients.setOnMouseClicked(event1 -> {
         ClientDAO repo = new ClientDAO();
         Client client = repo.findById(lst_clients.getSelectionModel().getSelectedItem().getId());
         text_nom.setText(client.getNom());
         text_prenom.setText(client.getPrenom());
         text_ville.setText(client.getVille());
        });
    }

    @FXML
    private void setVboxVisible(){
       vbox_form.setVisible(true);
    }
    @FXML
    private void setVboxUnvisible(){
        vbox_form.setVisible(false);
    }

}
