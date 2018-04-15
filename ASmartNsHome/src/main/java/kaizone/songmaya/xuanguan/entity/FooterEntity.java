package kaizone.songmaya.xuanguan.entity;

import org.springframework.scheduling.support.SimpleTriggerContext;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "p1_footer")
public class FooterEntity {

    @Id
//    @GeneratedValue
    @Column(name = "id")
    private Long id;
    private String text0;
    private String href0;
    private String target0;
    private String title0;
    private String text1;
    private String href1;
    private String target1;
    private String title1;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getText0() {
        return text0;
    }

    public void setText0(String text0) {
        this.text0 = text0;
    }

    public String getHref0() {
        return href0;
    }

    public void setHref0(String href0) {
        this.href0 = href0;
    }

    public String getTarget0() {
        return target0;
    }

    public void setTarget0(String target0) {
        this.target0 = target0;
    }

    public String getTitle0() {
        return title0;
    }

    public void setTitle0(String title0) {
        this.title0 = title0;
    }

    public String getText1() {
        return text1;
    }

    public void setText1(String text1) {
        this.text1 = text1;
    }

    public String getHref1() {
        return href1;
    }

    public void setHref1(String href1) {
        this.href1 = href1;
    }

    public String getTarget1() {
        return target1;
    }

    public void setTarget1(String target1) {
        this.target1 = target1;
    }

    public String getTitle1() {
        return title1;
    }

    public void setTitle1(String title1) {
        this.title1 = title1;
    }
}
