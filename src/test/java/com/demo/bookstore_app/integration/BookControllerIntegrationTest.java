package com.demo.bookstore_app.integration;

import com.demo.bookstore_app.models.Book;
import com.demo.bookstore_app.repositories.BookRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class BookControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private BookRepository bookRepository;

    @BeforeEach
    void cleanDatabase() {
        bookRepository.deleteAll();
    }

    @Test
    void testUpdateBook() throws Exception {
        Book existingBook = bookRepository.save(
                new Book("Existing Book", 200.00, "Existing Author", "Novel", 2003));
        Book updatedBook = new Book(
                "Updated Book", 130.00, "Updated Author", "Novel", 2003);

        MvcResult result = mockMvc.perform(put("/book/{bookId}", existingBook.getBookId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedBook)))
                .andExpect(status().isOk())
                .andReturn();

        Book modifiedBook = objectMapper.readValue(
                result.getResponse().getContentAsString(), Book.class);

        assertEquals(existingBook.getBookId(), modifiedBook.getBookId());
        assertEquals(updatedBook.getBookName(), modifiedBook.getBookName());
        assertEquals(updatedBook.getAuthorName(), modifiedBook.getAuthorName());
    }

    @Test
    void testDeleteBook() throws Exception {
        Book existingBook = bookRepository.save(
                new Book("Existing Book", 230.00, "Existing Author", "Classic", 2023));

        mockMvc.perform(delete("/book/{bookId}", existingBook.getBookId()))
                .andExpect(status().isOk());

        assertFalse(bookRepository.existsById(existingBook.getBookId()));
    }
}
