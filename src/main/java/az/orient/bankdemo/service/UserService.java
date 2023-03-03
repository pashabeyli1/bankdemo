package az.orient.bankdemo.service;

import az.orient.bankdemo.dto.TokenDto;
import az.orient.bankdemo.dto.request.ReqUser;
import az.orient.bankdemo.dto.response.RespStatus;
import az.orient.bankdemo.dto.response.RespUser;
import az.orient.bankdemo.dto.response.Response;

public interface UserService {

    Response<RespUser> login(ReqUser reqUser);

    Response logout(TokenDto tokenDto);
}
