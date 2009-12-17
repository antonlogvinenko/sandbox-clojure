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
(def arr1 (make-array String 3))
(def arr2 (make-array String 4 5))

(is (= (.getName(class arr1)) "[Ljava.lang.String;"))
(is (= (.getName(class arr2)) "[[Ljava.lang.String;"))
(is (= (alength arr1) 3))
(is (= (alength arr2)) 4)
(is (= (alength (get arr2 0)) 5))

(is (= (seq (make-array String 3)) [nil nil nil]))

(defn painstakingly-create-array []
  (let [arr (make-array String 5)]
    (aset arr 0 "painstaking")
    (aset arr 1 "to")
    (aset arr 2 "fill")
    (aset arr 3 "in")
    (aset arr 4 "arrays")
    arr))
(is (= (aget (painstakingly-create-array) 0) "painstaking"))

(is (= (.getName (class (to-array ["Easier" "array" "creation"]))) "[Ljava.lang.Object;"))
(is (= (.getName (class (into-array Number [1 2 3]))) "[Ljava.lang.Number;"))
(is (= (.getName (class (into-array [1 2 3]))) "[Ljava.lang.Integer;"))

(def strings (into-array ["some" "blonde" "strings" "here"]))
(def a (seq (amap strings idx _ (.toUpperCase (aget strings idx)))))
(is (= a ["SOME" "BLONDE" "STRINGS" "HERE"]))
(def b (areduce strings idx ret 0 (max ret (.length (aget strings idx)))))
(is (= b 7))

;convenience functions
(is (= (map (memfn toUpperCase) ["a" "short" "message"]) ["A" "SHORT" "MESSAGE"]))
(is (true? (instance? Integer 10)))
(is (true? (instance? Comparable 10)))
(is (false? (instance? String 10)))

(is (= (format "%s ran %d miles today" "Stu" 8) "Stu ran 8 miles today"))

(import '(java.security MessageDigest))
(is (= ((bean (MessageDigest/getInstance "SHA")) :algorithm) "SHA"))


;optimizing for performance
(defn integer-sum-to [n]
  (let [n (int n)]
    (loop [i (int 1) sum (int 0)]
      (if (<= i n)
        (recur (inc i) (unchecked-add i sum))
        sum))))
(is (= (integer-sum-to 10) 55))

(defn better-sum-to [n]
  (reduce + (range 1 (inc n))))
(is (= (better-sum-to 10) 55))

(defn best-sum-to [n]
  (/ (* n (inc n)) 2))
(is (= (best-sum-to 10) 55))

(dotimes [_ 3] (time (best-sum-to 100)))

(defn describe-class [#^Class c]
  {:name (.getName c)
   :final (java.lang.reflect.Modifier/isFinal (.getModifiers c))})

;(set! *warn-on-reflection* to warn about impossible
;will complaing if cast will be required and unpossible
(is (= ((describe-class StringBuffer) :name) "java.lang.StringBuffer"))
;will not complain here:
(defn wants-a-string [#^Stirng s] s)
(is (= (wants-a-string 3) 3))



;exception handling
(use '[clojure.contrib.duck-streams])
(import '(java.io PrintWriter))

(spit "hello.out" "hello, world")
(defn spit2 [f content]
  (with-open [#^PrintWriter w (writer f)]
    (.print w content)))

(is (=
  (try (throw (Exception. "something failed")) (catch Exception _"something happened") (finally "we get to clean up"))
  "something happened"))

(defn class-available? [class-name]
  (try
    (Class/forName class-name) true
    (catch ClassNotFoundException _ false)))

(is (true? (class-available? "java.lang.String")))
(is (false? (class-available? "borg.util.Assimilate")))

(def z (
  proxy [Callable] []
  (call [] "sas")))