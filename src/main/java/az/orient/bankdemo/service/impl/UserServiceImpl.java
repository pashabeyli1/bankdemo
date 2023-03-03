package az.orient.bankdemo.service.impl;

import az.orient.bankdemo.dto.TokenDto;
import az.orient.bankdemo.dto.request.ReqUser;
import az.orient.bankdemo.dto.response.RespStatus;
import az.orient.bankdemo.dto.response.RespUser;
import az.orient.bankdemo.dto.response.Response;
import az.orient.bankdemo.entity.User;
import az.orient.bankdemo.entity.UserToken;
import az.orient.bankdemo.enums.EnumAvailableStatus;
import az.orient.bankdemo.exception.BankException;
import az.orient.bankdemo.exception.ExceptionConstants;
import az.orient.bankdemo.repository.UserRepository;
import az.orient.bankdemo.repository.UserTokenRepository;
import az.orient.bankdemo.service.UserService;
import az.orient.bankdemo.util.Utility;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final UserTokenRepository userTokenRepository;

    private final Utility utility;

    @Override
    public Response<RespUser> login(ReqUser reqUser) {
        Response<RespUser> response = new Response<>();
        try {
            String username = reqUser.getUsername();
            String password = reqUser.getPassword();
            User user = userRepository.findUserByUsernameAndPasswordAndActive(username,password, EnumAvailableStatus.ACTIVE.getValue());
            if (user == null) {
                throw new BankException(ExceptionConstants.INVALID_USER,"Username or password is invalid");
            }
            String token = UUID.randomUUID().toString();
            UserToken userToken = new UserToken();
            userToken.setUser(user);
            userToken.setToken(token);
            userTokenRepository.save(userToken);
            TokenDto tokenDto = new TokenDto();
            tokenDto.setUserId(user.getId());
            tokenDto.setToken(token);
            RespUser respUser = new RespUser();
            respUser.setTokenDto(tokenDto);
            respUser.setFullName(user.getFullName());
            response.setT(respUser);
            response.setStatus(RespStatus.getSuccessMessage());
        } catch (BankException ex) {
            ex.printStackTrace();
            response.setStatus(new RespStatus(ex.getCode(), ex.getMessage()));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return response;
    }

    @Override
    public Response logout(TokenDto tokenDto) {
        Response response = new Response();
        try {
            UserToken userToken = utility.checkToken(tokenDto);
            userToken.setActive(EnumAvailableStatus.DEACTIVE.getValue());
            userTokenRepository.save(userToken);
            response.setStatus(RespStatus.getSuccessMessage());

        } catch (BankException ex) {
            ex.printStackTrace();
            response.setStatus(new RespStatus(ex.getCode(), ex.getMessage()));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return response;
    }
}
