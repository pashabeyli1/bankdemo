package az.orient.bankdemo.service.impl;

import az.orient.bankdemo.dto.request.ReqAccount;
import az.orient.bankdemo.dto.response.RespAccount;
import az.orient.bankdemo.dto.response.RespCustomer;
import az.orient.bankdemo.dto.response.RespStatus;
import az.orient.bankdemo.dto.response.Response;
import az.orient.bankdemo.entity.Account;
import az.orient.bankdemo.entity.Customer;
import az.orient.bankdemo.enums.EnumAvailableStatus;
import az.orient.bankdemo.exception.BankException;
import az.orient.bankdemo.exception.ExceptionConstants;
import az.orient.bankdemo.repository.AccountRepository;
import az.orient.bankdemo.repository.CustomerRepository;
import az.orient.bankdemo.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;

    private final CustomerRepository customerRepository;

    @Override
    public Response<List<RespAccount>> getAccountListByCustomerId(Long customerId) {
        Response<List<RespAccount>> response = new Response<>();
        try {
            Customer customer = customerRepository.findCustomerByIdAndActive(customerId, EnumAvailableStatus.ACTIVE.getValue());
            if (customer == null) {
                throw new BankException(ExceptionConstants.CUSTOMER_NOT_FOUND,"Customer not found");
            }
            List<Account> accountList = accountRepository.findAllByCustomerAndActive(customer,EnumAvailableStatus.ACTIVE.getValue());
            if (accountList.isEmpty()) {
                throw new BankException(ExceptionConstants.ACCOUNT_NOT_FOUND,"Account not found");
            }
            List<RespAccount> respAccountList = accountList.stream().map(this::convert).collect(Collectors.toList());
            response.setT(respAccountList);
            response.setStatus(RespStatus.getSuccessMessage());
        } catch (BankException ex) {
            ex.printStackTrace();
            response.setStatus(new RespStatus(ex.getCode(), ex.getMessage()));
        } catch (Exception ex) {
            ex.printStackTrace();
            response.setStatus(new RespStatus(ExceptionConstants.INTERNAL_EXCEPTION, "Internal Exception"));
        }
        return response;
    }

    @Override
    public Response createAccount(ReqAccount reqAccount) {
        Response response = new Response();
        try {
            Long customerId = reqAccount.getCustomerId();
            String name = reqAccount.getName();
            if (customerId == null || name == null){
                throw new BankException(ExceptionConstants.CUSTOMER_NOT_FOUND,"Customer not found!");
            }
            Customer customer = customerRepository.findCustomerByIdAndActive(reqAccount.getCustomerId(),EnumAvailableStatus.ACTIVE.getValue());
            if (customer == null) {
                throw new BankException(ExceptionConstants.CUSTOMER_NOT_FOUND,"Customer not found!");
            }
            Account account = Account.builder()
                    .name(reqAccount.getName())
                    .accountNo(reqAccount.getAccountNo())
                    .amount(reqAccount.getAmount())
                    .currency(reqAccount.getCurrency())
                    .iban(reqAccount.getIban())
                    .customer(customer)
                    .build();
         accountRepository.save(account);
         response.setStatus(RespStatus.getSuccessMessage());
        } catch (BankException ex) {
            ex.printStackTrace();
            response.setStatus(new RespStatus(ex.getCode(), ex.getMessage()));
        } catch (Exception ex) {
            ex.printStackTrace();
            response.setStatus(new RespStatus(ExceptionConstants.INTERNAL_EXCEPTION, "Internal Exception"));
        }
        return response;
    }


    private RespAccount convert(Account account) {
        RespCustomer respCustomer = RespCustomer.builder()
                .id(account.getCustomer().getId())
                .name(account.getCustomer().getName())
                .surname(account.getCustomer().getSurname())
                .build();
        RespAccount respAccount = RespAccount.builder()
                .id(account.getId())
                .name(account.getName())
                .accountNo(account.getAccountNo())
                .iban(account.getIban())
                .amount(account.getAmount() != null ? (double)account.getAmount()/100 : null)
                .currency(account.getCurrency())
                .respCustomer(respCustomer)
                .build();
        return respAccount;
    }


}
