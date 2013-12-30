package org.localstorm.feeds;

/**
 * @author localstorm
 *         Date: 30.12.13
 */
public abstract class AbstractBlogPoster implements BlogPoster {

    @Override
    public boolean post(BlogEntry e, EntryDecorator decor) throws Exception {
        if (e != null) {
            if (decor != null) {
                e = decor.decorate(e);
            }
            if (e != null) {
                postDirect(e);
                return true;
            }
        }
        return false;
    }

    protected abstract void postDirect(BlogEntry e);

}
