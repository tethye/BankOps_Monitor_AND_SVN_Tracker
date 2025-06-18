package com.example.myproject.Entity;


import com.example.myproject.DTO.BranchwiseDTO;
import com.example.myproject.DTO.ClientsDTO;
import jakarta.persistence.*;


@Entity
@SqlResultSetMapping(
        name = "ClientsDTOMapping",
        classes = @ConstructorResult(
                targetClass = ClientsDTO.class,
                columns = {
                        @ColumnResult(name = "CLIENTS_CODE", type = Long.class),
                        @ColumnResult(name = "CLIENTS_NAME", type = String.class),
                        @ColumnResult(name = "INDCLIENT_FATHER_NAME", type = String.class),
                        @ColumnResult(name = "IACLINK_ACTUAL_ACNUM", type = String.class),
                        @ColumnResult(name = "CLIENTS_PHN", type = String.class),
                        @ColumnResult(name = "CLIENTS_HOME_BRN_CODE", type = Integer.class),
                        @ColumnResult(name = "CLIENTS_TYPE_FLG", type = String.class),
                        @ColumnResult(name = "CLIENTS_PAN_GIR_NUM", type = String.class),
                        @ColumnResult(name = "CLIENTS_ADDR1", type = String.class),
                        @ColumnResult(name = "CLIENTS_ADDR2", type = String.class),
                        @ColumnResult(name = "CLIENTS_ADDR3", type = String.class),
                        @ColumnResult(name = "CLIENTS_ADDR4", type = String.class),
                        @ColumnResult(name = "CLIENTS_ADDR5", type = String.class)
                }
        )
)
@NamedNativeQuery(
        name = "ClientsRepo.getClientsData",
        query = "SELECT c.CLIENTS_CODE, c.CLIENTS_NAME, i.INDCLIENT_FATHER_NAME, ia.IACLINK_ACTUAL_ACNUM, " +
                "TO_CHAR(GET_MOBILE_NUM(c.CLIENTS_CODE)) AS CLIENTS_PHN, c.CLIENTS_HOME_BRN_CODE, " +
                "c.CLIENTS_TYPE_FLG, c.CLIENTS_PAN_GIR_NUM, c.CLIENTS_ADDR1, c.CLIENTS_ADDR2, c.CLIENTS_ADDR3, c.CLIENTS_ADDR4, c.CLIENTS_ADDR5 " +
                "FROM clients c INNER JOIN indclients i ON c.CLIENTS_CODE = i.INDCLIENT_CODE " +
                "LEFT JOIN iaclink ia ON c.CLIENTS_CODE = ia.IACLINK_CIF_NUMBER " +
                "WHERE c.CLIENTS_CODE IN (:clientCodes)",
        resultSetMapping = "ClientsDTOMapping"
)
@SqlResultSetMapping(
        name = "ClientsDTOMappingforalldata",
        classes = @ConstructorResult(
                targetClass = ClientsDTO.class,
                columns = {
                        @ColumnResult(name = "CLIENTS_CODE", type = Long.class),
                        @ColumnResult(name = "CLIENTS_NAME", type = String.class),
                        @ColumnResult(name = "INDCLIENT_FATHER_NAME", type = String.class),
                        @ColumnResult(name = "IACLINK_ACTUAL_ACNUM", type = String.class),
                        @ColumnResult(name = "CLIENTS_PHN", type = String.class),
                        @ColumnResult(name = "CLIENTS_HOME_BRN_CODE", type = Integer.class),
                        @ColumnResult(name = "CLIENTS_TYPE_FLG", type = String.class),
                        @ColumnResult(name = "CLIENTS_PAN_GIR_NUM", type = String.class),
                        @ColumnResult(name = "CLIENTS_ADDR1", type = String.class),
                        @ColumnResult(name = "CLIENTS_ADDR2", type = String.class),
                        @ColumnResult(name = "CLIENTS_ADDR3", type = String.class),
                        @ColumnResult(name = "CLIENTS_ADDR4", type = String.class),
                        @ColumnResult(name = "CLIENTS_ADDR5", type = String.class)
                }
        )
)
@NamedNativeQuery(
        name = "ClientsRepo.getAllClientsData",
        query = "SELECT cn.CLIENTS_CODE, cn.CLIENTS_NAME, i.INDCLIENT_FATHER_NAME, ia.IACLINK_ACTUAL_ACNUM, " +
                "TO_CHAR(GET_MOBILE_NUM(cn.CLIENTS_CODE)) AS CLIENTS_PHN, c.CLIENTS_HOME_BRN_CODE, " +
                "c.CLIENTS_TYPE_FLG, c.CLIENTS_PAN_GIR_NUM, c.CLIENTS_ADDR1, c.CLIENTS_ADDR2, c.CLIENTS_ADDR3, " +
                "c.CLIENTS_ADDR4, c.CLIENTS_ADDR5 " +
                "FROM clientsname cn " +
                "INNER JOIN clients c ON cn.CLIENTS_CODE = c.CLIENTS_CODE " +
                "INNER JOIN indclients i ON c.CLIENTS_CODE = i.INDCLIENT_CODE " +
                "LEFT JOIN iaclink ia ON c.CLIENTS_CODE = ia.IACLINK_CIF_NUMBER " +
                "WHERE CONTAINS(c.CLIENTS_NAME, ?) > 0",
        resultSetMapping = "ClientsDTOMappingforalldata"
)

@SqlResultSetMapping(
        name = "BranchwiseDTOMapping",
        classes = @ConstructorResult(
                targetClass = BranchwiseDTO.class,
                columns = {
                        @ColumnResult(name = "CLIENTS_HOME_BRN_CODE", type = Integer.class),
                        @ColumnResult(name = "cnt", type = Integer.class)
                }
        )
)
@NamedNativeQuery(
        name = "ClientsRepo.getBranchwiseCounts",
        query = "SELECT CLIENTS_HOME_BRN_CODE, COUNT(CLIENTS_HOME_BRN_CODE) AS cnt " +
                "FROM Clientsname " +
                "WHERE CONTAINS(CLIENTS_NAME, :name) > 0 " +
                "GROUP BY CLIENTS_HOME_BRN_CODE " +
                "ORDER BY cnt DESC",
        resultSetMapping = "BranchwiseDTOMapping"
)

public class Clients {
    @Id
    Long Clients_code;
    String Clients_name;
    Integer Clients_Home_Brn_Code;

    public Long getClients_code() {
        return Clients_code;
    }

    public void setClients_code(Long clients_code) {
        Clients_code = clients_code;
    }

    public String getClients_name() {
        return Clients_name;
    }

    public void setClients_name(String clients_name) {
        Clients_name = clients_name;
    }

    public Integer getClients_Home_Brn_Code() {
        return Clients_Home_Brn_Code;
    }

    public void setClients_Home_Brn_Code(Integer clients_Home_Brn_Code) {
        Clients_Home_Brn_Code = clients_Home_Brn_Code;
    }

    public Clients(Long clients_code, String clients_name, Integer clients_Home_Brn_Code) {
        Clients_code = clients_code;
        Clients_name = clients_name;
        Clients_Home_Brn_Code = clients_Home_Brn_Code;
    }

    public Clients() {
    }

    @Override
    public String toString() {
        return "Clients{" +
                "Clients_code=" + Clients_code +
                ", Clients_name='" + Clients_name + '\'' +
                ", Clients_Home_Brn_Code=" + Clients_Home_Brn_Code +
                '}';
    }
}
