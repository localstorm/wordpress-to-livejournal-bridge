package org.localstorm.feeds.dupes;

import com.sun.org.apache.xpath.internal.SourceTree;
import org.localstorm.feeds.BlogEntry;
import org.localstorm.feeds.BlogSource;
import org.localstorm.feeds.DupesDetector;

import java.util.List;

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
        return linkForwarding(candidate, found) || contentProximity(candidate, found);
    }

    private boolean contentProximity(BlogEntry candidate, BlogEntry found) {
        String tx1 = (candidate.getBody() != null) ? candidate.getBody() : "";
        String tx2 = (found.getBody() != null) ? found.getBody() : "";
        if (tx1.isEmpty() != tx2.isEmpty()) {
            return false;
        } else {
            int dist = levenshteinDistance(tx1, tx2);
            int threshold = Math.min(tx1.length(), tx2.length()) / 10; // ~90% similar
            return dist < threshold;
        }
    }


    public static int levenshteinDistance(String s, String t) {
        int n = s.length();
        int m = t.length();

        if (n == 0) return m;
        if (m == 0) return n;

        int[][] d = new int[n + 1][m + 1];

        for (int i = 0; i <= n; d[i][0] = i++) ;
        for (int j = 1; j <= m; d[0][j] = j++) ;

        for (int i = 1; i <= n; i++) {
            char sc = s.charAt(i - 1);
            for (int j = 1; j <= m; j++) {
                int v = d[i - 1][j - 1];
                if (t.charAt(j - 1) != sc) v++;
                d[i][j] = Math.min(Math.min(d[i - 1][j] + 1, d[i][j - 1] + 1), v);
            }
        }
        return d[n][m];
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
