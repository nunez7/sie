package edu.mx.utdelacosta.service;

import java.util.List;

import edu.mx.utdelacosta.model.Instrumento;

public interface IInstrumentoService {
 List<Instrumento> buscarTodos();
 Instrumento buscarPorId(Integer id);
}
