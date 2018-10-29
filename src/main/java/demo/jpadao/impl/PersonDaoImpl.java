package demo.jpadao.impl;

import demo.entity.Person;
import demo.jpadao.PersonDao;
import org.springframework.stereotype.Repository;

@Repository
public class PersonDaoImpl extends BaseDaoImpl<Person,Long> implements PersonDao {
}
