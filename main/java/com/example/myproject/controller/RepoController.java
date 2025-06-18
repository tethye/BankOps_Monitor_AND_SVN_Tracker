package com.example.myproject.controller;

import com.example.myproject.Service.SVNService;
import com.example.myproject.model.CommitEntry;
import com.example.myproject.model.Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.tmatesoft.svn.core.SVNException;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/repo")
public class RepoController {

    @Autowired
    private SVNService svnService;


    @GetMapping("/{repoName}")
    public String repoDetail(@PathVariable String repoName,
                             @RequestParam(defaultValue = "") String path,
                             Model model,
                             @RequestParam(defaultValue = "0") int page,
                             @RequestParam(defaultValue = "10") int size) {

        Repository repo = svnService.getRepository(repoName);
        List<CommitEntry> commits = null;
        try {
            commits = svnService.getCommitHistory(repo.getUrl(), path);
        } catch (SVNException e) {
            throw new RuntimeException(e);
        }
        Page<CommitEntry> commitPage = getPaginatedCommits(commits, page, size);

        model.addAttribute("repo", repo);
        model.addAttribute("path", path);
        model.addAttribute("directory", svnService.getDirectoryStructure(repo.getUrl(), path));
        model.addAttribute("commits", commitPage);
        model.addAttribute("displayPages", createPageNumbers(page, commits.size()));
        model.addAttribute("chartData", svnService.prepareChartData(commits));
        return "repo-detail";
    }
    private Page<CommitEntry> getPaginatedCommits(List<CommitEntry> commits, int page, int size) {
        int total = commits.size();
        int start = Math.min(page * size, total);
        int end = Math.min((page + 1) * size, total);

        return new PageImpl<>(
                commits.subList(start, end),
                PageRequest.of(page, size),
                total
        );
    }

    public List<Integer> createPageNumbers(int currentPage, int totalPages) {
        List<Integer> pageNumbers = new ArrayList<>();

        // Always include first page
        pageNumbers.add(0);

        // Add '...' if currentPage > 3
        if (currentPage > 3) {
            pageNumbers.add(-1); // placeholder for ellipsis
        }

        // Add current page +/- 1
        for (int i = currentPage - 1; i <= currentPage + 1; i++) {
            if (i > 0 && i < totalPages - 1) {
                pageNumbers.add(i);
            }
        }

        // Add '...' if currentPage < totalPages - 4
        if (currentPage < totalPages - 4) {
            pageNumbers.add(-1); // placeholder for ellipsis
        }

        // Always include last page
        if (totalPages > 1) {
            pageNumbers.add(totalPages - 1);
        }

        return pageNumbers;
    }



}