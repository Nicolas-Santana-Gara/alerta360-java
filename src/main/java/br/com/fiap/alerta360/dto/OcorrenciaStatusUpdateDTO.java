package br.com.fiap.alerta360.dto;

import br.com.fiap.alerta360.model.enums.StatusOcorrencia;
import jakarta.validation.constraints.NotNull;

/**
 * Dados recebidos para a equipe de triagem alterar o status de uma
 * ocorrencia (recebido -> em analise -> encaminhado -> concluido).
 */
public class OcorrenciaStatusUpdateDTO {

    @NotNull(message = "O novo status e obrigatorio")
    private StatusOcorrencia status;

    public StatusOcorrencia getStatus() {
        return status;
    }

    public void setStatus(StatusOcorrencia status) {
        this.status = status;
    }
}
