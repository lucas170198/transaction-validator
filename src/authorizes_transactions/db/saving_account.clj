(ns authorizes-transactions.db.saving-account
  (:require
   [authorizes-transactions.logic :as logic]))

(def accounts (atom nil))

(defn get-account [] @account)

(defn create-account! [account]
    (if (logic/account-reset? @accounts)
      (merge @accounts
             {:violations ["illegal-account-reset"]})
      (merge (reset! @accounts account)
             {:violations []}))))

(defn update-account! [account]
  (swap! @accounts account))