(ns main.core
 (:require [clojure.data.json :as json])
 (:require [clojure.test :as test]))
(use 'clojure.java.io)
(use 'clj-webdriver.taxi)
(use '[clj-webdriver.driver :only [init-driver]])
(import 'org.openqa.selenium.phantomjs.PhantomJSDriver
        'org.openqa.selenium.remote.DesiredCapabilities)
(set-driver! (init-driver {:webdriver (PhantomJSDriver. (DesiredCapabilities. ))}))

 (def fstr "http://200.48.102.67/pecaoe/sipe/HojaVida.htm?p=72&op=140&c=")
 ;"txtCargoPostula"  "txtLugarPostula" "txtFormaDesignacion" "txtDNI" "txtApellidoPaterno" "txtApellidoMaterno" "txtNombres" "txtFechaNacimiento"
 ; "txtSexo" "txtCorreoElectronico" "txtPais" "txtDepartamentoNac" "txtProvinciaNac" "txtDistritoNac" "txtLugarResicencia"
 ; "txtLugarDepartamentoRes" "txtLugarDepartamentoRes" "txtLugarProvinciaRes" "txtLugarDistritoRes" "txtTiempoRes"
 ; "txtPadre" "txtMadre" "txtConyuge"
(defn fill-table [table-id table-keys]
 (let [z (pmap text (elements table-id))
       siz (count z)
       siz-r (count table-keys)]
   (pmap (fn [x]( zipmap table-keys (drop (* x (inc siz-r)) z )))
        (range (dec (/ siz siz-r))))))

(def results
    (
     let [
          labels ["#txtCargoPostula" "#txtLugarPostula" "#txtDNI" "#txtApellidoPaterno" "#txtApellidoMaterno" "#txtNombres" "#txtSexo" ]
          keys-Educacion [:nombre :lugar :estado :año ]
          keys-Tecnico [:Nombre :Lugar :Especialidad :Curso :Estado :Periodo]
          keys-Universitario [:Universidad :Lugar :Facultad :Carrera :Estado :Grado :Periodo]
          keys-Postgrado [:Tipo :Centro :Lugar :Especialidad :Estado :Grado :Periodo]
          keys-CargosP [:Organizacion :Ambito :Cargo :Periodo]
          keys-CargosE [:Organizacion :Ambito :Cargo :Lugar :Proceso :Periodo]
          keys-Militancia [:Denominacion :Periodo]
          keys-Ingresos [:t :RemPub :RemPriv :RemTotal :RenPub :RenPriv :RenTotal :OtrosPub :OtrosPriv :OtrosTotal :Total ]
          keys-Inmuebles [:tipo :direccion :ficha :autovaluo]
          ]

     (map (fn [x]
          (cond
           (test/is (to (str fstr x)))
           (zipmap [:Inmuebles :Ingresos :Militancia :CargosE :CargosP :Postgrado :Universitario :Tecnico :Secundaria :Primaria :ID :CargoPostula :LugarPostula :DNI :Ap.Paterno :Ap.Materno :Nombres :Sexo]
                   (conj
                    (map text labels)
                    x
                    ;;;tblEducacionPrimaria
                    ;;; lblEducacion
                    (fill-table "table#tblEducacionPrimaria td" keys-Educacion)
                    ;;;tblEducacionSecundaria
                    (fill-table "table#tblEducacionSecundaria td" keys-Educacion)
                    (fill-table "table#tblTecnico td" keys-Tecnico)
                    (fill-table "table#tblUniversitario td" keys-Universitario)
                    (fill-table "table#tblPostgrado td" keys-Postgrado)
                    (fill-table "table#tblCargoPartidario td" keys-CargosP)
                    (fill-table "table#tblCargoEleccion td" keys-CargosE)
                    (fill-table "table#tblRenuncias td" keys-Militancia)
                    (fill-table "table#tblIngresos td" keys-Ingresos)
                    (fill-table "table#tblCandidatoInmueble td" keys-Inmuebles)


                    ))))
        (range 104272 104280))))

(with-open [wrtr (writer "/home/saiberz/projects/elecciones2014/resultado3.json")]
    (.write wrtr (json/write-str results)))


;;test
(to (str fstr 104272))



