(ns authorizes-transactions.db.saving-acount
  (:require
   [authorizes-transactions.protocols.storage-acount :as storage-acount]
   [authorizes-transactions.logic :as logic]))

(def acount (atom nil))

(extend-protocol StorageAcount
  Object
  (get-acount [] @acount)
  (create-acount! [acount]
    (if (logic/acount-reset? @acount)
      (merge @acount
             {:violations ["illegal-account-reset"]})
      (merge (reset! @acount acount)
             {:violations []}))))