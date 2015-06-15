package com.subinkrishna.android.common.util;

import android.content.Context;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import com.subinkrishna.android.common.util.R;

/**
 * Holds date/time related methods.
 *
 * @author Subinkrishna Gopi
 */
public class DateUtil {

    /**
     * Formats date (time in millis) to JUST NOW, TODAY / YESTERDAY format.
     *
     * @param context
     * @param timeInMillis
     * @param prefix
     * @return
     */
    public static String formatDate(final Context context,
                                    final long timeInMillis,
                                    final boolean prefix) {
        String formattedDate = null;

        // Date Formats
        final String A_DAY_BEFORE_YESTERDAY = "MMM d h:mm aaa";
        final String YESTERDAY_OR_TODAY = "h:mm aaa";
        final SimpleDateFormat formatter = new SimpleDateFormat(A_DAY_BEFORE_YESTERDAY);

        final boolean isToday = isToday(timeInMillis);
        final boolean isYesterday = isYesterday(timeInMillis);

        if (isJustNow(timeInMillis)) {
            // Just now
            formattedDate = context.getString(R.string.just_now);
        } else if (isToday || isYesterday) {
            // Today or yesterday
            formatter.applyPattern(YESTERDAY_OR_TODAY);
            formattedDate = formatter.format(new Date(timeInMillis));

            // Append Today/Yesterday if prefix is true
            if (prefix) {
                final String datePrefix = context.getString(isToday ? R.string.today : R.string.yesterday);
                formattedDate = datePrefix + " " + formattedDate;
            }
        } else {
            // Some other date
            formattedDate = formatter.format(new Date(timeInMillis));
        }

        return formattedDate;
    }

    public static boolean isJustNow(final long timeInMillis) {
        final Calendar now = Calendar.getInstance();
        now.add(Calendar.MINUTE, -15);
        final Calendar incoming = Calendar.getInstance();
        incoming.setTimeInMillis(timeInMillis);
        return incoming.after(now);
    }

    public static boolean isToday(final long timeInMillis) {
        final Calendar now = Calendar.getInstance();
        final Calendar incoming = Calendar.getInstance();
        incoming.setTimeInMillis(timeInMillis);
        return (now.get(Calendar.ERA) == incoming.get(Calendar.ERA)) &&
               (now.get(Calendar.YEAR) == incoming.get(Calendar.YEAR)) &&
               (now.get(Calendar.DAY_OF_YEAR) == incoming.get(Calendar.DAY_OF_YEAR));
    }

    public static boolean isYesterday(final long timeInMillis) {
        final Calendar yesterday = Calendar.getInstance();
        yesterday.add(Calendar.DAY_OF_YEAR, -1);
        final Calendar incoming = Calendar.getInstance();
        incoming.setTimeInMillis(timeInMillis);
        return (yesterday.get(Calendar.ERA) == incoming.get(Calendar.ERA)) &&
               (yesterday.get(Calendar.YEAR) == incoming.get(Calendar.YEAR)) &&
               (yesterday.get(Calendar.DAY_OF_YEAR) == incoming.get(Calendar.DAY_OF_YEAR));
    }

    public static boolean isThisWeek(final long timeInMillis) {
        final Calendar thisWeek = Calendar.getInstance();
        thisWeek.set(Calendar.HOUR_OF_DAY, 0);
        thisWeek.set(Calendar.MINUTE, 0);
        thisWeek.set(Calendar.SECOND, 0);
        thisWeek.set(Calendar.MILLISECOND, 0);
        thisWeek.add(Calendar.WEEK_OF_YEAR, -1);
        final Calendar incoming = Calendar.getInstance();
        incoming.setTimeInMillis(timeInMillis);
        return  !incoming.before(thisWeek);
    }

    public static boolean isOlderThanThisWeek(final long timeInMillis) {
        final Calendar thisWeek = Calendar.getInstance();
        thisWeek.set(Calendar.HOUR_OF_DAY, 0);
        thisWeek.set(Calendar.MINUTE, 0);
        thisWeek.set(Calendar.SECOND, 0);
        thisWeek.set(Calendar.MILLISECOND, 0);
        thisWeek.add(Calendar.WEEK_OF_YEAR, -1);
        final Calendar incoming = Calendar.getInstance();
        incoming.setTimeInMillis(timeInMillis);
        return  incoming.before(thisWeek);
    }

    public static boolean isOlderThanYesterday(final long timeInMillis) {
        final Calendar yesterday = Calendar.getInstance();
        yesterday.set(Calendar.HOUR_OF_DAY, 0);
        yesterday.set(Calendar.MINUTE, 0);
        yesterday.set(Calendar.SECOND, 0);
        yesterday.set(Calendar.MILLISECOND, 0);
        yesterday.add(Calendar.DAY_OF_YEAR, -1);
        final Calendar incoming = Calendar.getInstance();
        incoming.setTimeInMillis(timeInMillis);
        return  incoming.before(yesterday);
    }

}
