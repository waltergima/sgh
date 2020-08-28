package br.org.ccb.sgh.http.dto;

import java.io.Serializable;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InnerObjectDto implements Serializable {
	private static final long serialVersionUID = 1044661506391540430L;
	
	@NotNull 
	@Min(1)
	private Long id;
}
