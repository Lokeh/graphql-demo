(ns graphql-demo.server
  (:gen-class) ; for -main method in uberjar
  (:require [io.pedestal.http :as server]
            [com.walmartlabs.lacinia.pedestal :as lacinia]
            [graphql-demo.schema :as schema]
            ;; [graphql-demo.service :as service]
            ))

(def service (lacinia/pedestal-service schema/star-wars-schema {:graphiql true}))

;; This is an adapted service map, that can be started and stopped
;; From the REPL you can call server/start and server/stop on this service
(defonce runnable-service (server/create-server service))

(defn run-dev
  "The entry-point for 'lein run-dev'"
  [& args]
  (println "\nCreating your [DEV] server...")
  (server/start service))

(defn -main
  "The entry-point for 'lein run'"
  [& args]
  (println "\nCreating your server...")
  (server/start runnable-service))
