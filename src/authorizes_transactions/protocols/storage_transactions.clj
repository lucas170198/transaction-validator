(ns authorizes-transactions.protocols.storage-transactions)

(defprotocol StorageTransactions
  (get-most-recents [time-range] "return the most recent transactions")
  (get-all [_] "return all transactions")
  (insert-transaction [transaction] "return a create transaction and the actual acount state"))