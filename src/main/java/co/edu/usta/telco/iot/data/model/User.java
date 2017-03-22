package co.edu.usta.telco.iot.data.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.domain.Persistable;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Felipe on 27/09/2016.
 */
@Document
public class User implements Serializable, Persistable<String> {

    @Indexed(unique = true, sparse = true)
    private String token;

    private String password;

    @Indexed(unique = true)
    private String login;

    private boolean admin;

    @Override
    public String getId() {
        return login;
    }

    @JsonIgnore
    @Override
    public boolean isNew() {
        return true;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public boolean isAdmin() {
        return admin;
    }

    public void setAdmin(boolean admin) {
        this.admin = admin;
    }
}
