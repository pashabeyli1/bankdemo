package az.orient.bankdemo.dto.response;

import az.orient.bankdemo.dto.TokenDto;
import lombok.Data;

@Data
public class RespUser {

    private String fullName;
    private TokenDto tokenDto;

}
