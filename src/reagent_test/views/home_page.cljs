(ns reagent-test.views.home-page
  (:require [reagent.core :as reagent]))

(def coords (reagent/atom []))

(def *map* (atom nil))

(def id-counter (atom 0))

(defn coord [c]
  ^{:key (:id c)} [:li
                   [:span (str (:id c) " " (:lat c) " - " (:lng c))]])

(defn coord-list []
  [:div
   [:h3 "Coord list"]
   [:ul
    (for [c @coords]
      [coord c])]])

(defn home-render []
  [:div
   [:div#map ]
   [:div#coordList]
   ])

(defn add-coord [lat lng]
  (let [marker (.marker js/L #js [lat lng])]
    (.addTo marker @*map*))
  (swap! coords conj {:id @id-counter :lat lat :lng lng})
  (swap! id-counter + 1))

(defn click-map [e]
  (let [lat (.. js/e -latlng -lat) lng (.. js/e -latlng -lng)]
    (add-coord lat lng)))

(defn home-did-mount []
  (let [map (.setView (.map js/L "map") #js [51.505 -0.09] 13)]

    (.addLayer map (js/L.Google. "ROADMAP"))
    (.on map "click" click-map)
    (reset! *map* map)
    (reagent/render-component [coord-list]
                              (.getElementById js/document "coordList"))
    ))

(defn home-page []
  (reagent/create-class {:render home-render
                         :component-did-mount home-did-mount}))
