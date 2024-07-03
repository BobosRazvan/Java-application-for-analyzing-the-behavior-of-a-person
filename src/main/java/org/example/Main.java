package org.example;

import java.io.InputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Collectors;


public class Main {
    public static void main(String[] args) {

        InputStream inputStream = Main.class.getResourceAsStream("/Activities.txt");
        Scanner scanner = new Scanner(inputStream);
        List<MonitoredData> listOfMonitoredData=new ArrayList<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");


        while (scanner.hasNextLine()) {
            String activity = scanner.nextLine();
            String parts[]=activity.split("\t\t");


            LocalDateTime startTime = LocalDateTime.parse(parts[0], DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            LocalDateTime endTime = LocalDateTime.parse(parts[1], DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            String activityName = parts[2];

            MonitoredData monitoredData = new MonitoredData(startTime, endTime, activityName);
            listOfMonitoredData.add(monitoredData);

        }

        scanner.close();

        for(MonitoredData monitoredData:listOfMonitoredData){
            String startTime=monitoredData.getStartTime().format(formatter);
            String endTime=monitoredData.getEndTime().format(formatter);
            System.out.println(startTime+"    "+endTime+"    "+monitoredData.getActivity());
        }
        System.out.println("Number of distinct days that appear in the monitoring data: ");
        System.out.println(listOfMonitoredData.stream().map(MonitoredData::getStartTimeDay).distinct().count());

        System.out.println("Number of times each activity has appeared over the entire monitoring period: ");
        Map<String,Integer> listOfActivities=listOfMonitoredData.stream().collect(Collectors.groupingBy(MonitoredData::getActivity, Collectors.summingInt(e->1)));
        for(Map.Entry<String,Integer> entry:listOfActivities.entrySet()){
            System.out.println(entry.getKey()+"    "+entry.getValue());
        }
        ///Collectors.summingInt(e->1))  functie care aduna 1 de fiecare data( (e-> 1) returneaxza 1 mereu)
    }
}