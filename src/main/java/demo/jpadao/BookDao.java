package demo.jpadao;

import demo.entity.Book;

import java.util.List;

public interface BookDao {
    Book selectById(Long id);
    void insert(Book book);
    void deleteById(Book book);
    void updataById(Book book);

    List selectByExample(Book book);

    Book selectWithJPQL(Long id);

    Book selectWithJPQLAnno(Long id);
}
