package com.example.newopenapiexchangeproject3;

public class UpdateVO {

  private String UpdateTv;
  private String UpdateName;
  private String UpdateTime;

    public UpdateVO(String updateTv, String updateName, String updateTime) {
        UpdateTv = updateTv;
        UpdateName = updateName;
        UpdateTime = updateTime;
    }

    public String getUpdateTv() {
        return UpdateTv;
    }

    public void setUpdateTv(String updateTv) {
        UpdateTv = updateTv;
    }

    public String getUpdateName() {
        return UpdateName;
    }

    public void setUpdateName(String updateName) {
        UpdateName = updateName;
    }

    public String getUpdateTime() {
        return UpdateTime;
    }

    public void setUpdateTime(String updateTime) {
        UpdateTime = updateTime;
    }
}
