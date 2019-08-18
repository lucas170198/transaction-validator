(ns authorizes-transactions.logic)

(defn account-reset? [account]
  (not (empty? account)))

(defn insufficient-limit? [transaction account]
  (let [transaction-value (:amount transaction)
        account-limit (:availableLimit account)]
    (> transaction-value account-limit)))

(defn card-blocked? [account]
  (-> (:activeCard account)
      (not)))

(defn high-frequency? [transaction-hist]
  (> (count transaction-hist) 3))

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



