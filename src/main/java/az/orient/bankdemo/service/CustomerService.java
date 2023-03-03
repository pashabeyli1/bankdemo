package az.orient.bankdemo.service;

import az.orient.bankdemo.dto.TokenDto;
import az.orient.bankdemo.dto.request.ReqCustomer;
import az.orient.bankdemo.dto.request.ReqCustomerId;
import az.orient.bankdemo.dto.response.RespCustomer;
import az.orient.bankdemo.dto.response.Response;

import java.util.List;

public interface CustomerService {

    Response<List<RespCustomer>> getCustomerList(TokenDto tokenDto);

    Response<RespCustomer> getCustomerById(ReqCustomerId reqCustomerId);

    Response addCustomer(ReqCustomer reqCustomer);

    Response updateCustomer(ReqCustomer reqCustomer);

    Response deleteCustomer(ReqCustomer reqCustomer);
}
