create table pessoa(
	codigo BIGINT(20) PRIMARY KEY AUTO_INCREMENT,
	nome VARCHAR(50) NOT NULL,
	rua VARCHAR(30),
	numero VARCHAR(30),
	complemento VARCHAR(30),
	bairro VARCHAR(30),
	cep VARCHAR(30),
	cidade VARCHAR(30),
	estado VARCHAR(30),
	ativo BOOLEAN NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
 INSERT INTO pessoa 
(nome, rua, numero, complemento, bairro, cep, cidade, estado, ativo) values 
('João Silva', 'Rua do Abacaxi', '10', null, 'Brasil', '38.400-121', 'Uberlândia', 'MG', true),
('Maria Rita', 'Rua do Sabiá', '110', 'Apto 101', 'Colina', '11.400-122', 'Ribeirão Preto', 'SP', true),
('Pedro Santos', 'Rua da Bateria', '23', null, 'Morumbi', '54.212-123', 'Goiânia', 'GO', true),
('Ricardo Pereira', 'Rua do Motorista', '123', 'Apto 302', 'Aparecida', '38.400-124', 'Salvador', 'BA', true),
('Josué Mariano', 'Av Rio Branco', '321', null, 'Jardins', '56.400-125', 'Natal', 'RN', true),
('Pedro Barbosa', 'Av Brasil', '100', null, 'Tubalina', '77.400-126', 'Porto Alegre', 'RS', true),
('Henrique Medeiros', 'Rua do Sapo', '1120', 'Apto 201', 'Centro', '12.400-127', 'Rio de Janeiro', 'RJ', true),
('Carlos Santana', 'Rua da Manga', '433', null, 'Centro', '31.400-128', 'Belo Horizonte', 'MG', true),
('Leonardo Oliveira', 'Rua do Músico', '566', null, 'Segismundo Pereira', '38.400-009', 'Uberlândia', 'MG', true),
('Isabela Martins', 'Rua da Terra', '1233', 'Apto 10', 'Vigilato', '99.400-121', 'Manaus', 'AM', true); 