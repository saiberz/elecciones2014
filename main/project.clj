(defproject main "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :jvm-opts ["-Dphantomjs.binary.path=./phantomjs-1.9.1-linux-x86_64/bin/phantomjs"]
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.6.0"]
                 [clj-webdriver/clj-webdriver "0.6.0"]
                 [com.github.detro.ghostdriver/phantomjsdriver "1.0.3"]
                 [org.clojure/data.json "0.2.5"]]
  )
