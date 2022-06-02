package edu.mx.utdelacosta.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@PreAuthorize("hasRole('Administrador') and hasRole('Caja')")
@RequestMapping("/caja")
public class CajaController {
	
	private String NOMBRE_UT = "UNIVERSIDAD TECNOLÓGICA DE NAYARIT";
	
	@GetMapping("/archivosBanco")
	public String archivosBanco() {
		return "caja/archivosBanco";
	}
	
	@GetMapping("/pagosPersona")
	public String pagosPersona() {
		return "caja/pagosPersona";
	}
	
	@GetMapping("/pagosEmpresa")
	public String pagosEmpresa() {
		return "caja/pagosEmpresa";
	}
	
	@GetMapping("/folios")
	public String folios() {
		return "caja/folios";
	}
	
	@GetMapping("/clientes")
	public String clientes() {
		return "caja/clientes";
	}
	
	@GetMapping("/conceptos")
	public String conceptos() {
		return "caja/conceptos";
	}
	
	@GetMapping("/descuentos")
	public String descuentos() {
		return "caja/descuentos";
	}
	
	@GetMapping("/pagosCuatrimestre")
	public String pagosCuatrimestre() {
		return "caja/pagosCuatrimestre";
	}
	
	@GetMapping("/reportes")
	public String reportes() {
		return "caja/reportes";
	}
	
	@GetMapping("/reporteCorteCaja")
	public String reporteCorteCaja(Model model) {
		model.addAttribute("NOMBRE_UT", NOMBRE_UT);
		return "caja/reporteCorteCaja";
	}
	
	@GetMapping("/reporteCorteCajaDetallado")
	public String reporteCorteCajaDetallado(Model model) {
		model.addAttribute("NOMBRE_UT", NOMBRE_UT);
		return "caja/reporteCorteCajaDetallado";
	}
	
	@GetMapping("/reportePoliza")
	public String reportePoliza(Model model) {
		model.addAttribute("NOMBRE_UT", NOMBRE_UT);
		return "caja/reportePoliza";
	}
	
	@GetMapping("/reporteAdeudos")
	public String reporteAdeudos(Model model) {
		model.addAttribute("NOMBRE_UT", NOMBRE_UT);
		return "caja/reporteAdeudos";
	}
	
	@GetMapping("/manual")
	public String manual() {
		return "caja/manual";
	}
	
	@GetMapping("/cobro/{id}")
	public String cobro(@PathVariable("id") int id) {
		//El id que se recibe es el id de persona
		return "caja/plantillaFactura";
	}
	
	@GetMapping("/cobroempresa/{id}")
	public String cobroempresa(@PathVariable("id") int id) {
		//El id que se recibe es el id de persona
		return "caja/plantillaFacturaEmpresa";
	}
}
