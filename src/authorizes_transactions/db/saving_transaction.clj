(ns authorizes-transactions.db.saving-transaction
  (:require [authorizes-transactions.logic :as logic]))

(def transaction-hist (atom []))

(defn get-transactions [] @transaction-hist)

(defn save-transaction! [transaction]
  (swap! transaction-hist conj transaction))