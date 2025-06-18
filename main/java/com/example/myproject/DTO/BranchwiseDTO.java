package com.example.myproject.DTO;

public class BranchwiseDTO {
    Integer homeBranchCode;
    Integer count;

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Integer getHomeBranchCode() {
        return homeBranchCode;
    }

    public void setHomeBranchCode(Integer homeBranchCode) {
        this.homeBranchCode = homeBranchCode;
    }

    public BranchwiseDTO(Integer homeBranchCode, Integer count) {
        this.homeBranchCode = homeBranchCode;
        this.count = count;
    }
    public BranchwiseDTO() {}

}
