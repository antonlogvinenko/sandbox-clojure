(ns exploring-clojure
  (:use clojure.contrib.test-is)) 

;numeric types
(is (= (+ 1 2) 3))
(is (= (+ 1 2 3) 6))
(is (= (concat [1 2] [3 4]) [1 2 3 4]))
(is (zero? (+)))

(is (= (class (/ 22 7)) clojure.lang.Ratio))
(is (= (class (/ 22.0 7)) java.lang.Double))

(is (= (quot 22 7) 3))
(is (= (rem 22 7) 1))

(is (= (+ 1 (/ 0.00001 100000000000000000000)) 1.0))
(is (= (+ 1 (/ 0.00001M 100000000000000000000)) 1.0000000000000000000000001M))

(is (= (class (* 1000 1000 1000)) java.lang.Integer))
(is (= (class (* 1000 1000 1000 1000 1000 1000 1000 1000 1000)) java.math.BigInteger))



;strings and characters
(is (= "bla
kha" "bla\nkha"))

(is (= (.toUpperCase "hello") "HELLO"))

(is (= (str 1 2 "3" nil "4") "1234"))
(is (= (str \h \e \y \space \y \o \u) "hey you"))

(is (= (Character/toUpperCase \s) \S))

(is (= (interleave "abc" "def") [\a \d \b \e \c \f]))
(is (= (apply str (interleave "abc" "def")) "adbecf"))
(is (= (apply str (take-nth 2 "abcdef")) "ace"))



;booleans and nil
(is (false? false))
(is (true? true))

(is (false? (true? ())))
(is (false? (false? ())))

(is (false? (true? nil)))
(is (false? (false? nil)))

(is (= (if () "first" "second") "first"))
(is (= (if nil "first" "second") "second"))

(is (true? (nil? nil)))
(is (true? (zero? 0)))


;maps, keywords, structs
(def inventors1 {"Lisp" "McCarthy" "Clojure" "Hickey"})
(def inventors2 {"Lisp" "McCarthy", "Clojure" "Hickey"})
(is (= inventors1 inventors2))

(def inv inventors1)
(is (= (inv "Lisp") "McCarthy"))
(is (= (inv "Foo")) nil)

(is (= (get inv "Lisp") "McCarthy"))
(is (= (get inv "Python" "Somebody stupid") "Somebody stupid"))

(def inv {:Lisp "McCarthy" :Clojure "Hickey"})
(is (= (inv :Clojure) "Hickey"))
(is (= (:Clojure inv) "Hickey"))


(defstruct book :title :author)

(def book1 (struct book "Cryptonomicon" "Neal Stephenson"))
(is (= (:title book1) "Cryptonomicon"))

(def book2 (struct-map book :copyright 2008 :title "Anathem"))
(is (= (:copyright book2) 2008))
(is (= (:title book2) "Anathem"))
(is (nil? (:author book2)))



;functions
(is (true? (string? "bla")))
(is (true? (keyword? :hello)))
(is (true? (symbol? 'hello)))

(defn greeting
  "Returns a greeting of the form 'Hello, username."
  [username]
  (str "Hello, " username))
(is (= (greeting "world") "Hello, world"))

(defn greeting
  "Returns a greeting of the form 'Hello, username.'
  Defailt username is 'world'."
  ([] (greeting "world"))
  ([username] (str "Hello, " username)))
(is (= (greeting) "Hello, world"))
(is (= (greeting "Anton") "Hello, Anton"))

(defn date [person-1 person-2 & chaperones]
  (str person-1 " and " person-2 " went out with " (count chaperones) " chaperones"))
(is (= (date "Me" "Myself" "I" "Hickey" "McCarty") "Me and Myself went out with 3 chaperones"))



;anonymous functions
(use '[clojure.contrib.str-utils :only (re-split)])

(def b (filter (fn [w] (> (count w) 2)) (re-split #"\W+" "A fine day")))
(is (= b ["fine" "day"]))

(def b (filter #(> (count %) 2) (re-split #"\W+" "A fine day")))
(is (= b ["fine" "day"]))

(defn make-greeter [greeting-prefix]
  (fn [username] (str greeting-prefix ", " username)))
(def greeter (make-greeter "Heyo"))
(is (= (greeter "Anton") "Heyo, Anton"))

