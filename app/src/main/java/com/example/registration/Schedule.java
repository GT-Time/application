/*
 *  1 2 3 4 5 .. 14
 * :15
 * :30
 * :45
 */
package com.example.registration;

import android.content.Context;
import android.graphics.Color;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

public class Schedule {
    private String monday[][] = new String [22][4];
    private String tuesday[][] = new String [22][4];
    private String wednesday[][] = new String [22][4];
    private String thursday[][] = new String [22][4];
    private String friday[][] = new String [22][4];

    public Schedule() {
        for (int i = 1; i < 22; ++i) {
            for (int j = 0; j < 4; ++j) {

                monday[i][j] = "";
                tuesday[i][j] = "";
                wednesday[i][j] = "";
                thursday[i][j] = "";
                friday[i][j] = "";
            }
        }
    }
    public void addSchedule(String scheduleText) {
        int startHour,endHour;
        int startMinute;
        int endMinute;

        int temp = scheduleText.indexOf('-');
        String timeFrame;

        //class starting hour
        int startPoint = scheduleText.indexOf(']');
        int endPoint = scheduleText.indexOf(':');
        startHour = Integer.parseInt(scheduleText.substring(startPoint+1,endPoint));


        if((timeFrame = scheduleText.substring(temp-3,temp-1)).contains("p")) {
            if(startHour != 12) startHour+=12;
        }

        //class starting minute
        startPoint = endPoint;
        endPoint = scheduleText.indexOf(timeFrame) - 1;
        startMinute = Integer.parseInt(scheduleText.substring(startPoint+1,endPoint));

        //class ending hour
        startPoint = scheduleText.indexOf('-');
        endPoint = scheduleText.lastIndexOf(':');
        endHour = Integer.parseInt(scheduleText.substring(startPoint+2,endPoint));

        if((timeFrame = scheduleText.substring(temp+7,temp+9)).contains("p")) {
            if(endHour != 12 ) endHour+=12;
        }

        //class ending minute
        startPoint = endPoint + 1;
        endPoint = scheduleText.lastIndexOf(timeFrame) - 1;
        endMinute = Integer.parseInt(scheduleText.substring(startPoint,endPoint));

        temp = 0;
        int firstIndex = 0, secondIndex = 0;
        switch(startMinute) {
            case 0 : firstIndex = 0;
            break;
            case 15 : firstIndex = 1;
            break;
            case 30 : firstIndex = 2;
            break;
            case 45 : firstIndex = 3;
            break;
        }

        switch(endMinute) {
            case 0 : secondIndex = 0;
                break;
            case 15 : secondIndex = 1;
                break;
            case 30 : secondIndex = 2;
                break;
            case 45 : secondIndex = 3;
                break;
        }
        if((temp = scheduleText.indexOf('M')) > -1) {
            monday[startHour][firstIndex]= "Class";
            monday[endHour][secondIndex]= "Class";

            int fillMinute = ((endHour - startHour) * 60  + (endMinute - startMinute))/15;

            for(int i = 1; i < fillMinute; i++) {
                int tempIndex = (firstIndex + i) % 4;
                int tempHour = startHour;
                if((firstIndex + i) / 4 > 0) tempHour++;
                monday[tempHour][tempIndex] = "Class";
            }
        }
        if((temp = scheduleText.indexOf('T')) > -1) {
            tuesday[startHour][firstIndex]= "Class";
            tuesday[endHour][secondIndex]= "Class";

            int fillMinute = ((endHour - startHour) * 60  + (endMinute - startMinute))/15;
            for(int i = 1; i < fillMinute; i++) {
                int tempIndex = (firstIndex + i) % 4;
                int tempHour = startHour;
                if((firstIndex + i) / 4 > 0) tempHour++;
                tuesday[tempHour][tempIndex] = "Class";
            }
        }
        if((temp = scheduleText.indexOf('W')) > -1) {
            wednesday[startHour][firstIndex]= "Class";
            wednesday[endHour][secondIndex]= "Class";

            int fillMinute = ((endHour - startHour) * 60  + (endMinute - startMinute))/15;
            for(int i = 1; i < fillMinute; i++) {
                int tempIndex = (firstIndex + i) % 4;
                int tempHour = startHour;
                if((firstIndex + i) / 4 > 0) tempHour++;
                wednesday[tempHour][tempIndex] = "Class";
            }
        }
        if((temp = scheduleText.indexOf('R')) > -1) {
            thursday[startHour][firstIndex]= "Class";
            thursday[endHour][secondIndex]= "Class";

            int fillMinute = ((endHour - startHour) * 60  + (endMinute - startMinute))/15;
            for(int i = 1; i < fillMinute; i++) {
                int tempIndex = (firstIndex + i) % 4;
                int tempHour = startHour;
                if((firstIndex + i) / 4 > 0) tempHour++;
                thursday[tempHour][tempIndex] = "Class";
            }
        }
        if((temp = scheduleText.indexOf('F')) > -1) {
            friday[startHour][firstIndex]= "Class";
            friday[endHour][secondIndex]= "Class";

            int fillMinute = ((endHour - startHour) * 60  + (endMinute - startMinute))/15;
            for(int i = 1; i < fillMinute; i++) {
                int tempIndex = (firstIndex + i) % 4;
                int tempHour = startHour;
                if((firstIndex + i) / 4 > 0) tempHour++;
                friday[tempHour][tempIndex] = "Class";
            }
        }
    }

    /*
     * return true if time is not already occupied
     */
    public boolean validate(String scheduleText) {

        if(scheduleText.equals("")) return true;

        int startHour,endHour;
        int startMinute;
        int endMinute;

        int temp = scheduleText.indexOf('-');
        char timeFrame;

        //class starting hour
        int startPoint = scheduleText.indexOf(']');
        int endPoint = scheduleText.indexOf(':');
        startHour = Integer.parseInt(scheduleText.substring(startPoint+1,endPoint));

        if((timeFrame = scheduleText.charAt(scheduleText.indexOf('m') - 1)) == 'p') {
           if(startHour != 12) startHour+=12;
        }

        //class starting minute
        startPoint = endPoint;
        endPoint = scheduleText.indexOf(timeFrame) - 1;
        startMinute = Integer.parseInt(scheduleText.substring(startPoint+1,endPoint));

        //class ending hour
        startPoint = scheduleText.indexOf('-');
        endPoint = scheduleText.lastIndexOf(':');
        endHour = Integer.parseInt(scheduleText.substring(startPoint+2,endPoint));

        if((timeFrame = scheduleText.charAt(scheduleText.lastIndexOf('m') - 1)) == 'p') {
           if(endHour != 12) endHour+=12;
        }

        //class ending minute
        startPoint = endPoint + 1;
        endPoint = scheduleText.lastIndexOf(timeFrame) - 1;
        endMinute = Integer.parseInt(scheduleText.substring(startPoint,endPoint));

        temp = 0;
        int firstIndex = 0, secondIndex = 0;
        switch(startMinute) {
            case 0 : firstIndex = 0;
                break;
            case 15 : firstIndex = 1;
                break;
            case 30 : firstIndex = 2;
                break;
            case 45 : firstIndex = 3;
                break;
        }

        switch(endMinute) {
            case 0 : secondIndex = 0;
                break;
            case 15 : secondIndex = 1;
                break;
            case 30 : secondIndex = 2;
                break;
            case 45 : secondIndex = 3;
                break;
        }
       // if((temp = scheduleText.indexOf('M')) > -1 && !monday[startHour][firstIndex].equals("") && !monday[endHour][secondIndex].equals("")) {
        if((temp = scheduleText.indexOf('M')) > -1) {
            int fillMinute = ((endHour - startHour) * 60  + (endMinute - startMinute))/15;
            for(int i = 1; i < fillMinute; i++) {
                int tempIndex = (firstIndex + i) % 4;
                int tempHour = startHour;
                if((firstIndex + i) / 4 > 0) tempHour++;
                if(!monday[tempHour][tempIndex].equals(""))
                    return false;
            }
            return true;
        }
        //if((temp = scheduleText.indexOf('T')) > -1 && !tuesday[startHour][firstIndex].equals("") && !tuesday[endHour][secondIndex].equals("")) {
        if((temp = scheduleText.indexOf('T')) > -1) {int fillMinute = ((endHour - startHour) * 60  + (endMinute - startMinute))/15;
            for(int i = 1; i < fillMinute; i++) {
                int tempIndex = (firstIndex + i) % 4;
                int tempHour = startHour;
                if((firstIndex + i) / 4 > 0) tempHour++;
                if(!tuesday[tempHour][tempIndex].equals(""))
                    return false;
            }
            return true;
        }
        //if((temp = scheduleText.indexOf('W')) > -1 && !wednesday[startHour][firstIndex].equals("") && !wednesday[endHour][secondIndex].equals("")) {
        if((temp = scheduleText.indexOf('W')) > -1){
            int fillMinute = ((endHour - startHour) * 60  + (endMinute - startMinute))/15;
            for(int i = 1; i < fillMinute; i++) {
                int tempIndex = (firstIndex + i) % 4;
                int tempHour = startHour;
                if((firstIndex + i) / 4 > 0) tempHour++;
                if(!wednesday[tempHour][tempIndex].equals(""))
                    return false;
            }
            return true;
        }
        //if((temp = scheduleText.indexOf('R')) > -1 && !thursday[startHour][firstIndex].equals("") && !thursday[endHour][secondIndex].equals("")) {
        if((temp = scheduleText.indexOf('R')) > -1){
            int fillMinute = ((endHour - startHour) * 60  + (endMinute - startMinute))/15;
            for(int i = 1; i < fillMinute; i++) {
                int tempIndex = (firstIndex + i) % 4;
                int tempHour = startHour;
                if((firstIndex + i) / 4 > 0) tempHour++;
                if(!thursday[tempHour][tempIndex].equals(""))
                    return false;
            }
            return true;
        }
        //if((temp = scheduleText.indexOf('F')) > -1 && !friday[startHour][firstIndex].equals("") && !friday[endHour][secondIndex].equals("")) {
        if((temp = scheduleText.indexOf('F')) > -1){
            int fillMinute = ((endHour - startHour) * 60  + (endMinute - startMinute))/15;
            for(int i = 1; i < fillMinute; i++) {
                int tempIndex = (firstIndex + i) % 4;
                int tempHour = startHour;
                if((firstIndex + i) / 4 > 0) tempHour++;
                if(!friday[tempHour][tempIndex].equals(""))
                    return false;
            }
            return true;
        }

        return true;
    }

    public void addSchedule(String scheduleText, String courseTitle, String courseProfessor) {

        String professor;
        if(courseProfessor.equals("")) professor = "";
        else {
            //professor = "(" + courseProfessor.split(" ")[0] +" "+ courseProfessor.substring(courseProfessor.lastIndexOf(" ")+1,courseProfessor.lastIndexOf(" ")+2)+ ")";
            professor = "";
        }

        int startHour,endHour;
        int startMinute;
        int endMinute;

        int temp = scheduleText.indexOf('-');
        String timeFrame;

        //class starting hour
        int startPoint = scheduleText.indexOf(']');
        int endPoint = scheduleText.indexOf(':');
        startHour = Integer.parseInt(scheduleText.substring(startPoint+1,endPoint));

        if((timeFrame = scheduleText.substring(temp-3,temp-1)).contains("p") && startHour != 12) {

            startHour+=12;
        }

        //class starting minute
        startPoint = endPoint;
        endPoint = scheduleText.indexOf(timeFrame) - 1;
        startMinute = Integer.parseInt(scheduleText.substring(startPoint+1,endPoint));

        //class ending hour
        startPoint = scheduleText.indexOf('-');
        endPoint = scheduleText.lastIndexOf(':');
        endHour = Integer.parseInt(scheduleText.substring(startPoint+2,endPoint));

        if((timeFrame = scheduleText.substring(temp+7,temp+9)).contains("p") && endHour != 12) {
            endHour+=12;
        }

        //class ending minute
        startPoint = endPoint + 1;
        endPoint = scheduleText.lastIndexOf(timeFrame.trim()) - 1;
        endMinute = Integer.parseInt(scheduleText.substring(startPoint,endPoint));

        temp = 0;
        int firstIndex = 0, secondIndex = 0;
        switch(startMinute) {
            case 0 : firstIndex = 0;
                break;
            case 15 : firstIndex = 1;
                break;
            case 30 : firstIndex = 2;
                break;
            case 45 : firstIndex = 3;
                break;
        }

        switch(endMinute) {
            case 0 : secondIndex = 0;
                break;
            case 15 : secondIndex = 1;
                break;
            case 30 : secondIndex = 2;
                break;
            case 45 : secondIndex = 3;
                break;
        }
        if((temp = scheduleText.indexOf('M')) > -1) {
            monday[startHour][firstIndex]= courseTitle +  professor ;
            monday[endHour][secondIndex]= "Class";

            int fillMinute = ((endHour - startHour) * 60  + (endMinute - startMinute))/15;
            for(int i = 1; i < fillMinute; i++) {
                int tempIndex = (firstIndex + i) % 4;
                int tempHour = startHour;
                if((firstIndex + i) / 4 > 0) tempHour++;
                monday[tempHour][tempIndex] = "Class";
            }
    //        monday[endHour][secondIndex]= courseTitle + professor;
        }
        if((temp = scheduleText.indexOf('T')) > -1) {
            tuesday[startHour][firstIndex]= courseTitle + professor;
            tuesday[endHour][secondIndex]= "Class";

            int fillMinute = ((endHour - startHour) * 60  + (endMinute - startMinute))/15;
            for(int i = 1; i < fillMinute; i++) {
                int tempIndex = (firstIndex + i) % 4;
                int tempHour = startHour;
                if((firstIndex + i) / 4 > 0) tempHour++;
                tuesday[tempHour][tempIndex] = "Class";
            }
  //          tuesday[endHour][secondIndex]= courseTitle + professor;
        }
        if((temp = scheduleText.indexOf('W')) > -1) {
            wednesday[startHour][firstIndex]= courseTitle + professor;
            wednesday[endHour][secondIndex]= "Class";

            int fillMinute = ((endHour - startHour) * 60  + (endMinute - startMinute))/15;
            for(int i = 1; i < fillMinute; i++) {
                int tempIndex = (firstIndex + i) % 4;
                int tempHour = startHour;
                if((firstIndex + i) / 4 > 0) tempHour++;
                wednesday[tempHour][tempIndex] = "Class";
            }
     //       wednesday[endHour][secondIndex]= courseTitle + professor;
        }
        if((temp = scheduleText.indexOf('R')) > -1) {
            thursday[startHour][firstIndex]= courseTitle + professor;
            thursday[endHour][secondIndex]= "Class";

            int fillMinute = ((endHour - startHour) * 60  + (endMinute - startMinute))/15;
            for(int i = 1; i < fillMinute; i++) {
                int tempIndex = (firstIndex + i) % 4;
                int tempHour = startHour;
                if((firstIndex + i) / 4 > 0) tempHour++;
                thursday[tempHour][tempIndex] = "Class";
            }
    //        thursday[endHour][secondIndex]= courseTitle + professor;
        }
        if((temp = scheduleText.indexOf('F')) > -1) {
            friday[startHour][firstIndex]= courseTitle + professor;
            friday[endHour][secondIndex]= "Class";

            int fillMinute = ((endHour - startHour) * 60  + (endMinute - startMinute))/15;
            for(int i = 1; i < fillMinute; i++) {
                int tempIndex = (firstIndex + i) % 4;
                int tempHour = startHour;
                if((firstIndex + i) / 4 > 0) tempHour++;
                friday[tempHour][tempIndex] = "Class";
            }
      //      friday[endHour][secondIndex]= courseTitle +  professor;
        }
    }

    public void setting(AutoResizeTextView[] monday, AutoResizeTextView[] tuesday, AutoResizeTextView[] wednesday, AutoResizeTextView[] thursday, AutoResizeTextView[] friday, Context context) {
        int max_length = 0;
        String maxString = "";
        for(int i = 8; i < 22; i++){
            for(int j = 0; j < 4; j++) {
                if(this.monday[i][j].length()>max_length) {
                    max_length = this.monday[i][j].length();
                    maxString = this.monday[i][j];
                }
                if(this.tuesday[i][j].length()>max_length) {
                    max_length = this.tuesday[i][j].length();
                    maxString = this.tuesday[i][j];
                }
                if(this.wednesday[i][j].length()>max_length) {
                    max_length = this.wednesday[i][j].length();
                    maxString = this.wednesday[i][j];
                }
                if(this.thursday[i][j].length()>max_length) {
                    max_length = this.thursday[i][j].length();
                    maxString = this.thursday[i][j];
                }
                if(this.friday[i][j].length()>max_length) {
                    max_length = this.friday[i][j].length();
                    maxString = this.friday[i][j];
                }
            }
        }

        for (int i = 8; i < 22; ++i) {
            for (int j = 0; j < 4; ++j) {
                if(!this.monday[i][j].equals("") && !this.monday[i][j].equals("Class")) {
                    monday[i].setTextColor(ContextCompat.getColor(context, R.color.colorAnnie));
                    monday[i].setText(this.monday[i][j]);
                }
                else {
                    monday[i].setText(maxString);
                }
                if(!this.tuesday[i][j].equals("") && !this.tuesday[i][j].equals("Class")) {
                    tuesday[i].setTextColor(ContextCompat.getColor(context, R.color.colorAnnie));
                    tuesday[i].setText(this.tuesday[i][j]);
                }
                else {
                    tuesday[i].setText(maxString);
                }
                if(!this.wednesday[i][j].equals("") && !this.wednesday[i][j].equals("Class")) {
                    wednesday[i].setTextColor(ContextCompat.getColor(context, R.color.colorAnnie));
                    wednesday[i].setText(this.wednesday[i][j]);
                }
                else {
                    wednesday[i].setText(maxString);
                }
                if(!this.thursday[i][j].equals("") && !this.thursday[i][j].equals("Class")) {
                    thursday[i].setTextColor(ContextCompat.getColor(context, R.color.colorAnnie));
                    thursday[i].setText(this.thursday[i][j]);
                }
                else {
                    thursday[i].setText(maxString);
                }
                if(!this.friday[i][j].equals("") && !this.friday[i][j].equals("Class")) {
                    friday[i].setTextColor(ContextCompat.getColor(context, R.color.colorAnnie));
                    friday[i].setText(this.friday[i][j]);
                }
                else {
                    friday[i].setText(maxString);
                }
                monday[i].resizeText();
                tuesday[i].resizeText();
                wednesday[i].resizeText();
                thursday[i].resizeText();
                friday[i].resizeText();
            }
        }
    }
}
