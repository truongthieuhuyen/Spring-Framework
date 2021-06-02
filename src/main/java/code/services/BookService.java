package code.services;

import code.controller.request.AddBookRequest;
import code.controller.response.BookListResponse;
import code.controller.response.BookResponse;
import code.entity.BookEntity;
import code.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BookService {

    @Autowired
    BookRepository bookRepository;

    public BookListResponse getListBookByTitle(String title) {
//        if (!order.equals("ASC") && !order.equals("DESC")) {
//            response.setCode(-1);
//            response.setMessage("Order value is not valid");
//            return response;
//        }
        BookListResponse response = new BookListResponse();
        List<BookEntity> search = bookRepository.findAllByTitle(title);
//        String query = "select * from spring.books where category_id ='" + category_id + "' order by " + orderBy + " " + order + ";";
//        try {
//            Statement st = connection.createStatement();
//            ResultSet rs = st.executeQuery(query);
//            while (rs.next()) {
//                if (data == null) {// data =null --> khởi tạo mới
//                    data = new ArrayList<>();
//                }
//                Integer id = rs.getInt("book_id");// check lai column_name trong database
//                String title = rs.getString("title");
//                String author = rs.getString("author");
//                Float price = rs.getFloat("price");
//                Book item = new Book(id,title, author, price);
//                data.add(item);
//            }
//            response.setData(data);
//            response.setCode(200);
//            response.setMessage("Success");
//
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        if (search != null) {
            response.setCode(200);
            response.setMessage("OK");
            response.setData(search);
            return response;
//        }
//        response.setCode(-1);
//        response.setMessage("No result");
//        return response;
    }

    public BookEntity addNewBook(AddBookRequest requests) {
        // native query thì sẽ luôn if = true > không tạo mới
        // query bt thì sẽ luôn false > luôn tạo mới
        BookEntity search = bookRepository.findAllByAuthorAndTitle(requests.getTitle(), requests.getAuthor());
        if (search != null) {
            System.out.println("Book already existed" + requests.getTitle());
            return null;
        }
        BookEntity item = new BookEntity();

        item.setTitle(requests.getTitle());
        item.setAuthor(requests.getAuthor());
        item.setDescription(requests.getDescription());
        item.setPicture(requests.getPicture());
        item.setPrice(requests.getPrice());
        item.setYear(requests.getYear());
        item.setCategoryId(requests.getCategoryId());

        item = bookRepository.save(item);
        return item;
}

}
