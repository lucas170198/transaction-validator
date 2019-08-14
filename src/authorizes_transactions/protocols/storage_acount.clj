(ns authorizes-transactions.protocols.storage-acount)

(defprotocol StorageAcount
  "Protocol for storage a client"
  (get-acount [acount] "return a storage acount")
  (create-acount [acount] "create a new acount and return this object"))