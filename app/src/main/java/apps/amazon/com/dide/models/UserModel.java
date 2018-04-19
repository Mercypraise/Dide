package apps.amazon.com.dide.models;

public class UserModel{

    public String displayName, email, pNumber, gender, emergencyNumber;

    public UserModel() {
    }

    public UserModel(String displayName, String email, String pNumber, String gender, String emergencyNumber){
        this.displayName = displayName;
        this.email = email;
        this.pNumber = pNumber;
        this.gender = gender;
        this.emergencyNumber = emergencyNumber;
    }


    public String getDisplayName(){
        return displayName;
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
