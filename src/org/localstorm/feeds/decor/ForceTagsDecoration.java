package org.localstorm.feeds.decor;

import org.localstorm.feeds.BlogEntry;
import org.localstorm.feeds.EntryDecorator;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * @author localstorm
 *         Date: 30.12.13
 */
public class ForceTagsDecoration implements EntryDecorator {

    private final String[] tags;

    public ForceTagsDecoration(String... tags) {
        this.tags = tags;
    }

    @Override
    public BlogEntry decorate(BlogEntry e) {
        e = e.clone();
        e.setTags(new ArrayList<>(Arrays.asList(tags)));
        return e;
    }
}
