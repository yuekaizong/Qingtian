package kaizone.songmaya.qingtian.hystric;

import kaizone.songmaya.qingtian.service.SchedualServiceHi;
import org.springframework.stereotype.Component;

@Component
public class SchedualServiceHiHystric implements SchedualServiceHi{

    @Override
    public String sayHiFromClientOne(String name) {
        return "sorry "+name;
    }
}
