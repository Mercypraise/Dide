package apps.amazon.com.dide.models;

public class UserModel{

    public String displayname, email, pNumber, gender, emergencyNumber;

    public UserModel() {
    }

    public UserModel(String displayname, String email, String pNumber, String gender, String emergencyNumber){
        this.displayname = displayname;
        this.email = email;
        this.pNumber = pNumber;
        this.gender = gender;
        this.emergencyNumber = emergencyNumber;
    }


    public String getDisplayName(){
        return displayname;
    }

    public String getEmergencyNumber(){
        return emergencyNumber;
    }

    public String getEmail(){
        return email;
    }

    public String getpNumber(){
        return pNumber;
    }

    public String getGender(){
        return gender;
    }
}
