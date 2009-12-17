(ns working-with-sequences (:use clojure.contrib.test-is) (:import (clojure.lang ISeq)))

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

