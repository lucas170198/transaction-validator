(ns authorizes-transactions.handler-test
  (:require [authorizes-transactions.handler :as handler]
            [midje.sweet :refer :all]))

(def account-map {:account {:activeCard true :availableLimit 100}})
(def transaction-map
  {:transaction {:merchant "Burger King" :amount 20, :time "2019-02-13T10:00:00.000Z"}})

(facts "Validation of account schema"
       (fact "Account json format" :unit
             (handler/operation-account? account-map) => true)
       (fact "Any map different of account json"
             (handler/operation-account? {:foo {:bar "bla"}}) => false)
       (fact "Empty map"
             (handler/operation-account? {}) => false))

(facts "Validation of transaction schema"
       (fact "Transaction json format" :unit
             (handler/operation-transaction? transaction-map) => true)
       (fact "Any map different"
             (handler/operation-transaction? {:foo {:bar "bla"}}) => false)
       (fact "Empty map"
             (handler/operation-transaction? {}) => false))