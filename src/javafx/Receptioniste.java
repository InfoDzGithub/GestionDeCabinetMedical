
package javafx;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;


public class Receptioniste {
    private SimpleStringProperty ID, first_name,family_name,Phone_Number,User_Name,Password,Address;
   // private SimpleIntegerProperty ID;
    public Receptioniste(String ID, String first_name, String family_name, String Phone_Number, String User_Name, String Password, String Address) {
        this.ID = new SimpleStringProperty(ID);
        this.first_name = new SimpleStringProperty(first_name);
        this.family_name = new SimpleStringProperty(family_name);
        this.Phone_Number = new SimpleStringProperty(Phone_Number);
        this.User_Name = new SimpleStringProperty(User_Name);
        this.Password = new SimpleStringProperty(Password);
        this.Address = new SimpleStringProperty(Address);
    }

    public String getID() {
        return ID.get() ;
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

   
    //Property values
    public StringProperty IDProperty() {
        return ID;
    }
    public StringProperty nomProperty() {
        return family_name;
    }

    public StringProperty prenomProperty() {
        return first_name;
    }

   
    public StringProperty adresseProperty() {
        return Address;
    }

    public StringProperty ntelProperty() {
        return Phone_Number;
    }

    public StringProperty usernameProperty() {
        return User_Name;
    }
     public StringProperty passwordProperty() {
        return Password;
    }
    
   
    
                       }
