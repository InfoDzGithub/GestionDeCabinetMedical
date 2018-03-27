/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafx;

import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
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
import javafx.scene.control.ComboBox;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javax.swing.JOptionPane;

/**
 * FXML Controller class
 *
 * @author User
 */
public class ManageAppointmentController implements Initializable {

    @FXML
    private JFXDatePicker dateSelector;
    @FXML
    private JFXDatePicker SelectTimer;
    @FXML
    private ComboBox<String> combobox;
     @FXML
    private JFXTextField firstName_box;
    @FXML
    private JFXTextField familyName_box;
    @FXML
     ObservableList<String> list = FXCollections.observableArrayList();
    
 /*****************************************************************************************************************/
     @FXML
    public void Home(ActionEvent event) throws IOException {
        Parent loginAdmin = FXMLLoader.load(getClass().getResource("RecepPortal.fxml"));
           Scene ab = new Scene(loginAdmin);
            Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
            window.setScene(ab);
            window.show(); } 
  /******************************************************************************************************************/
@FXML
    public void Sign_Out(ActionEvent event) throws IOException {
        Parent loginAdmin = FXMLLoader.load(getClass().getResource("LoginRecep.fxml"));
           Scene ab = new Scene(loginAdmin);
            Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
            window.setScene(ab);
            window.show(); } 
 /******************************************************************************************************************/   
 public void loadData(){
       list.clear();
         try {
             Connection con=Connexion.ConnecrDB();
           
            ResultSet rs = con.createStatement().executeQuery("SELECT concat(concat(nom_pat,' '),concat(prenom_pat,' '),nic_pat) FROM patient");
            while (rs.next()) {
                //get string from db,whichever way 
                list.add(new String(rs.getString(1)));
            }

        } catch (Exception ex) {
            System.err.println("Error"+ex);
        }
   }
 
 
 /*****************************************************************************************************************/   
    @Override
    public void initialize(URL url, ResourceBundle rb) {
     loadData();
     combobox.setItems(list);
 }
/******************************************************************************************************************/
    @FXML
    private void ComboboxSelect(MouseEvent event) {
       
        String id=combobox.getSelectionModel().getSelectedItem();
       String red="SELECT * from patient where concat(concat(nom_pat,' '),concat(prenom_pat,' '),nic_pat)='" +id+"'";
    try{
               Connection conn=Connexion.ConnecrDB();
               PreparedStatement preparedSt=conn.prepareStatement(red);
               ResultSet result=preparedSt.executeQuery();
               
               if(result.next())
               { 
                   firstName_box.setText(result.getString("prenom_pat"));
                   familyName_box.setText(result.getString("nom_pat"));
               
               }
                   
   }
   catch(Exception e){
       
   }
    }  
        
 /******************************************************************************************************************/   
}
