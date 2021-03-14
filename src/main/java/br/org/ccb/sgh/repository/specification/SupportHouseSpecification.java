package br.org.ccb.sgh.repository.specification;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import br.org.ccb.sgh.entity.SupportHouse;
import br.org.ccb.sgh.http.dto.SupportHouseRequestParamsDto;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class SupportHouseSpecification implements Specification<SupportHouse> {
	private static final long serialVersionUID = -1153018398623719356L;

	private SupportHouseRequestParamsDto params;

	@Override
	public Predicate toPredicate(Root<SupportHouse> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
		List<Predicate> predicates = new ArrayList<>();

		if (params.getId() != null) {
			predicates.add(criteriaBuilder.equal(root.get("id"), params.getId()));
		}
		if (params.getName() != null) {
			predicates.add(criteriaBuilder.like(root.get("name"), "%" + params.getName() + "%"));
		}
		if (params.getCnpj() != null) {
			predicates.add(criteriaBuilder.like(root.get("cnpj"), "%" + params.getCnpj() + "%"));
		}
		if (params.getCity() != null) {
			predicates.add(criteriaBuilder.like(root.join("address", JoinType.INNER).get("city"),
					"%" + params.getCity() + "%"));
		}
		if (params.getState() != null) {
			predicates.add(criteriaBuilder.like(root.join("address", JoinType.INNER).get("state"),
					"%" + params.getState() + "%"));
		}

		return query.where(criteriaBuilder.and(predicates.toArray(new Predicate[0]))).distinct(true).getRestriction();
	}

}
