# 🧩 Sudoku Game Project

## 📝 Opis projekta

Ta projekt je namenjen razvoju klasične ugankarske igre Sudoku, ki bo delovala na namiznih in Android napravah. Igra bo zgrajena z uporabo libGDX ogrodja, z dodatno podporo za Ashley ECS (Entity Component System) za upravljanje entitet, Freetype za prilagodljivo besedilno oblikovanje in orodji Scene2d za gradnjo uporabniškega vmesnika.

### 🎲 Dinamika igre

Sudoku je logična ugankarska igra, ki temelji na postavitvi številk. Cilj igre je izpolniti mrežo 9x9 tako, da vsaka vrstica, stolpec in vsak od devetih 3x3 podkvadratov vsebuje vsa števila od 1 do 9. Igra začne z delno izpolnjeno mrežo, ki igralcu omogoča, da s postopnim dodajanjem številk odkrije edinstveno rešitev.

### ⚙️ Mehanika igre

- **Začetek igre:** Igralec izbere težavnostno stopnjo, ki določa število že izpolnjenih polj na začetku igre.
- **Igranje:** Igralec klikne na prazno polje in vnaša številke od 1 do 9. Neveljavne poteze (kot so ponovitve števil v vrstici, stolpcu ali 3x3 kvadratu) so preprečene ali označene z rdečo.
- **Pomoč in namigi:** Igra lahko ponudi omejeno število namigov ali možnost preverjanja pravilnosti trenutnih vnosov.
- **Zmaga:** Igra se konča, ko so vsa polja pravilno izpolnjena.

### 🧩 Elementi igre

- **Mreža 9x9:** Osnovni igralni prostor, razdeljen na manjše 3x3 bloke.
- **⏱️ Časovnik:** Meri, koliko časa igralec porabi za rešitev uganke.
- **⚙️ Nastavitve:** Omogočajo igralcu, da prilagodi igro, npr. vklop/izklop samodejnega označevanja neveljavnih potez.
- **📈 Izbira težavnosti:** Omogoča izbiro med več težavnostnimi stopnjami, ki vplivajo na število predhodno izpolnjenih polj.
- **💾 Shranjevanje/ponovno nalaganje:** Možnost shranjevanja trenutnega stanja igre in nadaljevanja kasneje.

---


