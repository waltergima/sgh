package br.org.ccb.sgh.http.dto;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReservationDto implements Serializable {
	private static final long serialVersionUID = 6162176147663268476L;
	
	@NotNull
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
	@JsonDeserialize(using = LocalDateDeserializer.class)
	@JsonSerialize(using = LocalDateSerializer.class)
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	private LocalDate initialDate;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
	@JsonDeserialize(using = LocalDateDeserializer.class)
	@JsonSerialize(using = LocalDateSerializer.class)
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	private LocalDate finalDate;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
	@JsonDeserialize(using = LocalDateDeserializer.class)
	@JsonSerialize(using = LocalDateSerializer.class)
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	private LocalDate checkinDate;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
	@JsonDeserialize(using = LocalDateDeserializer.class)
	@JsonSerialize(using = LocalDateSerializer.class)
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	private LocalDate checkoutDate;
	@NotNull
	@Valid
	private InnerObjectDto room;
	@NotNull
	@Valid
	private List<InnerObjectDto> guests;
	@Valid
	private ContactDto contact;
	@Size(min = 1)
	private String observation;
	@NotNull
	@Size(min = 1)
	private String status;
}
