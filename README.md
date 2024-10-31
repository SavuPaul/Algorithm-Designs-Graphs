TEMA 2 - PROIECTAREA ALGORITMILOR

! Scheletul de cod pentru probleme este structurat ca cel din laboratoare. !

PROBLEMA 1 - Numarare
Pentru optimizarea temporala, rezolvarea problemei este facuta odata cu citirea, in aceeasi functie. Mai intai se citesc
muchiile pentru primul graf, iar apoi, stiind ca ne intereseaza doar muchiile comune, prelucrarea se va face doar pe
muchiile celui de-al doilea graf care exista deja in primul.
Rezolvarea consta intr-un vector "paths" unde valoarea fiecarui element, paths[i], reprezinta numarul de cai existente 
de la nodul 1 pana la nodul i. Aceste valori sunt updatate la fiecare citire a unei muchii care deja exista in graful 1.
Spre exemplu, daca printre lista de muchii avem:

- (1 -> 2), atunci paths[2] = 1
- (1 -> 4), atunci paths[4] = 1;
- (4 -> 2), atunci paths[2] = 2;
- (2 -> 7), atunci paths[7] = 2; adica valoarea nodului din care pleaca muchia

Pentru muchiile orientate inspre nodul N, varful din care iese muchia este adaugat intr-o stiva. Acest lucru se intampla
deoarece vrem ca toate nodurile din graf sa fie procesate (adica vectorul paths sa fie completat integral). Ulterior,
rezultatul final va fi compus din adunarea tuturor valorilor paths[j], unde j ia, pe rand, valorile introduse in stiva.

COMPLEXITATE: O(n^2) deoarece pe langa O(n)-ul din bucla unde se citesc muchiile pentru al doilea graf, exista si apelul
"contains" pe array list, avand, si el, complexitate O(n).



PROBLEMA 2 - Trenuri

Pentru rezolvarea acestei probleme, am folosit un HashMap in cadrul caruia am asociat fiecarui oras un numar, pentru a
usura lucrul cu listele de adiacenta. Urmatorul pas a fost sa extrag subgraful dintre nodul de start si nodul destinatie
deoarece nu ne intereseaza nodurile in care NU putem ajunge de la nodul de start. De asemenea, nu ne intereseaza nici
nodurile care se afla mai departe de nodul destinatie. In timp ce am extras subgraful, am calculat si gradul intern
pentru fiecare nod din acest subgraf, ceea ce ajuta ulterior la sortarea topologica a acestuia. Odata ce avem sortarea
topologica, scopul este ca pentru fiecare nod cu vecini din lista de adiacenta a subgrafului, sa crestem cu 1 distanta
vecinilor fata de nod. De exemplu:
Fie lista de adiacenta:
1: 2
2: 4
4: 3
Presupunem ca nodul de start este 1, iar cel destinatie este 3. Vectorul de distante va arata in felul urmator:
d[1] = 0, d[2] = d[1] + 1 = 1, d[4] = d[2] + 1 = 2, d[3] = d[4] + 1 = 3.
Numarul de muchii dintre start si destinatie este de 3, deci exista 3 + 1 = 4 orase diferite.

COMPLEXITATE: O(V + E)


PROBLEMA 3 - Drumuri
Pentru aceasta problema am folosit scheletul din laboratorul 10 de pe git.
Am aplicat algoritmul lui Dijkstra de 2 ori, prima oara pentru nodul x, iar a2a oara pentru nodul y. Ca si modificari,
am schimbat tipul de return al functiei dijkstra, aceasta returnand doar distanta de la sursa la nodul z, intrucat doar
aceasta ne intereseaza. De asemenea, costurile muchiilor sunt de tip long, iar rezultatul final este de tip BigInteger,
ceea ce permite ca numarul sa nu aiba o dimensiune maxima prestabilita. Astfel, costurile adunate pot depasi limita
tipului de date long.
In momentul in care executam cei 2 algoritmi Dijkstra, trebuie sa tinem cont de muchiile care deja au fost vizitate in
prima iteratie (de la x la z). Daca in cazul rularii algoritmului pentru nodul y, trecem printr-o muchie care deja a
fost utilizata in cadrul rularii pentru nodul x, nu trebuie sa mai tinem cont de ea la rezultatul final. Acest lucru
este facut cu campul "used" din Pair, care este incrementat pentru fiecare muchie care compune drumul de la x la z sau
de la y la z. Daca used == 2, atunci inseamna ca este a2a oara cand folosim muchia, deci scadem costul ei.

Informatiile pentru BigInteger le-am preluat de pe urmatorul forum:
https://stackoverflow.com/questions/21724945/store-a-number-that-is-longer-than-type-long-in-java

COMPLEXITATE: O(m * logn + n^2), acest n^2 provine de la cele 2 structuri repetitive unde verificam daca muchia este
folosita de 2 ori.


PROBLEMA 4 - Scandal
Nu este rezolvata.

