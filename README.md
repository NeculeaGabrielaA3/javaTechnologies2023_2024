# javaTechnologies2023_2024
Repository for Java Technologies labs.

# Laborator 1
In clasa DigitsOfNumberServlet se afla compulsory-ul de la acest laborator. Servletul primeste un parametru "number", si afiseaza in format HTML o lista ordonata cu cifrele numarului respectiv.

In clasa GenerateTreeMatrixServlet se afla homework-ul de la acest laborator. In metoda "doPost" afisam un form cu un field si un buton de submit, in acel field va trebuie introdus numarul de noduri pentru arborele ce va fi generat. Genereaza un arbore si creaza matricea de adiacenta iar apoi afiseaza aceasta matrice sub forma unul table in HTML. Pentru partea de afisarea in server log a informatiilor despre request, avem metoda "serverInfoAboutRequest", unde sunt preluate informatiile necesare utilizand metodele clasei HttpServletRequest. 

# Laborator 2

Clasa "UploadServlet" servletul care gestionează cererile HTTP de tip POST pentru încărcarea unui fișier și procesează acest fișier pentru a genera o reprezentare a unui graf, apoi redirecționează utilizatorii către o pagină numită "result.jsp" pentru a afișa rezultatele procesării acestui fișier in format DIMACS.

Clasa "Graph" este o clasa care modeleaza graful si proprietatile sale (nr noduri, nr muchii, nr componente conexe).

Clasa "GraphPropertiesService" este clasa care se ocupa de business-logic-ul aplicatiei, determina proprietatile grafului.

Clasa "LoggingFilter": A web filter that will log all requests received by input.jsp.

# Laborator 3 - homework

Accesand: http://localhost:8080/L3FINAL-1.0-SNAPSHOT/faces/projects.xhtml veti putea vedea proiectele. De asemenea veti putea adauga si sterge un proiect.
Accesand: http://localhost:8080/L3FINAL-1.0-SNAPSHOT/faces/seeStudents.xhtml veti putea vedea studentii. De asemenea veti putea adauga, sterge un student, veti putea vedea ce proiecte au fost asociate acelui student, veti putea asocia un nou proiect studentului respectiv.

Avem 3 tabele: student, project si project_student(Acest tabel este folosit pentru a realiza o asociere între studenți și proiecte)

Fișierele xhtml sunt utilizate pentru definirea interfeței utilizatorului și a componentelor JSF. Ele conțin etichete specifice JSF, cum ar fi <h:form>, <p:dataTable>, <p:commandButton>, etc.

Dialogurile create cu ajutorul componentelor p:dialog din PrimeFaces permit adăugarea de studenți și proiecte și afișarea detaliilor proiectelor asociate unui student.
