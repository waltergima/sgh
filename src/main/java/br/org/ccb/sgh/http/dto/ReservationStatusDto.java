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
public class ReservationStatusDto implements Serializable {
	private static final long serialVersionUID = 5058451108739101477L;
	
	@NotNull
	@Size(min = 1)
	private String status;
}
