package org.localstorm.feeds.decor;

import org.localstorm.feeds.BlogEntry;
import org.localstorm.feeds.EntryDecorator;

/**
 * @author localstorm
 *         Date: 30.12.13
 */
public class TitlePrefixDecoration implements EntryDecorator {

    private final String prefix;

    public TitlePrefixDecoration(String prefix) {
        this.prefix = prefix;
    }

    @Override
    public BlogEntry decorate(BlogEntry e) {
        String title = e.getTitle();
        if (title == null || title.trim().length() == 0) {
            return e;
        }
        if (title.startsWith(prefix)) {
            return e;
        }

        title = prefix + title;
        BlogEntry clon = e.clone();
        clon.setTitle(title);
        return clon;
    }

}
