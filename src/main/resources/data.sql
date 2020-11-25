INSERT INTO CARD 
    (firstname, lastname, email, number) 
VALUES
    ('John', 'Doe', 'John.Doe@google.com', '123987123987'),
    ('Piotr', 'Suski', 'Piotr.Suski@zilch.com', '122298709799');

INSERT INTO TRANSACTION
    (cardid, number, price,	currency, insertdate)
VALUES
	(1, '112', '233', 'USD', parsedatetime('25-11-2020 12:40:50', 'dd-MM-yyyy hh:mm:ss')),
	(1, '155', '33', 'USD', parsedatetime('25-11-2019 12:40:50', 'dd-MM-yyyy hh:mm:ss')),
	(1, '212', '533', 'USD', parsedatetime('24-11-2020 12:40:50', 'dd-MM-yyyy hh:mm:ss')),
	(1, '412', '633', 'USD', parsedatetime('21-11-2020 12:40:50', 'dd-MM-yyyy hh:mm:ss'));
    
    