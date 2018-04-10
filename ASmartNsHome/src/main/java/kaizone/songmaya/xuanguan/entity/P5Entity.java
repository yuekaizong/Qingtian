package kaizone.songmaya.xuanguan.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "p5")
public class P5Entity {
    private String title;
    private String content;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
