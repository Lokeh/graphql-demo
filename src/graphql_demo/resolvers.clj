(ns graphql-demo.resolvers
  (:require
    [com.walmartlabs.lacinia.schema :as schema]))

(defn add-type [value tag] (assoc value :type tag))

(defn tag [value] (schema/tag-with-type value (:type value)))

(def humans-data
  (map #(add-type % :human)
       [{:id          "1000"
         :name        "Luke Skywalker"
         :friends     ["1002", "1003", "2000", "2001"]
         :appears_in  ["NEWHOPE" "EMPIRE" "JEDI"]
         :home-planet "Tatooine"
         :force-side  "3001"}
        {:id          "1001"
         :name        "Darth Vader"
         :friends     ["1004"]
         :appears_in  ["NEWHOPE" "EMPIRE" "JEDI"]
         :home_planet "Tatooine"
         :force_side  "3000"}
        {:id          "1003"
         :name        "Leia Organa"
         :friends     ["1000", "1002", "2000", "2001"]
         :appears_in  ["NEWHOPE" "EMPIRE" "JEDI"]
         :home_planet "Alderaan"
         :force_side  "3001"}
        {:id         "1002"
         :name       "Han Solo"
         :friends    ["1000", "1003", "2001"]
         :appears_in ["NEWHOPE" "EMPIRE" "JEDI"]
         :force_side "3001"}
        {:id         "1004"
         :name       "Wilhuff Tarkin"
         :friends    ["1001"]
         :appears_in ["NEWHOPE"]
         :force_side "3000"}]))

(def ^:private droids-data
  (map #(add-type % :droid)
       [{:id               "2001"
         :name             "R2-D2"
         :friends          ["1000", "1002", "1003"]
         :appears_in       ["NEWHOPE" "EMPIRE" "JEDI"]
         :primary_function "ASTROMECH"}
        {:id               "2000"
         :name             "C-3PO"
         :friends          ["1000", "1002", "1003", "2001"]
         :appears_in       ["NEWHOPE" "EMPIRE" "JEDI"]
         :primary_function "PROTOCOL"}]))

(def ^:private character-data (concat humans-data droids-data))


(def ^:private hero-data
  {"NEWHOPE" "2001"
   "EMPIRE"  "1002"
   "JEDI"    "1000"})

(defn ^:private first-match [data key value]
  (-> (filter #(= (get % key) value) data)
      first))

(defn resolve-hero
  [ctx args value]
  (let [episode (:episode args "NEWHOPE")
        hero-id (get hero-data episode)]
    (first-match character-data :id hero-id)))

(defn resolve-droid
  [ctx args value]
  (first-match droids-data :id (:id args)))

(defn resolve-friends
  [ctx args value]
  (println "resolving friends")
  (map #(tag (first-match character-data :id %)) (:friends value)))

(defn resolve-human
  [ctx args value]
  (println "resolving humans")
  (let [match (first-match humans-data :id (:id args))]
    (tag match)))

