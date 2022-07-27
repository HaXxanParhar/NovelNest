package com.arfideveloper.novelnest.utilities;

import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

/********** Developed HaXxan Parhar **********
 * Created by : usman hassan parhar on 13/08/2021 at 11:37 AM
 ******************************************************/


public class HaxxanTime {

    public static final int SECOND = 1000;
    public static final int MINUTE = 60000;
    public static final long HOUR = 3600000;
    public static final long DAY = 86400000;
    public static final long WEEK = 604800000;
    public static final long MONTH = 2629743000L;
    public static final long YEAR = 31556926000L;
    TimeZone timeZone;
    int standardTime;
    int dayLightSaving;
    int netTime;

    public HaxxanTime() {
        timeZone = TimeZone.getDefault();
        standardTime = timeZone.getRawOffset();
        dayLightSaving = timeZone.getDSTSavings();
        netTime = standardTime + dayLightSaving;
    }


    public String getTime(long milliseconds) {

        //setup the current time
        long currentTime = System.currentTimeMillis();
        //subtract the zone time from current time
        currentTime = currentTime + netTime;
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy-hh:mm a", Locale.UK);//UK time is UTC or 0 GMT
        String currentDate = dateFormat.format(new Date(currentTime));
        String[] separated;
        separated = currentDate.split("-");
        int currentDay = Integer.parseInt(separated[0]);
        int currentMonth = Integer.parseInt(separated[1]);
        int currentYear = Integer.parseInt(separated[2]);

        //changing to utc time according to relative timezone
        milliseconds = milliseconds + netTime;

        //setup the input time
        String inputDate = dateFormat.format(new Date(milliseconds));
        separated = inputDate.split("-");
        int inputDay = Integer.parseInt(separated[0]);
        int inputMonth = Integer.parseInt(separated[1]);
        int inputYear = Integer.parseInt(separated[2]);

        String output = "";
        long timeDiff = currentTime - milliseconds;
        //Same year
        if (timeDiff < YEAR) {

            //Same Month
            if (timeDiff < MONTH) {

                //Same Day
                if (timeDiff < DAY) {

                    //within Same Hour
                    if (timeDiff < HOUR) {

                        //within same minute
                        if (timeDiff < MINUTE) {

                            //seconds ago
                            int seconds = (int) (timeDiff / SECOND);
                            if (seconds < 30) {
                                output = "Just now";
                            } else {
                                output = seconds + " seconds ago";
                            }
                        } else {
                            //Minutes ago
                            int mins = (int) (timeDiff / MINUTE);
                            output = mins + " minutes ago";
                        }
                    } else {

                        //Hours ago
                        int hours = (int) (timeDiff / HOUR);
                        output = hours + " hours ago";
                    }

                } else { //Difference from 1 day to 1 month

                    //Same Week
                    if (timeDiff < WEEK) {
                        //Days ago
                        output = (timeDiff / DAY) + " days ago";
                    } else {
                        //Weeks ago
                        output = (timeDiff / WEEK) + " weeks ago";
                    }

                }
            } else {
                //Months ago
                output = (timeDiff / MONTH) + " months ago";
            }

        } else {
            //years ago
            output = (timeDiff / YEAR) + " years ago";
        }

        return output;
    }

    // API Time : 2021-05-11T09:10:14.000000Z
    public long getMillisecondsFromApiTime(String input) {
        Date output = new Date();
        try {
            input = input.split("\\.")[0];
            Log.e("YAM", "Input : " + input);
            String[] arr = input.split("T");
            String date = arr[0];
            Log.e("YAM", "Date : " + date);
            String time = arr[1];
            Log.e("YAM", "Time : " + time);
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
            output = dateFormat.parse(date + " " + time);
            return output.getTime() + netTime;
        } catch (Exception e) {
            Log.e("YAM", "Exception : " + e.getMessage());
            e.printStackTrace();
        }
        return output.getTime() + netTime;
    }

    /**
     * This method will return time from the API (created at) Time : 2021-05-11T09:10:14.000000Z
     */
    public String getTime(String time) {
        //get milliseconds from API (created at) timestamp
        long milliseconds = getMillisecondsFromApiTime(time);
        return getTime(milliseconds);
    }


}
