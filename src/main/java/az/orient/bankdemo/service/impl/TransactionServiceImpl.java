package az.orient.bankdemo.service.impl;

import az.orient.bankdemo.dto.response.*;
import az.orient.bankdemo.entity.Account;
import az.orient.bankdemo.entity.Customer;
import az.orient.bankdemo.entity.Transaction;
import az.orient.bankdemo.enums.EnumAvailableStatus;
import az.orient.bankdemo.exception.BankException;
import az.orient.bankdemo.exception.ExceptionConstants;
import az.orient.bankdemo.repository.AccountRepository;
import az.orient.bankdemo.repository.TransactionRepository;
import az.orient.bankdemo.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository transactionRepository;
    private final AccountRepository accountRepository;

    @Override
    public Response<List<RespTransaction>> getTransactionList(Long accountId) {

        Response<List<RespTransaction>> response = new Response<>();

        try {
            if(accountId==null){
                throw new BankException(ExceptionConstants.ACCOUNT_NOT_FOUND,"Account not found");
            }
            Account account = accountRepository.findAccountByIdAndActive(accountId,EnumAvailableStatus.ACTIVE.getValue());
            List<Transaction> transactionList = transactionRepository.findAllByDtAccountAndActive(account,EnumAvailableStatus.ACTIVE.getValue());
            if (transactionList.isEmpty()) {
                throw new BankException(ExceptionConstants.TRANSACTION_NO_FOUND, "Transaction not found");
            }
            List<RespTransaction> respTransactionList = transactionList.stream().map(this::convert).collect(Collectors.toList());
            response.setT(respTransactionList);
            response.setStatus(RespStatus.getSuccessMessage());
        } catch (BankException ex) {
            ex.printStackTrace();
            response.setStatus(new RespStatus(ex.getCode(), ex.getMessage()));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return response;
    }


    private RespTransaction convert(Transaction transaction) {
        RespCustomer respCustomer = RespCustomer.builder()
                .id(transaction.getDtAccount().getCustomer().getId())
                .name(transaction.getDtAccount().getCustomer().getName())
                .surname(transaction.getDtAccount().getCustomer().getSurname())
                .build();
        RespAccount respAccount = RespAccount.builder()
                .id(transaction.getDtAccount().getId())
                .iban(transaction.getDtAccount().getIban())
                .accountNo(transaction.getDtAccount().getAccountNo())
                .name(transaction.getDtAccount().getName())
                .respCustomer(respCustomer)
                .build();
        RespTransaction respTransaction=RespTransaction.builder()
                .id(transaction.getId())
                .receiptNo(transaction.getReceiptNo())
                .dtAccount(respAccount)
                .crAccount(transaction.getCrAccount())
                .amount((double)transaction.getAmount()/100)
                .trDate(transaction.getTrDate())
                .build();
        return respTransaction;
    }

}


