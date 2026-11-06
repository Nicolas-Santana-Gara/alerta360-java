package br.com.fiap.alerta360.dto;

import br.com.fiap.alerta360.model.PontoCritico;
import jakarta.validation.constraints.NotBlank;

/**
 * Dados de PontoCritico trocados pela API (entrada e saida).
 */
public class PontoCriticoDTO {

    private Long id;

    @NotBlank(message = "O nome do ponto critico e obrigatorio")
    private String nome;

    @NotBlank(message = "O logradouro e obrigatorio")
    private String logradouro;

    private String bairro;
    private Double latitude;
    private Double longitude;
    private Integer distEstacaoBombeamentoM;

    public PontoCriticoDTO() {
    }

    public PontoCriticoDTO(PontoCritico pontoCritico) {
        this.id = pontoCritico.getId();
        this.nome = pontoCritico.getNome();
        this.logradouro = pontoCritico.getLogradouro();
        this.bairro = pontoCritico.getBairro();
        this.latitude = pontoCritico.getLatitude();
        this.longitude = pontoCritico.getLongitude();
        this.distEstacaoBombeamentoM = pontoCritico.getDistEstacaoBombeamentoM();
    }

    public PontoCritico paraEntidade() {
        PontoCritico pontoCritico = new PontoCritico();
        pontoCritico.setId(this.id);
        pontoCritico.setNome(this.nome);
        pontoCritico.setLogradouro(this.logradouro);
        pontoCritico.setBairro(this.bairro != null ? this.bairro : "Jardim Helena");
        pontoCritico.setLatitude(this.latitude);
        pontoCritico.setLongitude(this.longitude);
        pontoCritico.setDistEstacaoBombeamentoM(this.distEstacaoBombeamentoM);
        return pontoCritico;
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
}
