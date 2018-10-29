package demo.springdao;


import demo.entity.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BookRepository extends JpaRepository<Book,Long> {
    List<Book> findByBookname(String bookname);

    @Query(value = "select b from Book b where b.bookname like %?1%")
    Page<Book> findByBooknameLike(String bookname , Pageable pageable);

    @Query("select b from Book b where b.id = :id")
    Book findAllById(@Param("id")Long id);

    @Query(value = "select * from book where bookname like %?1% order by ?2 ",nativeQuery = true)
    List<Book> findAllOrderBy(String bookname , String sort);

    @Modifying
    @Query(value = "DELETE FROM book where id = ?1 ",nativeQuery = true)
    void deleteById(Long id);


}
