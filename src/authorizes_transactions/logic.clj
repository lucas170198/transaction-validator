(ns authorizes-transactions.logic
  (:require [clj-time.local :as local]
            [clj-time.core :as time]))

(defn account-reset? [account]
  (not (empty? account)))

(defn insufficient-limit? [transaction account]
  (let [transaction-value (:amount transaction)
        account-limit (:availableLimit account)]
    (> transaction-value account-limit)))

(defn card-blocked? [account]
  (-> (:activeCard account)
      (not)))


(defn get-transaction-time [transaction]
  (-> (:time transaction)
      (local/to-local-date-time)))

(defn between-range? [transaction minute transaction-hist]
  (let [min-time (time/minus (get-transaction-time transaction)
                             (time/minutes minute))
        max-time (time/plus (get-transaction-time transaction)
                             (time/minutes minute))]
    (time/within?
     (time/interval min-time max-time)
     (get-transaction-time transaction-hist))))

(defn high-frequency? [transaction-hist transaction]
  (-> (filter #(between-range? transaction 2 %) transaction-hist)
      (count)
      (>= 3)))

(defn same-attributes? [transaction merchant amount]
  (-> (:merchant transaction)
      (= merchant)
      (and
       (= amount (:amount transaction)))))

(defn similar-transaction? [transaction-hist transaction]
  (let [merchant (:merchant transaction) amount (:amount transaction)]
    (-> (filter #(same-attributes? % merchant transaction) transaction-hist)
        (count)
        (> 2))))



