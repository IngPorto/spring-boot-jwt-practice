package com.cboauth2.apitest.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
public class LoginForm {
    @Id
    @Getter @Setter @Column(name = "id")
    private int id;
    @Getter @Setter
    private String email;
    @Getter @Setter
    private String password;
}
