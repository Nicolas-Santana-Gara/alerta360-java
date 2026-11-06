package br.com.fiap.alerta360.dto;

import br.com.fiap.alerta360.model.Orientacao;
import jakarta.validation.constraints.NotBlank;

/**
 * Dados de Orientacao trocados pela API (entrada e saida).
 */
public class OrientacaoDTO {

    private Long id;

    @NotBlank(message = "O titulo e obrigatorio")
    private String titulo;

    @NotBlank(message = "O conteudo e obrigatorio")
    private String conteudo;

    private String linkOficial;

    public OrientacaoDTO() {
    }

    public OrientacaoDTO(Orientacao orientacao) {
        this.id = orientacao.getId();
        this.titulo = orientacao.getTitulo();
        this.conteudo = orientacao.getConteudo();
        this.linkOficial = orientacao.getLinkOficial();
    }

    public Orientacao paraEntidade() {
        return new Orientacao(this.titulo, this.conteudo, this.linkOficial);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getConteudo() {
        return conteudo;
    }

    public void setConteudo(String conteudo) {
        this.conteudo = conteudo;
    }

    public String getLinkOficial() {
        return linkOficial;
    }

    public void setLinkOficial(String linkOficial) {
        this.linkOficial = linkOficial;
    }
}
