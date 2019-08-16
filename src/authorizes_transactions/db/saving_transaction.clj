(ns authorizes-transactions.db.saving-transaction
  (:require [authorizes-transactions.logic :as logic]))

(def transaction-hist (atom nil))

(defn get-transactions [] @transactions)

(defn sum-transactions [transactions]
  (-> (map :amount)
      (reduce +)))

(defn new-account-state [account]
  (->> (sum-transactions @transaction-hist)
       (update account :availableLimit -)))

(defn save-transaction! [transaction account]
  (swap! @transaction-hist (conj @transaction-hist transaction))
  (new-account-state account))