package az.orient.bankdemo.util;

import az.orient.bankdemo.dto.TokenDto;
import az.orient.bankdemo.entity.User;
import az.orient.bankdemo.entity.UserToken;
import az.orient.bankdemo.enums.EnumAvailableStatus;
import az.orient.bankdemo.exception.BankException;
import az.orient.bankdemo.exception.ExceptionConstants;
import az.orient.bankdemo.repository.UserRepository;
import az.orient.bankdemo.repository.UserTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class Utility {

    private final UserRepository userRepository;

    private final UserTokenRepository userTokenRepository;

    public UserToken checkToken(TokenDto tokenDto) throws BankException {
        String token = tokenDto.getToken();
        Long userId = tokenDto.getUserId();
        if (token == null || userId == null) {
            throw new BankException(ExceptionConstants.INVALID_REQUEST_DATA,"Invalid request data");
        }
        User user = userRepository.findUserByIdAndActive(userId, EnumAvailableStatus.ACTIVE.getValue());
        if (user == null) {
            throw new BankException(ExceptionConstants.INVALID_USER, "Invalid user");
        }
        UserToken userToken = userTokenRepository.findUserTokenByUserAndTokenAndActive(user,token,EnumAvailableStatus.ACTIVE.getValue());
        if (userToken == null) {
            throw new BankException(ExceptionConstants.INVALID_TOKEN, "Invalid token");
        }
        return userToken;
    }

}
