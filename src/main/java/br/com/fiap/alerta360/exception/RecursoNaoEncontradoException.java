package br.com.fiap.alerta360.exception;

/**
 * Lancada quando um recurso solicitado (usuario, ponto critico, ocorrencia,
 * orientacao) nao existe na base.
 */
public class RecursoNaoEncontradoException extends RuntimeException {

    public RecursoNaoEncontradoException(String mensagem) {
        super(mensagem);
    }
}
