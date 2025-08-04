package com.testing_company.case_management.controller;

import com.testing_company.case_management.dto.CustomerResponseDTO;
import com.testing_company.case_management.dto.TestItemResponseDTO;
import com.testing_company.case_management.model.Customer;
import com.testing_company.case_management.model.TestItem;
import com.testing_company.case_management.service.TestItemService;
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
@RequestMapping("/api/testItem")
@Slf4j
@AllArgsConstructor
public class TestItemController {
    private final TestItemService testItemService;
    @PostMapping("/create")
    public ResponseEntity<TestItem> createTestItem(@Valid @RequestBody TestItem testItem){
        LogUtils.logRequest(log,this,"建立TestItem：{}"+testItem);
        TestItem createdTestItem=testItemService.createTestItem(testItem);
        LogUtils.logResponse(log,this,"建立TestItem：{}"+testItem);
        return ResponseEntity.ok(createdTestItem);
    }
    @GetMapping("/findById/{testItemId}")
    public ResponseEntity<TestItemResponseDTO>findTestItemById(@PathVariable Long testItemId){
        LogUtils.logRequest(log,this,"尋找TestItem_ID：{}"+testItemId);
        TestItemResponseDTO foundTestItem=testItemService.findTestItemById(testItemId);
        LogUtils.logResponse(log,this,"尋找TestItem_ID：{}"+testItemId);
        return ResponseEntity.ok(foundTestItem);
    }
    @GetMapping("/findAll")
    public ResponseEntity<Page<TestItemResponseDTO>>findAllTestItems(
            @RequestParam(defaultValue = "0")int page,
            @RequestParam(defaultValue = "10")int size,
            @RequestParam(defaultValue = "id")String sortBy,
            @RequestParam(defaultValue = "asc")String sortDir
    ){
        LogUtils.logRequest(log,this,"尋找所有TestItem");
        Sort sort=sortDir.equalsIgnoreCase("asc")
                ?Sort.by(sortBy).ascending():Sort.by(sortBy).descending();
        Pageable pageable= PageRequest.of(page, size, sort);
        Page<TestItemResponseDTO> foundTestItems=testItemService.findAllTestItems(pageable);
        LogUtils.logResponse(log,this,"尋找所有TestItem");
        return ResponseEntity.ok(foundTestItems);
    }
    @PostMapping("/update/{testItemId}")
    public ResponseEntity<TestItemResponseDTO>updateCustomer(@PathVariable Long testItemId, @RequestBody TestItem inputtedTestItem){
        LogUtils.logRequest(log,this,"更新TestItem_ID：{}",testItemId);
        TestItemResponseDTO updatedTestItem=testItemService.updateTestItem(testItemId, inputtedTestItem);
        LogUtils.logResponse(log,this,"更新TestItem_ID：{}",testItemId);
        return ResponseEntity.ok(updatedTestItem);
    }
}
