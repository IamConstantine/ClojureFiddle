(ns util.jwt-verifier
  (:import [com.okta.jwt JwtVerifiers])
  (:import [java.time Duration]))

(defn- jwt-token-verifier
  [okta-config]
  (.build
    (doto (JwtVerifiers/accessTokenVerifierBuilder)
      (.setIssuer (str "https://" (config :okta-domain) "/oauth2/default"))
      (.setAudience (config :aud))
      (.setConnectionTimeout (Duration/ofMillis (okta-config :duration)))
      (.setReadTimeout (Duration/ofMillis (okta-config :duration))))))

(defn decode-access-token
  [access-token]
  (.decode jwt-token-verifier access-token))

(defn get-claims
  [access-token]
  (.getClaims (decode-access-token access-token)))
