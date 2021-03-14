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
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class RoomSpecification implements Specification<Room> {

	private static final long serialVersionUID = -7733399014842439846L;

	private static final String CHECKIN_DATE = "checkinDate";
	private static final String CHECKOUT_DATE = "checkoutDate";

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
		
		if (params.getNumberOfBeds() != null) {
			predicates.add(criteriaBuilder.equal(root.get("numberOfBeds"), params.getNumberOfBeds()));
		}
		
		if (params.getReservationId() != null || params.getSupportHouseId() != null) {
			if(params.getReservationId() != null) {				
				predicates.add(criteriaBuilder.equal(root.join("reservations").get("id"), params.getReservationId()));
			}
			if(params.getSupportHouseId() != null) {
				predicates.add(criteriaBuilder.equal(root.join("reservations").get("supportHouse").get("id"), params.getSupportHouseId()));
			}
		}
		
		Subquery<Reservation> subquery = query.subquery(Reservation.class);
		Root<Reservation> reservation = subquery.from(Reservation.class);
		
		getAvailability(root, criteriaBuilder, predicates, subquery, reservation);
		
		return query.where(criteriaBuilder.and(predicates.toArray(new Predicate[0]))).distinct(true).getRestriction();
	}

	private void getAvailability(Root<Room> root, CriteriaBuilder criteriaBuilder, List<Predicate> predicates,
			Subquery<Reservation> subquery, Root<Reservation> reservation) {
		if (params.getInitalDate() != null && params.getFinalDate() != null) {
			Subquery<Reservation> subquery2 = subquery.select(reservation.get("room").get("id")).distinct(true)
						.where(
								criteriaBuilder.and(
										criteriaBuilder.equal(root.get("id"), reservation.get("room").get("id")),
								criteriaBuilder.or(
								criteriaBuilder.between(criteriaBuilder.literal(params.getInitalDate()), reservation.get(CHECKIN_DATE), reservation.get(CHECKOUT_DATE)),
								criteriaBuilder.between(criteriaBuilder.literal(params.getFinalDate()), reservation.get(CHECKIN_DATE), reservation.get(CHECKOUT_DATE)),
								criteriaBuilder.between(reservation.get(CHECKIN_DATE), criteriaBuilder.literal(params.getInitalDate()), criteriaBuilder.literal(params.getFinalDate()))
									)));
			
			if (Boolean.TRUE.equals(params.getAvailable())) {
				predicates.add(criteriaBuilder.exists(subquery2).not());
			} else {
				predicates.add(criteriaBuilder.exists(subquery2));
			}
		}
	}

}
