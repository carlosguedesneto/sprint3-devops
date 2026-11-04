# Projeto Clyvovet Nexus - Documentação Completa

## Integrantes
- Grupo: Clyvovet Nexus
- Carlos Alberto Guedes Neto RM: 566022
- Eduardo Novaes Mollo RM: 561515
- Vinícius L. E. M. Garcia RM: 563340
- Luan Peixoto Marins Rocha RM: 562258
- Mathaus Victor Souza Marcelino RM: 564146

---

## Descrição da Solução

O **Clyvovet Nexus** é um sistema backend (API RESTful) desenvolvido em **Java com Spring Boot** para otimizar a gestão de clínicas veterinárias. A aplicação permite o cadastro estruturado de **Pets** (identificando nome, espécie, raça e idade) e o gerenciamento de **Eventos Preventivos** (como vacinas, vermifugação, avaliações cardiológicas e profilaxias dentárias). Todo evento preventivo é vinculado ao histórico do animal, possuindo controle exato da data de realização e da data do próximo vencimento, garantindo um acompanhamento de saúde contínuo e centralizado na nuvem.

---

## Descrição dos Benefícios para o Negócio

A implementação do Clyvovet Nexus resolve problemas operacionais comuns em clínicas veterinárias e traz melhorias estratégicas fundamentais:

- **Fidelização e Retenção de Clientes:** O controle automatizado de "datas de próximo vencimento" permite que a clínica notifique os tutores de forma proativa sobre o momento de retornar para reforços de vacinas ou check-ups anuais, aumentando o retorno financeiro recorrente.
- **Foco na Saúde Preventiva:** Resolve o problema comum de esquecimento e perda de prazos de vacinação por parte dos tutores, diminuindo a incidência de doenças graves e aumentando a qualidade e expectativa de vida dos animais.
- **Organização e Centralização:** Substitui fichas de papel, históricos fragmentados ou planilhas locais por um banco de dados relacional **Azure SQL Database** robusto e seguro na nuvem, acessível com alta disponibilidade.
- **Automação e Qualidade com CI/CD:** A integração contínua (CI) garante que todos os testes automáticos passem antes de gerar o artefato, e a entrega contínua (CD) realiza o deploy automático no **Azure Web App**, eliminando erros manuais de publicação.

---

## DDL e Inserts do Banco de Dados (script_db.sql)

```sql
-- Eliminar tabelas se já existirem (para garantir uma criação limpa)
IF OBJECT_ID('dbo.eventos_preventivos', 'U') IS NOT NULL DROP TABLE dbo.eventos_preventivos;
IF OBJECT_ID('dbo.pets', 'U') IS NOT NULL DROP TABLE dbo.pets;

-- Criar tabela pets
CREATE TABLE pets (
    id BIGINT IDENTITY(1,1) PRIMARY KEY,
    nome VARCHAR(255) NOT NULL,
    especie VARCHAR(255) NOT NULL,
    raca VARCHAR(255),
    idade INT
);

-- Criar tabela eventos_preventivos
CREATE TABLE eventos_preventivos (
    id BIGINT IDENTITY(1,1) PRIMARY KEY,
    tipo_evento VARCHAR(255) NOT NULL,
    descricao VARCHAR(255) NOT NULL,
    data_realizacao DATE,
    data_proximo_vencimento DATE,
    pet_id BIGINT NOT NULL,
    CONSTRAINT fk_pet FOREIGN KEY (pet_id) REFERENCES pets(id) ON DELETE CASCADE
);

-- Inserts iniciais para popular o banco automaticamente
INSERT INTO pets (nome, especie, raca, idade) VALUES ('Zeus', 'Cachorro', 'Pastor Alemão', 4);
INSERT INTO pets (nome, especie, raca, idade) VALUES ('Pandora', 'Gato', 'Maine Coon', 2);

INSERT INTO eventos_preventivos (tipo_evento, descricao, data_realizacao, data_proximo_vencimento, pet_id) 
VALUES ('Vacina', 'Vacina Antirrábica', '2026-01-15', '2027-01-15', 1);

INSERT INTO eventos_preventivos (tipo_evento, descricao, data_realizacao, data_proximo_vencimento, pet_id) 
VALUES ('Limpeza de Tártaro', 'Profilaxia dentária preventiva', '2026-04-05', '2027-04-05', 2);
```

---

## Arquitetura em Nuvem e Esteira CI/CD (Azure DevOps)

A infraestrutura foi provisionada na **Microsoft Azure** integrada ao **Azure DevOps Pipelines** para automação de CI/CD:

1. **Repositório:** GitHub integrado ao Azure DevOps.
2. **Esteira de CI (`Build_and_Test_CI`):**
   - Configura a JDK 17 na máquina virtual remota (`ubuntu-latest`).
   - Executa a compilação do projeto e a suíte de testes unitários automatizados (`PetServiceTest`) via Maven (`clyvovet-nexus/pom.xml`).
   - Copia o pacote gerado (`.jar`) e publica o artefato (`drop`) no repositório do Azure DevOps.
3. **Esteira de CD (`Deploy_CD`):**
   - Disparada automaticamente após o sucesso da etapa de CI.
   - Faz o download do artefato `.jar`.
   - Utiliza a task `AzureRmWebAppDeployment@4` e a *Service Connection* autorizada para efetuar o deploy automático no **Azure App Service**.
4. **Segurança de Variáveis de Ambiente:**
   - As credenciais sensíveis da base de dados (`DB_URL`, `DB_USER`, `DB_PASS`) estão protegidas nas **Application Settings** do Azure Web App, garantindo que nenhum dado sensível seja exposto no código ou repositório.

---

## Endpoints da API e Conexões

- **Aplicação Web (API Base):** `https://app-clyvovet-nexus-cwg4dxdednbpfdcv.canadacentral-01.azurewebsites.net`
- **Rotas de Pet:** `/api/pets` (GET, POST, PUT, DELETE)
- **Rotas de Eventos Preventivos:** `/api/eventos-preventivos` (GET, POST)

### Tabela de Rotas de Pets

| Verbo HTTP | Endpoint | Descrição | Exemplo de Body (JSON) |
| :--- | :--- | :--- | :--- |
| **GET** | `/api/pets` | Listar todos os pets cadastrados | - |
| **GET** | `/api/pets/{id}` | Buscar pet por ID | - |
| **POST** | `/api/pets` | Cadastrar um novo pet | `{"nome": "Thor", "especie": "Cachorro", "raca": "Golden Retriever", "idade": 3}` |
| **PUT** | `/api/pets/{id}` | Atualizar dados de um pet existente | `{"nome": "Zeus Silva", "especie": "Cachorro", "raca": "Pastor Alemão", "idade": 5}` |
| **DELETE** | `/api/pets/{id}` | Remover um pet por ID | - |


