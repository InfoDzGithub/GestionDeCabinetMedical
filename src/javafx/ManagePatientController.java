package javafx;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextField;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
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
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
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
    private  JFXTextField age_box; 
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
    private TableColumn<Pat, Date> age_col;
    @FXML
    private TableColumn<Pat, String> situationF_col;
    @FXML
    private TableColumn<Pat, String> address_col;
    private Connexion db;
     
    ObservableList<Pat> list = FXCollections.observableArrayList();
    @FXML
    private TableView<Pat> tab;
    
   
  /*********************************************************************************************************/
           public static String currentDay()
           { Date date=new Date();
               return new SimpleDateFormat("yyyy-MM-dd").format(date);
           }
    @FXML
    private Label errortel;
    @FXML
    private Label errortel1;
    @FXML
    private Label nameError1;
    @FXML
    private Label nameError;
    
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
            sitFam=rdb_married.getText();
        
        KeyEvent event1 = null;
        
      if(cni.isEmpty()|| firstName.isEmpty()|| familyName.isEmpty()|| address.isEmpty()
       || gender.isEmpty()||sitFam.isEmpty()  || phoneNumber.isEmpty()|| age.isEmpty() )  
     {
      infoBox2("Please Fill Out The Form ", null, "Form Error!");   
     }
      else if(!OnKeyPressed(event1)||!OnKeyPressed1(event1)||!handle(event))
        {
      infoBox2("Please, Correct The Form ", null, "Form Error!");   
         
     }
      else{  
          String req="SELECT nic_pat FROM patient WHERE nic_pat='"+cni+"'";
         
          try{
               Connection conn=Connexion.ConnecrDB();
               PreparedStatement preparedSt=conn.prepareStatement(req);
               ResultSet result=preparedSt.executeQuery();
               
               if(result.next())
               { 
                 infoBox("The patient already exists", null, "Alert"); 
               }
               else
               {
                String sql="INSERT INTO patient(nic_pat,nom_pat,prenom_pat,sexe_pat,dateN_pat,adresse_pat,num_tel_pat,situation_fam,crdt_Pat) VALUES(?,?,?,?,?,?,?,?,?)";
                 
                   try {
                  conn=Connexion.ConnecrDB();
                 preparedSt=conn.prepareStatement(sql);
                 
                  preparedSt.setString(1, cni);
                  preparedSt.setString(2, familyName);
                  preparedSt.setString(3, firstName);
                   preparedSt.setString(4, gender);
                    preparedSt.setString(5, age);
                  preparedSt.setString(6, address);
                  preparedSt.setString(7, phoneNumber);
                  preparedSt.setString(8,sitFam);
                  preparedSt.setString(9,currentDay());
                  preparedSt.execute();
                  infoBox("Patient Added Successfully", null, "Success");
                 
                  loadData();
                  CancelData(event);
      
                    } 
                   catch (Exception e){}   
           
               }
                   
   }
   catch(Exception e){
       
   }
          
  
  
      } }
    /**********************************************************************************************************/
    public void loadData(){
       list.clear();
         try {
             Connection con=Connexion.ConnecrDB();
           
            ResultSet rs = con.createStatement().executeQuery("SELECT * FROM patient  where crdt_Pat='"+currentDay()+"'");
            while (rs.next()) {
                //get string from db,whichever way 
                list.add(new Pat(rs.getString(1),rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7), rs.getString(8),rs.getString(9) ));
            }

        } catch (SQLException ex) {
          
        }
   
        id_col.setCellValueFactory(new PropertyValueFactory<Pat,Integer>("ID"));
    cni_col.setCellValueFactory(new PropertyValueFactory<Pat,Integer>("CNI"));
      familyName_col.setCellValueFactory(new PropertyValueFactory<Pat,String>("nom"));
        firstName_col.setCellValueFactory(new PropertyValueFactory<Pat,String>("prenom"));
         gender_col.setCellValueFactory(new PropertyValueFactory<>("sexe"));
          address_col.setCellValueFactory(new PropertyValueFactory<Pat,String>("adresse"));
      phoneNumber_col.setCellValueFactory(new PropertyValueFactory<Pat,String>("ntel"));
        age_col.setCellValueFactory(new PropertyValueFactory<Pat,Date>("age"));
          situationF_col.setCellValueFactory(new PropertyValueFactory<Pat,String>("sitF"));
     
     tab.setItems(list);
  
}
    /********************************************************************************************************/
    
    @FXML
    private void consultData(ActionEvent event) {
         list.clear();
         try {
             Connection con=Connexion.ConnecrDB();
           
            ResultSet rs = con.createStatement().executeQuery("SELECT * FROM patient ");
            while (rs.next()) {
                
                list.add(new Pat(rs.getString(1),rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7), rs.getString(8),rs.getString(9) ));
            }

        } catch (SQLException ex) {
          
        }
   
        id_col.setCellValueFactory(new PropertyValueFactory<Pat,Integer>("ID"));
    cni_col.setCellValueFactory(new PropertyValueFactory<Pat,Integer>("CNI"));
      familyName_col.setCellValueFactory(new PropertyValueFactory<Pat,String>("nom"));
        firstName_col.setCellValueFactory(new PropertyValueFactory<Pat,String>("prenom"));
         gender_col.setCellValueFactory(new PropertyValueFactory<>("sexe"));
          address_col.setCellValueFactory(new PropertyValueFactory<Pat,String>("adresse"));
      phoneNumber_col.setCellValueFactory(new PropertyValueFactory<Pat,String>("ntel"));
        age_col.setCellValueFactory(new PropertyValueFactory<Pat,Date>("age"));
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
                   age_box.setText(result.getString("dateN_pat"));
                  
                   
                  if("Male".equals(result.getString("sexe_pat")))
                   {rdb_male.setSelected(true);}
                   else  if("Female".equals(result.getString("sexe_pat")))
                   {rdb_female.setSelected(true);}
                      if("Single".equals(result.getString("situation_fam")))
                   {rdb_signal.setSelected(true);}
                   else  if("Married".equals(result.getString("situation_fam")))
                   {rdb_married.setSelected(true);}
                 
               }
                   
   }
   catch(Exception e){}
    }
/**********************************************************************************************************/ 
     @FXML
    private void UpdateData(ActionEvent event)  {
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
            sitFam=rdb_married.getText();
         KeyEvent event1 = null;

        int myIndex =tab.getSelectionModel().getSelectedIndex();
         if(myIndex > -1){
               if(cni.isEmpty()|| firstName.isEmpty()|| familyName.isEmpty()|| address.isEmpty()
       || gender.isEmpty()||sitFam.isEmpty()  || phoneNumber.isEmpty()|| age.isEmpty() )  
     {
      infoBox2("Please Fill Out The Form ", null, "Form Error!");     
     }
               else if(!OnKeyPressed(event1)||!OnKeyPressed1(event1)|| !handle(event))
        {
      infoBox2("Correct The Form ", null, "Form Error!");   
         
     }
               else{ String id=tab.getItems().get(myIndex).getID();
         String req="Update patient set nic_pat = ? , prenom_pat = ? , nom_pat = ? , adresse_pat = ? ,num_tel_pat = ? , dateN_pat = ? , situation_fam = ? ,sexe_pat = ? where id_pat ='" +id+"'";
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
                   infoBox("Patient Modified Successfully", null, "Success");
                 
                  
                  
                  
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
  /***********************************************************************************************************/
    private void DeleteData(ActionEvent event) throws SQLException {
        
        
       int myIndex=tab.getSelectionModel().getSelectedIndex();
 if(myIndex > -1){
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
               infoBox("Patient Deleted Successfully", null, "Success");
               
//               Statement m = conn.createStatement();
//                  m.execute("set @autoid :=0");
//                  m.execute("UPDATE  patient  set id_pat = @autoid := (@autoid+1)");
//                  m.execute("ALTER TABLE patient  auto_increment = 1");
                  
               loadData();
              CancelData(event);
         }
         catch(Exception e)
         {
             
         }
}
   else
   {
       infoBox2("You Should Select a Row First", null, "Failed");
   }        
    }
    
 /************************************************************************************************************/
    @FXML
    public void CancelData(ActionEvent event) throws IOException
    {
       
                  cni_box.clear();
                  age_box.clear();
                 rdb_signal.setSelected(false);
                 rdb_married.setSelected(false);
                   firstName_box.clear();
                   familyName_box.clear();
                   address_box.clear();
                   phoneNumber_box.clear();
                   rdb_male.setSelected(false);
                   rdb_female.setSelected(false);
    }

    @FXML
    private boolean OnKeyPressed(KeyEvent event) {
         try
       {int i=Integer.parseInt(phoneNumber_box.getText()) ;
         errortel.setText(""); return true;}
        
         catch(NumberFormatException e)
         {
             errortel.setText("Invalid Number");return false;
         }
    }

    @FXML
    private boolean OnKeyPressed1(KeyEvent event) {
        try
       {int i=Integer.parseInt(cni_box.getText()) ;
         errortel1.setText("");return true;}
        
         catch(NumberFormatException e)
         {
             errortel1.setText("Invalid CNI");return false;
         }
    }
/***********************************************************************************/
     @FXML
    private void error(KeyEvent keyEvent) {
        if (!keyEvent.isConsumed()){KeyCode code = keyEvent.getCode();
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
    }
 /************************************************************************************/

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
   /*************************************************************************************************************/
     @FXML
    public boolean handle(ActionEvent event) {
            if(firstName_box.getText().matches("[aA-zZ ]+$") || familyName_box.getText().matches("[aA-zZ ]+$")) 
             return true;
                else return false;
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
           Alert alert=new Alert(Alert.AlertType.INFORMATION);
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
    
    
 /****************************************************************************************************************/ 
    
}