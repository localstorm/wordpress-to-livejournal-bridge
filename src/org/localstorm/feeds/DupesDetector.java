package org.localstorm.feeds;

/**
 * @author localstorm
 *         Date: 30.12.13
 */
public interface DupesDetector {

    public boolean needsPublishing(BlogEntry be, BlogSource targetBlog, int depth) throws Exception;

}
