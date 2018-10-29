package demo.entity;

import javax.persistence.*;

@Entity
@NamedQuery(name = "selectById",query = "select b from Book b where b.id = :id")
public class Book {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    private String bookname;

    private String price;

    public Book() {
    }

    public Book(String bookname , String price) {
        this.bookname = bookname;
        this.price = price;
    }
    public Book(String bookname) {
        this.bookname = bookname;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBookname() {
        return bookname;
    }

    public void setBookname(String bookname) {
        this.bookname = bookname;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", bookname='" + bookname + '\'' +
                ", price='" + price + '\'' +
                '}';
    }
}
