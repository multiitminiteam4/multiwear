package multiwear.member.model.dto;


public class MemberDto {


    private String user_Id;
    private String userName;
    private String email;
    private String password;
    private String phone;
    

    public MemberDto(String user_Id, String userName, String email, String password, String phone) {
        this.user_Id = user_Id;
        this.userName = userName;
        this.email = email;
        this.password = password;
        this.phone = phone;
    }

    public MemberDto(String userId, String password) {
        this.user_Id = user_Id;
        this.password = password;
    }

    public String getUser_Id() {
        return user_Id;
    }

    public void setUser_Id(String user_Id) {
        this.user_Id = user_Id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

   

    @Override
    public String toString() {
        return "MemberDto{" +
                "user_Id='" + user_Id + '\'' +
                ", userName='" + userName + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", phone=" + phone +
                '}';
    }
}