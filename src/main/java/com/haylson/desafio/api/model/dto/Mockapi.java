package com.haylson.desafio.api.model.dto;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode(of={"id"})
public class Mockapi implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private Long id;
    private String first_name;
    private String last_name;
    private String career;
    private String department;
    
}
