package demo.jpadao.impl;

import com.sun.tools.javac.util.ArrayUtils;
import demo.jpadao.BaseDao;
import org.springframework.core.ResolvableType;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

@Transactional
@Repository
public class BaseDaoImpl<T,ID extends Serializable>  implements BaseDao<T,ID> {

    private Class<T> entityClass;

    @PersistenceContext
    protected EntityManager entityManager;

    public BaseDaoImpl(){
        ResolvableType resolvableType = ResolvableType.forClass(getClass());
        entityClass = (Class<T>) resolvableType.as(BaseDaoImpl.class).getGeneric().resolve();
    }

    @Override
    public void insert(T t) {
        entityManager.persist(t);
    }

    @Override
    public void delete(T t) {
        entityManager.remove(t);
    }

    @Override
    public void update(T t) {
        entityManager.merge(t);
    }

    @Override
    public T selectById(ID id) {
        return entityManager.find(entityClass,id);
    }

    @Override
    public List<T> selectByExample(T t) {
        //条件构造器
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        //create条件查询
        CriteriaQuery criteriaQuery = builder.createQuery(entityClass);
        //查询的根，即select语句中from部分
        Root<T> root = criteriaQuery.from(entityClass);
        //查询字段，即select语句中select部分，参数root意为查询所有字段对应*，若要查询部分字段，参数中可填root.get("attrName")
        CriteriaQuery<T> select = criteriaQuery.select(root);
        //获取属性值不为空的，字段和值，拼装equeal语句
        List<Predicate> predicates = new ArrayList<>();
        try {
            Field[] fields = entityClass.getDeclaredFields();
            for (Field field : fields){
                field.setAccessible(true);
                Object value = field.get(t);
                if(value!=null && !value.equals("")){
                    Predicate p = builder.equal(root.get(field.getName()),value);
                    predicates.add(p);
                }
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        //将生成的equeal语句拼接到Where子句中
        int size = predicates.size();
        if(size>0){
            Predicate [] p = new Predicate[size];
            for(int i=0 ; i<predicates.size() ; i++){
                p[i] = predicates.get(i);
            }
            criteriaQuery.where(p);
        }
        //返回List集合
        return entityManager.createQuery(select).getResultList();
    }

}
