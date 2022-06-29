package edu.mx.utdelacosta.controller;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import edu.mx.utdelacosta.model.Mail;
import edu.mx.utdelacosta.model.PasswordToken;
import edu.mx.utdelacosta.model.Persona;
import edu.mx.utdelacosta.model.Usuario;
import edu.mx.utdelacosta.service.EmailSenderService;
import edu.mx.utdelacosta.service.IPasswordTokenService;
import edu.mx.utdelacosta.service.IPersonaService;
import edu.mx.utdelacosta.service.IUsuariosService;

@Controller
public class LoginController {

	@Autowired
	private IPersonaService servicePersonas;

	@Autowired
	private IPasswordTokenService tokenService;

	@Autowired
	private IUsuariosService usuarioService;

	@Autowired
	private EmailSenderService emailService;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Value("${spring.mail.username}")
	private String EMAIL_DEFAULT;

	@GetMapping("/login")
	public String mostrarLogin() {
		return "login";
	}
	
	@GetMapping("/forgot-password")
	public String forgotPassword(Model model, Usuario usuario) {
		model.addAttribute("usuario", usuario);
		return "forgot-password";
	}

	@PostMapping("/forgot-password")
	public String forgotUserPassword(Model model, Usuario usuario, HttpServletRequest request) {
		Persona existingUser = servicePersonas.buscarPorEmail(usuario.getPersona().getEmail());

		if (existingUser != null) {
			Usuario usuarioT = usuarioService.buscarPorPersona(existingUser);
			PasswordToken token = new PasswordToken();
			token.setToken(UUID.randomUUID().toString());
			token.setUsuario(usuarioT);
			token.setFechaExpiracion(30);
			token.setFechaAlta(new Date());
			tokenService.guardar(token);

			// Create the email
			Mail mail = new Mail();
			mail.setDe(EMAIL_DEFAULT);
			mail.setPara(new String[] { usuarioT.getPersona().getEmail() });
			//Email title
			mail.setTitulo("Restablecer contraseña para sistema");
			//Variables a plantilla
			Map<String, Object> variables = new HashMap<>();
			variables.put("titulo", "Restablecer contraseña");
			String url = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort();
			variables.put("cuerpoCorreo",
					"Alguien solicitó restablecer la contraseña en su cuenta SIE. Si no lo solicitó, ignore este correo electrónico. "
					+ "Para completar el proceso de restablecimiento de contraseña, haga clic <a href='" + url
							+ "/siest/reset-password/" + token.getToken() + "' class='btn' target='_blank'>aquí </a>");
			mail.setVariables(variables);
			try {
				emailService.sendEmail(mail);
				model.addAttribute("typemessage", "alert-success");
				model.addAttribute("message",
						"Acabamos de enviarle un correo electrónico con instrucciones para restablecer su contraseña. Si no recibe un correo electrónico, intente nuevamente o comuníquese con nosotros.");
			} catch (MessagingException | IOException e) {
				// TODO Auto-generated catch block
				// e.printStackTrace();
				model.addAttribute("typemessage", "alert-warning");
				model.addAttribute("message", "No se pudo enviar el correo, intente de nuevo");
			}
			return "forgot-password";

		} else {
			model.addAttribute("typemessage", "alert-warning");
			model.addAttribute("message", "Este email no existe!");
			return "forgot-password";
		}
	}

	@GetMapping("/reset-password/{token}")
	public String recoverPassword(Model model, @PathVariable(name = "token", required = false) String token) {

		PasswordToken resetToken = tokenService.buscarPorToken(token);

		if (resetToken == null || resetToken.expiro()) {
			model.addAttribute("typemessage", "alert-warning");
			model.addAttribute("message", "El token ha expirado o no es válido, solicita uno nuevo.");
			return "login";
		}
		model.addAttribute("usuario", resetToken.getUsuario());
		model.addAttribute("token", resetToken.getToken());
		return "recover-password";
	}

	@PostMapping("/recover-password")
	public String recover(Model model, Usuario usuario) {
		return "recover-password";
	}

	@PostMapping("/reset-password")
	public String resetPassword(Model model, Usuario usuario, @RequestParam(required = false) String token) {
		if (usuario.getPersona().getEmail() != null && token!=null) {
			//Buscamos los implicados
			PasswordToken tokenS = tokenService.buscarPorToken(token);
			Usuario usuarioS = tokenS.getUsuario();
			String updatedPassword = passwordEncoder.encode(usuario.getContrasenia());
			usuarioService.actualizarContrasenia(usuarioS.getId(), updatedPassword);
			tokenService.eliminar(tokenS);
			model.addAttribute("typemessage", "alert-success");
			model.addAttribute("message", "Contraseña actualizada.");
			return "login";
		}
		model.addAttribute("typemessage", "alert-warning");
		model.addAttribute("message", "El link es inválido o no existe.");
		return "login";
	}

}
