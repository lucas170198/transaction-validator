(ns authorizes-transactions.handler
  (:require [validateur.validation :refer :all]
            [authorizes-transactions.controller :as controller]))

(def account-schema (nested :account (validation-set
                                      (presence-of :activeCard)
                                      (presence-of :availableLimit))))

(def transaction-schema (nested :transaction (validation-set
                                              (presence-of :merchant)
                                              (presence-of :amount)
                                              (presence-of :time))))
(defn operation-account? [json-map]
  (empty? (account-schema json-map)))

(defn operation-transaction? [json-map]
  (empty? (transaction-schema json-map)))

(defn validate [json-map]
  (cond (operation-account? json-map) (controller/create-account!
                                       (:account json-map))
        (operation-transaction? json-map) (controller/new-transaction!
                                           (:transaction json-map))
        :else ""))