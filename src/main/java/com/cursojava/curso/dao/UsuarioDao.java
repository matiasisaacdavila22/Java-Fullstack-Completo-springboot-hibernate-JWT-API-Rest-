package com.cursojava.curso.dao;

import com.cursojava.curso.models.Usuario;

import java.util.List;

public interface UsuarioDao {
    List<Usuario> getAllUsers();

    void removeUser(long id);

    void registrar(Usuario usuario);

    Usuario optenerUserForCredentials(Usuario usuario);
}
