-- sgh.Address definition

CREATE TABLE Address (
  id bigint(20) auto_increment NOT NULL,
  city varchar(255) DEFAULT NULL,
  district varchar(255) DEFAULT NULL,
  number varchar(255) DEFAULT NULL,
  state varchar(255) DEFAULT NULL,
  street varchar(255) DEFAULT NULL,
  zipCode varchar(255) DEFAULT NULL,
  PRIMARY KEY (id)
);


-- sgh.GuestType definition

CREATE TABLE GuestType (
  id bigint(20) auto_increment NOT NULL,
  description varchar(255) DEFAULT NULL,
  PRIMARY KEY (id)
);


-- sgh.hibernate_sequence definition

-- CREATE TABLE hibernate_sequence (
  -- next_val bigint(20) DEFAULT NULL
-- );

-- sgh.Contact definition

CREATE TABLE Contact (
  id bigint(20) auto_increment NOT NULL,
  celNumber varchar(255) DEFAULT NULL,
  ministery varchar(255) DEFAULT NULL,
  name varchar(255) DEFAULT NULL,
  observation varchar(255) DEFAULT NULL,
  phoneNumber varchar(255) DEFAULT NULL,
  relationship varchar(255) DEFAULT NULL,
  address_id bigint(20) DEFAULT NULL,
  PRIMARY KEY (id),
  UNIQUE KEY UK_510n2cfle7gkafydaj6na02ur (address_id),
  CONSTRAINT FKj0ag8jhilwi89v3crnwqu5i3w FOREIGN KEY (address_id) REFERENCES Address (id)
);


-- sgh.Guest definition

CREATE TABLE Guest (
  id bigint(20) auto_increment NOT NULL,
  baptized bit(1) DEFAULT NULL,
  celNumber varchar(255) DEFAULT NULL,
  cpf varchar(255) DEFAULT NULL,
  dateOfBirth date DEFAULT NULL,
  ministery bit(1) DEFAULT NULL,
  name varchar(255) DEFAULT NULL,
  observation varchar(255) DEFAULT NULL,
  phoneNumber varchar(255) DEFAULT NULL,
  prayingHouse varchar(255) DEFAULT NULL,
  rg varchar(255) DEFAULT NULL,
  address_id bigint(20) DEFAULT NULL,
  type_id bigint(20) DEFAULT NULL,
  dateOfBaptism date DEFAULT NULL,
  PRIMARY KEY (id),
  UNIQUE KEY UK_6ob6uk0mpirnuspmrvpgxrs84 (address_id),
  CONSTRAINT FK9dsr92l06ocy5kgap5g46w3nw FOREIGN KEY (address_id) REFERENCES Address (id),
  CONSTRAINT FKyc35c80s51sagtptdit8hk4w FOREIGN KEY (type_id) REFERENCES GuestType (id)
);


-- sgh.SupportHouse definition

CREATE TABLE SupportHouse (
  id bigint(20) auto_increment NOT NULL,
  cnpj varchar(255) DEFAULT NULL,
  name varchar(255) DEFAULT NULL,
  address_id bigint(20) NOT NULL,
  PRIMARY KEY (id),
  UNIQUE KEY UK_paax8y2ckggcrgfr5e7efg8ae (address_id),
  CONSTRAINT FKonxnxs8463pa1qt392mpwtgx3 FOREIGN KEY (address_id) REFERENCES Address (id)
);


-- sgh.Room definition

CREATE TABLE Room (
  id bigint(20) auto_increment NOT NULL,
  floor varchar(255) DEFAULT NULL,
  name varchar(255) DEFAULT NULL,
  number varchar(255) DEFAULT NULL,
  numberOfBeds int(11) DEFAULT NULL,
  support_house_id bigint(20) DEFAULT NULL,
  PRIMARY KEY (id),
  CONSTRAINT FK41vfxf5cx67j9igekc3njw4o2 FOREIGN KEY (support_house_id) REFERENCES SupportHouse (id)
);


-- sgh.Reservation definition

CREATE TABLE Reservation (
  id bigint(20) auto_increment NOT NULL,
  checkinDate date DEFAULT NULL,
  checkoutDate date DEFAULT NULL,
  finalDate date DEFAULT NULL,
  initialDate date DEFAULT NULL,
  observation varchar(255) DEFAULT NULL,
  status varchar(255) DEFAULT NULL,
  contact_id bigint(20) DEFAULT NULL,
  room_id bigint(20) DEFAULT NULL,
  test date DEFAULT NULL,
  PRIMARY KEY (id),
  UNIQUE KEY UK_5xgbh4nvscg4n4scng5fxr4gx (contact_id),
  CONSTRAINT FKprp9xfstf1buic0e0mg1tndfu FOREIGN KEY (room_id) REFERENCES Room (id),
  CONSTRAINT FKqmnw4fdgia8xvdpqny80t288j FOREIGN KEY (contact_id) REFERENCES Contact (id)
);


-- sgh.reservations_guests definition

CREATE TABLE reservations_guests (
  reservation_id bigint(20) NOT NULL,
  guest_id bigint(20) NOT NULL,
  CONSTRAINT FK1ow8olxrms1lqt6brgdv3dvlw FOREIGN KEY (guest_id) REFERENCES Guest (id),
  CONSTRAINT FKf3pygh6bio6c4o39f7mu6tydi FOREIGN KEY (reservation_id) REFERENCES Reservation (id)
);