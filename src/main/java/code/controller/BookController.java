package code.controller;

import code.controller.request.AddBookRequest;
import code.controller.response.BookListResponse;
import code.services.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/book")
public class BookController {
    @Autowired
    BookService bookService;

    @GetMapping(value = "/showBook/")
    public BookListResponse getListBookByName(@RequestParam String title,
                                              @RequestParam(name = "order", defaultValue = "ASC") String order,
                                              @RequestParam(name = "orderBy", defaultValue = "bookId") String orderBy,
                                              @RequestParam(name = "pageNum", defaultValue = "0") Integer pageNum) {
        return bookService.getBookByTitle(title, orderBy, order, pageNum);
    }

    @PostMapping(value = "/addBook")
    public String addNewBook(@RequestBody AddBookRequest requests, Integer userId) {
        return bookService.addNewBookService(requests,userId);
    }

    @PutMapping(value = "/updateBook")
    public String updateBook( String description, String picture, Double price,@RequestBody Integer categoryId, Integer bookId, Integer userId) {
        return bookService.updateBookService(description,picture,price,categoryId,bookId,userId);
    }
//    @PostMapping(value = "/testAddBook")
//    public String testNewBook(String title, String author) {
//        return bookService.addBook(title,author);
//    }

    @DeleteMapping(value = "deleteBook")
    public String deleteBook(Integer publishedId, Integer bookId){
        return bookService.deleteBookService(publishedId,bookId);
    }
}
