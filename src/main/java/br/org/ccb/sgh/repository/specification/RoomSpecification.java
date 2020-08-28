package br.org.ccb.sgh.repository.specification;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import br.org.ccb.sgh.entity.Room;
import br.org.ccb.sgh.http.dto.RoomRequestParamsDto;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class RoomSpecification implements Specification<Room> {

	private static final long serialVersionUID = -7733399014842439846L;

	private static final String CHECKOUT_DATE = "checkoutDate";

	private static final String RESERVATIONS = "reservations";

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
		if (Boolean.TRUE.equals(params.getAvailable())) {
			Join<Object, Object> reservations = root.join(RESERVATIONS, JoinType.LEFT);
			predicates.add(criteriaBuilder.or(criteriaBuilder.isTrue(reservations.isNull()),
					criteriaBuilder.and(criteriaBuilder.isTrue(reservations.get(CHECKOUT_DATE).isNotNull()),
							criteriaBuilder.lessThanOrEqualTo(reservations.get(CHECKOUT_DATE), LocalDate.now()))));
		}
		if (Boolean.FALSE.equals(params.getAvailable())) {
			Join<Object, Object> reservations = root.join(RESERVATIONS, JoinType.INNER);
			predicates.add(criteriaBuilder.or(criteriaBuilder.isTrue(reservations.get(CHECKOUT_DATE).isNull()),
					criteriaBuilder.greaterThanOrEqualTo(reservations.get(CHECKOUT_DATE), LocalDate.now())));
		}

		return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
	}

}
