DROP TABLE IF EXISTS CARD;
DROP TABLE IF EXISTS TRANSACTION;
  
CREATE TABLE CARD (
    id INT AUTO_INCREMENT PRIMARY KEY,
    firstname VARCHAR(255) NOT NULL,
    lastname VARCHAR(255),
	email VARCHAR(255) NOT NULL,
	number VARCHAR(255) NOT NULL
);


CREATE TABLE TRANSACTION (
    id INT AUTO_INCREMENT PRIMARY KEY,
    cardid INT NOT NULL,
    number VARCHAR(255) NOT NULL,
    price DECIMAL(10,2) NOT NULL,
	currency VARCHAR(3) NOT NULL,
	insertdate TIMESTAMP NOT NULL
);

ALTER TABLE TRANSACTION 
ADD FOREIGN KEY (cardid) references CARD(id);