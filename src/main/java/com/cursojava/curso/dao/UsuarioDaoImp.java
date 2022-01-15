package com.cursojava.curso.dao;

import com.cursojava.curso.models.Usuario;
import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
@Transactional
public class UsuarioDaoImp  implements UsuarioDao{

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @Transactional
    public List<Usuario> getAllUsers() {

        String query = "FROM Usuario";
        return entityManager.createQuery(query).getResultList();
      }

    @Override
    public void removeUser(long id) {
       Usuario usuario = entityManager.find(Usuario.class, id);
       System.out.println(usuario);
       entityManager.remove(usuario);
     }

    @Override
    public void registrar(Usuario usuario) {
        entityManager.merge(usuario);
    }

    @Override
    public Usuario optenerUserForCredentials(Usuario usuario) {

            String query = "FROM Usuario WHERE email = :email";
            System.out.println(usuario);
            List<Usuario> listUsers = entityManager.createQuery(query)
                    .setParameter("email", usuario.getEmail())
                    .getResultList();
        if(listUsers.isEmpty()){
             return null;
             }
            String passwordHash = listUsers.get(0).getPassword();
            Argon2 argon2 = Argon2Factory.create(Argon2Factory.Argon2Types.ARGON2id);
            if(argon2.verify(passwordHash, usuario.getPassword())){
                return listUsers.get(0);
            }else{
                return null;
            }
        }
    }
