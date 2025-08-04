package com.testing_company.case_management.controller;

import com.testing_company.case_management.dto.TeamResponseDTO;
import com.testing_company.case_management.model.JobLevel;
import com.testing_company.case_management.model.Team;
import com.testing_company.case_management.service.TeamService;
import com.testing_company.case_management.util.LogUtils;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/team")
@Slf4j
@AllArgsConstructor
public class TeamController {
    private final TeamService teamService;
    @PostMapping("/create")
    public ResponseEntity<Team> createTeam(@Valid @RequestBody Team team){
        LogUtils.logRequest(log,this,"建立Team：{}"+team);
        Team createdResult=teamService.createTeam(team);
        LogUtils.logResponse(log,this,"建立Team：{}"+team);
        return ResponseEntity.status(HttpStatus.CREATED).header("X-message","Successfully create Team").body(createdResult);
    }
    @GetMapping("/findById/{teamId}")
    public ResponseEntity<TeamResponseDTO> findTeamById(@PathVariable Long teamId){
        LogUtils.logRequest(log,this,"尋找Team_ID：{}"+teamId);
        TeamResponseDTO teamResponseDTO=teamService.findTeamById(teamId);
        LogUtils.logResponse(log,this,"尋找Team_ID：{}"+teamId);
        return ResponseEntity.status(HttpStatus.OK).body(teamResponseDTO);
    }
    @GetMapping("/findAll")
    public ResponseEntity<Page<TeamResponseDTO>> findAllTeams(
            @RequestParam(defaultValue = "0")int page,
            @RequestParam(defaultValue = "10")int size,
            @RequestParam(defaultValue = "id")String sortBy,
            @RequestParam(defaultValue = "desc")String sortDir
    ){
        LogUtils.logRequest(log,this,"尋找所有Team");
        Sort sort=sortDir.equalsIgnoreCase("desc")?
                Sort.by(sortBy).descending():Sort.by(sortBy).ascending();
        Pageable pageable= PageRequest.of(page, size, sort);
        Page<TeamResponseDTO>findResult=teamService.findAllTeams(pageable);
        LogUtils.logResponse(log,this,"尋找所有Team");
        return ResponseEntity.status(HttpStatus.OK).body(findResult);
    }
    @PostMapping("/update/{teamId}")
    public ResponseEntity<?>updateTeamById(@PathVariable Long teamId, @Valid@RequestBody Team inputtedTeam){
        LogUtils.logRequest(log,this,"更新Team_ID：{}"+teamId);
        Team updatedTeam=teamService.updateTeamById(teamId, inputtedTeam);
        LogUtils.logResponse(log,this,"更新Team_ID：{}"+teamId);
        return ResponseEntity.ok(updatedTeam);

    }
}
