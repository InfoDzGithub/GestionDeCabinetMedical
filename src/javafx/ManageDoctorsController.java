/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafx;


import com.jfoenix.controls.JFXTextField;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import static java.util.Collections.list;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleStringProperty;
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

/**
 * FXML Controller class
 *
 * @author User
 */
public class ManageDoctorsController implements Initializable {

    @FXML
    private JFXTextField cni_box;
    @FXML
    private JFXTextField firstName_box;
    @FXML
    private JFXTextField familyName_box;
    @FXML
    private JFXTextField phoneNumber_box;
   
    @FXML
    private JFXTextField password_box;
    @FXML
    private JFXTextField userName_box;
    @FXML
    private JFXTextField address_box;
    
     @FXML
    private TableColumn<Doc,Integer> id_col;
    @FXML
    private TableColumn<Doc, Integer> cni_col;
    @FXML
    private TableColumn<Doc, String> firstName_col;
    @FXML
    private TableColumn<Doc, String> familyName_col;
    @FXML
    private TableColumn<Doc, String> phoneNumber_col;
   
    @FXML
    private TableColumn<Doc, String> password_col;
    @FXML
    private TableColumn<Doc, String> userName_col;
    @FXML
    private TableColumn<Doc, String> address_col;
    
     private Connexion db;
    ObservableList<Doc> list = FXCollections.observableArrayList();
    @FXML
    private TableView<Doc> tab;
    
   
     @Override
    public void initialize(URL url, ResourceBundle rb) {
       
         db = new Connexion();
         loadData();

    } 
    
    //ADD data
    @FXML
    public void insertData(ActionEvent event) throws IOException {
        
        String firstName= firstName_box.getText();
        String familyName= familyName_box.getText();
        String address= address_box.getText();
        String phoneNumber= phoneNumber_box.getText();
        String cni= cni_box.getText();
        //String gender=gender_box.getTypeSelector();
        String user= userName_box.getText();
        String pass= password_box.getText();
       
      if(cni.isEmpty()|| firstName.isEmpty()|| familyName.isEmpty()|| address.isEmpty()
           || phoneNumber.isEmpty()|| user.isEmpty() || pass.isEmpty())  
     {
      infoBox2("Please Fill Out The Form ", null, "Form Error!");   
         
     }
       
          
  String sql="INSERT INTO medecin(num_cni,nom_med,prenom_med,adress_med,num_tel_med,username_med,password_med) VALUES(?,?,?,?,?,?,?)";
       Connection conn;
                   try {
                  conn=Connexion.ConnecrDB();
                  PreparedStatement preparedSt=conn.prepareStatement(sql);
                 
                  preparedSt.setString(1, cni);
                  preparedSt.setString(2, familyName);
                  preparedSt.setString(3, firstName);
                  preparedSt.setString(4, address);
                  preparedSt.setString(5, phoneNumber);
                  preparedSt.setString(6, user);
                  preparedSt.setString(7, pass);
                  preparedSt.execute();
                  infoBox("Doctor Added Successfully", null, "Success");
                 
                   cni_box.clear();
                   firstName_box.clear();
                   familyName_box.clear();
                   address_box.clear();
                   phoneNumber_box.clear();
                   userName_box.clear();
                   password_box.clear();
                    } 
                   catch (Exception e)
                        {
                            
                        }   
        
         }
   
    
    
    
    
    //Loat Data
    @FXML
    public void loadData(){
       list.clear();
         try {
             Connection con=Connexion.ConnecrDB();
           
            ResultSet rs = con.createStatement().executeQuery("SELECT * FROM medecin");
            while (rs.next()) {
                //get string from db,whichever way 
                list.add(new Doc(rs.getString(1),rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7), rs.getString(8) ));
            }

        } catch (SQLException ex) {
            System.err.println("Error"+ex);
        }
   
        id_col.setCellValueFactory(new PropertyValueFactory<Doc,Integer>("ID"));
    cni_col.setCellValueFactory(new PropertyValueFactory<Doc,Integer>("CNI"));
      familyName_col.setCellValueFactory(new PropertyValueFactory<Doc,String>("nom"));
        firstName_col.setCellValueFactory(new PropertyValueFactory<Doc,String>("prenom"));
         // gender_col.setCellValueFactory(new PropertyValueFactory<>("sexe"));
          address_col.setCellValueFactory(new PropertyValueFactory<Doc,String>("adresse"));
      phoneNumber_col.setCellValueFactory(new PropertyValueFactory<Doc,String>("ntel"));
        userName_col.setCellValueFactory(new PropertyValueFactory<Doc,String>("username"));
          password_col.setCellValueFactory(new PropertyValueFactory<Doc,String>("password"));
     
     tab.setItems(list);
  
}
    
    //Class Doctor
       public static class Doc {
    
     private final SimpleStringProperty  ID,CNI, nom, prenom, adresse, ntel, username, password;
     //private final SimpleStringProperty sexe;
   
    //constructeur
    public Doc(String ID,String CNI,String nom,String prenom,String adresse,String ntel,String username,String password)
    {
      this.ID = new SimpleStringProperty(ID);
      this.CNI = new SimpleStringProperty(CNI);
        this.nom = new SimpleStringProperty(nom);
        this.prenom = new SimpleStringProperty(prenom);
       // this.sexe= new SimpleStringProperty(sexe);
         this.adresse= new SimpleStringProperty(adresse);
          this.ntel= new SimpleStringProperty(ntel);
           this.username= new SimpleStringProperty(username);
            this.password= new SimpleStringProperty(password);
    }
    
     public String getID() {
            return ID.get();
        }

   
         public String getCNI() {
            return CNI.get();
        }

        public  String  getNom() {
            return nom.get();
        }

        public  String  getPrenom() {
            return prenom.get();
        }

//        public  String  getSexe() {
//            return sexe.get();
//        }

        public  String  getAdresse() {
            return adresse.get();
        }

        public  String  getNtel() {
            return ntel.get();
        }

        public  String  getUsername() {
            return username.get();
        }

        public  String  getPassword() {
            return password.get();
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
