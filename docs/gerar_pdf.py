# -*- coding: utf-8 -*-
"""Monta a documentacao PDF da entrega Domain Driven Design - Java (Alerta360)."""

from reportlab.lib.pagesizes import A4
from reportlab.lib.units import cm
from reportlab.lib import colors
from reportlab.lib.styles import getSampleStyleSheet, ParagraphStyle
from reportlab.lib.enums import TA_CENTER, TA_LEFT
from reportlab.platypus import (
    SimpleDocTemplate, Paragraph, Spacer, Image, PageBreak, Table, TableStyle,
    ListFlowable, ListItem
)

AZUL = colors.HexColor("#1f4e79")
AZUL_CLARO = colors.HexColor("#dbe9f6")

styles = getSampleStyleSheet()
styles.add(ParagraphStyle(name="TituloCapa", fontSize=24, leading=30, alignment=TA_CENTER,
                           textColor=AZUL, fontName="Helvetica-Bold", spaceAfter=10))
styles.add(ParagraphStyle(name="SubtituloCapa", fontSize=14, leading=20, alignment=TA_CENTER,
                           textColor=colors.HexColor("#444444"), spaceAfter=6))
styles.add(ParagraphStyle(name="H1", fontSize=16, leading=20, textColor=AZUL,
                           fontName="Helvetica-Bold", spaceBefore=14, spaceAfter=8))
styles.add(ParagraphStyle(name="H2", fontSize=12.5, leading=16, textColor=AZUL,
                           fontName="Helvetica-Bold", spaceBefore=10, spaceAfter=6))
styles.add(ParagraphStyle(name="CorpoTexto", fontSize=10.3, leading=14.5, alignment=TA_LEFT,
                           spaceAfter=6))
styles.add(ParagraphStyle(name="Legenda", fontSize=8.7, leading=11, alignment=TA_CENTER,
                           textColor=colors.HexColor("#555555"), spaceBefore=4, spaceAfter=10))
styles.add(ParagraphStyle(name="Codigo", fontSize=9, leading=12, fontName="Courier",
                           backColor=colors.HexColor("#f2f2f2"), spaceAfter=6))

elementos = []

# ---------------------------------------------------------------------------
# CAPA
# ---------------------------------------------------------------------------
elementos.append(Spacer(1, 4 * cm))
elementos.append(Paragraph("Alerta360", styles["TituloCapa"]))
elementos.append(Paragraph("CitizenTech — Prevenção e Resiliência a Alagamentos", styles["SubtituloCapa"]))
elementos.append(Paragraph("Território: Jardim Helena — São Paulo (SP)", styles["SubtituloCapa"]))
elementos.append(Spacer(1, 1.5 * cm))
elementos.append(Paragraph("Disciplina: Domain Driven Design — Java", styles["SubtituloCapa"]))
elementos.append(Paragraph("Global Solution 2026.2 — FIAP", styles["SubtituloCapa"]))
elementos.append(Spacer(1, 2 * cm))

dados_integrantes = [["Integrante", "RM"], ["Nicolas Santana Gará", "RM561461"]]
tabela_integrantes = Table(dados_integrantes, colWidths=[8 * cm, 4 * cm])
tabela_integrantes.setStyle(TableStyle([
    ("BACKGROUND", (0, 0), (-1, 0), AZUL),
    ("TEXTCOLOR", (0, 0), (-1, 0), colors.white),
    ("FONTNAME", (0, 0), (-1, 0), "Helvetica-Bold"),
    ("FONTSIZE", (0, 0), (-1, -1), 10),
    ("GRID", (0, 0), (-1, -1), 0.6, colors.HexColor("#999999")),
    ("ALIGN", (0, 0), (-1, -1), "CENTER"),
    ("ROWBACKGROUNDS", (0, 1), (-1, -1), [colors.white, AZUL_CLARO]),
    ("TOPPADDING", (0, 0), (-1, -1), 6),
    ("BOTTOMPADDING", (0, 0), (-1, -1), 6),
]))
elementos.append(tabela_integrantes)
elementos.append(Spacer(1, 0.3 * cm))
elementos.append(Paragraph(
    "<i>Observação: demais integrantes do grupo a serem incluídos nesta tabela antes do envio final.</i>",
    ParagraphStyle(name="obs", parent=styles["SubtituloCapa"], fontSize=9)))
elementos.append(Spacer(1, 3 * cm))
elementos.append(Paragraph("Novembro de 2026", styles["SubtituloCapa"]))
elementos.append(PageBreak())

# ---------------------------------------------------------------------------
# INDICE
# ---------------------------------------------------------------------------
elementos.append(Paragraph("Índice", styles["H1"]))
indice_itens = [
    "1. Especificação do projeto — descritivo, justificativa e objetivos",
    "2. Diagrama de Classes",
    "3. Diagrama Entidade-Relacionamento (DER)",
    "4. Protótipos de tela",
    "5. Tabela de endpoints (requisições HTTP)",
    "6. Instruções de execução do projeto",
]
elementos.append(ListFlowable(
    [ListItem(Paragraph(i, styles["CorpoTexto"])) for i in indice_itens],
    bulletType="bullet", start="circle", leftIndent=14,
))
elementos.append(PageBreak())

# ---------------------------------------------------------------------------
# 1) ESPECIFICACAO
# ---------------------------------------------------------------------------
elementos.append(Paragraph("1. Especificação do projeto", styles["H1"]))

elementos.append(Paragraph("Descritivo da solução", styles["H2"]))
elementos.append(Paragraph(
    "O Alerta360 é uma CitizenTech voltada à prevenção e à resiliência urbana diante de "
    "alagamentos. A plataforma permite que moradores de Jardim Helena (São Paulo — SP) "
    "registrem ocorrências de alagamento, água acumulada, bueiro obstruído ou vias "
    "temporariamente difíceis de atravessar em pontos críticos da região, acompanhem o "
    "status de triagem de cada relato e consultem orientações preventivas e contatos "
    "oficiais. Esta entrega — da disciplina Domain Driven Design — Java — implementa o "
    "núcleo de domínio da solução como uma API RESTful em Spring Boot, persistida em "
    "banco de dados Oracle (instância da FIAP), organizada nas camadas Model, DAO, BO "
    "(regras de negócio) e Controller, com tratamento centralizado de exceções.",
    styles["CorpoTexto"]))

elementos.append(Paragraph("Justificativa", styles["H2"]))
elementos.append(Paragraph(
    "Jardim Helena é um bairro da Zona Leste de São Paulo, na subprefeitura de São Miguel "
    "Paulista, próximo ao Rio Tietê, com histórico recorrente de alagamento em dias de "
    "chuva forte — um padrão amplamente reportado em pontos de monitoramento da Zona "
    "Leste pela Prefeitura de São Paulo / CGE-SP (Centro de Gerenciamento de "
    "Emergências). Hoje, a informação sobre quais ruas estão alagadas, há quanto tempo e "
    "com que gravidade costuma circular de forma fragmentada entre vizinhos, sem um canal "
    "único, atualizado e acessível. O Alerta360 justifica-se por organizar esse "
    "conhecimento comunitário em uma ferramenta simples, sem pretender substituir os "
    "canais oficiais de emergência — apenas reduzir a assimetria de informação entre "
    "quem sofre o problema todos os dias.",
    styles["CorpoTexto"]))

elementos.append(Paragraph("Objetivos", styles["H2"]))
objetivos = [
    "Permitir que qualquer morador registre uma ocorrência de alagamento em um ponto crítico, com categoria, descrição e foto opcional.",
    "Dar visibilidade ao status de cada ocorrência (recebido, em análise, encaminhado, concluído), evitando relatos duplicados e dando retorno à comunidade.",
    "Disponibilizar um cadastro de pontos críticos da região, com dados que ajudam a entender por que certos locais demoram mais a escoar (ex.: distância até a estação de bombeamento).",
    "Oferecer um painel de orientações preventivas e contatos oficiais, reforçando que o Alerta360 complementa — mas não substitui — a Defesa Civil.",
    "Entregar uma API RESTful organizada em camadas (Model, DAO, BO, Controller) e com tratamento de exceções consistente, pronta para ser consumida por um front-end web.",
]
elementos.append(ListFlowable(
    [ListItem(Paragraph(o, styles["CorpoTexto"])) for o in objetivos],
    bulletType="bullet", leftIndent=14,
))
elementos.append(PageBreak())

# ---------------------------------------------------------------------------
# 2) DIAGRAMA DE CLASSES
# ---------------------------------------------------------------------------
elementos.append(Paragraph("2. Diagrama de Classes", styles["H1"]))
elementos.append(Paragraph(
    "O diagrama abaixo reflete as entidades JPA implementadas em <font face='Courier'>"
    "br.com.fiap.alerta360.model</font>. As associações <i>Usuario → Ocorrencia</i> e "
    "<i>PontoCritico → Ocorrencia</i> são de um-para-muitos (1:N): um usuário pode "
    "registrar várias ocorrências, e um ponto crítico pode acumular várias ocorrências "
    "ao longo do tempo.",
    styles["CorpoTexto"]))
elementos.append(Image("fig_diagrama_classes.png", width=17 * cm, height=17 * cm * (1125 / 1650)))
elementos.append(Paragraph("Figura 1 — Diagrama de classes do domínio Alerta360.", styles["Legenda"]))
elementos.append(PageBreak())

# ---------------------------------------------------------------------------
# 3) DER
# ---------------------------------------------------------------------------
elementos.append(Paragraph("3. Diagrama Entidade-Relacionamento (DER)", styles["H1"]))
elementos.append(Paragraph(
    "As tabelas usam o prefixo <font face='Courier'>ALR_</font> para não colidir com "
    "tabelas de outras disciplinas no schema Oracle compartilhado da FIAP. As chaves "
    "estrangeiras de <font face='Courier'>ALR_OCORRENCIA</font> materializam as mesmas "
    "associações do diagrama de classes.",
    styles["CorpoTexto"]))
elementos.append(Image("fig_der.png", width=17 * cm, height=17 * cm * (1125 / 1650)))
elementos.append(Paragraph("Figura 2 — Diagrama Entidade-Relacionamento (Oracle).", styles["Legenda"]))
elementos.append(PageBreak())

# ---------------------------------------------------------------------------
# 4) PROTOTIPOS DE TELA
# ---------------------------------------------------------------------------
elementos.append(Paragraph("4. Protótipos de tela", styles["H1"]))
elementos.append(Paragraph(
    "Protótipos (esboços) das três telas que consumiriam esta API em um front-end: o "
    "mapa comunitário de ocorrências, o formulário de registro de uma nova ocorrência e "
    "o painel administrativo de triagem usado pela equipe responsável por atualizar o "
    "status dos relatos.",
    styles["CorpoTexto"]))
elementos.append(Image("fig_telas.png", width=17 * cm, height=17 * cm * (1200 / 1950)))
elementos.append(Paragraph(
    "Figura 3 — Da esquerda para a direita: (1) mapa de ocorrências, consumindo "
    "GET /api/ocorrencias e GET /api/pontos-criticos; (2) formulário de nova ocorrência, "
    "consumindo POST /api/ocorrencias; (3) painel de triagem, consumindo "
    "PUT /api/ocorrencias/{id}/status.",
    styles["Legenda"]))
elementos.append(PageBreak())

# ---------------------------------------------------------------------------
# 5) TABELA DE ENDPOINTS
# ---------------------------------------------------------------------------
elementos.append(Paragraph("5. Tabela de endpoints (requisições HTTP)", styles["H1"]))

endpoints = [
    ["Recurso", "Método", "Endpoint", "Descrição"],
    ["Usuário", "GET", "/api/usuarios", "Lista todos os usuários"],
    ["Usuário", "GET", "/api/usuarios/{id}", "Busca um usuário por id"],
    ["Usuário", "POST", "/api/usuarios", "Cadastra um usuário"],
    ["Usuário", "PUT", "/api/usuarios/{id}", "Atualiza um usuário"],
    ["Usuário", "DELETE", "/api/usuarios/{id}", "Remove um usuário"],
    ["Ponto Crítico", "GET", "/api/pontos-criticos", "Lista todos os pontos críticos"],
    ["Ponto Crítico", "GET", "/api/pontos-criticos/{id}", "Busca um ponto crítico por id"],
    ["Ponto Crítico", "POST", "/api/pontos-criticos", "Cadastra um ponto crítico"],
    ["Ponto Crítico", "PUT", "/api/pontos-criticos/{id}", "Atualiza um ponto crítico"],
    ["Ponto Crítico", "DELETE", "/api/pontos-criticos/{id}", "Remove um ponto crítico"],
    ["Ocorrência", "GET", "/api/ocorrencias", "Lista ocorrências (filtros: status, pontoCriticoId)"],
    ["Ocorrência", "GET", "/api/ocorrencias/{id}", "Busca uma ocorrência por id"],
    ["Ocorrência", "POST", "/api/ocorrencias", "Registra uma ocorrência"],
    ["Ocorrência", "PUT", "/api/ocorrencias/{id}/status", "Atualiza o status de uma ocorrência"],
    ["Ocorrência", "DELETE", "/api/ocorrencias/{id}", "Remove uma ocorrência"],
    ["Orientação", "GET", "/api/orientacoes", "Lista orientações preventivas"],
    ["Orientação", "GET", "/api/orientacoes/{id}", "Busca uma orientação por id"],
    ["Orientação", "POST", "/api/orientacoes", "Cadastra uma orientação"],
    ["Orientação", "PUT", "/api/orientacoes/{id}", "Atualiza uma orientação"],
    ["Orientação", "DELETE", "/api/orientacoes/{id}", "Remove uma orientação"],
]

tabela_estilo_base = [
    ("BACKGROUND", (0, 0), (-1, 0), AZUL),
    ("TEXTCOLOR", (0, 0), (-1, 0), colors.white),
    ("FONTNAME", (0, 0), (-1, 0), "Helvetica-Bold"),
    ("FONTSIZE", (0, 0), (-1, -1), 8.3),
    ("GRID", (0, 0), (-1, -1), 0.5, colors.HexColor("#aaaaaa")),
    ("VALIGN", (0, 0), (-1, -1), "MIDDLE"),
    ("ROWBACKGROUNDS", (0, 1), (-1, -1), [colors.white, AZUL_CLARO]),
    ("TOPPADDING", (0, 0), (-1, -1), 4),
    ("BOTTOMPADDING", (0, 0), (-1, -1), 4),
]

tabela_endpoints = Table(endpoints, colWidths=[2.6 * cm, 1.8 * cm, 6.4 * cm, 6.2 * cm], repeatRows=1)
tabela_endpoints.setStyle(TableStyle(tabela_estilo_base))
elementos.append(tabela_endpoints)
elementos.append(Spacer(1, 0.3 * cm))
elementos.append(Paragraph(
    "Exemplo de corpo (body) para <font face='Courier'>POST /api/ocorrencias</font>:",
    styles["CorpoTexto"]))
exemplo_json = (
    "{<br/>"
    "&nbsp;&nbsp;\"usuarioId\": 1,<br/>"
    "&nbsp;&nbsp;\"pontoCriticoId\": 1,<br/>"
    "&nbsp;&nbsp;\"categoria\": \"ALAGAMENTO\",<br/>"
    "&nbsp;&nbsp;\"descricao\": \"Agua na altura da calcada apos a chuva das 18h\",<br/>"
    "&nbsp;&nbsp;\"fotoUrl\": null<br/>"
    "}"
)
elementos.append(Paragraph(exemplo_json, styles["Codigo"]))
elementos.append(PageBreak())

# ---------------------------------------------------------------------------
# 6) INSTRUCOES DE EXECUCAO
# ---------------------------------------------------------------------------
elementos.append(Paragraph("6. Instruções de execução do projeto", styles["H1"]))

elementos.append(Paragraph("Ferramentas e versões utilizadas", styles["H2"]))
ferramentas = [
    "IDE: IntelliJ IDEA (ou outra IDE Java com suporte a Maven/Spring Boot)",
    "Java JDK 17",
    "Spring Boot 3.3.4 (Web, Data JPA, Validation)",
    "Driver Oracle: ojdbc11 (23.4.0.24.05)",
    "Banco de dados: Oracle — instância da FIAP",
    "Build/deploy: Maven + Docker (render.com)",
]
elementos.append(ListFlowable(
    [ListItem(Paragraph(f, styles["CorpoTexto"])) for f in ferramentas],
    bulletType="bullet", leftIndent=14,
))

elementos.append(Paragraph("Passo a passo — execução local", styles["H2"]))
passos = [
    "Clonar o repositório do GitHub (link abaixo).",
    "Definir as variáveis de ambiente DB_URL, DB_USER e DB_PASSWORD com as credenciais do Oracle da FIAP (ver README.md do repositório para os comandos em PowerShell/Linux).",
    "Rodar \"mvn spring-boot:run\" na raiz do projeto, ou executar a classe Alerta360Application pela IDE.",
    "A API sobe em http://localhost:8080. Os endpoints podem ser testados com Postman/Insomnia ou pelo front-end da equipe.",
    "Rodar \"mvn test\" para executar os testes automatizados (usam H2 em memória, não dependem do Oracle).",
]
elementos.append(ListFlowable(
    [ListItem(Paragraph(f"{i+1}. {p}", styles["CorpoTexto"])) for i, p in enumerate(passos)],
    bulletType="bullet", leftIndent=14,
))

elementos.append(Paragraph("Links", styles["H2"]))
elementos.append(Paragraph(
    "<b>Repositório GitHub:</b> [INCLUIR LINK DO REPOSITORIO ANTES DO ENVIO]<br/>"
    "<b>Deploy (render.com):</b> [INCLUIR LINK DO DEPLOY ANTES DO ENVIO]",
    styles["CorpoTexto"]))

elementos.append(Paragraph("Demais informações", styles["H2"]))
elementos.append(Paragraph(
    "O projeto já inclui um Dockerfile (build multi-stage com Maven + runtime JRE 17) e "
    "um render.yaml opcional, prontos para deploy direto no render.com — o passo a passo "
    "completo do deploy está documentado no README.md do repositório. Na primeira "
    "execução, um DataSeeder popula automaticamente alguns pontos críticos e orientações "
    "de Jardim Helena, apenas para facilitar a demonstração e os testes manuais da API.",
    styles["CorpoTexto"]))

doc = SimpleDocTemplate(
    "Alerta360_DDD_Java_Documentacao.pdf",
    pagesize=A4,
    leftMargin=2 * cm, rightMargin=2 * cm, topMargin=2 * cm, bottomMargin=2 * cm,
    title="Alerta360 - Documentacao DDD Java",
    author="Nicolas Santana Gara - RM561461",
)
doc.build(elementos)
print("PDF gerado: Alerta360_DDD_Java_Documentacao.pdf")
