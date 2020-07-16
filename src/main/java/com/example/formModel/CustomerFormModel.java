package com.example.formModel;

import java.time.LocalDate;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class CustomerFormModel {

	private Long id;
	private String name;
	private String company;
	
}
