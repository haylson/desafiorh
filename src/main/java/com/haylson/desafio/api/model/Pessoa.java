package com.haylson.desafio.api.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode(of={"id"})
@Entity
@Table(name = "PESSOA")
public class Pessoa implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	private Long id;
	
	@NotNull
	@Size(min=3, max=50)
	@Column(name = "first_name")
	private String firstName;
	
	@NotNull
	@Size(min=3, max=50)
	@Column(name = "last_name")
	private String lastName;
	
	@ManyToOne(optional = false)
	@JoinColumn(name="setor_id", nullable = false, foreignKey = @ForeignKey(name = "fk_pessoa_setor"))
	private Setor setor;

}
