package com.testing_company.case_management.service;

import com.testing_company.case_management.exception.NotFoundException;
import com.testing_company.case_management.model.JobLevel;
import com.testing_company.case_management.repository.JobLevelRepository;
import com.testing_company.case_management.util.LogUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.batch.BatchProperties;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@AllArgsConstructor
public class JobLevelService {
    private final JobLevelRepository jobLevelRepository;

    public JobLevel createJobLevel(JobLevel jobLevel){
        LogUtils.logRequest(log,this,"建立JobLevel：{}",jobLevel);
        JobLevel createdJobLevel=jobLevelRepository.save(jobLevel);
        LogUtils.logResponse(log,this, "建立JobLevel：{}",jobLevel);
        return createdJobLevel;
    }
    public JobLevel findJobLevelById(Long jobLevelId){
        LogUtils.logRequest(log,this,"尋找JobLevel_ID：{}",jobLevelId);
        Optional<JobLevel>optionalOfFindResult=jobLevelRepository.findById(jobLevelId);
        JobLevel findResult=optionalOfFindResult.orElseThrow(()->new NotFoundException("找不到JobLevel_ID："+jobLevelId));
        LogUtils.logResponse(log,this,"尋找JobLevel_ID：{}",jobLevelId);
        return findResult;
    }
    public Page<JobLevel>findAllJobLevels(Pageable pageable){
        LogUtils.logRequest(log,this,"尋找所有JobLevel");
        Page<JobLevel>findResult=jobLevelRepository.findAll(pageable);
        LogUtils.logResponse(log,this,"尋找所有JobLevel");
        return findResult;
    }
    public JobLevel updateJobLevelById(Long jobLevelId, JobLevel newjobLevel){
        LogUtils.logRequest(log,this,"更新JobLevel_ID：{}",jobLevelId);
        JobLevel updatedJobLevel=jobLevelRepository.findById(jobLevelId)
                .orElseThrow(()-> new NotFoundException("找不到JobLevel_ID："+jobLevelId));
        updatedJobLevel.setJobLevel(newjobLevel.getJobLevel());
        jobLevelRepository.save(updatedJobLevel);
        LogUtils.logResponse(log,this,"更新JobLevel_ID：{}",jobLevelId);
        return updatedJobLevel;
    }
}
