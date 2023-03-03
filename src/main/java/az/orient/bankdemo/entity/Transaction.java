package az.orient.bankdemo.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;
import java.util.Date;

@Table(name = "transaction")
@Entity
@DynamicInsert
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String receiptNo;
    @ManyToOne
    @JoinColumn(name = "dt_account_id")
    private Account dtAccount;
    private String  crAccount;
    private Integer amount;
    @CreationTimestamp
    private Date    trDate;
    @ColumnDefault(value = "1")
    private Integer active;


}
