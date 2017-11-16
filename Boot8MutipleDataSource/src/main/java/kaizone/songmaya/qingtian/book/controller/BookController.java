package kaizone.songmaya.qingtian.book.controller;

import kaizone.songmaya.qingtian.book.bean.BookBean;
import kaizone.songmaya.qingtian.book.jpa.BookJPA;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/book")
public class BookController {
    @Autowired
    private BookJPA bookJPA;

    /**
     * 查询书籍列表
     *
     * @return
     */
    @RequestMapping(value = "/list")
    public List<BookBean> list() {
        return bookJPA.findAll();
    }
}
