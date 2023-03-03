package az.orient.bankdemo.controller;

import az.orient.bankdemo.dto.TokenDto;
import az.orient.bankdemo.dto.request.ReqUser;
import az.orient.bankdemo.dto.response.RespStatus;
import az.orient.bankdemo.dto.response.RespUser;
import az.orient.bankdemo.dto.response.Response;
import az.orient.bankdemo.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;


    @PostMapping("/login")
    public Response<RespUser> login(@RequestBody ReqUser reqUser) {
        return userService.login(reqUser);
    }


    @PostMapping("/logout")
    public Response logout(@RequestBody TokenDto tokenDto) {
        return userService.logout(tokenDto);
    }


}
