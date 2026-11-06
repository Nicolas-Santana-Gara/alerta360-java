package br.com.fiap.alerta360.model;

import br.com.fiap.alerta360.model.enums.TipoUsuario;
import jakarta.persistence.*;

import java.time.LocalDateTime;

/**
 * Cidadao ou administrador que utiliza a plataforma Alerta360.
 */
@Entity
@Table(name = "ALR_USUARIO")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_USUARIO")
    private Long id;

    @Column(name = "NOME", nullable = false, length = 120)
    private String nome;

    @Column(name = "EMAIL", nullable = false, unique = true, length = 150)
    private String email;

    @Column(name = "BAIRRO", length = 80)
    private String bairro;

    @Enumerated(EnumType.STRING)
    @Column(name = "TIPO_USUARIO", nullable = false, length = 20)
    private TipoUsuario tipoUsuario;

    @Column(name = "DATA_CADASTRO", nullable = false)
    private LocalDateTime dataCadastro;

    public Usuario() {
    }

    public Usuario(String nome, String email, String bairro, TipoUsuario tipoUsuario) {
        this.nome = nome;
        this.email = email;
        this.bairro = bairro;
        this.tipoUsuario = tipoUsuario;
    }

    @PrePersist
    protected void aoCriar() {
        this.dataCadastro = LocalDateTime.now();
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public TipoUsuario getTipoUsuario() {
        return tipoUsuario;
    }

    public void setTipoUsuario(TipoUsuario tipoUsuario) {
        this.tipoUsuario = tipoUsuario;
    }

    public LocalDateTime getDataCadastro() {
        return dataCadastro;
    }

    public void setDataCadastro(LocalDateTime dataCadastro) {
        this.dataCadastro = dataCadastro;
    }
}
