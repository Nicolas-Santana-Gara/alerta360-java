package br.com.fiap.alerta360.model;

import br.com.fiap.alerta360.model.enums.CategoriaOcorrencia;
import br.com.fiap.alerta360.model.enums.StatusOcorrencia;
import jakarta.persistence.*;

import java.time.LocalDateTime;

/**
 * Relato de um cidadao sobre um problema de alagamento em um ponto critico
 * de Jardim Helena (SP).
 */
@Entity
@Table(name = "ALR_OCORRENCIA")
public class Ocorrencia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_OCORRENCIA")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "ID_USUARIO", nullable = false)
    private Usuario usuario;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "ID_PONTO_CRITICO", nullable = false)
    private PontoCritico pontoCritico;

    @Enumerated(EnumType.STRING)
    @Column(name = "CATEGORIA", nullable = false, length = 30)
    private CategoriaOcorrencia categoria;

    @Column(name = "DESCRICAO", length = 500)
    private String descricao;

    @Column(name = "FOTO_URL", length = 300)
    private String fotoUrl;

    @Column(name = "DATA_HORA", nullable = false)
    private LocalDateTime dataHora;

    @Enumerated(EnumType.STRING)
    @Column(name = "STATUS", nullable = false, length = 20)
    private StatusOcorrencia status = StatusOcorrencia.RECEBIDO;

    public Ocorrencia() {
    }

    public Ocorrencia(Usuario usuario, PontoCritico pontoCritico, CategoriaOcorrencia categoria,
                       String descricao, String fotoUrl) {
        this.usuario = usuario;
        this.pontoCritico = pontoCritico;
        this.categoria = categoria;
        this.descricao = descricao;
        this.fotoUrl = fotoUrl;
    }

    @PrePersist
    protected void aoCriar() {
        this.dataHora = LocalDateTime.now();
        if (this.status == null) {
            this.status = StatusOcorrencia.RECEBIDO;
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public PontoCritico getPontoCritico() {
        return pontoCritico;
    }

    public void setPontoCritico(PontoCritico pontoCritico) {
        this.pontoCritico = pontoCritico;
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

    public LocalDateTime getDataHora() {
        return dataHora;
    }

    public void setDataHora(LocalDateTime dataHora) {
        this.dataHora = dataHora;
    }

    public StatusOcorrencia getStatus() {
        return status;
    }

    public void setStatus(StatusOcorrencia status) {
        this.status = status;
    }
}
