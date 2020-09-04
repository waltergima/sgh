package br.org.ccb.sgh.http.dto;

import java.io.Serializable;

import javax.validation.Valid;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ContactDto implements Serializable {
	private static final long serialVersionUID = 4414487908235938969L;
	
	@Size(min = 1, max = 255)
	private String name;
	@Size(min = 1, max = 11)
	private String phoneNumber;
	@Size(min = 1, max = 11)
	private String celNumber;
	@Size(min = 1, max = 255)
	private String ministery;
	@Size(min = 1, max = 255)
	private String relationship;
	@Valid
	private AddressDto address;
	@Size(min = 1)
	private String observation;
}
