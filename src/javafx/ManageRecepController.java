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
import java.sql.SQLException;
import java.util.ResourceBundle;
import static javafx.LoginAdminController.infoBox;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;

import javafx.scene.control.SplitMenuButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import javafx.stage.Stage;





public class ManageRecepController implements Initializable {

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
    @FXML
    private TableView<Receptioniste> tableView;
    @FXML
    private TableColumn<Receptioniste,Integer> idColumn;
    @FXML
    private TableColumn<Receptioniste,String> firstNameColumn;
    @FXML
    private TableColumn<Receptioniste, String> familyNameColumn;
    @FXML
    private TableColumn<Receptioniste, String> phoneNumberColumn;
    @FXML
    private TableColumn<Receptioniste, String> userNameColumn;
    @FXML
    private TableColumn<Receptioniste, String> passwordColumn;
    @FXML
    private TableColumn<Receptioniste, String> addressColumn;
    
    ObservableList<Receptioniste>data = FXCollections.observableArrayList();
 
//     public ManageRecepController(){
//       
//       conn=Connexion.ConnecrDB();
//       }   
    
      
    @Override
    public void initialize(URL url, ResourceBundle rb) {
     
     connexion=new Connexion();
   
      
    
    } 
    @FXML
    public void cancel(ActionEvent event) throws IOException
    {
       
                  id_box.clear();
                  username_box.clear();
                   password_box.clear();
                   firstName_box.clear();
                   familyName_box.clear();
                   address_box.clear();
                   phoneNumber_box.clear();
    }
    @FXML
    public void insertData(ActionEvent event) throws IOException {
        String firstName= firstName_box.getText();
        String familyName= familyName_box.getText();
        String address= address_box.getText();
        String phoneNumber= phoneNumber_box.getText();
        String id= id_box.getText();
        String gender=gender_box.getText();
        String user= username_box.getText();
        String pass= password_box.getText();
         
         
       if(id.isEmpty()|| firstName.isEmpty()|| familyName.isEmpty()|| address.isEmpty()
           || phoneNumber.isEmpty()|| user.isEmpty() || pass.isEmpty())  
     {
      infoBox2("Please Fill out The Form", null, "Form Error!");   
         
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
                  infoBox("Receptionist Add Successfully", null, "Succes");
                  id_box.clear();
                  username_box.clear();
                   password_box.clear();
                   firstName_box.clear();
                   familyName_box.clear();
                   address_box.clear();
                   phoneNumber_box.clear();
                  
                    } 
                   catch (Exception e)
                        {
                            
                        }   
        
         }  
    //
  @FXML
public void loadData(ActionEvent event) throws IOException, SQLException
{
  try {
             Connection con=Connexion.ConnecrDB();
           // data = FXCollections.observableArrayList();
            // Execute query and store result in a resultset
            ResultSet rs = con.createStatement().executeQuery("SELECT * FROM receptioniste");
            while (rs.next()) {
                //get string from db,whichever way 
                data.add(new Receptioniste(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7) ));
            }

        } catch (SQLException ex) {
            System.err.println("Error"+ex);
        }
   
       idColumn.setCellValueFactory(new PropertyValueFactory<Receptioniste, Integer>("ID"));
     firstNameColumn.setCellValueFactory(new PropertyValueFactory<Receptioniste, String>("first_name"));
     familyNameColumn.setCellValueFactory(new PropertyValueFactory<Receptioniste, String>("family_name"));
     phoneNumberColumn.setCellValueFactory(new PropertyValueFactory<Receptioniste, String>("Phone_Number"));
     userNameColumn.setCellValueFactory(new PropertyValueFactory<Receptioniste, String>("User_Name"));
     passwordColumn.setCellValueFactory(new PropertyValueFactory<Receptioniste, String>("Password"));
     addressColumn.setCellValueFactory(new PropertyValueFactory<Receptioniste, String>("Address"));
     
     tableView.setItems(data);
  
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