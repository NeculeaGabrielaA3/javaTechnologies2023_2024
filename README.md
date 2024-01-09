# javaTechnologies2023_2024
Repository for Java Technologies labs.

# Laborator 8 - homework:
  # Exercitiul 1:
  
   Pentru acest laborator am creat un serviciu web RESTful care implementează operațiunile CRUD pentru entitatea timetable. Serviciul se află în clasa TimetableResource și are următoarele metode:
  - createTimetable(POST) : se creaza o noua resursa
  - deleteTimetable(DELETE) : se sterge o resursa
  - updateTimetable(PUT) : se modifica o resursa
  - getTimetableForUser(GET) : returneaza timetable-urile specifice unui anumit user
  - getAllTimetables(GET) : returneaza toate timetable-urile
      Am adăugat suport pentru documentație folosind pachetul OpenApi. Documentația poate fi găsită la http://localhost:8080/openapi, apoi trebuie să copiați json-ul generat și să-l postați într-un editor online precum https://editor.swagger.io/, unde veți vedea toate 
   informațiile despre API. Am adăugat informații despre fiecare metodă, o descriere scurtă, detalii despre tipul cererii și răspunsului pentru a fi mai specific și codurile de stare care ar trebui returnate în situații specifice.
  
  # Exercitiul 2:
  De asemenea, am creat un filtru CacheDFilter care funcționează ca un cache pentru lista de timetables. Când se face o cerere, se verifică lista din cache pentru a vedea dacă este goală și, dacă da, se va obține răspunsul de la cerere, altfel se va returna direct 
  lista existentă. Când un orar este creat/modificat/șters, lista devine nulă, astfel încât data viitoare când lista este necesară, aceasta va fi actualizată cu toate documentele din baza de date.

# Laborator 4 - homework

Am configurat un pool la baza de date și am creat un DataSource pentru a obține conexiuni la baza de date.

Am creat cele doua sabloane: dataEdit.xhtml si dataView.xhtml.

"page.xhtml": Pagina generală a aplicației cu antet (titlu și meniu), conținut și subsol (drepturi de autor și versiune). header-ul, footer-ul sunt în fișiere .xhtml distincte.

"dataView.xhtml": Pagină generică pentru afișarea datelor sub formă de tabel.

"dataEdit.xhtml": Pagină generică pentru editarea datelor, sub forma unui dialog cu un formular.

studentView.xhtml si projectView.xhtml folosesc template-ul dataView.xhtml, plus lucruri necesare fiecareia.

createStudent.xhtml si createProject.xhtml folosesc template-ul dataEdit.xhtml.

Clasele StudentBean si ProjectBean sunt utilizate pentru managerierea studentilor, respectiv proiectelor.

# Laborator 3 - homework

Accesand: http://localhost:8080/L3FINAL-1.0-SNAPSHOT/faces/projects.xhtml veti putea vedea proiectele. De asemenea veti putea adauga si sterge un proiect.
Accesand: http://localhost:8080/L3FINAL-1.0-SNAPSHOT/faces/seeStudents.xhtml veti putea vedea studentii. De asemenea veti putea adauga, sterge un student, veti putea vedea ce proiecte au fost asociate acelui student, veti putea asocia un nou proiect studentului respectiv.

Avem 3 tabele: student, project si project_student(Acest tabel este folosit pentru a realiza o asociere între studenți și proiecte)

Fișierele xhtml sunt utilizate pentru definirea interfeței utilizatorului și a componentelor JSF. Ele conțin etichete specifice JSF, cum ar fi <h:form>, <p:dataTable>, <p:commandButton>, etc.

Dialogurile create cu ajutorul componentelor p:dialog din PrimeFaces permit adăugarea de studenți și proiecte și afișarea detaliilor proiectelor asociate unui student.

# Laborator 2

Clasa "UploadServlet" servletul care gestionează cererile HTTP de tip POST pentru încărcarea unui fișier și procesează acest fișier pentru a genera o reprezentare a unui graf, apoi redirecționează utilizatorii către o pagină numită "result.jsp" pentru a afișa rezultatele procesării acestui fișier in format DIMACS.

Clasa "Graph" este o clasa care modeleaza graful si proprietatile sale (nr noduri, nr muchii, nr componente conexe).

Clasa "GraphPropertiesService" este clasa care se ocupa de business-logic-ul aplicatiei, determina proprietatile grafului.

Clasa "LoggingFilter": A web filter that will log all requests received by input.jsp.

# Laborator 1
In clasa DigitsOfNumberServlet se afla compulsory-ul de la acest laborator. Servletul primeste un parametru "number", si afiseaza in format HTML o lista ordonata cu cifrele numarului respectiv.

In clasa GenerateTreeMatrixServlet se afla homework-ul de la acest laborator. In metoda "doPost" afisam un form cu un field si un buton de submit, in acel field va trebuie introdus numarul de noduri pentru arborele ce va fi generat. Genereaza un arbore si creaza matricea de adiacenta iar apoi afiseaza aceasta matrice sub forma unul table in HTML. Pentru partea de afisarea in server log a informatiilor despre request, avem metoda "serverInfoAboutRequest", unde sunt preluate informatiile necesare utilizand metodele clasei HttpServletRequest. 
