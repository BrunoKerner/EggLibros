/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.libros.egg.controladores;

import com.libros.egg.entidades.Autor;
import com.libros.egg.repositorios.AutorRepositorio;
import com.libros.egg.servicios.AutorServicio;
import java.util.Comparator;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author Bruno
 */
@Controller
@RequestMapping("/autor")
public class AutorController {

    private AutorServicio as;

    private AutorRepositorio ar;

//    @GetMapping("/listarautores")
//    public String listarautores(ModelMap modelo) {
//        List<Autor> autores = ar.findAll();
//        modelo.put("autores", autores);
//        modelo.addAttribute("byNombre", Comparator.comparing(Autor::getNombre));
//        return "listarautores.html";
//    }
//
//    @GetMapping("/agregarautor")
//    public String agregarautor() {
//        return "agregarautor.html";
//    }
//
//    @PostMapping("/agregarautor")
//    public String agregarautor(@RequestParam String nombre) throws Exception {
//        as.crearAutor(nombre, true);
//        return "agregarautor.html";
//    }
//
//    @GetMapping("/eliminarautor")
//    public String eliminarautor() {
//        return "eliminarautor.html";
//    }
//
//    @PostMapping("/eliminarautor")
//    public String eliminarautor(@RequestParam String nombre) throws Exception {
//        as.eliminarAutor(nombre);
//        return "eliminarautor.html";
//    }
//
//    @GetMapping("/autor/baja/{id}")
//    public String baja(@PathVariable String id) {
//
//        try {
//            as.baja(id);
//            return "redirect:/listarautores";
//        } catch (Exception e) {
//            return "redirect:/";
//        }
//
//    }
//
//    @GetMapping("/autor/alta/{id}")
//    public String alta(@PathVariable String id) {
//
//        try {
//            as.alta(id);
//            return "redirect:/listarautores";
//        } catch (Exception e) {
//            return "redirect:/";
//        }
//    }
//
//    @GetMapping("/modificarautor")
//    public String modificarautor() {
//        return "modificarautor.html";
//    }
//
//    @GetMapping("autor/{autorId}/editar")
//    public String editarAutor(@PathVariable String autorId, Model modelo) {
//        Autor autor = ar.buscarPorId(autorId);
//        modelo.addAttribute("autor", autor);
//        return "modificarautor.html";
//    }
//
//    @PostMapping("autor/{autorId}/editar")
//    public String editarAutor(@ModelAttribute Autor autor, @PathVariable String autorId, String nombre) throws Exception {
//        as.editarAutor(autorId, nombre);
//        return "modificarautor.html";
//    }

}
