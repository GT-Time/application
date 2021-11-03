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

package com.gttime.android.component;

import com.gttime.android.util.util.Util;
import com.github.tlaabs.timetableview.Time;

import java.util.AbstractMap;
import java.util.HashMap;
import java.util.Map;

public class CourseTime extends Time{
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
