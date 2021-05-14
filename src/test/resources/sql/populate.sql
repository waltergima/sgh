INSERT INTO GuestType
(id, description)
VALUES
(1, 'Guest'),
(2, 'Companion');

INSERT INTO Address
(id, city, district, `number`, state, street, zipCode)
VALUES
(1, 'Test', 'District', '1F', 'SP', 'Street', '11111111'),
(2, 'Test2', 'District2', '1G', 'MG', 'Street2', '22222222'),
(3, 'Test3', 'District3', '1H', 'AL', 'Street3', '33333333'),
(4, 'Test4', 'District4', '1I', 'AM', 'Street4', '44444444'),
(5, 'Test5', 'District5', '1J', 'AM', 'Street5', '55555555'),
(6, 'Test6', 'District6', '1K', 'AM', 'Street6', '66666666'),
(7, 'Test7', 'District7', '1L', 'AM', 'Street7', '77777777'),
(8, 'Test8', 'District8', '1M', 'AM', 'Street8', '88888888'),
(9, 'Test9', 'District9', '1N', 'AM', 'Street9', '99999999');

INSERT INTO SupportHouse
(id, cnpj, name, address_id)
VALUES
(1, '48183050000188', 'Test', 1),
(2, '76085751000110', 'Test 2', 4);

INSERT INTO Contact
(id, celNumber, ministery, name, observation, phoneNumber, relationship, address_id)
VALUES
(1, '16988887745', 'Diácono', 'Contact 1', 'obs', '1666987450', 'Irmão', 1),
(2, '16988887745', 'Diácono', 'Contact 2', 'obs', '1666987450', 'Irmão', 5),
(3, '16988887745', 'Diácono', 'Contact 3', 'obs', '1666987450', 'Irmão', 6),
(4, '16988887745', 'Diácono', 'Contact 4', 'obs', '1666987450', 'Irmão', 7),
(5, '16988887745', 'Diácono', 'Contact 5', 'obs', '1666987450', 'Irmão', 8),
(6, '16988887745', 'Diácono', 'Contact 6', 'obs', '1666987450', 'Irmão', 9);

INSERT INTO Room
(id, floor, name, `number`, numberOfBeds, support_house_id)
VALUES
(1, '1', 'Room 1', '1A', 4, 1),
(2, '2', 'Room 2', '2A', 1, 1),
(3, '3', 'Room 3', '3A', 1, 1),
(4, '4', 'Room 4', '4A', 1, 1),
(5, '5', 'Room 5', '5A', 1, 1),
(6, '6', 'Room 6', '6A', 1, 1); 

INSERT INTO Reservation
(id, initialDate, finalDate, checkinDate, checkoutDate, observation, status, contact_id, room_id)
VALUES
(1, '2020-11-01', '2020-11-09', '2020-11-09', '2020-11-12', 'none', 'CONFIRMED', 1, 1),
(2, DATEADD('DAY', -1, CURRENT_DATE()), DATEADD('DAY', +10, CURRENT_DATE()), DATEADD('DAY', +1, CURRENT_DATE()), DATEADD('DAY', +10, CURRENT_DATE()), 'none', 'CONFIRMED', 2, 2),
(3, DATEADD('DAY', +1, CURRENT_DATE()), DATEADD('DAY', +10, CURRENT_DATE()), DATEADD('DAY', -1, CURRENT_DATE()), null, 'none', 'CONFIRMED', 3, 3),
(4, DATEADD('DAY', -10, CURRENT_DATE()), DATEADD('DAY', -5, CURRENT_DATE()), DATEADD('DAY', -1, CURRENT_DATE()), DATEADD('DAY', +10, CURRENT_DATE()), 'none', 'CONFIRMED', 4, 4),
(5, DATEADD('DAY', +1, CURRENT_DATE()), DATEADD('DAY', +10, CURRENT_DATE()), DATEADD('DAY', +1, CURRENT_DATE()), DATEADD('DAY', +10, CURRENT_DATE()), 'none', 'CONFIRMED', 5, 5),
(6, DATEADD('DAY', -10, CURRENT_DATE()), DATEADD('DAY', -1, CURRENT_DATE()), DATEADD('DAY', -10, CURRENT_DATE()), DATEADD('DAY', -1, CURRENT_DATE()), 'none', 'CONFIRMED', 6, 6);


INSERT INTO Guest
(id, baptized, celNumber, cpf, dateOfBirth, ministery, name, observation, phoneNumber, prayingHouse, rg, address_id, type_id, dateOfBaptism)
VALUES
(1, true, '18966547890', '19290228083', '2000-12-11', true, 'Guest 1', 'observation', '1658741258', 'Central', '126547854', 1, 1, '2019-01-01'),
(2, false, '11988887746', '24953683013', '2001-11-10', false, 'Guest 2', 'observation', '1166987451', 'Central', '226547854', 2, 2, '2018-12-12'),
(3, false, '11988887746', '24953683013', '2001-11-10', false, 'Guest 3', 'observation', '1166987451', 'Central', '226547854', 3, 2, '2018-12-12'),
(4, false, '11988887746', '24953683013', '2001-11-10', false, 'Guest 4', 'observation', '1166987451', 'Central', '226547854', 4, 2, '2018-12-12'),
(5, false, '11988887746', '24953683013', '2001-11-10', false, 'Guest 5', 'observation', '1166987451', 'Central', '226547854', 5, 2, '2018-12-12'),
(6, false, '11988887746', '24953683013', '2001-11-10', false, 'Guest 6', 'observation', '1166987451', 'Central', '226547854', 6, 2, '2018-12-12'),
(7, false, '11988887746', '24953683013', '2001-11-10', false, 'Guest 7', 'observation', '1166987451', 'Central', '226547854', 7, 2, '2018-12-12');

INSERT INTO reservations_guests (reservation_id, guest_id) 
VALUES
(1, 1),
(1, 2),
(2, 3),
(3, 4),
(4, 5),
(5, 6),
(6, 7);