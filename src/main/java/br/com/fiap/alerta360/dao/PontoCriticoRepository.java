package br.com.fiap.alerta360.dao;

import br.com.fiap.alerta360.model.PontoCritico;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Camada DAO de PontoCritico.
 */
public interface PontoCriticoRepository extends JpaRepository<PontoCritico, Long> {

    List<PontoCritico> findByBairroIgnoreCase(String bairro);
}
