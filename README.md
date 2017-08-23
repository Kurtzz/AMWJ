# Zadanie 3

Celem tego projektu bedzie zaimplementowanie prostego "garbage collectora" (GC). Jego algorytm
zostanie opisany w pozniejszym terminie.
Jako ze testowanie GC musi miec generator obiektow i referencji miedzy nimi
najpierw zdefiniujemy prosty jezyk. Napisanie parsera i interpretera tego jezyka
jest czescia tego projektu.

#### NASZ PROSTY JEZYK (NPJ)

Skladnia:
```
<PROGRAM> ::= <STATEMENTS>
<STATEMENTS> ::= <STATEMENT> | <STATEMENTS> <STATEMENT>
<STATEMENT> = epsilon|<VARIABLE-DECLARATION>;|<ASSIGNMENT>;|Print "string
message";|Print <string-constant>;HeapAnalyze;|Collect;
<VARIABLE-DECLARATION> ::= VarDeclT <string-constant>;|VarDeclS <string-constant>
"<string-constant>";|VarDeclS <string-constant> NULL;
<ASSIGNMENT> ::= <LVALUE> = <RVALUE>
<LVALUE> ::= <DEREF>
<RVALUE> ::= <DEREF>|NULL|<integer-constant>;|"<string-constant>";
<DEREF> ::= <string-constant>;|<DEREF>.<string-constant>;
<DEREF> ::= <string-constant>;|<string-constant>.<string-constant>;
```
`<string-constant>` to ciag malych liter i liczb zaczynajacy sie od litery.

`<integer-constant>` to ciag cyfr oznaczalacych nieujemna stala calkowita.

W pierwszym kroku nalezy zbudowac z powyzszego opisu poprawna gramatyke.

#### Semantyka NPJ:

W jezyku NPJ sa dwa typy obiektow: `T` oraz `S` - lancuchy znakow.

Typ `T` sklada sie z dwoch pol: `f1` i `f2`, takze typu `T`, oraz pola data typu `int`. Rozmiar obiektow typu
`T` jest wiec zawsze taki sam.

Zadeklarowane obiekty sa jednoczesnie alokowane na stercie a ich pola ustawiane na domyslne
poczatkowe wartosci (`NULL` oraz 0, zaleznie od typu).

Przed uzyciem danej zmiennej musi byc ona zadeklarowana.

Slowo kluczowe `VarDeclT` sluzy do zadeklarowania zmiennej typu `T`.

Slowo kluczowe `VarDeclS` sluzy do zadeklarowania zmiennej typu `S` - lancuch znakow.

#### Przykladowe programy:

1.
```
VarDeclT treetop;
VarDeclT t1;
VarDeclT t2;
treetop.f1 = t1;
treetop.f2 = t2;
treetop.data = 1;
treetop.f1.data = 2;
treetop.f2.data = 3;
t1 = NULL;
```
2.
```
VarDeclT cycle;
cycle.f1 = cycle;
cycle.data = 23;
VarDeclS s1 "testMessage";
VarDeclS s2 "test2";
VarDeclS s3 "test3";
Print "stringMessage";
Print s1;
Print s2;
HeapAnalyze;
Collect;
HeapAnalyze;
```

NPJ sam zarzadza swoja sterta. Jest to tablica zadeklarowana w Javie jako `int[]`.

Kazdy obiekt `T` ma zajmowac cztery elementy w tablicy: naglowek, pola `f1` i `f2` (`NULL` 
jest reprezentowany przez 0, wskazniki do obiektow przez ich indeksy w stercie) oraz pole `data`.

Rozmiar obiektow typu `S` jest zmienny i rowny `2 + N` slow: naglowek, dlugosc lancucha znakow,
tablica z poszczegolnymi znakami.

Rozmiar tablicy (sterty) jest definiowany przy uruchamianiu interpretera NPJ za pomoca
`-Dnpj.heap.size=<size>`, gdzie size jest iloscia SLOW.

`Print` wypisuje na standardowe wyjscie wartosc lancucha znakow (podanego albo bezposrednio albo
jako nazwa zmiennej typu `S`) zakonczone znakiem nowej lini lub ciag znakow `NULL` np:
```
VarDeclS s1 "testMessage";
VarDeclS s2 "test2";
Print "stringMessage";
Print s1;
Print s2;
```
wypisze na ekranie:
```
stringMessage
testMessage
test2
```

__UWAGA:__ lancuchy znakow przekazane jako bezposredni argument wywolania instrukcji `Print` nie
wymagaja wolnego miejsca na stercie. Np. `Print "stringMessage"` nie alokuje nic na stercie.

__UWAGA:__ dla ulatwienia przyjmijmy, ze nawet gdy dwie zmienne typu `S` maja taka sama wartosc to
kazda z nich zajmuje tyle samo miejsca na stercie:

Program:
```
VarDeclS s1 "tekst1";
VarDeclS s2 "tekst1";
```
potrzebuje stery o rozmiarze 16 slow.

`HeapAnalyze` sluzy przekazaniu sterty do analizy - dokladniejsze informacje zostana podane
pozniej.

`Collect` sluzy uruchomieniu GC.

Komendy `HeapAnalyze` oraz `Collect` wolaja odpowiednie metody w klasie NPJ (nie trzeba jej
implementowac - za jej implementacje bede odpowiedzialny ja),
ktora dziala troche jak `java.lang.System`. A zatem interpretacja `HeapAnalyze` wywola
`NPJ.heapAnalyze(odpowiednie parametry)` a interpretacja `Collect` wywola
`NPJ.collect(int[], Collector, Map<Object, Object>)`. Collector implementuje interfejs:
```java
public interface Collector {
    public void collect(int[], Map<Object, Object>);
}
```
Konkretne implementacje kolektora beda implementowane przez panstwa (stad parametr
`Map<Object, Object>`, ktory mozecie uzyc do przekazania do waszego kolektora specyficznych
struktur danych).

Implementowanym przez Panstwa kolektoroem ma byc 'semi-space copying collector' - sterta
wejsciowa jest dzielona na dwie polowy jednakowego rozmiary, zalozmy ze `NULL` jest wspolny
dla obu polowek.

Inaczej mowiac rozmiar sterty podany jako parametr do interpertera spelniac bedzie warunek:
heapsize % 8 = 1.
