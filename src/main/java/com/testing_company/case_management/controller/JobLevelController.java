package com.testing_company.case_management.controller;

import com.testing_company.case_management.dto.requestDTO.JobLevelRequestDTO;
import com.testing_company.case_management.model.JobLevel;
import com.testing_company.case_management.service.JobLevelService;
import com.testing_company.case_management.util.LogUtils;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableArgumentResolver;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/jobLevel")
@AllArgsConstructor
public class JobLevelController {
    private final JobLevelService jobLevelService;


    @PostMapping("/create")
    public ResponseEntity<List<JobLevel>> createJobLevel(@Valid @RequestBody List<JobLevelRequestDTO> jobLevelRequestDTOs){
        LogUtils.logRequest(log,this,"建立JobLevel：{}",jobLevelRequestDTOs);
        List<JobLevel> createdResult=jobLevelService.createJobLevel(jobLevelRequestDTOs);
        LogUtils.logResponse(log,this, "建立JobLevel：{}",jobLevelRequestDTOs);
        return ResponseEntity.status(HttpStatus.CREATED).header("X-message","Successfully create JobLevel").body(createdResult);
    }
    @GetMapping("/findById/{jobLevelId}")
    public ResponseEntity<JobLevel> findJobLevelById(@PathVariable Long jobLevelId){
        LogUtils.logRequest(log,this,"尋找JobLevel_ID：{}",jobLevelId);
        JobLevel findResult=jobLevelService.findJobLevelById(jobLevelId);
        LogUtils.logResponse(log,this,"尋找JobLevel_ID：{}",jobLevelId);
        return ResponseEntity.status(HttpStatus.OK).body(findResult);
    }
    @GetMapping("/findAll")
    public ResponseEntity<Page<JobLevel>> findAllJobLevels(
            @RequestParam(defaultValue = "0")int page,
            @RequestParam(defaultValue = "10")int size,
            @RequestParam(defaultValue = "id")String sortBy,
            @RequestParam(defaultValue = "desc")String sortDir
    ){
        LogUtils.logRequest(log,this,"尋找所有JobLevel");
        Sort sort=sortDir.equalsIgnoreCase("desc")?
                Sort.by(sortBy).descending():Sort.by(sortBy).ascending();
        Pageable pageable= PageRequest.of(page, size, sort);
        Page<JobLevel>findResult=jobLevelService.findAllJobLevels(pageable);
        LogUtils.logResponse(log,this,"尋找所有JobLevel");
        return ResponseEntity.status(HttpStatus.OK).body(findResult);
    }
    @PostMapping("/update/{jobLevelId}")
    public ResponseEntity<?>updateJobLevelById(@PathVariable Long jobLevelId, @Valid@RequestBody JobLevelRequestDTO newjobLevel){
        LogUtils.logRequest(log,this,"更新JobLevel_ID：{}",jobLevelId);
        JobLevel updateResult=jobLevelService.updateJobLevelById(jobLevelId, newjobLevel);
        LogUtils.logResponse(log,this,"更新JobLevel_ID：{}",jobLevelId);
        return ResponseEntity.ok(updateResult);

    }

}
