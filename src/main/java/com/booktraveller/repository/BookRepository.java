package com.booktraveller.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.booktraveller.entity.Book;
import java.util.List;


@Repository
public interface BookRepository extends JpaRepository<Book, Long>{

	
    @Modifying
    @Transactional
    @Query("UPDATE Book SET countries = LOWER(countries)")
    void lowerCountries();
    
    List<Book> findByCountries(String countries);
    
    
}
