(ns authorizes-transactions.db.saving-transaction
  (:require [authorizes-transactions.logic :as logic]))

(def transaction-hist (atom []))

(defn get-transactions [] @transaction-hist)


; (defn sum-transactions []
;   (-> (map :amount @transaction-hist)
;       (reduce +)))

(defn save-transaction! [transaction]
  (swap! transaction-hist conj transaction))