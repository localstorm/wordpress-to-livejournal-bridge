package org.localstorm.feeds.tools;

import org.localstorm.feeds.*;
import org.localstorm.feeds.blogs.LivejournalPoster;
import org.localstorm.feeds.blogs.LivejournalSource;
import org.localstorm.feeds.blogs.WordpressSource;
import org.localstorm.feeds.decor.*;
import org.localstorm.feeds.dupes.NaiveDupesDetector;

import java.util.Collections;
import java.util.List;

/**
 * @author localstorm
 *         Date: 30.12.13
 */
public class WordpressToLivejournal {

    public static final int REPUBLISH_DEPTH = 5;

    public static void main(String[] args) throws Exception {
        if (args.length != 2) {
            System.err.println("Usage: <livejournal user> <lj password>");
            return;
        }

        BlogSource wp = new WordpressSource("http://localstorm.wordpress.com/feed/", 60000);
        BlogSource lj = new LivejournalSource("http://localstorm.livejournal.com/data/rss", 60000);

        BlogPoster ljPoster = new LivejournalPoster(args[0], args[1]);
        EntryDecorator ljDecor = new ChainedDecoration(
                new LinkForwardDecoration(),
                new RegExTitleDecoration("^\\[[a-zA-Z]+\\]:", ""),
                new TitlePrefixDecoration("[WP]: "),
                new TagsForceDecoration("stuff to read")
        );

        wp.setIgnoreTags(Collections.<String>singleton("Uncategorized"));
        List<BlogEntry> entries = wp.read(REPUBLISH_DEPTH);
        for (BlogEntry e : entries) {
            NaiveDupesDetector dd = new NaiveDupesDetector();
            if (dd.needsPublishing(e, lj, REPUBLISH_DEPTH * 2)) {
                ljPoster.post(e, ljDecor);
            } else {
                System.out.println("No republishing: " + e.toString(Mode.MINIMAL));
            }
        }
    }
}
