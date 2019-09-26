(ns util.okta-api
  (:require [clj-http.client :as client]))

; requires the clj http dependency

(defn get-users
  ;list users api
  [okta-config]
  (client/get (str "https://" (okta-config :okta-domain) "/api/v1/users")
              {:accept       :application/json
               :content-type :application/json
               :headers      {:authorization (str "SSWS " (okta-config :api-token))}}))

(defn get-token
  ;required for authorization web flow to retrieve the tokens from the authorization code
  [okta-config code]
  (let [ (client/post (str "https://" (okta-config :okta-domain) "/oauth2/default/v1/token")
               {:accept       :application/json
                :content-type :application/x-www-form-urlencoded
                :basic-auth [(okta-config :client-id) (okta-config :secret)]
                :query-params {:grant_type   "authorization_code"
                               :redirect_uri (okta-config :redirect-uri)
                               :code         code}})]))
