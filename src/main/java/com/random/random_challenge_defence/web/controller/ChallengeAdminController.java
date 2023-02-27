package com.random.random_challenge_defence.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class ChallengeAdminController {

    @ResponseBody
    @RequestMapping("/")
    public String test() {
        return "herllo";
    }
}
