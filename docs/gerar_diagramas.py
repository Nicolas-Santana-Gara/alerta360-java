# -*- coding: utf-8 -*-
"""Gera class diagram, DER e wireframes das telas do Alerta360 (PNG), sem
depender do binario 'dot' do Graphviz - usa apenas matplotlib."""

import matplotlib
matplotlib.use("Agg")
import matplotlib.pyplot as plt
import matplotlib.patches as patches
from matplotlib.lines import Line2D

AZUL = "#1f4e79"
AZUL_CLARO = "#dbe9f6"
CINZA = "#555555"


def caixa(ax, x, y, w, h, titulo, linhas, cor_borda=AZUL, cor_fundo=AZUL_CLARO, titulo_cor="white"):
    ax.add_patch(patches.FancyBboxPatch(
        (x, y), w, h, boxstyle="round,pad=0.02,rounding_size=0.02",
        linewidth=1.4, edgecolor=cor_borda, facecolor="white"))
    header_h = 0.42
    ax.add_patch(patches.FancyBboxPatch(
        (x, y + h - header_h), w, header_h, boxstyle="round,pad=0.0,rounding_size=0.02",
        linewidth=1.4, edgecolor=cor_borda, facecolor=cor_borda))
    ax.text(x + w / 2, y + h - header_h / 2, titulo, ha="center", va="center",
            fontsize=11, fontweight="bold", color=titulo_cor)
    ax.plot([x, x + w], [y + h - header_h, y + h - header_h], color=cor_borda, linewidth=1.4)

    n = len(linhas)
    for i, linha in enumerate(linhas):
        ly = y + h - header_h - 0.30 - i * 0.30
        ax.text(x + 0.12, ly, linha, ha="left", va="center", fontsize=8.6, color="#222222",
                family="monospace")


def seta_relacao(ax, p1, p2, label_origem="1", label_destino="N", texto=""):
    ax.annotate(
        "", xy=p2, xytext=p1,
        arrowprops=dict(arrowstyle="-|>", color=CINZA, lw=1.3,
                         shrinkA=2, shrinkB=2, mutation_scale=16),
    )
    mx, my = (p1[0] + p2[0]) / 2, (p1[1] + p2[1]) / 2
    if texto:
        ax.text(mx, my + 0.18, texto, ha="center", va="bottom", fontsize=8, color=CINZA, style="italic")
    ax.text(p1[0] + (p2[0] - p1[0]) * 0.12, p1[1] + (p2[1] - p1[1]) * 0.12 + 0.12,
            label_origem, fontsize=8.5, color=CINZA)
    ax.text(p1[0] + (p2[0] - p1[0]) * 0.88, p1[1] + (p2[1] - p1[1]) * 0.88 + 0.12,
            label_destino, fontsize=8.5, color=CINZA)


# ---------------------------------------------------------------------------
# 1) DIAGRAMA DE CLASSES
# ---------------------------------------------------------------------------
fig, ax = plt.subplots(figsize=(11, 7.5))
ax.set_xlim(0, 11)
ax.set_ylim(0, 7.5)
ax.axis("off")
ax.set_title("Alerta360 — Diagrama de Classes (camada model)", fontsize=13, fontweight="bold", pad=14)

caixa(ax, 0.4, 4.6, 3.0, 2.6, "Usuario", [
    "- id: Long",
    "- nome: String",
    "- email: String",
    "- bairro: String",
    "- tipoUsuario: TipoUsuario",
    "- dataCadastro: LocalDateTime",
])

caixa(ax, 7.6, 4.6, 3.0, 2.6, "PontoCritico", [
    "- id: Long",
    "- nome: String",
    "- logradouro: String",
    "- bairro: String",
    "- latitude: Double",
    "- longitude: Double",
    "- distEstacaoBombeamentoM: Int",
])

caixa(ax, 4.0, 0.5, 3.0, 3.0, "Ocorrencia", [
    "- id: Long",
    "- usuario: Usuario",
    "- pontoCritico: PontoCritico",
    "- categoria: CategoriaOcorrencia",
    "- descricao: String",
    "- fotoUrl: String",
    "- dataHora: LocalDateTime",
    "- status: StatusOcorrencia",
])

caixa(ax, 0.4, 0.5, 3.0, 1.9, "Orientacao", [
    "- id: Long",
    "- titulo: String",
    "- conteudo: String",
    "- linkOficial: String",
])

caixa(ax, 7.6, 0.4, 3.0, 2.8, "<<enum>> Enums do Dominio", [
    "CategoriaOcorrencia:",
    "  ALAGAMENTO, AGUA_ACUMULADA,",
    "  BUEIRO_OBSTRUIDO, VIA_DIFICIL",
    "StatusOcorrencia:",
    "  RECEBIDO, EM_ANALISE,",
    "  ENCAMINHADO, CONCLUIDO",
    "TipoUsuario: CIDADAO, ADMIN.",
], cor_borda="#7a7a7a", cor_fundo="#eeeeee")

seta_relacao(ax, (1.9, 4.6), (5.0, 3.5), "1", "N", "registra")
seta_relacao(ax, (9.1, 4.6), (6.8, 3.5), "1", "N", "ocorre em")

plt.tight_layout()
plt.savefig("fig_diagrama_classes.png", dpi=150)
plt.close(fig)


# ---------------------------------------------------------------------------
# 2) DIAGRAMA ENTIDADE-RELACIONAMENTO (DER)
# ---------------------------------------------------------------------------
fig, ax = plt.subplots(figsize=(11, 7.5))
ax.set_xlim(0, 11)
ax.set_ylim(0, 7.5)
ax.axis("off")
ax.set_title("Alerta360 — Diagrama Entidade-Relacionamento (Oracle)", fontsize=13, fontweight="bold", pad=14)

caixa(ax, 0.4, 4.6, 3.1, 2.6, "ALR_USUARIO", [
    "PK ID_USUARIO: NUMBER",
    "   NOME: VARCHAR2(120)",
    "   EMAIL: VARCHAR2(150)",
    "   BAIRRO: VARCHAR2(80)",
    "   TIPO_USUARIO: VARCHAR2(20)",
    "   DATA_CADASTRO: TIMESTAMP",
], cor_borda="#1f4e79")

caixa(ax, 7.5, 4.3, 3.1, 2.9, "ALR_PONTO_CRITICO", [
    "PK ID_PONTO_CRITICO: NUMBER",
    "   NOME: VARCHAR2(120)",
    "   LOGRADOURO: VARCHAR2(150)",
    "   BAIRRO: VARCHAR2(80)",
    "   LATITUDE: NUMBER",
    "   LONGITUDE: NUMBER",
    "   DIST_ESTACAO_BOMBEAMENTO_M",
], cor_borda="#1f4e79")

caixa(ax, 3.95, 0.5, 3.1, 3.0, "ALR_OCORRENCIA", [
    "PK ID_OCORRENCIA: NUMBER",
    "FK ID_USUARIO: NUMBER",
    "FK ID_PONTO_CRITICO: NUMBER",
    "   CATEGORIA: VARCHAR2(30)",
    "   DESCRICAO: VARCHAR2(500)",
    "   FOTO_URL: VARCHAR2(300)",
    "   DATA_HORA: TIMESTAMP",
    "   STATUS: VARCHAR2(20)",
], cor_borda="#1f4e79")

caixa(ax, 0.4, 0.3, 3.1, 1.9, "ALR_ORIENTACAO", [
    "PK ID_ORIENTACAO: NUMBER",
    "   TITULO: VARCHAR2(150)",
    "   CONTEUDO: VARCHAR2(1000)",
    "   LINK_OFICIAL: VARCHAR2(300)",
], cor_borda="#1f4e79")

seta_relacao(ax, (1.95, 4.6), (5.0, 3.5), "1", "N", "FK ID_USUARIO")
seta_relacao(ax, (9.0, 4.6), (6.9, 3.5), "1", "N", "FK ID_PONTO_CRITICO")

plt.tight_layout()
plt.savefig("fig_der.png", dpi=150)
plt.close(fig)


# ---------------------------------------------------------------------------
# 3) WIREFRAMES DAS TELAS (prototipo)
# ---------------------------------------------------------------------------
def tela_base(ax, titulo):
    ax.set_xlim(0, 4)
    ax.set_ylim(0, 7.2)
    ax.axis("off")
    ax.add_patch(patches.FancyBboxPatch((0, 0), 4, 7.2, boxstyle="round,pad=0.02,rounding_size=0.08",
                                         linewidth=2, edgecolor="#222222", facecolor="white"))
    ax.add_patch(patches.Rectangle((0, 6.6), 4, 0.6, facecolor="#1f4e79"))
    ax.text(2, 6.9, titulo, ha="center", va="center", color="white", fontsize=10, fontweight="bold")


fig = plt.figure(figsize=(13, 8))

# Tela 1 — Mapa de ocorrencias
ax1 = fig.add_subplot(1, 3, 1)
tela_base(ax1, "Alerta360 — Mapa")
ax1.add_patch(patches.Rectangle((0.2, 2.1), 3.6, 4.3, facecolor="#dbe9f6", edgecolor="#1f4e79"))
ax1.text(2, 4.25, "[ mapa de\nJardim Helena\ncom pontos criticos ]", ha="center", va="center", fontsize=8.5)
for (px, py, cor) in [(1.0, 3.0, "#d9534f"), (2.6, 3.6, "#f0ad4e"), (1.8, 5.2, "#5cb85c")]:
    ax1.add_patch(patches.Circle((px, py), 0.12, facecolor=cor, edgecolor="black"))
ax1.add_patch(patches.FancyBboxPatch((0.3, 1.3), 1.6, 0.5, boxstyle="round,pad=0.02", facecolor="#1f4e79"))
ax1.text(1.1, 1.55, "+ Relatar", ha="center", va="center", color="white", fontsize=8.5)
ax1.add_patch(patches.FancyBboxPatch((2.1, 1.3), 1.6, 0.5, boxstyle="round,pad=0.02", facecolor="white", edgecolor="#1f4e79"))
ax1.text(2.9, 1.55, "Orientacoes", ha="center", va="center", color="#1f4e79", fontsize=8.5)
ax1.text(2, 0.7, "Filtros: categoria / periodo / status", ha="center", fontsize=7.6, color="#555")

# Tela 2 — Formulario de ocorrencia
ax2 = fig.add_subplot(1, 3, 2)
tela_base(ax2, "Nova Ocorrencia")
campos = ["Ponto critico (lista)", "Categoria (ALAGAMENTO...)", "Descricao", "Foto (opcional)"]
yy = 5.6
for campo in campos:
    ax2.add_patch(patches.FancyBboxPatch((0.3, yy), 3.4, 0.55, boxstyle="round,pad=0.02",
                                          facecolor="white", edgecolor="#1f4e79"))
    ax2.text(0.45, yy + 0.27, campo, ha="left", va="center", fontsize=8)
    yy -= 0.85
ax2.add_patch(patches.FancyBboxPatch((0.9, yy - 0.1), 2.2, 0.55, boxstyle="round,pad=0.02",
                                      facecolor="#1f4e79"))
ax2.text(2.0, yy + 0.18, "Enviar relato", ha="center", va="center", color="white", fontsize=8.5)
ax2.text(2, 0.6, "POST /api/ocorrencias", ha="center", fontsize=7.6, color="#555", family="monospace")

# Tela 3 — Painel administrativo
ax3 = fig.add_subplot(1, 3, 3)
tela_base(ax3, "Painel de Triagem")
cabecalho = ["Ponto", "Categoria", "Status"]
linhas_tab = [
    ["Rua Vila Jacui", "Alagamento", "RECEBIDO"],
    ["Av. Jd. Helena", "Bueiro obst.", "EM_ANALISE"],
    ["Praca Jd. Helena", "Agua acum.", "ENCAMINHADO"],
]
y0 = 6.1
for j, c in enumerate(cabecalho):
    ax3.text(0.35 + j * 1.2, y0, c, fontsize=7.6, fontweight="bold")
ax3.plot([0.25, 3.75], [y0 - 0.12, y0 - 0.12], color="#1f4e79", linewidth=1)
for i, linha in enumerate(linhas_tab):
    yy = y0 - 0.5 - i * 0.5
    for j, v in enumerate(linha):
        ax3.text(0.35 + j * 1.2, yy, v, fontsize=7.2)
ax3.add_patch(patches.FancyBboxPatch((0.6, 1.0), 2.8, 0.5, boxstyle="round,pad=0.02",
                                      facecolor="#1f4e79"))
ax3.text(2.0, 1.25, "Atualizar status", ha="center", va="center", color="white", fontsize=8.3)
ax3.text(2, 0.5, "PUT /api/ocorrencias/{id}/status", ha="center", fontsize=7.2, color="#555", family="monospace")

plt.tight_layout()
plt.savefig("fig_telas.png", dpi=150)
plt.close(fig)

print("Diagramas gerados: fig_diagrama_classes.png, fig_der.png, fig_telas.png")
