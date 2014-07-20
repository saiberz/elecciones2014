(ns main.core
 (:require [clojure.data.json :as json]))

(use 'clojure.java.io)
(use 'clj-webdriver.taxi)

(import 'org.openqa.selenium.phantomjs.PhantomJSDriver
        'org.openqa.selenium.remote.DesiredCapabilities)

(use '[clj-webdriver.driver :only [init-driver]])

(set-driver! (init-driver {:webdriver (PhantomJSDriver. (DesiredCapabilities. ))}))

(defn pot[x] (* x x) )
(pot 3)
(map (fn [x] (+ 1 x)) (range 10))


 (defn nrange[x] (map (fn [x] (+ 1234 x)) (range x)) )
 (def fstr "http://200.48.102.67/pecaoe/sipe/HojaVida.htm?p=72&op=140&c=")

 (to (str fstr 104272))
 (map to (map (fn [x] (str fstr x)) (nrange 1)))

 ;"txtCargoPostula"  "txtLugarPostula" "txtFormaDesignacion" "txtDNI" "txtApellidoPaterno" "txtApellidoMaterno" "txtNombres" "txtFechaNacimiento"
 ; "txtSexo" "txtCorreoElectronico" "txtPais" "txtDepartamentoNac" "txtProvinciaNac" "txtDistritoNac" "txtLugarResicencia"
 ; "txtLugarDepartamentoRes" "txtLugarDepartamentoRes" "txtLugarProvinciaRes" "txtLugarDistritoRes" "txtTiempoRes"
 ; "txtPadre" "txtMadre" "txtConyuge"

;(def labels ["#txtCargoPostula" "#txtLugarPostula" "#txtDNI" "#txtApellidoPaterno" "#txtApellidoMaterno" "#txtNombres" "#txtSexo"])
(def labels ["#txtDNI"])

(def results
  (map (fn [x]
         (to x)
         (zipmap [:primaria :cargo :lugar :dni :paterno :materno :nombres :sexo]
                 (conj
                  (map text labels)
                  (let [wtf (map text (elements "table#tblEducacionPrimaria td")) ]
                    (if (empty? wtf)"" wtf))
                  )))
       ;(text (elements "table#tblEducacionPrimaria td"))
       (map (fn [x] (str fstr x)) (nrange 1))))

 (with-open [wrtr (writer "/home/saiberz/projects/test.json")]
    (.write wrtr (json/write-str results)))

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
