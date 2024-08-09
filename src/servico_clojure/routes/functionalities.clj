(ns servico-clojure.routes.functionalities
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
                        :task    task}}))

(defn fn-hello [request]
  {:status 200 :body (str "Hello " (get-in request [:query-params :name] "Everybody"))})

(defn remove-tasks [request]
  (let [store (:store request)
        task-id (UUID/fromString (get-in request [:path-params :id]))]
    (swap! store dissoc task-id)
    {:status 200 :body {:message "Task removed successfully!"}}))

(defn update-tasks [request]
  (let [task-id (UUID/fromString (get-in request [:path-params :id]))
        name (get-in request [:query-params :name])
        status (get-in request [:query-params :status])
        task (create-tasks-map task-id name status)
        store (:store request)]
    (swap! store assoc task-id task)
    {:status 200 :body {:message "Task updated successfully!"
                        :task    task}}))
