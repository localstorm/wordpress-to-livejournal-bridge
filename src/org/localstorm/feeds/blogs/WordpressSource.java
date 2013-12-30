package org.localstorm.feeds.blogs;

import com.sun.syndication.feed.synd.SyndContent;
import com.sun.syndication.feed.synd.SyndEntry;
import com.sun.syndication.feed.synd.SyndFeed;
import com.sun.syndication.fetcher.FeedFetcher;
import com.sun.syndication.fetcher.impl.FeedFetcherCache;
import com.sun.syndication.fetcher.impl.HashMapFeedInfoCache;
import com.sun.syndication.fetcher.impl.HttpURLFeedFetcher;
import org.localstorm.feeds.AbstractBlogSource;
import org.localstorm.feeds.BlogEntry;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * @author localstorm
 *         Date: 30.12.13
 */
public class WordpressSource extends AbstractBlogSource {

    private String rssUrl;

    public WordpressSource(String rss, long ttl) {
        this.rssUrl = rss;
        this.setCacheTTL(ttl);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<BlogEntry> readDirect() throws Exception {
        FeedFetcherCache feedInfoCache = HashMapFeedInfoCache.getInstance();
        FeedFetcher feedFetcher = new HttpURLFeedFetcher(feedInfoCache);
        SyndFeed feed = feedFetcher.retrieveFeed(new URL(rssUrl));

        ArrayList<BlogEntry> entries = new ArrayList<>();

        for (Object o: feed.getEntries()) {
            SyndEntry entry  = (SyndEntry) o;

            BlogEntry bentry = new BlogEntry();
            bentry.setTitle(entry.getTitle());
            bentry.setPublishDate(entry.getPublishedDate());
            bentry.setLink(entry.getLink());
            bentry.setBody(toBody((List<SyndContent>) entry.getContents()));
            bentry.setTags(toTags(entry.getCategories()));
            entries.add(bentry);
        }

        return entries;
    }
}
