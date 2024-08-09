(ns servico-clojure.server.start
  (:require [io.pedestal.http :as http]))

(defn start-server [service-map]
  (let [server (atom nil)]
    (reset! server (http/start (http/create-server service-map)))
    server))

(defn stop-server [server]
  (http/stop @server))

(defn restart-server [server service-map]
  (stop-server server)
  (start-server service-map))

(defn start
  []
  (try start-server (catch Exception e (println "Error to execute start" (.getMessage e))))
  (try restart-server (catch Exception e (println "Error to execute restart" (.getMessage e)))))

(start)
