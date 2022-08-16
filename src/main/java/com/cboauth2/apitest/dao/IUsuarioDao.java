package com.cboauth2.apitest.dao;

import com.cboauth2.apitest.models.LoginForm;
import com.cboauth2.apitest.models.User;

import java.util.List;

public interface IUsuarioDao {

    User getUser(int id);
    List<User> getUsers();
    User createUser(User user);
    User editUser(int id);
    void deleteUser(int id);

    User login(LoginForm loginForm);
}
