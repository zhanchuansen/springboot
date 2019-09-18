package com.senchuan.zhan;

import com.senchuan.zhan.RedisInterface.RedisTemplateDao;
import com.sun.glass.ui.Application;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class RedisTemplateTest {
    public static Logger logger = LoggerFactory.getLogger(RedisTemplateTest.class);

    @Test
    public void contextLoads() {
    }

    @Autowired
    RedisTemplateDao redisTemplateDao;

    @Test
    public void testRedis() {
        redisTemplateDao.setKey("name", "forezp");
        redisTemplateDao.setKey("age", "11");
        logger.info(redisTemplateDao.getValue("name"));
        logger.info(redisTemplateDao.getValue("age"));
    }

}
