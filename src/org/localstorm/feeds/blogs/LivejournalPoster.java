package org.localstorm.feeds.blogs;

import org.katkov.lj.ClientsFactory;
import org.katkov.lj.XMLRPCClient;
import org.katkov.lj.xmlrpc.arguments.PostEventArgument;
import org.localstorm.feeds.AbstractBlogPoster;
import org.localstorm.feeds.BlogEntry;

import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * @author localstorm
 *         Date: 30.12.13
 */
public class LivejournalPoster extends AbstractBlogPoster {

    private XMLRPCClient rpcClient = ClientsFactory.getXMLRPCClient();
    private String user;
    private String password;

    public LivejournalPoster(String user, String password) {
        this.user = user;
        this.password = password;
    }

    @Override
    protected void postDirect(BlogEntry e) {
        PostEventArgument pea = new PostEventArgument();

        Date d = (e.getPublishDate() !=null ) ? e.getPublishDate() : new Date();
        pea.setYear(year(d));
        pea.setMon(month(d));
        pea.setDay(day(d));
        pea.setHour(hour(d));
        pea.setMin(min(d));

        pea.setUsername(user);
        pea.setPassword(password);
        pea.setSubject(e.getTitle());
        pea.setEvent(e.getBody());
        pea.setProps(Collections.singletonMap("taglist", toCsv(e.getTags())));

        System.out.println("Posting: " + e);
        rpcClient.postevent(pea, 10000);
    }

    private String toCsv(List<String> tags) {
        StringBuilder csv = new StringBuilder();
        for (String tag: tags) {
            if (csv.length()>0) {
                csv.append(",");
            }
            csv.append(tag);
        }
        return csv.toString();
    }

    private int year(Date date) {
        return date.getYear() + 1900;
    }

    private int month(Date date) {
        return date.getMonth()+1;
    }

    private int day(Date date) {
        return date.getDate();
    }

    private int hour(Date date) {
        return date.getHours();
    }

    private int min(Date date) {
        return date.getMinutes();
    }

}
