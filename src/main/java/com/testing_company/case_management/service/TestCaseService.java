package com.testing_company.case_management.service;

import com.testing_company.case_management.dto.FindTestCaseByCustomRequestDTO;
import com.testing_company.case_management.dto.TestCaseResponseDTO;
import com.testing_company.case_management.dto.TestCaseWithTestItemDTO;
import com.testing_company.case_management.dto.TestCaseWithTestItemResponseDTO;
import com.testing_company.case_management.enums.CaseStatus;
import com.testing_company.case_management.enums.SampleStatus;
import com.testing_company.case_management.exception.NotFoundException;
import com.testing_company.case_management.model.*;
import com.testing_company.case_management.repository.*;
import com.testing_company.case_management.util.BeanUtil;
import com.testing_company.case_management.util.LogUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@AllArgsConstructor
public class TestCaseService {
    private final TestCaseRepository testCaseRepository;
    private final TestItemRepository testItemRepository;
    private final CustomerRepository customerRepository;
    private final PointOfContactRepository pointOfContactRepository;
    private final UserRepository userRepository;
    private final TeamRepository teamRepository;
    private final DepartmentRepository departmentRepository;

    public TestCase createTestCase(TestCase testCase){
        LogUtils.logRequest(log,this,"建立TestCase：{}"+testCase);
        //處理testCaseNumber
        Long nextNumber= Optional.ofNullable(testCaseRepository.findMaxCaseNumber()).orElse(0L)+1;
        String formattedNumber=String.format("%07d",nextNumber);

        TestItem testItem= BeanUtil.findIfIdPresent(testCase.getTestItemId(),testItemRepository::findById);
        Team team=BeanUtil.findIfIdPresent(testItem.getTeamId(),teamRepository::findById);
        Department department=BeanUtil.findIfIdPresent(team.getDepartmentId(),departmentRepository::findById);
        testCase.setTestCaseNumber(department.getAbbreviation()+formattedNumber);

        //處理testingdays&testingprice
        if(testCase.getTestingDays()==null){testCase.setTestingDays(testItem.getTestingDays());};
        if(testCase.getTestingPrice()==null){testCase.setTestingPrice(testItem.getTestingPrice());};

        //處理各關卡負責人
        testCase.setExperimentOperatorId(testItem.getExperimentOperatorId());
        testCase.setExperimentReviewerId(testItem.getExperimentReviewerId());
        testCase.setReportConductorId(testItem.getReportConductorId());

        testCase.setCaseStatus(CaseStatus.CASE_CREATED);
        testCase.setSampleStatus(SampleStatus.AWAITING_DELIVERY);

        //TODO: caseHandlerID待Security建立後處理
        TestCase createdTestCase=testCaseRepository.save(testCase);
        LogUtils.logResponse(log,this,"建立TestCase：{}"+testCase);
        return createdTestCase;
    }
    public List<TestCase> createTestCases(List<TestCase> testCase){
        LogUtils.logRequest(log,this,"建立TestCase：{}"+testCase);
        List<TestCase>testCasesToSave=new ArrayList<>();
        for(TestCase t:testCase){
        //處理testCaseNumber
        Long nextNumber= Optional.ofNullable(testCaseRepository.findMaxCaseNumber()).orElse(0L)+1;
        String formattedNumber=String.format("%07d",nextNumber);

        TestItem testItem= BeanUtil.findIfIdPresent(t.getTestItemId(),testItemRepository::findById);
        Team team=BeanUtil.findIfIdPresent(testItem.getTeamId(),teamRepository::findById);
        Department department=BeanUtil.findIfIdPresent(team.getDepartmentId(),departmentRepository::findById);
        t.setTestCaseNumber(department.getAbbreviation()+formattedNumber);

        //處理testingdays&testingprice
        if(t.getTestingDays()==null){t.setTestingDays(testItem.getTestingDays());};
        if(t.getTestingPrice()==null){t.setTestingPrice(testItem.getTestingPrice());};

        //處理各關卡負責人
        t.setExperimentOperatorId(testItem.getExperimentOperatorId());
        t.setExperimentReviewerId(testItem.getExperimentReviewerId());
        t.setReportConductorId(testItem.getReportConductorId());

        t.setCaseStatus(CaseStatus.CASE_CREATED);
        t.setSampleStatus(SampleStatus.AWAITING_DELIVERY);

        //TODO: caseHandlerID、lastModifiedByID待Security建立後處理
        TestCase createdTestCase=testCaseRepository.save(t);
        testCasesToSave.add(createdTestCase);}
        LogUtils.logResponse(log,this,"建立TestCase：{}"+testCase);
        return testCasesToSave;
    }
    public TestCaseResponseDTO findTestCaseById(Long testCaseId){
        LogUtils.logRequest(log,this,"尋找TestCase_ID：{}"+testCaseId);
        TestCase foundTestCase=testCaseRepository.findById(testCaseId).orElseThrow(()->new NotFoundException("找不到ID為"+testCaseId+"之TestCase"));
        LogUtils.logResponse(log,this,"尋找TestCase_ID：{}"+testCaseId);
        return convertToDTO(foundTestCase);
    }
    public List<TestCaseWithTestItemResponseDTO> findRelatedToUser(FindTestCaseByCustomRequestDTO findTestCaseByCustomRequestDTO){
        LogUtils.logRequest(log, this,"找尋特定條件TestCase：{}",findTestCaseByCustomRequestDTO);
        Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
        String currentUsername=authentication.getName();
        User user=userRepository.findByUsername(currentUsername).orElseThrow(null);
        System.out.println(user);
        FindTestCaseByCustomRequestDTO finalConditions=new FindTestCaseByCustomRequestDTO();
        BeanUtil.copyNotNullProperties(findTestCaseByCustomRequestDTO, finalConditions);
        if(finalConditions.getCaseStatus().isEmpty()){finalConditions.setCaseStatus(null);}

        if(user.getRole().name().equals("LAB_MEMBER")){finalConditions.setExperiment_operator_id(user.getId());}else{finalConditions.setExperiment_operator_id(null);}
        if(user.getRole().name().equals("LAB_LEADER")){finalConditions.setExperiment_reviewer_id(user.getId());}else{finalConditions.setExperiment_reviewer_id(null);}
        if(user.getRole().name().equals("REPORT_MEMBER")){finalConditions.setReport_conductor_id(user.getId());}else{finalConditions.setReport_conductor_id(null);}

        List<TestCaseWithTestItemDTO> testCases=testCaseRepository.findRelatedToUser(
                finalConditions.getCaseStartDateTime(),
                finalConditions.getCaseEndDateTime(),
                finalConditions.getCaseStatus(),
                finalConditions.getExperiment_operator_id(),
                finalConditions.getExperiment_reviewer_id(),
                finalConditions.getReport_conductor_id());
        LogUtils.logResponse(log, this,"找尋特定條件TestCase：{}",findTestCaseByCustomRequestDTO);
        return testCases.stream().map(this::convertToDTO2).toList();
    }
    public Page<TestCaseResponseDTO> findTestCasesByCustom(Pageable pageable, LocalDate caseStartDate_Begin, LocalDate caseStartDate_End){
        LogUtils.logRequest(log,this,"尋找特定條件之TestCase");
        LocalDate today=LocalDate.now();
        Timestamp begin=caseStartDate_Begin!=null
                ?Timestamp.valueOf(caseStartDate_Begin.atStartOfDay()):Timestamp.valueOf(today.atStartOfDay());
        Timestamp end=caseStartDate_End!=null
                ?Timestamp.valueOf(caseStartDate_End.plusDays(1).atStartOfDay()):Timestamp.valueOf(today.plusDays(1).atStartOfDay());



        Page<TestCase>foundTestCases=testCaseRepository.findByCaseStartTimeBetween(begin, end, pageable);
        LogUtils.logResponse(log,this,"尋找特定條件之TestCase");
        return foundTestCases.map(this::convertToDTO);
    }
    public TestCaseResponseDTO updateTestCase(Long testCaseId, TestCase inputtedTestCase){
        LogUtils.logRequest(log,this,"更新TestCase_ID：{}"+testCaseId);
        TestCase updatedTestCase=testCaseRepository.findById(testCaseId).orElseThrow(()->new NotFoundException("找不到ID為"+testCaseId+"之TestCase"));
        BeanUtil.copyNotNullProperties(inputtedTestCase, updatedTestCase);
        testCaseRepository.save(updatedTestCase);
        LogUtils.logResponse(log,this,"更新TestCase_ID：{}"+testCaseId);
        return convertToDTO(updatedTestCase);
    }
    public TestCaseResponseDTO startTestCase(Long testCaseId, Double sampleOriginWeight){
        LogUtils.logRequest(log,this,"啟動TestCase_ID：{}"+testCaseId);
        TestCase testCase=testCaseRepository.findById(testCaseId).orElseThrow(()->new NotFoundException("找不到ID為"+testCaseId+"之TestCase"));
        TestItem testItem=testItemRepository.findById(testCase.getTestItemId()).orElseThrow(null);
        User experimentOperator=userRepository.findById(testItem.getExperimentOperatorId()).orElseThrow(null);
        testCase.setCaseStartTime(Timestamp.valueOf(LocalDateTime.now()));
        testCase.setLabDeadline(Timestamp.valueOf(LocalDateTime.now().plusDays(testCase.getTestingDays()-2)));
        testCase.setReportDeadline(Timestamp.valueOf(LocalDateTime.now().plusDays(testCase.getTestingDays())));
        testCase.setCaseStatus(CaseStatus.IN_PROGRESS);
        testCase.setSampleStatus(SampleStatus.PENDING_TESTING_AREA);
        testCase.setSampleOriginalWeight(sampleOriginWeight);
        testCase.setExperimentOperatorId(experimentOperator.getId());
        testCaseRepository.save(testCase);
        LogUtils.logResponse(log,this,"啟動TestCase_ID：{}"+testCaseId);
        return convertToDTO(testCase);

    }
    public TestCaseResponseDTO changeTestCaseOperator(Long testCaseId, Long userId){
        LogUtils.logRequest(log,this,"變更實驗執行者TestCase_ID：{}"+testCaseId);
        TestCase changeTestCaseOperator=testCaseRepository.findById(testCaseId).orElseThrow(()->new NotFoundException("找不到ID為"+testCaseId+"之TestCase"));
        User assingedUser=userRepository.findById(userId).orElseThrow(()->new NotFoundException("找不到ID為"+userId+"之User"));
        changeTestCaseOperator.setExperimentOperatorId(assingedUser.getId());
        testCaseRepository.save(changeTestCaseOperator);
        LogUtils.logResponse(log,this,"變更實驗執行者TestCase_ID：{}"+testCaseId);
        return convertToDTO(changeTestCaseOperator);
    }
    public TestCaseResponseDTO collectSample(Long testCaseId){
        LogUtils.logRequest(log,this,"取出樣品TestCase_ID：{}"+testCaseId);
        TestCase collectedTestCase=testCaseRepository.findById(testCaseId).orElseThrow(()->new NotFoundException("找不到ID為"+testCaseId+"之TestCase"));
        collectedTestCase.setSampleStatus(SampleStatus.LAB_COLLECTED);
        testCaseRepository.save(collectedTestCase);
        LogUtils.logResponse(log,this,"取出樣品TestCase_ID：{}"+testCaseId);
        return convertToDTO(collectedTestCase);
    }
    public TestCaseResponseDTO returnSample(Long testCaseId, Double sampleRemainingWeight){
        LogUtils.logRequest(log,this,"歸還樣品TestCase_ID：{}"+testCaseId);
        TestCase returnedTestCase=testCaseRepository.findById(testCaseId).orElseThrow(()->new NotFoundException("找不到ID為"+testCaseId+"之TestCase"));
        returnedTestCase.setSampleStatus(SampleStatus.STORAGE_AREA);
        returnedTestCase.setSampleRemainingWeight(sampleRemainingWeight);
        testCaseRepository.save(returnedTestCase);
        LogUtils.logResponse(log,this,"歸還樣品TestCase_ID：{}"+testCaseId);
        return convertToDTO(returnedTestCase);
    }
    public TestCaseResponseDTO submitTestCaseToReview(Long testCaseId, String testResult){
        LogUtils.logRequest(log,this,"提交TestCase_ID：{}至審核"+testCaseId);
        TestCase submittedTestCase=testCaseRepository.findById(testCaseId).orElseThrow(()->new NotFoundException("找不到ID為"+testCaseId+"之TestCase"));
        submittedTestCase.setCaseStatus(CaseStatus.UNDER_REVIEW);
        submittedTestCase.setTestResult(testResult);
        submittedTestCase.setExperimentEndTime(Timestamp.valueOf(LocalDateTime.now()));
        testCaseRepository.save(submittedTestCase);
        LogUtils.logResponse(log,this,"提交TestCase_ID：{}至審核"+testCaseId);
        return convertToDTO(submittedTestCase);
    }
    public TestCaseResponseDTO withdrawTestCaseToExperiment(Long testCaseId){
        LogUtils.logRequest(log,this,"退回TestCase_ID：{}至實驗"+testCaseId);
        TestCase withdrawnTestCase=testCaseRepository.findById(testCaseId).orElseThrow(()->new NotFoundException("找不到ID為"+testCaseId+"之TestCase"));
        withdrawnTestCase.setCaseStatus(CaseStatus.IN_PROGRESS);
        withdrawnTestCase.setTestResult(null);
        withdrawnTestCase.setExperimentEndTime(null);
        testCaseRepository.save(withdrawnTestCase);
        LogUtils.logResponse(log,this,"退回TestCase_ID：{}至實驗"+testCaseId);
        return convertToDTO(withdrawnTestCase);
    }
    public TestCaseResponseDTO submitTestCaseToReport(Long testCaseId){
        LogUtils.logRequest(log,this,"提交TestCase_ID：{}至報告組"+testCaseId);
        TestCase submittedTestCase=testCaseRepository.findById(testCaseId).orElseThrow(()->new NotFoundException("找不到ID為"+testCaseId+"之TestCase"));
        submittedTestCase.setCaseStatus(CaseStatus.REPORT_IN_PREPARATION);
        submittedTestCase.setExperimentReviewTime(Timestamp.valueOf(LocalDateTime.now()));
        testCaseRepository.save(submittedTestCase);
        LogUtils.logResponse(log,this,"提交TestCase_ID：{}至報告組"+testCaseId);
        return convertToDTO(submittedTestCase);
    }
    public TestCaseResponseDTO finishTestCaseReport(Long testCaseId){
        LogUtils.logRequest(log,this,"完成TestCase_ID：{}之報告"+testCaseId);
        TestCase finishedTestCase=testCaseRepository.findById(testCaseId).orElseThrow(()->new NotFoundException("找不到ID為"+testCaseId+"之TestCase"));
        finishedTestCase.setReportCloseTime(Timestamp.valueOf(LocalDateTime.now()));
        finishedTestCase.setCaseStatus(CaseStatus.CLOSED);
        testCaseRepository.save(finishedTestCase);
        LogUtils.logResponse(log,this,"完成TestCase_ID：{}之報告"+testCaseId);
        return convertToDTO(finishedTestCase);
    }
    public TestCaseResponseDTO convertToDTO(TestCase testCase){
        Customer customer= BeanUtil.findIfIdPresent(testCase.getCustomerId(),customerRepository::findById);
        PointOfContact pointOfContact=BeanUtil.findIfIdPresent(testCase.getPointOfContactId(),pointOfContactRepository::findById);
        TestItem testItem=BeanUtil.findIfIdPresent(testCase.getTestItemId(),testItemRepository::findById);
        User experimentOperator=BeanUtil.findIfIdPresent(testCase.getExperimentOperatorId(),userRepository::findById);
        User experimentReviewer=BeanUtil.findIfIdPresent(testCase.getExperimentReviewerId(),userRepository::findById);
        User reportConductor=BeanUtil.findIfIdPresent(testCase.getReportConductorId(),userRepository::findById);
        User caseHandler=BeanUtil.findIfIdPresent(testCase.getCaseHandlerId(),userRepository::findById);
        User modifier=BeanUtil.findIfIdPresent(testCase.getLastModifiedById(),userRepository::findById);
        Team team=BeanUtil.findIfIdPresent(testItem.getTeamId(), teamRepository::findById);
        Department department=BeanUtil.findIfIdPresent(team.getDepartmentId(), departmentRepository::findById);

        return TestCaseResponseDTO.builder()
                .id(testCase.getId())
                .testCaseNumber(testCase.getTestCaseNumber())
                .customer(customer!=null?customer.getName():null)
                .pointOfContact_person(pointOfContact!=null?pointOfContact.getContactPerson():null)
                .pointOfContact_email(pointOfContact!=null?pointOfContact.getContactEmail():null)
                .pointOfContact_phone(pointOfContact!=null?pointOfContact.getContactPhone():null)
                .pointOfContact_address(pointOfContact!=null?pointOfContact.getContactAddress():null)
                .sampleName(testCase.getSampleName())
                .testItem_name(testItem!=null?testItem.getName():null)
                .testingDays(testCase.getTestingDays())
                .testingPrice(testCase.getTestingPrice())
                .department(department!=null?department.getDepartment():null)
                .team(team!=null?team.getTeam():null)
                .caseStartTime(testCase.getCaseStartTime())
                .experimentEndTime(testCase.getExperimentEndTime())
                .experimentReviewTime(testCase.getExperimentReviewTime())
                .reportCloseTime(testCase.getReportCloseTime())
                .labDeadline(testCase.getLabDeadline())
                .reportDeadline(testCase.getReportDeadline())
                .experimentOperator(experimentOperator!=null? experimentOperator.getEmployeeNumber()+"_"+experimentOperator.getName() :null)
                .experimentReviewer(experimentReviewer!=null?experimentReviewer.getEmployeeNumber()+"_"+experimentReviewer.getName():null)
                .reportConductor(reportConductor!=null?reportConductor.getEmployeeNumber()+"_"+reportConductor.getName():null)
                .testResult(testCase.getTestResult())
                .caseStatus(testCase.getCaseStatus().getDisplayName())
                .sampleStatus(testCase.getSampleStatus().getDisplayName())
                .sampleOriginalWeight(testCase.getSampleOriginalWeight())
                .sampleRemainingWeight(testCase.getSampleRemainingWeight())
                .caseHandler(caseHandler!=null?caseHandler.getEmployeeNumber()+"_"+caseHandler.getName():null)
                .note(testCase.getNote())
                .createdTime(testCase.getCreatedTime())
                .lastModifiedTime(testCase.getLastModifiedTime())
                .lastModifiedBy(modifier!=null? modifier.getEmployeeNumber()+"_"+modifier.getName():null)
                .build();
    }
    public TestCaseWithTestItemResponseDTO convertToDTO2(TestCaseWithTestItemDTO t){
        TestItem testItem=BeanUtil.findIfIdPresent(t.getTiId(),testItemRepository::findById);
        User experimentOperator=BeanUtil.findIfIdPresent(t.getExperimentOperatorId(),userRepository::findById);
        User experimentReviewer=BeanUtil.findIfIdPresent(t.getExperimentReviewerId(),userRepository::findById);
        User reportConductor=BeanUtil.findIfIdPresent(t.getReportConductorId(),userRepository::findById);

        Team team=BeanUtil.findIfIdPresent(testItem.getTeamId(), teamRepository::findById);
        Department department=BeanUtil.findIfIdPresent(team.getDepartmentId(), departmentRepository::findById);

        return TestCaseWithTestItemResponseDTO.builder()
                .testCaseNumber(t.getTestCaseNumber())
                .sampleName(t.getSampleName())
                .testItem(testItem.getName())
                .caseStartTime(t.getCaseStartTime())
                .labDeadline(t.getLabDeadline())
                .reportDeadline(t.getReportDeadline())
                .experimentOperator(experimentOperator!=null? experimentOperator.getEmployeeNumber()+experimentOperator.getName() : null)
                .experimentReviewer(experimentReviewer!=null? experimentReviewer.getEmployeeNumber()+experimentReviewer.getName() : null)
                .reportConductor(reportConductor!=null? reportConductor.getEmployeeNumber()+reportConductor.getName() : null)
                .caseStatus(t.getCaseStatus())
                .team(department.getDepartment()+team.getTeam())
                .build();
    }
}
