package az.orient.bankdemo.enums;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;


@AllArgsConstructor
@Getter
public enum EnumAvailableStatus {

    ACTIVE(1) , DEACTIVE(0) , DELETED(2);

   private int value;



}
