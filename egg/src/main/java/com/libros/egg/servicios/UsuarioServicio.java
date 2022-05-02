/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.libros.egg.servicios;

import com.libros.egg.entidades.Usuario;
import com.libros.egg.repositorios.UsuarioRepositorio;
import java.util.List;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Bruno
 */
@Service
public class UsuarioServicio {

    @Autowired
    private UsuarioRepositorio ur;

    @Transactional
    public Usuario crearUsuario(String nombre, String apellido, String email, String clave, boolean login, Integer administrador) throws Exception {

        validacion(nombre, apellido, email, clave);

        Usuario usuario = new Usuario();
        usuario.setNombre(nombre);
        usuario.setApellido(apellido);
        usuario.setEmail(email);
        usuario.setClave(clave);
        usuario.setLogin(true);
        if (administrador == 1) {
            usuario.setAdministrador(true);
        } else {
            usuario.setAdministrador(false);
        }

        return ur.save(usuario);
    }

    @Transactional
    public void validacion(String nombre, String apellido, String email, String clave) throws Exception {

        if (nombre == null || nombre.isEmpty() || nombre.contains("  ")) {
            throw new Exception("El nombre del usuario no puede ser nulo");
        }

        if (apellido == null || apellido.isEmpty() || apellido.contains("  ")) {
            throw new Exception("El apellido del usuario no puede ser nulo");
        }

        if (email == null || email.isEmpty() || email.contains("  ")) {
            throw new Exception("El email del usuario no puede ser nulo");
        }

        if (clave == null || clave.isEmpty() || clave.contains("  ")) {
            throw new Exception("La contraseña del usuario no puede ser nula");
        }
    }

    @Transactional
    public Usuario usuarioLogin(String email, String clave) throws Exception {

        Usuario u = ur.buscarPorEmail(email);

        if (u != null) {
            if (clave.equals(u.getClave())) {
                u.setLogin(true);
                return ur.save(u);
            } else {
                throw new Exception("Clave incorrecta");
            }

        } else {
            throw new Exception("El usuario no esta registrado");
        }
    }

    @Transactional
    public Usuario usuarioLogout(String id) {

        Usuario entidad = ur.getOne(id);

        entidad.setLogin(false);
        return ur.save(entidad);
    }

}
