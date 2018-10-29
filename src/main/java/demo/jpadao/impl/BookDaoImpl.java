package demo.jpadao.impl;

import demo.entity.Book;
import demo.jpadao.BookDao;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

@Repository
@Transactional
public class BookDaoImpl implements BookDao {

    @PersistenceContext
    protected EntityManager entityManager;

    @Override
    public Book selectById(Long id) {
        return entityManager.find(Book.class,id);
    }

    @Override
    public void insert(Book book) {
        entityManager.persist(book);
    }

    @Override
    public void deleteById(Book book) {
        entityManager.getTransaction().begin();
        entityManager.remove(book);
        entityManager.getTransaction().commit();
    }

    @Override
    public void updataById(Book book) {
        entityManager.merge(book);
    }

    @Override
    public List selectByExample(Book book) {
        CriteriaBuilder criteriaBuilder=entityManager.getCriteriaBuilder();
        CriteriaQuery<Book> criteriaQuery = criteriaBuilder.createQuery(Book.class);
        Root<Book> root = criteriaQuery.from(Book.class);
        //CriteriaQuery<Book> select = criteriaQuery.multiselect(root.get("price"),root.get("bookname"));
        //CriteriaQuery<Book> select = criteriaQuery.select( root.get("bookname"));
        CriteriaQuery<Book> select = criteriaQuery.select(root);
        criteriaQuery.where(criteriaBuilder.like(root.get("bookname"),"%"+book.getBookname()+"%"),
                            criteriaBuilder.greaterThan(root.get("price"),"70"));
        //criteriaBuilder.between(root.get("price"),"50","60");
        Query query = entityManager.createQuery(select);

        return query.getResultList();
    }

    @Override
    public Book selectWithJPQL(Long id) {
        String hql="select b from Book b where b.id = ?1";
        Query query = entityManager.createQuery(hql);
        query.setParameter(1,id);
        return (Book) query.getResultList().get(0);
    }

    @Override
    public Book selectWithJPQLAnno(Long id) {
        Query query = entityManager.createNamedQuery("selectById");
        query.setParameter("id",id);
        return (Book) query.getResultList().get(0);
    }
}
