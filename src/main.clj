(ns main)

;;;; EPIC: Fetch data from OpenWeather API

;;; Fetch 5-day forecast for Helsinki using your API key

;;; Transform the data into a seq containing points with :x = timestamp, :y = temperature

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

;;; Find method that sets the visibility of Frame

;;; Set the size of the frame and make it static, i.e., unresizable

;;; Get the Graphics2D context of the frame and plot the points on the frame
