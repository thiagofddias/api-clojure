(ns servico-clojure.api
  (:require [servico-clojure.server.server :as server]
            [com.stuartsierra.component :as component]
            [servico-clojure.routes.routes :as routes]
            [servico-clojure.db.database :as database]))

(def component-system
  (component/system-map
    :database (database/new-database)
    :routes (routes/new-routes)
    :server (component/using (server/new-server) [:database :routes])))

(def component-result (component/start component-system))
(def test-request (-> component-result :server :test-request))

(println "The application is running")
