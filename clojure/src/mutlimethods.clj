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

