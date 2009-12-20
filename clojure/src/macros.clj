(ns macros (:use clojure.contrib.test-is))

;flow macro
(defmacro unless [expr form]
  (list 'if expr nil form))
(is (= (unless (> 0 2) 42) 42))
(is (= (unless (> 2 0) 42) nil))
(is (= (macroexpand-1 '(unless (> 0 2) 42)) '(if (> 0 2) nil 42)))

(defmacro when-not2 [test & body]
  (list 'if test nil (cons 'do body)))

;making macros simpler
(defmacro chain
  ([x form] `(. ~x ~form))
  ([x form & more] `(chain (. ~x ~form) ~@more)))

;names in a macro
(defmacro bench [expr]
  `(let [start# 300
         result# ~expr]
    {:result result# :elapsed (- 400 start#)}))
;gensym

(is (= (bench (str "a" "b")) {:elapsed 100 :result "ab"}))

;taxonomy of macros
;(comment & exprs)

;(create-struct & key-symbols)
;(defstruct name & key-symbols)
;(declare & names)

;java interop: . as new, .. as calls chaining
;(use '[clojure.contrib.import-static :only (import-static)])
;(import-static java.lang.Math PI pow) 

;postponing evaluation
;(delay & epxr)
;(force dalayed)

;wrapping evaluation
;time let binding with-open dosync
(is (= (with-out-str (print "oaha") (print "cool")) "oahacool"))

;avoiding lambda
;macros take unevaluated expressions




