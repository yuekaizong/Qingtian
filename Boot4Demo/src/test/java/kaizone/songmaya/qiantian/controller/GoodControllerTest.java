package kaizone.songmaya.qiantian.controller;

import kaizone.songmaya.qiantian.entity.GoodEntity;
import kaizone.songmaya.qiantian.entity.GoodTypeEntity;
import kaizone.songmaya.qiantian.jpa.GoodJPA;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.HandlerInterceptor;

@RunWith(SpringRunner.class)
@SpringBootTest
public class GoodControllerTest {

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private GoodJPA goodJPA;

    @Before
    public void before() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void testIndex() throws Exception {
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/good/index")
                .param("name", "admin")).andReturn();

        HandlerInterceptor[] interceptors = mvcResult.getInterceptors();
        if (interceptors != null) {
            for (int i = 0; i < interceptors.length; i++) {
                System.out.println(interceptors[i].getClass().getName());
            }
        }

        int status = mvcResult.getResponse().getStatus();
        String responseString = mvcResult.getResponse().getContentAsString();
        System.out.println("status=" + status);
        System.out.println("返回内容=" + responseString);
    }

    @Test
    public void testDetail() throws Exception {
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/good/detail").param("id", "1"))
                .andReturn();
        int status = mvcResult.getResponse().getStatus();
        String responseString = mvcResult.getResponse().getContentAsString();
        System.out.println("status=" + status);
        System.out.println("responseString=" + responseString);
    }

    @Test
    public void testInsert(){
        GoodEntity item = new GoodEntity();
        item.setTitle("西红柿");
        item.setOrder(2);
        item.setPrice(3.28);
        item.setUnit("元");
        GoodTypeEntity type = new GoodTypeEntity();
        type.setId(1L);
        item.setType(type);
        item.setType(type);
        goodJPA.save(item);

        Assert.assertNotNull(item.getId());
    }

    @Test
    public void testDelete(){
        goodJPA.delete(2L);
        Assert.assertNull(goodJPA.findOne(2L));
    }

}






