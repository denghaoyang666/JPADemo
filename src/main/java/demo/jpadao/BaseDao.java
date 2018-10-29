package demo.jpadao;

import java.io.Serializable;
import java.util.List;

public interface BaseDao<T,ID extends Serializable> {
    /**
     * 简单插入操作
     * @param t
     *        实体对象
     * */
    void insert(T t);

    /**
     * 简单删除操作
     * @param t
     *        实体对象
     * */
    void delete(T t);

    /**
     * 简单修改操作
     * @param t
     *        实体对象
     * */
    void update(T t);

    /**
     * 根据主键查找操作
     * @param id
     *        主键
     * */
    T selectById(ID id);

    /**
     * 根据实体对象属性查询数据
     * @param t
     * @return
     */
    List<T> selectByExample(T t);
}
