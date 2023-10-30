package com.booktraveller.service;

import java.util.List;
import java.util.Optional;

import org.hibernate.annotations.NotFound;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.booktraveller.entity.Book;
import com.booktraveller.repository.BookRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class BookService {

	@Autowired
	BookRepository bookRepository;
	
	public List<Book> getAllBooks(){
		return this.bookRepository.findAll();
		
	}
	
	public Book getBookById(long id) {
		Optional<Book> opt = this.bookRepository.findById(id);
		
		if (opt.isEmpty()) {
			Book emptyBook = new Book();
			return emptyBook;
			
		}else {
			Book bookFound = opt.get();
			return bookFound;
		}
		
		
	}

	public void addNewBook(Book book) {
			this.bookRepository.save(book);		
	}
	
	public Book updateBook (long id, Book updatedBook){
			Book existingBook = this.bookRepository.findById(id)
					.orElseThrow(()-> new EntityNotFoundException("VotreEntite non trouvée avec l'ID : " + id));

				if (updatedBook.getAuthor() != null) {
					existingBook.setAuthor(updatedBook.getAuthor());
				}
				if (updatedBook.getCountries() != null) {
					existingBook.setCountries(updatedBook.getCountries());
				}

				if (updatedBook.getDescription() != null) {
					existingBook.setDescription(updatedBook.getDescription());
				}
				if (updatedBook.getImgurl() != null) {
					existingBook.setImgurl(updatedBook.getImgurl());
				}
				if (updatedBook.getTitle() != null) {
					existingBook.setTitle(updatedBook.getTitle());
				}
				if (updatedBook.getAuthor() != null) {
					existingBook.setAuthor(updatedBook.getAuthor());
				}
		
		        return this.bookRepository.save(existingBook);
	}
	
	
	public List<Book> findByCountry(String country){
		return this.bookRepository.findByCountries(country);
	}
	
	/**
	 * Function to put all extracted data in lowercase
	 */
    public void lowerCountries() {
        this.bookRepository.lowerCountries();
    }
}
