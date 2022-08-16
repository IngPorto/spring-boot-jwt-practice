package com.cboauth2.apitest.controllers;

import com.cboauth2.apitest.dao.IUsuarioDao;
import com.cboauth2.apitest.models.ApiResponse;
import com.cboauth2.apitest.models.LoginForm;
import com.cboauth2.apitest.models.User;
import com.cboauth2.apitest.utils.JWTUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping(value = "auth")
public class AuthController {
    @Autowired
    private IUsuarioDao userDao;

    // Autowired porque el jwt es un componente. Siendo un componente puedo
    // usar la anotación @Value para cargar información del application.properties.
    // Queda cargado en memoria con la información lista para usar (inyección de
    // dependencias)
    @Autowired
    private JWTUtil jwtUtil;

    @RequestMapping(value = "login", method = RequestMethod.POST)
    public ResponseEntity<ApiResponse> login(@RequestBody LoginForm loginForm){
        User user = userDao.login(loginForm);
        if (user != null){
            String tokenJWT = jwtUtil.create(String.valueOf(user.getId()), user.getEmail());
            ApiResponse apiResponse = new ApiResponse();
            apiResponse.setOk(true);
            apiResponse.setStatus(HttpStatus.ACCEPTED.value());
            apiResponse.setTokenJWT(tokenJWT);
            apiResponse.setData(user);
            return new ResponseEntity<>(apiResponse, HttpStatus.ACCEPTED);
        } else {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }
}
