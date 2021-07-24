package br.org.ccb.sgh.repository.specification;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import br.org.ccb.sgh.entity.Reservation;
import br.org.ccb.sgh.http.dto.ReservationRequestParamsDto;
import br.org.ccb.sgh.util.ReservationStatus;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ReservationSpecification implements Specification<Reservation> {
	private static final long serialVersionUID = -8019516470168264457L;

	private ReservationRequestParamsDto params;

	@Override
	public Predicate toPredicate(Root<Reservation> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
		List<Predicate> predicates = new ArrayList<>();
		if (params.getId() != null) {
			predicates.add(criteriaBuilder.equal(root.get("id"), params.getId()));
		}
		if (params.getInitialDate() != null) {
			predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("initialDate"), params.getInitialDate()));
		}
		if (params.getFinalDate() != null) {
			predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("finalDate"), params.getFinalDate()));
		}
		if (params.getCheckinDate() != null) {
			predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("checkinDate"), params.getCheckinDate()));
		}
		if (params.getCheckoutDate() != null) {
			predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("checkoutDate"), params.getCheckoutDate()));
		}
		if (params.getRoomId() != null) {
			predicates.add(criteriaBuilder.equal(root.get("room").get("id"), params.getRoomId()));
		}
		if (params.getRoomName() != null) {
			predicates.add(criteriaBuilder.like(root.join("room", JoinType.INNER).get("name"),
					"%" + params.getRoomName() + "%"));
		}
		if (params.getGuestId() != null) {
			predicates.add(criteriaBuilder.equal(root.join("guests", JoinType.INNER).get("id"), params.getGuestId()));
		}
		if (params.getGuestName() != null) {
			predicates.add(criteriaBuilder.like(root.join("guests", JoinType.INNER).get("name"),
					"%" + params.getGuestName() + "%"));
		}
		if (params.getContactId() != null) {
			predicates
					.add(criteriaBuilder.equal(root.get("contact").get("id"), params.getContactId()));
		}
		if (params.getContactName() != null) {
			predicates.add(criteriaBuilder.like(root.join("contact", JoinType.INNER).get("name"),
					"%" + params.getContactName() + "%"));
		}
		if (params.getSupportHouseId() != null) {
			predicates.add(criteriaBuilder.equal(root.join("room", JoinType.INNER).get("supportHouse").get("id"),
					params.getSupportHouseId()));
		}
		if (params.getStatus() != null) {
			predicates.add(criteriaBuilder.equal(root.get("status"), ReservationStatus.valueOf(params.getStatus())));
		}

		return query.where(criteriaBuilder.and(predicates.toArray(new Predicate[0]))).distinct(true).getRestriction();
	}

}
