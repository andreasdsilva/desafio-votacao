CREATE TABLE assembleia (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    description VARCHAR(255) DEFAULT '',
    creation_date DATE NOT NULL,
    start_date DATE NOT NULL,
    end_date DATE NOT NULL
);

CREATE TABLE associado (
    id BIGSERIAL PRIMARY KEY,
    nome VARCHAR(255) NOT NULL,
    documento VARCHAR(255) NOT NULL,
    status VARCHAR(50)
);

CREATE TABLE pauta (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    description VARCHAR(255) DEFAULT '',
    assembleia_id BIGINT,
    start_time TIMESTAMP,
    end_time TIMESTAMP NOT NULL,
    FOREIGN KEY (assembleia_id) REFERENCES assembleia(id)
);

CREATE TABLE voto (
    id BIGSERIAL PRIMARY KEY,
    associado_id BIGINT,
    pauta_id BIGINT,
    voto_result VARCHAR(50) NOT NULL,
    FOREIGN KEY (associado_id) REFERENCES associado(id),
    FOREIGN KEY (pauta_id) REFERENCES pauta(id)
);