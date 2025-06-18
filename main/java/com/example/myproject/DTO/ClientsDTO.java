package com.example.myproject.DTO;


import jakarta.persistence.criteria.CriteriaBuilder;

public class ClientsDTO {

    private Long clientsCode;
    private String clientsName;
    private String fatherName;
    private String accountNumber;
    private String phoneNumber;
    private Integer homeBranchCode;
    private String typeFlag;
    private String panNumber;
    private String address1;
    private String address2;
    private String address3;
    private String address4;
    private String address5;

    public Long getClientsCode() {
        return clientsCode;
    }

    public void setClientsCode(Long clientsCode) {
        this.clientsCode = clientsCode;
    }

    public String getClientsName() {
        return clientsName;
    }

    public void setClientsName(String clientsName) {
        this.clientsName = clientsName;
    }

    public String getFatherName() {
        return fatherName;
    }

    public void setFatherName(String fatherName) {
        this.fatherName = fatherName;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Integer getHomeBranchCode() {
        return homeBranchCode;
    }

    public void setHomeBranchCode(Integer homeBranchCode) {
        this.homeBranchCode = homeBranchCode;
    }

    public String getTypeFlag() {
        return typeFlag;
    }

    public void setTypeFlag(String typeFlag) {
        this.typeFlag = typeFlag;
    }

    public String getPanNumber() {
        return panNumber;
    }

    public void setPanNumber(String panNumber) {
        this.panNumber = panNumber;
    }

    public String getAddress1() {
        return address1;
    }

    public void setAddress1(String address1) {
        this.address1 = address1;
    }

    public String getAddress2() {
        return address2;
    }

    public void setAddress2(String address2) {
        this.address2 = address2;
    }

    public String getAddress3() {
        return address3;
    }

    public void setAddress3(String address3) {
        this.address3 = address3;
    }

    public String getAddress4() {
        return address4;
    }

    public void setAddress4(String address4) {
        this.address4 = address4;
    }

    public String getAddress5() {
        return address5;
    }

    public void setAddress5(String address5) {
        this.address5 = address5;
    }

    public ClientsDTO(){}

    public ClientsDTO(Long CLIENTS_CODE,
                      String CLIENTS_NAME,
                      String INDCLIENT_FATHER_NAME,
                      String IACLINK_ACTUAL_ACNUM,
                      String CLIENTS_PHN,
                      Integer CLIENTS_HOME_BRN_CODE,
                      String CLIENTS_TYPE_FLG,
                      String CLIENTS_PAN_GIR_NUM,
                      String CLIENTS_ADDR1,
                      String CLIENTS_ADDR2,
                      String CLIENTS_ADDR3,
                      String CLIENTS_ADDR4,
                      String CLIENTS_ADDR5) {
        this.clientsCode = CLIENTS_CODE;
        this.clientsName = CLIENTS_NAME;
        this.fatherName = INDCLIENT_FATHER_NAME;
        this.accountNumber = IACLINK_ACTUAL_ACNUM;
        this.phoneNumber = CLIENTS_PHN;
        this.homeBranchCode = CLIENTS_HOME_BRN_CODE;
        this.typeFlag = CLIENTS_TYPE_FLG;
        this.panNumber = CLIENTS_PAN_GIR_NUM;
        this.address1 = CLIENTS_ADDR1;
        this.address2 = CLIENTS_ADDR2;
        this.address3 = CLIENTS_ADDR3;
        this.address4 = CLIENTS_ADDR4;
        this.address5 = CLIENTS_ADDR5;
    }
}
