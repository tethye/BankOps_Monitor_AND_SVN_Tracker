package com.example.myproject.controller;



import com.example.myproject.Entity.SvnRepository;
import com.example.myproject.Repository.SVNRepo;
import com.example.myproject.Service.SVNService;
import com.example.myproject.model.CommitEntry;
import com.example.myproject.model.Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.tmatesoft.svn.core.SVNException;

import java.util.List;

@Controller
@RequestMapping("/")
public class DashboardController {

    @Autowired
    private SVNService svnService;
    @Autowired
    private SVNRepo svnRepo;

    @GetMapping("/")
    public String dashboard(Model model,
                            @RequestParam(defaultValue = "0") int page,
                            @RequestParam(defaultValue = "10") int size) {
        List<CommitEntry> allCommits = svnService.getAllCommits();
        Page<CommitEntry> commitPage = getPaginatedCommits(allCommits, page, size);

        model.addAttribute("repositories", svnService.getRepositories());
        model.addAttribute("commits", commitPage);
        model.addAttribute("chartData", svnService.prepareChartData(allCommits));
        return "dashboard";
    }

//    @PostMapping("/add-repo")
//    public String addRepository(@ModelAttribute Repository repo) throws SVNException {
//        svnService.addRepository(repo);
//        return "redirect:/";
//    }

    @GetMapping("/modal")
    public String getModalForm() {
        return "AddRepositoryform :: repoModalContent";
    }

    @PostMapping("/addrepo")
    @ResponseBody
    public ResponseEntity<?> saveRepository(@RequestBody SvnRepository repo) {
        svnRepo.save(repo);
        return ResponseEntity.ok().build();

    }


    private Page<CommitEntry> getPaginatedCommits(List<CommitEntry> allCommits, int page, int size) {
        int total = allCommits.size();
        int start = Math.min(page * size, total);
        int end = Math.min((page + 1) * size, total);

        return new PageImpl<>(
                allCommits.subList(start, end),
                PageRequest.of(page, size),
                total
        );
    }


}

