package org.localstorm.feeds.decor;

import org.localstorm.feeds.BlogEntry;
import org.localstorm.feeds.EntryDecorator;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author localstorm
 *         Date: 30.12.13
 */
public class RestrictTagsDecoration implements EntryDecorator {

    private final Set<String> restrictedTags;

    public RestrictTagsDecoration(List<String> restrictedTags) {
        this.restrictedTags = new HashSet<>(restrictedTags);
    }

    @Override
    public BlogEntry decorate(BlogEntry e) {
        for (String tag: e.getTags()) {
            if (restrictedTags.contains(tag)) {
                return null;
            }
        }

        return e;
    }
}
