(ns main
  (:require [org.httpkit.client :as http]
            [api-key :refer [+api-key+]]
            [jsonista.core :as j])
  (:import [java.awt Frame Color]))

;;;; EPIC: Fetch data from OpenWeather API

;;; Fetch 5-day forecast for Helsinki using your API key


(defonce response (-> (http/get (str "https://api.openweathermap.org/data/2.5/forecast?q=Helsinki&units=metric&appid=" +api-key+))
                      (deref)
                      :body
                      (j/read-value (j/object-mapper {:decode-key-fn keyword}))))
      
;;; Transform the data into a seq containing points with :x = timestamp, :y = temperature

(def points
  (map (fn [m] {:x (:dt m) :y (get-in m [:main :temp])}) (:list response)))

;;;; EPIC: Plot temperature curve in Java Frame

;;; Helper function for scaling the points to fit the 

(defn scale-points
  [points w h]
  "Scale points in given seq. Each point is a map with :x and :y keys for coordinates.
   Points are scaled so that they fit within a 2D space from [0, 0] to [w-1, h-1]"
  (let [min-x (apply min (map :x points))
        max-x (apply max (map :x points))
        min-y (apply min (map :y points))
        max-y (apply max (map :y points))
        range-x (- max-x min-x)
        range-y (- max-y min-y)]
    (map (fn [{:keys [x y]}]
           {:x (* (- w 1) (/ (- x min-x) range-x))
            :y (* (- h 1) (/ (- y min-y) range-y))}) points)))

;;; Create a java.awt.Frame 

#_(defonce frame (Frame.))

;;; Find method that sets the visibility of Frame

(for [m (.getMethods Frame)
      :let [name (.getName m)]
      :when (re-find #"Vis" name)]
  name)
#_(.setVisible frame true)

;;; Set the size of the frame and make it static, i.e., unresizable

(defonce frame (doto (Frame.)
                 (.setSize 640 480)
                 (.setResizable false)))

;;; Get the Graphics2D context of the frame and plot the points on the frame

(defonce gfx (.getGraphics frame))

(.setColor gfx Color/RED)

(.clearRect gfx 0 0 640 480)

(doseq [[p1 p2] (partition 2 1 (scale-points points 640 440))]
  (.drawLine gfx (:x p1) (+ 40 (:y p1)) (:x p2) (+ 40 (:y p2))))
