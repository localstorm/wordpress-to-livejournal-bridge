package org.localstorm.feeds;

import com.sun.syndication.feed.synd.SyndCategory;
import com.sun.syndication.feed.synd.SyndContent;

import java.util.*;

/**
 * @author localstorm
 *         Date: 30.12.13
 */
public abstract class AbstractBlogSource implements BlogSource {

    private long fetchTime = 0L;
    private List<BlogEntry> entries = new ArrayList<>();
    private Set<String> ignoreTags = new HashSet<>();
    private long cacheTTL;

    public Set<String> getIgnoreTags() {
        return ignoreTags;
    }

    @Override
    public void setCacheTTL(long ttlMillis) {
        this.cacheTTL = ttlMillis;
    }

    @Override
    public List<BlogEntry> read(int depth) throws Exception {
        List<BlogEntry> entries = read();
        if (entries.size() > depth) {
            entries = entries.subList(0, depth);
        }
        return entries;
    }

    @Override
    public void setIgnoreTags(Set<String> ignoreTags) {
        this.ignoreTags = ignoreTags;
    }

    protected abstract List<BlogEntry> readDirect() throws Exception;

    @Override
    public List<BlogEntry> read() throws Exception {
        List<BlogEntry> entries = readCached();
        if (entries == null) {
            return cache(readDirect());
        } else {
            return entries;
        }
    }

    protected List<BlogEntry> cache(List<BlogEntry> entries) {
        this.entries.clear();
        this.entries.addAll(entries);
        this.fetchTime = System.currentTimeMillis();
        return entries;
    }

    protected List<BlogEntry> readCached() {
        if (System.currentTimeMillis() - cacheTTL <= fetchTime) {
            return this.entries;
        } else {
            return null;
        }
    }

    protected List<String> toTags(List<SyndCategory> categories) {
        List<String> tags = new ArrayList<>();
        for (SyndCategory cat : categories) {
            if (cat.getName() != null && !ignoreTags.contains(cat.getName())) {
                tags.add(cat.getName());
            }
        }
        return tags;
    }

    protected String toBody(List<SyndContent> contents) {
        StringBuilder sb = new StringBuilder();
        for (SyndContent sc : contents) {
            if (sb.length() > 0) {
                sb.append("\n");
            }
            sb.append(sc.getValue());
        }
        return sb.toString();
    }

}
