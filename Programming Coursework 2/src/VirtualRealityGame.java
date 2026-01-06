// VirtualRealityGame inherits from ActiveGame
public class VirtualRealityGame extends ActiveGame {

    // Public enum so it can be accessed from main and other classes
    public enum EquipmentType {
        VR_HEADSET,
        HEADSET_CONTROLLER,
        FULL_BODY_SUIT
    }

    private final EquipmentType equipmentType;

    // Constructor
    public VirtualRealityGame(String gameName, String gameID, int pricePerPlay, int minAge, EquipmentType equipmentType)
            throws InvalidGameIDException {
        super(gameName, gameID, pricePerPlay, minAge);
        if (gameID.length() != 10 || !gameID.startsWith("AV")) throw new InvalidGameIDException();
        this.equipmentType = equipmentType;
    }

    // Accessor method
    public EquipmentType getEquipmentType() {
        return equipmentType;
    }

    // Overrides calculatePrice method from ActiveGame
    @Override
    public int calculatePrice(boolean peak) {
        if (peak) {
            return getPricePerPlay();
        } else {
            if (equipmentType == EquipmentType.VR_HEADSET) {
                return (int) Math.floor(getPricePerPlay() * 0.9);
            } else if (equipmentType == EquipmentType.HEADSET_CONTROLLER) {
                return (int) Math.floor(getPricePerPlay() * 0.95);
            } else if (equipmentType == EquipmentType.FULL_BODY_SUIT) {
                return getPricePerPlay();
            } else {
                return getPricePerPlay(); // just in case of an unexpected value
            }
        }
    }

    // Overrides string toString method
    @Override
    public String toString() {
        return "VirtualRealityGame{" +
                "name=" + getName() +
                ", gameID=" + getGameID() +
                ", pricePerPlay=" + getPricePerPlay() +
                ", minAge=" + getMinAge() +
                ", equipmentType=" + getEquipmentType() +
                '}';
    }

    // Testing harness (Tests results of using different equipment types)
    public static void main(String[] args) {
        try {
            VirtualRealityGame vr = new VirtualRealityGame("VR Racing", "AV98767482", 200, 14, EquipmentType.VR_HEADSET);
            System.out.println(vr);
            System.out.println("Peak Price: " + vr.calculatePrice(true));
            System.out.println("OffPeak Price: " + vr.calculatePrice(false));

            VirtualRealityGame vr1 = new VirtualRealityGame("VR Shooter", "AV98367442", 200, 14, EquipmentType.HEADSET_CONTROLLER);
            System.out.println(vr1);
            System.out.println("Peak Price: " + vr1.calculatePrice(true));
            System.out.println("OffPeak Price: " + vr1.calculatePrice(false));

            VirtualRealityGame vr2 = new VirtualRealityGame("VR Shooter", "AV98367442", 200, 14, EquipmentType.FULL_BODY_SUIT);
            System.out.println(vr2);
            System.out.println("Peak Price: " + vr2.calculatePrice(true));
            System.out.println("OffPeak Price: " + vr2.calculatePrice(false));

        } catch (InvalidGameIDException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }
}
