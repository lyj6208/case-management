package com.testing_company.case_management.service;

import com.testing_company.case_management.dto.PointOfContactDTO;
import com.testing_company.case_management.exception.NotFoundException;
import com.testing_company.case_management.model.Customer;
import com.testing_company.case_management.model.PointOfContact;
import com.testing_company.case_management.model.User;
import com.testing_company.case_management.repository.CustomerRepository;
import com.testing_company.case_management.repository.PointOfContactRepository;
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
public class PointOfContactService {
    private final PointOfContactRepository pointOfContactRepository;
    private final CustomerRepository customerRepository;
    private final UserRepository userRepository;
    public List<PointOfContact> createPointOfContact(List<PointOfContact> pointOfContact){
        LogUtils.logRequest(log,this,"建立PointOfContact：{}"+pointOfContact);
        List<PointOfContact> createdPointOfContact=pointOfContactRepository.saveAll(pointOfContact);
        LogUtils.logResponse(log,this,"建立PointOfContact：{}"+pointOfContact);
        return pointOfContact;
    }

    public PointOfContactDTO findPointOfContactById(Long pointOfContactId){
        LogUtils.logRequest(log,this,"尋找PointOfContact_ID：{}"+pointOfContactId);
        PointOfContact foundPointOfContact=pointOfContactRepository.findById(pointOfContactId).orElseThrow(()->new NotFoundException("找不到ID為"+pointOfContactId+"之PointOfContact"));
        LogUtils.logResponse(log,this,"尋找PointOfContact_ID：{}"+pointOfContactId);
        return convertToDTO(foundPointOfContact);
    }
    public Page<PointOfContactDTO> findAllPointOfContacts(Pageable pageable){
        LogUtils.logRequest(log,this,"尋找所有PointOfContact");
        Page<PointOfContact>foundPointOfContact=pointOfContactRepository.findAll(pageable);
        LogUtils.logResponse(log,this,"尋找所有PointOfContact");
        return foundPointOfContact.map(this::convertToDTO);
    }
    public PointOfContactDTO convertToDTO(PointOfContact pointOfContact){
        Customer customer= BeanUtil.findIfIdPresent(pointOfContact.getCustomerId(),customerRepository::findById);
        User modifies=BeanUtil.findIfIdPresent(pointOfContact.getLastModifiedById(),userRepository::findById);
        return PointOfContactDTO.builder()
                .id(pointOfContact.getId())
                .customerId(customer!=null?pointOfContact.getCustomerId():null)
                .customerName(customer!=null? customer.getName() : null)
                .contactPerson(pointOfContact.getContactPerson())
                .contactPhone(pointOfContact.getContactPhone())
                .contactEmail(pointOfContact.getContactEmail())
                .contactAddress(pointOfContact.getContactAddress())
                .createdTime(pointOfContact.getCreatedTime())
                .lastModifiedTime(pointOfContact.getLastModifiedTime())
                .lastModifiedBy(modifies!=null? modifies.getEmployeeNumber()+"_"+modifies.getName() :null)
        .build();
    }
    public PointOfContactDTO updatePointOfContact(Long pointOfContactId, PointOfContact inputtedPointOfContact){
        LogUtils.logRequest(log,this,"更新PointOfContact_ID：{}"+pointOfContactId);
        PointOfContact updatedPointOfContact=pointOfContactRepository.findById(pointOfContactId).orElseThrow(()->new NotFoundException("找不到ID為"+pointOfContactId+"的PointOfContact"));
        BeanUtil.copyNotNullProperties(inputtedPointOfContact, updatedPointOfContact);
        pointOfContactRepository.save(updatedPointOfContact);
        LogUtils.logResponse(log,this,"更新PointOfContact_ID：{}"+pointOfContactId);
        return convertToDTO(updatedPointOfContact);
    }
}
