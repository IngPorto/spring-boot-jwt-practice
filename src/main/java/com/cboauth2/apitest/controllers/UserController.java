package com.cboauth2.apitest.controllers;

import com.cboauth2.apitest.dao.IUsuarioDao;
import com.cboauth2.apitest.models.User;
import com.cboauth2.apitest.utils.JWTUtil;
import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "api")
public class UserController {

    @Autowired
    private IUsuarioDao userDao;
    private List<User> users = new ArrayList<User>();
    @Autowired
    private JWTUtil jwtUtil;

    @RequestMapping(value = "prueba")
    public String prueba(){
        return "prueba";
    }

    @RequestMapping(value = "frutas")
    public List<String> frutas(){
        return List.of("Manzana", "Pera", "Durazno");
    }

    @RequestMapping(value = "user/{id}")
    public User getUser(@PathVariable int id){
        return users.get(id);
    }

    @RequestMapping(value = "user", method = RequestMethod.POST)
    public void createUser(@RequestBody User user){
        Argon2 argon2 = Argon2Factory.create(Argon2Factory.Argon2Types.ARGON2id);
        // i: Número de iteraciones,
        // i1: uso de memoria,
        // i2: paralelismo: hilos de procesos que se asignarán para realizar las iteraciones
        // s: texto que queremos hashear
        String hash = argon2.hash(1,1024, 1, user.getPassword());
        user.setPassword(hash);
        userDao.createUser(user);
        /*
        User user = new User();
        user.setId(users.size());
        user.setNombre("Juan");
        user.setApellido("Galindo");
        user.setEmail("juan.galindo@gmail.com");
        user.setTelefono("+57123456");
        user.setPassword("1234123");
        users.add(user);
        return user;
         */
    }

    //editar
    @RequestMapping(value = "edit/{id}", method = RequestMethod.PUT)
    public User editUser(@PathVariable int id){
        return users.get(id);
    }

    //eliminar
    @RequestMapping(value = "delete/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<String> deleteUser(@PathVariable int id, @RequestHeader(value = "Authorization") String token){
        if(!isValidToken(token)) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        userDao.deleteUser(id);
        return new ResponseEntity<>("user_deleted", HttpStatus.ACCEPTED);
        //User user = users.get(id);
        //users.remove(id);
        //return user;
    }

    //buscar uno
    @RequestMapping(value = "search/{id}")
    public User searchUser(@PathVariable int id){
        return users.get(id);
    }

    //buscar todos
    @RequestMapping(value = "search")
    public ResponseEntity<List<User>> searchUsers(@RequestHeader(value = "Authorization") String token){
        if(!isValidToken(token)) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        return new ResponseEntity<>(userDao.getUsers(), HttpStatus.ACCEPTED);
    }

    private boolean isValidToken(String token) {
        if(jwtUtil.getKey(token) == null) {
            return false;
        }
        return true;
    }

}
