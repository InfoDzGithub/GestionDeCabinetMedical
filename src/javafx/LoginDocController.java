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
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author User
 */
public class LoginDocController implements Initializable {

    @FXML
    private JFXTextField username_box;
    @FXML
    private JFXPasswordField password_box;
    public static String user;
    
     Connection conn=null;
    ResultSet resultat=null;
    PreparedStatement preparedSt=null;
   /**************************************************************************************************************/  
     public LoginDocController(){
       
       conn=Connexion.ConnecrDB();
       }  
   
    
  /*****************************************************************************************************************/  
    @FXML
public void exitApplication(ActionEvent event) {
   Platform.exit();
}
/*********************************************************************************************************************/    
   @FXML
    public void doctorPortal (ActionEvent event) throws IOException {
          user= username_box.getText().toString();
        String pass= password_box.getText().toString();
         
       if(user.isEmpty() || pass.isEmpty())  
     {
      infoBox2("Please enter your username or your password", null, "Form Error!");   
         
     }
         else{
        String sql="SELECT * FROM medecin WHERE username_med= " + "'" + user + "'" 
            + " AND password_med= " + "'" + pass + "'";
              
     
                   try {
                     resultat=conn.prepareStatement(sql).executeQuery();

                       if(resultat.next())
                       {
                            infoBox("Login Succuful", null, "Succes"); 
                            Parent loginDoctor = FXMLLoader.load(getClass().getResource("DoctorPortal.fxml"));
                            Scene doc = new Scene(loginDoctor);
                            Stage window1;
                            window1 = (Stage)((Node)event.getSource()).getScene().getWindow();
                            window1.setScene(doc);
                            window1.show();

                       }
                       else
                       {
                        infoBox2("Correct your username or your password", null, "Failed");
                        username_box.clear();
                        password_box.clear();
                         
                       }
                        } 
                   catch (Exception e)
                        {   
                            
                        }   
        
         }
        }      
 /********************************************************************************************************************/     
         @FXML
    public void changeScreen8(ActionEvent event) throws IOException {
        Parent loginAdmin = FXMLLoader.load(getClass().getResource("Accueil.fxml"));
           Scene ab = new Scene(loginAdmin);
            Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
            window.setScene(ab);
            window.show(); }
    
 /******************************************************************************************************************/  
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
  /*****************************************************************************************************************/
 public static void infoBox(String infoMsg,String headerText,String title)         
       {
           Alert alert=new Alert(Alert.AlertType.INFORMATION);
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
