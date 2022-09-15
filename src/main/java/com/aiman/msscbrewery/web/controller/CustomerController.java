package com.aiman.msscbrewery.web.controller;

import com.aiman.msscbrewery.web.model.CustomerDto;
import com.aiman.msscbrewery.web.services.CustomerService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("api/v1/customer")
public class CustomerController {

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping("/{customerId}")
    public ResponseEntity<CustomerDto> getCustomer(@PathVariable("customerId") UUID customerId){
        return new ResponseEntity<>(customerService.getCustomerById(customerId), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<CustomerDto> handlePost(@RequestBody CustomerDto customerDto){

        CustomerDto savedCustomer=customerService.saveNewCustomer(customerDto);

        HttpHeaders headers=new HttpHeaders();
        headers.add("Location","api/v1/customer/"+savedCustomer.getId().toString());

        return new ResponseEntity<>(headers,HttpStatus.CREATED);
    }
    @PutMapping({"/{customerId}"})
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void handleUpdate(@PathVariable("customerId") UUID customerId, @RequestBody CustomerDto customerDto){

        customerService.updateCustomer(customerId,customerDto);
    }

    @DeleteMapping({"/{customerId}"})
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCustomer(@PathVariable("customerId") UUID customerId){

        customerService.deleteCustomerById(customerId);
    }
}
