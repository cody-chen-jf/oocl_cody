package controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import pojo.User;
import service.UserService;

import javax.annotation.Resource;
import java.util.UUID;

/**
 * Created by CHENCO7 on 8/9/2017.
 */
@Controller
public class UserController {
    @Resource(name = "userServiceImpl")
    private UserService service;

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public String register(User user){
        user.setId(UUID.randomUUID().toString());
        if(service.register(user)){
            return "redirect:login.jsp";
        }else {
            return "forward:register.jsp";
        }
    }

    @RequestMapping(value = "login", method = RequestMethod.POST)
    public String login(String name, String password, Model model){
        User user = service.login(name, password);
        if(user != null){
            return "success";
        }else {
            model.addAttribute("error", "用户名密码错误");
            return "login";
        }
    }
}
