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
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.ResourceBundle;
import static javafx.LoginAdminController.infoBox;
import static javafx.ManageAppointmentController.infoBox;
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
import static javax.swing.UIManager.getString;

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
         
            ResultSet rs = con.createStatement().executeQuery("SELECT info_P FROM rdv where date_rdv='" +ManagePatientController.currentDay()+"' ");//
            while (rs.next()) {
                //get string from db,whichever way 
                list2.add(new String(rs.getString(1)));
            }

        } catch (Exception ex) {
            System.err.println("Error"+ex);
        }
   }
 /****************************************************************************************************************/
     public int age_patient()
     {
        String id= search_box.getText();
         int age=0;String date_naiss="";
         String req="Select dateN_pat from patient where id_pat='" +id+"' ";
          try{
                            Connection conn=Connexion.ConnecrDB();
                            PreparedStatement preparedSt=conn.prepareStatement(req);
                            ResultSet result=preparedSt.executeQuery();

                            if(result.next())    date_naiss=result.getString(1);
                             
             }
          catch(Exception e){}  
        
          char c=date_naiss.charAt(4);
          String mano = date_naiss.substring(0,4); 
        int year = Integer.parseInt(mano); 
         return age=2018-year;
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
    // ManageAppointmentController. infoBox(age_patient()+"", null, "Success");
     
    }  

  /***************************************************************************************************************/
       
    
}
