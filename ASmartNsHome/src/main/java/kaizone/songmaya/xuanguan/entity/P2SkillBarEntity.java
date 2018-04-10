package kaizone.songmaya.xuanguan.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "p2_cell")
public class P2SkillBarEntity {

    private String name;
    private String value;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
