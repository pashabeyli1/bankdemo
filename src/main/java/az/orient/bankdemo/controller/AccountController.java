package az.orient.bankdemo.controller;

import az.orient.bankdemo.dto.request.ReqAccount;
import az.orient.bankdemo.dto.response.RespAccount;
import az.orient.bankdemo.dto.response.Response;
import az.orient.bankdemo.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/account")
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;

    @GetMapping("/GetAccountList/{customerId}")
    public Response<List<RespAccount>> getAccountListByCustomerId(@PathVariable Long customerId) {
         return accountService.getAccountListByCustomerId(customerId);
    }

    @PostMapping("/CreateAccount")
    public Response createAccount(@RequestBody ReqAccount reqAccount) {
        return accountService.createAccount(reqAccount);
    }


}
