package br.com.fiap.alerta360.model;

import jakarta.persistence.*;

/**
 * Conteudo informativo de prevencao exibido no painel do Alerta360
 * (orientacoes de preparacao para chuva e contatos oficiais).
 */
@Entity
@Table(name = "ALR_ORIENTACAO")
public class Orientacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_ORIENTACAO")
    private Long id;

    @Column(name = "TITULO", nullable = false, length = 150)
    private String titulo;

    @Column(name = "CONTEUDO", nullable = false, length = 1000)
    private String conteudo;

    @Column(name = "LINK_OFICIAL", length = 300)
    private String linkOficial;

    public Orientacao() {
    }

    public Orientacao(String titulo, String conteudo, String linkOficial) {
        this.titulo = titulo;
        this.conteudo = conteudo;
        this.linkOficial = linkOficial;
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
