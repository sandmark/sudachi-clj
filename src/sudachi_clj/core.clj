(ns sudachi-clj.core
  (:require [integrant.core :as ig]
            [sudachi-clj.config :as config]))

(defonce system nil)

(defn stop []
  (alter-var-root
   #'system
   (fn [sys]
     (when sys (ig/halt! sys))
     nil))
  :stopped)

(defn start [& {:keys [dictionary-file]}]
  (alter-var-root
   #'system
   (fn [sys]
     (when sys (stop))
     (-> config/config
         (update :sudachi-clj.config/dictionary-file #(or dictionary-file %))
         ig/init)))
  :started)

(defn restart []
  (alter-var-root
   #'system
   (fn [sys]
     (let [dictionary-file (:sudachi-clj.config/dictionary-file sys)]
       (stop)
       (start :dictionary-file dictionary-file))))
  :restarted)

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
  (when-let [f (get system ::analyze)]
    (f text mode)))

(defn noun? [[_ [part]]]
  (= "名詞" part))
