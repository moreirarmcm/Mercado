/*
** Banco de dados do Mercado (mySQL);
**
*/
CREATE DATABASE mercado;

USE mercado;

CREATE TABLE produtos (
	codigo INT,
	nome VARCHAR (15),
	preco FLOAT (7,2)
	);
	
ALTER TABLE produtos 
	ADD CONSTRAINT pk_curso 
	PRIMARY KEY (codigo);
	
ALTER TABLE produtos
	MODIFY codigo INT AUTO_INCREMENT;

ALTER TABLE produtos
	MODIFY nome VARCHAR(30);