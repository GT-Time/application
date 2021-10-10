package com.example.registration;

import com.example.util.Util;
import com.github.tlaabs.timetableview.Time;

import java.util.AbstractMap;
import java.util.HashMap;
import java.util.Map;

public class CourseTime extends Time {
    CourseTime(String time) {
        HashMap.Entry parsed = this.parse(time);

        int hour = (int)parsed.getKey();
        int min = (int)parsed.getValue();

        setHour(hour);
        setMinute(min);
    }

    /**
     *
     * @param time in String
     *        ex: 6:30 pm
     */
    public Map.Entry parse(String time) {
        time = time.trim();
        String[] pair = Util.split(time," ", 2);

        String hourMin = pair[0];
        String timeFrame = pair[1];

        String[] hourMinPair = Util.split(hourMin,":", 2);

        int hour = 0;
        int min = 0;

        if(!hourMinPair[0].isEmpty() && !hourMinPair[1].isEmpty()) {
            hour = Integer.parseInt(hourMinPair[0]);
            min = Integer.parseInt(hourMinPair[1]);
        }

        if(time.contains("p") && hour!=12) hour += 12;

        HashMap.Entry<Integer, Integer> returnVal = new AbstractMap.SimpleEntry<Integer, Integer>(hour, min);
        return returnVal;
    }
}
