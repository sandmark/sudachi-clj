(ns sudachi-clj.core
  (:require [integrant.core :as ig]
            [sudachi-clj.system :as system]))

(defmethod ig/init-key ::analyze [_ {:keys [modes tokenizer]}]
  (fn [text mode]
    (letfn [(token->part [token]
              [(.surface token) (.partOfSpeech token)])]
      (->> text
           (.tokenize tokenizer (mode modes))
           (map token->part)
           (filter (comp seq first))
           (into [])))))

(defn analyze [text & {:keys [mode] :or {mode :c}}]
  (when-let [f (get system/system ::analyze)]
    (f text mode)))

(defn noun? [[_ [part]]]
  (= "名詞" part))
