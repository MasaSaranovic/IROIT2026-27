package com.demo.bookstore_app.service;

import com.demo.bookstore_app.models.Book;
import com.demo.bookstore_app.repositories.BookRepository;
import com.demo.bookstore_app.services.BookService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;


import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


import java.util.Arrays;
import java.util.List;


import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;
import static org.junit.jupiter.api.Assertions.*;


@ExtendWith(MockitoExtension.class)
public class BookServiceTest {


   @Mock
   private BookRepository bookRepository;


   @InjectMocks
   private BookService bookService;


   @Test
   public void testGetAllBooks() throws Exception {
       // Mock data
       List<Book> books = Arrays.asList(
               new Book("Book 1", 350.00, "Author 1", "Novel", 1998),
               new Book("Book 2", 250.00, "Author 2", "Horor", 2007)
       );

       when(bookRepository.findAll()).thenReturn(books);

       List<Book> result = bookService.getAllBooks();

       assertEquals(2, result.size());
   }




   @Test
   public void testSaveBook() throws Exception {
       
       Book bookToSave = new Book("New Book", 350.00, "New Author", "Novel", 1956);
       Book savedBook = new Book("New Book", 350.00, "New Author", "Novel", 1956);
       savedBook.setBookId(1L);

       when(bookRepository.save(bookToSave)).thenReturn(savedBook);

       Book result = bookService.saveBook(bookToSave);

       assertNotNull(result);
       assertEquals(savedBook.getBookId(), result.getBookId());
       assertEquals(bookToSave.getBookName(), result.getBookName());
       verify(bookRepository, times(1)).save(bookToSave);
   }


   @Test
   public void testDeleteBook() throws Exception {
       
       Long id = 10L;

       bookService.deleteBook(id);

       verify(bookRepository, times(1)).deleteById(id);
   }
}
