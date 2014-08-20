(ns webdata.core
                                        ;(:gen-class)
  (:require [clojure.data.json :as json])
  (:require [clojure.test :as test]))

                                        ;                                      
(use 'clojure.java.io)
(use 'clj-webdriver.taxi)
(use '[clj-webdriver.driver :only [init-driver]])
(import 'org.openqa.selenium.phantomjs.PhantomJSDriver
        'org.openqa.selenium.remote.DesiredCapabilities)
(set-driver! (init-driver {:webdriver (PhantomJSDriver. (DesiredCapabilities. ))}))

;; URL 
(def fstr "http://200.48.102.67/pecaoe/sipe/HojaVida.htm?p=72&op=140&c=")


(defn fill-table [table-id table-keys]
  "Add all elements to table"
  (let [z (pmap text (elements table-id))
        siz (count z)
        siz-r (count table-keys)]
    (pmap (fn [x]( zipmap table-keys (drop (* x (inc siz-r)) z )))
          (range (dec (/ siz siz-r))))))

(defn results [a b]
  (
   let [
        postulacion ["#txtCargoPostula" "#txtLugarPostula" "#txtFormaDesignacion"]
        personal ["#txtDNI" "#txtSexo" "#txtApellidoMaterno" "#txtApellidoPaterno" "#txtNombres"]
        remuneracion ["#txtIngresoRemuneracionPublica" "#txtIngresoRemuneracionPrivada" "#txtIngresoRemuneracionTotal"]
        renta ["#txtIngresoRentaPublica" "#txtIngresoRentaPrivada" "#txtIngresoRentaTotal"]
        otrosing ["#txtIngresoOtrosPublico" "#txtIngresoOtrosPrivado" "#txtIngresoOtrosTotal"]
        nacimiento ["#txtFechaNacimiento" "#txtPais" "#txtDepartamentoNac" "#txtProvinciaNac" "#txtDistritoNac"]
        residencia [ "#txtLugarDepartamentoRes" "#txtLugarProvinciaRes"  "#txtLugarDistritoRes" "#txtLugarResicencia" "#txtTiempoRes" ]
        familiar ["#txtPadre" "#txtMadre" "#txtConyuge"]

        detalleing [:Publico :Privado :Total]
        field-Nacimiento [:Fecha :Pais :Departamento :Pronvincia :Distrito]
        field-Residencia [:Departamento :Pronvincia :Distrito :Lugar :Tiempo]
        field-familiar [:Padre :Madre :Conyuge]
        field-Ingresos [:Remuneraciones :Renta :Otros :Total]
        field-Educacion [:Primaria :Secundaria :Tecnico :Universitaria :Postgrado ]
        field-Politica [:CargoE :CargoP :Militancia]
        field-Bienes [:Inmuebles :Muebles :Egresos]
        field-Sentencias [:Penal :Civil]
        field-Experiencia [:Centro :Sector :Desde :Hasta :Cargo :Ubicacion]
        field-OtraExp [:Cargo :Entidad :Periodo]
        field-Egresos [:Detalle :Monto]
        field-Observaciones [:Referencia :Anotacion]

        field-Otros [:uno :dos :AntPenal :AntCivil]
        keys-Postulacion [:Cargo :Lugar :Designacion]
        keys-Personal [:DNI :Sexo :ApMaterno :ApPaterno :Nombre]
        keys-Educacion [:nombre :lugar :estado :año ]
        keys-Tecnico [:Nombre :Lugar :Especialidad :Curso :Estado :Periodo]
        keys-Universitario [:Universidad :Lugar :Facultad :Carrera :Estado :Grado :Periodo]
        keys-Postgrado [:Tipo :Centro :Lugar :Especialidad :Estado :Grado :Periodo]
        keys-CargosP [:Organizacion :Ambito :Cargo :Periodo]
        keys-CargosE [:Organizacion :Ambito :Cargo :Lugar :Proceso :Periodo]
        keys-Militancia [:OrganizacionPolitica :Periodo]
        keys-Ingresos [:t :RemPub :RemPriv :RemTotal :RenPub :RenPriv :RenTotal :OtrosPub :OtrosPriv :OtrosTotal ]
        keys-Inmuebles [:Tipo :Direccion :Ficha :Autovaluo]
        keys-Muebles [:Bien :Tipo :Descripcion/Modelo :Placa/Caracteristicas :Valor]
        keys-General [:one :two :three :four :five :six :sept]
        keys-Penal [:Expediente :FechaSentencia :Juzgado :Delito :Fallo]
        keys-Civil [:Materia :Expediente :Juzgado :Demanda :Fallo]
        ]

    (map (fn [x]
           (try
             (to (str fstr x))
             (zipmap [:Postulacion :Personal :Nacimiento :Residencia :Familiar :Ingresos :Creditos :Educacion :Experiencia :Politica :Bienes :Antecedentes :Observaciones :foto :id]
                     (conj
                      []
                      (zipmap keys-Postulacion (pmap text postulacion))
                      (zipmap keys-Personal (pmap text personal))
                      (zipmap field-Nacimiento (pmap text nacimiento))
                      (zipmap field-Residencia (pmap text residencia))
                      (zipmap field-familiar (pmap text familiar))

                      (zipmap field-Ingresos
                              (conj []
                                    (zipmap detalleing (pmap text remuneracion))
                                    (zipmap detalleing (pmap text renta))
                                    (zipmap detalleing (pmap text otrosing))
                                    (text "#txtCandidatoIngresoTotal"))
                              )
                      (fill-table "table#tblEgreso td" field-Egresos)
                      (zipmap field-Educacion
                              (conj []
                                    (fill-table "table#tblEducacionPrimaria td" keys-Educacion)
                                    (fill-table "table#tblEducacionSecundaria td" keys-Educacion)
                                    (fill-table "table#tblTecnico td" keys-Tecnico)
                                    (fill-table "table#tblUniversitario td" keys-Universitario)
                                    (fill-table "table#tblPostgrado td" keys-Postgrado)))
                      
                      (zipmap [:Experiencia :Otros]
                              (conj []
                                    (fill-table "table#tblExperiencia td" field-Experiencia)
                                    (fill-table "table#tblAdicional td" field-OtraExp)))

                      (zipmap field-Politica
                              (conj []
                                    (fill-table "table#tblCargoPartidario td" keys-CargosP)
                                    (fill-table "table#tblCargoEleccion td" keys-CargosE)
                                    (fill-table "table#tblRenuncias td" keys-Militancia)))
                      (zipmap field-Bienes
                              (conj []
                                    (fill-table "table#tblCandidatoInmueble td" keys-Inmuebles)
                                    (fill-table "table#tblCandidatoMueble td" keys-Muebles)))

                      (zipmap field-Sentencias
                              (conj []
                                    (fill-table "table#tblAmbitoPenal td" keys-Penal)
                                    (fill-table "table#tblAmbitoCivil td" keys-Civil)))
                      (fill-table "table#tblObservaciones td" field-Observaciones)
                      (attribute "#fotoCandidato" :src)
                      x
                      ))
             (catch Exception e)
             )
           )
         (range a b))))


;; Where to download the data in json format 
(with-open [wrtr (writer "/home/saiberz/projects/Resultado.json")]
  (.write wrtr (json/write-str (results 10 20))))

(println-str "Export Exito!")


;; (pmap (fn [x]
;;         (let [dir (str "/home/saiberz/projects/elecciones2014/resultado" x ".json")]
;;           (with-open [wrtr (writer dir)]
;;             (.write wrtr (json/write-str (results (inc (* 20 x)) (* 20 (inc x))))))
;;           )) (range 0 1))


