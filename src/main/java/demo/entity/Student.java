package demo.entity;

import javax.persistence.*;
import java.util.List;

@Table
public class Student {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long s_id;
    private String s_name;



    public Long getS_id() {
        return s_id;
    }

    public void setS_id(Long s_id) {
        this.s_id = s_id;
    }

    public String getS_name() {
        return s_name;
    }

    public void setS_name(String s_name) {
        this.s_name = s_name;
    }


}
