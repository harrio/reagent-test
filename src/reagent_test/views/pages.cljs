(ns reagent-test.views.pages
  (:require [reagent-test.views.home-page :refer [home-page]]
            [reagent-test.views.about-page :refer [about-page]]))

(def pages {:home-page home-page
            :about-page about-page})
