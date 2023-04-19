package com.front.prev.dto.tables;

import com.front.prev.dto.ContratoDTO;
import com.github.pagehelper.Page;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ResultTableContrato {
	private Page<ContratoDTO> data;
	private int draw;
	private int length;
	private long recordsTotal;
	private long recordsFiltered;
}
