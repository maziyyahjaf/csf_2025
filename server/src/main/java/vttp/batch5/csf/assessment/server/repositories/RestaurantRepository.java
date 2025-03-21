package vttp.batch5.csf.assessment.server.repositories;

import java.util.Optional;



import java.sql.ResultSet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import vttp.batch5.csf.assessment.server.exceptions.InvalidUsernameException;
import vttp.batch5.csf.assessment.server.models.ValidUser;

// Use the following class for MySQL database
@Repository
public class RestaurantRepository {
    private static final Logger logger = LoggerFactory.getLogger(RestaurantRepository.class);
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public static final String CHECK_PASSWORD = 
                "SELECT password FROM customers WHERE username = ?";
    public static final String CHECK_USERNAME_PASSWORD = 
            "SELECT username, password FROM customers WHERE username = ?";

    
    public Optional<ValidUser> validateUsername(String username, String password) {

        try {
            // check if username exists
            return jdbcTemplate.query(CHECK_USERNAME_PASSWORD, (ResultSet rs)-> {
                if (rs.next()) {
                    return Optional.of(ValidUser.populate(rs));
                } else {
                    throw new InvalidUsernameException("Invalid username");
                }
            }, username);


        } catch(DataAccessException ex) {
            logger.error("SQL Error: {} - {}", ex.getMessage(), ex.getCause());
            throw new RuntimeException();

        }
    }

}
