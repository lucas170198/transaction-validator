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
  (> (count transactions) 3))

(defn same-attributes? [transaction merchant amount]
  (-> (:merchant transaction)
      (= merchant)
      (and
       (= amount (:amount transaction)))))

(defn similar-transaction? [transaction-hist transaction]
  (let [merchant (:merchant transaction) amount (:amount transaction)]
    (-> (filter #(same-attributes? % merchant transaction) last-transactions)
        (count)
        (> 2))))

(defn format-violation-text
  ([actual-violations flag text]
   (if flag
     (conj actual-violations text)
     actual-violations))
  ([flag text]
   (format-violation-text [] flag text)))

(defn get-transaction-violations [transaction-hist transaction account]
  (-> (format-violation-text (card-blocked? account) "card-blocked")
      (format-violation-text (insufficient-limit? transaction account) "insufficient-limit")
      (format-violation-text (high-frequency? transaction) "high-frequency-small-interval")
      (format-violation-text (similar-transaction? transaction account "doubled-transaction"))))

(defn get-account-violation [account]
  (format-violation-text (account-reset? account) "illegal-account-reset"))



