package org.localstorm.feeds;

/**
 * @author localstorm
 *         Date: 30.12.13
 */
public abstract class AbstractBlogPoster implements BlogPoster {

    @Override
    public void post(BlogEntry e, EntryDecorator decor) throws Exception {
        if (e != null) {
            if (decor != null) {
                e = decor.decorate(e);
            }
            postDirect(e);
        }
    }

    protected abstract void postDirect(BlogEntry e);

}
