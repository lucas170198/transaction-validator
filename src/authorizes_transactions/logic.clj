(ns authorizes-transactions.logic)

(defn account-reset? [account-atom]
  (not (empty? account-atom)))

(defn insufficient-limit? [transaction account]
  (let [transaction-value (get-in transaction [:transaction :amount])
        account-limit (get-in account [:account :availableLimit])]
    (< account transaction-value)))

(defn card-blocked? [account]
  (-> (get-in account [:account :activeCard])
      (not)))

(defn high-frequency? [transactions]
  (> (count transaction) 3))

(defn- same-merchant? [transaction merchant])

(defn similar-transaction? [last-transactions transaction]
  (let [transaction-merchant
        (get-in transaction [:transaction :merchant])]
    (-> (filter #(same-merchant? % transaction-merchant) last-transactions)
        (count)
        (> 2))))

