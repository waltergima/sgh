package br.org.ccb.sgh.repository;

import java.time.LocalDate;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.org.ccb.sgh.entity.Reservation;

@Repository
public interface ReservationRepository
		extends JpaRepository<Reservation, Long>, JpaSpecificationExecutor<Reservation> {
	
	@Transactional
	@Modifying
	@Query("update Reservation r set r.checkinDate = :checkinDate, status = 'CONFIRMED' where id = :id")
	public void checkin(@Param("id") Long id, @Param("checkinDate") LocalDate checkinDate);
	
	@Transactional
	@Modifying
	@Query("update Reservation r set r.checkoutDate = :checkoutDate, status = 'FINISHED' where id = :id")
	public void checkout(@Param("id") Long id, @Param("checkoutDate") LocalDate checkoutDate);

}
