package com.clj.startSpring;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;

import static org.hamcrest.Matchers.equalTo;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = StartSpringApplication.class)
public class StartSpringApplicationTests {

    private MockMvc mvc;

    @Before
    public void set() throws Exception {
        //mvc = MockMvcBuilders.standaloneSetup(new HelloController()).build();
        mvc = MockMvcBuilders.standaloneSetup(new UserControl()).build();
    }

    @Autowired
    private CljProperties properties;

    @Test
    public void getHello() throws Exception {
		/*mvc.perform(MockMvcRequestBuilders.get("/hello").accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(content().string("Hello,World!"));*/
        /*Assert.assertEquals(properties.getName(), "clj");
        Assert.assertEquals(properties.getTitle(), "niubi");*/
    }

    @Test
    public void testUser() throws Exception{
        //测试UserControl
        RequestBuilder requset = null;
        //1、get查找User列表，应该为空
        requset = get("/users/");
        mvc.perform(requset)
                .andExpect(status().isOk())
                .andExpect(content().string(equalTo("[]")));
        //2、post提交一个user
        requset = post("/users/")
                .param("id","1")
                .param("name","屌丝")
                .param("age","20");
        mvc.perform(requset)
                .andExpect(content().string(equalTo("success")));
        //3、get获取user列表，应该有刚才插入的数据
        requset = get("/users/");
        mvc.perform(requset)
                .andExpect(status().isOk())
                .andExpect(content().string(equalTo("[{\"name\":\"屌丝\",\"id\":1,\"age\":20}]")));
        //4、put修改id为1的user
        requset = put("/users/1")
                .param("name","超级大屌丝")
                .param("age","30");
        mvc.perform(requset)
                .andExpect(content().string(equalTo("success")));
        //5、get一个id为1的user
        requset = get("/users/1");
        mvc.perform(requset)
                .andExpect(content().string(equalTo("{\"name\":\"超级大屌丝\",\"id\":1,\"age\":30}")));
        //6、删除一个id为1的user
        requset = delete("/users/1");
        mvc.perform(requset)
                .andExpect(content().string(equalTo("success")));
        //7、get查询user列表，应该为诶空
        requset = get("/users/");
        mvc.perform(requset)
                .andExpect(status().isOk())
                .andExpect(content().string(equalTo("[]")));
    }

    @Test
    public void contextLoads() {
    }

}
