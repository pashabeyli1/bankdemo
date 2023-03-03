package az.orient.bankdemo.repository;

import az.orient.bankdemo.entity.User;
import az.orient.bankdemo.entity.UserToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserTokenRepository extends JpaRepository<UserToken,Long> {

    UserToken findUserTokenByUserAndTokenAndActive(User user,String token,Integer active);

}
