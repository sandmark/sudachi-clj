(ns sudachi-clj.config
  (:require [aero.core :as aero]
            [clojure.data.json :as json]
            [clojure.java.io :as io]
            [integrant.core :as ig])
  (:import [com.worksap.nlp.sudachi DictionaryFactory Tokenizer$SplitMode]))

(defmethod aero/reader 'ig/ref [_ _ value]
  (ig/ref value))

(def config (-> "config.edn"
                io/resource
                aero/read-config))

(defmethod ig/init-key ::split-mode [_ _]
  {:a Tokenizer$SplitMode/A
   :b Tokenizer$SplitMode/B
   :c Tokenizer$SplitMode/C})

(defmethod ig/init-key ::dictionary [_ settings]
  (.. (DictionaryFactory.) (create settings)))

(defmethod ig/halt-key! ::dictionary [_ dictionary]
  (.close dictionary))

(defmethod ig/init-key ::tokenizer [_ dictionary]
  (.create dictionary))

(defmethod ig/init-key ::dictionary-file [_ filename]
  (if (and filename (.exists (io/as-file filename)))
    filename
    (throw (java.io.IOException. (str "Dictionary file not found: " filename)))))

(defmethod ig/init-key ::json [_ m]
  (json/write-str m))
