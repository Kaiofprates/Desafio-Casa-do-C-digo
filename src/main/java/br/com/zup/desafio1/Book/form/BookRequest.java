package br.com.zup.desafio1.Book.form;

import br.com.zup.desafio1.Author.Author;
import br.com.zup.desafio1.Book.Book;
import br.com.zup.desafio1.Category.Category;
import br.com.zup.desafio1.validate.IdExists.ExistId;
import br.com.zup.desafio1.validate.UniqueValues.UniqueValue;
import org.springframework.util.Assert;

import javax.persistence.EntityManager;
import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDate;

public class BookRequest {

    @NotBlank(message = "this field cannot be empty")
    @UniqueValue(domainClass = Book.class, fieldName = "title")
    private String title;
    @NotBlank(message = "this field cannot be empty")
    @Size(max = 500)
    private String resume;
    private String sumary;
    @NotNull(message = "this field cannot be empty")
    @Min(20)
    private BigDecimal price;
    @NotNull(message = "this field cannot be empty")
    @Min(100)
    private Long pages;
    @NotEmpty(message = "this field cannot be empty")
    @UniqueValue(domainClass = Book.class, fieldName = "isbn")
    private String isbn;
    @Future
    private LocalDate publication;
    @NotNull(message = "this field cannot be empty")
    @ExistId(domainClass = Category.class, fieldName = "id")
    private Long categoryId;
    @NotNull(message = "this field cannot be empty")
    @ExistId(domainClass = Author.class, fieldName = "id")
    private Long authorId;

    public BookRequest(String title,
                       String resume,
                       String sumary,
                       BigDecimal price,
                       Long pages,
                       String isbn,
                       LocalDate publication,
                       Long categoryId,
                       Long authorId) {
        this.title = title;
        this.resume = resume;
        this.sumary = sumary;
        this.price = price;
        this.pages = pages;
        this.isbn = isbn;
        this.publication = publication;
        this.categoryId = categoryId;
        this.authorId = authorId;
    }

    public Book toModel(EntityManager manager) {

        Author author = manager.find(Author.class, this.authorId);
        Category category = manager.find(Category.class, this.categoryId);

        Assert.state(author != null, "Author not found");
        Assert.state(category != null, "Category not found");

        Book book = new Book(this.title, this.sumary, this.price, this.pages, this.isbn, this.publication, category, author);
        return book;
    }


}
