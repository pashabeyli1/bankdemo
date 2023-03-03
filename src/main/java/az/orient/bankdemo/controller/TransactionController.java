package az.orient.bankdemo.controller;

import az.orient.bankdemo.dto.response.RespTransaction;
import az.orient.bankdemo.dto.response.Response;
import az.orient.bankdemo.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/transaction")
@RequiredArgsConstructor
public class TransactionController {

    private final TransactionService transactionService;

    @GetMapping("/GetTransactionList/{accountId}")
    public Response<List<RespTransaction>>getTransactionList(@PathVariable Long accountId){
        return transactionService.getTransactionList(accountId);
    }
}
