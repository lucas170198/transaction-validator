(ns authorizes-transactions.db.saving-account)

(def account (atom nil))

(defn get-account [] @account)

(defn create-account! [new-account]
  (reset! account new-account))

(defn update-account! [limit]
  (swap! account assoc :availableLimit limit))