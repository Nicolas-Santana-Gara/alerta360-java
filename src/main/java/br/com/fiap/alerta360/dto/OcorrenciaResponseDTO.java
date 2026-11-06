package br.com.fiap.alerta360.dto;

import br.com.fiap.alerta360.model.Ocorrencia;
import br.com.fiap.alerta360.model.enums.CategoriaOcorrencia;
import br.com.fiap.alerta360.model.enums.StatusOcorrencia;

import java.time.LocalDateTime;

/**
 * Dados de Ocorrencia expostos pela API, com os dados do usuario e do ponto
 * critico ja resolvidos (sem precisar de outra chamada).
 */
public class OcorrenciaResponseDTO {

    private Long id;
    private Long usuarioId;
    private String usuarioNome;
    private Long pontoCriticoId;
    private String pontoCriticoNome;
    private CategoriaOcorrencia categoria;
    private String descricao;
    private String fotoUrl;
    private LocalDateTime dataHora;
    private StatusOcorrencia status;

    public OcorrenciaResponseDTO(Ocorrencia ocorrencia) {
        this.id = ocorrencia.getId();
        this.usuarioId = ocorrencia.getUsuario().getId();
        this.usuarioNome = ocorrencia.getUsuario().getNome();
        this.pontoCriticoId = ocorrencia.getPontoCritico().getId();
        this.pontoCriticoNome = ocorrencia.getPontoCritico().getNome();
        this.categoria = ocorrencia.getCategoria();
        this.descricao = ocorrencia.getDescricao();
        this.fotoUrl = ocorrencia.getFotoUrl();
        this.dataHora = ocorrencia.getDataHora();
        this.status = ocorrencia.getStatus();
    }

    public Long getId() {
        return id;
    }

    public Long getUsuarioId() {
        return usuarioId;
    }

    public String getUsuarioNome() {
        return usuarioNome;
    }

    public Long getPontoCriticoId() {
        return pontoCriticoId;
    }

    public String getPontoCriticoNome() {
        return pontoCriticoNome;
    }

    public CategoriaOcorrencia getCategoria() {
        return categoria;
    }

    public String getDescricao() {
        return descricao;
    }

    public String getFotoUrl() {
        return fotoUrl;
    }

    public LocalDateTime getDataHora() {
        return dataHora;
    }

    public StatusOcorrencia getStatus() {
        return status;
    }
}
