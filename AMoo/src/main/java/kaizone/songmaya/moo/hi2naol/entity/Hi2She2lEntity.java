package kaizone.songmaya.moo.hi2naol.entity;

import javax.persistence.*;

@Entity
@Table(name = "hi2she2l")
public class Hi2She2lEntity {
    @Id
//    @GeneratedValue
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "fade")
    private String fade;

    @Column(name = "enable")
    private Integer enable;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFade() {
        return fade;
    }

    public void setFade(String fade) {
        this.fade = fade;
    }

    public Integer getEnable() {
        return enable;
    }

    public void setEnable(Integer enable) {
        this.enable = enable;
    }
}
