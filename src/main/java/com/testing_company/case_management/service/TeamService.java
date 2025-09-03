package com.testing_company.case_management.service;

import com.testing_company.case_management.dto.responseDTO.TeamResponseDTO;
import com.testing_company.case_management.dto.requestDTO.TeamRequestDTO;
import com.testing_company.case_management.exception.NotFoundException;
import com.testing_company.case_management.model.Department;
import com.testing_company.case_management.model.Team;
import com.testing_company.case_management.repository.DepartmentRepository;
import com.testing_company.case_management.repository.TeamRepository;
import com.testing_company.case_management.util.LogUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.Conditions;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@AllArgsConstructor
public class TeamService {
    private final TeamRepository teamRepository;
    private final DepartmentRepository departmentRepository;
    public List<TeamResponseDTO> createTeam(List<TeamRequestDTO> teamRequestDTOS){
        LogUtils.logRequest(log,this,"建立Team：{}"+teamRequestDTOS);
        ModelMapper mapper=new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        List<Team> teamToSave=teamRequestDTOS.stream().map(t->mapper.map(t,Team.class)).toList();
        System.out.println(teamToSave.toString());
        List<Team> createdTeam=teamRepository.saveAll(teamToSave);
        LogUtils.logResponse(log,this,"建立Team：{}"+teamRequestDTOS);
        return createdTeam.stream().map(this::convertToResponseDTO).toList();
    }

    public TeamResponseDTO findTeamById(Long teamId){
        LogUtils.logRequest(log,this,"尋找Team_ID：{}"+teamId);
        Team foundTeam=teamRepository.findById(teamId).orElseThrow(()->new NotFoundException("找不到ID為"+teamId+"之Team"));
        LogUtils.logResponse(log,this,"尋找Team_ID：{}"+teamId);
        return convertToResponseDTO(foundTeam);
    }

    public Page<TeamResponseDTO> findAllTeams(Pageable pageable){
        LogUtils.logRequest(log,this,"尋找所有Team");
        Page<Team>foundTeam=teamRepository.findAll(pageable);
        LogUtils.logResponse(log,this,"尋找所有Team");
        return foundTeam.map(team -> {
            String departmentName = departmentRepository.findById(team.getDepartmentId())
                    .map(Department::getDepartmentNameInChinese)
                    .orElse("未知部門"); // 可自訂 fallback 字串
            return new TeamResponseDTO(team.getId(),departmentName, team.getTeamNameInChinese());
        });
    }
    public TeamResponseDTO updateTeamById(Long teamId, TeamRequestDTO newTeam){
        LogUtils.logRequest(log,this,"更新Team_ID：{}"+teamId);
        Team updatedTeam=teamRepository.findById(teamId)
                .orElseThrow(()-> new NotFoundException("找不到ID為"+teamId+"之JobLevel"));
        ModelMapper mapper=new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT).setPropertyCondition(Conditions.isNotNull());

        mapper.map(newTeam, updatedTeam);
        teamRepository.save(updatedTeam);
        LogUtils.logResponse(log,this,"更新Team_ID：{}"+teamId);
        return convertToResponseDTO(updatedTeam);
    }
    public TeamResponseDTO convertToResponseDTO(Team t){
        Department department=departmentRepository.findById(t.getDepartmentId()).orElseThrow(()->new NotFoundException("找不到department ID："+t.getDepartmentId()));
        return new TeamResponseDTO(t.getId(),department.getDepartmentNameInChinese(),t.getTeamNameInChinese());
    }
}


