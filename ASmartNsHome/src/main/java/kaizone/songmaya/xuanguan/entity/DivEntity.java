package kaizone.songmaya.xuanguan.entity;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "sns_p1")
public class DivEntity {
    @Id
//    @GeneratedValue
    @Column(name = "id")
    private Long id;
    private String name;
    private String flag;
    private String title;
    private String description;
    private String content;
    private String position;
    private String text1;
    private String text2;
    private String text3;
    private String text4;

    @OneToMany(cascade=CascadeType.ALL)
    @JoinColumn(name = "pid")
    private List<DivCellEntity> cells;

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

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getText1() {
        return text1;
    }

    public void setText1(String text1) {
        this.text1 = text1;
    }

    public String getText2() {
        return text2;
    }

    public void setText2(String text2) {
        this.text2 = text2;
    }

    public String getText3() {
        return text3;
    }

    public void setText3(String text3) {
        this.text3 = text3;
    }

    public String getText4() {
        return text4;
    }

    public void setText4(String text4) {
        this.text4 = text4;
    }

    public List<DivCellEntity> getCells() {
        return cells;
    }

    public void setCells(List<DivCellEntity> cells) {
        this.cells = cells;
    }
}
