//package code.controller;
//
//import code.controller.response.BookListResponse;
//import code.model.Book;
//import code.services.BookService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.*;
//
//import java.sql.Connection;
//import java.sql.ResultSet;
//import java.sql.Statement;
//import java.util.List;
//@RestController
//@RequestMapping(value = "/book")
//public class Act_51_controller {
//
//    @Autowired
//    BookService bookService;
//
//    @GetMapping(value = "/{category_id}") //API Find book by category ID
//    public BookListResponse getBookByCategory(@PathVariable(name = "category_id") Integer category_id,
//                                              @RequestParam(value = "orderBy", defaultValue = "title") String orderBy,
//                                              @RequestParam(value = "order", defaultValue = "ASC") String order) {
//        return bookService.getBookByCategory(category_id, orderBy, order);
//    }
//}
