package kaizone.songmaya.qingtian;

import kaizone.songmaya.qingtian.core.MailContentTypeEnum;
import kaizone.songmaya.qingtian.core.MailSender;

import java.util.ArrayList;

public class TestMail {

    public static void main(String[] args) throws Exception{
        ArrayList<String> targets = new ArrayList<>();
        targets.add("yuelibiao@haiercash.com");

        new MailSender().title("测试SpringBoot发送邮件")
                .content("简单文本内容发送")
                .contentType(MailContentTypeEnum.TEXT)
                .targets(targets).send();
    }
}
