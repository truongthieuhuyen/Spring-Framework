package code.controller;

import code.controller.request.AddBookRequest;
import code.controller.response.BookListResponse;
import code.controller.response.BookResponse;
import code.entity.BookEntity;
import code.services.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/book")
public class BookController {
    @Autowired
    BookService bookService;

    @GetMapping(value = "/listBook")
    public BookListResponse getListBookByName(@RequestParam String title) {
        return bookService.getListBookByTitle(title);
    }

    @PostMapping(value = "/addListBook")
    public BookEntity addListBook(@RequestBody AddBookRequest requests) {
        return bookService.addNewBook(requests);
    }
}
