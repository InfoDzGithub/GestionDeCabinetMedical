/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafx;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class AdminPortalController implements Initializable {
 /******************************************************************************************************************/
@FXML
    public void changeScreen5(ActionEvent event) throws IOException {
        Parent loginAdmin = FXMLLoader.load(getClass().getResource("ManageDoctors.fxml"));
           Scene ab = new Scene(loginAdmin);
            Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
            window.setScene(ab);
            window.show(); } 
 /*******************************************************************************************************************/
@FXML
    public void changeScreen6(ActionEvent event) throws IOException {
        Parent loginAdmin = FXMLLoader.load(getClass().getResource("ManageRecep.fxml"));
           Scene ab = new Scene(loginAdmin);
            Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
            window.setScene(ab);
            window.show(); } 
 /*******************************************************************************************************************/
    @FXML
    public void changeScreen7(ActionEvent event) throws IOException {
        Parent loginAdmin = FXMLLoader.load(getClass().getResource("LoginAdmin.fxml"));
           Scene ab = new Scene(loginAdmin);
            Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
            window.setScene(ab);
            window.show(); } 
  /*******************************************************************************************************************/

    @Override
    public void initialize(URL url, ResourceBundle rb) {
       
    }    
    
}
