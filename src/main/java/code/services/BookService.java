package code.services;

import code.controller.request.AddBookRequest;
import code.controller.response.BookListResponse;
import code.entity.BookEntity;
import code.entity.PublishedBookEntity;
import code.repository.BookRepository;
import code.repository.PublishedBookRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Service
public class BookService {
    private static final Logger logger = LoggerFactory.getLogger(BookService.class);

    @Autowired
    BookRepository bookRepository;
    @Autowired
    PublishedBookRepository publishedBookRepository;

    /*
     * find book by name
     * */
    public BookListResponse getBookByTitle(String title, String orderBy, String order, Integer pageNum) {
        logger.info("========== Start received request getBookByTittle : title {}, orderBy {}, order {}, page {}", title, orderBy, order, pageNum
        );

        /*Sort sort = Sort.by(Sort.Direction.ASC, orderBy);
        if (order.equals("DESC")) {
            sort = Sort.by(Sort.Direction.DESC, orderBy);
        }
        PageRequest pageRequest = PageRequest.of(pageNum, 2, sort);*/
        BookListResponse response = new BookListResponse();
        List<BookEntity> search = bookRepository.findNativeAllByTitle(title, orderBy, order, 2 * pageNum);

        if (!search.isEmpty()) {

            response.setCode(200);
            response.setMessage("OK");
            response.setData(search);
            return response;
        }
        logger.error("No results found with input title {}",title);
        response.setCode(-1);
        response.setMessage("No result");
        return response;
    }

    /*
    add new book
    * */
    public String addNewBookService(AddBookRequest requests, Integer userId) {
        List<BookEntity> search = bookRepository.findAllBookByTitleAndAuthor(requests.getTitle(), requests.getAuthor());
        if (!search.isEmpty()) {
            return "Book already existed";
        }
        //save in book table
        BookEntity item = new BookEntity();
        item.setTitle(requests.getTitle());
        item.setAuthor(requests.getAuthor());
        item.setDescription(requests.getDescription());
        item.setPicture(requests.getPicture());
        item.setPrice(requests.getPrice());
        item.setYear(requests.getYear());
        item.setCategoryId(requests.getCategoryId());
        item = bookRepository.save(item);

        //save in publish_book table
        PublishedBookEntity publishedItem = new PublishedBookEntity();
        publishedItem.setBookId(item.getBookId());
        publishedItem.setUserId(userId);
        publishedItem.setPostedTime(new Timestamp(System.currentTimeMillis()));
        publishedItem.setTitle(item.getTitle());
        publishedItem = publishedBookRepository.save(publishedItem);
        return "Added books to the library :" + item.getTitle();
    }

    /*
     * Update book
     * */
    @Transactional
    public String updateBookService(String description, String picture, Double price, Integer categoryId, Integer bookId, Integer userId) {
        //check in DB
        List<PublishedBookEntity> ub = publishedBookRepository.findAllByBookIdAndUserId(bookId, userId);
        if (ub == null) {
            return "You don't have access to do this";
        }
        //dung thu ham save
        publishedBookRepository.updateBookUsingNativeModify(description, price, picture, categoryId, bookId);
        return "Book has been updated";
    }

    /* Delete book
     * 1. delete in published_book table first
     * 2. delete in book table */
    @Transactional
    public String deleteBookService(Integer publishedId, Integer bookId) {
        //check in DB
        List<PublishedBookEntity> ub = publishedBookRepository.findByPublishedId(publishedId);
        if (ub == null) {
            return "You don't have access to do this";
        }
        publishedBookRepository.delete(publishedId);
        bookRepository.delete(bookId);
        return "Book has been deleted";
    }


}
