package com.testing_company.case_management.controller;

import com.testing_company.case_management.dto.CustomerResponseDTO;
import com.testing_company.case_management.exception.NotFoundException;
import com.testing_company.case_management.model.Customer;
import com.testing_company.case_management.service.CustomerService;
import com.testing_company.case_management.util.BeanUtil;
import com.testing_company.case_management.util.LogUtils;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/customer")
@Slf4j
@AllArgsConstructor
public class CustomerController {
    private final CustomerService customerService;
    @PostMapping("/create")
    public ResponseEntity<Customer>createCustomer(@Valid@RequestBody Customer customer){
        LogUtils.logRequest(log,this,"建立Customer：{}",customer);
        Customer createdCustomer=customerService.createCustomer(customer);
        LogUtils.logResponse(log,this,"建立Customer：{}",customer);
        return ResponseEntity.ok(createdCustomer);
    }
    @GetMapping("/findById/{customerId}")
    public ResponseEntity<CustomerResponseDTO>findCustomerById(@PathVariable Long customerId){
        LogUtils.logRequest(log,this,"尋找Customer_ID：{}",customerId);
        CustomerResponseDTO foundCustomer=customerService.findCustomerById(customerId);
        LogUtils.logResponse(log,this,"尋找Customer_ID：{}",customerId);
        return ResponseEntity.ok(foundCustomer);
    }
    @GetMapping("/findAll")
    public ResponseEntity<Page<CustomerResponseDTO>>findAllCustomers(
            @RequestParam(defaultValue = "0")int page,
            @RequestParam(defaultValue = "10")int size,
            @RequestParam(defaultValue = "id")String sortBy,
            @RequestParam(defaultValue = "asc")String sortDir
    ){
        LogUtils.logRequest(log,this,"尋找所有Customer");
        Sort sort=sortDir.equalsIgnoreCase("asc")
                ?Sort.by(sortBy).ascending():Sort.by(sortBy).descending();
        Pageable pageable= PageRequest.of(page, size, sort);
        Page<CustomerResponseDTO> foundCustomers=customerService.findAllCustomers(pageable);
        LogUtils.logResponse(log,this,"尋找所有Customer");
        return ResponseEntity.ok(foundCustomers);
    }
    @PostMapping("/update/{customerId}")
    public ResponseEntity<CustomerResponseDTO>updateCustomer(@PathVariable Long customerId, @RequestBody Customer inputtedCustomer){
        LogUtils.logRequest(log,this,"更新Customer_ID"+customerId);
        CustomerResponseDTO updatedCustomer=customerService.updateCustomer(customerId, inputtedCustomer);
        LogUtils.logResponse(log,this,"更新Customer_ID"+customerId);
        return ResponseEntity.ok(updatedCustomer);
    }
}
