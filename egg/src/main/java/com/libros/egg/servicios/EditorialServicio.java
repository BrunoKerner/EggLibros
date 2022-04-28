/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.libros.egg.servicios;

import com.libros.egg.entidades.Editorial;
import com.libros.egg.repositorios.EditorialRepositorio;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Bruno
 */
@Service
public class EditorialServicio {
    
    @Autowired
    EditorialRepositorio er;
    
    @Transactional
    public Editorial crearEditorial(String nombre, boolean alta) throws Exception {
        
        validacion(nombre);
        
        Editorial editorial = new Editorial();
        editorial.setNombre(nombre);
        editorial.setAlta(alta);

        return er.save(editorial);

    }
    
    @Transactional
    public Editorial editorialAlta(String id) {

        Editorial entidad = er.getOne(id);

        entidad.setAlta(true);
        return er.save(entidad);
    }

    @Transactional
    public Editorial editorialBaja(String id) {

        Editorial entidad = er.getOne(id);

        entidad.setAlta(false);
        return er.save(entidad);
    }
    
    @Transactional
    public void editarEditorial(String idEditorial, String nombre){
        
        Optional<Editorial> respuesta = er.findById(idEditorial);
        
        if(respuesta.isPresent()){
            Editorial editorial = respuesta.get();
            if(editorial.getId().equals(idEditorial)){
                editorial.setNombre(nombre);
                
                er.save(editorial);
            }
        }
        
    }
    
    @Transactional
    public void validacion(String nombre) throws Exception {

        if (nombre == null || nombre.isEmpty() || nombre.contains("  ")) {
            throw new Exception("El nombre de la Editorial no puede ser nulo");
        }
    }
    
}
