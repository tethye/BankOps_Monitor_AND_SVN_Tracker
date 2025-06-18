package com.example.myproject.Repository;

import com.example.myproject.Entity.SvnRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface SVNRepo extends JpaRepository<SvnRepository, Integer> {
    @Modifying
    @Transactional
    @Query(value = "delete from SVNREPOSITORY where repo_name=?", nativeQuery = true)
    void deleteByRepoName(String repoName);
}
