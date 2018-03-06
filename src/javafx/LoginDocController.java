/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafx;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author User
 */
public class LoginDocController implements Initializable {
    
    @FXML
public void exitApplication(ActionEvent event) {
   Platform.exit();
}
    
   @FXML
    public void doctorPortal (ActionEvent event) throws IOException {
        Parent loginDoctor = FXMLLoader.load(getClass().getResource("DoctorPortal.fxml"));
           Scene doc = new Scene(loginDoctor);
            Stage window1;
          window1 = (Stage)((Node)event.getSource()).getScene().getWindow();
            window1.setScene(doc);
            window1.show(); }      
      
         @FXML
    public void changeScreen8(ActionEvent event) throws IOException {
        Parent loginAdmin = FXMLLoader.load(getClass().getResource("Accueil.fxml"));
           Scene ab = new Scene(loginAdmin);
            Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
            window.setScene(ab);
            window.show(); }
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
