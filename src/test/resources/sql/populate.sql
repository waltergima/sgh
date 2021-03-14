INSERT INTO GuestType
(id, description)
VALUES
(1, 'Guest'),
(2, 'Companion');

INSERT INTO Address
(id, city, district, `number`, state, street, zipCode)
VALUES
(1, 'Test', 'District', '1F', 'SP', 'Street', '11111111'),
(2, 'Test2', 'District2', '1G', 'MG', 'Street2', '22222222');

INSERT INTO SupportHouse
(id, cnpj, name, address_id)
VALUES(1, '48183050000188', 'Test', 1);

INSERT INTO Contact
(id, celNumber, ministery, name, observation, phoneNumber, relationship, address_id)
VALUES(1, '16988887745', 'Diácono', 'Contact 1', 'obs', '1666987450', 'Irmão', 1);

INSERT INTO Room
(id, floor, name, `number`, numberOfBeds, support_house_id)
VALUES(1, '1', 'Room 1', '1A', 4, 1);


INSERT INTO Reservation
(id, checkinDate, checkoutDate, finalDate, initialDate, observation, status, contact_id, room_id, support_house_id)
VALUES(1, '2020-11-09', '2020-11-12', '2020-11-09', '2020-11-1', 'none', 'CONFIRMED', 1, 1, 1);


INSERT INTO Guest
(id, baptized, celNumber, cpf, dateOfBirth, ministery, name, observation, phoneNumber, prayingHouse, rg, address_id, type_id, dateOfBaptism)
VALUES
(1, true, '18966547890', '19290228083', '2000-12-11', true, 'Guest 1', 'observation', '1658741258', 'Central', '126547854', 1, 1, '2019-01-01'),
(2, false, '11988887746', '24953683013', '2001-11-10', false, 'Guest 2', 'observation', '1166987451', 'Central', '226547854', 2, 2, '2018-12-12');

INSERT INTO reservations_guests (reservation_id, guest_id) VALUES(1, 1);
INSERT INTO reservations_guests (reservation_id, guest_id) VALUES(1, 2);