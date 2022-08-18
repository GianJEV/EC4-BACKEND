package com.idat.ec3.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.idat.ec3.model.Sede;
import com.idat.ec3.service.SedeService;

@RestController
@RequestMapping("/sede")
public class SedeController {

	@Autowired
	SedeService service;
	
	//LISTAR
	@GetMapping
	public ResponseEntity<List<Sede>> listarSede(){
		List<Sede> obj = service.listarSede();
		return new ResponseEntity<List<Sede>>(obj, HttpStatus.OK);
	}
	
	//RequestBody se utiliza para almacenar objetos
	//ResponseEntity maneja respuestas de codigos http
	//INSERTAR
	@PostMapping
	public ResponseEntity<?> crearSede(@RequestBody Sede sede){
		
		Sede obj = null;
		//Creamos la colección de pares con el Map
		Map<String, Object> respuesta = new HashMap<>();
		
        try{
        	obj = service.crearSede(sede);
        } catch (DataAccessException e) {
        	respuesta.put("mensaje", "No se pudo insertar el objeto en la base de datos.");
        	respuesta.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(respuesta, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        respuesta.put("mensaje", "El objeto ha sido creado satisfactoriamente en la base de datos.");
        respuesta.put("sede", obj);
        return new ResponseEntity<Map<String, Object>>(respuesta, HttpStatus.CREATED);
		
	}
	
	//EDITAR
	@PutMapping
	public ResponseEntity<?> editarSede(@RequestBody Sede sede){
		
		Sede obj = null;
		Map<String, Object> respuesta = new HashMap<>();
		
        try{
        	obj = service.editarSede(sede);
        } catch (DataAccessException e) {
        	respuesta.put("mensaje", "No se pudo actualizar el objeto en la base de datos.");
        	respuesta.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(respuesta, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        respuesta.put("mensaje", "El objeto ha sido creado satisfactoriamente en la base de datos.");
        respuesta.put("sede", obj);
        return new ResponseEntity<Map<String, Object>>(respuesta, HttpStatus.ACCEPTED);
        
	}
	
	//Path Variable se utiliza para devolver objetos individuales o parte de sus datos
	//ELIMINAR
	@DeleteMapping("/{id}")
	public ResponseEntity<?> eliminarSede(@PathVariable Long id) throws Exception{
		
		Map<String, Object> respuesta = new HashMap<>();
		
        try{
        	service.eliminarSede(id);
        } catch (DataAccessException e) {
        	respuesta.put("mensaje", "No se pudo eliminar el objeto en la base de datos.");
        	respuesta.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(respuesta, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        respuesta.put("mensaje", "El objeto ha sido eliminado satisfactoriamente en la base de datos.");
        return new ResponseEntity<Map<String, Object>>(respuesta, HttpStatus.NO_CONTENT);
		
	}
	
	//LISTAR POR ID
	@GetMapping("/{id}")
	public ResponseEntity<?> listarPorId(@PathVariable Long id) throws Exception{
		
		Sede obj = null;
		Map<String, Object> respuesta = new HashMap<>();
		
		try {
			obj = service.ListarPorId(id);
		}catch (DataAccessException e) {
			// TODO: handle exception
			respuesta.put("mensaje", "Error. No se produjo la consulta en la base de datos.");
			respuesta.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(respuesta, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		if (obj == null) {
			respuesta.put("mensaje", "No se encontraron coincidencias.");
			return new ResponseEntity<Map<String, Object>>(respuesta, HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<Sede>(obj, HttpStatus.OK);
		
	}
	
	
}
