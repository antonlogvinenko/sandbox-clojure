(ns working-with-sequences
  (:use clojure.contrib.test-is)
  (:import (clojure.lang ISeq)))


;creating sequence - on any seq-able collection
(is (nil? (seq [])))
(is (nil? (seq nil)))
(is (= (seq [1 2 3]) '(1 2 3)))
(is (instance? ISeq (seq [1 2 3])))
(is (instance? clojure.lang.PersistentList '(1 2 3)))
(is (instance? clojure.lang.PersistentVector$ChunkedSeq (seq [1 2 3])))

;first
(is (= (first [1 2 3]) 1))

;rest
(is (= (rest [1 2 3]) '(2 3)))
(is (= (rest nil) '()))
(is (= (rest []) '()))

;next
(is (= (next [1 2 3]) '(2 3)))
(is (= (next nil) nil))
(is (= (next []) nil))

;cons
(is (= (cons 0 [1 2 3]) [0 1 2 3]))

(is (= (first {:fname "Stu" :lname "Halloway"}) [:fname "Stu"]))
(is (= (rest {:fname "Stu" :lname "Halloway"}) '([:lname "Halloway"])))
(is (= (cons [:mname "Dabbs"] {:fname "Stu" :lname "Halloway"}) '([:mname "Dabbs"] [:fname "Stu"] [:lname "Halloway"])))

(is (= (first #{:the :qiuck :brown :fox}) :brown))
(is (= (rest #{:the :quick :brown :fox}) '(:quick :fox :the)))
(is (= (cons :jumped #{:the :quick :brown :fox}) '(:jumped :brown :quick :fox :the)))

;sorted
(is (= (sorted-set :the :quick :brown :fox) #{:brown :fox :quick :the}))
(is (= (sorted-map :c 3 :b 2 :a 1) {:a 1 :b 2 :c 3}))

;conj
(is (= (conj '(1 2 3) :a) '(:a 1 2 3)))
(is (= (conj [1 2 3] :a) '(1 2 3 :a)))

;into
(is (= (into '(1 2 3) '(:a :b :c))) '(:c :b :a 1 2 3))
(is (= (into [1 2 3] [:a :b :c]) [1 2 3 :a :b :c]))

;creating sequences
(is (= (range 10) '(0 1 2 3 4 5 6 7 8 9)))
(is (= (range 10 20) '(10 11 12 13 14 15 16 17 18 19)))
(is (= (range 1 10 2) '(1 3 5 7 9)))

(is (= (repeat 5 1) '(1 1 1 1 1)))
(is (= (take 3 (repeat "x")) ["x" "x" "x"]))

(is (= (take 5 (iterate inc 1)) '(1 2 3 4 5)))

(is (= (take 5 (cycle (range 3))) '(0 1 2 0 1)))

(is (= (interleave (iterate inc 1) ["A" "B" "C" "D" "E"]) '(1 "A" 2 "B" 3 "C" 4 "D" 5 "E")))

(is (= (interpose "," ["apples" "bananas" "grapes"]) '("apples" "," "bananas" "," "grapes")))

(is (= (apply str (interpose \, ["apples" "bananas" "grapes"])) "apples,bananas,grapes"))

(use '[clojure.contrib.str-utils :only (str-join)])

(is (= (str-join \, ["apples" "bananas" "grapes"]) "apples,bananas,grapes"))

(is (= (list 1 2 3) '(1 2 3)))
(is (= (class (list 1 2 3)) clojure.lang.PersistentList))

(is (= (vector 1 2 3) [1 2 3]))
(is (= (class (vector 1 2 3)) clojure.lang.PersistentVector))

(is (= (vec '(1 2 3)) [1 2 3]))
(is (= (class (vec '(1 2 3))) clojure.lang.PersistentVector))

(is (= (hash-set 1 2 3 4) #{1 2 3 4}))
(is (= (class (hash-set 1 2 3)) clojure.lang.PersistentHashSet))

(is (= (set '(1 2 3)) #{1 2 3}))
(is (= (class (set '(1 2 3))) clojure.lang.PersistentHashSet))

(is (= (hash-map 1 2 3 4) {1 2 3 4}))
(is (= (class (hash-map 1 2 3 4)) clojure.lang.PersistentHashMap))


;filtering sequences

