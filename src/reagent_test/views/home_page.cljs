(ns reagent-test.views.home-page
  (:require [reagent.core :as reagent]))

(def coords (reagent/atom []))
(def map-bounds (reagent/atom {}))

(def *map* (atom nil))

(def id-counter (atom 0))

(defn coord [c]
  ^{:key (:id c)} [:li
                   [:span (str (:id c) ", " (:lat c) " - " (:lng c))]])

(defn coord-list []
  [:div
   [:h3 "Coord list"]
   [:ul
    (for [c @coords]
      [coord c])]])

(defn bounds []
  (let [{sw-lat :sw-lat
         sw-lng :sw-lng
         ne-lat :ne-lat
         ne-lng :ne-lng} @map-bounds]
    [:div (str sw-lat ", " sw-lng " - " ne-lat ", " ne-lng)]))


(defn home-render []
  [:div
   [:div#map ]
   [:div#coordList]
   [:div#bounds]
   ])

(defn add-coord [lat lng]
  (let [marker (.marker js/L #js [lat lng])]
    (.addTo marker @*map*))
  (swap! coords conj {:id @id-counter :lat lat :lng lng})
  (swap! id-counter + 1))

(defn click-map [e]
  (let [lat (.. js/e -latlng -lat) lng (.. js/e -latlng -lng)]
    (add-coord lat lng)))

(defn move-map [e]
  (let [bounds (.getBounds @*map*)]
    (let [sw (.getSouthWest bounds)
          ne (.getNorthEast bounds)]
      (println (.. js/sw -lat) ", "(.. js/sw -lng) " - " (.. js/ne -lat) ", "(.. js/ne -lng))
      (reset! map-bounds {:sw-lat (.. js/sw -lat) :sw-lng (.. js/sw -lng) :ne-lat (.. js/ne -lat) :ne-lng (.. js/ne -lng)}))))

(defn home-did-mount []
  (let [map (.setView (.map js/L "map") #js [51.505 -0.09] 13)]

    (.addLayer map (js/L.Google. "ROADMAP"))
    (.on map "click" click-map)
    (.on map "moveend" move-map)
    (reset! *map* map)
    (reagent/render-component [coord-list]
                              (.getElementById js/document "coordList"))
    (reagent/render-component [bounds]
                              (.getElementById js/document "bounds"))
    (move-map nil)
    ))

(defn home-page []
  (reagent/create-class {:render home-render
                         :component-did-mount home-did-mount}))
