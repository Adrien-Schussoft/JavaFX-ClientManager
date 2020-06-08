package test.org.adrien.model; 

import org.adrien.model.Client;
import org.adrien.model.ClientDAO;
import org.adrien.model.Connexion;
import org.junit.Test;
import org.junit.Before; 
import org.junit.After;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

/** 
* ClientDAO Tester. 
* 
* @author Adrien Dessartre
* @since <pre>mai 31, 2020</pre> 
* @version 1.0 
*/ 
public class ClientDAOTest { 

@Before
public void before() throws Exception {

} 

@After
public void after() throws Exception { 
} 

/** 
* 
* Method: insert(Client cli)
* 
*/

@Test
public void testInsert() throws Exception {
//TODO: Test goes here...
    Connection conn;
    conn = Connexion.getConnexion();
    Client cli = new Client();
    ClientDAO clientDAO = new ClientDAO();
    cli.setNom("testInsert");
    cli.setPrenom("test");
    cli.setVille("test");
    List<Client> clientList = clientDAO.list();
    int initialSize = clientList.get(clientList.size() - 1).getId();
    try {
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
    List<Client> newClientList = clientDAO.list();
    int newSize = newClientList.get(newClientList.size() - 1).getId();
    Client newCli = new Client(newSize);

    assertEquals(newCli.getId(),newSize);
    clientDAO.delete(newCli);
}
    /**
* 
* Method: update(Client cli)
* 
*/ 
@Test
public void testUpdate() throws Exception { 
//TODO: Test goes here...
    ClientDAO clientDAO = new ClientDAO();
    List<Client> clientList = clientDAO.list();
    int lastIndex = clientList.get(clientList.size() - 1).getId();
    Client client = clientDAO.findById(lastIndex);
    client.setNom("TestUpdate");
    client.setPrenom("TestUpdate");
    client.setVille("TestUpdate");
    clientDAO.update(client);
    client = clientDAO.findById(lastIndex);

    assertEquals(lastIndex,client.getId());
    assertEquals("TestUpdate", client.getNom());
    assertEquals("TestUpdate",client.getPrenom());
    assertEquals("TestUpdate",client.getVille());
} 

/** 
* 
* Method: delete(Client cli)
* 
*/

    @Test
public void testDelete() throws Exception { 
//TODO: Test goes here...
    Connection conn;
    ClientDAO clientDAO = new ClientDAO();
    List<Client> clientList = clientDAO.list();
    int initialSize = clientList.size() - 1;
    int lastIndex = clientList.get(initialSize).getId();

    try {
        conn = Connexion.getConnexion();
        String query = "DELETE FROM client WHERE cli_id = (?)";
        PreparedStatement pmt = conn.prepareStatement(query);
        pmt.setInt(1,lastIndex);
        pmt.execute();
        pmt.close();
        conn.close();
    }catch (SQLException e){
        e.printStackTrace();
    }
    List<Client> newClientList = clientDAO.list();
    int newSize = newClientList.size();
    assertEquals(initialSize,newSize);
} 

/** 
* 
* Method: findById(int id)
* 
*/ 
@Test
public void testFindById() throws Exception {
//TODO: Test goes here...
    Connection conn;
    conn = Connexion.getConnexion();
    ResultSet rs = null;
    Client client = new Client();
    ClientDAO clientDAO = new ClientDAO();
    client.setNom("testFind");
    client.setPrenom("test");
    client.setVille("test");

    //insert a new client
    clientDAO.insert(client);
    List<Client> newClientList = clientDAO.list();
    int newSize = newClientList.get(newClientList.size() - 1).getId();

    try {
        String query = "SELECT * FROM CLIENT WHERE cli_id = (?)";
        PreparedStatement stm = conn.prepareStatement(query);

        stm.setInt(1, newSize);
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
    assertEquals(client.getId(),newSize);
} 

/** 
* 
* Method: list()
* 
*/ 
@Test
public void testList() throws Exception { 
//TODO: Test goes here...
    ClientDAO clientDAO = new ClientDAO();
    List<Client> clientList = clientDAO.list();

    for(Client client : clientList) {
        assertNotNull(client.getId());
        assertNotNull(client.getNom());
        assertNotNull(client.getPrenom());
        assertNotNull(client.getVille());
    }
}

} 
