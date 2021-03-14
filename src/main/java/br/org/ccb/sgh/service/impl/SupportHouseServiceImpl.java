package br.org.ccb.sgh.service.impl;

import javax.transaction.Transactional;

import org.hibernate.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import br.org.ccb.sgh.entity.SupportHouse;
import br.org.ccb.sgh.http.dto.SupportHouseDto;
import br.org.ccb.sgh.http.dto.SupportHouseRequestParamsDto;
import br.org.ccb.sgh.repository.SupportHouseRepository;
import br.org.ccb.sgh.repository.specification.SupportHouseSpecification;
import br.org.ccb.sgh.service.SupportHouseService;

@Service
public class SupportHouseServiceImpl implements SupportHouseService {

	@Autowired
	private SupportHouseRepository supportHouseRepository;

	public Page<SupportHouse> findAll(SupportHouseRequestParamsDto requestParams) {
		Page<SupportHouse> supportHouses = this.supportHouseRepository.findAll(new SupportHouseSpecification(requestParams),
				requestParams.getPageRequest());
		
		if(supportHouses.isEmpty()) {
			throw new EmptyResultDataAccessException(requestParams.getLimit());
		}
		
		return supportHouses;
	}

	@Override
	public SupportHouse byId(Long id) {
		return supportHouseRepository.findById(id)
				.orElseThrow(() -> new ObjectNotFoundException(id, SupportHouse.class.getName()));
	}

	@Override
	public SupportHouse save(SupportHouseDto supportHouseDto) {
		SupportHouse supportHouse = SupportHouse.fromDto(null, null, supportHouseDto);
		return this.supportHouseRepository.save(supportHouse);
	}

	@Override
	@Transactional
	public SupportHouse update(Long id, SupportHouseDto supportHouseDto) {
		SupportHouse supportHouse = this.byId(id);
		supportHouse = SupportHouse.fromDto(supportHouse.getId(), supportHouse.getAddress().getId(), supportHouseDto);

		return this.supportHouseRepository.save(supportHouse);
	}

	@Override
	public void remove(Long id) {
		this.supportHouseRepository.deleteById(id);
	}

}
