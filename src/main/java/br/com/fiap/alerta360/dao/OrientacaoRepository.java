package br.com.fiap.alerta360.dao;

import br.com.fiap.alerta360.model.Orientacao;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Camada DAO de Orientacao.
 */
public interface OrientacaoRepository extends JpaRepository<Orientacao, Long> {
}
