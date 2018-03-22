package kaizone.songmaya.qingtian.bean;

import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.Min;

/**
 * ========================
 * Created with IntelliJ IDEA.
 * User：恒宇少年
 * Date：2017/10/14
 * Time：10:41
 * 码云：http://git.oschina.net/jnyqy
 * ========================
 */
public class StudentEntity {
    //学生姓名
    @NotEmpty
    private String name;

    //年龄
    @Min(value = 18,message = "年龄最小18岁")
    private int age;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
