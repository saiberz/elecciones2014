(defproject elecciones "0.1.1"
  :description "Clojure app to get information about regional elections candidates"
  :url "http://github.com/saiberz/elecciones2014"
  :jvm-opts ["-Dphantomjs.binary.path=./phantomjs-1.9.1-linux-x86_64/bin/phantomjs"]
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.6.0"]
                 [clj-webdriver/clj-webdriver "0.6.0"]
                 [com.novemberain/monger "2.0.0"]
                 [com.github.detro.ghostdriver/phantomjsdriver "1.0.3"]
                 [org.clojure/data.json "0.2.5"]]
  :main ^:skip-aot partidos.core
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all}})
