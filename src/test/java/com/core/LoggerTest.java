package com.core;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Created by Richard on 2017/11/29.
 */

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class LoggerTest {
    //
    //如果引用了@Slf4j注解可以省略如下代码
   // private final Logger logger = LoggerFactory.getLogger(LoggerTest.class);

    @Test
    public void test1(){

/*        String name ="name";
        String password ="password";

       //logger.info("info...");
       log.info("info...");
       log.debug("debug...");
       log.error("error...");

       //两种Log输出方法
       log.info("name :" + name + ",password :" + password);
       log.info("name : {} , password : {}" , name ,password);*/

    }
}
