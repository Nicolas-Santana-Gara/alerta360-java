package br.com.fiap.alerta360.controller;

import br.com.fiap.alerta360.bo.OrientacaoBO;
import br.com.fiap.alerta360.dto.OrientacaoDTO;
import br.com.fiap.alerta360.model.Orientacao;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Endpoints de Orientacao (painel informativo de prevencao do Alerta360).
 */
@RestController
@RequestMapping("/api/orientacoes")
public class OrientacaoController {

    private final OrientacaoBO orientacaoBO;

    public OrientacaoController(OrientacaoBO orientacaoBO) {
        this.orientacaoBO = orientacaoBO;
    }

    @GetMapping
    public ResponseEntity<List<OrientacaoDTO>> listar() {
        List<OrientacaoDTO> orientacoes = orientacaoBO.listarTodas().stream()
                .map(OrientacaoDTO::new)
                .toList();
        return ResponseEntity.ok(orientacoes);
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrientacaoDTO> buscarPorId(@PathVariable Long id) {
        Orientacao orientacao = orientacaoBO.buscarPorId(id);
        return ResponseEntity.ok(new OrientacaoDTO(orientacao));
    }

    @PostMapping
    public ResponseEntity<OrientacaoDTO> cadastrar(@RequestBody @Valid OrientacaoDTO dto) {
        Orientacao orientacao = orientacaoBO.cadastrar(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(new OrientacaoDTO(orientacao));
    }

    @PutMapping("/{id}")
    public ResponseEntity<OrientacaoDTO> atualizar(@PathVariable Long id,
                                                     @RequestBody @Valid OrientacaoDTO dto) {
        Orientacao orientacao = orientacaoBO.atualizar(id, dto);
        return ResponseEntity.ok(new OrientacaoDTO(orientacao));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        orientacaoBO.excluir(id);
        return ResponseEntity.noContent().build();
    }
}
