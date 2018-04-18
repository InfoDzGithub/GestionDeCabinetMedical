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
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author User
 */
public class LoginAdminController implements Initializable {
     @FXML
    private JFXTextField username_box;
    
    @FXML
    private JFXPasswordField password_box;
    
     
     
    
    Connection conn=null;
    ResultSet resultat=null;
    PreparedStatement preparedSt=null;
    
  
    @FXML
public void exitApplication(ActionEvent event) {
   Platform.exit();
}
  public LoginAdminController(){
       
       conn=Connexion.ConnecrDB();
       }   
    @FXML
    public void changeScreen4(ActionEvent event) throws IOException {
         String user= username_box.getText().toString();
        String pass= password_box.getText().toString();
         
         if(user.isEmpty() || pass.isEmpty())  
     {
      infoBox2("Please enter your username or your password", null, "Form Error!");   
         
     }
         else{
        String sql="SELECT * FROM admin WHERE username= " + "'" + user + "'" 
            + " AND password= " + "'" + pass + "'";
              
     
                   try {
                     resultat=conn.prepareStatement(sql).executeQuery();

                       if(resultat.next())
                       {
                            infoBox("Login Successful", null, "Success"); 
                         Parent home_page_parent2 = FXMLLoader.load(getClass().getResource("AdminPortal.fxml"));
                          Scene home_page_scene2 = new Scene(home_page_parent2);
                          Stage app_stage2 = (Stage) ((Node) event.getSource()).getScene().getWindow();
                          app_stage2.setScene(home_page_scene2);
                          app_stage2.show();

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
        
         } }  
     @FXML
    public void changeScreen5(ActionEvent event) throws IOException {
        Parent loginAdmin = FXMLLoader.load(getClass().getResource("Accueil.fxml"));
           Scene ab = new Scene(loginAdmin);
            Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
            window.setScene(ab);
            window.show(); }
    
    
     public static void infoBox(String infoMsg,String headerText,String title)         
       {
           Alert alert=new Alert(Alert.AlertType.INFORMATION);
           alert.setContentText(infoMsg);
           alert.setTitle(title);
           alert.setHeaderText(headerText);
           alert.showAndWait();
           
       }    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
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
