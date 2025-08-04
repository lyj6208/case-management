package com.testing_company.case_management.controller;

import com.testing_company.case_management.dto.PointOfContactDTO;
import com.testing_company.case_management.exception.NotFoundException;
import com.testing_company.case_management.model.PointOfContact;
import com.testing_company.case_management.service.PointOfContactService;
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
@RequestMapping("/api/pointOfContact")
@Slf4j
@AllArgsConstructor
public class PointOfContactController {
    private final PointOfContactService pointOfContactService;
    @PostMapping("/create")
    public ResponseEntity<PointOfContact>createPointOfContact(@Valid@RequestBody PointOfContact pointOfContact){
        LogUtils.logRequest(log,this,"建立PointOfContact：{}"+pointOfContact);
        PointOfContact createdPointOfContact=pointOfContactService.createPointOfContact(pointOfContact);
        LogUtils.logResponse(log,this,"建立PointOfContact：{}"+pointOfContact);
        return ResponseEntity.ok(createdPointOfContact);
    }
    @GetMapping("/findById/{pointOfContactId}")
    public ResponseEntity<PointOfContactDTO>findPointOfContactById(@PathVariable Long pointOfContactId){
        LogUtils.logRequest(log,this,"尋找PointOfContact_ID：{}"+pointOfContactId);
        PointOfContactDTO foundPointOfContact=pointOfContactService.findPointOfContactById(pointOfContactId);
        LogUtils.logResponse(log,this,"尋找PointOfContact_ID：{}"+pointOfContactId);
        return ResponseEntity.ok(foundPointOfContact);
    }
    @GetMapping("findAll")
    public ResponseEntity<Page<PointOfContactDTO>>findAllPointOfContacts(
            @RequestParam (defaultValue = "0")int page,
            @RequestParam (defaultValue = "10")int size,
            @RequestParam (defaultValue = "id")String sortBy,
            @RequestParam (defaultValue = "asc")String sortDir
    ){
        LogUtils.logRequest(log,this,"尋找所有PointOfContact");
        Sort sort=sortDir.equalsIgnoreCase("asc")
                ?Sort.by(sortBy).ascending():Sort.by(sortBy).descending();
        Pageable pageable= PageRequest.of(page, size, sort);
        Page<PointOfContactDTO> foundPointOfContacts=pointOfContactService.findAllPointOfContacts(pageable);
        LogUtils.logResponse(log,this,"尋找所有PointOfContact");
        return ResponseEntity.ok(foundPointOfContacts);
    }
    @PostMapping("/update/{pointOfContactId}")
    public ResponseEntity<PointOfContactDTO>updatePointOfContact(@PathVariable Long pointOfContactId, @RequestBody PointOfContact inputtedPointOfContact){
        LogUtils.logRequest(log,this,"更新PointOfContact_ID：{}"+pointOfContactId);
        PointOfContactDTO updatedPointOfContact=pointOfContactService.updatePointOfContact(pointOfContactId, inputtedPointOfContact);
        LogUtils.logResponse(log,this,"更新PointOfContact_ID：{}"+pointOfContactId);
        return ResponseEntity.ok(updatedPointOfContact);
    }
}
