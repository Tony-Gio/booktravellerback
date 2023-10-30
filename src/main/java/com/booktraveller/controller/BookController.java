package com.booktraveller.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.booktraveller.entity.Book;
import com.booktraveller.service.BookService;


@RestController
@RequestMapping("/books")
public class BookController {
	
@Autowired
BookService bookService;

@GetMapping
public List<Book> getAllBooks(){
	return this.bookService.getAllBooks();
}

@GetMapping("{id}")
public Book getBookById(@PathVariable long id) {
	return this.bookService.getBookById(id);
}

@GetMapping("/country/{country}")
public List<Book> getBookByCountry(@PathVariable String country){
	return bookService.findByCountry(country);
}

@PostMapping
public void addNewBook(@RequestBody Book book) {
	this.bookService.addNewBook(book);
}

@PutMapping("/{id}")
public ResponseEntity<Book> updateVotreEntite(@PathVariable Long id, @RequestBody Book UpdatedBook) {
    Book updatedBook = this.bookService.updateBook(id, UpdatedBook);
    return new ResponseEntity<>(updatedBook, HttpStatus.OK);
}

/**
 * Function to transform the name of countries extracted from Capitalized to lowercase
 */
@PostMapping("/lower")
public void updateColumnToLowerCase() {
    this.bookService.lowerCountries();
}

}