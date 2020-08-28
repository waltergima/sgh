package br.org.ccb.sgh.http.dto;

import java.io.Serializable;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SupportHouseDto implements Serializable {

	private static final long serialVersionUID = 9131219430758793876L;
	
	@NotBlank
	@Size(min = 1, max = 255)
	private String name;
	@Size(min = 14, max = 14)
	private String cnpj;
	@Valid
	private AddressDto address;
}
