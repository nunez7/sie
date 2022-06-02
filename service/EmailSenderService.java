package edu.mx.utdelacosta.service;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import edu.mx.utdelacosta.model.Mail;

@Service
public class EmailSenderService {
	
	@Autowired
    private JavaMailSender emailSender;
	
    @Autowired
    private SpringTemplateEngine templateEngine;
    
    @Value("${siestapp.ruta.imagenes}")
	private String rutaImagenes;
    
    @Value("${siestapp.ruta.docs}")
    private String pdfDirectory;
    
    public void sendEmail(Mail mail) throws MessagingException, IOException {
        MimeMessage message = emailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message,true,
                StandardCharsets.UTF_8.name());
        //Recuperams las variables que van a la plantilla
        Context context = new Context();
        context.setVariables(mail.getVariables());
        //Plantilla
        String html = templateEngine.process("mail/template", context);
        //Datos para email, addAttachment adjunta archivos
        //helper.addAttachment("image1", new File(rutaImagenes+"/logo_ut.jpg"));
        //Las imagenes necesarias para la plantilla
        FileSystemResource file = new FileSystemResource(new File(rutaImagenes+"logo_ut.jpg"));
        FileSystemResource face = new FileSystemResource(new File(rutaImagenes+"facebook.png"));
        FileSystemResource twitter = new FileSystemResource(new File(rutaImagenes+"twitter.png"));
        FileSystemResource instagram = new FileSystemResource(new File(rutaImagenes+"instagram.png"));
        helper.setTo(mail.getPara());
        helper.setText(html, true);
        helper.setSubject(mail.getTitulo());
        helper.setFrom(mail.getDe());
        helper.addInline("logoImage", file);
        helper.addInline("logoFace", face);
        helper.addInline("logoTwitter", twitter);
        helper.addInline("logoInstagram", instagram);
        emailSender.send(message);
    }
    
    public void sendEmailWithFichaPago(Mail mail, String pdfname) throws MessagingException, IOException {
        MimeMessage message = emailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message,true,
                StandardCharsets.UTF_8.name());
        //Recuperams las variables que van a la plantilla
        Context context = new Context();
        context.setVariables(mail.getVariables());
        //Plantilla
        String html = templateEngine.process("mail/template", context);
        //Datos para email, addAttachment adjunta archivos
        helper.addAttachment(pdfname, new File(pdfDirectory+"/alumnos/pagos/"+pdfname));
        //Las imagenes necesarias para la plantilla
        FileSystemResource file = new FileSystemResource(new File(rutaImagenes+"logo_ut.jpg"));
        FileSystemResource face = new FileSystemResource(new File(rutaImagenes+"facebook.png"));
        FileSystemResource twitter = new FileSystemResource(new File(rutaImagenes+"twitter.png"));
        FileSystemResource instagram = new FileSystemResource(new File(rutaImagenes+"instagram.png"));
        helper.setTo(mail.getPara());
        helper.setText(html, true);
        helper.setSubject(mail.getTitulo());
        helper.setFrom(mail.getDe());
        helper.addInline("logoImage", file);
        helper.addInline("logoFace", face);
        helper.addInline("logoTwitter", twitter);
        helper.addInline("logoInstagram", instagram);
        emailSender.send(message);
    }


}
