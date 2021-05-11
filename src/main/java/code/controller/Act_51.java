package code.controller;

import code.model.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;

public class Act_51 {

    @Autowired
    Connection connection;

    @GetMapping(value = "/book/{category_id}") //API
    public List<Book> getCategories(@PathVariable(value = "category_id") Integer category_id,
                                    @RequestParam(value = "book_name") String book_name,
                                    @RequestParam(value = "author") String author,
                                    @RequestParam(value = "price") String price) throws Exception {
//        String query = "Select count(*) as count_cate from spring.books where category ='" + category_id + "'";
//        Statement st = connection.createStatement();
//        ResultSet rs = st.executeQuery(query);
//        while (rs.next()) {
//            Integer count_cate = rs.getInt("count_cate");
//            if (count_cate >= 1) {
//                query = query +" order by "+book_name+
//                        "order asc;";
//            }
//        }
        return null;
    }
}
