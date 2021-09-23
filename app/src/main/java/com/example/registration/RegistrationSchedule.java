/*
 *  1 2 3 4 5 .. 14
 * :15
 * :30
 * :45
 */
package com.example.registration;

import com.github.tlaabs.timetableview.Schedule;
import com.github.tlaabs.timetableview.Time;

import java.util.AbstractMap;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class RegistrationSchedule extends Schedule {
    private Map<Integer, Integer> parsedTime;
    public RegistrationSchedule() {
        super();
    }

    public RegistrationSchedule(String professorName, String classTitle, String classPlace, char day, String time) {
        this.parsedTime = new HashMap<Integer, Integer>();
        setProfessorName(professorName);
        setClassTitle(classTitle);
        setClassPlace(classPlace);
        setDay(day);
        setTime(time);
    }

    public void setDay(char day) {
        switch(day) {
            case 'M':
                setDay(0);
                break;

            case 'T':
                setDay(1);
                break;

            case 'W':
                setDay(2);
                break;

            case 'R':
                setDay(3);
                break;

            case 'F':
                setDay(4);
                break;
        }
    }

    public void setTime(String time) {
        String [] timeArr = time.split("-", 2);

        Map.Entry parsedStart = this.parse(timeArr[0]);
        Map.Entry parsedEnd = this.parse(timeArr[1]);
        parsedTime.put((Integer) parsedStart.getKey(), (Integer) parsedStart.getValue());
        parsedTime.put((Integer) parsedEnd.getKey(), (Integer) parsedEnd.getValue());

        Set entrySet = parsedTime.entrySet();
        Iterator it = entrySet.iterator();

        HashMap.Entry curr = (HashMap.Entry)it.next();
        setStartTime(new Time((int) curr.getKey(), (int)curr.getValue()));

        curr = (HashMap.Entry)it.next();
        setEndTime(new Time((int) curr.getKey(), (int)curr.getValue()));
    }

    /**
     *
     * @param time in String
     *        ex: 6:30 pm
     */
    public Map.Entry parse(String time) {
        time = time.trim();
        String hourMin = time.split(" ")[0];
        String timeFrame = time.split(" ")[1];

        String[] pair = hourMin.split(":", 2);
        int hour = Integer.parseInt(pair[0]);
        int min = Integer.parseInt(pair[1]);

        if(time.contains("p")) hour += 12;

        Map.Entry<Integer, Integer> returnVal = new AbstractMap.SimpleEntry<Integer, Integer>(hour, min);
        return returnVal;
    }
}
