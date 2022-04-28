/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.libros.egg.repositorios;

import com.libros.egg.entidades.Libro;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Bruno
 */
@Repository
public interface LibroRepositorio extends JpaRepository<Libro, String>{
    
//    @Query("SELECT l FROM Libro l WHERE l.libro.titulo = :titulo")
//    public List<Libro> listarLibros(@Param("titulo") String libro);
//    

    @Override
    public List<Libro> findAll();
    
    @Query("SELECT l FROM Libro l WHERE l.id = :id")
    public Libro buscarPorId(@Param("id") String id);
    
    
}
