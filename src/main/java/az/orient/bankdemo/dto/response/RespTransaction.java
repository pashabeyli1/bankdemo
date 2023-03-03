package az.orient.bankdemo.dto.response;

import az.orient.bankdemo.entity.Account;
import lombok.Builder;
import lombok.Data;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.util.Date;

@Data
@Builder
public class RespTransaction {

    private Long id;
    private String receiptNo;
    private RespAccount dtAccount;
    private String  crAccount;
    private Double amount;
    private Date trDate;

}
