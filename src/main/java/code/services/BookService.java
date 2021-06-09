package code.services;

import code.controller.request.AddBookRequest;
import code.controller.response.BookListResponse;
import code.entity.BookEntity;
import code.entity.PublishedBookEntity;
import code.repository.BookRepository;
import code.repository.PublishedBookRepository;
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

    @Autowired
    BookRepository bookRepository;
    @Autowired
    PublishedBookRepository publishedBookRepository;

    public BookListResponse getBookByTitle(String title, String orderBy, String order, Integer pageNum) {
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
        response.setCode(-1);
        response.setMessage("No result");
        return response;
    }

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

    @Transactional
    public String updateBookService(BookEntity bookEntity, Integer userId) {
        //check in DB
        List<PublishedBookEntity> ub = publishedBookRepository.findAllByTitleAndUserId(bookEntity.getTitle(), userId);

        if (ub == null) {
            return "You don't have access to do this";
        }
        publishedBookRepository.updateBookUsingNativeModify(bookEntity.getTitle(), bookEntity.getDescription(), bookEntity.getAuthor(), bookEntity.getPrice(), bookEntity.getYear(), bookEntity.getPicture(), bookEntity.getCategoryId(), bookEntity.getBookId());

        return "Book has been updated";
    }

    /*
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
