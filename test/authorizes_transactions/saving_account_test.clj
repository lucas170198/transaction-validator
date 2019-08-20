(ns authorizes-transactions.saving-account-test
  (:require [authorizes-transactions.db.saving-account :as db]
            [midje.sweet :refer :all]))

(def account {:activeCard true :availableLimit 100})

(against-background [(before :facts (db/create-account! account))]
                    (facts "Get account" :unit
                           (fact "Get null account"
                                 (db/get-account) => account))
                    (facts "Update limit account" :unit
                           (fact "New limit for the account"
                                 (db/update-account! 50) => {:activeCard true :availableLimit 50})))
