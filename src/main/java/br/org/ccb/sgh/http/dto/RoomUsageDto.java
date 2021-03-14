package br.org.ccb.sgh.http.dto;

import java.io.Serializable;

import lombok.Data;

@Data
public class RoomUsageDto implements Serializable {
	private static final long serialVersionUID = -4208490597654094965L;
	
	private String status;
	private Long quantity;

}
