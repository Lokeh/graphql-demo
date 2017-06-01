(ns graphql-demo.service
  (:require [io.pedestal.http :as http]
            [io.pedestal.http.route :as route]
            [io.pedestal.http.body-params :as body-params]
            [ring.util.response :as ring-resp]
            [com.walmartlabs.lacinia.pedestal :as lacinia]
            [graphql-demo.schema :as schema]))

(def service (lacinia/pedestal-service schema/star-wars-schema {:graphiql true}))
