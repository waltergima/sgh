package br.org.ccb.sgh.http.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@EqualsAndHashCode(callSuper = false)
public class SupportHouseRequestParamsDto extends RequestParamsDto {
	private static final long serialVersionUID = 3935275764779817013L;
	
	private String name;
	private String cnpj;
	private String city;
	private String state;

}
