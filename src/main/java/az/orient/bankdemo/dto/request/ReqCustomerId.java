package az.orient.bankdemo.dto.request;

import az.orient.bankdemo.dto.TokenDto;
import lombok.Data;

@Data
public class ReqCustomerId {

    private Long customerId;
    private TokenDto tokenDto;
}
