(ns authorizes-transactions.controller-test
  (:require [midje.sweet :refer :all]
            [authorizes-transactions.controller :as controller]))

(def account {:activeCard true :availableLimit 100})

(def trans-1 {:merchant "Burger King" :amount 20 :time "2019-02-13T10:00:00.000Z"})

(def trans-2 {:merchant "KFC" :amount 110 :time "2019-02-13T10:00:00.000Z"})

(facts "Create a vector to violations"
       (fact "First violation in the vector" :unit
             (controller/format-violation-text true "card-blocked") => ["card-blocked"])
       (fact "With previus violations in the vector" :unit
             (controller/format-violation-text ["card-blocked"] true "insufficient-limit") =>
             ["card-blocked" "insufficient-limit"]))

(facts "Account create"
       (fact "Create new account" :integration
             (controller/create-account! account)
             => {:activeCard true :availableLimit 100  :violations []}))

(against-background [(before :facts (controller/create-account! account))]
                    (facts "Transactions create"
                           (fact "Create valid transaction" :integration
                                 (controller/new-transaction! trans-1)
                                 => {:activeCard true :availableLimit 80 :violations []})
                           (fact "Create invalid transaction" :integration
                                 (controller/new-transaction! trans-2)
                                 => {:activeCard true :availableLimit 80 :violations ["insufficient-limit"]})))