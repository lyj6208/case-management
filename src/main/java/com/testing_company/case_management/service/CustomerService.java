package com.testing_company.case_management.service;

import com.testing_company.case_management.dto.CustomerResponseDTO;
import com.testing_company.case_management.exception.NotFoundException;
import com.testing_company.case_management.model.Customer;
import com.testing_company.case_management.model.IndustryCategory;
import com.testing_company.case_management.model.User;
import com.testing_company.case_management.repository.CustomerRepository;
import com.testing_company.case_management.repository.IndustryCategoryRepository;
import com.testing_company.case_management.repository.UserRepository;
import com.testing_company.case_management.util.BeanUtil;
import com.testing_company.case_management.util.LogUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@AllArgsConstructor
public class CustomerService {
    private final CustomerRepository customerRepository;
    private final IndustryCategoryRepository industryCategoryRepository;
    private final UserRepository userRepository;

    public List<Customer> createCustomer(List<Customer> customer){
        LogUtils.logRequest(log,this,"建立Customer：{}",customer);
        List<Customer> createdCustomer=customerRepository.saveAll(customer);
        LogUtils.logResponse(log,this,"建立Customer：{}",customer);
        return createdCustomer;
    }
    public CustomerResponseDTO findCustomerById(Long customerId){
        LogUtils.logRequest(log,this,"尋找Customer_ID：{}",customerId);
        Customer foundCustomer=customerRepository.findById(customerId).orElseThrow(()->new NotFoundException("找不到ID為"+customerId+"之customer"));
        LogUtils.logResponse(log,this,"尋找Customer_ID：{}",customerId);
        return convertToDTO(foundCustomer);
    }
    public Page<CustomerResponseDTO> findAllCustomers(Pageable pageable){
        LogUtils.logRequest(log,this,"尋找所有Customer");
        Page<Customer> foundCustomers=customerRepository.findAll(pageable);
        LogUtils.logResponse(log,this,"尋找所有Customer");
        return foundCustomers.map(this::convertToDTO);
    }
    public CustomerResponseDTO updateCustomer(Long customerId, Customer inputtedcustomer){
        LogUtils.logRequest(log,this,"更新Customer_ID"+customerId);
        Customer updatedCustomer=customerRepository.findById(customerId).orElseThrow(()->new NotFoundException("找不到ID為"+customerId+"的Customer"));
        BeanUtil.copyNotNullProperties(inputtedcustomer, updatedCustomer);
        customerRepository.save(updatedCustomer);
        LogUtils.logResponse(log,this,"更新Customer_ID"+customerId);
        return convertToDTO(updatedCustomer);
    }
    public CustomerResponseDTO convertToDTO(Customer customer){
        IndustryCategory industryCategory= BeanUtil.findIfIdPresent(customer.getIndustryCategoryId(),industryCategoryRepository::findById);
        User modifier=BeanUtil.findIfIdPresent(customer.getLastModifiedById(),userRepository::findById);
        return CustomerResponseDTO.builder()
                .id(customer.getId())
                .isCompany(customer.getIsCompany())
                .name(customer.getName())
                .industryCategory(industryCategory!=null?industryCategory.getIndustryCategory():null)
                .address(customer.getAddress())
                .phone(customer.getPhone())
                .createdTime(customer.getCreatedTime())
                .lastModifiedTime(customer.getLastModifiedTime())
                .lastModifiedBy(modifier!=null?modifier.getEmployeeNumber()+"_"+modifier.getName():null)
                .build();
    }
}
