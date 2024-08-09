(ns servico-clojure.server.server
  (:require [servico-clojure.server.config :as config]
            [servico-clojure.server.start :as start]
            [com.stuartsierra.component :as component]))

(defrecord Server [database routes server]
  component/Lifecycle

  (start [this]
    (println "Start server")
    (let [service-map (config/create-service-map routes database)
          server (start/start-server service-map)]
      (assoc this :server server :test-request (partial config/test-request server))))

  (stop [this]
    (when-let [server (:server this)]
      (start/stop-server server))
    (assoc this :server nil :test-request nil)))

(defn new-server []
  (map->Server {}))
