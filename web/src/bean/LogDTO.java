package bean;

public class LogDTO {
    private String date;
    private String coin;
    private String buy_value;
    private String sell_value;
    private String profit;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getCoin() {
        return coin;
    }

    public void setCoin(String coin) {
        this.coin = coin;
    }

    public String getBuy_value() {
        return buy_value;
    }

    public void setBuy_value(String buy_value) {
        this.buy_value = buy_value;
    }

    public String getSell_value() {
        return sell_value;
    }

    public void setSell_value(String sell_value) {
        this.sell_value = sell_value;
    }

    public String getProfit() {
        return profit;
    }

    public void setProfit(String profit) {
        this.profit = profit;
    }
}
