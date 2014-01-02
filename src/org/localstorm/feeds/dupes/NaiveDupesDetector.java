package org.localstorm.feeds.dupes;

import org.localstorm.feeds.BlogEntry;
import org.localstorm.feeds.BlogSource;
import org.localstorm.feeds.DupesDetector;

import java.util.List;

import static org.localstorm.feeds.util.TextUtil.textSimilarPct;
import static org.localstorm.feeds.util.TextUtil.toPlainText;

/**
 * @author localstorm
 *         Date: 30.12.13
 */
public class NaiveDupesDetector implements DupesDetector {

    @Override
    public boolean needsPublishing(BlogEntry be, BlogSource targetBlog, int depth) throws Exception {
        List<BlogEntry> entries = targetBlog.read(depth);
        for (BlogEntry e : entries) {
            if (isReplicaOf(be, e)) {
                return false;
            }
        }
        return true;
    }

    private boolean isReplicaOf(BlogEntry candidate, BlogEntry found) {
        return linkForwarding(candidate, found) ||
               snippetProximity(candidate, found) ||
               contentProximity(candidate, found);
    }

    private boolean snippetProximity(BlogEntry candidate, BlogEntry found) {
        String tx1 = (candidate.getBody() != null) ? candidate.getBody() : "";
        String tx2 = (found.getBody() != null) ? found.getBody() : "";
        tx1 = toPlainText(tx1);
        tx2 = toPlainText(tx2);
        if (tx1.length() > tx2.length()) {
            String tx = tx1;
            tx1 = tx2;
            tx2 = tx;
        }

        tx2 = tx2.substring(0, tx1.length());
        if (tx1.isEmpty() != tx2.isEmpty()) {
            return false;
        } else {
            return textSimilarPct(tx1, tx2, 80);
        }
    }

    private boolean contentProximity(BlogEntry candidate, BlogEntry found) {
        String tx1 = (candidate.getBody() != null) ? candidate.getBody() : "";
        String tx2 = (found.getBody() != null) ? found.getBody() : "";
        tx1 = toPlainText(tx1);
        tx2 = toPlainText(tx2);
        if (tx1.isEmpty() != tx2.isEmpty()) {
            return false;
        } else {
            return textSimilarPct(tx1, tx2, 80);
        }
    }

    private boolean linkForwarding(BlogEntry candidate, BlogEntry found) {
        String body = found.getBody();
        if (body == null) {
            return false;
        } else {
            String candLink = noHttps(candidate.getLink());
            String foundBody = noHttps(found.getBody());
            boolean crossLink = foundBody.contains(candLink);
            if (crossLink) {
                String exceptLink = foundBody.replace(candLink, "").trim();
                exceptLink = compress(cleanHtml(exceptLink));
                String targetTitleCompressed = compress(found.getTitle());
                if (exceptLink.length() <= targetTitleCompressed.length()) {
                    return true;
                }
            }
        }
        return false;
    }

    private String cleanHtml(String txt) {
        txt = txt.replaceAll("href", "");
        txt = txt.replaceAll("target_blank", "");
        txt = txt.replaceAll("target_self", "");
        txt = txt.replaceAll("relnofollow", "");
        return txt;
    }

    private String noHttps(String link) {
        return link.replaceAll("[hH][tT][tT][pP][sS]://", "http://");
    }

    private String compress(String exceptLink) {
        return exceptLink.replaceAll("\\p{Punct}", "").replaceAll("\\p{Space}", "");
    }
}
