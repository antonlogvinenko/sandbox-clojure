(ns concurrency (:use clojure.contrib.test-is))

;refs and transactional memory
(def current-track (ref "Mars"))

(is (= (deref current-track) "Mars"))
(is (= @current-track "Mars"))

(dosync (ref-set current-track "Venus"))
(is (= @current-track "Venus"))

(def messages (ref ()))
(defn add-message [msg]
  (dosync (alter messages conj msg)))
(add-message "abc")
(add-message "def")
(is (= @messages ["def" "abc"]))

(defn add-message [msg]
  (dosync (commute messages conj msg)))
(add-message "ghi")
(add-message "jkl")

(defstruct message :sender :text)
(def validate-message-list
  (partial every? #(and (:sender %) (:text %))))
(def messages (ref () :validator validate-message-list))
(add-message (struct message "stu" "legit message"))
(is (= @messages '({:sender "stu" :text "legit message"})))

;atoms
(def current-track (atom {:title "Credo" :composer "Byrd"}))
(is (= (deref current-track) {:title "Credo" :composer "Byrd"}))
(is (= @current-track {:title "Credo" :composer "Byrd"}))

(reset! current-track {:title "Spem in Alium" :composer "Tallis"})
(is (= @current-track {:title "Spem in Alium" :composer "Tallis"}))

(swap! current-track assoc :title "Sancte Deus")
(is (= @current-track {:title "Sancte Deus" :composer "Tallis"}))


;agents
(def counter (agent 0))
(send counter inc)
;use sendoff for blocking function
(is (= @counter 1))
;(await & agents)
(await-for 500 counter)

(def counter (agent 0 :validator number?))
(send counter (fn [] "boo"))
;(println @agent now complains about exception: errors)
;(println (agent-errors counter))
(clear-agent-errors counter)
(is (= @counter 0))


;per-thread vars
;def
;binding
;set! for thread local dynamic binding