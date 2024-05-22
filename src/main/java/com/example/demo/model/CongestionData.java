package com.example.demo.model;

public class CongestionData {
    // 지역 이름
    private String areaName;
    // 지역 코드
    private String areaCode;
    // 실시간 인구 상태
    private String livePopulationStatus;
    // 지역 혼잡 수준
    private String areaCongestLevel;
    // 지역 혼잡 메시지
    private String areaCongestMsg;
    // 남성 인구 비율
    private String malePopulationRate;
    // 여성 인구 비율
    private String femalePopulationRate;

    // Getter 및 Setter
    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public String getAreaCode() {
        return areaCode;
    }

    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode;
    }

    public String getLivePopulationStatus() {
        return livePopulationStatus;
    }

    public void setLivePopulationStatus(String livePopulationStatus) {
        this.livePopulationStatus = livePopulationStatus;
    }

    public String getAreaCongestLevel() {
        return areaCongestLevel;
    }

    public void setAreaCongestLevel(String areaCongestLevel) {
        this.areaCongestLevel = areaCongestLevel;
    }

    public String getAreaCongestMsg() {
        return areaCongestMsg;
    }

    public void setAreaCongestMsg(String areaCongestMsg) {
        this.areaCongestMsg = areaCongestMsg;
    }

    public String getMalePopulationRate() {
        return malePopulationRate;
    }

    public void setMalePopulationRate(String malePopulationRate) {
        this.malePopulationRate = malePopulationRate;
    }

    public String getFemalePopulationRate() {
        return femalePopulationRate;
    }

    public void setFemalePopulationRate(String femalePopulationRate) {
        this.femalePopulationRate = femalePopulationRate;
    }
}
