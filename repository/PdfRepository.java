package edu.mx.utdelacosta.repository;

import java.util.Map;

public interface PdfRepository {
	 public void generarPdf(String templateName, Map<String, Object> data, String pdfFileName);
}
