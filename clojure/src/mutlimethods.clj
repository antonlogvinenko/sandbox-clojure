(ns working-with-sequences
  (:use clojure.contrib.test-is)
  (:import (clojure.lang ISeq)))


;defining multimethods
(defmulti my-decorate class)
(defmethod my-decorate String [s] (str s " is a string"))
(defmethod my-decorate nil [_] (str nil "nil is nil"))
(defmethod my-decorate Number [n] (str n " is a number"))
(defmethod my-decorate :default [n] (str n " was never seen by humanity"))

(is (= (my-decorate "string") "string is a string"))
(is (= (my-decorate nil) "nil is nil"))
(is (true? (isa? Integer Number)))
(is (= (my-decorate 42) "42 is a number"))
(is (= (my-decorate \Z) "Z was never seen by humanity"))

(defmulti f #(rem % 2) :default :oohaa-this-is-a-more-convenient-name)
(defmethod f 0 [x] "is even")
(defmethod f :oohaa-this-is-a-more-convenient-name [x] "is odd")
(is (= (f 4) "is even"))
(is (= (f 5) "is odd"))

(defmethod my-decorate Integer [n] (str n " is an Integer"))
(is (= (my-decorate 42) "42 is an Integer"))

(prefer-method my-decorate Integer Number)
(is (= (my-decorate 42) "42 is an Integer"))



(defstruct account :id :tag :balance)
(alias 'acc 'working-with-sequences)
(def test-savings (struct account 1 ::acc/Savings 100M))
(def test-checking (struct account 2 ::acc/Checking 250M))
(defmulti interest-rate :tag)
(defmethod interest-rate ::acc/Checking [_] 0M)
(defmethod interest-rate ::acc/Savings [_] 0.05M)
(is (= (interest-rate test-savings) 0.05M))
(is (= (interest-rate test-checking) 0M))

(derive ::acc/Savings ::acc/Account)
(derive ::acc/Checking ::acc/Account)
(is (true? (isa? ::acc/Savings ::acc/Account)))
(is (true? (isa? ::acc/Checking ::acc/Account)))
(defmulti info :tag)
(defmethod info ::acc/Account [_] "some account")
(defmethod info ::acc/Savings [_] "savings")
(is (= (info test-savings) "savings"))
(is (= (info test-checking) "some account"))



