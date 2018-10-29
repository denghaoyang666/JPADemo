import demo.entity.Book;
import demo.entity.Person;
import demo.jpadao.BookDao;
import demo.jpadao.PersonDao;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:ApplicationContext.xml"})
public class TestJPA {

    @Test
    public void generate(){
        //...
    }

    @Autowired
    BookDao bookDao;

    @Test
    public void testInsertBooks(){
        Book book1 = new Book();
        book1.setBookname("Java");
        book1.setPrice("88.00");

        Book book2 = new Book();
        book2.setBookname("Spring");
        book2.setPrice("77.00");

        Book book3 = new Book();
        book3.setBookname("Hibernate");
        book3.setPrice("66.00");

        bookDao.insert(book1);
        bookDao.insert(book2);
        bookDao.insert(book3);
    }

    @Test
    public void testSelectBook(){
        Book book = bookDao.selectById(1L);
        if(book==null){
            System.out.println("null");
        }else {
            System.out.println(book.toString());
        }
    }

    @Test
    public void testSelectByExample(){
        Book book1 = new Book();
        book1.setBookname("a");
        book1.setPrice("88.00");

        List<Book> books = bookDao.selectByExample(book1);
        for (Book book : books){
            System.out.println(book.toString());
        }
    }

    @Test
    public void testJPQL(){
        Book book = bookDao.selectWithJPQL(1l);
        System.out.println(book.toString());
    }

    @Test
    public void testJPQLAnno(){
        Book book = bookDao.selectWithJPQL(1l);
        System.out.println(book.toString());
    }


    /**
     * test Person
     */
    @Autowired
    PersonDao personDao;

    @Test
    public void testInsertPerson(){
        Person p1=new Person("111","张三","第一个张三");
        Person p2=new Person("222","张三","第二个张三");
        Person p3=new Person("333","李四","一个李四");
        personDao.insert(p1);
        personDao.insert(p2);
        personDao.insert(p3);
    }

    @Test
    public void testSelectByPerson(){
        Person person = new Person();
        person.setRealname("张三");
        person.setUsername("222");
        List<Person> persons = personDao.selectByExample(person);
        for(Person p : persons){
            System.out.println(p.toString());
        }
    }

}
