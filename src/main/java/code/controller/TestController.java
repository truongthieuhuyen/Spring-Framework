package code.controller;

import code.controller.response.BookForm;
import code.model.Book;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Controller
public class TestController {
    private static List<Book> books = new ArrayList<>();

    static {
        books.add(new Book(1, "MHA", "un_know", null));
        books.add(new Book(2, "1 piece", "Oda", 1.3f));
    }

    int currentId = 2;

    @GetMapping(value = {"/", "/index2"})
    public String indexTwo(Model model) {
        model.addAttribute("message", "Connected to file .css");
        return "index2";
    }

    @GetMapping(value = {"/bookList"})
    public String bookPage(Model model) {
        model.addAttribute("books", books);
        return "bookList";
    }

    @GetMapping(value = {"/addBook"})
    public String addBookPage(Model model) {
        BookForm bookForm = new BookForm();
        model.addAttribute("bookForm", bookForm);
        return "addBook";
    }

    @PostMapping(value = {"/addBook"})
    public String addBookForm(Model model, @ModelAttribute("bookForm") BookForm form) {
        Integer id = form.getId();
        String title = form.getTitle();
        String author = form.getAuthor();
        Float price = form.getPrice();
        currentId++;
        books.add(new Book(currentId, title, author, price));
        return "redirect:/bookList";
    }

//    @GetMapping(value = {"/bookDetail"})
//    public String getBookDetail( @RequestParam("id") Integer id) {
//        Book book = books.get(id);
//        return "bookDetail";
//    }

}
