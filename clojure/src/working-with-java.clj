(ns working-with-clojure (:use clojure.contrib.test-is))


;new
(is (= (new String) ""))
(def string (new String))
(is (= string ""))


;new shortened
(def string (String.))
(is (= string ""))
(def string (String. "abc"))
(is (= string "abc"))


;accessing objects and classes
(def string (new String "abcdef"))
(is (= (. string indexOf "c") 2))
(is (= (. string (indexOf "c") 2)))


(is (= (. string toUpperCase) "ABCDEF"))
(is (= (. string (toUpperCase)) "ABCDEF"))


(is (= (. Math PI) 3.141592653589793))
(is (= (. Math PI) 3.141592653589793))



;accessing objects shortened
(def string (new String "abcdef"))
(is (= (.indexOf string "c") 2))
(is (= (.toUpperCase string) "ABCDEF"))


;accessing statics shortened
(is (= (Math/PI) 3.141592653589793))
(is (= (Math/log 1) 0))

(def location (.getLocation (.getCodeSource (.getProtectionDomain (.getClass '(1 2))))))
(is (= (.getHost location) ""))
(def locationHost (.. '(1 2) getClass getProtectionDomain getCodeSource getLocation getHost))
(is (= locationHost ""))

(doto (System/getProperties)
  (.setProperty "name" "Stuart")
  (.setProperty "favoriteColor" "blue"))
(is (= (System/getProperty "name") "Stuart"))
(is (= (System/getProperty "favoriteColor") "blue"))

;imports
(import
  '(java.util Random Locale)
  '(java.text MessageFormat))
(is not (nil? Locale))
(is not (nil? MessageFormat))

;arrays
;(make-array)
;seq ()
;aset
;aget
;alength
;to-array
;into-array
;amap
;areduce
;memfn
;instance?
;format
;bean



