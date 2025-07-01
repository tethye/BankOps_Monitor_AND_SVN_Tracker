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
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.tmatesoft.svn.core.SVNException;

import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;

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
                            @RequestParam(defaultValue = "10") int size,
                            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate from,
                            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate to,
                            @RequestParam(required = false) String authorname) {
        if (from == null) {
            from = LocalDate.now().withDayOfYear(1); // First day of current year
        }
        if (to == null) {
            to = LocalDate.now(); // Today
        }

        List<CommitEntry> allCommits = svnService.getAllCommits(from, to);
        if (authorname != null) {
            if(authorname.length()>0){
                allCommits = allCommits.stream().filter(x -> x.getAuthor().equals(authorname)).collect(Collectors.toList());
            }
        }
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



    @GetMapping("/deleteRepo/{name}")
    public ResponseEntity<?> deleteRepository(@PathVariable String name) {
        svnRepo.deleteByRepoName(name);
        return ResponseEntity.ok().build(); // Redirect to list page after deletion
    }


    @GetMapping("/diff-viewer")
    public String getDiffViewerPage(@RequestParam String repoUrl, @RequestParam long rev, Model model) throws SVNException {
        String diff = svnService.getDiffBetweenRevisions(repoUrl, rev);

        String encodedDiff = Base64.getEncoder().encodeToString(diff.getBytes(StandardCharsets.UTF_8));

        model.addAttribute("encodedDiff", encodedDiff);
        model.addAttribute("filename", extractFilename(repoUrl)); // Optional for display
        model.addAttribute("rev", rev);

        return "diff-viewer"; // Thymeleaf template name
    }

    private String extractFilename(String repoUrl) {
        return repoUrl.substring(repoUrl.lastIndexOf('/') + 1);
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

