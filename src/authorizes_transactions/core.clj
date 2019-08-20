(ns authorizes-transactions.core
  (:require  [cheshire.core :refer [parse-string]]
             [authorizes-transactions.handler :as handler])
  (:gen-class))

(defn -main [& args]
  (try (println "- input:")
       (->> (parse-string (read-line) true)
            (handler/validate)
            (str "- output: \n")
            (println))
       (catch Exception e (println "")))
  (recur args))