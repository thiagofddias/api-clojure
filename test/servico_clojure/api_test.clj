(ns servico-clojure.api-test
  (:require [clojure.test :refer :all]
            [servico-clojure.api :as api])
  (:use [clojure.pprint :only [pprint]]))

(deftest tarefa-api-test
  (testing "Hello test"
    (let [path "/hello?name=Thiago"
          response (api/test-request :get path)
          body (:body response)]
      (is (= "Hello Thiago" body))))

  (testing "Crud Test"
    (let [_ (api/test-request :post "/task?name=Correr&status=pendente")
          _ (api/test-request :post "/task?name=Ler&status=pendente")
          tasks (clojure.edn/read-string (:body (api/test-request :get "/task")))
          task1 (-> tasks first second)
          task1-id (:id task1)
          task2 (-> tasks second second)
          task2-id (:id task2)
          _ (api/test-request :delete (str "/task/" task1-id))
          _ (api/test-request :patch (str "/task/" task2-id "?name=EstudarClojure&status=dificil"))
          tasks-processed (clojure.edn/read-string (:body (api/test-request :get "/task")))
          task-updated (-> tasks-processed vals first)]

      (is (= 2 (count tasks)))
      (is (= "Correr" (:name task1)))
      (is (= "pendente" (:status task1)))
      (is (= "Ler" (:name task2)))
      (is (= "pendente" (:status task2)))
      (is (= 1 (count tasks-processed)))
      (is (= "EstudarClojure" (:name task-updated)))
      (is (= "dificil" (:status task-updated))))))
