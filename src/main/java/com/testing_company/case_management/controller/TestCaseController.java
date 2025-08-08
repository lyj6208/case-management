package com.testing_company.case_management.controller;

import com.testing_company.case_management.dto.FindTestCaseByCustomRequestDTO;
import com.testing_company.case_management.dto.TestCaseResponseDTO;
import com.testing_company.case_management.dto.TestCaseWithTestItemResponseDTO;
import com.testing_company.case_management.model.TestCase;
import com.testing_company.case_management.service.TestCaseService;
import com.testing_company.case_management.util.LogUtils;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("api/testCase")
@Slf4j
@AllArgsConstructor
public class TestCaseController {
    private TestCaseService testCaseService;
    @PreAuthorize("hasAnyRole('CASE_HANDLE_MEMBER')")
    @PostMapping("/create")
    public ResponseEntity<TestCase> createTestCase(@Valid @RequestBody TestCase testCase){
        LogUtils.logRequest(log,this,"建立TestCase：{}"+testCase);
        TestCase createdTestCase=testCaseService.createTestCase(testCase);
        LogUtils.logResponse(log,this,"建立TestCase：{}"+testCase);
        return ResponseEntity.ok(createdTestCase);
    }
    @PostMapping("/createTestCases")
    public ResponseEntity<List<TestCase>> createTestCases(@Valid @RequestBody List<TestCase> testCase){
        LogUtils.logRequest(log,this,"建立TestCase：{}"+testCase);
        List<TestCase> createdTestCase=testCaseService.createTestCases(testCase);
        LogUtils.logResponse(log,this,"建立TestCase：{}"+testCase);
        return ResponseEntity.ok(createdTestCase);
    }
    @GetMapping("/findById/{testCaseId}")
    public ResponseEntity<TestCaseResponseDTO>findTestCaseById(@PathVariable Long testCaseId){
        LogUtils.logRequest(log,this,"尋找TestCase_ID：{}"+testCaseId);
        TestCaseResponseDTO foundTestCase=testCaseService.findTestCaseById(testCaseId);
        LogUtils.logResponse(log,this,"尋找TestCase_ID：{}"+testCaseId);
        return ResponseEntity.ok(foundTestCase);
    }
    @GetMapping("/findRelatedToUser")
    public ResponseEntity<List<TestCaseWithTestItemResponseDTO>> findRelatedToUser(@RequestBody FindTestCaseByCustomRequestDTO testCaseByCustomRequestDTO){
        LogUtils.logRequest(log, this,"找尋特定條件TestCase：{}",testCaseByCustomRequestDTO);
        List<TestCaseWithTestItemResponseDTO> testCaseResponseDTOS=testCaseService.findRelatedToUser(testCaseByCustomRequestDTO);
        LogUtils.logResponse(log, this,"找尋特定條件TestCase：{}",testCaseResponseDTOS);
        return ResponseEntity.ok(testCaseResponseDTOS);
    }
    @GetMapping("/findAllWithTimeCustom")
    public ResponseEntity<Page<TestCaseResponseDTO>> findAllTestCasesWithTimeCustom(
            @RequestParam(defaultValue = "0")int page,
            @RequestParam(defaultValue = "10")int size,
            @RequestParam(defaultValue = "id")String sortBy,
            @RequestParam(defaultValue = "asc")String sortDir,
            @RequestParam(required = false)@DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate caseStartDate_Begin,
            @RequestParam(required = false)@DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate caseStartDate_End
    ){
        LogUtils.logRequest(log,this,"尋找特定條件之TestCase");
        Sort sort=sortDir.equalsIgnoreCase("asc")
                ?Sort.by(sortBy).ascending():Sort.by(sortBy).descending();
        Pageable pageable= PageRequest.of(page, size, sort);
        Page<TestCaseResponseDTO> foundTestCases=testCaseService.findTestCasesByCustom(pageable, caseStartDate_Begin, caseStartDate_End);
        LogUtils.logResponse(log,this,"尋找特定條件之TestCase");
        return ResponseEntity.ok(foundTestCases);
    }

    @PostMapping("/update/{testCaseId}")
    public ResponseEntity<TestCaseResponseDTO>updateTestCase(@PathVariable Long testCaseId, @RequestBody TestCase inputtedTestCase){
        LogUtils.logRequest(log,this,"更新TestCase_ID：{}"+testCaseId);
        TestCaseResponseDTO updatedTestCase=testCaseService.updateTestCase(testCaseId, inputtedTestCase);
        LogUtils.logResponse(log,this,"更新TestCase_ID：{}"+testCaseId);
        return ResponseEntity.ok(updatedTestCase);
    }
    @PostMapping("/start/{testCaseId}/{sampleOriginWeight}")
    public ResponseEntity<TestCaseResponseDTO>startTestCase(@PathVariable Long testCaseId, @PathVariable Double sampleOriginWeight){
        LogUtils.logRequest(log,this,"啟動TestCase_ID：{}"+testCaseId);
        TestCaseResponseDTO startedTestCase=testCaseService.startTestCase(testCaseId, sampleOriginWeight);
        LogUtils.logResponse(log,this,"啟動TestCase_ID：{}"+testCaseId);
        return ResponseEntity.ok(startedTestCase);
    }
    @PostMapping("/changeTestCaseOperator/{testCaseId}/{userId}")
    public ResponseEntity<TestCaseResponseDTO> changeTestCaseOperator(@PathVariable Long testCaseId, @PathVariable Long userId){
        LogUtils.logRequest(log,this,"變更實驗執行者TestCase_ID：{}"+testCaseId);
        TestCaseResponseDTO testCase=testCaseService.changeTestCaseOperator(testCaseId, userId);
        LogUtils.logResponse(log,this,"變更實驗執行者TestCase_ID：{}"+testCaseId);
        return ResponseEntity.ok(testCase);
    }
    @PostMapping("/collectSample/{testCaseId}")
    public ResponseEntity<TestCaseResponseDTO> collectSample(@PathVariable Long testCaseId){
        LogUtils.logRequest(log,this,"取出樣品TestCase_ID：{}"+testCaseId);
        TestCaseResponseDTO collectedTestCase=testCaseService.collectSample(testCaseId);
        LogUtils.logResponse(log,this,"取出樣品TestCase_ID：{}"+testCaseId);
        return ResponseEntity.ok(collectedTestCase);
    }
    @PostMapping("/returnSample/{testCaseId}/{sampleRemainingWeight}")
    public ResponseEntity<TestCaseResponseDTO> returnSample(@PathVariable Long testCaseId, @PathVariable Double sampleRemainingWeight){
        LogUtils.logRequest(log,this,"歸還樣品TestCase_ID：{}"+testCaseId);
        TestCaseResponseDTO returnedTestCase=testCaseService.returnSample(testCaseId, sampleRemainingWeight);
        LogUtils.logResponse(log,this,"歸還樣品TestCase_ID：{}"+testCaseId);
        return ResponseEntity.ok(returnedTestCase);
    }
    @PostMapping("/submitToReview/{testCaseId}/{testResult}")
    public ResponseEntity<TestCaseResponseDTO> submitTestCaseToReview(@PathVariable Long testCaseId, @PathVariable String testResult){
        LogUtils.logRequest(log,this,"提交TestCase_ID：{}至審核"+testCaseId);
        TestCaseResponseDTO submittedTestCase=testCaseService.submitTestCaseToReview(testCaseId, testResult);
        LogUtils.logResponse(log,this,"提交TestCase_ID：{}至審核"+testCaseId);
        return ResponseEntity.ok(submittedTestCase);
    }
    @PostMapping("/withdrawToExperiment/{testCaseId}")
    public ResponseEntity<TestCaseResponseDTO> withdrawTestCaseToExperiment(@PathVariable Long testCaseId){
        LogUtils.logRequest(log,this,"退回TestCase_ID：{}至實驗"+testCaseId);
        TestCaseResponseDTO submittedTestCase=testCaseService.withdrawTestCaseToExperiment(testCaseId);
        LogUtils.logResponse(log,this,"退回TestCase_ID：{}至實驗"+testCaseId);
        return ResponseEntity.ok(submittedTestCase);
    }
    @PostMapping("/submitToReport/{testCaseId}")
    public ResponseEntity<TestCaseResponseDTO> submitTestCaseToReport(@PathVariable Long testCaseId){
        LogUtils.logRequest(log,this,"提交TestCase_ID：{}至報告組"+testCaseId);
        TestCaseResponseDTO submittedTestCase=testCaseService.submitTestCaseToReport(testCaseId);
        LogUtils.logResponse(log,this,"提交TestCase_ID：{}至報告組"+testCaseId);
        return ResponseEntity.ok(submittedTestCase);
    }
    @PostMapping("/finishReport/{testCaseId}")
    public ResponseEntity<TestCaseResponseDTO> finishTestCaseReport(@PathVariable Long testCaseId){
        LogUtils.logRequest(log,this,"完成TestCase_ID：{}之報告"+testCaseId);
        TestCaseResponseDTO finishedTestCase=testCaseService.finishTestCaseReport(testCaseId);
        LogUtils.logResponse(log,this,"完成TestCase_ID：{}之報告"+testCaseId);
        return ResponseEntity.ok(finishedTestCase);
    }

}
