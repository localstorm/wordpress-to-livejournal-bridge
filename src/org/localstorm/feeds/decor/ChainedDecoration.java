package org.localstorm.feeds.decor;

import org.localstorm.feeds.BlogEntry;
import org.localstorm.feeds.EntryDecorator;

/**
 * @author localstorm
 *         Date: 30.12.13
 */
public class ChainedDecoration implements EntryDecorator {

    private final EntryDecorator[] chain;

    public ChainedDecoration(EntryDecorator ... chain) {
        this.chain = chain;
    }

    @Override
    public BlogEntry decorate(BlogEntry e) {
        for (EntryDecorator ed : chain) {
            e = ed.decorate(e);
        }
        return e;
    }
}
