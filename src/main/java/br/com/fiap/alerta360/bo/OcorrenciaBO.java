package br.com.fiap.alerta360.bo;

import br.com.fiap.alerta360.dao.OcorrenciaRepository;
import br.com.fiap.alerta360.dao.PontoCriticoRepository;
import br.com.fiap.alerta360.dao.UsuarioRepository;
import br.com.fiap.alerta360.dto.OcorrenciaRequestDTO;
import br.com.fiap.alerta360.exception.RecursoNaoEncontradoException;
import br.com.fiap.alerta360.exception.RegraNegocioException;
import br.com.fiap.alerta360.model.Ocorrencia;
import br.com.fiap.alerta360.model.PontoCritico;
import br.com.fiap.alerta360.model.Usuario;
import br.com.fiap.alerta360.model.enums.StatusOcorrencia;
import org.springframework.stereotype.Service;

import java.util.EnumMap;
import java.util.EnumSet;
import java.util.List;
import java.util.Map;

/**
 * Camada BO de Ocorrencia: valida as referencias (usuario e ponto critico)
 * e a transicao de status antes de delegar a persistencia a camada DAO.
 */
@Service
public class OcorrenciaBO {

    private static final Map<StatusOcorrencia, EnumSet<StatusOcorrencia>> TRANSICOES_VALIDAS =
            new EnumMap<>(StatusOcorrencia.class);

    static {
        TRANSICOES_VALIDAS.put(StatusOcorrencia.RECEBIDO, EnumSet.of(StatusOcorrencia.EM_ANALISE));
        TRANSICOES_VALIDAS.put(StatusOcorrencia.EM_ANALISE, EnumSet.of(StatusOcorrencia.ENCAMINHADO, StatusOcorrencia.CONCLUIDO));
        TRANSICOES_VALIDAS.put(StatusOcorrencia.ENCAMINHADO, EnumSet.of(StatusOcorrencia.CONCLUIDO));
        TRANSICOES_VALIDAS.put(StatusOcorrencia.CONCLUIDO, EnumSet.noneOf(StatusOcorrencia.class));
    }

    private final OcorrenciaRepository ocorrenciaRepository;
    private final UsuarioRepository usuarioRepository;
    private final PontoCriticoRepository pontoCriticoRepository;

    public OcorrenciaBO(OcorrenciaRepository ocorrenciaRepository,
                         UsuarioRepository usuarioRepository,
                         PontoCriticoRepository pontoCriticoRepository) {
        this.ocorrenciaRepository = ocorrenciaRepository;
        this.usuarioRepository = usuarioRepository;
        this.pontoCriticoRepository = pontoCriticoRepository;
    }

    public List<Ocorrencia> listarTodas() {
        return ocorrenciaRepository.findAll();
    }

    public Ocorrencia buscarPorId(Long id) {
        return ocorrenciaRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException(
                        "Ocorrencia nao encontrada para o id " + id));
    }

    public List<Ocorrencia> listarPorPontoCritico(Long pontoCriticoId) {
        return ocorrenciaRepository.findByPontoCriticoId(pontoCriticoId);
    }

    public List<Ocorrencia> listarPorStatus(StatusOcorrencia status) {
        return ocorrenciaRepository.findByStatus(status);
    }

    public Ocorrencia registrar(OcorrenciaRequestDTO dto) {
        Usuario usuario = usuarioRepository.findById(dto.getUsuarioId())
                .orElseThrow(() -> new RecursoNaoEncontradoException(
                        "Usuario nao encontrado para o id " + dto.getUsuarioId()));

        PontoCritico pontoCritico = pontoCriticoRepository.findById(dto.getPontoCriticoId())
                .orElseThrow(() -> new RecursoNaoEncontradoException(
                        "Ponto critico nao encontrado para o id " + dto.getPontoCriticoId()));

        Ocorrencia ocorrencia = new Ocorrencia(
                usuario, pontoCritico, dto.getCategoria(), dto.getDescricao(), dto.getFotoUrl());
        return ocorrenciaRepository.save(ocorrencia);
    }

    public Ocorrencia atualizarStatus(Long id, StatusOcorrencia novoStatus) {
        Ocorrencia ocorrencia = buscarPorId(id);
        StatusOcorrencia statusAtual = ocorrencia.getStatus();

        if (statusAtual == novoStatus) {
            return ocorrencia;
        }

        EnumSet<StatusOcorrencia> permitidos = TRANSICOES_VALIDAS.get(statusAtual);
        if (permitidos == null || !permitidos.contains(novoStatus)) {
            throw new RegraNegocioException(
                    "Nao e possivel mudar o status de " + statusAtual + " para " + novoStatus + ".");
        }

        ocorrencia.setStatus(novoStatus);
        return ocorrenciaRepository.save(ocorrencia);
    }

    public void excluir(Long id) {
        Ocorrencia ocorrencia = buscarPorId(id);
        ocorrenciaRepository.delete(ocorrencia);
    }
}
