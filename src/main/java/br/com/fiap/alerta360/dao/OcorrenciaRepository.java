package br.com.fiap.alerta360.dao;

import br.com.fiap.alerta360.model.Ocorrencia;
import br.com.fiap.alerta360.model.enums.StatusOcorrencia;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Camada DAO de Ocorrencia.
 */
public interface OcorrenciaRepository extends JpaRepository<Ocorrencia, Long> {

    List<Ocorrencia> findByStatus(StatusOcorrencia status);

    List<Ocorrencia> findByPontoCriticoId(Long pontoCriticoId);

    List<Ocorrencia> findByUsuarioId(Long usuarioId);
}
