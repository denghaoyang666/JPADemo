import demo.entity.Book;
import demo.entity.Person;
import demo.springdao.BookRepository;
import demo.springdao.PersonDao;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:ApplicationContext.xml"})
public class TestSpring {
    @Autowired
    private BookRepository bookRepository;
    @Test
    public void testSelectBook(){
        Iterable<Book> books = bookRepository.findAll();
        for (Book book : books ){
            System.out.println(book);
        }
    }

    @Test
    public void testPageBook(){
        Sort sort = new Sort(Sort.Direction.DESC,"id");
        Page<Book> page = bookRepository.findAll(PageRequest.of(0,2,sort));
        for (Book book : page ){
            System.out.println(book);
        }
    }

    @Test
    public void testFindByBookname(){
        List<Book> books = bookRepository.findByBookname("java");
        for (Book book : books ){
            System.out.println(book);
        }
    }

    @Test
    public void testQuery(){
        Page<Book> page = bookRepository.findByBooknameLike("a",PageRequest.of(0,2));
        for (Book book : page ){
            System.out.println(book);
        }
    }

    @Test
    public void testQueryParam(){
        Book book = bookRepository.findAllById(1L);
        System.out.println(book);
    }

    @Test
    public void testSqlQuery(){
        List<Book> books = bookRepository.findAllOrderBy("a","id");
        for (Book book : books ){
            System.out.println(book);
        }
    }


    //test Person
    @Autowired
    private PersonDao personDao;

    @Test
    public void testJSEPagePerson(){
        Pageable pageable = PageRequest.of(0,5);
        Page<Person> personList =personDao.findAll((root, query, criteriaBuilder) -> {
            List<Predicate> predicateList = new ArrayList<>();
            predicateList.add(criteriaBuilder.like(root.get("realname"),"%三%"));
            predicateList.add(criteriaBuilder.lessThan(root.get("id"),"8"));

            Predicate[] predicates = new Predicate[predicateList.size()];
            query.where(predicateList.toArray(predicates));
            return query.getRestriction();
        },pageable);
        for (Person person : personList){
            System.out.println(person);
        }
    }

    @Test
    public void testQBEPagePerson(){
        Pageable pageable = PageRequest.of(0,5);

        Person person = new Person();
        person.setRealname("三");

        ExampleMatcher matcher = ExampleMatcher.matching()
                            .withMatcher("realname", ExampleMatcher.GenericPropertyMatchers.contains());

        Example<Person> example = Example.of(person,matcher);
        Page<Person> personList =personDao.findAll(example,pageable);
        for (Person p : personList){
            System.out.println(p);
        }
    }
}
