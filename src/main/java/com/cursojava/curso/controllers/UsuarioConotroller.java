package com.cursojava.curso.controllers;


import com.cursojava.curso.dao.UsuarioDao;
import com.cursojava.curso.models.Usuario;
import com.cursojava.curso.utils.JWTUtil;
import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class UsuarioConotroller {

    @Autowired
    private UsuarioDao usuarioDao;

    @Autowired
    JWTUtil jwtUtil;

    @RequestMapping(value = "api/usuarios", method = RequestMethod.POST)
    public void registrarUsuario(@RequestBody Usuario usuario){
        Argon2 argon2 = Argon2Factory.create(Argon2Factory.Argon2Types.ARGON2d.ARGON2id);
        String hash = argon2.hash(1, 1024,1,usuario.getPassword());
        usuario.setPassword(hash);
        usuarioDao.registrar(usuario);
    }

    @RequestMapping(value = "api/usuario/{id}")
    public Usuario getUsuarioById(@PathVariable String id){
        Usuario usuario = new Usuario();
        usuario.setNombre("lucas");
        usuario.setApellido("hoy");
        usuario.setEmail("lucas@gmail.com");
        usuario.setTelefono("123456");
        return usuario;
    }

    private  boolean validarToken(String token){
        String userId = jwtUtil.getKey(token);
         return userId != null;
    }


    @RequestMapping(value = "api/usuarios")
    public List<Usuario> getAllUsers(@RequestHeader(value = "Authorization") String token){
            if(!validarToken(token)){
                return null;
            }
         return usuarioDao.getAllUsers();
    }

    @RequestMapping(value = "api/usuarios/{id}", method = RequestMethod.DELETE)
    public void removeUsuario(@PathVariable long id, @RequestHeader(value = "Authorization") String token){
        if(!validarToken(token)){
            return;
        }
        usuarioDao.removeUser(id);
    }
}
