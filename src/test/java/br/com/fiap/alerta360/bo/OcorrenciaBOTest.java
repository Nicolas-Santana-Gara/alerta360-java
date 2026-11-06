package br.com.fiap.alerta360.bo;

import br.com.fiap.alerta360.dao.OcorrenciaRepository;
import br.com.fiap.alerta360.dao.PontoCriticoRepository;
import br.com.fiap.alerta360.dao.UsuarioRepository;
import br.com.fiap.alerta360.exception.RegraNegocioException;
import br.com.fiap.alerta360.model.Ocorrencia;
import br.com.fiap.alerta360.model.PontoCritico;
import br.com.fiap.alerta360.model.Usuario;
import br.com.fiap.alerta360.model.enums.CategoriaOcorrencia;
import br.com.fiap.alerta360.model.enums.StatusOcorrencia;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

/**
 * Testa a regra de negocio de transicao de status da camada BO, que e a
 * regra mais sensivel do dominio: nao e permitido "voltar" ou "pular"
 * etapas da triagem de uma ocorrencia.
 */
@ExtendWith(MockitoExtension.class)
class OcorrenciaBOTest {

    @Mock
    private OcorrenciaRepository ocorrenciaRepository;
    @Mock
    private UsuarioRepository usuarioRepository;
    @Mock
    private PontoCriticoRepository pontoCriticoRepository;

    private OcorrenciaBO ocorrenciaBO;
    private Ocorrencia ocorrencia;

    @BeforeEach
    void setUp() {
        ocorrenciaBO = new OcorrenciaBO(ocorrenciaRepository, usuarioRepository, pontoCriticoRepository);

        Usuario usuario = new Usuario("Maria Teste", "maria@teste.com", "Jardim Helena", null);
        PontoCritico ponto = new PontoCritico("Rua Teste", "Rua Teste", "Jardim Helena", -23.0, -46.0, 100);

        ocorrencia = new Ocorrencia(usuario, ponto, CategoriaOcorrencia.ALAGAMENTO, "teste", null);
        ocorrencia.setId(1L);
        ocorrencia.setStatus(StatusOcorrencia.RECEBIDO);

        when(ocorrenciaRepository.findById(1L)).thenReturn(Optional.of(ocorrencia));
    }

    @Test
    void devePermitirTransicaoValidaDeRecebidoParaEmAnalise() {
        when(ocorrenciaRepository.save(ocorrencia)).thenReturn(ocorrencia);

        Ocorrencia atualizada = ocorrenciaBO.atualizarStatus(1L, StatusOcorrencia.EM_ANALISE);

        assertThat(atualizada.getStatus()).isEqualTo(StatusOcorrencia.EM_ANALISE);
    }

    @Test
    void naoDevePermitirPularEtapaDeRecebidoParaConcluido() {
        assertThatThrownBy(() -> ocorrenciaBO.atualizarStatus(1L, StatusOcorrencia.CONCLUIDO))
                .isInstanceOf(RegraNegocioException.class);
    }

    @Test
    void naoDevePermitirVoltarStatusDeEmAnaliseParaRecebido() {
        ocorrencia.setStatus(StatusOcorrencia.EM_ANALISE);

        assertThatThrownBy(() -> ocorrenciaBO.atualizarStatus(1L, StatusOcorrencia.RECEBIDO))
                .isInstanceOf(RegraNegocioException.class);
    }
}
