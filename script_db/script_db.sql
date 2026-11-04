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

-- Inserts iniciais de teste
INSERT INTO pets (nome, especie, raca, idade) VALUES ('Zeus', 'Cachorro', 'Pastor Alemão', 4);
INSERT INTO pets (nome, especie, raca, idade) VALUES ('Pandora', 'Gato', 'Maine Coon', 2);

INSERT INTO eventos_preventivos (tipo_evento, descricao, data_realizacao, data_proximo_vencimento, pet_id) 
VALUES ('Vacina', 'Vacina Antirrábica', '2026-01-15', '2027-01-15', 1);