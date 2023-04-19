package com.front.prev.dto.tables;

import com.front.prev.dto.UserDTO;
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

public class ResultTableUser {
	
	private Page<UserDTO> data;
	private int draw;
	private int length;
	private long recordsTotal;
	private long recordsFiltered;
}
