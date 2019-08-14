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

(defn high-frequency? [transactions]
  (> (count transactions) 3))

(defn same-attributes? [transaction merchant amount]
  (-> (:merchant transaction)
      (= merchant)
      (and
       (= amount (:amount transaction)))))

(defn similar-transaction? [last-transactions transaction]
  (let [merchant (:merchant transaction) amount (:amount transaction)]
    (-> (filter #(same-attributes? % merchant transaction) last-transactions)
        (count)
        (> 2)))) 

