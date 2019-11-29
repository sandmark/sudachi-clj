(ns sudachi-clj.system
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
