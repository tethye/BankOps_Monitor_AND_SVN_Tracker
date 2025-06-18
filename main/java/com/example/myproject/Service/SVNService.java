package com.example.myproject.Service;

import com.example.myproject.Entity.SvnRepository;
import com.example.myproject.Repository.SVNRepo;
import com.example.myproject.model.CommitEntry;
import com.example.myproject.model.DirectoryEntry;
import com.example.myproject.model.Repository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.tmatesoft.svn.core.*;
import org.tmatesoft.svn.core.auth.ISVNAuthenticationManager;
import org.tmatesoft.svn.core.io.SVNRepository;
import org.tmatesoft.svn.core.io.SVNRepositoryFactory;
import org.tmatesoft.svn.core.wc.SVNLogClient;
import org.tmatesoft.svn.core.wc.SVNRevision;
import org.tmatesoft.svn.core.wc.SVNWCUtil;

import java.time.ZoneId;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Service
public class SVNService {
    private final Map<String, SVNRepository> repositories = new ConcurrentHashMap<>();
    private final List<Repository> repoList = new ArrayList<>();
    @Autowired
    private SVNRepo svnRepo;

    @PostConstruct
    public void init() throws SVNException {
        // Add initial repositories
        repositories.clear();
        repoList.clear();
         List<SvnRepository> svnRepositoryList = svnRepo.findAll();
         for(SvnRepository svnRepository : svnRepositoryList){
             addRepository(new Repository(svnRepository.getRepo_name(), svnRepository.getRepo_url(), svnRepository.getUsername(), svnRepository.getPassword()));
         }

    }

    public void addRepository(Repository repo) throws SVNException {
        if (repoList.stream().noneMatch(r -> r.getUrl().equals(repo.getUrl()))) {
            SVNRepository svnRepo = SVNRepositoryFactory.create(
                    SVNURL.parseURIEncoded(repo.getUrl()));
            ISVNAuthenticationManager authManager =
                    SVNWCUtil.createDefaultAuthenticationManager(
                            repo.getUsername(), repo.getPassword());
            svnRepo.setAuthenticationManager(authManager);
            repositories.put(repo.getUrl(), svnRepo);
            repoList.add(repo);
        }
    }

    public List<CommitEntry> getCommitHistory(String repoUrl, String path) throws SVNException {
        List<CommitEntry> entries = new ArrayList<>();
        SVNLogClient logClient = new SVNLogClient(
                repositories.get(repoUrl).getAuthenticationManager(), null);

        logClient.doLog(SVNURL.parseURIEncoded(repoUrl),
                new String[]{path}, SVNRevision.HEAD, SVNRevision.create(0),
                SVNRevision.HEAD, false, true, 0,
                new ISVNLogEntryHandler() {
                    @Override
                    public void handleLogEntry(SVNLogEntry logEntry) {
                        entries.add(convert(logEntry));
                    }
                });

        return entries.stream()
                .sorted(Comparator.comparing(CommitEntry::getDate).reversed())
                .collect(Collectors.toList());
    }

    private CommitEntry convert(SVNLogEntry entry) {
        return new CommitEntry(
                entry.getRevision(),
                entry.getAuthor(),
                entry.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime(),
                entry.getMessage(),
                String.join(", ", entry.getChangedPaths().keySet())
        );
    }

    public List<Repository> getRepositories() {
        return Collections.unmodifiableList(repoList);
    }

    public Repository getRepository(String repoName) {
        return repoList.stream()
                .filter(r -> r.getName().equalsIgnoreCase(repoName))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Repository not found"));
    }

    public List<DirectoryEntry> getDirectoryStructure(String repoUrl, String path) {
        try {
            SVNRepository repository = repositories.get(repoUrl);
            Collection<SVNDirEntry> entries = repository.getDir(path, -1, null, (Collection) null);

            return entries.stream()
                    .map(entry -> new DirectoryEntry(
                            entry.getName(),
                            entry.getRelativePath(),
                            entry.getKind() == SVNNodeKind.DIR,
                            entry.getRevision()
                    ))
                    .collect(Collectors.toList());
        } catch (SVNException e) {
            throw new RuntimeException("Error fetching directory structure", e);
        }
    }

    public List<CommitEntry> getAllCommits() {
        try {
            init();
        } catch (SVNException e) {
            throw new RuntimeException(e);
        }
        return repoList.stream()
                .flatMap(repo -> {
                    try {
                        return getCommitHistory(repo.getUrl(), "").stream();
                    } catch (SVNException e) {
                        throw new RuntimeException(e);
                    }
                })
                .sorted(Comparator.comparing(CommitEntry::getDate).reversed())
                .collect(Collectors.toList());
    }



    public Map<String, Long> getCommitStats(List<CommitEntry> commits) {
        return commits.stream()
                .collect(Collectors.groupingBy(
                        CommitEntry::getAuthor,
                        Collectors.counting()
                ));
    }

    public Map<String, Object> prepareChartData(List<CommitEntry> commits) {
        Map<String, Long> stats = getCommitStats(commits);

        // Sort by commit count descending
        List<Map.Entry<String, Long>> sorted = new ArrayList<>(stats.entrySet());
        sorted.sort((e1, e2) -> e2.getValue().compareTo(e1.getValue()));

        Map<String, Object> chartData = new LinkedHashMap<>();
        chartData.put("labels", sorted.stream()
                .map(Map.Entry::getKey)
                .collect(Collectors.toList()));

        chartData.put("data", sorted.stream()
                .map(Map.Entry::getValue)
                .collect(Collectors.toList()));

        return chartData;
    }
}

