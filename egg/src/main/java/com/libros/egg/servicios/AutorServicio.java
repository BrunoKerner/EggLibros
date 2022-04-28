/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.libros.egg.servicios;

import com.libros.egg.entidades.Autor;
import com.libros.egg.repositorios.AutorRepositorio;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;

/**
 *
 * @author Bruno
 */
@Service
public class AutorServicio {

    @Autowired
    private AutorRepositorio ar;

    @Transactional
    public Autor crearAutor(String nombre, boolean alta) throws Exception {

        validacion(nombre);

        Autor autor = new Autor();
        autor.setNombre(nombre);
        autor.setAlta(alta);

        return ar.save(autor);
    }

//    @Transactional
//    public Autor modificarAutor(String id) throws Exception {
//
//        Autor a = ar.buscarPorId(id);
//
//        if (a != null) {
//            a.setNombre(a.getNombre());
//            return ar.save(a);
//        }else{
//            throw new Exception("No se encontro el autor en la Base de Datos");
//        }
//
//    }
    @Transactional
    public void editarAutor(String idAutor, String nombre) {

        Optional<Autor> respuesta = ar.findById(idAutor);

        if (respuesta.isPresent()) {
            Autor autor = respuesta.get();
            if (autor.getId().equals(idAutor)) {
                autor.setNombre(nombre);

                ar.save(autor);
            }
        }

    }

//    @Transactional
//    public void eliminarAutor(Autor autor) throws Exception {
//
//        Autor a = ar.buscarPorId(autor.getId());
//
//        if (a != null) {
//            ar.delete(a);
//        } else {
//            throw new Exception("No se encontro ese libro en la Base de Datos");
//        }
//
//    }
    @Transactional
    public void eliminarAutor(String nombre) throws Exception {

        Autor a = ar.buscarPorNombre(nombre);

        ar.delete(a);

    }

    @Transactional
    public Autor alta(String id) {

        Autor entidad = ar.getOne(id);

        entidad.setAlta(true);
        return ar.save(entidad);
    }

    @Transactional
    public Autor baja(String id) {

        Autor entidad = ar.getOne(id);

        entidad.setAlta(false);
        return ar.save(entidad);
    }

    @Transactional
    public void validacion(String nombre) throws Exception {

        if (nombre == null || nombre.isEmpty() || nombre.contains("  ")) {
            throw new Exception("El nombre del Autor no puede ser nulo");
        }
    }
}
