package org.localstorm.feeds;

import java.util.List;
import java.util.Set;

/**
 * @author localstorm
 *         Date: 30.12.13
 */
public interface BlogSource {
    public void setCacheTTL(long ttlMillis);
    public List<BlogEntry> read(int depth) throws Exception;
    public List<BlogEntry> read() throws Exception;
    public void setIgnoreTags(Set<String> ignoreTags);
}
