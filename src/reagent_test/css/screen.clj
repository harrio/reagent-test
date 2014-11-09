(ns reagent-test.css.screen
  (:require  [garden.def :refer [defstyles]]
             [garden.units :as u :refer [em]]
             [garden.color :as color :refer [rgb]]))

(defstyles screen
           ;; Coloring Title
           [:div#title {:font-size (em 3)
                        :color (rgb 123 45 6)}]
           ;; Leafleat
           [:#map {:height "720px"}]

           [:.leaflet-map-pane {:z-index "2 !important"}]

           [:.leaflet-google-layer {:z-index "1 !important"}]

           )