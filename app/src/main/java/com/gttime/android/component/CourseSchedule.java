/*
Copyright 2019 tlaabs

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.

 */

/*
 *  1 2 3 4 5 .. 14
 * :15
 * :30
 * :45
 */
package com.gttime.android.component;

import com.github.tlaabs.timetableview.Schedule;
import com.gttime.android.util.StringUtil;

import java.util.HashMap;
import java.util.Map;

public class CourseSchedule extends Schedule {
    private Map<Integer, Integer> parseTimedTime;

    public CourseSchedule() {
        super();
    }

    public CourseSchedule(String courseTitle, String courseInstructor, String courseLocation, String courseTime, char courseDay) {
        this.parseTimedTime = new HashMap<Integer, Integer>();
        setDay(courseDay);
        setClassTitle(courseTitle);
        setStartTime(new CourseTime(StringUtil.split(courseTime,"-",2)[0]));
        setEndTime(new CourseTime(StringUtil.split(courseTime,"-",2)[1]));
        setClassPlace(courseLocation);
        setProfessorName(courseInstructor);
    }


    public void setDay(char day) {
        switch (day) {
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

    public String parseDay(int day) {
        char parsed = ' ';
        switch(day) {
            case 0 :
                parsed = 'M';
                break;
            case 1 :
                parsed = 'T';
                break;

            case 2 :
                parsed = 'W';
                break;

            case 3 :
                parsed = 'H';
                break;

            case 4 :
                parsed = 'F';
                break;
        }

        return Character.toString(parsed);
    }

    /**
     *  Additional variable setter & getter functions
     */

    public String getCourseTime() {
        return getStartTime() + " : " + getEndTime();
    }
}
