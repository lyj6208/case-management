package com.testing_company.case_management.service;

import com.testing_company.case_management.exception.NotFoundException;
import com.testing_company.case_management.model.Department;
import com.testing_company.case_management.model.IndustryCategory;
import com.testing_company.case_management.repository.IndustryCategoryRepository;
import com.testing_company.case_management.util.LogUtils;
import jakarta.persistence.criteria.CriteriaBuilder;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@AllArgsConstructor
public class IndustryCategoryService {
    private final IndustryCategoryRepository industryCategoryRepository;
    public IndustryCategory createIndustryCategory(IndustryCategory industryCategory){
        LogUtils.logRequest(log,this,"建立IndustryCategory：{}"+industryCategory);
        IndustryCategory createdIndustryCategory=industryCategoryRepository.save(industryCategory);
        LogUtils.logResponse(log,this,"建立IndustryCategory：{}"+industryCategory);
        return industryCategory;
    }
    public IndustryCategory findIndustryCategoryById(Long industryCategoryId){
        LogUtils.logRequest(log,this,"尋找IndustryCategory_ID：{}"+industryCategoryId);
        IndustryCategory foundIndustryCategory=industryCategoryRepository.findById(industryCategoryId).orElseThrow(()->new NotFoundException("找不到ID為"+industryCategoryId+"之industryCategory"));
        LogUtils.logResponse(log,this,"尋找IndustryCategory_ID：{}"+industryCategoryId);
        return foundIndustryCategory;
    }
    public Page<IndustryCategory>findAllIndustryCategories(Pageable pageable){
        LogUtils.logRequest(log,this,"尋找所有IndustryCategory");
        Page<IndustryCategory>foundIndustryCategories=industryCategoryRepository.findAll(pageable);
        LogUtils.logResponse(log,this,"尋找所有IndustryCategory");
        return foundIndustryCategories;
    }
    public IndustryCategory updateIndustryCategory(Long industryCategoryId, IndustryCategory industryCategory){
        LogUtils.logRequest(log,this,"更新IndustryCategory_ID：{}"+industryCategoryId);
        IndustryCategory updatedIndustryCategory=industryCategoryRepository.findById(industryCategoryId).orElseThrow(()->new NotFoundException("找不到ID為"+industryCategoryId+"之industryCategory"));
        updatedIndustryCategory.setIndustryCategory(industryCategory.getIndustryCategory());
        industryCategoryRepository.save(updatedIndustryCategory);
        LogUtils.logResponse(log,this,"更新IndustryCategory_ID：{}"+industryCategoryId);
        return updatedIndustryCategory;
    }
}
