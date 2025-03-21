package vttp.batch5.csf.assessment.server.models;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ValidUser {

    private String username;
    private String hashPassword;


    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getHashPassword() {
        return hashPassword;
    }
    public void setHashPassword(String hashPassword) {
        this.hashPassword = hashPassword;
    }

    public static ValidUser populate(ResultSet rs) throws SQLException {
        ValidUser validUser = new ValidUser();
        validUser.setUsername(rs.getString("username"));
        validUser.setHashPassword(rs.getString("password"));

        return validUser;
    }

    

    
}
