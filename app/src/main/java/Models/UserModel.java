package Models;

public class UserModel {
    private String identification;
    private String name;
    private String lastname;
    private String email;

    private  UserModel(){}

    public UserModel(String identification, String name, String lastname, String email) {
        this.identification = identification;
        this.name = name;
        this.lastname = lastname;
        this.email= email;

    }

    public String getIdentification() {
        return identification;
    }

    public void setIdentification(String identification){
        this.identification = identification;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }



}
