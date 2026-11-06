package br.com.fiap.alerta360;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * Verifica que o contexto Spring sobe corretamente (todas as camadas
 * conseguem ser instanciadas e injetadas) usando o banco H2 de teste.
 */
@SpringBootTest
class Alerta360ApplicationTests {

    @Test
    void contextLoads() {
    }
}
