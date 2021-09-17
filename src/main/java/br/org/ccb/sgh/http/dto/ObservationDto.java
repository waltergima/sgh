package br.org.ccb.sgh.http.dto;

import java.io.Serializable;

import lombok.Data;

@Data
public class ObservationDto implements Serializable {
	private static final long serialVersionUID = -8941740772656222101L;
	
	private String observation;

}
