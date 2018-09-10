package kaizone.songmaya.smartns.service.loan;

import kaizone.songmaya.smartns.jpa.loan.DictJpa;
import kaizone.songmaya.smartns.model.loan.Dict;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class DictService {

    @Autowired
    DictJpa dictJpa;

    public Map<String, List<Dict>> findByPid(String pid) {
        List<Dict> list = dictJpa.findByPid(Long.parseLong(pid));
        Map<String, List<Dict>> map = list.stream().collect(Collectors.toMap(d -> d.getFlag(), d -> dictJpa.findByPid(d.getId())));
        return map;
    }

    public void save(Dict dict) {

    }

    public List<Dict> findAll() {

        return null;
    }

}
