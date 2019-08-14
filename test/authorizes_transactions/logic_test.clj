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

(facts "Chack if already exists a acount in memory"
       (fact "Already exists an acount"
             (account-reset? account) => true)
       (fact "Don't exists an acount"
             (account-reset? nil) => false))

(facts "Check if account have suficient limit for a transaction"
       (fact "Limit is less than transaction value"
             (insufficient-limit? transaction-high account) => true)
       (fact "The limit is enough to the transaction"
             (insufficient-limit? transaction-low account) => false))


(facts "Check if card is blocked"
       (fact "The card is blocked"
             (card-blocked? blocked-account) => true)
       (fact "The card is unblocked"
             (card-blocked? account) => false))

(facts "Count the number of transactions"
       (fact "The vector have more then 3 transactions"
             (high-frequency? transactions) => true)
       (fact "The vector have less or equal then 3 transactions"
             (high-frequency? (rest transactions)) => false))

(facts "Compare merchant for a transaction"
       (let [merchant "Burger King"] 
            (fact "The transaction is from same merchant"
                  (same-merchant? transaction-low merchant) => true)
            (fact "The transaction is from another merchant"
                  (same-merchant? transaction-high merchant) => false)))

