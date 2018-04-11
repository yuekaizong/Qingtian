package kaizone.songmaya.xuanguan.entity;

import javax.persistence.*;

@Entity
@Table(name = "p1_cell")
public class DivCellEntity {

    @Id
//    @GeneratedValue
    @Column(name = "id")
    private Long id;
    private Long pid;
    private String img;
    private String title;
    private String description;
    private String href;
    private String dataTarget;
    private String viewMore;
    private String position;
    private String content;
    private String name;
    private String value;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getPid() {
        return pid;
    }

    public void setPid(Long pid) {
        this.pid = pid;
    }

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

    public String getDataTarget() {
        return dataTarget;
    }

    public void setDataTarget(String dataTarget) {
        this.dataTarget = dataTarget;
    }

    public String getViewMore() {
        return viewMore;
    }

    public void setViewMore(String viewMore) {
        this.viewMore = viewMore;
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
