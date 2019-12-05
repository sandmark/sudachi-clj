(defproject sudachi-clj "0.1.0"
  :description "sudachi-clj: a clojure wrapper for Sudachi"
  :license {:name "EPL-2.0 OR GPL-2.0-or-later WITH Classpath-exception-2.0"
            :url  "https://www.eclipse.org/legal/epl-2.0/"}
  :dependencies [[org.clojure/clojure "1.10.1"]
                 [com.worksap.nlp/sudachi "0.3.0"]
                 [integrant "0.7.0"]
                 [org.clojure/data.json "0.2.7"]
                 [aero "1.1.3"]]
  :repl-options {:init-ns sudachi-clj.core}
  :profiles {:dev {:dependencies [[fudje "0.9.7"]]}})
