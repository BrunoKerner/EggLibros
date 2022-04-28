/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.libros.egg.servicios;

import com.libros.egg.entidades.Autor;
import com.libros.egg.entidades.Editorial;
import com.libros.egg.entidades.Libro;
import com.libros.egg.repositorios.AutorRepositorio;
import com.libros.egg.repositorios.EditorialRepositorio;
import com.libros.egg.repositorios.LibroRepositorio;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Bruno
 */
@Service
public class LibroServicio {

    @Autowired
    LibroRepositorio lr;

    @Autowired
    AutorRepositorio ar;

    @Autowired
    EditorialRepositorio er;

    @Transactional
    public Libro crearLibro(String titulo, Integer anio, String idAutor, String idEditorial) throws Exception {

        validacion(titulo);
        
        Autor autor = ar.getOne(idAutor);

        Editorial editorial = er.getOne(idEditorial);

        Libro libro = new Libro();

        long randomIsbn = generarIsbn();
        libro.setIsbn(randomIsbn);
        libro.setTitulo(titulo);
        libro.setAnio(anio);
        libro.setEjemplares(0);
        libro.setEjemplaresPrestados(0);
        libro.setEjemplaresRestantes(0);
        libro.setAlta(true);
        libro.setAutor(autor);
        libro.setEditorial(editorial);

        return lr.save(libro);

    }

    @Transactional
    public void eliminarLibro(Libro libro) throws Exception {

        Libro l = lr.buscarPorId(libro.getId());

        if (l != null) {
            lr.delete(l);
        } else {
            throw new Exception("No se encontro ese libro en la Base de Datos");
        }

    }

    @Transactional
    public Libro modificarLibro(Libro libro) throws Exception {

        Libro l = lr.buscarPorId(libro.getId());

        if (l != null) {
            validacion(libro.getTitulo());
            l.setTitulo(libro.getTitulo());
            l.setAnio(libro.getAnio());
            l.setEjemplares(libro.getEjemplares());
            return lr.save(l);
        } else {
            throw new Exception("No se encontro ese libro en la Base de Datos");
        }

    }

    @Transactional
    public void editarLibro(String idLibro, String titulo, Integer anio) {

        Libro libro = lr.getOne(idLibro);

        libro.setTitulo(titulo);
        libro.setAnio(anio);

        lr.save(libro);

    }

    @Transactional
    public void validacion(String titulo) throws Exception {

        if (titulo == null || titulo.isEmpty() || titulo.contains("  ")) {
            throw new Exception("El titulo del Libro no puede ser nulo");
        }
    }

    @Transactional
    public long generarIsbn() {
        long min = 1000000000000L;
        long max = 9999999999999L;

        long randomLong = min + (long) (Math.random() * (max - min));

        return randomLong;
    }

    @Transactional
    public Libro libroAlta(String id) {

        Libro entidad = lr.getOne(id);

        entidad.setAlta(true);
        return lr.save(entidad);
    }

    @Transactional
    public Libro libroBaja(String id) {

        Libro entidad = lr.getOne(id);

        entidad.setAlta(false);
        return lr.save(entidad);
    }

    @Transactional
    public Libro libroEjemplaresMas(String id) {

        Libro entidad = lr.getOne(id);

        entidad.setEjemplares(entidad.getEjemplares() + 1);
        entidad.setEjemplaresRestantes(entidad.getEjemplaresRestantes() + 1);
        return lr.save(entidad);
    }

    @Transactional

    public Libro libroEjemplaresMenos(String id) throws Exception {

        Libro entidad = lr.getOne(id);

        if (entidad.getEjemplares() > 0 && entidad.getEjemplaresPrestados() < entidad.getEjemplares()) {
            entidad.setEjemplares(entidad.getEjemplares() - 1);
            entidad.setEjemplaresRestantes(entidad.getEjemplaresRestantes() - 1);
            return lr.save(entidad);
        } else {
            throw new Exception("La cantidad de ejemplares no puede ser negativa o todavia hay algunos ejemplares prestados");
        }

    }

    @Transactional
    public Libro libroEjemplaresPrestadosMas(String id) throws Exception {

        Libro entidad = lr.getOne(id);

        if (entidad.getEjemplaresRestantes() > 0) {
            entidad.setEjemplaresPrestados(entidad.getEjemplaresPrestados() + 1);
            entidad.setEjemplaresRestantes(entidad.getEjemplaresRestantes() - 1);
            return lr.save(entidad);
        } else {
            throw new Exception("No se pueden prestar mas libros de los hay restantes");
        }

    }

    @Transactional

    public Libro libroEjemplaresPrestadosMenos(String id) throws Exception {

        Libro entidad = lr.getOne(id);

        if (entidad.getEjemplaresPrestados() > 0) {
            entidad.setEjemplaresPrestados(entidad.getEjemplaresPrestados() - 1);
            entidad.setEjemplaresRestantes(entidad.getEjemplaresRestantes() + 1);
            return lr.save(entidad);
        } else {
            throw new Exception("Ya no hay libros para devolver");

        }
    }

}
