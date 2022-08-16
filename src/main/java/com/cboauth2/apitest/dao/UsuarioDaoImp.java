package com.cboauth2.apitest.dao;

import com.cboauth2.apitest.models.LoginForm;
import com.cboauth2.apitest.models.User;
import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
@Transactional
public class UsuarioDaoImp implements IUsuarioDao{

    @PersistenceContext
    private EntityManager entityManager;


    @Override
    public User getUser(int id) {
        return null;
    }

    @Override
    public List<User> getUsers() {
        String query = "FROM User";
        return entityManager.createQuery(query).getResultList();
    }

    @Override
    public User createUser(User user) {
        entityManager.merge(user);
        return null;
    }

    @Override
    public User editUser(int id) {
        return null;
    }

    @Override
    public void deleteUser(int id) {
        User usuario = entityManager.find(User.class, id);
        entityManager.remove(usuario);
    }

    @Override
    public User login(LoginForm loginForm) {
        try {
            User usuario = (User) entityManager.createQuery("FROM User WHERE email = :email")
                    .setParameter("email", loginForm.getEmail())
                    .getSingleResult();

            Argon2 argon2 = Argon2Factory.create(Argon2Factory.Argon2Types.ARGON2id);
            // se compara la contraseña del usuario registrado (hashed) con la original
            System.out.println("*****************************************");
            System.out.println(loginForm.getPassword());
            System.out.println("*****************************************");
            System.out.println(usuario.getPassword());
            System.out.println("*****************************************");

            boolean isCorrect = argon2.verify(usuario.getPassword(), loginForm.getPassword());
            if(isCorrect){
                User newUser = new User(usuario);
                newUser.setPassword(null);
                return newUser;
            } else {
                return null;
            }
        }catch (Exception exception){
            exception.printStackTrace();
            return null;
        }
    }
}
