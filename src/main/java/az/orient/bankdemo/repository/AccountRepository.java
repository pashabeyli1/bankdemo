package az.orient.bankdemo.repository;

import az.orient.bankdemo.entity.Account;
import az.orient.bankdemo.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AccountRepository extends JpaRepository<Account,Long> {

    Account findAccountByIdAndActive(Long id,Integer active);

    List<Account> findAllByCustomerAndActive(Customer customer,Integer active);

}
