package edu.mx.utdelacosta.controller;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import edu.mx.utdelacosta.model.Cliente;
import edu.mx.utdelacosta.model.Estado;
import edu.mx.utdelacosta.model.Localidad;
import edu.mx.utdelacosta.model.Municipio;
import edu.mx.utdelacosta.model.dto.ClienteDTO;
import edu.mx.utdelacosta.service.IClienteService;

@Controller
@PreAuthorize("hasRole('Administrador') and hasRole('Caja')")
@RequestMapping("/cliente")
public class ClienteController {
	
	@Autowired
	private IClienteService clienteService;
	
	@GetMapping("/search/{id}")
	public ResponseEntity<Cliente> get(@PathVariable("id") int id){
		Cliente cliente = clienteService.buscarPorId(id);
		if (cliente == null) {
	        return ResponseEntity.notFound().build();
	    } else {
	        return ResponseEntity.ok(cliente);
	    }
	}
	
	@PostMapping(path="/save", consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public String guardar(@RequestBody ClienteDTO cliente) {
		Cliente c = new Cliente();
		//para deitar el cliente
		if(cliente.getId() > 0) {
			c = clienteService.buscarPorId(cliente.getId());
			c.setNombre(cliente.getNombre());
			c.setContacto(cliente.getContacto());
			c.setDireccion(cliente.getDireccion());
			c.setEmail(cliente.getEmail());
			c.setEstado(new Estado(cliente.getEstado()));
			c.setMunicipio(new Municipio(cliente.getMunicipio()));
			c.setLocalidad(new Localidad(cliente.getLocalidad()));
			c.setEstatus(cliente.getEstatus());
			c.setRfc(cliente.getRfc());
			c.setTelefono1(cliente.getTelefono1());
			c.setTelefono2(cliente.getTelefono2());
			c.setTipo(cliente.getTipo());
			c.setTamanio(cliente.getTamanio());
			c.setSector(cliente.getSector());
			clienteService.guardar(c);
			return "up";
		}
		//cuando es cliente nuevo
		c.setNombre(cliente.getNombre());
		//para extraer la clave consecutiva
		String clave = clienteService.ultimaClave();
		c.setClave(clave);
		c.setContacto(cliente.getContacto());
		c.setDireccion(cliente.getDireccion());
		c.setEmail(cliente.getEmail());
		c.setEstado(new Estado(cliente.getEstado()));
		c.setMunicipio(new Municipio(cliente.getMunicipio()));
		c.setLocalidad(new Localidad(cliente.getLocalidad()));
		c.setEstatus(cliente.getEstatus());
		c.setRfc(cliente.getRfc());
		c.setTelefono1(cliente.getTelefono1());
		c.setTelefono2(cliente.getTelefono2());
		c.setTipo(cliente.getTipo());
		c.setTamanio(cliente.getTamanio());
		c.setSector(cliente.getSector());
		c.setFechaAlta(new Date());
		clienteService.guardar(c);
		return "ok";
	}
}
