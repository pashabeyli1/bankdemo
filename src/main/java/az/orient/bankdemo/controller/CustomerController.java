package az.orient.bankdemo.controller;

import az.orient.bankdemo.dto.TokenDto;
import az.orient.bankdemo.dto.request.ReqCustomer;
import az.orient.bankdemo.dto.request.ReqCustomerId;
import az.orient.bankdemo.dto.response.RespCustomer;
import az.orient.bankdemo.dto.response.Response;
import az.orient.bankdemo.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/customer")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;


    @PostMapping("/GetCustomerList")
    public Response<List<RespCustomer>> getCustomerList(@RequestBody TokenDto tokenDto) {
      return customerService.getCustomerList(tokenDto);
    }

    @PostMapping("/GetCustomerById")
    public Response<RespCustomer> getCustomerById(@RequestBody ReqCustomerId reqCustomerId) {
        return customerService.getCustomerById(reqCustomerId);
    }


    @PostMapping("/AddCustomer")
    public Response addCustomer(@RequestBody ReqCustomer reqCustomer) {
        return customerService.addCustomer(reqCustomer);
    }

    @PutMapping("/UpdateCustomer")
    public Response updateCustomer(@RequestBody ReqCustomer reqCustomer) {
        return customerService.updateCustomer(reqCustomer);
    }

    @PutMapping("/DeleteCustomer")
    public Response deleteCustomer(@PathVariable ReqCustomer reqCustomer) {
        return customerService.deleteCustomer(reqCustomer);
    }


}
