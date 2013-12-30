package org.localstorm.feeds;

/**
 * @author localstorm
 *         Date: 30.12.13
 */
public interface BlogPoster {
    public void post(BlogEntry e, EntryDecorator decorator) throws Exception;
}
