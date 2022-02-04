package stopklatka.model;

import java.util.ArrayList;

public class UserAccount {
    private Double money;
    private ArrayList<Movie> arrayList;

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

    public void sub(Double d) {
        if (this.money <= d) {
            this.money = 0.0;
        } else {
            this.money -= d;
        }

    }

    public void addToList(ArrayList<Movie> arrayList) {
        this.arrayList.addAll(arrayList);
    }

    public Double getMoney() {
        return money;
    }

    public void setMoney(Double money) {
        this.money = money;
    }
}
