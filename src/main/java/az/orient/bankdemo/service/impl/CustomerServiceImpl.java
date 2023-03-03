package az.orient.bankdemo.service.impl;

import az.orient.bankdemo.dto.TokenDto;
import az.orient.bankdemo.dto.request.ReqCustomer;
import az.orient.bankdemo.dto.request.ReqCustomerId;
import az.orient.bankdemo.dto.response.RespCustomer;
import az.orient.bankdemo.dto.response.RespStatus;
import az.orient.bankdemo.dto.response.Response;
import az.orient.bankdemo.entity.Customer;
import az.orient.bankdemo.enums.EnumAvailableStatus;
import az.orient.bankdemo.exception.BankException;
import az.orient.bankdemo.exception.ExceptionConstants;
import az.orient.bankdemo.repository.CustomerRepository;
import az.orient.bankdemo.service.CustomerService;
import az.orient.bankdemo.util.Utility;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;

    private final Utility utility;


    DateFormat df = new SimpleDateFormat("yyyy-MM-dd");


    @Override
    public Response<List<RespCustomer>> getCustomerList(TokenDto tokenDto) {
        Response<List<RespCustomer>> response = new Response<>();
        try {
            utility.checkToken(tokenDto);
            List<Customer> customerList = customerRepository.findAllByActive(EnumAvailableStatus.ACTIVE.getValue());
            if (customerList.isEmpty()) {
                throw new BankException(ExceptionConstants.CUSTOMER_NOT_FOUND, "Customer not found");
            }
            List<RespCustomer> respCustomerList = customerList.stream().map(this::convert).collect(Collectors.toList());
            response.setT(respCustomerList);
            response.setStatus(RespStatus.getSuccessMessage());
        } catch (BankException ex) {
            ex.printStackTrace();
            response.setStatus(new RespStatus(ex.getCode(), ex.getMessage()));
        } catch (Exception ex) {
            ex.printStackTrace();
            response.setStatus(new RespStatus(ExceptionConstants.INTERNAL_EXCEPTION, "Internal Exception"));
        }
        return response;
    }

    @Override
    public Response<RespCustomer> getCustomerById(ReqCustomerId reqCustomerId) {
        Response<RespCustomer> response = new Response<>();
        try {
            utility.checkToken(reqCustomerId.getTokenDto());
            if (reqCustomerId.getCustomerId() == null) {
                throw new BankException(ExceptionConstants.INVALID_REQUEST_DATA, "Invalid request data");
            }
            Customer customer = customerRepository.findCustomerByIdAndActive(reqCustomerId.getCustomerId(), EnumAvailableStatus.ACTIVE.getValue());
            if (customer == null) {
                throw new BankException(ExceptionConstants.CUSTOMER_NOT_FOUND, "Customer not found");
            }
            RespCustomer respCustomer = convert(customer);
            response.setT(respCustomer);
            response.setStatus(RespStatus.getSuccessMessage());
        } catch (BankException ex) {
            ex.printStackTrace();
            response.setStatus(new RespStatus(ex.getCode(), ex.getMessage()));
        } catch (Exception ex) {
            ex.printStackTrace();
            response.setStatus(new RespStatus(ExceptionConstants.INTERNAL_EXCEPTION, "Internal Exception"));
        }
        return response;
    }

    @Override
    public Response addCustomer(ReqCustomer reqCustomer) {
        Response response = new Response();
        try {
            utility.checkToken(reqCustomer.getTokenDto());
            String name = reqCustomer.getName();
            String surname = reqCustomer.getSurname();
            if (name == null || surname == null) {
                throw new BankException(ExceptionConstants.INVALID_REQUEST_DATA,"Invalid request data");
            }
            Customer customer = Customer.builder()
                    .name(name)
                    .surname(surname)
                    .address(reqCustomer.getAddress())
                    .dob(reqCustomer.getDob())
                    .phone(reqCustomer.getPhone())
                    .pin(reqCustomer.getPin())
                    .cif(reqCustomer.getCif())
                    .seria(reqCustomer.getSeria())
                    .build();

            customerRepository.save(customer);
            response.setStatus(RespStatus.getSuccessMessage());

        } catch (BankException ex) {
            ex.printStackTrace();
            response.setStatus(new RespStatus(ex.getCode(), ex.getMessage()));
        } catch (Exception ex) {
            ex.printStackTrace();
            response.setStatus(new RespStatus(ExceptionConstants.INTERNAL_EXCEPTION, "Internal Exception"));
        }
        return response;
    }

    @Override
    public Response updateCustomer(ReqCustomer reqCustomer) {
        Response response = new Response();
        try {
            utility.checkToken(reqCustomer.getTokenDto());
            Long customerId = reqCustomer.getCustomerId();
            String name = reqCustomer.getName();
            String surname = reqCustomer.getSurname();
            if (customerId == null || name == null || surname == null) {
                throw new BankException(ExceptionConstants.INVALID_REQUEST_DATA,"Invalid request data");
            }
            Customer customer = customerRepository.findCustomerByIdAndActive(customerId,EnumAvailableStatus.ACTIVE.getValue());
            if (customer == null) {
                throw new BankException(ExceptionConstants.CUSTOMER_NOT_FOUND,"Customer not found");
            }
            customer = Customer.builder()
                    .id(customer.getId())
                    .name(name)
                    .surname(surname)
                    .address(reqCustomer.getAddress())
                    .dob(reqCustomer.getDob())
                    .phone(reqCustomer.getPhone())
                    .pin(reqCustomer.getPin())
                    .cif(reqCustomer.getCif())
                    .seria(reqCustomer.getSeria())
                    .dataDate(customer.getDataDate())
                    .active(customer.getActive())
                    .build();

            customerRepository.save(customer);
            response.setStatus(RespStatus.getSuccessMessage());

        } catch (BankException ex) {
            ex.printStackTrace();
            response.setStatus(new RespStatus(ex.getCode(), ex.getMessage()));
        } catch (Exception ex) {
            ex.printStackTrace();
            response.setStatus(new RespStatus(ExceptionConstants.INTERNAL_EXCEPTION, "Internal Exception"));
        }
        return response;
    }

    @Override
    public Response deleteCustomer(ReqCustomer reqCustomer) {
        Response response = new Response();
        try {
            utility.checkToken(reqCustomer.getTokenDto());
            if (reqCustomer == null) {
                throw new BankException(ExceptionConstants.INVALID_REQUEST_DATA,"Invalid request data");
            }
            Customer customer = customerRepository.findCustomerByIdAndActive(reqCustomer.getCustomerId(), EnumAvailableStatus.ACTIVE.getValue());
            if (customer == null) {
                throw new BankException(ExceptionConstants.CUSTOMER_NOT_FOUND,"Customer not found");
            }
            customer.setActive(EnumAvailableStatus.DEACTIVE.getValue());
            customerRepository.save(customer);
            response.setStatus(RespStatus.getSuccessMessage());
        } catch (BankException ex) {
            ex.printStackTrace();
            response.setStatus(new RespStatus(ex.getCode(), ex.getMessage()));
        } catch (Exception ex) {
            ex.printStackTrace();
            response.setStatus(new RespStatus(ExceptionConstants.INTERNAL_EXCEPTION, "Internal Exception"));
        }
        return response;
    }


    private RespCustomer convert(Customer customer) {
        RespCustomer respCustomer = RespCustomer.builder().
                id(customer.getId()).
                name(customer.getName()).
                surname(customer.getSurname()).
                address(customer.getAddress()).
                dob(customer.getDob() != null ? df.format(customer.getDob()) : null).
                phone(customer.getPhone()).
                pin(customer.getPin()).
                seria(customer.getSeria()).
                cif(customer.getCif()).
                build();
        return respCustomer;
    }

}
