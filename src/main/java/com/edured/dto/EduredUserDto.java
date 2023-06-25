package com.edured.dto;

import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EduredUserDto {
    private long id;
    @Size(min = 3, max = 25, message = "Name should be 3-25 Characters !")
    private String name;
    @NotEmpty(message = "please fill this field !")
    @Email(message = "Enter a valid email!")
    @Size(min = 7, max = 40, message = "email should be 7-40 Characters only !")
    private String email;
    private String username;
    private String role;
    @Size(min = 5, max = 15, message = "Password should be 5-15 Characters !")
    private String password;
    @NotEmpty(message = "re enter the password!")
    private String confirmPassword;
    private String registeredDate;
    
    @AssertTrue(message = "Passwords do not match")
    public boolean isPasswordMatching() {
        if (password == null || confirmPassword == null) {
            return false;
        }
        return password.equals(confirmPassword);
    }
}
