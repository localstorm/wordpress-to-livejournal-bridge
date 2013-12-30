package org.localstorm.feeds;

import java.util.Date;
import java.util.List;

/**
 * @author localstorm
 *         Date: 30.12.13
 */
public class BlogEntry implements Cloneable {
    private String title;
    private String body;
    private List<String> tags;
    private String link;
    private Date publishDate;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public Date getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(Date publishDate) {
        this.publishDate = publishDate;
    }

    @Override
    public BlogEntry clone() {
        try {
            return (BlogEntry) super.clone();
        }catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("BlogEntry{");
        sb.append("title='").append(title).append('\'');
        sb.append(", body='").append(body).append('\'');
        sb.append(", tags=").append(tags);
        sb.append(", link='").append(link).append('\'');
        sb.append(", publishDate=").append(publishDate);
        sb.append('}');
        return sb.toString();
    }

    public String toString(Mode mode) {
        final StringBuilder sb = new StringBuilder("BlogEntry{");
        if (mode.ordinal() >= Mode.MINIMAL.ordinal()) {
            sb.append("link='").append(link).append('\'');
            sb.append(", publishDate=").append(publishDate);
        }
        if (mode.ordinal() >= Mode.BRIEF.ordinal()) {
           sb.append(", title='").append(title).append('\'');
        }
        if (mode.ordinal() >= Mode.FULL.ordinal()) {
            sb.append(", tags=").append(tags);
            sb.append(", body='").append(body).append('\'');

        }
        sb.append('}');
        return sb.toString();
    }
}
