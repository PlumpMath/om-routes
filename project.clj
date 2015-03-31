(defproject om-routes "0.1.0-SNAPSHOT"
  :description "Sync Om state with Navigation Bar"
  :url "http://github.com/bensu/om-routes"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}

  :scm {:name "git"
        :url "https://github.com/bensu/om-routes"}

  :signing {:gpg-key "sbensu@gmail.com"}

  :deploy-repositories [["clojars" {:creds :gpg}]]

  :dependencies [[org.clojure/clojure "1.6.0"]
                 [org.clojure/clojurescript "0.0-3126" :scope "provided"]
                 [org.clojure/core.async "0.1.346.0-17112a-alpha"]
                 [org.omcljs/om "0.8.8" :scope "provided"]
                 [bidi "1.18.7"]]

  :plugins [[lein-cljsbuild "1.0.5"]]

  :source-paths ["src"]

  :clean-targets ^{:protect false} ["examples/track_button/out"]
  
  :cljsbuild {
              :builds [{:id "track-button"
                        :source-paths ["src" "examples/track_button/src"]
                        :compiler {:output-to "examples/track_button/out/track_button.js"
                                   :output-dir "examples/track_button/out"
                                   :optimizations :none
                                   :main examples.track-button.core
                                   :asset-path "out"
                                   :source-map true
                                   :source-map-timestamp true
                                   :cache-analysis true}}
                       {:id "sorting"
                        :source-paths ["src" "examples/sorting/src"]
                        :compiler {:output-to "examples/sorting/out/sorting.js"
                                   :output-dir "examples/sorting/out"
                                   :optimizations :none
                                   :main examples.sorting.core
                                   :asset-path "out"
                                   :source-map true
                                   :source-map-timestamp true
                                   :cache-analysis true}}
                       {:id "refcursors"
                        :source-paths ["src" "examples/refcursors/src"]
                        :compiler {:output-to "examples/refcursors/out/refcursors.js"
                                   :output-dir "examples/refcursors/out"
                                   :optimizations :none
                                   :main examples.refcursors.core
                                   :asset-path "out"
                                   :source-map true
                                   :source-map-timestamp true
                                   :cache-analysis true}}]})
