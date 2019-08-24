(ns authorizes-transactions.logic-test
  (:require [midje.sweet :refer :all]
            [authorizes-transactions.logic :refer :all]))

(def account {:activeCard true :availableLimit 100})

(def blocked-account {:activeCard false :availableLimit 100})

(def transaction-one {:merchant "Burger King" :amount 20 :time "2019-02-13T10:02:00.000Z"})

(def transaction-two  {:merchant "KFC" :amount 110 :time "2019-02-13T10:00:00.000Z"})

(def transactions [{:merchant "Burger King" :amount 20 :time "2019-02-13T10:00:00.000Z"}
                   {:merchant "Burger King" :amount 20 :time "2019-02-13T10:01:00.000Z"}
                   {:merchant "Nike shop" :amount 50 :time "2019-02-13T10:02:00.000Z"}
                   {:merchant "Habbibs" :amount 20 :time "2019-02-13T10:03:00.000Z"}])

(facts "Account reset violation"
       (fact "Already exists an account" :unit
             (account-reset? account) => true)
       (fact "Don't exists an account" :unit
             (account-reset? nil) => false))

(facts "Insufucient limit violation"
       (fact "Limit is less than transaction value" :unit
             (insufficient-limit? transaction-two account) => true)
       (fact "The limit is enough to the transaction" :unit
             (insufficient-limit? transaction-one account) => false))

(facts "Card blocked violation"
       (fact "The card is blocked" :unit
             (card-blocked? blocked-account) => true)
       (fact "The card is unblocked" :unit
             (card-blocked? account) => false))

(facts "Same attributes function"
       (fact "Has same attributs" :unit
             (same-attributes? transaction-one "Burger King" 20) => true)
       (fact "Has diferent attributes" :unit
             (same-attributes? transaction-one "KFC" 40) => false))

(facts "High frenquency small interval violation"
       (fact "Has than 3 transactions on a 2 minute interval" :integration
             (high-frequency? transactions transaction-one) => true)
       (fact "Has less then 3 transactions on a 2 minute interval" :integration
             (high-frequency? transactions transaction-two) => false))

(facts "Doubled transaction violation"
       (fact "Has than 2 similar transactions (same amount and merchant) in a 2 minutes interval" :integration
             (similar-transaction? transactions transaction-one) => true)
       (fact "Dont has than 2 similar transactions (same amount and merchant) in a 2 minutes interval" :integration
             (similar-transaction? transactions transaction-two) => false))

