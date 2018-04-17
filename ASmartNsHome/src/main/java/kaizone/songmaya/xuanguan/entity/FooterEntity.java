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
//    private String target0;
//    private String title0;
//    private String text1;
//    private String href1;
//    private String target1;
//    private String title1;

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
}
