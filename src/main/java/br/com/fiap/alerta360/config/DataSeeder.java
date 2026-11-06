package br.com.fiap.alerta360.config;

import br.com.fiap.alerta360.dao.OrientacaoRepository;
import br.com.fiap.alerta360.dao.PontoCriticoRepository;
import br.com.fiap.alerta360.model.Orientacao;
import br.com.fiap.alerta360.model.PontoCritico;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * Popula alguns pontos criticos e orientacoes de Jardim Helena na primeira
 * execucao, apenas para facilitar a demonstracao/teste da API. Nao roda se
 * ja houver dados (nao sobrescreve nada em execucoes seguintes).
 */
@Component
public class DataSeeder implements CommandLineRunner {

    private final PontoCriticoRepository pontoCriticoRepository;
    private final OrientacaoRepository orientacaoRepository;

    public DataSeeder(PontoCriticoRepository pontoCriticoRepository,
                       OrientacaoRepository orientacaoRepository) {
        this.pontoCriticoRepository = pontoCriticoRepository;
        this.orientacaoRepository = orientacaoRepository;
    }

    @Override
    public void run(String... args) {
        if (pontoCriticoRepository.count() == 0) {
            pontoCriticoRepository.save(new PontoCritico(
                    "Rua Vila Jacui", "Rua Vila Jacui", "Jardim Helena",
                    -23.4857, -46.4788, 180));
            pontoCriticoRepository.save(new PontoCritico(
                    "Av. Jardim Helena", "Avenida Jardim Helena", "Jardim Helena",
                    -23.4901, -46.4731, 420));
            pontoCriticoRepository.save(new PontoCritico(
                    "Praca Jardim Helena", "Praca Jardim Helena", "Jardim Helena",
                    -23.4879, -46.4756, 300));
        }

        if (orientacaoRepository.count() == 0) {
            orientacaoRepository.save(new Orientacao(
                    "Em caso de emergencia",
                    "Em situacoes de risco imediato (agua subindo rapido, risco a vida), "
                            + "procure a Defesa Civil pelo numero 199 e os servicos de emergencia.",
                    "https://www.gov.br/defesacivil"));
            orientacaoRepository.save(new Orientacao(
                    "Antes da chuva forte",
                    "Evite deixar o carro em pontos historicamente alagados, guarde documentos "
                            + "e objetos de valor em local alto e acompanhe o Alerta360 para ver "
                            + "o status dos pontos criticos da regiao.",
                    null));
        }
    }
}
