package com.front.prev.dto;

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
public class ContratoDTO {
	
	private Integer idContrato;
	private String comentario;
	private Integer valor;
	private Integer valorExtra;
	private String fechaContratacion;
	private String fechaTermino;
	private String renovacion;
	private String capacitacion;
	private RepresentanteDTO representante;
	private EmpresaDTO empresa;
	private TipoPlanDTO tipoPlan;
	private EstadoContratoDTO estadoContrato;
}
