package com.example.demo.service;

import com.example.demo.model.CongestionData;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

@Service
public class SeoulApiService {

    private static final String BASE_URL = "http://openapi.seoul.go.kr:8088";
    private static final String API_KEY = "696d6c5643736f6838306c47435742";
    private static final String REQUEST_TYPE = "xml";
    private static final String SERVICE_NAME = "citydata_ppltn";
    private static final String START_INDEX = "1";
    private static final String END_INDEX = "10";

    public CongestionData getPopulationInfo(String areaName) {
        final String endpoint = String.format("%s/%s/%s/%s/%s/%s/%s",
                BASE_URL,
                API_KEY,
                REQUEST_TYPE,
                SERVICE_NAME,
                START_INDEX,
                END_INDEX,
                areaName);
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.getForEntity(endpoint, String.class);

        // XML 데이터 출력 (디버깅용)
        System.out.println("응답 본문: " + response.getBody());

        // XML 데이터 파싱
        CongestionData parsedData = parseXmlData(response.getBody(), areaName.trim());

        // 파싱된 데이터 출력 (디버깅용)
        if (parsedData != null) {
            System.out.println("지역 명: " + parsedData.getAreaName());
            System.out.println("지역 코드: " + parsedData.getAreaCode());
            System.out.println("지역 혼잡 수준: " + parsedData.getAreaCongestLevel());
            System.out.println("지역 혼잡 메시지: " + parsedData.getAreaCongestMsg());
            System.out.println("남성 인구 비율: " + parsedData.getMalePopulationRate());
            System.out.println("여성 인구 비율: " + parsedData.getFemalePopulationRate());
            System.out.println("예상 시간: " + parsedData.getForecastTimes());
            System.out.println("예상 혼잡도: " + parsedData.getForecastCongestions());
        }

        return parsedData;
    }

    private CongestionData parseXmlData(String xmlData, String areaName) {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(new InputSource(new StringReader(xmlData)));

            NodeList nList = document.getElementsByTagName("SeoulRtd.citydata_ppltn");

            CongestionData populationInfo = null;
            List<String> forecastTimes = new ArrayList<>();
            List<String> forecastCongestions = new ArrayList<>();

            for (int temp = 0; temp < nList.getLength(); temp++) {
                Node nNode = nList.item(temp);
                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element) nNode;

                    // 지역명으로 필터링
                    String areaNm = getTagValue("AREA_NM", element);
                    System.out.println("파싱된 지역 명: " + areaNm); // 디버깅용
                    if (areaNm != null && areaNm.equalsIgnoreCase(areaName)) {
                        if (populationInfo == null) {
                            populationInfo = new CongestionData();
                            populationInfo.setPopulationTime(getTagValue("PPLTN_TIME", element));
                            populationInfo.setAreaName(areaNm);
                            populationInfo.setAreaCode(getTagValue("AREA_CD", element));
                            populationInfo.setLivePopulationStatus(getTagValue("LIVE_PPLTN_STTS", element));
                            populationInfo.setAreaCongestLevel(getTagValue("AREA_CONGEST_LVL", element));
                            populationInfo.setAreaCongestMsg(getTagValue("AREA_CONGEST_MSG", element));
                            populationInfo.setMalePopulationRate(getTagValue("MALE_PPLTN_RATE", element));
                            populationInfo.setFemalePopulationRate(getTagValue("FEMALE_PPLTN_RATE", element));
                        }

                        NodeList forecastNodeList = element.getElementsByTagName("FCST_PPLTN");
                        for (int j = 1; j < forecastNodeList.getLength(); j++) {
                            Node forecastNode = forecastNodeList.item(j);
                            if (forecastNode.getNodeType() == Node.ELEMENT_NODE) {
                                Element forecastElement = (Element) forecastNode;
                                forecastTimes.add(getTagValue("FCST_TIME", forecastElement));
                                forecastCongestions.add(getTagValue("FCST_CONGEST_LVL", forecastElement));
                            }
                        }
                    }
                }
            }

            if (populationInfo != null) {
                populationInfo.setForecastTimes(forecastTimes);
                populationInfo.setForecastCongestions(forecastCongestions);
            }

            return populationInfo;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private String getTagValue(String tag, Element element) {
        NodeList nodeList = element.getElementsByTagName(tag);
        if (nodeList.getLength() > 0) {
            NodeList childNodes = nodeList.item(0).getChildNodes();
            if (childNodes.getLength() > 0) {
                Node value = childNodes.item(0);
                return value.getNodeValue();
            }
        }
        return null;
    }
}
