INSERT INTO autore (id, nome, cognome, data_nascita, data_morte, nazionalita, fotografia) VALUES (-1, 'Dante', 'Alighieri', '1265-06-01', '1321-09-14', 'Italiana', NULL);

INSERT INTO autore (id, nome, cognome, data_nascita, data_morte, nazionalita, fotografia) VALUES (-2, 'Jane', 'Austen', '1775-12-16', '1817-07-18', 'Britannica', NULL);

INSERT INTO autore (id, nome, cognome, data_nascita, data_morte, nazionalita, fotografia) VALUES (-3, 'Lev', 'Tolstoj', '1828-09-09', '1910-11-20', 'Russa', NULL);
INSERT INTO autore (id, nome, cognome, data_nascita, data_morte, nazionalita, fotografia) VALUES (-4, 'Gabriel', 'García Márquez', '1927-03-06', '2014-04-17', 'Colombiana', NULL);
INSERT INTO autore (id, nome, cognome, data_nascita, data_morte, nazionalita, fotografia) VALUES (-5, 'George', 'Orwell', '1903-06-25', '1950-01-21', 'Britannica', NULL);
INSERT INTO autore (id, nome, cognome, data_nascita, data_morte, nazionalita, fotografia) VALUES (-6, 'Franz', 'Kafka', '1883-07-03', '1924-06-03', 'Austro-Ungarica', NULL);
INSERT INTO autore (id, nome, cognome, data_nascita, data_morte, nazionalita, fotografia) VALUES (-7, 'Haruki', 'Murakami', '1949-01-12', NULL, 'Giapponese', NULL);
INSERT INTO autore (id, nome, cognome, data_nascita, data_morte, nazionalita, fotografia) VALUES (-8, 'Isabel', 'Allende', '1942-08-02', NULL, 'Cilena', NULL);
INSERT INTO autore (id, nome, cognome, data_nascita, data_morte, nazionalita, fotografia) VALUES (-9, 'Umberto', 'Eco', '1932-01-05', '2016-02-19', 'Italiana', NULL);
INSERT INTO autore (id, nome, cognome, data_nascita, data_morte, nazionalita, fotografia) VALUES (-10, 'J.K.', 'Rowling', '1965-07-31', NULL, 'Britannica', NULL);
INSERT INTO autore (id, nome, cognome, data_nascita, data_morte, nazionalita, fotografia) VALUES (-11, 'Ernest', 'Hemingway', '1899-07-21', '1961-07-02', 'Statunitense', NULL);
INSERT INTO autore (id, nome, cognome, data_nascita, data_morte, nazionalita, fotografia) VALUES (-12, 'Margaret', 'Atwood', '1939-11-18', NULL, 'Canadese', NULL);

INSERT INTO libro (id, titolo, anno_pubblicazione) VALUES (-12, 'Guerra e Pace', '1869-01-01');
INSERT INTO libro (id, titolo, anno_pubblicazione) VALUES (-13, 'Cent’anni di solitudine', '1967-05-30');
INSERT INTO libro (id, titolo, anno_pubblicazione) VALUES (-14, '1984', '1949-06-08');
INSERT INTO libro (id, titolo, anno_pubblicazione) VALUES (-15, 'La metamorfosi', '1915-01-01');
INSERT INTO libro (id, titolo, anno_pubblicazione) VALUES (-16, 'Norwegian Wood', '1987-09-04');
INSERT INTO libro (id, titolo, anno_pubblicazione) VALUES (-17, 'La casa degli spiriti', '1982-01-01');
INSERT INTO libro (id, titolo, anno_pubblicazione) VALUES (-18, 'Il nome della rosa', '1980-01-01');
INSERT INTO libro (id, titolo, anno_pubblicazione) VALUES (-19, 'Harry Potter e la Pietra Filosofale', '1997-06-26');
INSERT INTO libro (id, titolo, anno_pubblicazione) VALUES (-20, 'Il vecchio e il mare', '1952-09-01');
INSERT INTO libro (id, titolo, anno_pubblicazione) VALUES (-21, 'Il racconto dell’ancella', '1985-01-01');

INSERT INTO libro (id, titolo, anno_pubblicazione) VALUES (-10, 'Divina Commedia', '1320-01-01');

INSERT INTO libro (id, titolo, anno_pubblicazione) VALUES (-11, 'Orgoglio e Pregiudizio', '1813-01-28');

INSERT INTO libro_autori (libri_id, autori_id) VALUES (-10, -1);

INSERT INTO libro_autori (libri_id, autori_id) VALUES (-11, -2);
INSERT INTO libro_autori (libri_id, autori_id) VALUES (-12, -3);
INSERT INTO libro_autori (libri_id, autori_id) VALUES (-13, -4);
INSERT INTO libro_autori (libri_id, autori_id) VALUES (-14, -5);
INSERT INTO libro_autori (libri_id, autori_id) VALUES (-15, -6);
INSERT INTO libro_autori (libri_id, autori_id) VALUES (-16, -7);
INSERT INTO libro_autori (libri_id, autori_id) VALUES (-17, -8);
INSERT INTO libro_autori (libri_id, autori_id) VALUES (-18, -9);
INSERT INTO libro_autori (libri_id, autori_id) VALUES (-19, -10);
INSERT INTO libro_autori (libri_id, autori_id) VALUES (-20, -11);
INSERT INTO libro_autori (libri_id, autori_id) VALUES (-21, -12);



INSERT INTO utente (id, name, surname, email,eta) VALUES (100, 'Mario', 'Rossi', 'admin@example.com',30);
INSERT INTO credentials (id, username, password, role, user_id) VALUES (200, 'adm', '$2a$12$s3QTUipQcQAOXN0aZ0scie6KFTZ9ISeU6nnIUmeeXwgrL4AsnJ4.u', 'ROLE_ADMIN', 100);
INSERT INTO utente (id, name, surname, email,eta) VALUES (101, 'Giulia', 'Bianchi', 'user@example.com',31);
INSERT INTO credentials (id, username, password, role, user_id) VALUES (201, 'ute', '$2a$12$x/pn.th/advauZHiRKICZer7ObgOzLpyOwZdZ4GrrOaaGKgS4yeee', 'ROLE_USER', 101);