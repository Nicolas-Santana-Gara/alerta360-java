package br.com.fiap.alerta360.exception;

/**
 * Lancada quando uma regra de negocio da camada BO e violada
 * (ex.: e-mail de usuario duplicado, transicao de status invalida).
 */
public class RegraNegocioException extends RuntimeException {

    public RegraNegocioException(String mensagem) {
        super(mensagem);
    }
}
