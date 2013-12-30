package org.localstorm.feeds.decor;

import org.localstorm.feeds.BlogEntry;
import org.localstorm.feeds.EntryDecorator;

/**
 * @author localstorm
 *         Date: 30.12.13
 */
public class RegExTitleDecoration implements EntryDecorator {

    private String regex;
    private String replacement;

    public RegExTitleDecoration(String regex, String replacement) {
        this.regex = regex;
        this.replacement = replacement;
    }

    @Override
    public BlogEntry decorate(BlogEntry e) {
        String title = e.getTitle();
        if (title == null || title.trim().length() == 0) {
            return e;
        }

        BlogEntry clon = e.clone();
        clon.setTitle(title.replaceAll(regex, replacement));
        return clon;
    }

}
