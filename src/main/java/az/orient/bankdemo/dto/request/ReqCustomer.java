package az.orient.bankdemo.dto.request;

import az.orient.bankdemo.dto.TokenDto;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.Date;

@Data
public class ReqCustomer {

    private Long customerId;
    private String name;
    private String surname;
    private String address;
    private Date dob;
    private String phone;
    private String pin;
    private String seria;
    private String cif;
    private TokenDto tokenDto;


}
