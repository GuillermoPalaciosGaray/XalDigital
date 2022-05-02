-- Creacion de las tablas

Create table aerolineas (
    ID_AEROLINEA INTEGER,
    NOMBRE_AEROLINEA VARCHAR(128),
    PRIMARY KEY(ID_AEROLINEA)
);

INSERT INTO aerolineas (ID_AEROLINEA,NOMBRE_AEROLINEA) VALUES (1,'Volaris');
INSERT INTO aerolineas (ID_AEROLINEA,NOMBRE_AEROLINEA) VALUES (2,'Aeromar');
INSERT INTO aerolineas (ID_AEROLINEA,NOMBRE_AEROLINEA) VALUES (3,'Interjet');
INSERT INTO aerolineas (ID_AEROLINEA,NOMBRE_AEROLINEA) VALUES (4,'Aeromexico');

COMMIT;

SELECT * FROM AEROLINEAS;


Create table AEROPUERTO (
    ID_AEROPUERTO INTEGER,
    NOMBRE_AEROPUERTO VARCHAR(128),
    PRIMARY KEY(ID_AEROPUERTO)
);

INSERT INTO AEROPUERTO (ID_AEROPUERTO,NOMBRE_AEROPUERTO) VALUES (1,'Benito Juarez');
INSERT INTO AEROPUERTO (ID_AEROPUERTO,NOMBRE_AEROPUERTO) VALUES (2,'Guanajuato');
INSERT INTO AEROPUERTO (ID_AEROPUERTO,NOMBRE_AEROPUERTO) VALUES (3,'La Paz');
INSERT INTO AEROPUERTO (ID_AEROPUERTO,NOMBRE_AEROPUERTO) VALUES (4,'Oaxaca');
COMMIT;

SELECT * FROM AEROPUERTO;


Create table MOVIMIENTOS (
    ID_MOVIMIENTO INTEGER,
    DESCRIPCION VARCHAR(128),
    PRIMARY KEY(ID_MOVIMIENTO)
);


INSERT INTO MOVIMIENTOS (ID_MOVIMIENTO,DESCRIPCION) VALUES (1,'Salida');
INSERT INTO MOVIMIENTOS (ID_MOVIMIENTO,DESCRIPCION) VALUES (2,'Llegada');
COMMIT;

SELECT * FROM MOVIMIENTOS;


Create table VUELOS (
    ID_AEROLINEA INTEGER,
    ID_AEROPUERTO INTEGER,
    ID_MOVIMIENTO INTEGER,
    DIA date,
    CONSTRAINT FK_AEROLINEA    foreign key (ID_AEROLINEA)  references aerolineas(ID_AEROLINEA),
    CONSTRAINT FK_AEROPUERTO   foreign key (ID_AEROPUERTO) references AEROPUERTO(ID_AEROPUERTO),
    CONSTRAINT FK_MOVIMIENTOS  foreign key (ID_MOVIMIENTO) references MOVIMIENTOS(ID_MOVIMIENTO)
);

INSERT INTO VUELOS (ID_AEROLINEA,ID_AEROPUERTO,ID_MOVIMIENTO,DIA) 
VALUES (1,1,1,date'2021-05-02');

INSERT INTO VUELOS (ID_AEROLINEA,ID_AEROPUERTO,ID_MOVIMIENTO,DIA) 
VALUES (2,1,1,date'2021-05-02');

INSERT INTO VUELOS (ID_AEROLINEA,ID_AEROPUERTO,ID_MOVIMIENTO,DIA) 
VALUES (3,2,2,date'2021-05-02');

INSERT INTO VUELOS (ID_AEROLINEA,ID_AEROPUERTO,ID_MOVIMIENTO,DIA) 
VALUES (4,3,2,date'2021-05-02');

INSERT INTO VUELOS (ID_AEROLINEA,ID_AEROPUERTO,ID_MOVIMIENTO,DIA) 
VALUES (1,3,2,date'2021-05-02');

INSERT INTO VUELOS (ID_AEROLINEA,ID_AEROPUERTO,ID_MOVIMIENTO,DIA) 
VALUES (2,1,1,date'2021-05-02');

INSERT INTO VUELOS (ID_AEROLINEA,ID_AEROPUERTO,ID_MOVIMIENTO,DIA) 
VALUES (2,3,1,date'2021-05-04');

INSERT INTO VUELOS (ID_AEROLINEA,ID_AEROPUERTO,ID_MOVIMIENTO,DIA) 
VALUES (3,4,1,date'2021-05-04');

INSERT INTO VUELOS (ID_AEROLINEA,ID_AEROPUERTO,ID_MOVIMIENTO,DIA) 
VALUES (3,4,1,date'2021-05-04');

COMMIT;
SELECT * FROM VUELOS;

/*
  Inicia Respuestas SQL 
*/


-- �Cu�l es el nombre aeropuerto que ha tenido mayor movimiento durante el a�o?

SELECT COUNT(V.id_aeropuerto) vuelos,v.id_aeropuerto, A.nombre_aeropuerto 
FROM vuelos V, aeropuerto A
WHERE V.id_aeropuerto = A.id_aeropuerto (+)
GROUP BY V.id_aeropuerto,v.id_aeropuerto, A.nombre_aeropuerto
ORDER BY vuelos DESC;


-- �Cu�l es el nombre aerol�nea que ha realizado mayor n�mero de vuelos durante el a�o?

SELECT COUNT(V.id_aerolinea) vuelos_aerolinea,V.id_aerolinea, A.NOMBRE_AEROLINEA
FROM vuelos V, aerolineas A
WHERE V.id_aerolinea = A.id_aerolinea (+)
GROUP BY V.id_aerolinea,A.NOMBRE_AEROLINEA
ORDER BY vuelos_aerolinea DESC;

-- �En qu� d�a se han tenido mayor n�mero de vuelos?
SELECT COUNT(DIA) NUMERO_DE_VUELOS,DIA
FROM VUELOS
GROUP BY DIA
ORDER BY NUMERO_DE_VUELOS DESC;

-- �Cu�les son las aerol�neas que tienen mas de 2 vuelos por d�a?
SELECT COUNT(DIA) NUMERO_DE_VUELOS, V.id_aerolinea, A.NOMBRE_AEROLINEA ,DIA
FROM VUELOS V, aerolineas A
WHERE V.id_aerolinea = A.id_aerolinea (+)
GROUP BY DIA,V.id_aerolinea,a.nombre_aerolinea
HAVING COUNT(DIA) > 2
ORDER BY NUMERO_DE_VUELOS DESC;
