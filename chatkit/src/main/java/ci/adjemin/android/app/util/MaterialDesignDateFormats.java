package ci.adjemin.android.app.util;



import android.util.Pair;

import org.joda.time.*;

import java.util.Locale;

/**
 * Created by angebagui on 06/02/2016.
 */
public class MaterialDesignDateFormats {



    public static String display(long millis, Locale locale){
        final DateTime dateTime = new DateTime(millis);
        if (isSameYear(dateTime)){
            if (isSameMonth(dateTime)){
                if(isSameDay(dateTime)){
                    if(isCurrentMinute(dateTime)){
                        if(locale.equals(Locale.FRENCH) || locale.equals(Locale.FRANCE) || locale.equals(Locale.CANADA_FRENCH)){
                            return "A l'instant";
                        }else{
                            return "Just now";
                        }
                    }else if(isCurrentHour(dateTime)){

                        final int lostMinute = getLostMinute(dateTime);
                        if(lostMinute>1)
                            return lostMinute+" min";
                        return lostMinute+" mins";

                    }else{
                        final int lostHour = getLostHour(dateTime);
                        if(locale.equals(Locale.FRENCH) || locale.equals(Locale.FRANCE) || locale.equals(Locale.CANADA_FRENCH)){
                            return lostHour+" h";
                        }else{
                            if (lostHour>1){
                                return lostHour+" hrs";
                            }
                            return lostHour+" hr";

                        }
                    }
                }else if(isYesterday(dateTime)){
                    if(locale.equals(Locale.FRENCH) || locale.equals(Locale.FRANCE) || locale.equals(Locale.CANADA_FRENCH)){
                        return "Hier, à ".concat(Utils.get(dateTime.getHourOfDay())).concat(":").concat(Utils.get(dateTime.getMinuteOfHour()));
                    }else{
                        return "Yesterday, at ".concat(Utils.get(dateTime.getHourOfDay())).concat(":").concat(Utils.get(dateTime.getMinuteOfHour()));
                    }
                }else{
                    return futureContext(millis,locale);
                }
            }else{
                return futureContext(millis,locale);
            }
        }else{
            return distancePastContext(millis,locale).concat(" ").concat(String.valueOf(dateTime.getYear()));
        }
    }

    private static int getLostHour(DateTime dateTime) {
        int dateHour = dateTime.getHourOfDay();
        return DateTime.now().getHourOfDay() - dateHour;
    }

    private static int getLostMinute(DateTime dateTime) {
        int dateMinute = dateTime.getMinuteOfHour();
        return DateTime.now().getMinuteOfHour() - dateMinute;
    }


    //Time and date ranges
    /**
     * Date and time ranges are formatted based on the following:
     * The year is the same on both sides of the range
     * It’s the current year on both sides of the range
     * Whether both times have the same AM or PM
     */

    /**
     *
     * Separate a range of dates or times with an en dash, without spaces.
     * Add spaces when spelling out months, or to remove ambiguity.
     *
     * 8:00 AM–12:30 PM
     * 6 Jan – 2 Feb
     *
     * @param dateDepart
     * @param dateArrivee
     * @return
     */
    public static String enDashRangeElement(long dateDepart,long dateArrivee){
        StringBuilder builder = new StringBuilder();

        return builder.toString();
    }
    //  Date and time modifications by context
    /**
     * Include time to a future day or date : 10 Jan, 08:00
     *
     * @param millis
     * @return
     */
    public static String futureContext(long millis, Locale locale){
        StringBuilder builder = new StringBuilder();
        DateTime dateTime = new DateTime(millis);
        YearMonth yearMonth= YearMonth.fromDateFields(dateTime.toDate());
        builder.append(Utils.get(dateTime.getDayOfMonth()));
        builder.append(" ");
        builder.append(yearMonth.monthOfYear().getAsShortText(locale));
        builder.append(",");
        builder.append(" ");
        builder.append(Utils.get(dateTime.getHourOfDay()));
        builder.append(":");
        builder.append(Utils.get(dateTime.getMinuteOfHour()));


        return builder.toString();
    }
    /**
     * When referring to a past time, display both date and time. : Reminded Jan 5, 7:16 AM
     *
     * @param millis
     * @return
     */
    public static String pastContext(long millis,Locale locale){
        StringBuilder builder = new StringBuilder();
        DateTime dateTime = new DateTime(millis);
        YearMonth yearMonth= YearMonth.fromDateFields(dateTime.toDate());
        builder.append(yearMonth.monthOfYear().getAsShortText(locale));
        builder.append(" ");
        builder.append(Utils.get(dateTime.getDayOfMonth()));

        builder.append(",");
        builder.append(" ");
        builder.append(Utils.get(dateTime.getHourOfDay()));
        builder.append(":");
        builder.append(Utils.get(dateTime.getMinuteOfHour()));


        return builder.toString();
    }
    /**
     * Omit the time for events in the distant past : 3 Jan
     *
     * @param millis
     * @return
     */
    public static String distancePastContext(long millis, Locale locale){
        StringBuilder builder = new StringBuilder();
        DateTime dateTime = new DateTime(millis);
        YearMonth yearMonth= YearMonth.fromDateFields(dateTime.toDate());
        builder.append(Utils.get(dateTime.getDayOfMonth()));
        builder.append(" ");
        builder.append(yearMonth.monthOfYear().getAsShortText(locale));

        return builder.toString();
    }


    /**
     * When referring to a day of the week, such as for Calendar invites,
     * display the abbreviated day separated by a comma. : Mon, Jan 10, 8:00 AM
     *
     * @param millis
     * @return
     */
    public static String weekdayContext(long millis,Locale locale){
        StringBuilder builder = new StringBuilder();
        DateTime dateTime = new DateTime(millis);
        YearMonth yearMonth= YearMonth.fromDateFields(dateTime.toDate());

        builder.append(dateTime.dayOfWeek().getAsShortText(locale));
        builder.append(",");
        builder.append(" ");
        builder.append(yearMonth.monthOfYear().getAsShortText(locale));
        builder.append(" ");
        builder.append(Utils.get(dateTime.getDayOfMonth()));
        builder.append(",");
        builder.append(" ");
        if (locale.equals(Locale.ENGLISH)){
            builder.append(dateTime.getHourOfDay());
            builder.append(":");
            builder.append(Utils.get(dateTime.getMinuteOfHour()));
            builder.append(" ");
            // TODO:: Add AM or PM
            //DateTimeFormat.forPattern(pattern).withLocale(Locale.FRANCE).print(dt);
        }else {
            builder.append(Utils.get(dateTime.getHourOfDay()));
            builder.append(":");
            builder.append(Utils.get(dateTime.getMinuteOfHour()));
        }



        return builder.toString();
    }
    /**
     * Show the duration of a recording, like audio or video, in the format H:MM:SS.
     * Omit hours or seconds if they don’t apply.

     *Use the same format across the same context. A video labelled  “3:15” referring to
     *hours and minutes, should not use that time format later on that page to refer to
     *  minutes and seconds. : 0:30 1:01:05
     *
     * @param startMillis
     * @param endMillis
     * @return
     */
    public static Pair<String,String> durationContext(long startMillis, long endMillis){
        StringBuilder builder = new StringBuilder();
        DateTime dateTime = new DateTime(startMillis);
        builder.append(dateTime.getHourOfDay());
        builder.append(":");
        builder.append(Utils.get(dateTime.getMinuteOfHour()));
        if (dateTime.getSecondOfMinute()>0){
            builder.append(":");
            builder.append(Utils.get(dateTime.getSecondOfMinute()));
        }
        String startDate = builder.toString();

        DateTime dateTime1 = new DateTime(startMillis);
        StringBuilder builder1 = new StringBuilder();
        builder1.append(dateTime1.getHourOfDay());
        builder1.append(":");
        builder1.append(dateTime1.getMinuteOfHour());
        if (dateTime1.getSecondOfMinute()>0){
            builder1.append(":");
            builder1.append(Utils.get(dateTime1.getSecondOfMinute()));
        }
        String endDate = builder1.toString();
        return new Pair<String, String>(startDate,endDate);
    }

    private static boolean currentYear(long millis){
        DateTime dateTime = new DateTime(millis);
        Years dateYears = Years.years(dateTime.year().get());
        Years currentDateYears = Years.years(DateTime.now().year().get());
        return !dateYears.isGreaterThan(currentDateYears) || !dateYears.isLessThan(currentDateYears);
    }
    public static boolean isCurrentMinute(DateTime dateTime){
        return dateTime.getMinuteOfHour() == DateTime.now().getMinuteOfHour();
    }

    private static boolean isCurrentHour(DateTime dateTime) {
        return dateTime.getHourOfDay() == DateTime.now().getHourOfDay();
    }

    /**
     *  With current datetime
     * @param dateTime
     * @return
     */
    public static boolean isSameDay(DateTime dateTime){
        return  isSameDay(dateTime,DateTime.now());
    }
    public static boolean isSameDay(DateTime dateTime1, DateTime dateTime2){
        return  dateTime1.getDayOfMonth() == dateTime2.getDayOfMonth();
    }
    public static boolean isSameDay(long millis1, long millis2){
        return  isSameDay(new DateTime(millis1), new DateTime(millis2));
    }
    public static boolean isSameMonth(DateTime dateTime){
        DateTime dateNow = DateTime.now();
        return  dateTime.getMonthOfYear() == dateNow.getMonthOfYear();
    }

    public static boolean isSameYear(DateTime dateTime){
        return currentYear(dateTime.getMillis());

    }
    public static boolean isYesterday(DateTime dateTime){
        int currentDay = DateTime.now().getDayOfMonth();
        int yesterday = currentDay - 1;
        return dateTime.getDayOfMonth() == yesterday;
    }


    public static void main(String [] args){
        // millisecond to get 6 mins 1459718294000L with current millis = 1459718470257
        String date = display(1427915894000L, Locale.ENGLISH);

        System.out.print("Date: "+date);
    }
}
