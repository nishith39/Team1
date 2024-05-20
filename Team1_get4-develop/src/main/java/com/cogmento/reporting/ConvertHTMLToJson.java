package com.cogmento.reporting;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class ConvertHTMLToJson {
    public static String path = System.getProperty("user.dir");

    public static void convertHTMLReportToJSONReport(String filePath) {

        System.out.println("Converting HTML Report to JSON test Results for JIRA XRay Results Update");
        try {
            Document doc1 = Jsoup.parse(new File(path + "\\reports\\Test-Automaton-Report.html"), "utf-8");
            // Document doc1 = Jsoup.parse(new File(path +("\\reports\\Test-Automaton-Report.html"), "utf-8"));
            List<Map<String, String>> allcases = new ArrayList<Map<String, String>>();
            Elements testItems = doc1.select(".test-item");
            for (Element test : testItems) {
                Map<String, String> obj = new HashMap<String, String>();
                obj.put("name", test.select(".name").text());
                obj.put("status", test.select(".text-sm .badge").text());
//                obj.put("startTime", test.select(".info .badge-success").text());
//                obj.put("endTime", test.select(".info .badge-danger").text());
                obj.put("duration", test.select(".info .badge-default").first().text());
                allcases.add(obj);
            }

            ObjectMapper objectMapper = new ObjectMapper();
            String jacksonData = objectMapper.writeValueAsString(allcases);
            FileWriter writer;
            try {
                writer = new FileWriter(new File(filePath ));
                writer.write(jacksonData);
                writer.flush();
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.out.println(jacksonData);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}


