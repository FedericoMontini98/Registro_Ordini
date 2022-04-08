SET NAMES utf8;
SET FOREIGN_KEY_CHECKS=0;

BEGIN;
DROP DATABASE IF EXISTS registroOrdini;
CREATE DATABASE IF NOT EXISTS registroOrdini;
COMMIT;

USE registroOrdini;
-- ------------------------------------- --
--       Codice Table Structures         --
-- ------------------------------------- --

DROP TABLE IF EXISTS Aziende;
CREATE TABLE Aziende(
	nomeAzienda VARCHAR(50) NOT NULL,
	prossimoIndiceOrdine INT(10) UNSIGNED NOT NULL,
	PRIMARY KEY(nomeAzienda)
) ENGINE= InnoDB DEFAULT CHARSET=latin1;

DROP TABLE IF EXISTS Ordini;
CREATE TABLE Ordini(
	nomeAziendaFK VARCHAR(50) NOT NULL,
	ordineID INT UNSIGNED NOT NULL,
	nomeProdotto VARCHAR(50) NOT NULL,
	dataScadenza DATE NOT NULL,
	guadagno FLOAT NOT NULL,
	fornitore VARCHAR(50) NOT NULL,
	numPezzi INT UNSIGNED NOT NULL,
	PRIMARY KEY (nomeAziendaFK,ordineID),
CONSTRAINT
	FOREIGN KEY (nomeAziendaFK)
	REFERENCES Aziende(nomeAzienda)
) ENGINE= InnoDB DEFAULT CHARSET=latin1;

-- ------------------------------------- --
--         Area Popolamento              --
-- ------------------------------------- --
INSERT INTO Aziende VALUES ("Meccanica Montini", 19);
COMMIT;

INSERT INTO Ordini VALUES 
("Meccanica Montini",1,"Fresatura disco",'2021-01-20',150.5,"Twin Disk",3),
("Meccanica Montini",2,"Barra Alluminio",'2021-01-19',100,"Twin Disk",4),
("Meccanica Montini",3,"Tornitura ad Albero",'2021-01-12',120,"CM",1),
("Meccanica Montini",4,"Rullo Gomma",'2021-01-02',260,"CM",5),
("Meccanica Montini",5,"Barra Timone",'2021-01-09',150.5,"Twin Disk",3),
("Meccanica Montini",6,"Supporto Timone",'2021-01-13',150.5,"CM",3),
("Meccanica Montini", 7, "Prodotto", '2020-01-01',150,"CM",1),
("Meccanica Montini", 8, "Prodotto", '2020-02-01',50,"CM",1),
("Meccanica Montini", 9, "Prodotto", '2020-03-01',170,"CM",1),
("Meccanica Montini", 10, "Prodotto", '2020-04-01',190,"CM",1),
("Meccanica Montini", 11, "Prodotto", '2020-05-01',200,"CM",1),
("Meccanica Montini", 12, "Prodotto", '2020-06-01',10,"CM",1),
("Meccanica Montini", 13, "Prodotto", '2020-07-01',15,"CM",1),
("Meccanica Montini", 14, "Prodotto", '2020-08-01',10,"CM",1),
("Meccanica Montini", 15, "Prodotto", '2020-09-01',100,"CM",1),
("Meccanica Montini", 16, "Prodotto", '2020-10-01',130,"CM",1),
("Meccanica Montini", 17, "Prodotto", '2020-11-01',150,"CM",1),
("Meccanica Montini", 18, "Prodotto", '2020-12-01',190,"CM",1);
COMMIT;
-- ------------------------------------- --
--       Sezione Procedure               --
-- ------------------------------------- --
SET GLOBAL AUTOCOMMIT  = 1;
SET GLOBAL EVENT_SCHEDULER = 1;
SET GLOBAL log_bin_trust_function_creators = 1;
DELIMITER $$

DROP PROCEDURE IF EXISTS  verifica_azienda $$
CREATE PROCEDURE verifica_azienda(IN _nomeAzienda VARCHAR(50))
BEGIN
	-- Verifico l'esistenza dell'azienda prima di inserire l'ordine altrimenti lo creo per evitare inconsistenze --
	-- Creo una variabile che diventi non nulla se esiste già l'azienda nel DB --
	DECLARE _azienda VARCHAR(50);
	SELECT nomeAzienda INTO _azienda
	FROM Aziende
	WHERE nomeAzienda = _nomeAzienda;
	-- Se non trovo l'azienda procedo a crearla altrimenti procedo normalmente
	IF _azienda IS NULL
	THEN
		INSERT INTO Aziende VALUES (_nomeAzienda,0);
	END IF;
END$$
-- ------------------------------------- --
--       Sezione Triggers                --
-- ------------------------------------- --
DROP TRIGGER IF EXISTS Azienda_init$$
CREATE TRIGGER Azienda_init
BEFORE INSERT ON Aziende
FOR EACH ROW
BEGIN
	SET new.prossimoIndiceOrdine=0;
END$$

DROP TRIGGER IF EXISTS Verifica_Ordine_Insert$$
CREATE TRIGGER Verifica_Ordine_Insert
BEFORE INSERT ON Ordini
FOR EACH ROW
BEGIN
	DECLARE _IndiceOrdine INT UNSIGNED DEFAULT NULL;

	IF NEW.dataScadenza <= CURRENT_DATE() 
	THEN
		SIGNAL SQLSTATE '45000'
		SET MESSAGE_TEXT = 'Impossibile aggiungere un ordine già scaduto';
	END IF;

	SELECT prossimoIndiceOrdine INTO _IndiceOrdine
    FROM Aziende a
    WHERE a.nomeAzienda = NEW.nomeAziendaFK
	FOR UPDATE;

	UPDATE Aziende a
	SET a.prossimoIndiceOrdine = a.prossimoIndiceOrdine + 1
	WHERE a.nomeAzienda = NEW.nomeAziendaFK;

	IF _indiceOrdine IS NULL THEN
		SIGNAL SQLSTATE '45000'
		SET MESSAGE_TEXT = 'Azienda inesistente';
	END IF;

	SET new.OrdineID = _IndiceOrdine;
END$$

DROP TRIGGER IF EXISTS Verifica_Ordine_Update$$
CREATE TRIGGER Verifica_Ordine
BEFORE UPDATE ON Ordini
FOR EACH ROW
BEGIN
	IF NEW.dataScadenza <= CURRENT_DATE() 
	THEN
		SIGNAL SQLSTATE '45000'
		SET MESSAGE_TEXT = 'Impossibile modificare un ordine già scaduto';
	END IF;
END$$