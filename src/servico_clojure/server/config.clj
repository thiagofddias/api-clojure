(ns servico-clojure.server.config
  (:require [io.pedestal.http :as http]
            [io.pedestal.test :as test]
            [io.pedestal.interceptor :as i]))

(defn assoc-store [context database]
  (update context :request assoc :store (:store database)))

(defn test-request [server verb url]
  (test/response-for (::http/service-fn @server) verb url))

(defn create-service-map [routes database]
  (let [db-interceptor {:name  :db-interceptor
                        :enter (fn [context] (assoc-store context database))}
        service-map-base {::http/routes (:endpoints routes)
                          ::http/port   9999
                          ::http/type   :jetty
                          ::http/join?  false}]
    (-> service-map-base
        (http/default-interceptors)
        (update ::http/interceptors conj (i/interceptor db-interceptor)))))
