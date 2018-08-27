package kaizone.songmaya.smartns.model;

import javax.persistence.*;

@Entity
@Table(name = "near_loan")
public class Loan {
    @Id
    @GeneratedValue
    @Column(name = "id")
    private Long id;
    private String name;
    private String img;
    private String origin;
    private String amt;  //金额
    private String execTime; //执行时间
    private String count;   //人数
    private String intro;  //介绍
    private String conditioned;  //条件
    private String process;   //流程

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

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getAmt() {
        return amt;
    }

    public void setAmt(String amt) {
        this.amt = amt;
    }

    public String getExecTime() {
        return execTime;
    }

    public void setExecTime(String execTime) {
        this.execTime = execTime;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }

    public String getConditioned() {
        return conditioned;
    }

    public void setConditioned(String conditioned) {
        this.conditioned = conditioned;
    }

    public String getProcess() {
        return process;
    }

    public void setProcess(String process) {
        this.process = process;
    }
}
