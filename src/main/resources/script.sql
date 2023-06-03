CREATE TABLE cliente (
     ID bigint NOT NULL PRIMARY KEY AUTO_INCREMENT,
     cpf bigint NOT NULL UNIQUE,
     nome varchar(45) NOT NULL
);

CREATE TABLE contrato (
      ID bigint NOT NULL PRIMARY KEY AUTO_INCREMENT,
      redacao TEXT,
      ultima_atualizacao DATE NOT NULL,
      cliente_ID bigint,
      FOREIGN KEY (cliente_ID) REFERENCES cliente(ID)
);
