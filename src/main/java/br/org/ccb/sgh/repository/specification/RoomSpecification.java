package br.org.ccb.sgh.repository.specification;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Subquery;

import org.springframework.data.jpa.domain.Specification;

import br.org.ccb.sgh.entity.Reservation;
import br.org.ccb.sgh.entity.Room;
import br.org.ccb.sgh.http.dto.RoomRequestParamsDto;
import br.org.ccb.sgh.util.ReservationStatus;
import br.org.ccb.sgh.util.RoomStatus;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class RoomSpecification implements Specification<Room> {

	private static final long serialVersionUID = -7733399014842439846L;

	private static final String CHECKIN_DATE = "checkinDate";
	private static final String CHECKOUT_DATE = "checkoutDate";
	private static final String INITIAL_DATE = "initialDate";
	private static final String FINAL_DATE = "finalDate";

	private RoomRequestParamsDto params;

	@Override
	public Predicate toPredicate(Root<Room> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
		List<Predicate> predicates = new ArrayList<>();
		if (params.getId() != null) {
			predicates.add(criteriaBuilder.equal(root.get("id"), params.getId()));
		}
		if (params.getName() != null) {
			predicates.add(criteriaBuilder.like(root.get("name"), "%" + params.getName() + "%"));
		}
		if (params.getFloor() != null) {
			predicates.add(criteriaBuilder.like(root.get("floor"), "%" + params.getFloor() + "%"));
		}
		if (params.getNumber() != null) {
			predicates.add(criteriaBuilder.like(root.get("number"), "%" + params.getNumber() + "%"));
		}
		if (params.getNumberOfBeds() != null) {
			predicates.add(criteriaBuilder.equal(root.get("numberOfBeds"), params.getNumberOfBeds()));
		}
		
		if (params.getReservationId() != null) {
			predicates.add(criteriaBuilder.equal(root.join("reservations").get("id"), params.getReservationId()));
		}
		
		if (params.getSupportHouseId() != null) {
			predicates.add(criteriaBuilder.equal(root.join("supportHouse").get("id"), params.getSupportHouseId()));
		}
		
		Subquery<Reservation> subquery = query.subquery(Reservation.class);
		Root<Reservation> reservation = subquery.from(Reservation.class);
		
		getAvailability(root, criteriaBuilder, predicates, subquery, reservation);
		
		return query.where(criteriaBuilder.and(predicates.toArray(new Predicate[0]))).distinct(true).getRestriction();
	}

	private void getAvailability(Root<Room> root, CriteriaBuilder criteriaBuilder, List<Predicate> predicates,
			Subquery<Reservation> subquery, Root<Reservation> reservation) {
		if (params.getStatus() != null) {
			
			Predicate where = criteriaBuilder.and(
					criteriaBuilder.equal(root.get("id"), reservation.get("room").get("id")),
					criteriaBuilder.equal(criteriaBuilder.literal(ReservationStatus.CONFIRMED),
							reservation.get("status")),
					getStatusPredicate(root, criteriaBuilder, reservation));

			Subquery<Reservation> subquery2 = subquery.select(reservation.get("room").get("id")).distinct(true)
						.where(
								where);
			
			predicates.add(criteriaBuilder.exists(subquery2));
		}
	}
	
	private Predicate getStatusPredicate(Root<Room> root, CriteriaBuilder criteriaBuilder, Root<Reservation> reservation) {
		if(params.getStatus().equals(RoomStatus.AVAILABLE.toString())) {
			return criteriaBuilder.or(criteriaBuilder.isEmpty(root.get("reservations")),
					criteriaBuilder.and(getReservedPredicate(criteriaBuilder, reservation).not(), getOccupiedPredicate(criteriaBuilder, reservation).not()));
		}
		if(params.getStatus().equals(RoomStatus.RESERVED.toString())) {
			return getReservedPredicate(criteriaBuilder, reservation);
		}
		return getOccupiedPredicate(criteriaBuilder, reservation);
	}

	private Predicate getReservedPredicate(CriteriaBuilder criteriaBuilder, Root<Reservation> reservation) {
		return criteriaBuilder.or(
				criteriaBuilder.between(criteriaBuilder.literal(params.getInitialDate()), reservation.get(INITIAL_DATE),
						reservation.get(FINAL_DATE)),
				criteriaBuilder.between(criteriaBuilder.literal(params.getFinalDate()), reservation.get(INITIAL_DATE),
						reservation.get(FINAL_DATE)),
				criteriaBuilder.between(reservation.get(INITIAL_DATE), criteriaBuilder.literal(params.getInitialDate()),
						criteriaBuilder.literal(params.getFinalDate())));
	}
	
	private Predicate getOccupiedPredicate(CriteriaBuilder criteriaBuilder, Root<Reservation> reservation) {
		return criteriaBuilder.or(
				criteriaBuilder.between(criteriaBuilder.literal(params.getInitialDate()), reservation.get(CHECKIN_DATE),
						reservation.get(CHECKOUT_DATE)),
				criteriaBuilder.or(criteriaBuilder.isNull(reservation.get(CHECKOUT_DATE)),
						criteriaBuilder.between(criteriaBuilder.literal(params.getFinalDate()),
								reservation.get(CHECKIN_DATE), reservation.get(CHECKOUT_DATE))),
				criteriaBuilder.between(reservation.get(CHECKIN_DATE), criteriaBuilder.literal(params.getInitialDate()),
						criteriaBuilder.literal(params.getFinalDate())));
	}

}
