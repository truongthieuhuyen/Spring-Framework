package code.services;

import code.controller.response.BookListResponse;
import code.model.Book;
import com.mysql.cj.protocol.Resultset;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

@Service
public class BookService {

    @Autowired
    Connection connection;

    public BookListResponse getBookByCategory(Integer category_id, String orderBy, String order) {
        BookListResponse response = new BookListResponse();
        if (!order.equals("ASC") && !order.equals("DESC")) {
            response.setCode(-1);
            response.setMessage("Order value is not valid");
            return response;
        }
        List<Book> data = null;
        String query = "select * from spring.books where category_id ='" + category_id + "' order by " + orderBy + " " + order + ";";
        try {
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery(query);
            while (rs.next()) {
                if (data == null) {// data =null --> khởi tạo mới
                    data = new ArrayList<>();
                }
                Integer id = rs.getInt("book_id");// check lai column_name trong database
                String title = rs.getString("title");
                String author = rs.getString("author");
                Float price = rs.getFloat("price");
                Book item = new Book(id,title, author, price);
                data.add(item);
            }
            response.setData(data);
            response.setCode(200);
            response.setMessage("Success");

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return response;
    }


}
