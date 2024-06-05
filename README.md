Opis pewnego podejścia do SOLID



public class Swiatlo {
public void wlaczone() {
    System.out.println("Swiatlo: wlaczone");
}
public void wylaczone() {
    System.out.println("Swiatlo: wylaczone");
}
}


public class ElektrycznyWylacznik {
    public Swiatlo swiatlo;
    public boolean on;
    public ElektrycznyWylacznik(Swiatlo swiatlo) {
        this.swiatlo = swiatlo;
        this.on = false;
    }
    public boolean isOn() {
        return this.on;
    }
    public void nacisnij(){
        boolean sprawdzStan = isOn();
        if (sprawdzStan) {
            swiatlo.wylaczone();
            this.on = false;
        } else {
            swiatlo.wlaczone();
            this.on = true;
        }
    }
}



Mamy tutaj dwie klasy. Jedna z nich drukuje w konsoli tylko stan swiatla i nic więcej.  Druga z nich ma już troche logiki a dokładnie operuje tym światłem. Do operowania światłem służy metoda nacisnij. Metoda ta nic nie wie o prądzie i innych rzeczach potrzebnych. Wykonuje tylko operacje naciśnięcia przełącznika.

Wracając do tamtej treści było wspominane , jak wyżej napisałem , o zasadach SOLID.

Pomyślałem sobie, aby poukładać zgodnie z tymi regułami programowania ten kod, trzeba oddzielić, od razu stan, który jest w klasie ElektrycznyWylacznik.



public class StanSwiatla{
    public boolean on;
    public boolean isOn(){
        return this.on;
    }

    public void ustawWylaczone(){
        this.on = false;
    }

    public void ustawWlaczone(){
        this.on = true;
    }
}

 Z klasy ElektrycznyWylacznik można teraz pozbyć się zmiennej on a w jej miejsce wprowadzić instancje klasy StanSwiatla.

public class ElektrycznyWylacznik {
    public Swiatlo swiatlo;
    public StanSwiatla stanSwiatla;

...........

 Teraz, chcąc, niechcąc trzeba by, w moim odczuciu, uporządkować metode nacisnij w klasie ElektrycznyWylacznik.

...jej pierwotna definicja:

public void nacisnij(){
    boolean sprawdzStan = isOn();
    if (sprawdzStan) {
        swiatlo.wylaczone();
        this.on = false;
    } else {
        swiatlo.wlaczone();
        this.on = true;
    }
}

... po zmianie:

public void nacisnij(){
    boolean sprawdzStan = stanSwiatla.isOn();
    if (sprawdzStan) {
        swiatlo.wylaczone();
        this.stanSwiatla.ustawWylaczone();
    } else {
        swiatlo.wlaczone();
        this.stanSwiatla.ustawWlaczone();
    }
} 

 

Teraz klasa ElektrycznyWylacznik przyjmie postać:

public class ElektrycznyWylacznik {

    public Swiatlo swiatlo;
    public StanSwiatla stanSwiatla;

    public ElektrycznyWylacznik(Swiatlo swiatlo,StanSwiatla stanSwiatla) {
        this.swiatlo = swiatlo;
        this.stanSwiatla = stanSwiatla;
    }    
    public boolean isOn() {
        return this.stanSwiatla.isOn();
    }
    public void nacisnij(){
        boolean sprawdzStan = stanSwiatla.isOn();
        if (sprawdzStan) {
            swiatlo.wylaczone();
            this.stanSwiatla.ustawWylaczone();
        } else {
            swiatlo.wlaczone();
            this.stanSwiatla.ustawWlaczone();
        }
    }
}

 Biorąc teraz pod uwagę cały kod przyjmie on postać: 

 

public class Swiatlo {
    public void wlaczone() {
        System.out.println("Swiatlo: wlaczone");
    }
    public void wylaczone() {
        System.out.println("Swiatlo: wylaczone");
    }
}

public class StanSwiatla{
    public boolean on;
    public boolean isOn(){
        return this.on;
    }

    public void ustawWylaczone(){
        this.on = false;
    }

    public void ustawWlaczone(){
        this.on = true;
    }
}

 

public class ElektrycznyWylacznik {
 

    public Swiatlo swiatlo;
    public StanSwiatla stanSwiatla;

    public ElektrycznyWylacznik(Swiatlo swiatlo,StanSwiatla stanSwiatla) {
        this.swiatlo = swiatlo;
        this.stanSwiatla = stanSwiatla;
    }
    public boolean isOn() {
        return this.stanSwiatla.isOn();
    }
    public void nacisnij(){
        
        if ( this.stanSwiatla.isOn() ) {
            this.swiatlo.wylaczone();
            this.stanSwiatla.ustawWylaczone();
        } else {
            this.swiatlo.wlaczone();
            this.stanSwiatla.ustawWlaczone();
        }
        
    }    
}

Dla mnie ten kod wygląda lepiej a i podobne rozwiązanie potem znalazłem. 
Tutaj można się jeszcze pokusić o odpowiednie poustawianie dostępu by to co trzeba w klasach było widoczne bądź nie i która klasa ma być jak widoczna. 
To już może w innym czasie, bo z tym kodem można jeszcze popracować jeśli chodzi o wzorce projektowe. 
