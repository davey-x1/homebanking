package mindhub_homebanking.homebanking.enums;

public enum ColorEnum {
    GOLD(0),
    SILVER(1),
    TITANIUM(2);

    private int colorCode;

    ColorEnum(int colorCode){
        this.colorCode = colorCode;
    }

    public int getColorCode(){
        return this.colorCode;
    }

    public void setColorCode(int colorCode){
        this.colorCode = colorCode;
    }
}
