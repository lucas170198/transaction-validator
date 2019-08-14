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

(defn same-merchant? [transaction merchant]
  (-> (:merchant transaction)
      (= merchant)))

(defn similar-transaction? [last-transactions transaction]
  (let [transaction-merchant
        (:merchant transaction)]
    (-> (filter #(same-merchant? % transaction-merchant) last-transactions)
        (count)
        (> 2)))) 

