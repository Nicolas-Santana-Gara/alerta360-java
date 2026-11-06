package br.com.fiap.alerta360.controller;

import br.com.fiap.alerta360.bo.PontoCriticoBO;
import br.com.fiap.alerta360.dto.PontoCriticoDTO;
import br.com.fiap.alerta360.model.PontoCritico;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Endpoints de PontoCritico (pontos de alagamento monitorados em Jardim
 * Helena - SP).
 */
@RestController
@RequestMapping("/api/pontos-criticos")
public class PontoCriticoController {

    private final PontoCriticoBO pontoCriticoBO;

    public PontoCriticoController(PontoCriticoBO pontoCriticoBO) {
        this.pontoCriticoBO = pontoCriticoBO;
    }

    @GetMapping
    public ResponseEntity<List<PontoCriticoDTO>> listar() {
        List<PontoCriticoDTO> pontos = pontoCriticoBO.listarTodos().stream()
                .map(PontoCriticoDTO::new)
                .toList();
        return ResponseEntity.ok(pontos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PontoCriticoDTO> buscarPorId(@PathVariable Long id) {
        PontoCritico pontoCritico = pontoCriticoBO.buscarPorId(id);
        return ResponseEntity.ok(new PontoCriticoDTO(pontoCritico));
    }

    @PostMapping
    public ResponseEntity<PontoCriticoDTO> cadastrar(@RequestBody @Valid PontoCriticoDTO dto) {
        PontoCritico pontoCritico = pontoCriticoBO.cadastrar(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(new PontoCriticoDTO(pontoCritico));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PontoCriticoDTO> atualizar(@PathVariable Long id,
                                                       @RequestBody @Valid PontoCriticoDTO dto) {
        PontoCritico pontoCritico = pontoCriticoBO.atualizar(id, dto);
        return ResponseEntity.ok(new PontoCriticoDTO(pontoCritico));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        pontoCriticoBO.excluir(id);
        return ResponseEntity.noContent().build();
    }
}
