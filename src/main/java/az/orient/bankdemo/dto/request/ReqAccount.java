package az.orient.bankdemo.dto.request;

import az.orient.bankdemo.dto.TokenDto;
import az.orient.bankdemo.entity.Customer;
import lombok.Data;
import java.util.Date;

@Data
public class ReqAccount {

    private Long id;
    private String name;
    private String accountNo;
    private String iban;
    private Integer amount;
    private String currency;
    private Long customerId;
    private TokenDto tokenDto;

}
