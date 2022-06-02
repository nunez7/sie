package edu.mx.utdelacosta.controller;

import javax.servlet.http.HttpSession;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@PreAuthorize("hasRole('Administrador') OR hasRole('R.Humanos')")
@RequestMapping("/sesiones")
public class SesionesController {
	
	@GetMapping("/addsessionint/{nombre}/{val}")
    @ResponseBody
    public String addSessionInt(@PathVariable("nombre") String nombre, @PathVariable("val") int val, HttpSession session) {
    	if(nombre!=null) {
    		 session.setAttribute(nombre, null);
    		 session.setAttribute(nombre, val);
    	}
    	return "ok";
    }
	
	@GetMapping("/addsession/{nombre}/{val}")
    @ResponseBody
    public String addSession(@PathVariable("nombre") String nombre, @PathVariable("val") String val, HttpSession session) {
    	if(nombre!=null && val!=null) {
    		 session.setAttribute(nombre, null);
    		 session.setAttribute(nombre, val);
    	}
    	return "ok";
    }
}
