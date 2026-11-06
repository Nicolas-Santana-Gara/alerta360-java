package br.com.fiap.alerta360.dto;

import br.com.fiap.alerta360.model.Usuario;
import br.com.fiap.alerta360.model.enums.TipoUsuario;

import java.time.LocalDateTime;

/**
 * Dados de Usuario expostos pela API (sem informacoes sensiveis).
 */
public class UsuarioResponseDTO {

    private Long id;
    private String nome;
    private String email;
    private String bairro;
    private TipoUsuario tipoUsuario;
    private LocalDateTime dataCadastro;

    public UsuarioResponseDTO(Usuario usuario) {
        this.id = usuario.getId();
        this.nome = usuario.getNome();
        this.email = usuario.getEmail();
        this.bairro = usuario.getBairro();
        this.tipoUsuario = usuario.getTipoUsuario();
        this.dataCadastro = usuario.getDataCadastro();
    }

    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getEmail() {
        return email;
    }

    public String getBairro() {
        return bairro;
    }

    public TipoUsuario getTipoUsuario() {
        return tipoUsuario;
    }

    public LocalDateTime getDataCadastro() {
        return dataCadastro;
    }
}
