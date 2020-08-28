package br.org.ccb.sgh.service;

import org.springframework.data.domain.Page;

import br.org.ccb.sgh.entity.SupportHouse;
import br.org.ccb.sgh.http.dto.SupportHouseDto;
import br.org.ccb.sgh.http.dto.SupportHouseRequestParamsDto;

public interface SupportHouseService {
	
	public Page<SupportHouse> findAll(SupportHouseRequestParamsDto requestParams);

	public SupportHouse byId(Long id);

	public SupportHouse save(SupportHouseDto supportHouseDto);

	public SupportHouse update(Long id, SupportHouseDto supportHouseDto);

	public void remove(Long id);


}
