package az.orient.bankdemo.service;

import az.orient.bankdemo.dto.request.ReqAccount;
import az.orient.bankdemo.dto.response.RespAccount;
import az.orient.bankdemo.dto.response.Response;

import java.util.List;

public interface AccountService {
    Response<List<RespAccount>> getAccountListByCustomerId(Long customerId);

    Response createAccount(ReqAccount reqAccount);
}
