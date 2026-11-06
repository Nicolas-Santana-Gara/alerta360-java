package br.com.fiap.alerta360.controller;

import br.com.fiap.alerta360.bo.OcorrenciaBO;
import br.com.fiap.alerta360.dto.OcorrenciaRequestDTO;
import br.com.fiap.alerta360.dto.OcorrenciaResponseDTO;
import br.com.fiap.alerta360.dto.OcorrenciaStatusUpdateDTO;
import br.com.fiap.alerta360.model.Ocorrencia;
import br.com.fiap.alerta360.model.enums.StatusOcorrencia;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Endpoints de Ocorrencia: o coracao do Alerta360 - relato, consulta e
 * acompanhamento de status de alagamentos em Jardim Helena - SP.
 */
@RestController
@RequestMapping("/api/ocorrencias")
public class OcorrenciaController {

    private final OcorrenciaBO ocorrenciaBO;

    public OcorrenciaController(OcorrenciaBO ocorrenciaBO) {
        this.ocorrenciaBO = ocorrenciaBO;
    }

    @GetMapping
    public ResponseEntity<List<OcorrenciaResponseDTO>> listar(
            @RequestParam(required = false) StatusOcorrencia status,
            @RequestParam(required = false) Long pontoCriticoId) {

        List<Ocorrencia> ocorrencias;
        if (status != null) {
            ocorrencias = ocorrenciaBO.listarPorStatus(status);
        } else if (pontoCriticoId != null) {
            ocorrencias = ocorrenciaBO.listarPorPontoCritico(pontoCriticoId);
        } else {
            ocorrencias = ocorrenciaBO.listarTodas();
        }

        List<OcorrenciaResponseDTO> resposta = ocorrencias.stream()
                .map(OcorrenciaResponseDTO::new)
                .toList();
        return ResponseEntity.ok(resposta);
    }

    @GetMapping("/{id}")
    public ResponseEntity<OcorrenciaResponseDTO> buscarPorId(@PathVariable Long id) {
        Ocorrencia ocorrencia = ocorrenciaBO.buscarPorId(id);
        return ResponseEntity.ok(new OcorrenciaResponseDTO(ocorrencia));
    }

    @PostMapping
    public ResponseEntity<OcorrenciaResponseDTO> registrar(@RequestBody @Valid OcorrenciaRequestDTO dto) {
        Ocorrencia ocorrencia = ocorrenciaBO.registrar(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(new OcorrenciaResponseDTO(ocorrencia));
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<OcorrenciaResponseDTO> atualizarStatus(@PathVariable Long id,
                                                                   @RequestBody @Valid OcorrenciaStatusUpdateDTO dto) {
        Ocorrencia ocorrencia = ocorrenciaBO.atualizarStatus(id, dto.getStatus());
        return ResponseEntity.ok(new OcorrenciaResponseDTO(ocorrencia));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        ocorrenciaBO.excluir(id);
        return ResponseEntity.noContent().build();
    }
}
