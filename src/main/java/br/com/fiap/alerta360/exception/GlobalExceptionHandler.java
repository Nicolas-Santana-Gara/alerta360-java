package br.com.fiap.alerta360.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Camada de tratamento de excecoes da API: converte cada tipo de erro em
 * uma resposta HTTP consistente, sem expor detalhes internos da aplicacao.
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(RecursoNaoEncontradoException.class)
    public ResponseEntity<ApiErro> tratarRecursoNaoEncontrado(RecursoNaoEncontradoException ex,
                                                                HttpServletRequest request) {
        ApiErro erro = new ApiErro(
                HttpStatus.NOT_FOUND.value(),
                "Recurso nao encontrado",
                ex.getMessage(),
                request.getRequestURI()
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(erro);
    }

    @ExceptionHandler(RegraNegocioException.class)
    public ResponseEntity<ApiErro> tratarRegraNegocio(RegraNegocioException ex,
                                                        HttpServletRequest request) {
        ApiErro erro = new ApiErro(
                HttpStatus.UNPROCESSABLE_ENTITY.value(),
                "Regra de negocio violada",
                ex.getMessage(),
                request.getRequestURI()
        );
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(erro);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> tratarValidacao(MethodArgumentNotValidException ex,
                                                                 HttpServletRequest request) {
        Map<String, Object> corpo = new LinkedHashMap<>();
        corpo.put("timestamp", java.time.LocalDateTime.now());
        corpo.put("status", HttpStatus.BAD_REQUEST.value());
        corpo.put("erro", "Dados invalidos");
        corpo.put("caminho", request.getRequestURI());

        Map<String, String> camposInvalidos = new LinkedHashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(
                fieldError -> camposInvalidos.put(fieldError.getField(), fieldError.getDefaultMessage())
        );
        corpo.put("campos", camposInvalidos);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(corpo);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiErro> tratarErroGenerico(Exception ex, HttpServletRequest request) {
        ApiErro erro = new ApiErro(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "Erro interno",
                "Ocorreu um erro inesperado ao processar a requisicao.",
                request.getRequestURI()
        );
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(erro);
    }
}
