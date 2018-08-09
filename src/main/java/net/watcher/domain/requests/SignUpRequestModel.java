package net.watcher.domain.requests;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class SignUpRequestModel {
    @NotNull
    @JsonProperty(value = "_firstName")
    private String firstName;
    @NotNull
    @Size(min = 3)
    @JsonProperty(value = "_lastName")
    private String lastName;
    @NotNull
    @Size(min = 3)
    @Size(max = 15)
    @JsonProperty(value = "_login")
    private String login;
    @NotNull
    @Size(min = 3)
    @Size(max = 25)
    @JsonProperty(value = "_password")
    private String password;
    @NotNull
    @Email
    @JsonProperty(value = "_email")
    private String email;
    @NotNull
    @JsonProperty(value = "_gender")
    private String gender;
    @NotNull
    @JsonProperty(value = "_dateOfBirth")
    private String dateOfBirth;
    @NotNull
    @JsonProperty(value = "_country")
    private String country;
    @JsonProperty(value = "_address")
    private String address;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
