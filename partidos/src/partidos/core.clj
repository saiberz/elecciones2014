(ns partidos.core
  (:gen-class)
  (:require [clojure.data.json :as json]
            [monger.collection :as mc]
            [monger.core :as mg])
  (:require [clojure.test :as test]))

;(use 'clojure.java.io)
;(use 'alembic.still)
;(load-project)

(use 'clj-webdriver.taxi)
(use '[clj-webdriver.driver :only [init-driver]])
(import 'org.openqa.selenium.phantomjs.PhantomJSDriver
        'org.openqa.selenium.remote.DesiredCapabilities)
(set-driver!
 (init-driver
 {:webdriver (PhantomJSDriver. (DesiredCapabilities. ))}))

(def fstr "http://200.48.102.68/sipesg_erm2014/wf_BuscarListaCandidato_HV.aspx")
(to fstr)
(print (title))

(select-option "#ddlTipoEleccion" {:index 0})
;; (select-option "#ddlDepartamento" {:index 1})
;; (select-option "#ddlOrgPolitica" {:index 4})
;; (selected-options "#ddlTipoEleccion")
;; (selected-options "#ddlDepartamento")
;; (selected-options "#ddlOrgPolitica")
;; (click ".boton_buscar")
;; (click (find-element {:src "images/consulta/mas.png"}))

(defn get-resume []
  (let [links (elements "[src='images/consulta/hojavida1.png']")]
    (cond (not (empty? links))
          (pmap #(nth (re-find #"'(.+)'" (attribute % :onclick)) 1) (elements "[src='images/consulta/hojavida1.png']")))))

(defn get-candis []
  (cond (not (empty? (elements "table.body_tabla tbody tr td:nth-child(3)")))
        (pmap (fn[a b c d e f] (zipmap
                               [:dni :jur :nat :des :est :hdv] [a b c d e f]))
              (pmap text (elements "table.body_tabla tbody tr td:nth-child(3)"))
              (pmap text (elements "table.body_tabla tbody tr td:nth-child(7)"))
              (pmap text (elements "table.body_tabla tbody tr td:nth-child(8)"))
              (pmap text (elements "table.body_tabla tbody tr td:nth-child(9)"))
              (pmap text (elements "table.body_tabla tbody tr td:nth-child(10)"))
              (get-resume))))

(defn add-list []
  "Agrega los detalles de la lista"
  (merge (zipmap [:jee :expediente :solicitud :op :estado :exped]
                 (pmap #(text (nth (elements ".tabla_main td" ) %)) (range 1 6)))
         {:exp (re-find #"[0-9]+" (attribute (element "#imgDetalle") :onclick))}
         {:candis (get-candis)}))

(defn -main []
  (let [conn (mg/connect {:host "localhost" :port 27017})
        db   (mg/get-db conn "test")
        coll "regional"]
                                        ;  (loop [y 17]
    (loop [y 3]
                                        ;(loop [y (dec (count (elements "#ddlDepartamento.select option")))]
      (when (> y 0)
        (select-option "#ddlDepartamento" {:index y})
        (loop [x 7]
                                        ;    (loop [x (dec (count (elements "#ddlOrgPolitica.select option")))]
          (when (> x 0)
            (select-option "#ddlOrgPolitica" {:index x})
            (click ".boton_buscar")
            (click "[src='images/consulta/mas.png']")
                                        ;    (Thread/sleep 200)
;          (mc/insert db coll
            (mc/insert db coll (add-list))
            (println "Added")
;            (println (add-list))
            (recur (- x 1))))
        (recur (- y 1))))))


;; (print (add-list))

;; (keyword (range 5))
;; (map keyword (range 5))
;; (map (fn[x] (keyword x)) (range 5))
;; (keyword )
;; (let [dnis (get-dnis)]
;;  (zipmap
;;   (map keyword
;;        (map str (range (count dnis)))) dnis))

;; (zipmap [:1 :2 ]
;;         (zipmap :dni)
;; )
;; (zipmap [:dni :dni2] [1 2])
;; (k)
;; (assoc {} :Partido
;;        (zipmap [:one :two :three]
;;                [1 2 3]))
;; (map text (elements "table.body_tabla tbody tr td:nth-child(3)"))





;; (map #(+ 1 %) (range 1 5))
;; (add-list)


;; (mer )

;; (println (map (fn[x](text (nth (elements ".tabla_main td" ) x))) (range 1 6)))

;; (text (nth (elements ".tabla_main td" ) 2))
;; (text (nth (elements ".tabla_main td" ) 3))
;; (text (nth (elements ".tabla_main td" ) 4))
;; (text (nth (elements ".tabla_main td" ) 5))



;; (attribute (element "#imgDetalle") :onclick)
;; (element "")
;; (element "#imgPlanGobierno")

;; (nth (elements ".tabla_main td" ) 6)
;; (text (nth (elements "tr td [style = 'text-align: left;']" ) 0))

;; (map text (elements "tr td" ))
;; (elements "[align = 'text-align: left;']" )



;; (text (nth (elements "tr td [style = 'text-align: left;']" )))
;; (map text (elements "tbody tr td [style = 'text-align: left;']" ))
;; (println (map text (elements "td")))
;; style="text-align: left;"
;; (println (text (element "td")))
;; (map text (nth (elements "tr td") 0))
;; (elements "tr td")
;; (+ 2 3)


;; (text (last (elements "[align='left']" )));Estado



;; (conj [] 1 2)
;; (conj []
;;       "nombre" "123" "000270" "tacha" "url" "url")
;; (merge {:nombre "Nombre Lista"
;;  :codinnngo "123"
;;  :expediente "00070-2014- 077"
;;        } {:dnis [{:dni 12} 3 4]})

;; (conj [] (get-dnis))
;; (vector  (get-dnis))
;; (type (get-dnis))
;; (map keyword (get-dnis))
;; (zipmap [:1 :2 ] (get-dnis))

;Hints
;group-by
;array-map
;merge
;assoc
