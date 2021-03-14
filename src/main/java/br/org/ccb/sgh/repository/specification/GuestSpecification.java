package br.org.ccb.sgh.repository.specification;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import br.org.ccb.sgh.entity.Guest;
import br.org.ccb.sgh.http.dto.GuestRequestParamsDto;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class GuestSpecification implements Specification<Guest> {
	private static final long serialVersionUID = 756823738054124967L;

	private GuestRequestParamsDto params;

	@Override
	public Predicate toPredicate(Root<Guest> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
		List<Predicate> predicates = new ArrayList<>();
		if (params.getId() != null) {
			predicates.add(criteriaBuilder.equal(root.get("id"), params.getId()));
		}
		if (params.getName() != null) {
			predicates.add(criteriaBuilder.like(root.get("name"), "%" + params.getName() + "%"));
		}
		if (params.getDateOfBirth() != null) {
			predicates.add(criteriaBuilder.equal(root.get("dateOfBirth"), params.getDateOfBirth()));
		}
		if (params.getDateOfBaptism() != null) {
			predicates.add(criteriaBuilder.equal(root.get("dateOfBaptism"), params.getDateOfBaptism()));
		}
		if (params.getRg() != null) {
			predicates.add(criteriaBuilder.like(root.get("rg"), "%" + params.getRg() + "%"));
		}
		if (params.getCpf() != null) {
			predicates.add(criteriaBuilder.like(root.get("cpf"), "%" + params.getCpf() + "%"));
		}
		if (params.getPhoneNumber() != null) {
			predicates.add(criteriaBuilder.like(root.get("phoneNumber"), "%" + params.getPhoneNumber() + "%"));
		}
		if (params.getMinistery() != null) {
			predicates.add(criteriaBuilder.equal(root.get("ministery"), params.getMinistery()));
		}
		if (params.getPrayingHouse() != null) {
			predicates.add(criteriaBuilder.like(root.get("prayingHouse"), "%" + params.getPrayingHouse() + "%"));
		}
		if (params.getObservation() != null) {
			predicates.add(criteriaBuilder.like(root.get("observation"), "%" + params.getObservation() + "%"));
		}
		if (params.getReservationId() != null) {
			predicates.add(criteriaBuilder.equal(root.join("reservations", JoinType.INNER).get("id"),
					params.getReservationId()));
		}
		
		return query.where(criteriaBuilder.and(predicates.toArray(new Predicate[0]))).distinct(true).getRestriction();
	}

}
