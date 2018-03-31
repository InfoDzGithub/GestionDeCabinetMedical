/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafx;

import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
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
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

/**
 * FXML Controller class
 *
 * @author User
 */
public class ManageAppointmentController implements Initializable {

    @FXML
    private JFXDatePicker dateSelector;
    @FXML
    private JFXDatePicker SelectTimer;
    @FXML
    private ComboBox<String> combobox;
    @FXML
    private JFXTextArea comment_box;
    
    
    
   
    ObservableList<String> list2 = FXCollections.observableArrayList();
     private Connexion db;
    ObservableList<Rdv> list = FXCollections.observableArrayList();
    @FXML
    private TableView<Rdv> tab;
    @FXML
    private TableColumn<Rdv, Integer> id_col;
    @FXML
    private TableColumn<Rdv, String> pat_inf_col;
    @FXML
    private TableColumn<Rdv, Date> date_col;
    @FXML
    private TableColumn<Rdv, Time> time_col;
    @FXML
    private TableColumn<Rdv, String> comment_col;
   
 /*************************************************************************************************************/ 
    @FXML
    public void insertData(ActionEvent event) { 
        
       
      String date=dateSelector.getValue().toString();
      String time=SelectTimer.getTime().toString();
     // String commT =comment_box.getText().replaceAll("\n", System.getProperty("line.separator"));
     String text =comment_box.getText();
     String patient_inf=combobox.selectionModelProperty().getValue().getSelectedItem();
    
         if(date.isEmpty() || time.isEmpty() || text.isEmpty())
         
         {
             infoBox2("Please Fill Out The Form ", null, "Form Error!");  
         }
         else{
        String sql="INSERT INTO rdv (date_rdv,heure_rdv,info_P,commentaire) VALUES(?,?,?,?) ";
       Connection conn;
                   try {
                  conn=Connexion.ConnecrDB();
                  PreparedStatement preparedSt=conn.prepareStatement(sql);
                 
                  preparedSt.setString(1,date);
                  preparedSt.setString(2, time);
                  preparedSt.setString(3, patient_inf);
                  preparedSt.setString(4, text);
                   
                  
                  preparedSt.execute();
                  infoBox("RDV Added Successfully", null, "Success");
                  
                  Statement m = conn.createStatement();
                  m.execute("set @autoid :=0");
                  m.execute("UPDATE  rdv  set id_rdv = @autoid := (@autoid+1)");
                  m.execute("ALTER TABLE rdv  auto_increment = 1");
                  
                   loadData();
                 
         comment_box.clear();
        dateSelector.setValue(null);
        SelectTimer.setValue(null);
        combobox.selectionModelProperty().getValue().clearSelection();
                   
                
                   
                    } 
                   catch (Exception e)
                        {
                        //infoBox2("You should select a row", null, "Failed");     
                        }     
   }}
 /*************************************************************************************************************/
  
    
 /**************************************************************************************************************/
    
     public void loadData(){
       list.clear();
         try {
             Connection con=Connexion.ConnecrDB();
            
            ResultSet rs = con.createStatement().executeQuery("SELECT * FROM rdv where date_rdv>='"+ManagePatientController.currentDay()+"'");
            while (rs.next()) {
                //get string from db,whichever way 
                list.add(new Rdv(rs.getString(1),rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5) ));
            }

        } catch (Exception ex) {
            
        }
   
      id_col.setCellValueFactory(new PropertyValueFactory<Rdv,Integer>("ID"));
      pat_inf_col.setCellValueFactory(new PropertyValueFactory<Rdv,String>("infP"));
      comment_col.setCellValueFactory(new PropertyValueFactory<Rdv,String>("comment"));
      date_col.setCellValueFactory(new PropertyValueFactory<Rdv,Date>("date"));
      time_col.setCellValueFactory(new PropertyValueFactory<Rdv,Time>("time"));
        
     
     tab.setItems(list);
  
}
  
 /**************************************************************************************************************/
  @FXML
    private void TableMouseClick(MouseEvent event) {
        
        int myIndex =tab.getSelectionModel().getSelectedIndex();
   String id=tab.getItems().get(myIndex).getID();
   String sqls="Select * from rdv where id_rdv ='" +id+"'";
   try{
               Connection conn=Connexion.ConnecrDB();
               PreparedStatement preparedSt=conn.prepareStatement(sqls);
               ResultSet result=preparedSt.executeQuery();
               
               if(result.next())
               { 
                   comment_box.setText(result.getString("commentaire"));
                   /*
                   
                   */
                  
               
               }
                   
   }
   catch(Exception e){
      
       
   }
    }
    /*************************************************************************************************************/
    @FXML
    private void UpdateDataR(ActionEvent event) {
          int myIndex =tab.getSelectionModel().getSelectedIndex();
         String id=tab.getItems().get(myIndex).getID();
      
String req="Update rdv set date_rdv = ? , heure_rdv = ? , info_P = ? , commentaire = ?  where id_rdv ='" +id+"' ";
         //
          Connection conn=Connexion.ConnecrDB();
         try{
          PreparedStatement preparedSt=conn.prepareStatement(req);
          
                  preparedSt.setString(1, dateSelector.getValue().toString());
                  preparedSt.setString(2,SelectTimer.getTime().toString() );
                  preparedSt.setString(3, combobox.selectionModelProperty().getValue().getSelectedItem());
                  preparedSt.setString(4, comment_box.getText().toString());
                 
                  preparedSt.execute();
                   infoBox("RDV Modify Successfully", null, "Success");
                 
                  loadData();
                 comment_box.clear();
        dateSelector.setValue(null);
        SelectTimer.setTime(null);
        combobox.selectionModelProperty().getValue().clearSelection();
                   
                   
         }
         catch(Exception e)
         {
             
         }
    
    }

  /***************************************************************************************************************/
     @FXML
    private void DeleteData(ActionEvent event) throws SQLException {
        
        
       int myIndex=tab.getSelectionModel().getSelectedIndex();
        String id=tab.getItems().get(myIndex).getID();
        

        String requette="Delete from rdv where id_rdv ='" +id+"'";
         try{
               Connection conn=Connexion.ConnecrDB();
               PreparedStatement preparedSt=conn.prepareStatement(requette);
               preparedSt.execute();
               infoBox("RDV Deletted Successfully", null, "Success");
               
               Statement m = conn.createStatement();
                  m.execute("set @autoid :=0");
                  m.execute("UPDATE  rdv  set id_rdv = @autoid := (@autoid+1)");
                  m.execute("ALTER TABLE rdv  auto_increment = 1");
                  
               loadData();
               comment_box.clear();
        dateSelector.setValue(null);
        SelectTimer.setTime(null);
        combobox.selectionModelProperty().getValue().clearSelection();
                  
               
         }
         catch(Exception e)
         {
             
         }
         Connection conn=Connexion.ConnecrDB();
         Statement s = conn.createStatement();
         s.execute("ALTER TABLE rdv auto_increment = 1");
        
    }
 /*****************************************************************************************************************/
     @FXML
    public void Home(ActionEvent event) throws IOException {
        Parent loginAdmin = FXMLLoader.load(getClass().getResource("RecepPortal.fxml"));
           Scene ab = new Scene(loginAdmin);
            Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
            window.setScene(ab);
            window.show(); } 
  /******************************************************************************************************************/
@FXML
    public void Sign_Out(ActionEvent event) throws IOException {
        Parent loginAdmin = FXMLLoader.load(getClass().getResource("LoginRecep.fxml"));
           Scene ab = new Scene(loginAdmin);
           Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
            window.setScene(ab);
            window.show(); } 
 /******************************************************************************************************************/   
 public void loadData2(){
       list2.clear();
         try {
             Connection con=Connexion.ConnecrDB();
           
            ResultSet rs = con.createStatement().executeQuery("SELECT concat(concat(id_pat,' '),concat(nom_pat,' '),concat(prenom_pat,' '),nic_pat) FROM patient ");// order by id_pat asc
            while (rs.next()) {
                //get string from db,whichever way 
                list2.add(new String(rs.getString(1)));
            }

        } catch (Exception ex) {
            
        }
   }
 
 
 /*****************************************************************************************************************/   
    @Override
    public void initialize(URL url, ResourceBundle rb) {
     loadData2();
     combobox.setItems(list2);
     //SelectTimer.setTime(LocalTime.of(14, 0));
     //dateSelector.setValue(LocalDate.of(2018, 03, 28));
      loadData();
     db = new Connexion();
        
    
 }
/******************************************************************************************************************/
    @FXML
    private void ComboboxSelect(MouseEvent event) {
       
    /*  
    String id=combobox.getSelectionModel().getSelectedItem();
    String red="SELECT * from patient where concat(concat(nom_pat,'  '),concat(prenom_pat,'   '),nic_pat)='" +id+"'";
    try{
               Connection conn=Connexion.ConnecrDB();
               PreparedStatement preparedSt=conn.prepareStatement(red);
               ResultSet result=preparedSt.executeQuery();
               
               if(result.next())
               { 
                   firstName_box.setText(result.getString("prenom_pat"));
                   familyName_box.setText(result.getString("nom_pat"));
               
               }
                   
   }
   catch(Exception e){
       
   }
   */
    }  

   
     
 /******************************************************************************************************************/   

 public static class Rdv{
    
     private final SimpleStringProperty  ID,infP,date,time,comment;
    // private final DateFormat date;
    
    //constructeur
    public Rdv(String ID,String date,String time,String infP,String comment)
    {//
      this.ID = new SimpleStringProperty(ID);
      this.infP = new SimpleStringProperty(infP);
      //this.date= new SimpleDateFormat("yy/mm/jj");
      this.time = new SimpleStringProperty(time);
      this.date = new SimpleStringProperty(date);
      this.comment= new SimpleStringProperty(comment);
      //SimpleDateFormat d = new SimpleDateFormat ("dd/MM/yyyy" );
      //SimpleDateFormat h = new SimpleDateFormat ("hh:mm");  
            
    }
    
     public String getID() {
            return ID.get();
        }

   
         public String getInfP() {
            return infP.get();
        }

        public String getDate() {
            return  date.get();
        }

        public  String  getTime() {
            return time.get();
        }

       public  String  getComment() {
            return comment.get();
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
       
     @FXML
    private void CancelData(ActionEvent event) {
        comment_box.clear();
        dateSelector.setValue(null);
        SelectTimer.setTime(null);
        combobox.selectionModelProperty().getValue().clearSelection();
        
    }    
    
/********************************************************************************************************************/
}
