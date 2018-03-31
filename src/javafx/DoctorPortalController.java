/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafx;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

/*******************************************************************************************************************/
public class DoctorPortalController implements Initializable {

    @FXML
    private JFXComboBox<String> combobox;
    @FXML
    private JFXTextField search_box;
    @FXML
    private JFXButton search_B;
     ObservableList<String> list2 = FXCollections.observableArrayList();
     private Connexion db;
 /*************************************************************************************************************/
      @FXML
    private void combobox_MouseClicke(MouseEvent event) {
       
    }
 /************************************************************************************************************/
     public void loadData2(){
       list2.clear();
         try {
             Connection con=Connexion.ConnecrDB();
           
            ResultSet rs = con.createStatement().executeQuery("SELECT info_P FROM rdv ");
            while (rs.next()) {
                //get string from db,whichever way 
                list2.add(new String(rs.getString(1)));
            }

        } catch (Exception ex) {
            System.err.println("Error"+ex);
        }
   }
 
     
     
 /***************************************************************************************************************/
    @FXML
    public void log_out(ActionEvent event) throws IOException {
        Parent loginAdmin = FXMLLoader.load(getClass().getResource("LoginDoc.fxml"));
           Scene ab = new Scene(loginAdmin);
            Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
            window.setScene(ab);
            window.show(); }   

/******************************************************************************************************************/
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        loadData2();
     combobox.setItems(list2);
     db = new Connexion();
    }  

   
/*****************************************************************************************************************/    
    
}
