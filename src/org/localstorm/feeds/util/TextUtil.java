package org.localstorm.feeds.util;

import net.htmlparser.jericho.Source;
import net.htmlparser.jericho.TextExtractor;

import java.io.IOException;
import java.io.StringReader;

public class TextUtil {

    public static boolean textSimilarPct(String tx1, String tx2, int percent) {
        int dist = levenshteinDistance(tx1, tx2);
        if (dist == 0) {
            return true;
        } else {
            double similarity = 100 * (1 - ((double) dist) / (Math.max(tx1.length(), tx2.length())));
            return similarity >= percent;
        }
    }

    public static String toPlainText(String html) {
        try {
            Source src = new Source(new StringReader(html));
            TextExtractor tx = new TextExtractor(src);
            return tx.toString();
        } catch (IOException e) {
            return html;
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


}
