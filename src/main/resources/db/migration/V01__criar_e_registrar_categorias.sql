CREATE TABLE categoria(
	codigo bigint(20) primary key auto_increment,
    nome varchar(50) not null
) 
ENGINE=InnoDB DEFAULT CHARSET=utf8;
insert into categoria (nome) values ('Lazer');
insert into categoria (nome) values ('Alimentação');
insert into categoria (nome) values ('Combustível');
insert into categoria (nome) values ('Farmácia');
insert into categoria (nome) values ('Serviços'); 
insert into categoria (nome) values ('Outros');