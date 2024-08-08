(ns servico-clojure.routes.routes
  (:require [com.stuartsierra.component :as component]
            [io.pedestal.http.route :as route]
            [servico-clojure.routes.functionalities :as functionalities]))

(defrecord Routes []
  component/Lifecycle

  (start [this]
    (println "Start routes")

    (def routes (route/expand-routes
                  #{["/hello" :get functionalities/fn-hello :route-name :hello-world]
                    ["/task" :post functionalities/create-task :route-name :create-task]
                    ["/task" :get functionalities/list-tasks :route-name :list-tasks]
                    ["/task/:id" :delete functionalities/remove-tasks :route-name :remove-tasks]
                    ["/task/:id" :patch functionalities/update-tasks :route-name :update-tasks]}))

    (assoc this :endpoints routes))

  (stop [this]
    (println "Stop routes")
    (assoc this :endpoints nil))
  )

(defn new-routes []
  (->Routes))
