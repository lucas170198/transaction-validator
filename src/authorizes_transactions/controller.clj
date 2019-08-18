(ns authorizes-transactions.controller
  (:require [authorizes-transactions.logic :as logic]
            [authorizes-transactions.db.saving-account :as db-account]
            [authorizes-transactions.db.saving-transaction :as db-transaction]))

(defn format-violation-text
  ([actual-violations flag text]
   (if flag
     (conj actual-violations text)
     actual-violations))
  ([flag text] (format-violation-text [] flag text)))

(defn get-transaction-violations [transaction account]
  (-> (format-violation-text
       (logic/card-blocked? account) "card-blocked")
      (format-violation-text
       (logic/insufficient-limit? transaction account) "insufficient-limit")
      (format-violation-text
       (logic/high-frequency? transaction) "high-frequency-small-interval")
      (format-violation-text
       (logic/similar-transaction? transaction (db-transaction/get-transactions)) "doubled-transaction")))

(defn valid-transaction? [transacion account]
  (-> (get-transaction-violations transacion account)
      (count)
      (= 0)))

(defn create-account! [new-account]
  (let [account (db-account/get-account)]
    (if (logic/account-reset? account)
      (merge account
             {:violations ["illegal-account-reset"]})
      (merge (db-account/create-account! new-account)
             {:violations []}))))

(defn save-transaction! [transacion account]
  (db-transaction/save-transaction! transacion)
  (let [trans-value (:amount transacion)
        atual-limit (:availableLimit account)]
    (-> (- atual-limit trans-value)
        (db-account/update-account!)))
  {:violations []})

(defn new-transaction! [transacion]
  (if-let [account (db-account/get-account)]
    (if (valid-transaction? transacion account)
      (merge (save-transaction! transacion account)
             (db-account/get-account))
      (merge (get-transaction-violations transacion account)
             (db-account/get-account)))))