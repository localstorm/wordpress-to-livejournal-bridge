package org.localstorm.feeds;

/**
 * @author localstorm
 *         Date: 30.12.13
 */
public interface BlogPoster {
    public boolean post(BlogEntry e, EntryDecorator decorator) throws Exception;
}
