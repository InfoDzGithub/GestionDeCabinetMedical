/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafx;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;
import javafx.application.Platform;
import static javafx.collections.FXCollections.concat;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.print.PrinterJob;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javax.swing.JOptionPane;

public class PrescriptionController implements Initializable {
  
    @FXML
    private Text date_box;
    @FXML
    private  Text age_box;
    @FXML
    private Text familyName_box;
    @FXML
    private Text firstName_box;
    @FXML
    private Label trait_box;
    @FXML
    private AnchorPane panel_box;
     private Connexion db;
    private Text FirstName_box;
    @FXML
    private Text eMail_box;
    @FXML
    private Text firstNmed_box;
    @FXML
    private Text familyNmed_box;
    @FXML
    private Text phoneN_box;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
           db = new Connexion();
           String id= DoctorPortalController.id;
           String usernameMed=LoginDocController.user;
           
           
        String req="Select nom_med,prenom_med,num_tel_med ,username_med from medecin where username_med='"+usernameMed+"'";
        try{
             Connection conn1=Connexion.ConnecrDB();
               PreparedStatement preparedSt1=conn1.prepareStatement(req);
               ResultSet result2=preparedSt1.executeQuery();
                while(result2.next())
               {
                  firstNmed_box.setText(result2.getString("nom_med").concat(" " .concat(result2.getString("prenom_med"))));
                 // familyNmed_box.setText(result2.getString("nom_med"));
                 eMail_box.setText(result2.getString("prenom_med").concat("_").concat(result2.getString("nom_med")).concat("@gmail.com"));
                  phoneN_box.setText(result2.getString("num_tel_med"));
               
        }}
         catch(Exception e){}
        
     
         age_box.setText(DoctorPortalController.ageP(id));
        String sqls="Select  nom_pat ,prenom_pat,nom_medc from patient, traitement where patient.id_pat= traitement.id_pat  and patient.id_pat='" +id+"'";
                                                                                              
   try{
               Connection conn=Connexion.ConnecrDB();
               PreparedStatement preparedSt=conn.prepareStatement(sqls);
               ResultSet result=preparedSt.executeQuery(); 
               
               
               while(result.next())
               { 
                  // age_box.setText(result.getString("age"));
                   familyName_box.setText(result.getString("nom_pat"));
                   firstName_box.setText(result.getString("prenom_pat"));
                   trait_box.setText(result.getString("nom_medc"));
                   
               }     
               }
   catch(Exception e){}
         date_box.setText( ManagePatientController.currentDay());
    }    
/******************************************************************************************************************/

         @FXML
    private void PrInt(ActionEvent event) {
        
        //Second Method
        if (doPrint(panel_box)) {
            System.out.println("Print");
        } else {
            System.out.println("error");
        }
    }
@FXML
public void exitApplication(ActionEvent event) {
   Platform.exit();
}
   
                                          
/****************************************************************************************************************/
    boolean doPrint(Node printPane) {
        PrinterJob job = PrinterJob.createPrinterJob();
        if (job == null) {
            System.out.println("1");
            return false;
        }
        if (!job.showPrintDialog(null)) {
            System.out.println("2");
            return false;
        }
        if (!job.printPage(printPane)) {
            System.out.println("3");
            return false;
        }
        return job.endJob();

    }
  
    @FXML
    private void buttonBuck(ActionEvent event) throws IOException {
         Parent loginAdmin = FXMLLoader.load(getClass().getResource("DoctorPortal.fxml"));
           Scene ab = new Scene(loginAdmin);
            Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
            window.setScene(ab);
            window.show();
    }
/*****************************************************************************************************************/  
}



 
   
   
  