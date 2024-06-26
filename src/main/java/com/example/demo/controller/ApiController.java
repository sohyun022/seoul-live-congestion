package com.example.demo.controller;

import com.example.demo.model.CongestionData;
import com.example.demo.service.SeoulApiService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ApiController {

    private static final Logger logger = LoggerFactory.getLogger(ApiController.class);

    @Autowired
    private SeoulApiService seoulApiService;

    @GetMapping("/Congestion")
    public String getCongestionForm(Model model) {
        return "congestion";
    }

    @PostMapping("/Congestion")
    public String postCongestionInfo(@RequestParam(value = "location", required = false) String location, Model model) {
        logger.info("Received POST request for congestion info with location: {}", location);

        if (location == null || location.trim().isEmpty()) {
            logger.warn("Invalid location parameter: {}", location);
            model.addAttribute("error", "Invalid Input.");
            return "congestion";
        }

        CongestionData data = seoulApiService.getPopulationInfo(location.trim());
        if (data == null) {
            logger.warn("No data found for location: {}", location);
            model.addAttribute("error", "No data found for location");
        } else {
            model.addAttribute("populationTime", data.getPopulationTime());
            model.addAttribute("areaName", data.getAreaName());
            model.addAttribute("areaCode", data.getAreaCode());
            model.addAttribute("livePopulationStatus", data.getLivePopulationStatus());
            model.addAttribute("areaCongestLevel", data.getAreaCongestLevel());
            model.addAttribute("areaCongestMsg", data.getAreaCongestMsg());
            model.addAttribute("malePopulationRate", data.getMalePopulationRate());
            model.addAttribute("femalePopulationRate", data.getFemalePopulationRate());
            model.addAttribute("forecastTimes", data.getForecastTimes());
            model.addAttribute("forecastCongestions", data.getForecastCongestions());
            model.addAttribute("latitude", data.getLatitude());
            model.addAttribute("longitude", data.getLongitude());
        }

        model.addAttribute("location", location);
        return "congestion";
    }
}
