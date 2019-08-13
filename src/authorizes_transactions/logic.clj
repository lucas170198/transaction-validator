(ns authorizes-transactions.logic)

(defn account-reset? [account]
  (not (empty? account)))

(defn insufficient-limit? [transaction account]
  (let [transaction-value (get-in transaction [:transaction :amount])
        account-limit (get-in account [:account :availableLimit])]
    (> transaction-value account-limit)))

(defn card-blocked? [account]
  (-> (get-in account [:account :activeCard])
      (not)))

(defn high-frequency? [transactions]
  (> (count transactions) 3))

(defn- same-merchant? [transaction merchant]
  (-> (get-in transaction [:transaction :merchant])
      (= merchant)))

(defn similar-transaction? [last-transactions transaction]
  (let [transaction-merchant
        (get-in transaction [:transaction :merchant])]
    (-> (filter #(same-merchant? % transaction-merchant) last-transactions)
        (count)
        (> 2))))

