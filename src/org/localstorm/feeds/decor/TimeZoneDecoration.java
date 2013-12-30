package org.localstorm.feeds.decor;

import org.localstorm.feeds.BlogEntry;
import org.localstorm.feeds.EntryDecorator;

import java.util.Date;
import java.util.TimeZone;

/**
 * @author localstorm
 *         Date: 30.12.13
 */
public class TimeZoneDecoration implements EntryDecorator {

    private int delta = 0;

    public TimeZoneDecoration(String targetTz) {
        int off1 = TimeZone.getDefault().getRawOffset();
        TimeZone tz = TimeZone.getTimeZone(targetTz);
        if (tz != null) {
            int off2 = tz.getRawOffset();
            delta = off2 - off1;
        }
    }

    @Override
    public BlogEntry decorate(BlogEntry e) {
        if (e.getPublishDate() == null) {
            e.setPublishDate(new Date());
        }
        long millis = e.getPublishDate().getTime();
        millis += delta;
        e.setPublishDate(new Date(millis));
        return e;
    }

}
