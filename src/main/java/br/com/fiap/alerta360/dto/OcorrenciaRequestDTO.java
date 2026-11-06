package br.com.fiap.alerta360.dto;

import br.com.fiap.alerta360.model.enums.CategoriaOcorrencia;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

/**
 * Dados recebidos do cidadao para registrar uma ocorrencia de alagamento.
 */
public class OcorrenciaRequestDTO {

    @NotNull(message = "O id do usuario e obrigatorio")
    private Long usuarioId;

    @NotNull(message = "O id do ponto critico e obrigatorio")
    private Long pontoCriticoId;

    @NotNull(message = "A categoria da ocorrencia e obrigatoria")
    private CategoriaOcorrencia categoria;

    @Size(max = 500, message = "A descricao deve ter no maximo 500 caracteres")
    private String descricao;

    private String fotoUrl;

    public Long getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Long usuarioId) {
        this.usuarioId = usuarioId;
    }

    public Long getPontoCriticoId() {
        return pontoCriticoId;
    }

    public void setPontoCriticoId(Long pontoCriticoId) {
        this.pontoCriticoId = pontoCriticoId;
    }

    public CategoriaOcorrencia getCategoria() {
        return categoria;
    }

    public void setCategoria(CategoriaOcorrencia categoria) {
        this.categoria = categoria;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getFotoUrl() {
        return fotoUrl;
    }

    public void setFotoUrl(String fotoUrl) {
        this.fotoUrl = fotoUrl;
    }
}
