package kaizone.songmaya.xuanguan.entity;

import javax.persistence.*;

@Entity
@Table(name = "sns_menu")
public class MenuEntity {
    @Id
//    @GeneratedValue
    @Column(name = "id")
    private Long id;
    private String text;
    private String href;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }
}
