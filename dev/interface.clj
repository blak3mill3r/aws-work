(ns interface
  (:require [ com.stuartsierra.component :as component ]
            ))

(defrecord Interface [resources-atom]
  component/Lifecycle
  (start [c]
    (-> c
        (assoc :resources-atom (atom {}))))
  (stop [c]
    (some-> c
            (dissoc :resources-atom))))

(defn new-interface [] (map->Interface {}))
