package br.com.fiap.alerta360.controller;

import br.com.fiap.alerta360.bo.UsuarioBO;
import br.com.fiap.alerta360.dto.UsuarioRequestDTO;
import br.com.fiap.alerta360.dto.UsuarioResponseDTO;
import br.com.fiap.alerta360.model.Usuario;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Endpoints de Usuario (cidadaos e administradores do Alerta360).
 */
@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    private final UsuarioBO usuarioBO;

    public UsuarioController(UsuarioBO usuarioBO) {
        this.usuarioBO = usuarioBO;
    }

    @GetMapping
    public ResponseEntity<List<UsuarioResponseDTO>> listar() {
        List<UsuarioResponseDTO> usuarios = usuarioBO.listarTodos().stream()
                .map(UsuarioResponseDTO::new)
                .toList();
        return ResponseEntity.ok(usuarios);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UsuarioResponseDTO> buscarPorId(@PathVariable Long id) {
        Usuario usuario = usuarioBO.buscarPorId(id);
        return ResponseEntity.ok(new UsuarioResponseDTO(usuario));
    }

    @PostMapping
    public ResponseEntity<UsuarioResponseDTO> cadastrar(@RequestBody @Valid UsuarioRequestDTO dto) {
        Usuario usuario = usuarioBO.cadastrar(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(new UsuarioResponseDTO(usuario));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UsuarioResponseDTO> atualizar(@PathVariable Long id,
                                                          @RequestBody @Valid UsuarioRequestDTO dto) {
        Usuario usuario = usuarioBO.atualizar(id, dto);
        return ResponseEntity.ok(new UsuarioResponseDTO(usuario));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        usuarioBO.excluir(id);
        return ResponseEntity.noContent().build();
    }
}
