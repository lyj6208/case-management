package com.testing_company.case_management.service;

import com.testing_company.case_management.dto.TestItemResponseDTO;
import com.testing_company.case_management.exception.NotFoundException;
import com.testing_company.case_management.model.*;
import com.testing_company.case_management.repository.DepartmentRepository;
import com.testing_company.case_management.repository.TeamRepository;
import com.testing_company.case_management.repository.TestItemRepository;
import com.testing_company.case_management.repository.UserRepository;
import com.testing_company.case_management.util.BeanUtil;
import com.testing_company.case_management.util.LogUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@AllArgsConstructor
public class TestItemService {
    private final TestItemRepository testItemRepository;
    private final TeamRepository teamRepository;
    private final DepartmentRepository departmentRepository;
    private final UserRepository userRepository;

    public List<TestItem> createTestItem(List<TestItem> testItem){
        LogUtils.logRequest(log,this,"建立TestItem：{}"+testItem);
        Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
        String currentUsername=authentication.getName();
        User currentUser=userRepository.findByUsername(currentUsername).orElseThrow(()->new NotFoundException("找不到使用者"));
        for(TestItem t:testItem){
            t.setLastModifiedById(currentUser.getId());
        }
        List<TestItem> createdTestItem=testItemRepository.saveAll(testItem);
        LogUtils.logResponse(log,this,"建立TestItem：{}"+testItem);
        return createdTestItem;
    }
    public TestItemResponseDTO findTestItemById(Long testItemId){
        LogUtils.logRequest(log,this,"尋找TestItem_ID：{}"+testItemId);
        TestItem foundTestItem=testItemRepository.findById(testItemId).orElseThrow(()->new NotFoundException("找不到ID為"+testItemId+"之TestItem"));
        LogUtils.logResponse(log,this,"尋找TestItem_ID：{}"+testItemId);
        return convertToDTO(foundTestItem);
    }
    public Page<TestItemResponseDTO> findAllTestItems(Pageable pageable){
        LogUtils.logRequest(log,this,"尋找所有TestItem");
        Page<TestItem> foundTestItems=testItemRepository.findAll(pageable);
        LogUtils.logResponse(log,this,"尋找所有TestItem");
        return foundTestItems.map(this::convertToDTO);
    }
    public TestItemResponseDTO updateTestItem(Long testItemId, TestItem inputtedTestItem){
        LogUtils.logRequest(log,this,"更新TestItem_ID：{}",testItemId);
        TestItem updatedTestItem=testItemRepository.findById(testItemId).orElseThrow(()->new NotFoundException("找不到ID為"+testItemId+"的Customer"));
        BeanUtil.copyNotNullProperties(inputtedTestItem,updatedTestItem);
        testItemRepository.save(updatedTestItem);
        LogUtils.logResponse(log,this,"更新TestItem_ID：{}",testItemId);
        return convertToDTO(updatedTestItem);
    }
    public TestItemResponseDTO convertToDTO(TestItem testItem){
        Team team= BeanUtil.findIfIdPresent(testItem.getTeamId(),teamRepository::findById);
        Department department = BeanUtil.findIfIdPresent(team.getDepartmentId(),departmentRepository::findById);
        User modifier=BeanUtil.findIfIdPresent(testItem.getLastModifiedById(),userRepository::findById);
        User experimentOperator=BeanUtil.findIfIdPresent(testItem.getExperimentOperatorId(),userRepository::findById);
        User experimentReviewer=BeanUtil.findIfIdPresent(testItem.getExperimentReviewerId(),userRepository::findById);
        User reportConductor=BeanUtil.findIfIdPresent(testItem.getReportConductorId(),userRepository::findById);
        return TestItemResponseDTO.builder()
                .id(testItem.getId())
                .name(testItem.getName())
                .department(department!=null?department.getDepartment():null)
                .team(team!=null?team.getTeam():null)
                .testingDays(testItem.getTestingDays())
                .testingPrice(testItem.getTestingPrice())
                .createdTime(testItem.getCreatedTime())
                .experimentOperator(experimentOperator!=null? experimentOperator.getEmployeeNumber()+"_"+experimentOperator.getName() :null)
                .experimentReviewer(experimentReviewer!=null? experimentReviewer.getEmployeeNumber()+"_"+experimentReviewer.getName() :null)
                .reportConductor(reportConductor!=null? reportConductor.getEmployeeNumber()+"_"+reportConductor.getName() :null)
                .lastModifiedTime(testItem.getLastModifiedTime())
                .lastModifiedBy(modifier!=null? modifier.getEmployeeNumber()+"_"+modifier.getName() : null)
                .build();
    }
}
