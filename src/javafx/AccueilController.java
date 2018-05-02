
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
import javafx.scene.control.Label;
import javafx.stage.Stage;


public class AccueilController implements Initializable {
    
    @FXML
    private Label label;
    
 /********************************************************************************************************************/   
@FXML
public void exitApplication(ActionEvent event) {
   Platform.exit();
}
/**********************************************************************************************************************/    
    @FXML
  
    public void changeScreen(ActionEvent event) throws IOException {
        Parent loginAdmin = FXMLLoader.load(getClass().getResource("LoginAdmin.fxml"));
           Scene ab = new Scene(loginAdmin);
            Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
            window.setScene(ab);
            window.show(); } 
 /*********************************************************************************************************************/
    @FXML
    
     public void changeScreen2(ActionEvent event) throws IOException {
        Parent loginAdmin =  FXMLLoader.load(getClass().getResource("LoginRecep.fxml"));
           Scene ab = new Scene(loginAdmin);
            Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
            window.setScene(ab);
            window.show(); }
 /**********************************************************************************************************************/
     @FXML
     
     public void changeScreen3(ActionEvent event) throws IOException {
        Parent loginAdmin =  FXMLLoader.load(getClass().getResource("LoginDoc.fxml"));
           Scene ab = new Scene(loginAdmin);
            Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
            window.setScene(ab);
            window.show(); }  
     
 /**********************************************************************************************************************/    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }    
/***********************************************************************************************************************/   
}
