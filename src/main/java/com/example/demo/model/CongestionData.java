package com.example.demo.model;

import java.util.List;

public class CongestionData {
    private String populationTime;
    private String areaName;
    private String areaCode;
    private String livePopulationStatus;
    private String areaCongestLevel;
    private String areaCongestMsg;
    private String malePopulationRate;
    private String femalePopulationRate;
    private List<String> forecastTimes;
    private List<String> forecastCongestions;

    // getters and setters for all fields

    public String getPopulationTime() {
        return populationTime;
    }

    public void setPopulationTime(String populationTime) {
        this.populationTime = populationTime;
    }

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

    public List<String> getForecastTimes() {
        return forecastTimes;
    }

    public void setForecastTimes(List<String> forecastTimes) {
        this.forecastTimes = forecastTimes;
    }

    public List<String> getForecastCongestions() {
        return forecastCongestions;
    }

    public void setForecastCongestions(List<String> forecastCongestions) {
        this.forecastCongestions = forecastCongestions;
    }
}
