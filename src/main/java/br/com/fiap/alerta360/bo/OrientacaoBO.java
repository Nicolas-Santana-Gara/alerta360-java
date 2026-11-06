package br.com.fiap.alerta360.bo;

import br.com.fiap.alerta360.dao.OrientacaoRepository;
import br.com.fiap.alerta360.dto.OrientacaoDTO;
import br.com.fiap.alerta360.exception.RecursoNaoEncontradoException;
import br.com.fiap.alerta360.model.Orientacao;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Camada BO de Orientacao (conteudo do painel informativo do Alerta360).
 */
@Service
public class OrientacaoBO {

    private final OrientacaoRepository orientacaoRepository;

    public OrientacaoBO(OrientacaoRepository orientacaoRepository) {
        this.orientacaoRepository = orientacaoRepository;
    }

    public List<Orientacao> listarTodas() {
        return orientacaoRepository.findAll();
    }

    public Orientacao buscarPorId(Long id) {
        return orientacaoRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException(
                        "Orientacao nao encontrada para o id " + id));
    }

    public Orientacao cadastrar(OrientacaoDTO dto) {
        return orientacaoRepository.save(dto.paraEntidade());
    }

    public Orientacao atualizar(Long id, OrientacaoDTO dto) {
        Orientacao orientacao = buscarPorId(id);
        orientacao.setTitulo(dto.getTitulo());
        orientacao.setConteudo(dto.getConteudo());
        orientacao.setLinkOficial(dto.getLinkOficial());
        return orientacaoRepository.save(orientacao);
    }

    public void excluir(Long id) {
        Orientacao orientacao = buscarPorId(id);
        orientacaoRepository.delete(orientacao);
    }
}
