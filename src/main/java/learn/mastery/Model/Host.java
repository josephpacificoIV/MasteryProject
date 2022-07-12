package learn.mastery.Model;

public class Host {

    private String id;
    private String last_name;
    private String email;
    private String phone;
    private String address;
    private String city;
    private String state;
    private String postal_code;
    private double standard_rate;
    private double weekend_rate;

    public Host(){

    }

    public Host(String id, String last_name, String email, String phone, String address, String city, String state, String postal_code, double standard_rate, double weekend_rate) {

        this.id = id;
        this.last_name = last_name;
        this.email = email;
        this.phone = phone;
        this.address = address;
        this.city = city;
        this.state = state;
        this.postal_code = postal_code;
        this.standard_rate = standard_rate;
        this.weekend_rate = weekend_rate;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getPostal_code() {
        return postal_code;
    }

    public void setPostal_code(String postal_code) {
        this.postal_code = postal_code;
    }

    public double getStandard_rate() {
        return standard_rate;
    }

    public void setStandard_rate(double standard_rate) {
        this.standard_rate = standard_rate;
    }

    public double getWeekend_rate() {
        return weekend_rate;
    }

    public void setWeekend_rate(double weekend_rate) {
        this.weekend_rate = weekend_rate;
    }
}
