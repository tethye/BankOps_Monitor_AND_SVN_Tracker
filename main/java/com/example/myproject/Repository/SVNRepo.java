package com.example.myproject.Repository;

import com.example.myproject.Entity.SvnRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SVNRepo extends JpaRepository<SvnRepository, Integer> {

}
