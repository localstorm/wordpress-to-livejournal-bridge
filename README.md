wordpress-to-livejournal-bridge
===============================

Simple tool to repost Wordpress to LiveJournal with pluggable decoration modules allowing you to transform your post in a way you want (e.g. add title prefix, publish link to WP only, set some tags for LJ etc.)

-----------------------------------------------
How to run:
-----------------------------------------------

Try republishing the last 5 posts from localstorm.wordpress.com to localstorm.livejournal.com (hardcoded):

java -cp "dist/*" org.localstorm.feeds.tools.WordpressToLivejournal <lj-user> <lj-password>

-----------------------------------------------
Requirements:
-----------------------------------------------
Java v.7+