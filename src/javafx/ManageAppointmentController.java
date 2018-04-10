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
import java.time.LocalDate;
import java.time.LocalDateTime;
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
import javafx.scene.control.Label;
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
    private JFXTextArea comment_box;
    @FXML
    private JFXTextField name_box;
   
    @FXML
    private JFXTextField search_box;
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
    @FXML
    private Label label_box;
    String inf_patient=" ", cni="",idpatient="";
  
    /***************************************************************************************************************/
  
    @FXML
    private void search_Patient(ActionEvent event) {
        
       cni=search_box.getText();
      if(cni.isEmpty())
        {
          label_box.setText(" Please Fill Out The CNI Patient");   
        }
      else{
        
      String req="Select concat(concat(nom_pat,' '),concat(prenom_pat,' '),dateN_pat) from patient where nic_pat ='" +cni+"'";
                try{
                            Connection conn=Connexion.ConnecrDB();
                            PreparedStatement preparedSt=conn.prepareStatement(req);
                            ResultSet result=preparedSt.executeQuery();

                            if(result.next())
                             name_box.setText(result.getString(1));
                         

                }
                catch(Exception e){}  
        
    }}
    /**************************************************************************************************/
     @FXML
    public boolean workingHoursMorning(LocalTime heure)
    {
        LocalTime timeBegin =LocalTime.of(9, 30);
        LocalTime timeEnd =LocalTime.of(12, 0);
        if (heure.isBefore(timeEnd) && heure.isAfter(timeBegin))
        {
            return true;
        }
        
        else return false;
    }
   
     @FXML
    public boolean workingHoursAfterNoon(LocalTime heure)
    {
        LocalTime timeBegin =LocalTime.of(14, 0);
        LocalTime timeEnd =LocalTime.of(17, 0);
        if (heure.isBefore(timeEnd) && heure.isAfter(timeBegin))
        {
            return true;
        }
        
        else return false;
    }
   
 /*************************************************************************************************************/ 
    @FXML
    public void insertData(ActionEvent event) { 
        cni=search_box.getText();
      inf_patient=name_box.getText();
      String text =comment_box.getText();
      String date=dateSelector.getValue().toString();
     // String time=SelectTimer.getTime().toString();
      LocalTime time=SelectTimer.getTime();
   
         if(date.isEmpty() || text.isEmpty() || !workingHoursAfterNoon(time)|| !workingHoursMorning(time))
         
         {
             infoBox2("Please Fill Out The Form With Time respecting", null, "Form Error!");  
         }
         else{ 
             String req="Select id_pat from patient where nic_pat ='" +cni+"'";
                try{
                            Connection conn=Connexion.ConnecrDB();
                            PreparedStatement preparedSt=conn.prepareStatement(req);
                            ResultSet result=preparedSt.executeQuery();

                            if(result.next())  idpatient=result.getString(1);
                            
                    }
                catch(Exception e){} 
                
        String sql="INSERT INTO rdv (date_rdv,heure_rdv,info_P,commentaire,id_pat) VALUES(?,?,?,?,?) ";
       Connection conn;
                   try {
                  conn=Connexion.ConnecrDB();
                  PreparedStatement preparedSt=conn.prepareStatement(sql);
                 
                  preparedSt.setString(1,date);
                  preparedSt.setString(2, time+"");
                  preparedSt.setString(3, inf_patient);
                  preparedSt.setString(4, text);
                  preparedSt.setString(5, idpatient);
                   
                   
                  
                  preparedSt.execute();
                  infoBox("RDV Added Successfully", null, "Success");
//                  
//                  Statement m = conn.createStatement();
//                  m.execute("set @autoid :=0");
//                  m.execute("UPDATE  rdv  set id_rdv = @autoid := (@autoid+1)");
//                  m.execute("ALTER TABLE rdv  auto_increment = 1");
                  
                   loadData();
                   
                 
                  CancelData(event);
                   
                    } 
                   catch (Exception e){} 
                   
   }}
    
 /**************************************************************************************************************/
    
     public void loadData(){
       list.clear();
         try {
             Connection con=Connexion.ConnecrDB();
            
            ResultSet rs = con.createStatement().executeQuery("SELECT * FROM rdv where date_rdv>='"+ManagePatientController.currentDay()+"'");
            while (rs.next()) {
                //get string from db,whichever way 
                list.add(new Rdv(rs.getString(1),rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6) ));
                               }

            } 
         catch (Exception ex) { }
   
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
                   
                   name_box.setText( result.getString("info_P"));
                 
                  String maReq="SELECT nic_pat from patient,rdv where info_P=concat(concat(nom_pat,' '),concat(prenom_pat,' '),dateN_pat) AND id_rdv='" +id+"'";
                try{       
                             Connection conn1=Connexion.ConnecrDB();
                            PreparedStatement preparedSt2=conn1.prepareStatement(maReq);
                            ResultSet result2=preparedSt2.executeQuery();

                            if(result2.next())
                            { 
                              search_box.setText( result2.getString(1));
                            }

                }
                catch(Exception e){}
               
               }
                   
        }
   catch(Exception e){}
    }
    /*************************************************************************************************************/
    @FXML
    private void UpdateDataR(ActionEvent event) {
          int myIndex =tab.getSelectionModel().getSelectedIndex();
           if(myIndex > -1){
         String id=tab.getItems().get(myIndex).getID();
       
String req="Update rdv set date_rdv = ? , heure_rdv = ? , info_P = ? , commentaire = ?  where id_rdv ='" +id+"' ";
          Connection conn=Connexion.ConnecrDB();
         try{
          PreparedStatement preparedSt=conn.prepareStatement(req);
          
                  preparedSt.setString(1, dateSelector.getValue().toString());
                  preparedSt.setString(2,SelectTimer.getTime().toString() );
                  preparedSt.setString(3, name_box.getText().toString());
                  preparedSt.setString(4, comment_box.getText().toString());
                 
                  preparedSt.execute();
                   infoBox("RDV Modify Successfully", null, "Success");
                 
                  loadData();
                  
                CancelData(event);
                   
                   
         }
         catch(Exception e)
         {
             
         }
         }
   else
   {
       infoBox2("You should select a row first", null, "Failed");
   }
    
    }

  /***************************************************************************************************************/
     @FXML
    private void DeleteData(ActionEvent event) throws SQLException {
        
        
       int myIndex=tab.getSelectionModel().getSelectedIndex();
        if(myIndex > -1){
        String id=tab.getItems().get(myIndex).getID();
        

        String requette="Delete from rdv where id_rdv ='" +id+"'";
         try{
               Connection conn=Connexion.ConnecrDB();
               PreparedStatement preparedSt=conn.prepareStatement(requette);
               preparedSt.execute();
               infoBox("RDV Deletted Successfully", null, "Success");
               
 
                  
               loadData();
       CancelData(event);
               
         }
         catch(Exception e)
         {
             
         }
         }
   else
   {
       infoBox2("You should select a row first", null, "Failed");
   }
       
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

 /*****************************************************************************************************************/   
    @Override
    public void initialize(URL url, ResourceBundle rb) {
     
     // Par défaut la date resoit la date du RDV
      loadData();
     db = new Connexion();
        
    
 }

 /******************************************************************************************************************/   

 public static class Rdv{
    
     private final SimpleStringProperty  ID,infP,date,time,comment;
    // private final DateFormat date;
    
    //constructeur
    public Rdv(String ID,String date,String time,String infP,String comment)
    {
      this.ID = new SimpleStringProperty(ID);
      this.infP = new SimpleStringProperty(infP);

      this.time = new SimpleStringProperty(time);
      this.date = new SimpleStringProperty(date);
      this.comment= new SimpleStringProperty(comment);
     
            
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
    
    
 /******************************************************************************************************************/  
       
     @FXML
    private void CancelData(ActionEvent event) {
        comment_box.clear();
       
        name_box.clear();
        search_box.clear();
        
    }    
    
/********************************************************************************************************************/
}