package br.org.ccb.sgh.http.dto;

import java.io.Serializable;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;

import lombok.Data;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
public class RequestParamsDto implements Serializable {
	private static final long serialVersionUID = -5933624758229524875L;
	
	private Long id;
	private Integer offset;
	private Integer limit;
	private String orderBy;
	private String direction;
	
	public PageRequest getPageRequest() {
		return PageRequest.of(offset, limit, Direction.fromString(direction), orderBy);
	}
}
