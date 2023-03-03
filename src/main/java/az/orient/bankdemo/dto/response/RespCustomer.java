package az.orient.bankdemo.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RespCustomer {

    private Long id;
    private String name;
    private String surname;
    private String address;
    private String dob;
    private String phone;
    private String cif;
    private String pin;
    private String seria;
}
