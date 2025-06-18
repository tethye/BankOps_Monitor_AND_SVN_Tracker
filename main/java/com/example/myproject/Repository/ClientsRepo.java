package com.example.myproject.Repository;

import com.example.myproject.DTO.BranchwiseDTO;
import com.example.myproject.DTO.ClientsDTO;
import com.example.myproject.Entity.Clients;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
public interface ClientsRepo extends JpaRepository<Clients, Long> {
     @Query(value = "SELECT COUNT(*) FROM clientsname WHERE CONTAINS(CLIENTS_NAME, ?) > 0", nativeQuery = true)
     int countByClientsName(String clientsName);

     @Query(value = "SELECT CLIENTS_CODE FROM ( SELECT CLIENTS_CODE, ROWNUM AS rnum FROM clientsname WHERE CONTAINS(CLIENTS_NAME, ?) > 0 AND ROWNUM <= ?) WHERE rnum >= ?", nativeQuery = true)
     ArrayList<Long> getClientsCode(String name, long limit, long offset);


     @Query(name = "ClientsRepo.getClientsData", nativeQuery = true)
     ArrayList<ClientsDTO> getClientsData(@Param("clientCodes") ArrayList<Long> clientCodes);

     @Query(name = "ClientsRepo.getAllClientsData", nativeQuery = true)
     ArrayList<ClientsDTO> getAllClientsData(String name);


     @Query(name = "ClientsRepo.getBranchwiseCounts", nativeQuery = true)
     ArrayList<BranchwiseDTO> getBranchwiseData(String name);


}
