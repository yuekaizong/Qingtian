package kaizone.songmaya.xuanguan.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "p4_cell")
public class P4CellEntity {
    private String img;
    private String title;
    private String position;
    private String content;

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
