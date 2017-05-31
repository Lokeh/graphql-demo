(ns graphql-demo.schema
  (:require [clojure.java.io :as io]
            [clojure.edn :as edn]
            [com.walmartlabs.lacinia.util :refer [attach-resolvers]]
            [com.walmartlabs.lacinia.schema :as schema]
            [graphql-demo.resolvers :as resolvers]))

(def star-wars-schema
  (-> (io/resource "star-wars-schema.edn")
      slurp
      edn/read-string
      (attach-resolvers {:resolve-hero    resolvers/resolve-hero
                         :resolve-human   resolvers/resolve-human
                         :resolve-droid   resolvers/resolve-droid
                         :resolve-friends resolvers/resolve-friends})
      schema/compile))



