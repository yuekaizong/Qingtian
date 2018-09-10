package kaizone.songmaya.smartns.controller;

import kaizone.songmaya.smartns.jpa.LoanJpa;
import kaizone.songmaya.smartns.model.Loan;
import kaizone.songmaya.smartns.model.loan.Dict;
import kaizone.songmaya.smartns.service.loan.DictService;
import kaizone.songmaya.smartns.util.ObjectUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@RestController
public class LoanController extends BaseController {

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    LoanJpa loanJpa;

    @Autowired
    DictService dictService;

    @GetMapping("/loan/list")
    public Map<String, Object> list() {
        Map<String, Object> data = restTemplate.getForObject("http://www.zhonglongit.com:8081/api/loan/loanMain.go", Map.class, "");
//        return success(loanJpa.findAll());
        return success(data);
    }

    @PutMapping("/loan/save")
    public Map<String, Object> loanSave(@RequestBody Loan params) {
        String name = params.getName();
        if (StringUtils.isEmpty(name)) {
            return fail("参数异常");
        }
        Loan loan = loanJpa.findByName(name);
        ObjectUtil.fill(loan, params);
        loanJpa.save(params);
        return success("提交成功");
    }

    @PutMapping("/loan/dict/save")
    public Map<String, Object> dictSave(@RequestBody Dict dict) {
        dictService.save(dict);
        return success("提交成功");
    }

    @GetMapping("/loan/dict/find/pid")
    public Map<String, Object> dictFind(String pid) {
        return success(dictService.findByPid(pid));
    }

    @GetMapping("/loan/dict/all")
    public Map<String, Object> dictAll(String id) {
        return success(dictService.findAll());
    }
}
