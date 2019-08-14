(ns authorizes-transactions.logic-test
  (:require [midje.sweet :refer :all]
            [authorizes-transactions.logic :refer :all]))

(def account {:activeCard true :availableLimit 100})

(def blocked-account {:activeCard false :availableLimit 100})

(def transaction-low {:merchant "Burger King" :amount 20 :time "2019-02-13T10:00:00.000Z"})

(def transaction-high  {:merchant "KFC" :amount 110 :time "2019-02-13T10:00:01.000Z"})

(def transactions [{:merchant "Burger King" :amount 20 :time "2019-02-13T10:00:00.000Z"}
                   {:merchant "Mac Donalds" :amount 30 :time "2019-02-13T10:01:00.000Z"}
                   {:merchant "Nike shop" :amount 50 :time "2019-02-13T10:02:00.000Z"}
                   {:merchant "Habbibs" :amount 20 :time "2019-02-13T10:03:00.000Z"}])

(facts "test the 'illegal-account-reset' rule to authorizes a account creation"
       (fact "the result is false, don't exists an account"
             (account-reset? nil) => false)
       (fact "the result is true, already exists an account"
             (account-reset? account) => true))

(facts "test the limit validation authorize for a transaction"
       (fact "the limit is enough to valid the transaction"
             (insufficient-limit? transaction-low account) => false)
       (fact "limit is less than transaction value"
             (insufficient-limit? transaction-high account) => true))

(facts "test if the card is blocked or no in an account"
       (fact "the card is blocked, return true"
             (card-blocked? blocked-account) => true)
       (fact "the card is unblocked, return false"
             (card-blocked? account) => false))

(facts "test if this have more than 3 transactions"
       (fact "the vector have more then 3 transactions, return true"
             (high-frequency? transactions) => true)
       (fact "the vector have less or equal then 3 transactions, return false"
             (high-frequency? (rest transactions)) => false))

