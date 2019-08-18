(ns authorizes-transactions.core
  (:require  [cheshire.core :refer [parse-string]]
             [authorizes-transactions.handler :as handler])
  (:gen-class))

(defn -main [& args]
  (-> (parse-string (read-line) true)
      (handler/validate)
      (println))
  (recur args))