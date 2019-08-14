(ns authorizes-transactions.logic)

(defn acount-reset? [acount]
  (not (empty? acount)))

(defn insufficient-limit? [transaction acount]
  (let [transaction-value (:amount transaction)
        acount-limit (:availableLimit acount)]
    (> transaction-value acount-limit)))

(defn card-blocked? [acount]
  (-> (:activeCard acount)
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

