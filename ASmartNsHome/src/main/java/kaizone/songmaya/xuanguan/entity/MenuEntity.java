package kaizone.songmaya.xuanguan.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "menu")
public class MenuEntity {
    private String text;
    private String href;

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
