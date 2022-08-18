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

import com.idat.ec3.model.Carrera;
import com.idat.ec3.service.CarreraService;

@RestController
@RequestMapping("/carrera")
public class CarreraController {

	@Autowired
	CarreraService service;
	
	//LISTAR
	@GetMapping
	public ResponseEntity<List<Carrera>> listarCarrera(){
		List<Carrera> obj = service.listarCarrera();
		return new ResponseEntity<List<Carrera>>(obj, HttpStatus.OK);
	}
	
	//RequestBody se utiliza para almacenar objetos
	//ResponseEntity maneja respuestas de codigos http
	//INSERTAR
	@PostMapping
	public ResponseEntity<?> crearCarrera(@RequestBody Carrera carrera){
		
		Carrera obj = null;
		//Creamos la colección de pares con el Map
		Map<String, Object> respuesta = new HashMap<>();
		
        try{
        	obj = service.crearCarrera(carrera);
        } catch (DataAccessException e) {
        	respuesta.put("mensaje", "No se pudo insertar el objeto en la base de datos.");
        	respuesta.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(respuesta, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        respuesta.put("mensaje", "El objeto ha sido creado satisfactoriamente en la base de datos.");
        respuesta.put("carrera", obj);
        return new ResponseEntity<Map<String, Object>>(respuesta, HttpStatus.CREATED);
		
	}
	
	//EDITAR
	@PutMapping
	public ResponseEntity<?> editarCarrera(@RequestBody Carrera carrera){
		
		Carrera obj = null;
		Map<String, Object> respuesta = new HashMap<>();
		
        try{
        	obj = service.editarCarrera(carrera);
        } catch (DataAccessException e) {
        	respuesta.put("mensaje", "No se pudo actualizar el objeto en la base de datos.");
        	respuesta.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(respuesta, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        respuesta.put("mensaje", "El objeto ha sido creado satisfactoriamente en la base de datos.");
        respuesta.put("carrera", obj);
        return new ResponseEntity<Map<String, Object>>(respuesta, HttpStatus.ACCEPTED);
        
	}
	
	
	//Path Variable se utiliza para devolver objetos individuales o parte de sus datos
	//ELIMINAR
	@DeleteMapping("/{id}")
	public ResponseEntity<?> eliminarCarrera(@PathVariable Long id) throws Exception{
		
		Map<String, Object> respuesta = new HashMap<>();
		
        try{
        	service.eliminarCarrera(id);
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
		
		Carrera obj = null;
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
		
		return new ResponseEntity<Carrera>(obj, HttpStatus.OK);
		
	}
}
