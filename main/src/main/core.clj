(ns main.core
 (:require [clojure.data.json :as json])
 (:require [clojure.test :as test]))

(use 'clojure.java.io)
(use 'clj-webdriver.taxi)

(import 'org.openqa.selenium.phantomjs.PhantomJSDriver
        'org.openqa.selenium.remote.DesiredCapabilities)

(use '[clj-webdriver.driver :only [init-driver]])

(set-driver! (init-driver {:webdriver (PhantomJSDriver. (DesiredCapabilities. ))}))

(defn pot[x] (* x x) )
(pot 3)


 (defn nrange[x y] (map (fn [x] (+ y x)) (range (- y x ))) )
 (def fstr "http://200.48.102.67/pecaoe/sipe/HojaVida.htm?p=72&op=140&c=")

 (to (str fstr 104272))
 (map to (map (fn [x] (str fstr x)) (nrange 1)))

 ;"txtCargoPostula"  "txtLugarPostula" "txtFormaDesignacion" "txtDNI" "txtApellidoPaterno" "txtApellidoMaterno" "txtNombres" "txtFechaNacimiento"
 ; "txtSexo" "txtCorreoElectronico" "txtPais" "txtDepartamentoNac" "txtProvinciaNac" "txtDistritoNac" "txtLugarResicencia"
 ; "txtLugarDepartamentoRes" "txtLugarDepartamentoRes" "txtLugarProvinciaRes" "txtLugarDistritoRes" "txtTiempoRes"
 ; "txtPadre" "txtMadre" "txtConyuge"

(def labels ["#txtCargoPostula" "#txtLugarPostula" "#txtDNI" "#txtApellidoPaterno" "#txtApellidoMaterno" "#txtNombres" "#txtSexo"])
;(def labels ["#txtDNI"])


(def results
   (map (fn [x]
          (cond
           (test/is (to (str fstr x)))
           (zipmap [:tecnico :secundaria :primaria :id :cargo :lugar :dni :paterno :materno :nombres :sexo ]
                   (conj
                    (pmap text labels)
                    x
                    (let [z (pmap text (elements "table#tblEducacionPrimaria td"))]
                      (pmap
                       (fn [x]
                         (zipmap [:nombre :lugar :estado :año]
                                 z))
                       (range (/ (count z) 5))))
                    (let [z (pmap text (elements "table#tblEducacionSecundaria td"))]
                      (pmap
                       (fn [x]
                         (zipmap [:nombre :lugar :estado :año]
                                 z))
                       (range (/ (count z) 5))))
                    (doall (pmap text (elements "table#tblTecnico td")))
                    ))))
        (range 1 180)))

(to (str fstr 12))

(with-open [wrtr (writer "/home/saiberz/projects/elecciones2014/resultado.json")]
    (.write wrtr (json/write-str results)))

(+ 3 2)
(let [z (pmap text (elements "table#tblEducacionPrimaria td"))]
  (pmap
   (fn [x]
     (zipmap [:nombre :lugar :estado :año]
             z))
   (range (/ (count z) 5))))
(pmap
 (fn [x]
   (zipmap [:nombre :lugar :estado :año]
           (pmap text (elements "table#tblEducacionPrimaria td"))))
 (range (/ (count (pmap text (elements "table#tblEducacionPrimaria td"))) 5)))


(test/is (to (str fstr 14)))
(text (element "#txtDNI"))
(map text (elements "table#tblTecnico td"))

(map
 (fn [x]
   (to x)
   (doall (map text (elements "table#tblEducacionPrimaria td"))))
 (map (fn [x] (str fstr x)) [1 2]))

;(map text (elements "table#tblEducacionPrimaria td") )
;(text (elements "table#tblEducacionPrimaria td"))
;(map (fn [x] (str fstr x)) [1 2])
;(let [wtf (elements "table#tblEducacionPrimaria td") ]

;(defn multis [x]
;  (let [a x]
;    (to (str fstr x))
;    (map text (elements "table#tblEducacionPrimaria td") ) ) )
;(map text (elements "table"))
;(multis 1)
;(to (str fstr 12 ))
;(map multis [1 2])

 (conj [2 3 4] (map text (elements "table#tblEducacionPrimaria td")) )

 (let [wtf (map text (elements "table#tblEducacionPrimaria td")) ]
                    (if (empty? wtf)"" wtf))

 ;(map text (elements "table#tblEducacionPrimaria td"))
(conj
 (map text labels)
 (map text (elements "table#tblEducacionPrimaria td")) )




(to (str fstr 104272))
(elements "td")
(elements "table#tblEducacionPrimaria")
(map text (elements "table#tblEducacionPrimaria td"))
(map text (elements "table#tblEducacionSecundaria td"))
(count (map text (elements "table#tblEducacionSecundaria td")))
;(find-table-cell "table#tblEducacionPrimaria" [0 0])
