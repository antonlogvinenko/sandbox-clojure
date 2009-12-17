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


;accesing objects and classes
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



;imports
(import
  '(java.util Random Locale)
  '(java.text MessageFormat))
(is not (nil? Locale))
(is not (nil? MessageFormat))