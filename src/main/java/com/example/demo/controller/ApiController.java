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

@Controller  //Spring MVC의 컨트롤러 역할을 한다는 것을 나타냄
public class ApiController {

    // GET 요청: 폼 페이지를 보여주는 메소드
    @GetMapping("/Congestion") //사용자가 "/Congestion" URL로 GET 요청을 보내면 이 메서드가 호출됩니다.

    public String getCongestionForm(Model model) {
        // 아무런 데이터를 추가하지 않고 단순히 폼 페이지("congestion" 뷰) 반환
        return "congestion";
    }

    private static final Logger logger = LoggerFactory.getLogger(ApiController.class);

    @Autowired
    private SeoulApiService seoulApiService;

    //@Autowired 어노테이션을 통해 SeoulApiService 클래스가 자동으로 주입됩니다.

    @PostMapping("/Congestion") //사용자가 폼을 제출하면 이 메서드가 호출됩니다.이 메서드는 사용자가 입력한 장소 정보를 받아 처리합니다.
    public String postCongestionInfo(@RequestParam(value = "location", required = false) String location, Model model) {
        //폼에서 전송된 "location" 파라미터 값을 받아 location 변수에 저장합니다.required = false는 이 파라미터가 필수가 아니라는 것을 의미합니다.
        logger.info("Received POST request for congestion info with location: {}", location);

        // 입력 값 검증
        if (location == null || location.trim().isEmpty()) {
            logger.warn("Invalid location parameter: {}", location);
            model.addAttribute("error", "Invalid Input.");
            return "congestion";
        }
        //logger를 사용하여 POST 요청 수신 시 장소 정보를 로깅하고, 오류 상황에 대한 로그 메시지를 기록합니다.

        CongestionData data = seoulApiService.getPopulationInfo(location.trim());
        //사용자가 입력한 장소 정보를 바탕으로 SeoulApiService의 getPopulationInfo() 메서드를 호출합니다.
        //이 메서드는 해당 장소의 혼잡도 정보를 담은 CongestionData 객체를 반환합니다.

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
        }
        // 입력한 장소를 다시 모델에 추가하여 폼에 유지
        model.addAttribute("location", location);
        //반환된 CongestionData 객체의 정보를 모델에 추가하여 뷰에 전달합니다.
        //이렇게 전달된 정보는 "congestion" 뷰에서 사용됩니다.

        return "congestion";
    }
}
