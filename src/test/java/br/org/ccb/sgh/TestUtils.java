package br.org.ccb.sgh;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import br.org.ccb.sgh.entity.Address;
import br.org.ccb.sgh.entity.Contact;
import br.org.ccb.sgh.entity.Guest;
import br.org.ccb.sgh.entity.GuestType;
import br.org.ccb.sgh.entity.Reservation;
import br.org.ccb.sgh.entity.Room;
import br.org.ccb.sgh.entity.Status;
import br.org.ccb.sgh.entity.SupportHouse;
import br.org.ccb.sgh.http.dto.AddressDto;
import br.org.ccb.sgh.http.dto.ContactDto;
import br.org.ccb.sgh.http.dto.GuestDto;
import br.org.ccb.sgh.http.dto.InnerObjectDto;
import br.org.ccb.sgh.http.dto.ReservationDto;
import br.org.ccb.sgh.http.dto.RoomDto;
import br.org.ccb.sgh.http.dto.SupportHouseDto;

public final class TestUtils {

	public static List<SupportHouse> createSupportHouseList() {
		return Stream.of(
				SupportHouse.builder().id(1l).name("Test").cnpj("48183050000188").address(createAddressList().get(0))
						.build(),
				SupportHouse.builder().id(2l).name("Test2").cnpj("39313910000160").address(createAddressList().get(1))
						.build())
				.collect(Collectors.toList());
	}

	public static SupportHouseDto createSupportHouseDto() {
		return SupportHouseDto.builder().name("Test").cnpj("48183050000188").address(createAddressDto()).build();
	}
	
	public static SupportHouseDto createSupportHouseUpdateDto() {
		return SupportHouseDto.builder().name("Test Update").cnpj("08629758000103").address(createAddressUpdateDto()).build();
	}

	public static AddressDto createAddressDto() {
		return AddressDto.builder().street("Street").city("Test").district("District").number("1F").state("SP")
				.zipCode("11111111").build();
	}
	
	public static AddressDto createAddressUpdateDto() {
		return AddressDto.builder().street("Street Update").city("Test Update").district("District Update").number("1F Update").state("MG")
				.zipCode("99999999").build();
	}

	public static List<Room> createRoomList() {
		return Stream.of(Room.builder().id(1l).name("Room 1").floor("1").number("1A").numberOfBeds(4)
				.supportHouse(SupportHouse.builder().id(createSupportHouseList().get(0).getId()).build()).build(),
				Room.builder().id(2l).name("Room 2").floor("1").number("1B").numberOfBeds(2)
						.supportHouse(SupportHouse.builder().id(createSupportHouseList().get(1).getId()).build())
						.build())
				.collect(Collectors.toList());
	}
	
	public static List<Room> createRoomUnavailableWithReservationList() {
		return Stream.of(Room.builder().id(1l).name("Room 1").floor("1").number("1A").numberOfBeds(4)
				.supportHouse(SupportHouse.builder().id(createSupportHouseList().get(0).getId()).build())
				.reservations(createReservationList(LocalDate.now().minusDays(1), LocalDate.now().plusDays(10), LocalDate.now().minusDays(1), LocalDate.now().plusDays(10))).build(),
				Room.builder().id(2l).name("Room 2").floor("1").number("1B").numberOfBeds(2)
						.supportHouse(SupportHouse.builder().id(createSupportHouseList().get(1).getId()).build())
						.reservations(createReservationList(LocalDate.now().minusDays(1), LocalDate.now().plusDays(10), LocalDate.now().minusDays(1), null))
						.build())
				.collect(Collectors.toList());
	}
	
	public static List<Room> createRoomAvailableWithReservationList() {
		return Stream.of(Room.builder().id(1l).name("Room 1").floor("1").number("1A").numberOfBeds(4)
				.supportHouse(SupportHouse.builder().id(createSupportHouseList().get(0).getId()).build())
				.reservations(createReservationList(LocalDate.now().plusDays(1), LocalDate.now().plusDays(10), LocalDate.now().plusDays(1), LocalDate.now().plusDays(10))).build(),
				Room.builder().id(2l).name("Room 2").floor("1").number("1B").numberOfBeds(2)
						.supportHouse(SupportHouse.builder().id(createSupportHouseList().get(1).getId()).build())
						.reservations(createReservationList(LocalDate.now().minusDays(10), LocalDate.now().minusDays(1), LocalDate.now().minusDays(10), LocalDate.now().minusDays(1)))
						.build())
				.collect(Collectors.toList());
	}
	
	public static List<Reservation> createReservationList() {
		return createReservationList(LocalDate.now(), LocalDate.now(), LocalDate.now(), LocalDate.now());
	}

	public static List<Reservation> createReservationList(LocalDate initialDate, LocalDate finalDate, LocalDate checkinDate, LocalDate checkoutDate) {
		return Stream.of(
				Reservation.builder().id(1l).initialDate(initialDate).finalDate(finalDate)
						.checkinDate(checkinDate).checkoutDate(checkoutDate).observation("Observation")
						.status(Status.CONFIRMED).guests(createGuestList()).contact(createContactList().get(0))
						.build(),
				Reservation.builder().id(2l).initialDate(initialDate).finalDate(finalDate)
						.checkinDate(checkinDate).checkoutDate(checkoutDate).observation("Observation 2")
						.status(Status.PAUSED).guests(createGuestList()).contact(createContactList().get(1))
						.build())
				.collect(Collectors.toList());
	}

	public static List<Guest> createGuestList() {
		return Stream.of(
				Guest.builder().id(1l).name("Guest 1").type(createGuestTypeList().get(0))
						.address(createAddressList().get(0)).dateOfBirth(LocalDate.now()).phoneNumber("1658741258")
						.rg("126547854").cpf("19290228083").celNumber("18966547890").ministery(Boolean.TRUE)
						.baptized(Boolean.TRUE).dateOfBaptism(LocalDate.now()).prayingHouse("Central")
						.observation("observation").build(),
				Guest.builder().id(2l).name("Guest 2").type(createGuestTypeList().get(1))
						.address(createAddressList().get(1)).dateOfBirth(LocalDate.now()).rg("226547854")
						.cpf("24953683013").phoneNumber("1166987451").celNumber("11988887746").ministery(Boolean.FALSE)
						.baptized(Boolean.FALSE).dateOfBaptism(LocalDate.now()).prayingHouse("Central")
						.observation("observation").build())
				.collect(Collectors.toList());
	}

	private static List<Address> createAddressList() {
		return Stream.of(
				Address.builder().id(1l).street("Street").city("Test").district("District").number("1F").state("SP")
						.zipCode("11111111").build(),
				Address.builder().id(2l).street("Street2").city("Test2").district("District2").number("1G").state("MG")
						.zipCode("22222222").build())
				.collect(Collectors.toList());
	}

	private static List<GuestType> createGuestTypeList() {
		return Stream.of(GuestType.builder().id(1l).description("Guest").build(),
				GuestType.builder().id(2l).description("Companion").build()).collect(Collectors.toList());
	}

	private static List<Contact> createContactList() {
		return Stream.of(
				Contact.builder().id(1l).name("Contact 1").phoneNumber("1666987450").celNumber("16988887745")
						.ministery("Diácono").relationship("Irmão").address(createAddressList().get(0))
						.observation("observation").build(),
				Contact.builder().id(2l).name("Contact 2").phoneNumber("1666987451").celNumber("16988887746")
						.ministery("Ancião").relationship("Cunhado").address(createAddressList().get(1))
						.observation("observation").build())
				.collect(Collectors.toList());
	}

	public static RoomDto createRoomDto() {
		return RoomDto.builder().id(1l).name("Room 1").floor("1").number("1A").numberOfBeds(4)
				.supportHouse(createInnerObjectDto(1l)).build();
	}
	
	public static RoomDto createRoomUpdateDto() {
		return RoomDto.builder().name("Room Update").floor("2").number("1A Update").numberOfBeds(1)
				.supportHouse(createInnerObjectDto(1l)).build();
	}

	public static InnerObjectDto createInnerObjectDto(Long id) {
		return InnerObjectDto.builder().id(id).build();
	}

	public static GuestDto createGuestDto() {
		return GuestDto.builder().name("Guest 1").type(1l).address(createAddressDto()).dateOfBirth(LocalDate.now())
				.dateOfBaptism(LocalDate.now()).rg("126547854").cpf("19290228083").phoneNumber("1658741258")
				.celNumber("18966547890").ministery(true).baptized(true).prayingHouse("Praying House 1")
				.observation("Observation").build();
	}
	
	public static GuestDto createUpdateGuestDto() {
		return GuestDto.builder().name("Guest Update").type(2l).address(createAddressUpdateDto()).dateOfBirth(LocalDate.now())
				.dateOfBaptism(LocalDate.now()).rg("999999999").cpf("87144689053").phoneNumber("9999999999")
				.celNumber("99999999999").ministery(false).baptized(false).prayingHouse("Praying House Update")
				.observation("Observation Update").build();
	}

	public static ReservationDto createReservationDto() {
		return ReservationDto.builder().initialDate(LocalDate.now()).finalDate(LocalDate.now())
				.checkinDate(LocalDate.now()).checkoutDate(LocalDate.now()).room(createInnerObjectDto(1l))
				.guests(List.of(createInnerObjectDto(1l), createInnerObjectDto(2l)))
				.contact(createContactDto())
				.observation("Observation")
				.status("CONFIRMED")
				.build();
	}
	
	public static ReservationDto createReservationUpdateDto() {
		return ReservationDto.builder().initialDate(LocalDate.now()).finalDate(LocalDate.now())
				.checkinDate(LocalDate.now()).checkoutDate(LocalDate.now()).room(createInnerObjectDto(1l))
				.guests(List.of(createInnerObjectDto(1l), createInnerObjectDto(2l)))
				.contact(createContactUpdateDto())
				.observation("Observation Update")
				.status("CANCELED")
				.build();
	}
	
	public static ContactDto createContactDto() {
		return ContactDto.builder().name("Contact 1").phoneNumber("1885478965").celNumber("15554785982")
				.ministery("Ministery").relationship("Relationship").address(createAddressDto())
				.observation("Observation").build();
	}
	
	public static ContactDto createContactUpdateDto() {
		return ContactDto.builder().name("Contact Update").phoneNumber("9999999999").celNumber("99999999999")
				.ministery("Ministery Update").relationship("Relationship Update").address(createAddressUpdateDto())
				.observation("Observation Update").build();
	}
}
