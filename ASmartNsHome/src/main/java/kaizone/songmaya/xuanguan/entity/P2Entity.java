package kaizone.songmaya.xuanguan.entity;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.List;

@Entity
@Table(name = "p2")
public class P2Entity {
    private String flag;
    private String title;
    private String content;
    private List<P2SkillBarEntity> skillBars;
    private String text1;
    private String text2;
    private String text3;
    private String text4;

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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public List<P2SkillBarEntity> getSkillBars() {
        return skillBars;
    }

    public void setSkillBars(List<P2SkillBarEntity> skillBars) {
        this.skillBars = skillBars;
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
}
