(defproject authorizes-transactions "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "EPL-2.0 OR GPL-2.0-or-later WITH Classpath-exception-2.0"
            :url "https://www.eclipse.org/legal/epl-2.0/"}
  :dependencies [[org.clojure/clojure "1.10.0"]
                 [clj-time "0.15.2"]
                 [cheshire "5.9.0"]
                 [org.clojure/tools.cli "0.4.1"]
                 [com.novemberain/validateur "2.5.0"]]
  :main ^:skip-aot authorizes-transactions.core
  :target-path "target/%s"
  :profiles   {:dev {:dependencies [[javax.servlet/servlet-api "2.5"]
                                    [ring/ring-mock "0.3.2"]
                                    [midje "1.9.6"]]
                     :plugins [[lein-midje "3.2.1"]
                               [lein-cloverage "1.0.13"]]}}) 
