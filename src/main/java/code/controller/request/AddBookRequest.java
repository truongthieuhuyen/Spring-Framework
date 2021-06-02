package code.controller.request;

import lombok.Getter;
import lombok.Setter;

@Getter@Setter
public class AddBookRequest {
    private String title;
    private String picture;
    private String year;
    private String author;
    private String description;
    private Double price;
    private Integer categoryId;
}
