package br.org.ccb.sgh.http.dto;

import java.io.Serializable;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AddressDto implements Serializable {
	private static final long serialVersionUID = 7294327206539171706L;
	
	@NotNull
	@Size(min = 1, max = 255)
	private String street;
	@NotNull
	@Size(min = 1, max = 255)
	private String district;
	@NotNull
	@Size(min = 1, max = 255)
	private String city;
	@NotNull
	@Size(min = 2, max = 2)
	private String state;
	@NotNull
	@Size(min = 1, max = 10)
	private String number;
	@NotNull
	@Size(min = 8, max = 8)
	private String zipCode;
}
