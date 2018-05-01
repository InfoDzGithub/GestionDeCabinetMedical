
package javafx;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextField;
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





public class ManageRecepController implements Initializable {

    @FXML
    private JFXTextField firstName_box;
    @FXML
    private JFXTextField cni_box;
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
    private JFXRadioButton rdb_male;
    @FXML
    private ToggleGroup Group1;
    @FXML
    private JFXRadioButton rdb_female;
   
    private Connexion connexion;
  
    @FXML
    private TableColumn<Receptioniste,Integer> id_Column;
     @FXML
    private TableColumn<Receptioniste, Integer> cni_Column;
    @FXML
    private TableColumn<Receptioniste,String> firstNameColumn;
    @FXML
    private TableColumn<Receptioniste, String> familyNameColumn;
      @FXML
    private TableColumn<Receptioniste, String> gender_col;
    @FXML
    private TableColumn<Receptioniste, String> phoneNumberColumn;
    @FXML
    private TableColumn<Receptioniste, String> userNameColumn;
    @FXML
    private TableColumn<Receptioniste, String> passwordColumn;
    @FXML
    private TableColumn<Receptioniste, String> addressColumn;
    
    ObservableList<Receptioniste>data = FXCollections.observableArrayList();
    @FXML
    private TableView<Receptioniste> tab;
    @FXML
    private Label errortel;
    @FXML
    private Label errortel1;
    @FXML
    private Label nameError;
    @FXML
    private Label nameError1;
 /**************************************************************************************************************/   
      
    @Override
    public void initialize(URL url, ResourceBundle rb) {
     
     connexion=new Connexion();
       
        loadData();
    } 
 /***************************************************************************************************************/     
  
    @FXML
    public void cancel(ActionEvent event) throws IOException
    {
       
                  cni_box.clear();
                  username_box.clear();
                   password_box.clear();
                   firstName_box.clear();
                   familyName_box.clear();
                   address_box.clear();
                   phoneNumber_box.clear();
                   rdb_male.setSelected(false);
                    rdb_female.setSelected(false);
    }
    /**********************************************************************************************************/
    @FXML
    public void insertData(ActionEvent event) throws IOException {
        String firstName= firstName_box.getText();
        String familyName= familyName_box.getText();
        String address= address_box.getText();
        String phoneNumber= phoneNumber_box.getText();
        String cni= cni_box.getText();
        String gender="";
         
        String user= username_box.getText();
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
      infoBox2("Correct The Form ", null, "Form Error!");   
         
     }
      else{  
          String req="SELECT num_cni FROM receptioniste WHERE num_cni='"+cni+"'";
         
          try{
               Connection conn3=Connexion.ConnecrDB();
               PreparedStatement preparedSt3=conn3.prepareStatement(req);
               ResultSet result=preparedSt3.executeQuery();
               
               if(result.next())
               { 
                 infoBox2("The receptionist already exists", null, "Alert"); 
               }
                else{  
                   String sql="INSERT INTO receptioniste(num_cni,nom_recep,prenom_recep,sexe_recep,adresse_recep,num_tel_recep,username_recep,password_recep) VALUES(?,?,?,?,?,?,?,?)";
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
                           infoBox("Receptionist Added Successfully", null, "Success");

                           Statement m = conn.createStatement();
                           m.execute("set @autoid :=0");
                           m.execute("UPDATE  receptioniste  set id_recep = @autoid := (@autoid+1)");
                           m.execute("ALTER TABLE receptioniste  auto_increment = 1");
                           loadData();
                           cancel(event);

                             } 
                        catch (Exception e) { }   
        
         }  }
           catch (Exception e){}   
       }}
   /**********************************************************************************************************/

 public void loadData() 
{
     data.clear();
  try {
             Connection con=Connexion.ConnecrDB();
           
            ResultSet rs = con.createStatement().executeQuery("SELECT * FROM receptioniste");
            while (rs.next()) {
               data.add(new Receptioniste(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7), rs.getString(8),rs.getString(9) ));
            }

        } catch (SQLException ex) {
            System.err.println("Error"+ex);
        }
   
       id_Column.setCellValueFactory(new PropertyValueFactory<Receptioniste, Integer>("ID"));
       cni_Column.setCellValueFactory(new PropertyValueFactory<Receptioniste, Integer>("CNI"));
     firstNameColumn.setCellValueFactory(new PropertyValueFactory<Receptioniste, String>("first_name"));
     familyNameColumn.setCellValueFactory(new PropertyValueFactory<Receptioniste, String>("family_name"));
     gender_col.setCellValueFactory(new PropertyValueFactory<Receptioniste, String>("sexe"));
     phoneNumberColumn.setCellValueFactory(new PropertyValueFactory<Receptioniste, String>("Phone_Number"));
     userNameColumn.setCellValueFactory(new PropertyValueFactory<Receptioniste, String>("User_Name"));
     passwordColumn.setCellValueFactory(new PropertyValueFactory<Receptioniste, String>("Password"));
     addressColumn.setCellValueFactory(new PropertyValueFactory<Receptioniste, String>("Address"));
     
     tab.setItems(data);
  
}
/***********************************************************************************************************/   

  @FXML
    private void DeleteDataR(ActionEvent event) throws SQLException {
        
        int myIndex =tab.getSelectionModel().getSelectedIndex();
        if(myIndex > -1){
        String id=tab.getItems().get(myIndex).getID();
        String requette="Delete from receptioniste where id_recep ='" +id+"'";
         try{
               Connection conn=Connexion.ConnecrDB();
               PreparedStatement preparedSt=conn.prepareStatement(requette);
               preparedSt.execute();
               infoBox("Receptionist Deleted Successfully", null, "Success");
               
               Statement m = conn.createStatement();
                  m.execute("set @autoid :=0");
                  m.execute("UPDATE  receptioniste  set id_recep = @autoid := (@autoid+1)");
                  m.execute("ALTER TABLE receptioniste  auto_increment = 1");
                  
               loadData();
               cancel(event);
               
         }
         catch(Exception e) {}
         }
   else
   {
       infoBox2("You Should Select a Row First", null, "Failed");
   }
        
         Connection conn=Connexion.ConnecrDB();
         Statement s = conn.createStatement();
         s.execute("ALTER TABLE receptioniste auto_increment = 1");
    }
 
 
 /***********************************************************************************************************/
 //Cette fonction permet de transformer une ligne from TableView to fields Corresponding
    @FXML
    private void TableMouseClick(MouseEvent event) {
       
   int myIndex =tab.getSelectionModel().getSelectedIndex();
  
   String id=tab.getItems().get(myIndex).getID();
   String sqls="Select * from receptioniste where id_recep ='" +id+"'";
   try{
               Connection conn=Connexion.ConnecrDB();
               PreparedStatement preparedSt=conn.prepareStatement(sqls);
               ResultSet result=preparedSt.executeQuery();
               
               if(result.next())
               { cni_box.setText(result.getString("num_cni"));
                   firstName_box.setText(result.getString("prenom_recep"));
                   familyName_box.setText(result.getString("nom_recep"));
                   address_box.setText(result.getString("adresse_recep"));
                   phoneNumber_box.setText(result.getString("num_tel_recep"));
                   username_box.setText(result.getString("username_recep"));
                   password_box.setText(result.getString("password_recep"));
                  
                   if("Male".equals(result.getString("sexe_recep")))
                   {rdb_male.setSelected(true);}
                   else  if("Female".equals(result.getString("sexe_recep")))
                   {rdb_female.setSelected(true);}
                 
               }
                   
   }
   catch(Exception e){}
  
    }
/**************************************************************************************************************/
    @FXML
    private void UpdateRData(ActionEvent event) {
        
         String firstName= firstName_box.getText();
        String familyName= familyName_box.getText();
        String address= address_box.getText();
        String phoneNumber= phoneNumber_box.getText();
        String cni= cni_box.getText();
        String gender="";
        String user= username_box.getText();
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
      else if(!OnKeyPressed(event1)||!OnKeyPressed1(event1)|| !handle(event))
        {
      infoBox2("Please, Correct The Form ", null, "Form Error!");   
         
     }
      else{
         String id=tab.getItems().get(myIndex).getID();
         String req="Update receptioniste set num_cni = ? , prenom_recep = ? , nom_recep = ? , adresse_recep = ? ,num_tel_recep = ? , username_recep = ? , password_recep = ? ,sexe_recep = ?  where id_recep ='" +id+"'";
          Connection conn=Connexion.ConnecrDB();
         try{
          PreparedStatement preparedSt=conn.prepareStatement(req);
          
                  preparedSt.setString(1, cni_box.getText().toString());
                  preparedSt.setString(2,firstName_box.getText().toString() );
                  preparedSt.setString(3, familyName_box.getText().toString());
                  preparedSt.setString(4,address_box.getText().toString());
                  preparedSt.setString(5, phoneNumber_box.getText().toString());
                  preparedSt.setString(6, username_box.getText().toString());
                  preparedSt.setString(7,  password_box.getText().toString());
                  
                  
                  if(rdb_male.isSelected())
                   preparedSt.setString(8, rdb_male.getText().toString());
                  else if(rdb_female.isSelected())
                 
                  preparedSt.setString(8, rdb_female.getText().toString());
                
                  preparedSt.execute();
                   infoBox("Receptionist Modified Successfully", null, "Success");
                 
                  loadData();
                  cancel(event);
                   
         }
         catch(Exception e)
         { }
          }}
   else
   {
       infoBox2("You Should Select a Row First", null, "Failed");
   }
    }
/***************************************************************************************/
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
/********************************************************************************/
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
/************************************************************************************************/
     @FXML
    public boolean handle(ActionEvent event) {
            if(firstName_box.getText().matches("[aA-zZ ]+$") || familyName_box.getText().matches("[aA-zZ ]+$")) 
             return true;
                else return false;
           }
/*****************************************************************************************************/

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
    
/******************************************************************************************************************/
public class Receptioniste {
    private SimpleStringProperty ID,CNI ,first_name,family_name,sexe,Phone_Number,User_Name,Password,Address;
  
    public Receptioniste(String ID,String CNI, String family_name, String first_name,String sexe, String Address, String Phone_Number, String User_Name, String Password) {
        this.ID = new SimpleStringProperty(ID);
         this.CNI = new SimpleStringProperty(CNI);
        this.first_name = new SimpleStringProperty(first_name);
        this.family_name = new SimpleStringProperty(family_name);
         this.sexe= new SimpleStringProperty(sexe);
        this.Phone_Number = new SimpleStringProperty(Phone_Number);
        this.User_Name = new SimpleStringProperty(User_Name);
        this.Password = new SimpleStringProperty(Password);
        this.Address = new SimpleStringProperty(Address);
    }

    public String getID() {
        return ID.get() ;
    }
  public String getCNI() {
            return CNI.get();
        }
    public void setID(SimpleStringProperty ID) {
        this.ID = ID;
    }

    public String getFirst_name() {
        return first_name.get();
    }

    public void setFirst_name(SimpleStringProperty first_name) {
        this.first_name = first_name;
    }

    public String getFamily_name() {
        return family_name.get();
    }

    public void setFamily_name(SimpleStringProperty family_name) {
        this.family_name = family_name;
    }

     public  String  getSexe() {
            return sexe.get();
       }
    public String getPhone_Number() {
        return Phone_Number.get();
    }

    public void setPhone_Number(SimpleStringProperty Phone_Number) {
        this.Phone_Number = Phone_Number;
    }

    public String getUser_Name() {
        return User_Name.get();
    }

    public void setUser_Name(SimpleStringProperty User_Name) {
        this.User_Name = User_Name;
    }

    public String getPassword() {
        return Password.get();
    }

    public void setPassword(SimpleStringProperty Password) {
        this.Password = Password;
    }

    public String getAddress() {
        return Address.get();
    }

    public void setAddress(SimpleStringProperty Address) {
        this.Address = Address;
    }

   
}
/******************************************************************************************************************/
 @FXML
    public void Home(ActionEvent event) throws IOException {
        Parent loginAdmin = FXMLLoader.load(getClass().getResource("AdminPortal.fxml"));
           Scene ab = new Scene(loginAdmin);
            Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
            window.setScene(ab);
            window.show(); } 
    /****************************************************************************************************************/
@FXML
    public void Sign_Out(ActionEvent event) throws IOException {
        Parent loginAdmin = FXMLLoader.load(getClass().getResource("LoginAdmin.fxml"));
           Scene ab = new Scene(loginAdmin);
            Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
            window.setScene(ab);
            window.show(); } 
    
/*******************************************************************************************************************/  
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
}