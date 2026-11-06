package br.com.fiap.alerta360.bo;

import br.com.fiap.alerta360.dao.UsuarioRepository;
import br.com.fiap.alerta360.dto.UsuarioRequestDTO;
import br.com.fiap.alerta360.exception.RecursoNaoEncontradoException;
import br.com.fiap.alerta360.exception.RegraNegocioException;
import br.com.fiap.alerta360.model.Usuario;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Camada BO (regras de negocio) de Usuario. Orquestra validacoes e chama a
 * camada DAO; os controllers nao acessam o repositorio diretamente.
 */
@Service
public class UsuarioBO {

    private final UsuarioRepository usuarioRepository;

    public UsuarioBO(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public List<Usuario> listarTodos() {
        return usuarioRepository.findAll();
    }

    public Usuario buscarPorId(Long id) {
        return usuarioRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException(
                        "Usuario nao encontrado para o id " + id));
    }

    public Usuario cadastrar(UsuarioRequestDTO dto) {
        if (usuarioRepository.existsByEmail(dto.getEmail())) {
            throw new RegraNegocioException("Ja existe um usuario cadastrado com este e-mail.");
        }
        Usuario usuario = new Usuario(dto.getNome(), dto.getEmail(), dto.getBairro(), dto.getTipoUsuario());
        return usuarioRepository.save(usuario);
    }

    public Usuario atualizar(Long id, UsuarioRequestDTO dto) {
        Usuario usuario = buscarPorId(id);

        boolean emailAlterado = !usuario.getEmail().equalsIgnoreCase(dto.getEmail());
        if (emailAlterado && usuarioRepository.existsByEmail(dto.getEmail())) {
            throw new RegraNegocioException("Ja existe um usuario cadastrado com este e-mail.");
        }

        usuario.setNome(dto.getNome());
        usuario.setEmail(dto.getEmail());
        usuario.setBairro(dto.getBairro());
        usuario.setTipoUsuario(dto.getTipoUsuario());
        return usuarioRepository.save(usuario);
    }

    public void excluir(Long id) {
        Usuario usuario = buscarPorId(id);
        usuarioRepository.delete(usuario);
    }
}
