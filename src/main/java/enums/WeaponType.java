package enums;

/**
 * @author Dimasavr
 */

public enum WeaponType {
    HAMMER (2),
    AXE (3),
    PISTOL (4),
    SHOTGUN (5),
    KNIFE (1);

    private final int degreeOfCool;

    WeaponType(int degreeOfCool) {
        this.degreeOfCool = degreeOfCool;
    }

    /**
     * Возвращает степень крутости оружия.
     * @return
     */
    public int getDegreeOfCool() {
        return degreeOfCool;
    }

    /**
     * Возвращает все значения enum
     * @return
     */
    public static String getV(){
        String v = "";
        for (WeaponType wt : WeaponType.values()){
            v += wt.toString() + " ";
        }
        return v;
    }
}
