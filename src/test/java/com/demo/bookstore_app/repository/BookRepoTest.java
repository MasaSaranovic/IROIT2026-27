package com.demo.bookstore_app.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import com.demo.bookstore_app.models.Book;
import com.demo.bookstore_app.repositories.BookRepository;

@DataJpaTest
@ActiveProfiles("test")
public class BookRepoTest
{


   @Autowired
   private BookRepository repo;


   @Test
   public void testfindByBookName(){
       Book book = new Book();
       String testTitle = "The Little prince";
       book.setBookName(testTitle);
       repo.save(book);
       List<Book> foundBooks = repo.findByBookNameContainingIgnoreCase(testTitle);
       assertFalse(foundBooks.isEmpty(), "The list should not be empty!");
   }

   @Test
   public void testfindByAuthorName(){
       Book book = new Book();
       String testAuthor = "Antoine de Saint-Exupery";
       book.setAuthorName(testAuthor);
       repo.save(book);
       List<Book> foundBooks = repo.findByAuthorNameContainingIgnoreCase(testAuthor);
       assertFalse(foundBooks.isEmpty(), "The list should not be empty!");
   }
   
   @Test
   public void testSave() {
       Book b1 = new Book("Book A", 19.99, "New Author", "Novel", 1970);
       repo.save(b1);
       Long savedBookID = b1.getBookId();
       Book book = repo.findById(savedBookID).orElseThrow();


       assertEquals(savedBookID, book.getBookId());
       assertEquals("Book A", book.getBookName());
       assertEquals(Double.valueOf(19.99), book.getBookPrice());
       assertEquals(1970, book.getPublishingYear());
   }

}
