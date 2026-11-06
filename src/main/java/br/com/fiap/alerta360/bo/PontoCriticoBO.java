package br.com.fiap.alerta360.bo;

import br.com.fiap.alerta360.dao.PontoCriticoRepository;
import br.com.fiap.alerta360.dto.PontoCriticoDTO;
import br.com.fiap.alerta360.exception.RecursoNaoEncontradoException;
import br.com.fiap.alerta360.model.PontoCritico;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Camada BO de PontoCritico.
 */
@Service
public class PontoCriticoBO {

    private final PontoCriticoRepository pontoCriticoRepository;

    public PontoCriticoBO(PontoCriticoRepository pontoCriticoRepository) {
        this.pontoCriticoRepository = pontoCriticoRepository;
    }

    public List<PontoCritico> listarTodos() {
        return pontoCriticoRepository.findAll();
    }

    public PontoCritico buscarPorId(Long id) {
        return pontoCriticoRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException(
                        "Ponto critico nao encontrado para o id " + id));
    }

    public PontoCritico cadastrar(PontoCriticoDTO dto) {
        PontoCritico pontoCritico = dto.paraEntidade();
        pontoCritico.setId(null);
        return pontoCriticoRepository.save(pontoCritico);
    }

    public PontoCritico atualizar(Long id, PontoCriticoDTO dto) {
        PontoCritico pontoCritico = buscarPorId(id);
        pontoCritico.setNome(dto.getNome());
        pontoCritico.setLogradouro(dto.getLogradouro());
        pontoCritico.setBairro(dto.getBairro() != null ? dto.getBairro() : pontoCritico.getBairro());
        pontoCritico.setLatitude(dto.getLatitude());
        pontoCritico.setLongitude(dto.getLongitude());
        pontoCritico.setDistEstacaoBombeamentoM(dto.getDistEstacaoBombeamentoM());
        return pontoCriticoRepository.save(pontoCritico);
    }

    public void excluir(Long id) {
        PontoCritico pontoCritico = buscarPorId(id);
        pontoCriticoRepository.delete(pontoCritico);
    }
}
