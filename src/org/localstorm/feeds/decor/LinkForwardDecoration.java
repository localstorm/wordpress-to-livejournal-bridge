package org.localstorm.feeds.decor;

import org.localstorm.feeds.BlogEntry;
import org.localstorm.feeds.EntryDecorator;

/**
 * @author localstorm
 *         Date: 30.12.13
 */
public class LinkForwardDecoration implements EntryDecorator {

    @Override
    public BlogEntry decorate(BlogEntry e) {
        e = e.clone();
        e.setBody(e.getLink());
        return e;
    }
}
