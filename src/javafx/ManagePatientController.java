/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafx;

import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextField;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;


public class ManagePatientController implements Initializable {
    
    @FXML
    private JFXTextField cni_box;
    @FXML
    private JFXTextField firstName_box;
    @FXML
    private JFXTextField familyName_box;
    @FXML
    private JFXTextField phoneNumber_box;
   
    @FXML
    private JFXTextField address_box;
     @FXML
    private JFXRadioButton rdb_male;
     @FXML
    private JFXRadioButton rdb_female;
     
    @FXML
    private JFXRadioButton rdb_signal;
    @FXML
    private JFXRadioButton rdb_married;
     @FXML
    private JFXTextField age_box; 
    @FXML
     private ToggleGroup GroupS1;
    @FXML
     private ToggleGroup GroupS;
   
    @FXML
    private TableColumn<Pat,Integer> id_col;
    @FXML
    private TableColumn<Pat, Integer> cni_col;
    @FXML
    private TableColumn<Pat, String> firstName_col;
    @FXML
    private TableColumn<Pat, String> familyName_col;
    @FXML
    private TableColumn<Pat, String> phoneNumber_col;
    @FXML
    private TableColumn<Pat, String> gender_col;
    @FXML
    private TableColumn<Pat, Integer> age_col;
    @FXML
    private TableColumn<Pat, String> situationF_col;
    @FXML
    private TableColumn<Pat, String> address_col;
    
     private Connexion db;
    ObservableList<Pat> list = FXCollections.observableArrayList();
    @FXML
    private TableView<Pat> tab;
   
    
    /**********************************************************************************************************/
     @FXML
    public void Home(ActionEvent event) throws IOException {
        Parent loginAdmin = FXMLLoader.load(getClass().getResource("RecepPortal.fxml"));
           Scene ab = new Scene(loginAdmin);
            Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
            window.setScene(ab);
            window.show(); } 
    /************************************************************************************************************/
@FXML
    public void Sign_Out(ActionEvent event) throws IOException {
        Parent loginAdmin = FXMLLoader.load(getClass().getResource("LoginRecep.fxml"));
           Scene ab = new Scene(loginAdmin);
            Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
            window.setScene(ab);
            window.show(); } 

   /*************************************************************************************************************/
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       db = new Connexion();
         loadData();
    } 
    /***********************************************************************************************************/
    @FXML
    public void insertData(ActionEvent event) throws IOException {
        
        String firstName= firstName_box.getText();
        String familyName= familyName_box.getText();
        String address= address_box.getText();
        String phoneNumber= phoneNumber_box.getText();
        String cni= cni_box.getText();
        String age=age_box.getText();
        String gender="";
         
        if(rdb_male.isSelected())
            gender=rdb_male.getText();
        else if(rdb_female.isSelected())
            gender=rdb_female.getText();
       String sitFam="";
         
        if(rdb_signal.isSelected())
            sitFam=rdb_signal.getText();
        else if(rdb_married.isSelected())
            gender=rdb_married.getText();
      if(cni.isEmpty()|| firstName.isEmpty()|| familyName.isEmpty()|| address.isEmpty()
          || gender.isEmpty() || phoneNumber.isEmpty()|| age.isEmpty() || sitFam.isEmpty())  
     {
      infoBox2("Please Fill Out The Form ", null, "Form Error!");   
         
     }
       
          
  String sql="INSERT INTO patient(nic_pat,nom_pat,prenom_pat,sexe_pat,age_pat,adresse_pat,num_tel_pat,situation_fam) VALUES(?,?,?,?,?,?,?,?)";
       Connection conn;
                   try {
                  conn=Connexion.ConnecrDB();
                  PreparedStatement preparedSt=conn.prepareStatement(sql);
                 
                  preparedSt.setString(1, cni);
                  preparedSt.setString(2, familyName);
                  preparedSt.setString(3, firstName);
                   preparedSt.setString(4, gender);
                    preparedSt.setString(5, age);
                  preparedSt.setString(6, address);
                  preparedSt.setString(7, phoneNumber);
                  preparedSt.setString(8,sitFam);
                  
                  preparedSt.execute();
                  infoBox("Patient Added Successfully", null, "Success");
                   loadData();
                 
                   cni_box.clear();
                   firstName_box.clear();
                   familyName_box.clear();
                   address_box.clear();
                   phoneNumber_box.clear();
                   age_box.clear();
                   rdb_male.setSelected(true);
                   rdb_signal.setSelected(true);
                   
                    } 
                   catch (Exception e)
                        {
                            
                        }   
        
         }
    /**********************************************************************************************************/
    public void loadData(){
       list.clear();
         try {
             Connection con=Connexion.ConnecrDB();
           
            ResultSet rs = con.createStatement().executeQuery("SELECT * FROM patient");
            while (rs.next()) {
                //get string from db,whichever way 
                list.add(new Pat(rs.getString(1),rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7), rs.getString(8),rs.getString(9) ));
            }

        } catch (SQLException ex) {
            System.err.println("Error"+ex);
        }
   
        id_col.setCellValueFactory(new PropertyValueFactory<Pat,Integer>("ID"));
    cni_col.setCellValueFactory(new PropertyValueFactory<Pat,Integer>("CNI"));
      familyName_col.setCellValueFactory(new PropertyValueFactory<Pat,String>("nom"));
        firstName_col.setCellValueFactory(new PropertyValueFactory<Pat,String>("prenom"));
         gender_col.setCellValueFactory(new PropertyValueFactory<>("sexe"));
          address_col.setCellValueFactory(new PropertyValueFactory<Pat,String>("adresse"));
      phoneNumber_col.setCellValueFactory(new PropertyValueFactory<Pat,String>("ntel"));
        age_col.setCellValueFactory(new PropertyValueFactory<Pat,Integer>("age"));
          situationF_col.setCellValueFactory(new PropertyValueFactory<Pat,String>("sitF"));
     
     tab.setItems(list);
  
}
    /*********************************************************************************************************/
    @FXML
    private void TableMouseClick(MouseEvent event) {
        int myIndex =tab.getSelectionModel().getSelectedIndex();
   String id=tab.getItems().get(myIndex).getID();
   String sqls="Select * from patient where id_pat ='" +id+"'";
   try{
               Connection conn=Connexion.ConnecrDB();
               PreparedStatement preparedSt=conn.prepareStatement(sqls);
               ResultSet result=preparedSt.executeQuery();
               
               if(result.next())
               { cni_box.setText(result.getString("nic_pat"));
                   firstName_box.setText(result.getString("prenom_pat"));
                   familyName_box.setText(result.getString("nom_pat"));
                   address_box.setText(result.getString("adresse_pat"));
                   phoneNumber_box.setText(result.getString("num_tel_pat"));
                   age_box.setText(result.getString("age_pat"));
                  
                   
                  /* if(result.getString("sexe_med").equals(rdb_male.selectedProperty()))
                   rdb_male.setText(result.getString("sexe_med"));
                   else  if(result.getString("sexe_med").equals(rdb_female.selectedProperty()))
                   rdb_female.setText(result.getString("sexe_med"));*/
                 
               }
                   
   }
   catch(Exception e){
       
   }
    }
   
  /**********************************************************************************************************/ 
     @FXML
    private void UpdateData(ActionEvent event)  {
        int myIndex =tab.getSelectionModel().getSelectedIndex();
         String id=tab.getItems().get(myIndex).getID();
         String req="Update patient set nic_pat = ? , prenom_pat = ? , nom_pat = ? , adresse_pat = ? ,num_tel_pat = ? , age_pat = ? , situation_fam = ? ,sexe_pat = ?  where id_pat ='" +id+"'";
          Connection conn=Connexion.ConnecrDB();
         try{
          PreparedStatement preparedSt=conn.prepareStatement(req);
          
                  preparedSt.setString(1, cni_box.getText().toString());
                  preparedSt.setString(2,firstName_box.getText().toString() );
                  preparedSt.setString(3, familyName_box.getText().toString());
                  preparedSt.setString(4,address_box.getText().toString());
                  preparedSt.setString(5, phoneNumber_box.getText().toString());
                  preparedSt.setString(6, age_box.getText().toString());
                 
                  if(rdb_signal.isSelected())
                   preparedSt.setString(7, rdb_signal.getText().toString());
                  else if(rdb_married.isSelected())
                  preparedSt.setString(7, rdb_married.getText().toString());
                  
                   if(rdb_male.isSelected())
                   preparedSt.setString(8, rdb_male.getText().toString());
                   else if(rdb_female.isSelected())
                   preparedSt.setString(8, rdb_female.getText().toString());
                
                  preparedSt.execute();
                   infoBox("Patient Modify Successfully", null, "Success");
                 
                  loadData();
                  cni_box.clear();
                   firstName_box.clear();
                   familyName_box.clear();
                   address_box.clear();
                   phoneNumber_box.clear();
                   age_box.clear();
                  
                   rdb_male.setSelected(true);
                    rdb_signal.setSelected(true);
                   
         }
         catch(Exception e)
         {
             
         }
    
    } 
  /***********************************************************************************************************/
     @FXML
    private void DeleteData(ActionEvent event) {
        
        
       int myIndex=tab.getSelectionModel().getSelectedIndex();
        String id=tab.getItems().get(myIndex).getID();
        
//       if(myIndex == -1)
//        {
//          infoBox2("You shoud select ligne", null, "Failed");  
//        }
        String requette="Delete from patient where id_pat ='" +id+"'";
         try{
               Connection conn=Connexion.ConnecrDB();
               PreparedStatement preparedSt=conn.prepareStatement(requette);
               preparedSt.execute();
               infoBox("Patient Deletted Successfully", null, "Success");
               
               loadData();
                cni_box.clear();
                   firstName_box.clear();
                   familyName_box.clear();
                   address_box.clear();
                   phoneNumber_box.clear();
                   age_box.clear();
                   rdb_signal.setSelected(true);
                   rdb_male.setSelected(true);
               
         }
         catch(Exception e)
         {
             
         }
        
        
    }
    
 /************************************************************************************************************/
    @FXML
    public void CancelData(ActionEvent event) throws IOException
    {
       infoBox("Doctor Canceled Successfully", null, "Success");
                  cni_box.clear();
                  age_box.clear();
                  rdb_male.setSelected(true);
                   rdb_signal.setSelected(true);
                   firstName_box.clear();
                   familyName_box.clear();
                   address_box.clear();
                   phoneNumber_box.clear();
    }

    
    /************************************************************************************************************/
   public static class Pat{
    
     private final SimpleStringProperty  ID,CNI, nom, prenom,sexe,age, adresse, ntel, sitF;
    //constructeur
    public Pat(String ID,String CNI,String nom,String prenom,String sexe,String age,String adresse,String ntel,String sitF)
    {
      this.ID = new SimpleStringProperty(ID);
      this.CNI = new SimpleStringProperty(CNI);
        this.nom = new SimpleStringProperty(nom);
        this.prenom = new SimpleStringProperty(prenom);
       this.sexe= new SimpleStringProperty(sexe);
        this.age= new SimpleStringProperty(age);
         this.adresse= new SimpleStringProperty(adresse);
          this.ntel= new SimpleStringProperty(ntel);
           this.sitF= new SimpleStringProperty(sitF);
            
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

       public  String  getSexe() {
            return sexe.get();
       }

        public  String  getAdresse() {
            return adresse.get();
        }

        public  String  getNtel() {
            return ntel.get();
        }

        public  String  getSitF() {
            return sitF.get();
        }

        
         public  String  getAge() {
            return age.get();
        }
        }
    
  /**************************************************************************************************************/  
    
  public static void infoBox(String infoMsg,String headerText,String title)         
       {
           Alert alert=new Alert(Alert.AlertType.CONFIRMATION);
           alert.setContentText(infoMsg);
           alert.setTitle(title);
           alert.setHeaderText(headerText);
           alert.showAndWait();
           
       }    
 /**************************************************************************************************************/   
     public static void infoBox2(String infoMsg,String headerText,String title)         
       {
           Alert alert=new Alert(Alert.AlertType.ERROR);
           alert.setContentText(infoMsg);
           alert.setTitle(title);
           alert.setHeaderText(headerText);
           alert.showAndWait();
           
       }    
    
    
 /******************************************************************************************************************/   
}
