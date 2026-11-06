package br.com.fiap.alerta360.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Local da regiao de Jardim Helena (SP) historicamente sujeito a alagamento,
 * acompanhado pela comunidade atraves do Alerta360.
 */
@Entity
@Table(name = "ALR_PONTO_CRITICO")
public class PontoCritico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_PONTO_CRITICO")
    private Long id;

    @Column(name = "NOME", nullable = false, length = 120)
    private String nome;

    @Column(name = "LOGRADOURO", nullable = false, length = 150)
    private String logradouro;

    @Column(name = "BAIRRO", nullable = false, length = 80)
    private String bairro = "Jardim Helena";

    @Column(name = "LATITUDE")
    private Double latitude;

    @Column(name = "LONGITUDE")
    private Double longitude;

    @Column(name = "DIST_ESTACAO_BOMBEAMENTO_M")
    private Integer distEstacaoBombeamentoM;

    @OneToMany(mappedBy = "pontoCritico", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Ocorrencia> ocorrencias = new ArrayList<>();

    public PontoCritico() {
    }

    public PontoCritico(String nome, String logradouro, String bairro, Double latitude,
                         Double longitude, Integer distEstacaoBombeamentoM) {
        this.nome = nome;
        this.logradouro = logradouro;
        this.bairro = bairro;
        this.latitude = latitude;
        this.longitude = longitude;
        this.distEstacaoBombeamentoM = distEstacaoBombeamentoM;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getLogradouro() {
        return logradouro;
    }

    public void setLogradouro(String logradouro) {
        this.logradouro = logradouro;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Integer getDistEstacaoBombeamentoM() {
        return distEstacaoBombeamentoM;
    }

    public void setDistEstacaoBombeamentoM(Integer distEstacaoBombeamentoM) {
        this.distEstacaoBombeamentoM = distEstacaoBombeamentoM;
    }

    public List<Ocorrencia> getOcorrencias() {
        return ocorrencias;
    }

    public void setOcorrencias(List<Ocorrencia> ocorrencias) {
        this.ocorrencias = ocorrencias;
    }
}
