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
(is (= (filter even? [1 2 3 4 5 6]) [2 4 6]))

(is (= (take-while #(> 5 %) [1 3 5 7 9]) '(1 3)))
(is (= (take-while (complement #{\a\e\i\o\u}) "the-quick-brown-fox") '(\t \h)))

(is (= (drop-while #(> 5 %) [1 3 5 7 9 1]) '(5 7 9 1)))

(is (= (split-at 5 (range 10)) '((0 1 2 3 4) (5 6 7 8 9))))

(is (= (split-with #(<= % 10) (range 0 20 2)) '((0 2 4 6 8 10) (12 14 16 18))))


;sequence predicates
(is (true? (every? odd? [1 3 5])))
(is (false? (every? odd? [1 3 6])))

(is (true? (some odd? [1 2 4])))
(is (nil? (some odd? [2 4 6])))

(is (= (some identity [nil false 1 nil 2]) 1))

(is (false? (not-every? odd? [1 3 5])))
(is (true? (not-every? odd? [1 2 3])))

;not any is odd? (no odd elements?)
(is (true? (not-any? odd? [2 4 6])))
(is (false? (not-any? odd? [1 3 5])))
(is (false? (not-any? odd? [1 2 3 4])))

;transforming sequences
(is (= (map #(* 2 %) [1 2 3 4]) [2 4 6 8]))
(is (= (map #(* %1 %2) [1 2 3 4] [5 6 7 8]) [5 12 21 32]))

(is (= (reduce + (range 1 11)) 55))

(is (= (sort #(> %1 %2) [1 2 3]) [3 2 1]))
(is (= (sort > [1 2 3]) [3 2 1]))
(is (= (sort [3 2 1]) [1 2 3]))

(is (= (sort-by #(.toString %) [42 1 7 11]) '(1 11 42 7)))
(is (= (sort-by :grade > [{:grade 83 {:grade 90} {:grade 77}}])))

(is (= (for [word ["the" "quick" "brown" "fox"]] (format "%s!" word)) ["the!" "quick!" "brown!" "fox!"]))
(is (= (for [n (take 10 (iterate inc 1)) :when (even? n)] n) [2 4 6 8 10]))
(is (= (for [n (take 10 (iterate dec 10)) :while (even? n)] n) [10]))
(is (= (for [file "ABC" rand (range 1 2)] [file rand]) '([\A 1] [\B 1] [\C 1])))

;forcing sequences
(def x (for [i (range 1 3)] (do (println i) i)))
(is (= x) [])
;doall forces and stores in memory
(is (= (doall x) [1 2]))
;dorun forces but does not store
(is (= (dorun x) nil))
(is (= (doseq [k (take 5 (iterate inc 1))] (println k) k) nil))




;seq-ing java collections
(is (= (first (.getBytes "hello")) 104))
(is (= (rest (.getBytes "hello")) '(101 108 108 111)))
(is (= (cons (int \h) (.getBytes "ello")) '(104 101 108 108 111)))

(is (= (key (first (System/getProperties))) "java.runtime.name"))
;same with rest and cons

(is (= (first "Hello") \H))
(is (= (rest "Hello") '(\e\l\l\o)))
(is (= (cons \H "ello") '(\H\e\l\l\o)))

(is (= (apply str (reverse "hello")) "olleh"))

;seq-ing regular expressions
(def m (re-matcher #"\w+" "the quick brown fox"))
(def a1 (re-find m))
(def a2 (re-find m))
(is (= a1 "the"))
(is (= a2 "quick"))

(is (= (re-seq #"\w+" "the quick brown fox") '("the" "quick" "brown" "fox")))
(is (= (sort (re-seq #"\w+" "the quick brown fox")) '("brown" "fox" "quick" "the")))
(is (= (drop 2 (re-seq #"\w+" "the quick brown fox")) '("brown" "fox")))
(is (= (map #(.toUpperCase %) (re-seq #"\w+" "the quick brown fox")) '("THE" "QUICK" "BROWN" "FOX")))









;seq-ing the file system
(import '(java.io File))
(is (= (.getName (class (.listFiles (File. ".")))) "[Ljava.io.File;"))
;not testing, just for example
(println (map #(.getName %) (seq (.listFiles (File. ".")))))
(println (map #(.getName %) (.listFiles (File. "."))))
(count (file-seq (File. ".")))

;seq-ing a stream
(use '[clojure.contrib.duck-streams :only(reader)])
(is (= (take 1 (line-seq (reader "working-with-sequences.clj"))) '("(ns working-with-sequences")))
(is (= (with-open [rdr (reader "purpose.txt")] (count (line-seq rdr)))))

;seq-ing XML
(use '[clojure.xml :only (parse)])
(def c (for [x
      (xml-seq (parse (java.io.File. "compositors.xml")))
      :when (= :composition (:tag x))]
  (:composer (:attrs x))))
(is (= c '("J. S. Bach" "J. S. Bach" "W. A. Mozart")))


;functions on list
(is (= (peek '(1 2 3)) 1))
(is (= (pop '(1 2 3)) '(2 3)))
(is (= (try (do (pop '()) "1") (catch Exception _ "2")) "2"))
(is (= (rest ()) '()))

;functions on vectors
(is (= (peek [1 2 3]) 3))
(is (= (pop [1 2 3]) [1 2]))
(is (= (try (do (pop []) "1") (catch Exception _ "2")) "2"))

(is (= (get [:a :b :c] 1) :b))
(is (= (get [:a :b :c] 5) nil))

(is (= ([:a :b :c] 1) :b))
(is (= (try (do ([:a :b :c] 5) "1") (catch Exception _ "2")) "2"))

(is (= (assoc [0 1 2 3 4] 2 :two) [0 1 :two 3 4]))

(is (= (subvec [1 2 3 4 5] 3) [4 5]))
(is (= (subvec [1 2 3 4 5] 1 3) [2 3]))

;functions on maps
(def m {:sundance "spaniel" :darwin "beagle"})
(is (= (keys m) '(:sundance :darwin)))
(is (= (vals m) '("spaniel" "beagle")))

(is (= (get m :darwin) "beagle"))
(is (= (get m :snoopy) nil))
(is (= (get m :snoopy "what's that?") "what's that?"))

(is (= (m :darwin) "beagle"))
(is (= (m :snoopy) nil))
(is (= (m :snoopy "what's that?") "what's that?"))

(is (= (:darwin m) "beagle"))
(is (= (:snoopy m) nil))
(is (= (:snoopy m "what's that?") "what's that?"))

(def score {:stu nil :joey 100})
(is (= (score :stu) nil))
(is (= (score :stu2) nil))
(is (true? (contains? score :stu)))
(is (false? (contains? score :stu2)))


;assoc dissoc select-keys merge
(def song {:name "Awake" :album "Sleep"})
(is (= (assoc song :format "MP3") {:name "Awake" :album "Sleep" :format "MP3"}))
(is (= (dissoc song :name) {:album "Sleep"}))
(is (= (select-keys song [:name]) {:name "Awake"}))
(is (= (merge song {:year 1986}) {:name "Awake" :album "Sleep" :year 1986}))

(is (=
  (merge-with concat {:rubble ["Barney"] :flintstone ["Fred"]} {:rubble ["Betty"] :flintstone ["Wilma"]})
  {:rubble ["Barney" "Betty"] :flintstone ["Fred" "Wilma"]}))

;functions on sets
(use 'clojure.set)
(def languages #{"java" "c" "d" "clojure"})
(def letters #{"a" "b" "c" "d" "e"})
(def beverages #{"java" "chai" "pop"})

(is (= (union languages beverages) #{"java" "c" "d" "clojure" "chai" "pop"}))
(is (= (difference languages beverages) #{"c" "d" "clojure"}))
(is (= (intersection languages beverages) #{"java"}))
(is (= (select #(= 1 (.length %)) languages) #{"c" "d"}))

(def compositions #{{:name "The art" :composer "Bach"} {:name "Requiem" :composer "Verdi"}})
(is (=
  (rename compositions {:name :title})
  #{{:title "The art" :composer "Bach"} {:title "Requiem" :composer "Verdi"}}
  ))
(is (= (project compositions [:name]) #{{:name "Requiem"} {:name "The art"}}))

(def composers #{{:composer "Bach" :country "Germany"} {:composer "Verdi" :country "Austria"}})
(is (=
  (join compositions composers)
    #{{:composer "Bach" :name "The art" :country "Germany"} {:composer "Verdi" :name "Requiem" :country "Austria"}}))

(def nations #{{:nation "Germany" :language "German"} {:nation "Austria" :language "German"}}) 

(is (=
  (join nations composers {:nation :country})
    #{{:composer "Bach" :country "Germany" :language "German" :nation "Germany"}
    {:composer "Verdi" :country "Austria" :language "German" :nation "Austria"}}))


