package myjava.myutil;

import org.joda.time.DateTime;

public class Time {
    public static void main(String[] args) {
        DateTime date = new DateTime(System.currentTimeMillis());
        System.out.println(date.withTimeAtStartOfDay().plusHours(date.getHourOfDay()));
    }
}
