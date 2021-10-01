package com.example.registration;

import com.github.tlaabs.timetableview.Time;

import java.util.AbstractMap;
import java.util.HashMap;
import java.util.Map;

public class CourseTime extends Time {
    CourseTime(String time) {
<<<<<<< HEAD
        HashMap.Entry parsed = this.parse(time);
=======
        String [] timeArr = Util.split(time," ", 2);

        String [] hourMin = Util.split(timeArr[0],":",2);
        String timeFrame = timeArr[1];

        HashMap.Entry parsed = this.parse(hourMin[0]);
>>>>>>> 4ac01cfd11578410af300e5ae45eaebc78ba666d

        int hour = (int)parsed.getKey();
        int min = (int)parsed.getValue();

<<<<<<< HEAD
=======
        if(timeFrame.contains("p")) hour+=12;

>>>>>>> 4ac01cfd11578410af300e5ae45eaebc78ba666d
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
<<<<<<< HEAD
        String[] pair = Util.split(time," ", 2);

        String hourMin = pair[0];
        String timeFrame = pair[1];

        String[] hourMinPair = Util.split(hourMin,":", 2);

        int hour = 0;
        int min = 0;

        if(!hourMinPair[0].isEmpty() && hourMinPair[1].isEmpty()) {
            hour = Integer.parseInt(hourMinPair[0]);
            min = Integer.parseInt(hourMinPair[1]);
        }
=======
        String[] timeFramePair = Util.split(time," ", 2);

        // HACK : handle exception
        if (timeFramePair.length < 2) return null;

        String hourMin = timeFramePair[0];
        String timeFrame = timeFramePair[1];

        String[] hourMinPair = Util.split(hourMin,":", 2);
        // HACK :  handle exception
        if (timeFramePair.length < 2) return null;

        int hour = Integer.parseInt(hourMinPair[0]);
        int min = Integer.parseInt(hourMinPair[1]);
>>>>>>> 4ac01cfd11578410af300e5ae45eaebc78ba666d

        if(time.contains("p")) hour += 12;

        HashMap.Entry<Integer, Integer> returnVal = new AbstractMap.SimpleEntry<Integer, Integer>(hour, min);
        return returnVal;
    }
}
