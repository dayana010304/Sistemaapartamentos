package Models;


public class ApartmentModel {
    private String owner;
    private String country;
    private String city;
    private String address;
    private String numberhab;
    private String valuenight;
    private String reviewapar;

    private  ApartmentModel(){}

    public ApartmentModel(String owner, String country, String city, String address, String numberhab, String valuenight, String reviewapar) {
        this.owner = owner;
        this.country = country;
        this.city = city;
        this.address = address;
        this.numberhab = numberhab;
        this.valuenight = valuenight;
        this.reviewapar = reviewapar;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner){
        this.owner = owner;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getNumberhab() {
        return numberhab;
    }

    public void setNumberhab(String numberhab) {
        this.numberhab = numberhab;
    }

    public String getValuenight() {
        return valuenight;
    }

    public void setValuenight(String valuenight) {
        this.valuenight = valuenight;
    }

    public String getReviewapar() {
        return reviewapar;
    }

    public void setReviewapar(String reviewapar) {
        this.reviewapar = reviewapar;
    }

}
