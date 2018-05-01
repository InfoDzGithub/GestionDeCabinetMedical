
package javafx;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextField;
import com.sun.javafx.tk.Toolkit;
import static com.sun.javafx.tk.Toolkit.getToolkit;
import java.awt.event.KeyAdapter;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import java.util.ResourceBundle;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.SplitMenuButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javax.swing.JOptionPane;
import javax.swing.JTable;


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
    private JFXRadioButton rdb_male;
    @FXML
    private ToggleGroup myGroup;
    @FXML
    private JFXRadioButton rdb_female;
    
    
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
    private TableColumn<Doc, String> gender_col;
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
    @FXML
    private Label errortel;
    @FXML
    private Label errortel1;
    @FXML
    private Label nameError;
    KeyEvent event1;
    @FXML
    private Label nameError1;
   
 /***************************************************************************************************************/ 
   
     @Override
    public void initialize(URL url, ResourceBundle rb) {
       
         db = new Connexion();
         loadData();

    } 
/****************************************************************************************************************/   
   @FXML
    public void insertData(ActionEvent event) throws IOException {
        
        String firstName= firstName_box.getText();
        String familyName= familyName_box.getText();
        String address= address_box.getText();
        String phoneNumber= phoneNumber_box.getText();
        String cni= cni_box.getText();
        String gender="";
         
        String user= userName_box.getText();
        String pass= password_box.getText();
        if(rdb_male.isSelected())
            gender=rdb_male.getText();
        else if(rdb_female.isSelected())
            gender=rdb_female.getText();
        KeyEvent event1 = null;
         
       
      if(cni.isEmpty()|| firstName.isEmpty()|| familyName.isEmpty()|| address.isEmpty()
          || gender.isEmpty() || phoneNumber.isEmpty()|| user.isEmpty() || pass.isEmpty()  )  
     {
      infoBox2("Please Fill Out The Form ", null, "Form Error!");   
         
     }
      else if(!OnKeyPressed(event1)||!OnKeyPressed1(event1)|| !handle(event))
       {
            
      infoBox2("Please, Correct The Form ! ", null, "Form Error!");
      
         
     }
    else{  
          String req="SELECT num_cni FROM medecin WHERE num_cni='"+cni+"'";
         
          try{
               Connection conn2=Connexion.ConnecrDB();
               PreparedStatement preparedSt2=conn2.prepareStatement(req);
               ResultSet result=preparedSt2.executeQuery();
               
               if(result.next())
               { 
                 infoBox2("The Doctor already exists", null, "Alert"); 
               }
             else{
               
               
             String sql="INSERT INTO medecin(num_cni,nom_med,prenom_med,sexe_med,adress_med,num_tel_med,username_med,password_med) VALUES(?,?,?,?,?,?,?,?)";
                 Connection conn;
                             try {
                            conn=Connexion.ConnecrDB();
                            PreparedStatement preparedSt=conn.prepareStatement(sql);

                            preparedSt.setString(1, cni);
                            preparedSt.setString(2, familyName);
                            preparedSt.setString(3, firstName);
                             preparedSt.setString(4, gender);
                            preparedSt.setString(5, address);
                            preparedSt.setString(6, phoneNumber);
                            preparedSt.setString(7, user);
                            preparedSt.setString(8, pass);
                            preparedSt.execute();
                            infoBox("Doctor Added Successfully", null, "Success");

                            Statement m = conn.createStatement();
                            m.execute("set @autoid :=0");
                            m.execute("UPDATE  medecin  set id_med = @autoid := (@autoid+1)");
                            m.execute("ALTER TABLE medecin auto_increment = 1");

                             loadData();

                           CancelData(event);
                              } 
                             catch (Exception e)
                                  {

                                  }   
        
         }}
          catch (Exception e)
                        {
                            
                        }
      }
   
    }
   
    
    
    
 /******************************************************************************************************************/  
    public void loadData(){
       list.clear();
         try {
             Connection con=Connexion.ConnecrDB();
           
            ResultSet rs = con.createStatement().executeQuery("SELECT * FROM medecin");
            while (rs.next()) {
              
                list.add(new Doc(rs.getString(1),rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7), rs.getString(8),rs.getString(9) ));
            }

        } catch (SQLException ex) {
            System.err.println("Error"+ex);
        }
         
   
        id_col.setCellValueFactory(new PropertyValueFactory<Doc,Integer>("ID"));
    cni_col.setCellValueFactory(new PropertyValueFactory<Doc,Integer>("CNI"));
      familyName_col.setCellValueFactory(new PropertyValueFactory<Doc,String>("nom"));
        firstName_col.setCellValueFactory(new PropertyValueFactory<Doc,String>("prenom"));
         gender_col.setCellValueFactory(new PropertyValueFactory<>("sexe"));
          address_col.setCellValueFactory(new PropertyValueFactory<Doc,String>("adresse"));
      phoneNumber_col.setCellValueFactory(new PropertyValueFactory<Doc,String>("ntel"));
        userName_col.setCellValueFactory(new PropertyValueFactory<Doc,String>("username"));
          password_col.setCellValueFactory(new PropertyValueFactory<Doc,String>("password"));
     
     tab.setItems(list);
  
}
/**************************************************************************************************************/
 //Cette fonction permet de transformer une ligne from TableView to fields correspandant
    @FXML
    private void TableMouseClick(MouseEvent event) {
       
   int myIndex =tab.getSelectionModel().getSelectedIndex();
   String id=tab.getItems().get(myIndex).getID();
   String sqls="Select * from medecin where id_med ='" +id+"'";
   try{
               Connection conn=Connexion.ConnecrDB();
               PreparedStatement preparedSt=conn.prepareStatement(sqls);
               ResultSet result=preparedSt.executeQuery();
               
               if(result.next())
               { cni_box.setText(result.getString("num_cni"));
                   firstName_box.setText(result.getString("prenom_med"));
                   familyName_box.setText(result.getString("nom_med"));
                   address_box.setText(result.getString("adress_med"));
                   phoneNumber_box.setText(result.getString("num_tel_med"));
                   userName_box.setText(result.getString("username_med"));
                   password_box.setText(result.getString("password_med"));
                 
                   
                   if("Male".equals(result.getString("sexe_med")))
                   {rdb_male.setSelected(true);}
                   else  if("Female".equals(result.getString("sexe_med")))
                   {rdb_female.setSelected(true);}
                 
               }
                   
   }
   catch(Exception e){
       
   }
  
    }
/******************************************************************************************************************/
    @FXML
    private void UpdateData(ActionEvent event) throws SQLException {
         String firstName= firstName_box.getText();
        String familyName= familyName_box.getText();
        String address= address_box.getText();
        String phoneNumber= phoneNumber_box.getText();
        String cni= cni_box.getText();
        String gender="";
         
        String user= userName_box.getText();
        String pass= password_box.getText();
        if(rdb_male.isSelected())
            gender=rdb_male.getText();
        else if(rdb_female.isSelected())
            gender=rdb_female.getText();
     KeyEvent event1 = null;
       
      int myIndex =tab.getSelectionModel().getSelectedIndex();
         if(myIndex > -1){
              if(cni.isEmpty()|| firstName.isEmpty()|| familyName.isEmpty()|| address.isEmpty()
          || gender.isEmpty() || phoneNumber.isEmpty()|| user.isEmpty() || pass.isEmpty()  )  
     {
      infoBox2("Please Fill Out The Form ", null, "Form Error!");   
         
     }
      else if(!OnKeyPressed(event1)||!OnKeyPressed1(event1) || !handle(event))
        {
      infoBox2("Correct The Form ", null, "Form Error!");   
         
     }
      else{ String id=tab.getItems().get(myIndex).getID();
         String req="Update medecin set num_cni = ? , prenom_med = ? , nom_med = ? , adress_med = ? ,num_tel_med = ? , username_med = ? , password_med = ? ,sexe_med = ?  where id_med ='" +id+"'";
          Connection conn=Connexion.ConnecrDB();
         try{
          PreparedStatement preparedSt=conn.prepareStatement(req);
          
                  preparedSt.setString(1, cni_box.getText().toString());
                  preparedSt.setString(2,firstName_box.getText().toString() );
                  preparedSt.setString(3, familyName_box.getText().toString());
                  preparedSt.setString(4,address_box.getText().toString());
                  preparedSt.setString(5, phoneNumber_box.getText().toString());
                  preparedSt.setString(6, userName_box.getText().toString());
                  preparedSt.setString(7,  password_box.getText().toString());
                  
                  
                  if(rdb_male.isSelected())
                   preparedSt.setString(8, rdb_male.getText().toString());
                  else if(rdb_female.isSelected())
                 
                  preparedSt.setString(8, rdb_female.getText().toString());
                
                  preparedSt.execute();
                   infoBox("Doctor Modified Successfully", null, "Success");
                 
                  loadData();
                 CancelData(event);
                   
         }
         catch(Exception e)
         {
             
         }
         }}
   else
   {
       infoBox2("You Should Select a Row First", null, "Failed");
   }
}
 /**************************************************************************************************/
     @FXML
    public void CancelData(ActionEvent event) throws IOException
    {
       
                  cni_box.clear();
                  userName_box.clear();
                   password_box.clear();
                   firstName_box.clear();
                   familyName_box.clear();
                   address_box.clear();
                   phoneNumber_box.clear();
                    rdb_male.setSelected(false);
                    rdb_female.setSelected(false);
    }
/****************************************************************************************************************/
    @FXML
    private void DeleteData(ActionEvent event) throws SQLException {
        
        int myIndex =tab.getSelectionModel().getSelectedIndex();
         if(myIndex > -1)
         {
        String id=tab.getItems().get(myIndex).getID();
        String requette="Delete from medecin where id_med ='" +id+"'";
         try{
               Connection conn=Connexion.ConnecrDB();
               PreparedStatement preparedSt=conn.prepareStatement(requette);
               preparedSt.execute();
               infoBox("Doctor Deleted Successfully", null, "Success");
               
                Statement m = conn.createStatement();
                  m.execute("set @autoid :=0");
                  m.execute("UPDATE  medecin  set id_med = @autoid := (@autoid+1)");
                  m.execute("ALTER TABLE medecin auto_increment = 1");
                  
               loadData();
                CancelData(event);
                  
               
         }
         catch(Exception e){}
        
         }
   else
   {
       infoBox2("You Should Select a Row First", null, "Failed");
   }
         Connection conn=Connexion.ConnecrDB();
         Statement s = conn.createStatement();
         s.execute("ALTER TABLE medecin auto_increment = 1");
        
    }
    /***********************************************************************/
    @FXML
    public boolean handle(ActionEvent event) {
            if(firstName_box.getText().matches("[aA-zZ ]+$") || familyName_box.getText().matches("[aA-zZ ]+$")) 
             return true;
                else return false;
           }
/**********************************************************************/
    @FXML
    private boolean OnKeyPressed1(KeyEvent event) {
       
         try
       {int i=Integer.parseInt(cni_box.getText()) ;
         errortel1.setText(""); return true;}
        
         catch(NumberFormatException e)
         {
             errortel1.setText("Invalid CNI");return false;
         }
    }

    @FXML
    private void error(KeyEvent keyEvent) {
       KeyCode code = keyEvent.getCode();
    switch (code) {
        case TAB: nameError.setText(" ");break;
        case BACK_SPACE:nameError.setText(" ");break;
        case SPACE:nameError.setText(" ");
            break;
        default:
            if (!code.isLetterKey()) {
                keyEvent.consume();
                nameError.setText("Invalid form");
            } 
    }
       
    }
    /***********************************************************************************/
   
    @FXML
    private void error2(KeyEvent keyEvent) {
             KeyCode code = keyEvent.getCode();
    switch (code) {
        case TAB: nameError1.setText(" ");break;
        case BACK_SPACE:nameError1.setText(" ");break;
        case SPACE:nameError1.setText(" ");
            break;
        default:
            if (!code.isLetterKey()) {
                keyEvent.consume();
                nameError1.setText("Invalid form");
            } 
    }
    
    }
    
    

    
    
/****************************************************************************************************************/

       public static class Doc {
    
     private final SimpleStringProperty  ID,CNI, nom, prenom,sexe, adresse, ntel, username, password;
    
    public Doc(String ID,String CNI,String nom,String prenom,String sexe,String adresse,String ntel,String username,String password)
    {
      this.ID = new SimpleStringProperty(ID);
      this.CNI = new SimpleStringProperty(CNI);
        this.nom = new SimpleStringProperty(nom);
        this.prenom = new SimpleStringProperty(prenom);
       this.sexe= new SimpleStringProperty(sexe);
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

       public  String  getSexe() {
            return sexe.get();
       }

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
       /********************************************************************************************/
        @FXML
    private boolean OnKeyPressed(KeyEvent event1) {
         try
       {int i=Integer.parseInt(phoneNumber_box.getText()) ;
         errortel.setText(""); return true;}
        
         catch(NumberFormatException e)
         {
             errortel.setText("Invalid Number");
            return false;
         }
         
    }
/**********************************************************************************************************/
      
    @FXML
    public void Home(ActionEvent event) throws IOException {
        Parent loginAdmin = FXMLLoader.load(getClass().getResource("AdminPortal.fxml"));
           Scene ab = new Scene(loginAdmin);
            Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
            window.setScene(ab);
            window.show(); } 
 /***************************************************************************************************************/
@FXML
    public void Sign_Out(ActionEvent event) throws IOException {
        Parent loginAdmin = FXMLLoader.load(getClass().getResource("LoginAdmin.fxml"));
           Scene ab = new Scene(loginAdmin);
            Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
            window.setScene(ab);
            window.show(); } 
/*********************************************************************************************************************/    
    
   
    public static void infoBox(String infoMsg,String headerText,String title)         
       {
           Alert alert=new Alert(Alert.AlertType.INFORMATION);
           alert.setContentText(infoMsg);
           alert.setTitle(title);
           alert.setHeaderText(headerText);
           alert.showAndWait();
           
       } 
/******************************************************************************************************************/
    
     public static void infoBox2(String infoMsg,String headerText,String title)         
       {
           Alert alert=new Alert(Alert.AlertType.ERROR);
           alert.setContentText(infoMsg);
           alert.setTitle(title);
           alert.setHeaderText(headerText);
           alert.showAndWait();
           
       } 
  /********************************************************************************************************************/
  
}
