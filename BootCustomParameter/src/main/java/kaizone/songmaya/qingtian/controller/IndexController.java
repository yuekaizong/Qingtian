package kaizone.songmaya.qingtian.controller;

import kaizone.songmaya.qingtian.annotation.ParameterModel;
import kaizone.songmaya.qingtian.bean.StudentEntity;
import kaizone.songmaya.qingtian.bean.TeacherEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class IndexController {

    @RequestMapping(value = "/submit")
    public String resolver(@ParameterModel TeacherEntity teacher, @ParameterModel StudentEntity student) {
        return "教师名称：" + teacher.getName() + "，学生名称：" + student.getName() + "，学生年龄：" + student.getAge();
    }
}
