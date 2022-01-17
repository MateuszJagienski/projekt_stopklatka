package stopklatka.model;

import java.util.ArrayList;

public class UserAccount {
    private Double money;
    private ArrayList<Film> arrayList;

    public UserAccount(Double money) {
        this.money = money;
        this.arrayList = new ArrayList<>();
    }

    public void add(Double d) {
        if (this.money <= d) {
            this.money = 0.0;
        } else {
            this.money += d;
        }
    }

    public void addToList(Film film) {
        this.arrayList.add(film);
    }

    public Double getMoney() {
        return money;
    }

    public void setMoney(Double money) {
        this.money = money;
    }
}
