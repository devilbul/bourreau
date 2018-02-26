package fr.warframe.devilbul.utils.enumeration;

public enum ClanWarframe {
    Fantome("Fantome", 10, "https://vignette.wikia.nocookie.net/warframe/images/e/e2/LeaderBadgeGhostHolo.png/revision/latest/scale-to-width-down/50?cb=20140626180628"),
    Ombre("Ombre", 30, "https://vignette.wikia.nocookie.net/warframe/images/1/15/LeaderBadgeShadowHolo.png/revision/latest/scale-to-width-down/50?cb=20140626180629"),
    Tempete("Tempete", 100, "https://vignette.wikia.nocookie.net/warframe/images/6/66/LeaderBadgeStormHolo.png/revision/latest/scale-to-width-down/50?cb=20140626180630"),
    Montagne("Montagne", 300, "https://vignette.wikia.nocookie.net/warframe/images/c/c3/LeaderBadgeMountainHolo.png/revision/latest/scale-to-width-down/50?cb=20140626180629"),
    Lune("Lune", 1000, "https://vignette.wikia.nocookie.net/warframe/images/f/fb/LeaderBadgeMoonHolo.png/revision/latest/scale-to-width-down/50?cb=20140626180629");

    private String type;
    private int maxMember;
    private String urlImage;

    ClanWarframe(String type, int maxMember, String urlImage) {
        this.type = type;
        this.maxMember = maxMember;
        this.urlImage = urlImage;
    }

    @Override
    public String toString() {
        return this.type + " (" + this.maxMember + " membres max)";
    }

    public String getType() {
        return type;
    }

    public int getMaxMember() {
        return maxMember;
    }

    public String getUrlImage() {
        return urlImage;
    }
}
