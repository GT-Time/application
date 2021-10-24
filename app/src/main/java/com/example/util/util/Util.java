package com.example.util.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 *  Collection of utility function
 */
public class Util {
    public static String[] split(String input, String regex, int limit) {
        String[] pair = input.split(regex,limit);

        String[] returnArr = new String[limit];

        if(pair.length <= 1) {
            for(int i = 0; i < limit; i++) returnArr[i] = "";
            return returnArr;
        }

        return pair;
    }

    public static int parseInt(String strNum) {
        int returnVal = 0;
        if (strNum == null) {
            return returnVal;
        }

        try {
            returnVal = (int) Double.parseDouble(strNum.trim());
        } catch (NumberFormatException nfe) {
            returnVal = 0;
        }

        return returnVal;
    }

    public static String getFileName(String filename) {
        return filename + ".json";
    }

    /*
     * Read local file content
     * @param Context Where to retrieve local json directory from
     * @param String json filename to be fetched
     * @return String json content
     */
    public static String fetchLocalFile(File file) {
        try {
            file.createNewFile(); // if file already exists will do nothing
            FileReader fileReader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            StringBuilder stringBuilder = new StringBuilder();
            String line = bufferedReader.readLine();
            while (line != null) {
                stringBuilder.append(line).append("\n");
                line = bufferedReader.readLine();
            }
            bufferedReader.close();
            String response = stringBuilder.toString();
            return response;
        } catch (IOException io) {
            io.printStackTrace();
        }

        return "";
    }

    /*
     * Read remote content
     * @param String url to fetch content from
     * @return String fetched content
     */
    public static String fetchRemote(String target) {
        try {
            URL url = new URL(target);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            InputStream inputStream = httpURLConnection.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
            String temp;
            StringBuilder stringBuilder = new StringBuilder();
            while ((temp = br.readLine()) != null) {
                stringBuilder.append(temp + "\n");
            }
            br.close();
            inputStream.close();
            httpURLConnection.disconnect();
            return stringBuilder.toString();
        }

        catch(Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
