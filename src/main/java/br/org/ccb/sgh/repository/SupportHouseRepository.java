package br.org.ccb.sgh.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import br.org.ccb.sgh.entity.SupportHouse;

@Repository
public interface SupportHouseRepository
		extends JpaRepository<SupportHouse, Long>, JpaSpecificationExecutor<SupportHouse> {

}
