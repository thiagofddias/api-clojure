(ns servico-clojure.routes.functionalities
  (:require [com.stuartsierra.component :as component])
  (:import (java.util UUID)))

(defn list-tasks [request]
  {:status 200 :body @(:store request)})

(defn create-tasks-map [uuid name status]
  {:id uuid :name name :status status})

(defn create-task [request]
  (let [uuid (UUID/randomUUID)
        name (get-in request [:query-params :name])
        status (get-in request [:query-params :status])
        task (create-tasks-map uuid name status)
        store (:store request)]
    (swap! store assoc uuid task)
    {:status 200 :body {:message "Task registered successfully"
                        :task   task}}))

(defn fn-hello [request]
  {:status 200 :body (str "Hello " (get-in request [:query-params :name] " Everybody"))})

(defn remove-tasks [request]
  (let [store (:store request)
        task-id (get-in request [:path-params :id])
        task-id-uuid (UUID/fromString task-id)]
    (swap! store dissoc task-id-uuid)
    {:status 200 :body {:message "Task removed successfully!"}}))

(defn update-tasks [request]
  (let [task-id (get-in request [:path-params :id])
        task-id-uuid (UUID/fromString task-id)
        name (get-in request [:query-params :name])
        status (get-in request [:query-params :status])
        tarefa (create-tasks-map task-id-uuid name status)
        store (:store request)]
    (swap! store assoc task-id-uuid tarefa)
    {:status 200 :body {:message "Task updated successfully!"
                        :task     tarefa}}))