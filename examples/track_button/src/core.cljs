(ns examples.track-button.core
    (:require-macros [cljs.core.async.macros :refer [go]])
    (:require [om.core :as om :include-macros true]
              [cljs.core.async :as async :refer [put! chan]]
              [om-routes.core :as routes]
              [om.dom :as dom :include-macros true]))

(enable-console-print!)

;; Main State

(defonce app-state (atom {:nav {:last-click nil}}))

;; Navigation API

(def nav-path :nav)

(defn get-nav [data]
  (get-in data [nav-path :last-click]))

(defn nav-to [view-cursor method]
  (om/update! view-cursor [nav-path :last-click] method :om-routes.core/nav))

(defn url->state [{:keys [last-click]}]
  {:last-click (keyword last-click)})

(def route [["#" :last-click] (routes/make-handler url->state)])

;; View

(defn view-component [data owner]
  (om/component
   (dom/div nil
            (dom/h1 nil (case (get-nav data)
                          :button "A button got me here"
                          :link "A link got me here"
                          "Who got me here?"))
            (dom/button #js {:onClick (fn [_] (nav-to data :button))} "Button") 
            (dom/br nil)
            (dom/a #js {:href "#link"} "Link"))))

;; Main Component

(let [tx-chan (chan)
      tx-pub-chan (async/pub tx-chan (fn [_] :txs))]
  (om/root
   (fn [data owner]
     (reify
       om/IRender
       (render [_]
         (om/build routes/om-routes data
                   {:opts {:view-component view-component
                           :route route
                           :debug true
                           :nav-path nav-path}}))))
   app-state
   {:target (. js/document (getElementById "app"))
    :shared {:tx-chan tx-pub-chan}
    :tx-listen (fn [tx-data root-cursor]
                 (put! tx-chan [tx-data root-cursor]))}))
