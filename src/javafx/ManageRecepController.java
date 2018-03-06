/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafx;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;
import static javafx.LoginAdminController.infoBox;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;

import javafx.scene.control.SplitMenuButton;

import javafx.stage.Stage;





public class ManageRecepController implements Initializable {
    
        
    /*Connection conn=null;
    ResultSet resultat=null;
    PreparedStatement preparedSt=null;*/
    @FXML
    private JFXTextField firstName_box;
    @FXML
    private JFXTextField id_box;
    @FXML
    private JFXTextField familyName_box;
    @FXML
    private JFXTextField address_box;
    @FXML
    private JFXTextField phoneNumber_box;
    @FXML
    private JFXTextField username_box;
    @FXML
    private JFXTextField password_box;
    @FXML
    private SplitMenuButton gender_box;
   
  private Connexion connexion;
    
 
//     public ManageRecepController(){
//       
//       conn=Connexion.ConnecrDB();
//       }   
    
      
    @Override
    public void initialize(URL url, ResourceBundle rb) {
     
     connexion=new Connexion();
    } 
    @FXML
    public void insertData(ActionEvent event) throws IOException {
        String firstName= firstName_box.getText();
        String familyName= familyName_box.getText();
        String address= address_box.getText();
        String phoneNumber= phoneNumber_box.getText();
        String id= id_box.getText();
        //String gender=gender_box.getItems().toString();
        String user= username_box.getText();
        String pass= password_box.getText();
         
         
       if(id.isEmpty()|| firstName.isEmpty()|| familyName.isEmpty()|| address.isEmpty()
           || phoneNumber.isEmpty()|| user.isEmpty() || pass.isEmpty())  
     {
      infoBox2("Filling the gups,please", null, "Form Error!");   
         
     }
       
          
  String sql="INSERT INTO receptioniste(num_recep,nom_recep,prenom_recep,adresse_recep,num_tel_recep,username_recep,password_recep) VALUES(?,?,?,?,?,?,?)";
       Connection conn;
                   try {
                  conn=Connexion.ConnecrDB();
                  PreparedStatement preparedSt=conn.prepareStatement(sql);
                  preparedSt.setString(1, id);
                  preparedSt.setString(2, familyName);
                  preparedSt.setString(3, firstName);
                  preparedSt.setString(4, address);
                  preparedSt.setString(5, phoneNumber);
                  preparedSt.setString(6, user);
                  preparedSt.setString(7, pass);
                  preparedSt.execute();
                  infoBox("Add 1 row", null, "Succes");
                  
                    } 
                   catch (Exception e)
                        {
                            
                        }   
        
         }  
    
 @FXML
    public void Home(ActionEvent event) throws IOException {
        Parent loginAdmin = FXMLLoader.load(getClass().getResource("AdminPortal.fxml"));
           Scene ab = new Scene(loginAdmin);
            Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
            window.setScene(ab);
            window.show(); } 
@FXML
    public void Sign_Out(ActionEvent event) throws IOException {
        Parent loginAdmin = FXMLLoader.load(getClass().getResource("LoginAdmin.fxml"));
           Scene ab = new Scene(loginAdmin);
            Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
            window.setScene(ab);
            window.show(); } 
    
  
    public static void infoBox(String infoMsg,String headerText,String title)         
       {
           Alert alert=new Alert(Alert.AlertType.CONFIRMATION);
           alert.setContentText(infoMsg);
           alert.setTitle(title);
           alert.setHeaderText(headerText);
           alert.showAndWait();
           
       }    
    
     public static void infoBox2(String infoMsg,String headerText,String title)         
       {
           Alert alert=new Alert(Alert.AlertType.ERROR);
           alert.setContentText(infoMsg);
           alert.setTitle(title);
           alert.setHeaderText(headerText);
           alert.showAndWait();
           
       }    
}