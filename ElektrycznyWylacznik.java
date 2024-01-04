
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
            this.swiatlo.wylaczone();
            this.stanSwiatla.ustawWylaczone();
        } else {
            this.swiatlo.wlaczone();
            this.stanSwiatla.ustawWlaczone();
        }
    }
}