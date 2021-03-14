INSERT INTO GuestType (id, description) VALUES(1, 'Hóspede');
INSERT INTO GuestType (id, description) VALUES(2, 'Acompanhante');

INSERT INTO Address (id, city, district, `number`, state, street, zipCode) VALUES(1, 'Franca', 'Jardim América', '1F', 'SP', 'Rua 1', '14409012');
INSERT INTO Address (id, city, district, `number`, state, street, zipCode) VALUES(4, 'Franca', 'Jardim América', '2F', 'SP', 'Rua 3', '14409012');
INSERT INTO Address (id, city, district, `number`, state, street, zipCode) VALUES(5, 'Barretos', 'Jardim Europa', '3G', 'SP', 'Rua 2', '12030530');
INSERT INTO Address (id, city, district, `number`, state, street, zipCode) VALUES(6, 'Manaus', 'Centro', '25', 'AM', 'Rua 5', '78895874');
INSERT INTO Address (id, city, district, `number`, state, street, zipCode) VALUES(7, 'Recife', 'Jardim Botanico', '125', 'PE', 'Rua 6', '88895874');


INSERT INTO SupportHouse (id, cnpj, name, address_id) VALUES(1, '04170023000103', 'Test', 1);
INSERT INTO SupportHouse (id, cnpj, name, address_id) VALUES(2, '45488909000179', 'Test 1', 5);

INSERT INTO Guest (id, baptized, celNumber, cpf, dateOfBaptism, dateOfBirth, ministery, name, observation, phoneNumber, prayingHouse, rg, address_id, type_id) VALUES(2, 0, '16998877445', '82056528053', '2020-02-02', '1990-02-02', 1, 'Geraldina Manuela', 'Alimentação sem restrições', '16998877445', 'Jardim América', '224567898', 4, 2);

INSERT INTO Room (id, floor, name, `number`, numberOfBeds, support_house_id) VALUES(1, '1', 'Quarto 01', '1F', 3, 1);
INSERT INTO Room (id, floor, name, `number`, numberOfBeds, support_house_id) VALUES(2, '2', 'Quarto 02', '2G', 2, 2);
INSERT INTO Room (id, floor, name, `number`, numberOfBeds, support_house_id) VALUES(3, '1', 'Quarto 02', '2A', 4, 1);

INSERT INTO Contact (id, celNumber, ministery, name, observation, phoneNumber, relationship, address_id) VALUES(1, '16988745224', 'Ancião', 'José Contato', 'Observação', '1688554789', 'Irmão', 6);
INSERT INTO Contact (id, celNumber, ministery, name, observation, phoneNumber, relationship, address_id) VALUES(2, '19988745224', 'Diácono', 'João Contato', 'Observação 2', '1988554789', 'Amigo', 7);

INSERT INTO Reservation (id, checkinDate, checkoutDate, finalDate, initialDate, observation, status, contact_id, room_id) VALUES(1, NULL, NULL, '2021-02-20', '2021-02-01', 'Observação', 'CONFIRMED', 1, 1);
INSERT INTO Reservation (id, checkinDate, checkoutDate, finalDate, initialDate, observation, status, contact_id, room_id) VALUES(2, '2021-02-02', '2021-02-18', '2021-02-20', '2021-02-01', 'Observação', 'CONFIRMED', 2, 1);

INSERT INTO reservations_guests (reservation_id, guest_id) VALUES(1, 2);
INSERT INTO reservations_guests (reservation_id, guest_id) VALUES(2, 2);
