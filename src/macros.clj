(ns macros
  (:require [com.rpl.specter :refer :all]))

(defmacro ->% [& forms] `(fn [x#] (-> x# ~@forms)))
(defmacro ->>% [& forms] `(fn [x#] (->> x# ~@forms)))

(defmacro transform! [r apath transform-fn] `(swap! ~r (->>% (transform ~apath ~transform-fn))))
(defmacro multi-transform! [r apath] `(swap! ~r (->>% (multi-transform ~apath))))
(defmacro setval! [r apath aval] `(swap! ~r (->>% (setval ~apath ~aval))))
