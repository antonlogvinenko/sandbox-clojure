(ns exploring-clojure (:use clojure.contrib.test-is))



;numeric types
(is (= (+ 1 2) 3))
(is (= (+ 1 2 3) 6))
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

(is not (true? nil))
(is not (false? nil))

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
  Default username is 'world'."
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
(def heyo-greeter (make-greeter "Heyo"))
(is (= (heyo-greeter "Anton") "Heyo, Anton"))

(defn make-greeter [prefix] #(str prefix ", " %))
(def hi-greeter (make-greeter "Hi"))
(is (= (hi-greeter "Anton") "Hi, Anton"))

(is (= ((make-greeter "Welcome") "Anton") "Welcome, Anton"))



;vars
(def foo 42)
(is (= foo 42))
(is (= (var foo) #'exploring-clojure/foo))
(is (= #'foo #'exploring-clojure/foo))



;bindings
(defn square-corners [bottom left size]
  (let [top (+ bottom size) right (+ left size)]
    [[bottom left] [top left] [top right] [bottom right]]))
(is (= (square-corners 1 1 4) [[1 1] [5 1] [5 5] [1 5]]))

(is (= (let [[x y] [1 2 3]] [x y]) [1 2]))
(is (= (let [[_ _ z] [1 2 3]] z) 3))
(is (= (let [[_ _ z] [1 2 3]] _) 2))

(is (= (let [[x y :as coords] [1 2 3 4 5 6]] coords) [1 2 3 4 5 6]))

(defn greet-author [{author :first-name}]
  (str "Hello, " author))
(is (= (greet-author {:first-name "Neal" :second-name "Stephenson"}) "Hello, Neal"))



;namespaces
(is (= (def foo 10) #'exploring-clojure/foo))
(is (= (resolve 'foo) #'exploring-clojure/foo))

(ns exploring-clojure-somewhere-else (:use clojure.contrib.test-is))
(def foo 42)                
(is (= (resolve 'foo) #'exploring-clojure-somewhere-else/foo))
(is (= foo 42))
(in-ns 'exploring-clojure)
;a little bit more stupid namespaces stuff will be here soon
(is (= foo 10))
(is (= String java.lang.String))
(is (= (clojure.core/use 'clojure.core) nil))

(is (= java.io.File/separator "/"))
(import '(java.io InputStream File))
(is (= File/separator "/"))

(require 'clojure.contrib.math)
(is (= (clojure.contrib.math/round 1.7) 2))

(use 'clojure.contrib.math)
(is (= (round 1.7) 2))
(use '[clojure.contrib.math :only (round)])
(is (= (round 1.2) 1))
(use :reload '[clojure.contrib.math :only (round)])
(is (= (round 1.2) 1))
(use :reload-all '[clojure.contrib.math :only (round)])

(ns exploring-clojure2
  (:use clojure.contrib.test-is clojure.contrib.str-utils)
  (:import (java.io File)))
(is (= File/separator "/"))



;flow control
(defn is-small? [number]
  (if (< number 100) "yes"))
(is (= (is-small? 30) "yes"))
(is (nil? (is-small? 100)))

(defn is-small? [number]
  (if (< number 100) "yes" "no"))
(is (= (is-small? 30) "yes"))
(is (= (is-small? 100) "no"))


(defn is-small? [number]
  (if (< number 100)
  "yes"
  (do
    ()
    "no")))

(defn fill [x]
  (loop [result [] x x]
  (if (zero? x)
    result
    (recur (conj result x) (dec x)))))
(is (= (fill 5) [5 4 3 2 1]))

(defn countdown [result x]
  (if (zero? x)
    result
    (recur (conj result x) (dec x))))
(is (= (countdown [] 5) [5 4 3 2 1]))
(is (= (countdown [6] 5) [6 5 4 3 2 1]))


;for loop
(defn indexed [coll] (map vector (iterate inc 0) coll))
(is (= (indexed "abcde") [[0 \a] [1 \b] [2 \c] [3 \d] [4 \e]]))

(defn index-filter [pred coll]
  (when pred
    (for [[idx elt] (indexed coll) :when (pred elt)] idx)))
(is (= (index-filter #{\a \c} "abcde") [0 2]))

(defn index-of-any [pred coll]
  (first (index-filter pred coll)))

(is (= (index-of-any #{\z \a} "zzabyycdxx") 0))

(is (= (nth (index-filter #{:h} [:t :t :h :t :h :t :t :t :h :h]) 2) 8))

;object's metadata
(def anton {:name "anton" :email "anton@gmail.com"})
(def serializable-anton (with-meta anton {:serializable true}))

(is (= anton serializable-anton))
(is not (identical? anton serializable-anton))

(is (nil? (meta anton)))
(is not (nil? (meta serializable-anton)))
(is (= (meta serializable-anton) {:serializable true}))

(is (nil? ^anton))
(is not (nil? serializable-anton))
(is (= ^serializable-anton {:serializable true}))

(def anton-with-address (assoc serializable-anton :state "NC"))
(is (= anton-with-address {:name "anton" :email "anton@gmail.com" :state "NC"}))
(is (= ^anton-with-address ^serializable-anton))
(is not (= serializable-anton anton-with-address))



;var's metadata
(is (= ((meta #'str) :name) 'str))

(defn #^{:tag String} shout [#^{:tag String} s] (.toUpperCase s))
;???(is (= (#'shout :tag) String))

;???(is thrown (shout 1))
(def #^{:testdata true} foo (with-meta [1 2 3] {:order :ascending}))
(is (true? ((meta #'foo) :testdata)))
(is (= (meta foo) {:order :ascending}))

;???binding arguments and in let
;??? namespaces
;??? meta
;??? complete 'for feature list