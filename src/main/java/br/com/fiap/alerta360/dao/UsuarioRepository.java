package br.com.fiap.alerta360.dao;

import br.com.fiap.alerta360.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Camada DAO de Usuario. Spring Data JPA gera a implementacao em tempo de
 * execucao a partir desta interface (operacoes de acesso ao Oracle).
 */
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    Optional<Usuario> findByEmail(String email);

    boolean existsByEmail(String email);
}
