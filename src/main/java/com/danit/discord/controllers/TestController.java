package com.danit.discord.controllers;

import com.danit.discord.entities.Test;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

@Controller
public class TestController {
//    @QueryMapping
//    public List<Test> testsPagination(@Argument int count, @Argument int offset) {
//        return postDao.getRecentPosts(count, offset);
//        return new ArrayList<Test>();
//    }

    @ResponseBody
    @RequestMapping(method = RequestMethod.GET, path = "/")
    public String helloTest() {
        return "hello test";
    }

}
