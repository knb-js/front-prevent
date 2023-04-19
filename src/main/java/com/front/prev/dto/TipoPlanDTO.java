package com.front.prev.dto;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(Include.NON_NULL)
public class TipoPlanDTO {

	private Integer idTipoPlan;
	private String nombrePlan;
	private String active;
	private LocalDateTime created;
	private LocalDateTime update;
	private String descripcion;
	
}
