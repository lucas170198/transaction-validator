(ns authorizes-transactions.saving-transaction-test
  (:require [authorizes-transactions.db.saving-transaction :as db]
            [midje.sweet :refer :all]))

(def transaction {:transaction
                  {:merchant "Burger King" :amount 20 :time "2019-02-13T10:00:00.000Z"}})

(against-background [(before :facts (db/save-transaction! transaction))]
                    (facts "Get transactions"
                           (fact "Get the save transaction" :unit
                                 (first (db/get-transactions)) => (:transaction transaction))))