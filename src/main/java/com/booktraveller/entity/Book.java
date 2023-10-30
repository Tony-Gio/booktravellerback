package com.booktraveller.entity;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class Book {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;
	
	@Column(name="title")
    String title;
	
	@Column(name="author")	
    String author ;
	
	@Column(name="country")
    String countries;
	
	@Column(name="date")
    int date;
	
	@Column(name="imgurl")
    String imgurl;
	
	@Column(name="description")
    String description ;
}
