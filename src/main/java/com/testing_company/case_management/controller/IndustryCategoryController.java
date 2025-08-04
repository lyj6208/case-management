package com.testing_company.case_management.controller;

import com.testing_company.case_management.model.IndustryCategory;
import com.testing_company.case_management.service.IndustryCategoryService;
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
@RequestMapping("/api/industryCategory")
@AllArgsConstructor
@Slf4j
public class IndustryCategoryController {
    private final IndustryCategoryService industryCategoryService;
    @PostMapping("/create")
    public ResponseEntity<IndustryCategory>createIndustryCategory(@Valid @RequestBody IndustryCategory industryCategory){
        LogUtils.logRequest(log,this,"建立IndustryCategory：{}"+industryCategory);
        IndustryCategory createdIndsutryCategory=industryCategoryService.createIndustryCategory(industryCategory);
        LogUtils.logResponse(log,this,"建立IndustryCategory：{}"+industryCategory);
        return ResponseEntity.ok(createdIndsutryCategory);
    }

    @GetMapping("/findById/{industryCategoryId}")
    public ResponseEntity<IndustryCategory>findIndustryCategoryById(@PathVariable Long industryCategoryId){
        LogUtils.logRequest(log,this,"尋找IndustryCategory_ID：{}"+industryCategoryId);
        IndustryCategory foundIndustryCategory=industryCategoryService.findIndustryCategoryById(industryCategoryId);
        LogUtils.logResponse(log,this,"尋找IndustryCategory_ID：{}"+industryCategoryId);
        return ResponseEntity.ok(foundIndustryCategory);
    }

    @GetMapping("/findAll")
    public ResponseEntity<Page<IndustryCategory>>findAllIndustryCategories(
            @RequestParam(defaultValue = "5")int page,
            @RequestParam(defaultValue = "10")int size,
            @RequestParam(defaultValue = "id")String  sortBy,
            @RequestParam(defaultValue = "asc")String sortDir
    ){
        LogUtils.logRequest(log,this,"尋找所有IndustryCategory");
        Sort sort=sortDir.equalsIgnoreCase("acs")
                ?Sort.by(sortBy).ascending():Sort.by(sortBy).descending();
        Pageable pageable= PageRequest.of(page, size, sort);
        Page<IndustryCategory>foundIndustryCategories=industryCategoryService.findAllIndustryCategories(pageable);
        LogUtils.logResponse(log,this,"尋找所有IndustryCategory");
        return ResponseEntity.ok(foundIndustryCategories);
    }
    @PostMapping("/update/{industryCategoryId}")
    public ResponseEntity<IndustryCategory> updateIndustryCategory(@PathVariable Long industryCategoryId,@RequestBody IndustryCategory inputtedIndustryCategory){
        LogUtils.logRequest(log,this,"更新IndustryCategory_ID：{}"+industryCategoryId);
        IndustryCategory updatedIndustryCategory=industryCategoryService.updateIndustryCategory(industryCategoryId,inputtedIndustryCategory);
        LogUtils.logResponse(log,this,"更新IndustryCategory_ID：{}"+industryCategoryId);
        return ResponseEntity.ok(updatedIndustryCategory);
    }

}
