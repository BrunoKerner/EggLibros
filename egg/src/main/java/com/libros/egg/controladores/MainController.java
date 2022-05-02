/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.libros.egg.controladores;

import com.libros.egg.entidades.Autor;
import com.libros.egg.entidades.Editorial;
import com.libros.egg.entidades.Libro;
import com.libros.egg.entidades.Usuario;
import com.libros.egg.repositorios.AutorRepositorio;
import com.libros.egg.repositorios.EditorialRepositorio;
import com.libros.egg.repositorios.LibroRepositorio;
import com.libros.egg.repositorios.UsuarioRepositorio;
import com.libros.egg.servicios.AutorServicio;
import com.libros.egg.servicios.EditorialServicio;
import com.libros.egg.servicios.LibroServicio;
import com.libros.egg.servicios.UsuarioServicio;
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

/**
 *
 * @author Bruno
 */
@Controller
@RequestMapping("/")
public class MainController {

    @Autowired
    private AutorServicio as;

    @Autowired
    private EditorialServicio es;

    @Autowired
    private AutorRepositorio ar;

    @Autowired
    private EditorialRepositorio er;

    @Autowired
    private LibroServicio ls;

    @Autowired
    private LibroRepositorio lr;

    @Autowired
    private UsuarioServicio us;

    @Autowired
    private UsuarioRepositorio ur;

    @GetMapping("/")
    public String index() {
        return "index.html";
    }

    @GetMapping("/home")
    public String home(ModelMap modelo) {
        List<Usuario> usuariosLogin = ur.findLogin();
        List<Usuario> usuariosAdmin = ur.findAdmin();
        List<Usuario> usuarios = ur.findAll();
        List<Libro> libros = lr.findAll();
        List<Autor> autores = ar.findAll();
        List<Editorial> editoriales = er.findAll();
        modelo.put("usuariosLogin", usuariosLogin);
        modelo.put("usuariosAdmin", usuariosAdmin);
        modelo.put("usuarios", usuarios);
        modelo.put("libros", libros);
        modelo.put("autores", autores);
        modelo.put("editoriales", editoriales);
        return "home.html";
    }

    //CONTROLADORES USUARIO
    @GetMapping("/registrar")
    public String registrarUsuario(ModelMap modelo) {
        return "registrar.html";
    }

    @PostMapping("/registrar")
    public String registrarUsuario(ModelMap modelo, @RequestParam String nombre, @RequestParam String apellido, @RequestParam String email, @RequestParam String clave, @RequestParam Integer administrador) {
        try {
            us.crearUsuario(nombre, apellido, email, clave, true, administrador);
            modelo.put("exito", "Usuario registrado exitosamente");
            return "redirect:/home";
        } catch (Exception e) {
            modelo.put("error", e.getMessage());
            return "redirect:/registrar";
        }

    }
    
     @GetMapping("/ingresar")
    public String ingresarUsuario(ModelMap modelo) {
        return "ingresar.html";
    }
    
    @PostMapping("/ingresar")
    public String usuarioLogin(ModelMap modelo, @RequestParam String clave, @RequestParam String email) throws Exception {

        try {
            us.usuarioLogin(email, clave);
            return "redirect:/home";
        } catch (Exception e) {
            modelo.put("error", e.getMessage());
            return "/ingresar";
        }
        
    }
    
    @GetMapping("/usuario/logout/{id}")
    public String usuarioLogout(@PathVariable String id) {

        us.usuarioLogout(id);
        return "redirect:/home";

    }

    //CONTROLADORES LIBRO
    
    @GetMapping("/libros")
    public String libros(ModelMap modelo) {
        List<Libro> libros = lr.findAll();
        modelo.put("libros", libros);
        modelo.addAttribute("porAutor", Comparator.comparing(Libro::toStringAutor));
        return "libros.html";
    }

    @GetMapping("/libros2")
    public String libros2(ModelMap modelo) {
        List<Libro> libros = lr.findAll();
        modelo.put("libros", libros);
        modelo.addAttribute("porAutor", Comparator.comparing(Libro::toStringAutor));
        return "libros2.html";
    }

    @GetMapping("/agregarlibro")
    public String agregarlibro(ModelMap modelo) {
        List<Autor> autores = ar.findAll();
        List<Editorial> editoriales = er.findAll();
        modelo.put("autores", autores);
        modelo.put("editoriales", editoriales);
        return "agregarlibro.html";
    }

    @PostMapping("/agregarlibro")
    public String agregarLibro(ModelMap modelo, @RequestParam String titulo, Integer anio, @RequestParam String idAutor, @RequestParam String idEditorial) throws Exception {
        try {
            ls.crearLibro(titulo, anio, idAutor, idEditorial);
            modelo.put("exito", "Libro agregado exitosamente");
            return "agregarlibro.html";
        } catch (Exception e) {
            modelo.put("error", e.getMessage());
            return "agregarlibro.html";
        }

    }

    @GetMapping("/libro/baja/{id}")
    public String libroBaja(@PathVariable String id) {

        try {
            ls.libroBaja(id);
            return "redirect:/libros";
        } catch (Exception e) {
            return "redirect:/";
        }

    }

    @GetMapping("/libro/alta/{id}")
    public String libroAlta(@PathVariable String id) {

        try {
            ls.libroAlta(id);
            return "redirect:/libros";
        } catch (Exception e) {
            return "redirect:/";
        }
    }

    @GetMapping("/libro/ejemplaresMas/{id}")
    public String libroEjemplaresMas(@PathVariable String id) {

        try {
            ls.libroEjemplaresMas(id);
            return "redirect:/libros";
        } catch (Exception e) {
            return "redirect:/libros";
        }
    }

    @GetMapping("/libro/ejemplaresMenos/{id}")
    public String libroEjemplaresMenos(@PathVariable String id, ModelMap modelo) {

        try {
            ls.libroEjemplaresMenos(id);
            return "redirect:/libros";
        } catch (Exception e) {
            modelo.put("error", "La cantidad de ejemplares no puede ser negativa");
            return "redirect:/libros";
        }
    }

    @GetMapping("/libro/ejemplaresPrestadosMas/{id}")
    public String ejemplaresPrestadosMas(@PathVariable String id) {

        try {
            ls.libroEjemplaresPrestadosMas(id);
            return "redirect:/libros";
        } catch (Exception e) {
            return "redirect:/libros";
        }
    }

    @GetMapping("/libro/ejemplaresPrestadosMenos/{id}")
    public String ejemplaresPrestadosMenos(@PathVariable String id) {

        try {
            ls.libroEjemplaresPrestadosMenos(id);
            return "redirect:/libros";
        } catch (Exception e) {
            return "redirect:/libros";
        }
    }

    @GetMapping("/modificarlibro")
    public String modificarlibro() {
        return "modificarlibro.html";
    }

    @GetMapping("libro/{libroId}/editar")
    public String editarLibro(@PathVariable String libroId, Model modelo) {
        Libro libro = lr.buscarPorId(libroId);
        modelo.addAttribute("libro", libro);
        return "modificarlibro.html";
    }

    @PostMapping("libro/{libroId}/editar")
    public String editarLibro(@ModelAttribute Libro libro, @PathVariable String libroId, String titulo, Integer anio) throws Exception {
        ls.editarLibro(libroId, titulo, anio);
        return "modificarlibro.html";
    }

    //CONTROLADORES EDITORIAL
    @GetMapping("/agregareditorial")
    public String agregareditorial() {
        return "agregareditorial.html";
    }

    @PostMapping("/agregareditorial")
    public String agregareditorial(ModelMap modelo, @RequestParam String nombre) throws Exception {
        try {
            es.crearEditorial(nombre, true);
            modelo.put("exito", "Editorial agregada exitosamente");
            return "agregareditorial.html";
        } catch (Exception e) {
            modelo.put("error", e.getMessage());
            return "agregareditorial.html";
        }
    }

    @GetMapping("/listareditoriales")
    public String listareditoriales(ModelMap modelo) {
        List<Editorial> editoriales = er.findAll();
        modelo.put("editoriales", editoriales);
        modelo.addAttribute("porNombre", Comparator.comparing(Editorial::getNombre));
        return "listareditoriales.html";
    }

    @GetMapping("/editorial/baja/{id}")
    public String editorialBaja(@PathVariable String id) {

        try {
            es.editorialBaja(id);
            return "redirect:/listareditoriales";
        } catch (Exception e) {
            return "redirect:/";
        }

    }

    @GetMapping("/editorial/alta/{id}")
    public String editorialAlta(@PathVariable String id) {

        try {
            es.editorialAlta(id);
            return "redirect:/listareditoriales";
        } catch (Exception e) {
            return "redirect:/";
        }
    }

    @GetMapping("/modificareditorial")
    public String modificareditorial() {
        return "modificareditorial.html";
    }

    @GetMapping("editorial/{editorialId}/editar")
    public String editarEditorial(@PathVariable String editorialId, Model modelo) {
        Editorial editorial = er.buscarPorId(editorialId);
        modelo.addAttribute("editorial", editorial);
        return "modificareditorial.html";
    }

    @PostMapping("editorial/{editorialId}/editar")
    public String editarEditorial(@ModelAttribute Editorial editorial, @PathVariable String editorialId, String nombre) throws Exception {
        es.editarEditorial(editorialId, nombre);
        return "modificareditorial.html";
    }

    //CONTROLADORES AUTOR
    @GetMapping("/listarautores")
    public String listarautores(ModelMap modelo) {
        List<Autor> autores = ar.findAll();
        modelo.put("autores", autores);
        modelo.addAttribute("porNombre", Comparator.comparing(Autor::getNombre));
        return "listarautores.html";
    }

    @GetMapping("/agregarautor")
    public String agregarautor() {
        return "agregarautor.html";
    }

    @PostMapping("/agregarautor")
    public String agregarautor(@RequestParam String nombre, ModelMap modelo) {
        try {
            as.crearAutor(nombre, true);
            modelo.put("exito", "Autor agregado exitosamente");
            return "agregarautor.html";
        } catch (Exception e) {
            modelo.put("error", e.getMessage());
            return "agregarautor.html";
        }

    }

    @GetMapping("/eliminarautor")
    public String eliminarautor() {
        return "eliminarautor.html";
    }

    @PostMapping("/eliminarautor")
    public String eliminarautor(@RequestParam String nombre) throws Exception {
        as.eliminarAutor(nombre);
        return "eliminarautor.html";
    }

    @GetMapping("/autor/baja/{id}")
    public String baja(@PathVariable String id) {

        try {
            as.baja(id);
            return "redirect:/listarautores";
        } catch (Exception e) {
            return "redirect:/";
        }

    }

    @GetMapping("/autor/alta/{id}")
    public String alta(@PathVariable String id) {

        try {
            as.alta(id);
            return "redirect:/listarautores";
        } catch (Exception e) {
            return "redirect:/";
        }
    }

    @GetMapping("/modificarautor")
    public String modificarautor() {
        return "modificarautor.html";
    }

    @GetMapping("autor/{autorId}/editar")
    public String editarAutor(@PathVariable String autorId, Model modelo) {
        Autor autor = ar.buscarPorId(autorId);
        modelo.addAttribute("autor", autor);
        return "modificarautor.html";
    }

    @PostMapping("autor/{autorId}/editar")
    public String editarAutor(@ModelAttribute Autor autor, @PathVariable String autorId, String nombre) throws Exception {
        as.editarAutor(autorId, nombre);
        return "modificarautor.html";
    }
}
